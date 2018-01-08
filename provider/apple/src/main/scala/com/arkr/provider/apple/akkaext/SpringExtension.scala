package com.arkr.provider.apple.akkaext

import akka.actor._


/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
object SpringExtension extends ExtensionId[SpringExt] with ExtensionIdProvider {

  // 这里创建得到一个key 对应于extensions的一个key
  // 这个方法只会被akka使用
  override def createExtension(system: ExtendedActorSystem) = new SpringExt

  // 该方法的核心是通过createExtension 创建 SpringExt
  // 并将创建好的SpringExt保存到ActorSystem里
  override def get(system: ActorSystem): SpringExt = super.get(system)

  override def lookup(): SpringExtension.type = SpringExtension
}