package com.arkr.common.exception

/**
  * Created by hztanhuayou on 2017/8/19
  */
class ArkrRuntimeException extends RuntimeException {
  var code: Int = 1001
  var message: String = getMessage
}

object ArkrRuntimeException {
  def forMessage(code: Int, msg: String): ArkrRuntimeException = {
    val exception = new ArkrRuntimeException
    exception.code = code
    exception.message = msg
    exception
  }
}
