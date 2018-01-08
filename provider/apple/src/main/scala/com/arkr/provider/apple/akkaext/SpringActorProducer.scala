package com.arkr.provider.apple.akkaext

import akka.actor.{Actor, IndirectActorProducer}
import org.springframework.context.ApplicationContext

/**
  * 实现IndirectActorProducer特质以增加一种创建Actor的方法
  * 调用Props时会创建这个对象
  *
  * @author hztanhuayou
  * @date 2018/1/8
  */
class SpringActorProducer(ctx: ApplicationContext, actorBeanName: String) extends IndirectActorProducer {

  override def produce: Actor = ctx.getBean(actorBeanName, classOf[Actor])

  override def actorClass: Class[_ <: Actor] = ctx.getType(actorBeanName).asInstanceOf[Class[_ <: Actor]]
}
