package com.arkr.hekr.exception

/**
  * Created by hztanhuayou on 2017/8/19
  */
class ArkrRuntimeException extends RuntimeException {
  var code: Int = 1001
  var message: String = getMessage
}
