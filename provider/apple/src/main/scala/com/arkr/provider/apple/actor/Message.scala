package com.arkr.provider.apple.actor

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
trait Message

sealed trait Cmd extends Message

case class Start(file: String) extends Cmd

/**
  * @param line 一行文本
  */
case class Line(line: String) extends Cmd

/**
  * 统计结果
  *
  * @param word  单词
  * @param count 出现次数
  */
case class Word(word: String, count: Int) extends Cmd
