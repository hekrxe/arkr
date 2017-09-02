package com.arkr.hekr.sys.config

/**
  * Created by hztanhuayou on 2017/9/2
  */
private[config] class PropertiesConfigLoader(val profile: String) extends ConfigLoader {

  override def getOrder: Int = Int.MaxValue

  override def get(key: String): String = {
    ""
  }
}
