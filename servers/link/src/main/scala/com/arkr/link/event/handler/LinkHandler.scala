package com.arkr.link.event.handler

import com.alibaba.fastjson.JSON
import com.corundumstudio.socketio.annotation.OnEvent
import com.corundumstudio.socketio.{AckRequest, SocketIOClient}
import com.sun.nio.sctp.MessageInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
  * @author hztanhuayou
  * @date 2018/1/27
  */
@Component("linkHandler")
class LinkHandler {
  private val logger = LoggerFactory.getLogger(getClass)


  @OnEvent(value = "Chat")
  def OnEvent(client: SocketIOClient, request: AckRequest, data: MessageInfo): Unit = {
    logger.info(JSON.toJSONString(client, false))
  }

}
