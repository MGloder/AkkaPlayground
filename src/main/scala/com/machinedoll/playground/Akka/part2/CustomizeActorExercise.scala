package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorSystem, Props}
import com.machinedoll.playground.Akka.part2.CustomizeActorExercise.Counter.{Decrement, Increment, Print}

object CustomizeActorExercise extends App {
  val system = ActorSystem("CustomizeActorSystem")

  object Counter {

    case object Increment

    case object Decrement

    case object Print

  }

  class Counter extends Actor {

    import Counter._

    override def receive: Receive = incrementReceive(0)

    def incrementReceive(current: Int): Receive = {
      case Increment => context.become(incrementReceive(current + 1))
      case Decrement => context.become(decrementReceive(current - 1))
      case Print => println(s"[${context.self.path}] current count: ${current}")
    }

    def decrementReceive(current: Int): Receive = {
      case Increment => context.become(incrementReceive(current + 1))
      case Decrement => context.become(decrementReceive(current - 1))
      case Print => println(s"[${context.self.path}] current count: ${current}")
    }
  }

  val alice = system.actorOf(Props[Counter], "aliceCounter")

  alice ! Increment
  alice ! Decrement
  alice ! Decrement
  alice ! Decrement
  alice ! Print

}
