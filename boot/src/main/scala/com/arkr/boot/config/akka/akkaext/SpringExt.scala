package com.arkr.boot.config.akka.akkaext

import akka.actor.{Actor, ActorSystem, Extension, Props}
import org.springframework.context.ApplicationContext

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
private[akka] class SpringExt extends Extension {
  @volatile var applicationContext: ApplicationContext = _

  /**
    * 这个方法会被调用很多次
    *
    * @return
    */
  def initialize(implicit applicationContext: ApplicationContext): SpringExt = {
    this.applicationContext = applicationContext
    this
  }

  // 此扩展提供的方法，返回创建Actor所需的参数
  // 利用反射创建了一个SpringActorProducer对象，参数就是applicationContext, actorBeanName
  // 然后返回一个Props对象，这个对象描述了创建actor的方式
  // 即 clazz属性
  def props(actorBeanName: String): Props = {
    Props(classOf[SpringActorProducer], applicationContext, actorBeanName)
  }

  def props[T <: Actor](classOfActor: Class[T]): Props = {
    Props(classOf[SpringActorProducer], applicationContext, classOfActor)
  }
}

object SpringExt {
  def apply(system: ActorSystem)(implicit ctx: ApplicationContext): SpringExt = {
    // 这里主要是做创建并初始化
    // 在ActorSystem里 SpringExtension->SpringExt (即akka里会维护一堆扩展如Logging$Extension等,此时又加入了SpringExtension)
    // SpringExtension就是
    val ext = SpringExtension.get(system)
    ext.initialize
  }
}
