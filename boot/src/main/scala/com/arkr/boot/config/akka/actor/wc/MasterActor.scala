package com.arkr.boot.config.akka.actor.wc

import akka.actor.{Actor, ActorSystem}
import com.arkr.boot.config.akka.actor.{Done, Line, Start, Word}
import com.arkr.boot.config.akka.akkaext.SpringExt
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils

import scala.collection.mutable
import scala.io.Source

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("masterActor")
private[akka] class MasterActor extends Actor {
  private val logger = LoggerFactory.getLogger(getClass)

  @Autowired var actorSystem: ActorSystem = _
  @Autowired var applicationContext: ApplicationContext = _

  private var workCount = 0
  private var workReceive = 0

  private val list = mutable.ListBuffer[(String, Int)]()

  override def receive: Receive = {
    case Start(file) =>
      logger.info(file)
      Source.fromFile(ResourceUtils.getFile(file)).getLines().foreach(line => {
        val workRef = actorSystem.actorOf(SpringExt.apply(actorSystem)(applicationContext)
          .props(classOf[WorkActor]), s"workActor-name-$workCount")
        workRef ! Line(line)
        workCount += 1
      })

    case Word(word, count) =>
      list += ((word, count))
    case Done =>
      workReceive += 1
      if (workReceive == workCount) {
        list.groupBy(_._1).foreach(wd => {
          val sum = wd._2.foldLeft(0)(_ + _._2)
          logger.info(s"${wd._1}${"*" * 20}$sum")
        })
        context.stop(self)
      }
    case _ =>
  }
}
