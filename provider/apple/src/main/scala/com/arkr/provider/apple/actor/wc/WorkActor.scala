package com.arkr.provider.apple.actor.wc

import akka.actor.Actor
import com.arkr.provider.apple.actor.{Line, Word}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("workActor")
class WorkActor extends Actor {
  private val logger = LoggerFactory.getLogger(getClass)

  override def receive: Receive = {
    case Line(line) =>
      line.split(" ").map((_, 1)).groupBy(_._1).mapValues(_.foldLeft(0)(_ + _._2)).foreach { case (word, count) =>
        logger.info(s"$word${"=" * 10}$count")
        sender() ! Word(word, count)
      }
      context.stop(self)
    case _ =>
  }
}
