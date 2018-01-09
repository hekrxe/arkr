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
class SpringActorProducer private(val ctx: ApplicationContext) extends IndirectActorProducer {
  private var classOfActor: Class[_ <: Actor] = _
  private var actorBeanName: String = _

  def this(ctx: ApplicationContext, actorBeanName: String) {
    this(ctx)
    this.actorBeanName = actorBeanName
  }

  def this(ctx: ApplicationContext, classOfActor: Class[_ <: Actor]) {
    this(ctx)
    this.classOfActor = classOfActor
  }

  override def produce: Actor = {
    if (classOfActor eq null) {
      ctx.getBean(actorBeanName, classOf[Actor])
    } else {
      ctx.getBean(classOfActor)
    }
  }

  override def actorClass: Class[_ <: Actor] = {
    if (classOfActor ne null) {
      classOfActor
    } else {
      ctx.getType(actorBeanName).asInstanceOf[Class[_ <: Actor]]
    }
  }
}
