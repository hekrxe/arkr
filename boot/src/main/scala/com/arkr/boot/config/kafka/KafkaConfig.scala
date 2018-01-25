package com.arkr.boot.config.kafka

import java.util

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.{ConcurrentKafkaListenerContainerFactory, KafkaListenerContainerFactory}
import org.springframework.kafka.core.{DefaultKafkaConsumerFactory, DefaultKafkaProducerFactory, KafkaTemplate}
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

/**
  * @author hztanhuayou
  * @date 2018/1/25
  */
@Configuration
@EnableKafka
@PropertySource(value = Array("classpath:config/kafka.properties"))
private[kafka] class KafkaConfig extends InitializingBean {
  private val logger = LoggerFactory.getLogger(getClass)
  /**
    * 127.0.0.1:9092,xxx.com:9991,...
    */
  @Value(value = "${kafka.broker.list}")
  private var SERVERS: String = _

  @Value(value = "${kafka.consumer.group}")
  private var GROUP: String = _

  @Value(value = "${kafka.consumer.listener.concurrency:10}")
  private var CONCURRENCY: Integer = _

  @Bean(name = Array("kafkaProducerConfig"))
  def producerConfig: util.Map[String, Object] = {
    val map = new util.HashMap[String, Object]()
    map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS)
    map.put(ProducerConfig.RETRIES_CONFIG, Int.box(0))
    map.put(ProducerConfig.BATCH_SIZE_CONFIG, Int.box(4096))
    map.put(ProducerConfig.LINGER_MS_CONFIG, Int.box(1))
    map.put(ProducerConfig.BUFFER_MEMORY_CONFIG, Int.box(40960))
    map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    map
  }

  @Bean(name = Array("kafkaProducerFactory"))
  def producerFactory = new DefaultKafkaProducerFactory[String, String](producerConfig)

  @Bean(name = Array("kafkaProducerTemplate"))
  def kafkaProducerTemplate = new KafkaTemplate[String, String](producerFactory)

  @Bean(name = Array("kafkaConsumerConfig"))
  def consumerConfig: util.Map[String, Object] = {
    val map = new util.HashMap[String, Object]()

    map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS)
    map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.box(false))
    map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100")
    map.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000")
    map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    map.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP)
    map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    map
  }


  @Bean(name = Array("kafkaConsumerFactory"))
  def consumerFactory = new DefaultKafkaConsumerFactory[String, String](consumerConfig)

  @Bean(name = Array("kafkaListenerContainerFactory"))
  def kafkaListenerContainerFactory: KafkaListenerContainerFactory[ConcurrentMessageListenerContainer[String, String]] = {
    val factory = new ConcurrentKafkaListenerContainerFactory[String, String]
    factory.setConsumerFactory(consumerFactory)
    factory.setConcurrency(CONCURRENCY)
    factory.getContainerProperties.setPollTimeout(3000)
    factory
  }


  override def afterPropertiesSet(): Unit = {
    logger.info("Kafka config succeed.")
  }
}