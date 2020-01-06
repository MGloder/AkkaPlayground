package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.Logging

object ActorLogging extends App {

  class AkkaSimpleLoggger extends Actor {
    val logger = Logging(context.system, this)

    override def receive: Receive = {
      case message: String => logger.info("something to log")
    }
  }

  val system = ActorSystem("akka-log")

  val logActor = system.actorOf(Props[AkkaSimpleLoggger], "logger")

  logActor ! "Something"


  class ActorLoggingExample extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info("Something to log with Actor Logging")
    }
  }

  val actorImplicitLoger = system.actorOf(Props[ActorLoggingExample], "actorLoggingExample")
  actorImplicitLoger ! "something to say"
}
