package com.arkr.hear.handler

import com.arkr.hene.mq.kafka.KafkaQueueTopic
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
  * Created by hztanhuayou on 2017/8/26
  */
@Component
class MyKafkaListener {
  private val logger = LoggerFactory.getLogger(classOf[MyKafkaListener])

  @KafkaListener(topics = Array(KafkaQueueTopic.HELLO, "my-replicated-topic2"))
  def receiveMsgFromKafka(msg: String): Unit = {
    logger.info(s"receive msg: $msg")
  }
}
