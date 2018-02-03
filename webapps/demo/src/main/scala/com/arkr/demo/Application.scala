package com.arkr.demo

import com.arkr.boot.config.sys.{SysConfig, SysConfigLoader}
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.{Banner, SpringApplication}

/**
  * Created by hztanhuayou on 2017/8/17
  */
@SpringBootApplication
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration]))
class Application

object Application extends App {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  SysConfigLoader.apply(Array("classpath:config/res.properties"), "near")
  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.run(args: _*)
  logger.info(s"${SysConfig.getOrElse("env", null)} Started")
  logger.info(SysConfig.getOrElse("abc", "aa"))
}