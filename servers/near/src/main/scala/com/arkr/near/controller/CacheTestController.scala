package com.arkr.near.controller

import java.sql.Timestamp

import com.alibaba.dubbo.config.annotation.Reference
import com.alibaba.fastjson.JSON
import com.arkr.boot.dao.UserDAO
import com.arkr.boot.model.User
import com.arkr.common.controller.AbstractController
import com.arkr.service.echo.MyEchoService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

/**
  * Created by hztanhuayou on 2017/8/19
  */
@RestController
@RequestMapping(value = Array("/cache/test"))
class CacheTestController extends AbstractController {
  private val logger = LoggerFactory.getLogger(classOf[CacheTestController])

  @Autowired private var userDAO: UserDAO = _
  @Reference(version = "1.0.0") private var myEchoService: MyEchoService = _

  @RequestMapping(Array("/get"))
  def testRedis(@RequestParam("id") id: Long): User = {
    val user = userDAO.queryById(id)
    logger.info(s"User：${JSON.toJSONString(user, false)}")
    user
  }

  @RequestMapping(Array("/set"))
  def testRedis1(@RequestParam("name") name: String): String = {
    val user = new User
    user.setUsername(name)
    user.setPassword("121212")
    val now = new Timestamp(System.currentTimeMillis())
    user.setDbCreateTime(now)
    user.setDbUpdateTime(now)

    logger.info(s"User：${JSON.toJSONString(user, false)}")
    userDAO.insert(user)

    val dbUser = userDAO.queryByUsername(name)
    logger.info(s"dbUser: ${JSON.toJSONString(dbUser, false)}")

    "OK"
  }

  @RequestMapping(Array("/dubbo"))
  def testDubbo(): String = {
    myEchoService.echo("hi!") + " Dubbo"
  }

}
