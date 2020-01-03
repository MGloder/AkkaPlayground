package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorSystem}

object SimplifiedVotingSystem extends App {
  val system = ActorSystem("SimplifiedVotingSystem")

  case class Vote(candidate: String)
  case object VoteStatueRequest
  case class VoteStatusReply(candidate: Option[String])
  class Citizen extends Actor {
    override def receive: Receive = ???
  }

}
