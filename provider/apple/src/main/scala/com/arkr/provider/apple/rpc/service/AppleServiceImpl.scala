package com.arkr.provider.apple.rpc.service

import com.alibaba.dubbo.config.annotation.Service
import com.arkr.provider.apple.service.LocalService
import com.arkr.service.apple.AppleService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Service(version = "1.1.1")
class AppleServiceImpl extends AppleService {
  private val logger = LoggerFactory.getLogger(getClass)

  @Autowired private var localService: LocalService = _

  /**
    * 一个服务...
    *
    * @param iHaveAPen 一些参数
    * @return some thing
    */
  override def apple(iHaveAPen: String): String = {
    logger.info(s"Receive: $iHaveAPen")
    localService.echo("iHaveAApple")
  }
}
