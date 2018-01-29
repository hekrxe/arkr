package com.arkr.link

import com.alibaba.fastjson.JSON
import com.corundumstudio.socketio._

/**
  * @author hztanhuayou
  * @date 2018/1/29
  */
object WsServer extends App {
  val configuration = new Configuration
  configuration.setHostname("127.0.0.1")
  configuration.setPort(12345)
  configuration.setTransports(Transport.WEBSOCKET)

  val socketConfig = new SocketConfig()
  socketConfig.setReuseAddress(true)
  configuration.setSocketConfig(socketConfig)

  val server = new SocketIOServer(configuration)

  server.addConnectListener((client: SocketIOClient) => {
    println(JSON.toJSONString(client, false))
    client.sendEvent("hello", "word")
  })

  server.addEventListener("event", classOf[String], (client: SocketIOClient, data: String, ackSender: AckRequest) => {
    println(JSON.toJSONString(client, false))
    println(JSON.toJSONString(data, false))
    println(ackSender)
  })

  server.start()

  Thread.sleep(Integer.MAX_VALUE)

  server.stop()
}
