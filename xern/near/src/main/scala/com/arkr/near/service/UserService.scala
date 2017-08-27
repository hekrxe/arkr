package com.arkr.near.service

import com.alibaba.dubbo.config.annotation.Reference
import com.arkr.hekr.model.User
import com.arkr.xehe.service.hexe.MyEchoService
import org.springframework.stereotype.{Component, Service}

/**
  * Created by hztanhuayou on 2017/8/27
  */
@Component("userService")
class UserService {
  @Reference(version = "1.0.0") private var myEchoService: MyEchoService = _

  def echo(str: String): String = {
    myEchoService.echo(str)
  }

  def queryById(id: Long): User = {
    myEchoService.queryById(id)
  }


}
