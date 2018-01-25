package com.arkr.provider.apple

import akka.actor.ActorSystem
import com.arkr.boot.config.sys.{SysConfig, SysConfigLoader}
import com.arkr.provider.apple.actor.Start
import com.arkr.provider.apple.actor.wc.MasterActor
import com.arkr.provider.apple.akkaext.SpringExt
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.{Banner, SpringApplication}
import org.springframework.context.ApplicationContext

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */

@SpringBootApplication
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration]))
class Application extends InitializingBean {
  @Autowired var actorSystem: ActorSystem = _
  @Autowired var applicationContext: ApplicationContext = _

  override def afterPropertiesSet(): Unit = {
    actorSystem.actorOf(SpringExt.apply(actorSystem)(applicationContext)
      .props(classOf[MasterActor]), "masterActor-name") ! Start("classpath:config/application.properties")
  }
}

object Application extends App {
  SysConfigLoader.apply(Array[String]("classpath:config/res.properties"), "apple")

  val application = new SpringApplication(classOf[Application])
  application.setBannerMode(Banner.Mode.OFF)
  application.setWebEnvironment(false)
  application.run(args: _*)
  LoggerFactory.getLogger(getClass)
    .info("Service Apple Started. " + SysConfig.getOrElse("who", "undefine") + " " + SysConfig.getString("you"))
}