package com.arkr.hear.consumer

import com.alibaba.fastjson.JSON
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

/**
  * @author hztanhuayou
  * @date 2018/1/25
  */

@Component("pushListener")
class PushListener extends InitializingBean {
  private val logger = LoggerFactory.getLogger(getClass)

  @Autowired private var kafkaProducerTemplate: KafkaTemplate[String, String] = _

  @KafkaListener(topics = Array("topic.push"))
  def receive(record: ConsumerRecord[String, String]): Unit = {
    logger.info(JSON.toJSONString(record, false))
  }

  override def afterPropertiesSet(): Unit = {
    kafkaProducerTemplate.send("topic.push", "data........")
  }
}
