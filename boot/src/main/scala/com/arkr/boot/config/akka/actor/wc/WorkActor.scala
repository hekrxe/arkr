package com.arkr.boot.config.akka.actor.wc

import akka.Done
import akka.actor.Actor
import com.arkr.boot.config.akka.actor.{Line, Word}
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("workActor")
private[akka] class WorkActor extends Actor {

  override def receive: Receive = {
    case Line(line) =>
      line.split(" ").map((_, 1)).groupBy(_._1).mapValues(_.foldLeft(0)(_ + _._2)).foreach { case (word, count) =>
        sender() ! Word(word, count)
      }
      sender() ! Done
      context.stop(self)
    case _ =>
  }
}
