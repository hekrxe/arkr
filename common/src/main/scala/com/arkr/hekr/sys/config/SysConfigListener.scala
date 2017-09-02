package com.arkr.hekr.sys.config

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.util.StringUtils

/**
  * Created by hztanhuayou on 2017/8/29
  */
class SysConfigListener extends ApplicationListener[ApplicationEnvironmentPreparedEvent] {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
    * 在SpringBoot启动过程中会给监听器发送这些事件
    * ||
    * ApplicationStartedEvent == SpringBoot 开始启动了(run)
    * 此时已经把各种Listener加载进SpringApplicationRunListeners进行管理(SpringApplicationRunListener的唯一一个实现类 EventPublishingRunListener 管理)
    * |
    * ApplicationEnvironmentPreparedEvent == 初始化环境变量
    * 此时主要初始化了系统环境变量及web(app)的系统级环境变量
    * 例如可以拿到 profiles are active: dev...(其实是 ConfigFileApplicationListener 这个监听器第一个被执行,然后初始化进去的)
    * 此时的environment中只能拿到 application-[].properties[yml] 文件
    * ||
    * ApplicationPreparedEvent == Context准备ContextLoaded完成
    * 创建一个ApplicationContext
    * 如: internalConfigurationBeanNameGenerator的设置,BeanFactory的初始化等
    * |
    * ContextRefreshedEvent == Context刷新
    * Spring 核心流程如 DI AOP等,资源文件加载(各种.properties/.yml)等
    * invokeBeanFactoryPostProcessors
    * |_PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors)
    *
    * 同时将 ListenerBean加入到监听器列表(这个监听器列表是ApplicationContext的列表而非SpringBoot的,叫ApplicationEventMulticaster)
    * 但是在加入之前会先把 EventPublishingRunListener 管理的监听器先加入进去(这个优先级高)
    * ||
    * EmbeddedServletContainerInitializedEvent == Servlet初始化完成
    * |
    * ApplicationReadyEvent == 应用已启动完成
    * 启动App(ServletContainer)完成后
    */
  // 由于此处做的是全局的配置文件可读,即在Bean初始化前都可以读那么只能在 ContextRefreshedEvent 这个事件前进行
  override def onApplicationEvent(event: ApplicationEnvironmentPreparedEvent): Unit = {
    event.getEnvironment.getActiveProfiles.foreach(profile => {
      val pf = profile.trim
      if (StringUtils.hasText(pf)) {
        SysConfigImpl.load(new PropertiesConfigLoader(pf))
      }
    })

  }
}
