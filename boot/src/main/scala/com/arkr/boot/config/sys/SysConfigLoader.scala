package com.arkr.boot.config.sys

import java.util.concurrent.atomic.AtomicBoolean

import org.springframework.util.StringUtils

import scala.collection.mutable

/**
  * @author hztanhuayou
  * @date 2017/12/5
  */

object SysConfigLoader {

  private val INITIALIZED = new AtomicBoolean(false)
  private val LOCK = new Object

  private def onApplicationStart(resources: List[String], zkPath: String): Unit = {
    if (!INITIALIZED.get()) {
      LOCK.synchronized {
        if (!INITIALIZED.get()) {
          val propertiesLoader = if (null == resources) {
            new PropertiesLoader(List())
          } else {
            new PropertiesLoader(resources)
          }
          val config = mutable.ListBuffer[ConfigLoader]()
          config += propertiesLoader
          if (!StringUtils.hasText(zkPath)) {
            config += new ZooKeeperLoader(zkPath)
          }
          SysConfigImpl.load(config.toList)
        }
      }
      INITIALIZED.set(true)
    }
  }

  def apply(resources: Array[String], zkPath: String = null): Unit = onApplicationStart(resources.toList, zkPath)
}
