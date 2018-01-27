package com.arkr.link

import com.alibaba.fastjson.JSON
import com.corundumstudio.socketio.annotation.{OnConnect, OnDisconnect, SpringAnnotationScanner}
import com.corundumstudio.socketio.{SocketConfig, SocketIOClient, SocketIOServer, Transport, Configuration => SocketIOConfiguration}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.{Bean, PropertySource, Configuration => SpringConfiguration}
import org.springframework.core.annotation.Order

/**
  *
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
  *
  * @author hztanhuayou
  * @date 2018/1/27
  */
@Order(value = Int.MaxValue - 1)
@SpringConfiguration
@PropertySource(value = Array("classpath:config/link.properties"))
class LinkConfig extends ApplicationListener[ApplicationReadyEvent] {
  private val logger = LoggerFactory.getLogger(getClass)

  private var SOCKET_IO_SERVER: SocketIOServer = _
  private var STARTED = false

  @Value(value = "${link.hostname:127.0.0.1}")
  private var HOSTNAME: String = _

  @Value(value = "${link.port:12306}")
  private var PORT: Integer = _

  @Order(value = Int.MaxValue - 1)
  @Bean(name = Array("socketIOServer"))
  def socketIOServer: SocketIOServer = {
    val configuration = new SocketIOConfiguration
    configuration.setHostname(HOSTNAME)
    configuration.setPort(PORT)
    configuration.setTransports(Transport.WEBSOCKET)

    val socketConfig = new SocketConfig()
    socketConfig.setReuseAddress(true)
    configuration.setSocketConfig(socketConfig)

    SOCKET_IO_SERVER = new SocketIOServer(configuration)

    SOCKET_IO_SERVER
  }

  /**
    * 扫描 用了
    * com.corundumstudio.socketio.annotation.OnConnect,
    * com.corundumstudio.socketio.annotation.OnDisconnect,
    * com.corundumstudio.socketio.annotation.OnEvent
    * 注解 的Bean
    * 以便把它们加载到 SocketIOServer 的监听器里
    *
    * @return
    */
  @Bean
  def springAnnotationScanner: SpringAnnotationScanner = {
    new SpringAnnotationScanner(socketIOServer)
  }

  @OnConnect
  def onConnect(client: SocketIOClient): Unit = {
    logger.info("onConnect: " + JSON.toJSONString(client, false))
  }

  @OnDisconnect
  def onDisconnect(client: SocketIOClient): Unit = {
    logger.info("onDisconnect: " + JSON.toJSONString(client, false))
  }

  override def onApplicationEvent(event: ApplicationReadyEvent): Unit = {
    if (!STARTED) {
      SOCKET_IO_SERVER.start()
      logger.info(s"SOCKET_IO_SERVER STARTED ON $PORT")
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run(): Unit = {
          SOCKET_IO_SERVER.stop()
          logger.info("SOCKET_IO_SERVER STOP!")
        }
      })
      STARTED = true
    }
  }
}
