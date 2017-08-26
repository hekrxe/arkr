package com.arkr.near.controller

import com.arkr.hekr.controller.AbstractController
import com.arkr.hekr.message.HttpResponse
import com.arkr.hene.mq.kafka.KafkaQueueTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}


/**
  * Created by hztanhuayou on 2017/8/26
  */
@RestController
@RequestMapping(value = Array("/api/kafka"), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class KafkaTestController extends AbstractController with InitializingBean {
  private val logger = LoggerFactory.getLogger(classOf[KafkaTestController])

  @Autowired private var kafkaTemplate: KafkaTemplate[String, String] = _

  @RequestMapping(value = Array("/send"))
  def send(@RequestParam(value = "msg") msg: String): HttpResponse = {
    body((response: HttpResponse) => {
      logger.info(s"send msg: $msg")
      val unit = kafkaTemplate.send(KafkaQueueTopic.HELLO, msg)
      val result = unit.get()
      response.setResult(result)
    })
  }

  override def afterPropertiesSet(): Unit = {
    logger.info("kafkaTemplate autowired succeed!")
  }
}
