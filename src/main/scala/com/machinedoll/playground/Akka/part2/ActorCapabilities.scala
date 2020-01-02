package com.machinedoll.playground.Akka.part2

import java.util.Date

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilities extends App {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case "Hi" => context.sender() ! "Hello there!"
      case message: String => println(s"[$self]I have received simple message: $message")
      case number: Int => println(s"I have received a number ${number.toString}")
      case anotherMsg: SpecialMessage => println(s"I have received something special ${anotherMsg.msg}")
      case averySpecialMsg: VerySpecialMessage => println(s"I have received a very special msg ${averySpecialMsg.msg}")
      case date: NotSerializableMsg => println(s"A date ${date.date.getTime}")
      case SayHiTo(ref) => ref ! "Hi"
    }
  }

  val system = ActorSystem("ActorCapabilities")

  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  simpleActor ! "message"
  simpleActor ! 42

  case class SpecialMessage(msg: String)

  case class VerySpecialMessage(msg: String, specialMessage: SpecialMessage)

  simpleActor ! new SpecialMessage("A Example Message")

  simpleActor ! new VerySpecialMessage("very", new SpecialMessage("msg"))

  case class NotSerializableMsg(date: Date)

  simpleActor ! new NotSerializableMsg(new Date(System.currentTimeMillis()))

  case class SayHiTo(ref: ActorRef)

  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob = system.actorOf(Props[SimpleActor], "bob")

  alice ! new SayHiTo(bob)

  alice ! "Hi"
}

