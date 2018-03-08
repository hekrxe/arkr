package com.arkr.boot.config.akka.actor

/**
  * @author hztanhuayou
  * @date 2018/1/8
  */
private[akka] trait Message

private[akka] sealed trait Cmd extends Message

private[akka] case class Start(file: String) extends Cmd

/**
  * @param line 一行文本
  */
private[akka] case class Line(line: String) extends Cmd

/**
  * 统计结果
  *
  * @param word  单词
  * @param count 出现次数
  */
private[akka] case class Word(word: String, count: Int) extends Cmd

private[akka] case class Done() extends Cmd
