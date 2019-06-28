package objects
import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, _}
import objects.MainMenu.protocolconfig

import scala.util.Random

  object MainMenu {

    private val protocolconfig = new ProtocolConf()

    val mainmenu =
      exec(
        http("Main Page")
          .get("/")
          .headers(protocolconfig.getHeaderGet())
          .check(status.is(200))
          .check(
            css("#main > table > tbody > tr:nth-child(1) > td:nth-child(1) > a", "href")
              .saveAs("computerURL"))
          .notSilent
          .resources(
                http("Response main.css")
                    .get("/assets/stylesheets/main.css")
                    .headers(protocolconfig.getHeaderGet())
                    .check(status.is(200))
                    .notSilent,

                http("Response bootstrap.min.css")
                    .get("/assets/stylesheets/bootstrap.min.css")
                    .headers(protocolconfig.getHeaderGet())
                    .check(status.is(200))
                    .notSilent
                      ))
          .pause(5)
}

    object AccessNewComputer {

      private val protocolconfig = new ProtocolConf()

      val newcomputer =
        exec(
          http("New computer page")
            .get("/computers/new")
            .headers(protocolconfig.getHeaderGet())
            .check(status.is(200))
            .notSilent
          .resources(
             http("Response main.css")
               .get("/assets/stylesheets/main.css")
               .headers(protocolconfig.getHeaderGet())
               .check(status.is(200))
               .notSilent,

             http("Response bootstrap.min.css")
               .get("/assets/stylesheets/bootstrap.min.css")
               .headers(protocolconfig.getHeaderGet())
               .check(status.is(200))
               .notSilent))
        .pause(3)
}

     object RegisterNewComputer {

       private val protocolconfig = new ProtocolConf()

       val feedercomputername = jsonFile("src/test/scala/feeders/computerNames.json").random

       val feeder = Iterator.continually(Map("randomValue" -> Random.alphanumeric.take(10).mkString))

       val registernewcomputer = feed(feedercomputername).feed(feeder)
         .exec(
           http("Register new computer")
              .post("/computers")
              .headers(protocolconfig.getHeaderPost())
              .formParam("name", "${computerName} ${randomValue}")
              .formParam("introduced", "2009-01-01")
              .formParam("discontinued", "2019-01-01")
              .formParam("company", "${companyCode}")
              .check(status.is(200))
              .check(
                regex("""Computer (.*) has""" )
                  .findAll
                  .saveAs("fullComputerName"))
              .notSilent
              )
       }

     object  SearchRegisteredComputer {

      private val protocolconfig = new ProtocolConf()

      val searchregisteredcomputer =
         exec(
            http("Search registered computer")
              .get("/computers")
              .queryParam("f", "${fullComputerName._1}")
              .headers(protocolconfig.getHeaderGet())
              .check(status.is(200), substring("One computer found"))
              .notSilent)
         .pause(3)
      }


     object  AccessFirstComputer {

        private val protocolconfig = new ProtocolConf()

                val accessfirstcomputer =
                    exec(
                      http("Access the first Computer")
                        .get("${computerURL}")
                        .headers(protocolconfig.getHeaderGet())
                        .check(status.is(200), substring("Edit computer"))
                        .notSilent)
                    .pause(3)
}

     object  DeleteFirstComputer {

          private val protocolconfig = new ProtocolConf()

                  val deletefirstcomputer =
                      exec(
                        http("Delete the first Computer")
                          .post("${computerURL}/delete")
                          .headers(protocolconfig.getHeaderPost())
                          .check(status.is(200))
                          .check(
                              regex("""Computer has been deleted""")
                                .findAll
                                .saveAs("Delete message"))
                      .notSilent)
                      .pause(3)
}
