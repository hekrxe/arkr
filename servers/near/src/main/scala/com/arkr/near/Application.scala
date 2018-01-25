package com.arkr.near

import java.util

import com.arkr.boot.config.cache.EnableDataCaching
import com.arkr.boot.config.db.EnableDataSource
import com.arkr.boot.config.redis.EnableRedisTemplate
import com.arkr.boot.config.sys.{SysConfig, SysConfigLoader}
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{Banner, SpringApplication}
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * Created by hztanhuayou on 2017/8/17
  */
@RestController
@EnableDataSource
@EnableDataCaching
@EnableRedisTemplate
@SpringBootApplication
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

object Application extends App {
  private val logger = LoggerFactory.getLogger(classOf[Application])

  SysConfigLoader.apply(Array("classpath:config/res.properties"), "near")
  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.run(args: _*)
  logger.info(s"${SysConfig.getOrElse("env", null)} Started")
  logger.info(SysConfig.getOrElse("abc", "aa"))
}


private object Sort extends App {

  def quickSort(list: List[Int]): List[Int] = list match {
    case Nil => Nil
    case x :: xs =>
      val (before, after) = xs partition (_ < x)
      quickSort(before) ++ (x :: quickSort(after))
  }

}