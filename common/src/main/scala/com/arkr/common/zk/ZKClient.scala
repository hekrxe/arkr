package com.arkr.common.zk

import java.nio.charset.Charset

import com.alibaba.fastjson.JSON
import com.arkr.common.exception.ArkrRuntimeException
import org.apache.curator.framework.recipes.cache.{NodeCache, PathChildrenCache, PathChildrenCacheListener}
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.utils.{PathUtils, ZKPaths}
import org.apache.zookeeper.CreateMode
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PropertiesLoaderUtils

/**
  * @author hztanhuayou
  * @date 2017/12/21
  */
private class ZKClient {
  private val DEF_CHARSET = Charset.forName("UTF-8")
  private val EMPTY_DATA = "" getBytes DEF_CHARSET
  private val CLIENT = init()
  private var APP_NAMESPACE: String = _

  @throws[Exception]
  def getObject[T](path: String, clazz: Class[T]): T = {
    val appPath = makePath(path)
    checkPath(appPath)

    val bytes = CLIENT.getData.forPath(appPath)
    JSON.parseObject(bytes, 0, bytes.length, DEF_CHARSET, clazz)
  }

  @throws[Exception]
  def setData(path: String, data: Object): Unit = {
    val appPath = makePath(path)
    checkPath(appPath)
    notNull(data)

    CLIENT.setData().forPath(appPath, JSON.toJSONString(data, false).getBytes(DEF_CHARSET))
  }

  @throws[Exception]
  def create(path: String, data: Object, createMode: CreateMode = CreateMode.PERSISTENT): Unit = {
    val appPath = makePath(path)
    if (!exist(appPath)) {
      val dataBytes = if (null == data) {
        EMPTY_DATA
      } else {
        JSON.toJSONString(data, false).getBytes(DEF_CHARSET)
      }
      CLIENT.create().creatingParentsIfNeeded().withMode(createMode).forPath(appPath, dataBytes)
    }
  }

  @throws[Exception]
  def delete(path: String): Unit = {
    val appPath = makePath(path)
    if (exist(appPath)) {
      CLIENT.delete().guaranteed().forPath(appPath)
    }
  }

  @throws[Exception]
  def deleteIfChildrenNeeded(path: String): Unit = {
    val appPath = makePath(path)
    if (exist(appPath)) {
      CLIENT.delete().guaranteed().deletingChildrenIfNeeded().forPath(appPath)
    }
  }

  @throws[Exception]
  def pathWatcher(path: String, listener: NodeListener): Unit = {
    val appPath = makePath(path)
    val nodeCache = new NodeCache(CLIENT, appPath, false)
    listener.setNodeCache(nodeCache)
    nodeCache.getListenable.addListener(listener)
    nodeCache.start(true)
  }

  @throws[Exception]
  def childrenPathWatcher(path: String, listener: PathChildrenCacheListener): Unit = {
    val appPath = makePath(path)
    val cache = new PathChildrenCache(CLIENT, appPath, true)
    cache.getListenable.addListener(listener)
    cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT)
  }

  @throws[Exception]
  def exist(path: String): Boolean = null != CLIENT.checkExists().forPath(path)

  @throws[Exception]
  private def checkPath(path: String): Unit = {
    if (!exist(path)) {
      throw ArkrRuntimeException.forMessage(ZKClient.Code.ZK_PATH_404, s"$path,not exist")
    }
  }

  @throws[Exception]
  private def notNull[T](obj: T): T = {
    if (null == obj) {
      throw ArkrRuntimeException.forMessage(ZKClient.Code.ZK_NOT_NULL, "obj not null")
    }
    obj
  }

  private def makePath(path: String): String = ZKPaths.makePath(APP_NAMESPACE, path)


  private def init(): CuratorFramework = {
    val loader = new DefaultResourceLoader()
    val resource = loader.getResource("classpath:config/zk.properties")
    val properties = PropertiesLoaderUtils.loadProperties(resource)
    val connectString = properties.getProperty("connect.string", "localhost:2181")
    val namespace = properties.getProperty("namespace", "arkr")
    val sessionTimeoutMs = properties.getProperty("session.timeout.millis", "60000").asInstanceOf[Int]
    val connectionTimeoutMs = properties.getProperty("connection.timeout.millis", "15000").asInstanceOf[Int]

    val client = CuratorFrameworkFactory.builder()
      .connectString(connectString)
      .sessionTimeoutMs(sessionTimeoutMs)
      .connectionTimeoutMs(connectionTimeoutMs)
      .retryPolicy(new ExponentialBackoffRetry(2000, 2))
      .namespace(namespace)
      .build()
    client.start()

    client
  }

}

object ZKClient {
  private val client = new ZKClient

  object Code {
    val ZK_PATH_404 = 404

    val ZK_NOT_NULL = 600
  }

  def apply(namespace: String): ZKClient = {
    PathUtils.validatePath(namespace)
    client.APP_NAMESPACE = namespace
    client
  }
}
