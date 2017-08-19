package com.arkr.near.controller

import com.alibaba.fastjson.JSON
import com.arkr.hekr.controller.AbstractController
import com.arkr.near.{Demo, DemoService}
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

  @Autowired private var demoService: DemoService = _

  @RequestMapping(Array("/get"))
  def testRedis(@RequestParam("id") id: Long): String = {
    val demo = demoService.getById(id)
    logger.info("demo: " + JSON.toJSONString(demo, false))
    val demoEx = demoService.getByIdEx(id)
    logger.info("demoEx: " + JSON.toJSONString(demoEx, false))
    "OK"
  }

  @RequestMapping(Array("/set"))
  def testRedis1(@RequestParam("id") id: Long,
                 @RequestParam("value") value: String): String = {
    val demo = new Demo().setId(id).setName(value)
    demoService.save(demo)
    demoService.saveEx(demo)
    "OK"
  }

}
