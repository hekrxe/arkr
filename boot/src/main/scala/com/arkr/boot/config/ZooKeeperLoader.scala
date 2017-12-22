package com.arkr.boot.config

import com.arkr.common.zk.ZKClient

/**
  * @author hztanhuayou
  * @date 2017/12/21
  */
private[config] class ZooKeeperLoader(val zkPath: String) extends ConfigLoader {
  /**
    * 优先级选取依据
    *
    * @return
    */
  override def getOrder: Int = Int.MinValue

  /**
    * 获取key对应的配置值
    *
    * @param key 例如来自*.properties文件
    * @return 该key对应的值
    */
  override def get(key: String): String = {
    ZKClient.apply.exist("aaaaaaaaaaaaaaaaaaaaaaa")
    ""
  }

  /**
    * callback 重新加载
    */
  override def reload(): Unit = {

  }
}
