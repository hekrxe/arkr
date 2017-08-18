package com.arkr.demos.near

import java.util

import com.arkr.demos.near.config.ApplicationConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.{Banner, SpringApplication}
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * Created by hztanhuayou on 2017/8/17
  */
@RestController
@SpringBootApplication(scanBasePackageClasses = Array(classOf[ApplicationConfig]))
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration]))
class Application {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  private val ut = System.currentTimeMillis()

  @RequestMapping(value = Array("/"))
  def home: Object = {
    logger.info("/ access")
    val myInfo = new util.HashMap[String, Object]()
    myInfo.put("name", "near")
    myInfo.put("version", "3.10")
    myInfo.put("ut", Long.box(ut))
    myInfo
  }

}


object Application {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  def main(args: Array[String]): Unit = {
    val application = new SpringApplication(classOf[Application])
    application.setBannerMode(Banner.Mode.OFF)
    application.run(args: _*)
    logger.info("Started")
  }

}
