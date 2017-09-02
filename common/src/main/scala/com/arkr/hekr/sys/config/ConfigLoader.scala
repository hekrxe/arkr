package com.arkr.hekr.sys.config

/**
  * Created by hztanhuayou on 2017/9/2
  */
private[config] trait ConfigLoader {
  def getOrder: Int

  /**
    * 获取 key 对应的值
    *
    * @param key key
    * @return 获取不到时返回 null
    */
  def get(key: String): String
}
