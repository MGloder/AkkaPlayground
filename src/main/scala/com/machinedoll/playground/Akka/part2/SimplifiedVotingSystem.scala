package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object SimplifiedVotingSystem extends App {
  val system = ActorSystem("SimplifiedVotingSystem")

  case class Vote(candidate: String)

  case object VoteStatueRequest

  case class VoteStatusReply(candidate: Option[String])

  class Citizen extends Actor {
    override def receive: Receive = ???
  }

  case class AggregateVotes(citizen: Set[ActorRef])

  class VoteAggregator extends Actor {
    override def receive: Receive = ???
  }

  val alice = system.actorOf(Props[Citizen], "alice")
  val bob = system.actorOf(Props[Citizen], "bob")
  val mark = system.actorOf(Props[Citizen], "mark")

  alice ! Vote("Mask")
  bob ! Vote("Daniel")
  mark ! Vote("Mask")

  val voteAggregator = system.actorOf(Props[VoteAggregator], "VotingAgg")
  voteAggregator ! AggregateVotes(Set(alice, bob, mark))

}
