package com.arkr.hekr.sys.config

import java.util.Properties

import org.slf4j.LoggerFactory
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PropertiesLoaderUtils

import scala.collection.mutable
import scala.collection.JavaConverters._

/**
  * Created by hztanhuayou on 2017/9/2
  */
private[config] class PropertiesConfigLoader(val profiles: List[String]) extends ConfigLoader {
  private val logger = LoggerFactory.getLogger(getClass)

  private val properties = initializeDefaultProperties()

  override def getOrder: Int = Int.MaxValue

  override def get(key: String): String = {
    properties.getOrElse(key, null)
  }

  def load(properties: List[Properties]): Unit = {
  }

  private def initializeDefaultProperties(): Map[String, String] = {
    logger.info(s"Start load properties, profile=$profiles")

    val configProperties = mutable.HashMap[String, String]()
    val defaultProperties = List("common.properties", "boot.properties")

    val resourceLoader = new DefaultResourceLoader()
    defaultProperties.foreach(defaultProperty => {
      profiles.foreach(profile => {
        val resource = resourceLoader.getResource(s"classpath:/config/$profile/$defaultProperty")
        val properties = PropertiesLoaderUtils.loadProperties(resource)
        if (null != properties) {
          configProperties ++= properties.asInstanceOf[java.util.Map[String, String]].asScala
        }
      })
    })

    logger.info(s"Load properties succeed, profile=$profiles")
    configProperties.foreach { case (k, v) =>
      logger.info(s"$k ========== $v")
    }
    configProperties.toMap
  }
}
