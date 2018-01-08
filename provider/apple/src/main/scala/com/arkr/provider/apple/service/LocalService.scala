package com.arkr.provider.apple.service

import org.springframework.stereotype.Service

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
@Service("localService")
class LocalService {
  def echo(content: String) = content
}
