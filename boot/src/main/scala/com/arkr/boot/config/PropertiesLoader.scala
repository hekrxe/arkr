package com.arkr.boot.config

import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.support.PropertiesLoaderUtils

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * 资源文件加载器
  * 会默认加载 common,device两个文件
  *
  * @param resources 加载哪些资源文件
  * @author hztanhuayou
  * @date 2017/12/5
  */
private[config] class PropertiesLoader(val resources: List[String]) extends ConfigLoader {

  @volatile private var PROPERTIES = init()

  /**
    * 优先级选取依据
    *
    * @return
    */
  override def getOrder: Int = Int.MaxValue

  /**
    * 获取key对应的配置值
    *
    * @param key 例如来自*.properties文件
    * @return 该key对应的值
    */
  override def get(key: String): String = PROPERTIES.getOrElse(key, null)


  /**
    * callback 重新加载
    */
  override def reload(): Unit = {
    PROPERTIES = init()
  }

  private def init(): Map[String, String] = {
    val loader = new DefaultResourceLoader()
    val configProperties = mutable.HashMap[String, String]()

    mutable.ListBuffer(
      "classpath:config/boot.properties"
    ).++=(resources).foreach(source => {
      val resource = loader.getResource(source)
      val properties = PropertiesLoaderUtils.loadProperties(resource)
      if (null != properties) {
        configProperties ++= properties.asInstanceOf[java.util.Map[String, String]].asScala
      }
    })

    configProperties.toMap
  }

}
