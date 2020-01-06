package com.machinedoll.playground.Akka.part2

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorChildExercise extends App {

  // distributed word counting

  val system = ActorSystem("ActorChildExample")

  object WorkCounterMaster {

    case class Initialize(nChildren: Int)

    case class WordCountTask(text: String)

    case class WordCountReply(count: Int)

    case object ReadyStatus

  }

  object WordCounterWorker {
    case class WorkerTaskCount(worker: ActorRef, target: String)
  }

  class WordCounterMaster extends Actor {

    import WorkCounterMaster._
    import WordCounterWorker._

    var availableWorkerSet: Set[ActorRef] = Set()
    var count = 0

    override def receive: Receive = initialize

    def workerController(str: String): Receive = {
      case WordCountTask(text: String) => {
        // 1 split text by \\n
        // agg result from worker
        sender() ! WorkerTaskCount(availableWorkerSet.head, text)
      }

      case WordCountReply => println(s"word count: ${count}")
    }

    def initialize: Receive = {
      case Initialize(num) => (0 to num).foreach(n => {
        availableWorkerSet + context.actorOf(Props[WordCounterWorker], s"worker-${n}")
      })
        context.become(workerController("Initialized"))
    }


    //    def withWorker(worker: ActorRef): Unit = {

    //    }

    def WorkerController(status: String): Receive = {
      case "Initialized" => println("Word counter worker initialized and ready to process task")
      case WordCountTask(text) =>
    }


  }

  class WordCounterWorker extends Actor {
    import WordCounterWorker._
    import WorkCounterMaster._
    override def receive: Receive = {
      case WorkerTaskCount(ref: ActorRef, target: String) => {
        ???
//        val count = target.split(" ").map(w => (w, 1)).reduce()
//        sender() ! WordCountReply(count)
      }
    }
  }

  val wordCounterMaster = system.actorOf(Props[WordCounterMaster], "wordCounterMaster")
  /*
    create word counter master
    send init 10 to word counter masker
    send "test text" to word counter masker
      wcm will send a word count task to one of the children
        child replies with a word count reply to the master
      master replies with 3 to the sender
    requester -> wcm -> wcw
            r <- wcm <-

    Round Robin logic
      1,2 ,3 ,4, 5, 6, 7 tasks
   */
  //  wordCounterMaster ! WordCountTask("Hello Akka A")
}
