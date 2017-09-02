package com.arkr.near.listeners

import org.slf4j.LoggerFactory
import org.springframework.context.{ApplicationEvent, ApplicationListener}
import org.springframework.stereotype.Component

/**
  * Created by hztanhuayou on 2017/8/29
  */
@Component
class AppListener extends ApplicationListener[ApplicationEvent] {
  private val logger = LoggerFactory.getLogger(getClass)

  override def onApplicationEvent(event: ApplicationEvent): Unit = {
    logger.info(s"App: ${event.getClass.getSimpleName}")
  }
}
