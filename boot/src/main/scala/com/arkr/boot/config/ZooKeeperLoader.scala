package com.arkr.boot.config

import java.util

import com.alibaba.fastjson.JSON
import com.arkr.common.zk.ZKClient
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent
import org.apache.curator.utils.ZKPaths
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * @author hztanhuayou
  * @date 2017/12/21
  */
private[config] class ZooKeeperLoader(val zkPath: String) extends ConfigLoader {
  private val logger = LoggerFactory.getLogger(getClass)

  private val APPLICATION_NAMESPACE = ZKClient.Root.ZK_CONFIG

  private val children = mutable.ListBuffer[String]()

  @volatile private var properties: Map[String, String] = Map()

  /**
    * initialization
    */
  init()

  /**
    * 优先级选取依据
    *
    * @return
    */
  override def getOrder: Int = Int.MinValue

  /**
    * 获取key对应的配置值
    *
    * @param key 例如来自*.properties文件
    * @return 该key对应的值
    */
  override def get(key: String): String = properties.getOrElse(key, null)

  /**
    * callback 重新加载
    */
  override def reload(): Unit = {
    logger.info("Reload...")

    val tmpProperties = mutable.HashMap[String, String]()
    children.foreach(child => {
      val hashMap = ZKClient.getObject(child, classOf[util.HashMap[String, String]])
      if (hashMap.isDefined) {
        tmpProperties ++= hashMap.get.asScala
      }
    })

    properties.synchronized {
      properties = tmpProperties.toMap
    }

    logger.info("Reloaded")
  }

  private def init(): Unit = {
    children += ZKPaths.makePath(APPLICATION_NAMESPACE, "boot")
    children += ZKPaths.makePath(APPLICATION_NAMESPACE, zkPath)
    ZKClient.childrenPathWatcher(APPLICATION_NAMESPACE, (_, event: PathChildrenCacheEvent) => {
      event.getType match {
        case PathChildrenCacheEvent.Type.CHILD_ADDED
             | PathChildrenCacheEvent.Type.CHILD_REMOVED
             | PathChildrenCacheEvent.Type.CHILD_UPDATED =>
          val data = event.getData
          if (null != data) {
            val path = data.getPath
            if (children.contains(path)) {
              reload()
            }
          }
        case PathChildrenCacheEvent.Type.INITIALIZED =>
          reload()
        case _ =>
          logger.warn(JSON.toJSONString(event, false))
      }
    })
  }
}
