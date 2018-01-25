package com.arkr.boot.config.sys

/**
  * @author hztanhuayou
  * @date 2017/12/5
  */
private[config] trait ConfigLoader {
  /**
    * 优先级选取依据
    *
    * @return
    */
  def getOrder: Int

  /**
    * 获取key对应的配置值
    *
    * @param key 例如来自*.properties文件
    * @return 该key对应的值
    */
  def get(key: String): String

  /**
    * callback 重新加载
    */
  def reload()
}
