package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorSystem, Props}

object ActorIntro extends App {
  /*
    word count actor
   */
  // #1
  val actorSystem = ActorSystem("firstActorSystem")

  // #2
  class WordCountActor extends Actor {
    var totalWord = 0

    override def receive: PartialFunction[Any, Unit] = {
      case message: String => {
        println(s"I have received a message ${message}")
        totalWord += message.split(" ").length
        println(totalWord)
      }
      case msg => println(s"I cannot undersrtand ${msg.toString}")
    }

  }

  // #3
  val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCounter")
  val anotherWordCounter = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")

  // #4
  wordCounter ! "I am learning Akka and it's pretty demn cool!"
  anotherWordCounter ! "Another Message: I am learning Akka and it's pretty demn cool!"

  //#5

  object Person {
    def props(name: String) = Props(new Person(name))
  }

  class Person(val name: String) extends Actor {
    override def receive: Receive = {
      case "hi" => println(s"Hi, My name is $name")
    }
  }

  val aPersonActor = actorSystem.actorOf(Person.props("Alice"))

  aPersonActor ! "hi"
  aPersonActor ! "example"


}
