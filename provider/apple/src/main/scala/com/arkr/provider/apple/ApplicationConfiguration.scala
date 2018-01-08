package com.arkr.provider.apple

import akka.actor.ActorSystem
import com.arkr.provider.apple.akkaext.SpringExt
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Configuration
class ApplicationConfiguration {

  private val logger = Logger.getLogger(classOf[ApplicationConfiguration])
  @Autowired var applicationContext: ApplicationContext = _

  @Bean(name = Array("actorSystem"), destroyMethod = "shutdown")
  def actorSystem(): ActorSystem = {
    logger.info("Start Init ActorSystem")
    val system = ActorSystem("actorSystemApple")

    /**
      * 所有一切从这开始
      */
    SpringExt(system)(ctx = applicationContext)
    logger.info("ActorSystem Started")
    // 下面这行(创建Actor)的关键源码:
    // akka.actor.ActorRefProvider
    // val mailboxType = system.mailboxes.getMailboxType(props2, dispatcher.configurator.config)
    // 这里props2就是 props
    // 然后通过props2得到ctx里的clazz 反射创建对象
    //    system.actorOf(props, "aasa")
    system
  }

}
