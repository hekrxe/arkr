package com.arkr.hekr.controller

import com.arkr.hekr.exception.ArkrRuntimeException
import com.arkr.hekr.message.HttpResponse

import scala.util.{Failure, Success, Try}

/**
  * Created by hztanhuayou on 2017/8/19
  */
abstract class AbstractController {

  def body(fun: HttpResponse => Unit): HttpResponse = {
    val response = new HttpResponse
    val result = Try {
      fun(response)
    }
    result match {
      case Success(v) =>
      case Failure(e) =>
        e match {
          case rex: ArkrRuntimeException =>
            response.setCode(rex.code)
            response.setMessage(rex.message)
          case _ =>
            response.setCode(1001)
            response.setMessage(e.getMessage)
        }
    }
    response
  }
}
