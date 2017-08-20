package com.arkr.near.config

import com.arkr.hene.data.config.DataConfig
import com.arkr.near.SpringComponentScanMarker
import org.springframework.context.annotation.{ComponentScan, Configuration, Import}

/**
  * 应用程序根配置
  * Created by hztanhuayou on 2017/8/17
  */
@Configuration
@Import(value = Array(classOf[DataConfig], classOf[WebMvcConfig]))
@ComponentScan(basePackageClasses = Array(classOf[SpringComponentScanMarker]))
class ApplicationConfig {
  // do nothing
}
