package com.arkr.near.config

import java.util

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
  * springMVC 配置
  * Created by hztanhuayou on 2017/8/17
  */
@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    super.configureMessageConverters(converters)
    val fastConverter = new FastJsonHttpMessageConverter
    val fastJsonConfig = new FastJsonConfig
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
    fastConverter.setFastJsonConfig(fastJsonConfig)
    converters.add(fastConverter)
  }

}
