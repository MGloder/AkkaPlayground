package com.machinedoll.playground.Akka

import akka.actor.ActorSystem

object BasicAkka {
  def main(args: Array[String]): Unit = {
    val exampleAkkaSystem = ActorSystem("HelloAkka")
    println(exampleAkkaSystem)
  }
}
