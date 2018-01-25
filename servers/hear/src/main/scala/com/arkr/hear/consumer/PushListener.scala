package com.arkr.hear.consumer

import com.alibaba.fastjson.JSON
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
  * @author hztanhuayou
  * @date 2018/1/25
  */

@Component("pushListener")
class PushListener {
  private val logger = LoggerFactory.getLogger(getClass)

  @KafkaListener(topics = Array("topic.push"))
  def receive(record: ConsumerRecord[String, String]): Unit = {
    logger.info(JSON.toJSONString(record, false))
  }

}
