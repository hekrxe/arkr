package com.arkr.near.global

import javax.servlet.http.HttpServletRequest

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}
import org.springframework.web.servlet.ModelAndView

/**
  * Created by hztanhuayou on 2017/8/17
  */
@ControllerAdvice
class GlobalDefaultExceptionHandler {
  private val logger = LoggerFactory.getLogger(classOf[GlobalDefaultExceptionHandler])

  @ExceptionHandler(value = Array(classOf[Exception]))
  def defaultErrorHandler(req: HttpServletRequest, e: Exception): Any = {
    logger.error(s"${req.getRequestURI},ex=${e.getMessage}", e)
    val mav = new ModelAndView("error")
    mav.addObject("info", e.getMessage)
    mav
  }
}

