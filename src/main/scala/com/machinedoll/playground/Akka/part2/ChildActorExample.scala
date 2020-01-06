package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.machinedoll.playground.Akka.part2.ChildActorExample.Parent.{CreateChild, TellChild}

object ChildActorExample extends App {
  val system = ActorSystem("SimpleChildActorSystem")

  object Parent {

    case class CreateChild(name: String)

    case class TellChild(message: String)

  }

  class Parent extends Actor {
    import Parent._

    override def receive: Receive = {
      case CreateChild(name) =>
        println(s"[${self.path}] creating child")
        val childRef = context.actorOf(Props[Child], name)
        context.become(withChild(childRef))
    }

    def withChild(childRef: ActorRef): Receive = {
      case TellChild(message) => {
        if (childRef != null) childRef forward message
      }
    }
  }

  class Child extends Actor {
    override def receive: Receive = {
      case message => println(s"[${self.path}] I got ${message}")
    }
  }

  val parent = system.actorOf(Props[Parent], "parent")
  parent ! CreateChild("Alice")
  parent ! TellChild("Hi Kid")

}
