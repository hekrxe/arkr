package com.arkr.near.controller

import java.sql.Date

import com.alibaba.fastjson.JSON
import com.arkr.hekr.controller.AbstractController
import com.arkr.hene.data.dao.UserDAO
import com.arkr.hene.data.model.User
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

  @RequestMapping(Array("/get"))
  def testRedis(@RequestParam("id") id: Long): User = {
    val user = userDAO.queryById(id)
    logger.info(s"User：${JSON.toJSONString(user, false)}")
    user
  }

  @RequestMapping(Array("/set"))
  def testRedis1(@RequestParam("name") name: String): String = {
    val user = new User
    user.setAge(((1000 * Math.random()) % 100).toLong)
    user.setCtm(new Date(System.currentTimeMillis()))
    user.setUsername(name)
    logger.info(s"User：${JSON.toJSONString(user, false)}")
    userDAO.insert(user)
    "OK"
  }

}
