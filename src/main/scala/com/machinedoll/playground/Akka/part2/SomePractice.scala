package com.machinedoll.playground.Akka.part2

import akka.actor.Status.{Failure, Success}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable

object SomePractice extends App {
  val system = ActorSystem("SimpleActorSystem")

  // Exe 1

  case class IncrementAction(value: Int)

  case class DecrementAction(v: Int)


  class CounterActor extends Actor {
    var value: Int = 0

    override def receive: Receive = {
      case IncrementAction(v) => value += v
      case DecrementAction(v) => value -= v
      case "Print" => println(s"current value is $value")
    }
  }

  val simpleCounter = system.actorOf(Props[CounterActor], "SimpleCounter")

  val startTime = System.currentTimeMillis()
  println(s"Start Time ${startTime}")
  (1 to 10000).foreach(i => {
    simpleCounter ! IncrementAction(i)
  })

  (1 to 10000).foreach(i => {
    simpleCounter ! DecrementAction(i)
  })

  simpleCounter ! "Print"
  val endTime = System.currentTimeMillis()
  println(s"Interval ${endTime - startTime} ms")

  var x = 0
  val second = System.currentTimeMillis()
  (1 to 10000).foreach(i => x += i)
  val endSecond = System.currentTimeMillis()
  println(s"Interval2: ${endSecond - second}")


  // Exe 2

  class BankAccountActor extends Actor {
    var account: mutable.HashMap[String, Double] = mutable.HashMap("alice" -> 0)

    override def receive: Receive = {
      case Success(msg) => println(s"${self}'s action success with msg ${msg}")
      case Failure(e) => println(s"${self}'s action failed with msg ${e}")
      case Deposit(user, amount) => {
        println(s"deposit ammount: ${amount}")
        account("alice") += amount
        self ! Success("XXXX")
      }
      case Withdraw(user, amount) => {
        println(s"trying to withdraw amount ${amount}")
        if (account("alice") > amount) {
          account("alice") -= amount
          self ! Success("YYYY")
        } else {
          self ! Failure(new Exception("insufficient amount of balance"))

        }
      }
      case Statement(user) => println(s"current balance for user: ${user} is ${account(user)}")
    }
  }

  case class Deposit(user: ActorRef, money: Double)

  case class Withdraw(user: ActorRef, money: Double)

  case class Statement(user: String)

  val alice = system.actorOf(Props[BankAccountActor], "aliceBankActor")

  //  alice ! Deposit(alice, 20)
  //  alice ! Statement("alice")
  //  alice ! Withdraw(alice, 15)
  //  alice ! Withdraw(alice, 10)
  //  alice ! Statement("alice")

}
