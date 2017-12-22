package com.arkr.common.message

import scala.beans.BeanProperty

/**
  * Created by hztanhuayou on 2017/8/19
  */
trait MSG extends Serializable

class HttpResponse extends MSG {
  @BeanProperty var code: Int = 200
  @BeanProperty var result: Object = _
  @BeanProperty var message: String = _
  @BeanProperty var time: Long = System.currentTimeMillis()

  private def aa() {

  }
}
