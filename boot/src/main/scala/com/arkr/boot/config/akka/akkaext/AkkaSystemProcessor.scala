package com.arkr.boot.config.akka.akkaext

import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.ActorSystem
import com.arkr.boot.config.akka.actor.Start
import com.arkr.boot.config.akka.actor.wc.MasterActor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.{ApplicationContext, ApplicationContextAware, ApplicationListener}

/**
  * User: tanhuayou  
  * Date: 2018/3/8
  */
class AkkaSystemProcessor(run: Boolean) extends BeanPostProcessor with ApplicationContextAware with ApplicationListener[ContextRefreshedEvent] {
  private val logger = LoggerFactory.getLogger(getClass)
  private var application: ApplicationContext = _
  private var system: ActorSystem = _

  private val initialized = new AtomicBoolean(false)
  private val runnable = new AtomicBoolean(true)

  override def postProcessAfterInitialization(bean: Object, beanName: String): AnyRef = {
    if (!initialized.get() && bean.isInstanceOf[ActorSystem]) {
      system = bean.asInstanceOf[ActorSystem]
      SpringExt(system)(ctx = application)
      logger.info(s"Akka System inject to spring succeed,beanName=$beanName")
      initialized.set(true)
    }
    bean
  }

  override def postProcessBeforeInitialization(bean: Object, beanName: String): AnyRef = bean

  override def setApplicationContext(applicationContext: ApplicationContext): Unit = {
    this.application = applicationContext
  }

  override def onApplicationEvent(event: ContextRefreshedEvent): Unit = {
    if (run && runnable.get()) {
      system.actorOf(
        SpringExt(system)(application).props(classOf[MasterActor]),
        "AkkaWordCountMaster") ! Start("classpath:config/application.properties")
      runnable.set(false)
    }
  }
}
