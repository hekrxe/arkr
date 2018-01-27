package com.arkr.link

import com.arkr.boot.config.kafka.EnableKafkaConfig
import com.arkr.boot.config.sys.{SysConfig, SysConfigLoader}
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.{Banner, SpringApplication}

/**
  * @author hztanhuayou
  * @date 2018/1/27
  */
@EnableKafkaConfig
@SpringBootApplication
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration]))
class Application

object Application extends App {
  private val logger = LoggerFactory.getLogger(classOf[Application])
  SysConfigLoader.apply(Array("classpath:config/res.properties"), "link")

  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.run(args: _*)
  logger.info(SysConfig.getString("who"))
  logger.info("Started")
}
