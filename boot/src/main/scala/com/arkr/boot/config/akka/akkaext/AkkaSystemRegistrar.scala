package com.arkr.boot.config.akka.akkaext

import akka.actor.ActorSystem
import com.arkr.boot.config.akka.EnableAkka
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.{BeanDefinitionBuilder, BeanDefinitionReaderUtils, BeanDefinitionRegistry}
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.`type`.AnnotationMetadata
import org.springframework.core.annotation.AnnotationAttributes

/**
  * User: tanhuayou  
  * Date: 2018/3/8
  */
private[akka] class AkkaSystemRegistrar extends ImportBeanDefinitionRegistrar {
  private val logger = LoggerFactory.getLogger(getClass)

  override def registerBeanDefinitions(metadata: AnnotationMetadata, registry: BeanDefinitionRegistry): Unit = {
    val attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(classOf[EnableAkka].getName))
    val systemName = attributes.getString("akkaSystemName")
    val run = attributes.getBoolean("run")

    registerAkkaSystem(systemName, registry)

    registerAkkaSystemProcessor(run, registry)
  }

  private def registerAkkaSystem(systemName: String, registry: BeanDefinitionRegistry): Unit = {
    val builder = BeanDefinitionBuilder.rootBeanDefinition(classOf[ActorSystem], "apply")
    builder.addConstructorArgValue(systemName)
    builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
    val definition = builder.getBeanDefinition
    BeanDefinitionReaderUtils.registerWithGeneratedName(definition, registry)
    logger.info(s"Akka System: $systemName registered")
  }

  private def registerAkkaSystemProcessor(run: Boolean, registry: BeanDefinitionRegistry): Unit = {
    val builder = BeanDefinitionBuilder.rootBeanDefinition(classOf[AkkaSystemProcessor])
    builder.addConstructorArgValue(run)
    builder.setRole(BeanDefinition.ROLE_APPLICATION)
    val definition = builder.getBeanDefinition
    BeanDefinitionReaderUtils.registerWithGeneratedName(definition, registry)
  }
}
