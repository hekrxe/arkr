package com.arkr.boot.config.akka

import com.arkr.boot.config.akka.actor.wc.MasterActor
import org.springframework.context.annotation.{ComponentScan, Configuration}

/**
  * User: tanhuayou  
  * Date: 2018/3/8
  */
@Configuration
@ComponentScan(basePackageClasses = Array(classOf[MasterActor]))
private[akka] class WordCountExample
