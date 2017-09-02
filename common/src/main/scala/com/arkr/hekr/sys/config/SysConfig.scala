package com.arkr.hekr.sys.config

import scala.collection.mutable

/**
  * Created by hztanhuayou on 2017/8/30
  */
object SysConfig {
  private val impl = SysConfigImpl

  def getOrElse(key: String, value: String): String = impl.getOrElse(key, value)

}

private class SysConfigImpl private {
  private implicit val ordering: Ordering[ConfigLoader] = new Ordering[ConfigLoader] {
    override def compare(x: ConfigLoader, y: ConfigLoader): Int = x.getOrder - y.getOrder
  }
  private val sources = mutable.TreeSet[ConfigLoader]()

  def load(source: ConfigLoader): Unit = {
    sources.synchronized {
      sources += source
    }
  }

  def getOrElse(key: String, value: String): String = {
    var ret = value
    var find = false
    val iterator = sources.iterator
    while (!find && iterator.hasNext) {
      val loader = iterator.next()
      val v = loader.get(key)
      if (null != v) {
        ret = v
        find = true
      }
    }
    ret
  }
}

private[sys] object SysConfigImpl {
  val impl = new SysConfigImpl

  def load(source: ConfigLoader): Unit = {
    if (null != source)
      impl.load(source)
  }

  def load(sources: List[ConfigLoader]): Unit = {
    if (null != sources) {
      sources foreach load
    }
  }

  def getOrElse(key: String, value: String): String = impl.getOrElse(key, value)
}