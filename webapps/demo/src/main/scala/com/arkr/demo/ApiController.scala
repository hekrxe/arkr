package com.arkr.demo

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

/**
  * @author hztanhuayou
  * @date 2018/2/2
  */
@RestController
@RequestMapping(value = Array("/"), produces = Array(MediaType.APPLICATION_JSON_UTF8_VALUE))
class ApiController {
  private val user1 = new IUser
  user1.id = 1
  user1.companyId = 1
  user1.name = "thy"
  user1.phone = "183333333"
  user1.email = "111@111.com"
  user1.age = 17


  private val user2 = new IUser
  user2.id = 2
  user2.companyId = 2
  user2.name = "lm"
  user2.phone = "183aaa333333"
  user2.email = "111@112211.com"
  user2.age = 17


  private val user3 = new IUser
  user3.id = 3
  user3.companyId = 1
  user3.name = "thydda"
  user3.phone = "183332123333"
  user3.email = "1aaa11@111.com"
  user3.age = 17


  private val user4 = new IUser
  user4.id = 4
  user4.companyId = 1
  user4.name = "thasdy"
  user4.phone = "1833as33333"
  user4.email = "111@11551.com"
  user4.age = 17


  private val userList = List(user1, user2, user3, user4).asJava


  @RequestMapping(value = Array("/users"), method = Array(RequestMethod.GET))
  def sessionList(): java.util.List[IUser] = userList

}

class IUser {
  @BeanProperty var id: Int = _
  @BeanProperty var companyId: Int = _
  @BeanProperty var name: String = _
  @BeanProperty var phone: String = _
  @BeanProperty var email: String = _
  @BeanProperty var age: Int = _
}

class Company {
  @BeanProperty var id: Int = _
  @BeanProperty var name: String = _
  @BeanProperty var description: String = _

}
