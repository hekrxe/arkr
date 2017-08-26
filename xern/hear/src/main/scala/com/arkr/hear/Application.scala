package com.arkr.hear

import com.arkr.hear.config.ApplicationConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{Banner, SpringApplication}

/**
  * Created by hztanhuayou on 2017/8/26
  */
@SpringBootApplication(scanBasePackageClasses = Array(classOf[ApplicationConfig]))
class Application

object Application extends App {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.run(args: _*)
  logger.info("Started")
}


