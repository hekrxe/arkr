package com.arkr.hear.config

import com.arkr.hear.SpringComponentScanMarker
import com.arkr.hene.data.config.DataConfig
import com.arkr.hene.mq.kafka.KafKaConfig
import org.springframework.context.annotation.{ComponentScan, Configuration, Import}

/**
  * Created by hztanhuayou on 2017/8/26
  */
@Configuration
@Import(value = Array(
  classOf[DataConfig],
  classOf[KafKaConfig]
))
@ComponentScan(basePackageClasses = Array(classOf[SpringComponentScanMarker]))
class ApplicationConfig {
  // do nothing
}