package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.machinedoll.playground.Akka.part2.ChangingActorBehavior.Mom.MomStart

object ChangingActorBehavior extends App {
  val system = ActorSystem("ExampleActorSystem")

  object FussyKid {

    case object Accept

    case object Reject

    val HAPPY = "happy"
    val SAD = "sad"
  }

  class FussyKid extends Actor {

    import FussyKid._
    import Mom._

    var state = HAPPY

    override def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(msg) =>
        if (state == HAPPY) sender() ! Accept else sender() ! Reject
    }
  }

  object Mom {

    case class MomStart(kidRef: ActorRef)

    case class Food(food: String)

    case class Ask(msg: String)

    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocolate"
  }

  class Mom extends Actor {

    import FussyKid._
    import Mom._

    override def receive: Receive = {
      case MomStart(kid) => {
        kid ! Food(VEGETABLE)
        kid ! Ask("do you want to play")
      }
      case Accept => println("Yah, my kid is happy")
      case Reject => println("No, my kid is sad")
    }
  }

  val momActor = system.actorOf(Props[Mom], "momActor")
  val kidActor = system.actorOf(Props[FussyKid], "kidActor")

  momActor ! MomStart(kidActor)

}
