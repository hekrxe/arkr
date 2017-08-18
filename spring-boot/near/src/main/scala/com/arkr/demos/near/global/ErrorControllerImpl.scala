package com.arkr.demos.near.global

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

/**
  * Created by hztanhuayou on 2017/8/17
  */
@Controller
class ErrorControllerImpl extends ErrorController {
  private val ERROR_404_PAGE = "error"

  override def getErrorPath: String = ERROR_404_PAGE

  @RequestMapping(value = Array("/error"))
  def errorHtml(request: HttpServletRequest, response: HttpServletResponse): ModelAndView = {
    val mav = new ModelAndView(ERROR_404_PAGE)
    mav.addObject("info", "ErrorInfo")
    mav
  }
}
