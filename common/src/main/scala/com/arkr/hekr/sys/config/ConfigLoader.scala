package com.arkr.hekr.sys.config

/**
  * Created by hztanhuayou on 2017/9/2
  */
private[config] trait ConfigLoader {
  def getOrder: Int

  def get(key: String): String
}
