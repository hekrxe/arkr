package com.arkr.boot.config.sys

/**
  * @author hztanhuayou
  * @date 2017/12/5
  */

object Env {

  private[config] sealed abstract class SysEnv

  object Online extends SysEnv

  object Release extends SysEnv

  object Testing extends SysEnv

  object Dev extends SysEnv

  object None extends SysEnv


  private[config] object SysEnvToolkit {

    def bingo(env: String): SysEnv = {
      env match {
        case "online" => Online
        case "release" => Release
        case "testing" => Testing
        case "dev" => Dev
        case _ => None
      }
    }
  }

}
