package com.arkr.provider.apple.actor.wc

import akka.actor.{Actor, ActorSystem}
import com.arkr.provider.apple.actor.{Line, Start, Word}
import com.arkr.provider.apple.akkaext.SpringExt
import com.arkr.provider.apple.service.LocalService
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
class MasterActor extends Actor {
  private val logger = LoggerFactory.getLogger(getClass)

  @Autowired var actorSystem: ActorSystem = _
  @Autowired var applicationContext: ApplicationContext = _
  @Autowired private var localService: LocalService = _

  private var workCount = 0
  private var workReceive = 0

  private val list = mutable.ListBuffer[(String, Int)]()

  override def receive: Receive = {
    case Start(file) =>
      logger.info(localService.echo(file))
      Source.fromFile(ResourceUtils.getFile(file)).getLines().foreach(line => {
        logger.info(line)
        val workRef = actorSystem.actorOf(SpringExt.apply(actorSystem)(applicationContext).props("workActor"), s"workActor-name-$workCount")
        workRef ! Line(line)
        workCount += 1
      })

    case Word(word, count) =>
      list += ((word, count))
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
