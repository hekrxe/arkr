package com.arkr.hear

import com.arkr.boot.config.cache.EnableDataCaching
import com.arkr.boot.config.db.EnableDataSource
import com.arkr.boot.config.redis.EnableRedisTemplate
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{Banner, SpringApplication}

/**
  * Created by hztanhuayou on 2017/8/26
  */
@EnableDataSource
@EnableDataCaching
@EnableRedisTemplate
@SpringBootApplication
class Application

object Application extends App {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.run(args: _*)
  logger.info("Started")
}


