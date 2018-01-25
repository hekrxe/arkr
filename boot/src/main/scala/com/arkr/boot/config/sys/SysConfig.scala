package com.arkr.boot.config.sys

import java.util.concurrent.atomic.AtomicBoolean

import scala.collection.mutable

/**
  * 一个简单的SysConfig实现
  *
  * @author hztanhuayou
  * @date 2017/12/5
  */
object SysConfig {

  def isDev: Boolean = getSysEnv.equals(Env.Dev)

  def isTesting: Boolean = getSysEnv.equals(Env.Testing)

  def isRelease: Boolean = getSysEnv.equals(Env.Release)

  def isOnline: Boolean = getSysEnv.equals(Env.Online)

  def getSysEnv: Env.SysEnv = {
    val env = getOrElse("env", "")
    Env.SysEnvToolkit.bingo(env)
  }

  def getDouble(key: String): java.lang.Double = getDouble(key, null)

  def getDouble(key: String, default: java.lang.Double): java.lang.Double = {
    val value = getString(key)
    if (null != value) {
      value.toDouble
    } else {
      default
    }
  }

  def getBoolean(key: String): java.lang.Boolean = getBoolean(key, null)

  def getBoolean(key: String, default: java.lang.Boolean): java.lang.Boolean = {
    val value = getString(key)
    if (null != value) {
      value.toBoolean
    } else {
      default
    }
  }

  def getLong(key: String): java.lang.Long = getLong(key, null)

  def getLong(key: String, default: java.lang.Long): java.lang.Long = {
    val value = getString(key)
    if (null != value) {
      value.toLong
    } else {
      default
    }
  }

  def getInteger(key: String): Integer = getInteger(key, null)

  def getInteger(key: String, default: Integer): Integer = {
    val value = getString(key)
    if (null != value) {
      value.toInt
    } else {
      default
    }
  }

  def getString(key: String): String = getOrElse(key, null)

  def getOrElse(key: String, value: String): String = impl.getOrElse(key, value)

  private val impl = SysConfigImpl
}

class SysConfigImpl private {

  private implicit val ordering: Ordering[ConfigLoader] = (x: ConfigLoader, y: ConfigLoader) => x.getOrder - y.getOrder

  private val sources = mutable.TreeSet[ConfigLoader]()

  private val INITIALIZED = new AtomicBoolean(false)

  def load(source: List[ConfigLoader]): Unit = {
    if (!INITIALIZED.get()) {
      sources ++= source
      setReloadTimer()
      INITIALIZED.set(true)
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


  private def setReloadTimer(): Unit = {

  }
}

private[config] object SysConfigImpl {
  val impl = new SysConfigImpl()

  def load(source: List[ConfigLoader]): Unit = {
    if (null != source)
      impl.load(source)
  }

  def getOrElse(key: String, value: String): String = impl.getOrElse(key, value)
}