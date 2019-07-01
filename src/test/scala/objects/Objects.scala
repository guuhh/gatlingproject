package objects
import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, _}

import scala.util.Random

  object MainMenu {

    private val protocolconfig = new ProtocolConf()

    val feedIndexComputer = Iterator.continually(
      Map("indexComputer" -> ("#main > table > tbody > tr:nth-child(" + (Random.nextInt(10) + 1) + ") > td:nth-child(1) > a"))
      )

    val mainmenu = feed(feedIndexComputer)
      .exec(
        http("Access Main Page")
          .get("/")
          .headers(protocolconfig.getHeaderGet())
          .check(status.is(200))
          .check(
            css("${indexComputer}", "href")
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

      val accessnewcomputer =
        exec(
          http("Access New computer")
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

       val feederRamdonValue = Iterator.continually(Map("randomValue" -> Random.alphanumeric.take(10).mkString))

       val registernewcomputer = feed(feedercomputername).feed(feederRamdonValue)
         .exec(
           http("Register new computer")
              .post("/computers")
              .headers(protocolconfig.getHeaderPost())
              .formParam("name", "${computerNameBrand} ${computerNameVersion} ${randomValue}")
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
         .pause(3)
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

      object  SearchAComputer {

        private val protocolconfig = new ProtocolConf()

        val feedercomputername = jsonFile("src/test/scala/feeders/computerNames.json").random

        val searchracomputer = feed(feedercomputername).pause(1)
          .exec(
            http("Search a computer")
              .get("/computers")
              .queryParam("f", "${computerBrand}")
              .headers(protocolconfig.getHeaderGet())
              .check(status.is(200))
              .check(
                regex("""<a href="/computers/([0-9]*)""")
                  .count
                  .saveAs("ComputerListIndexSearched"))
              .check(
                css("#main > table > tbody > tr:nth-child(${ComputerListIndexSearched}) > td:nth-child(1) > a","ref")
                 .saveAs("computerURL"))
              .notSilent)
            .pause(3)
      }


     object  AccessAComputerSearched {

        private val protocolconfig = new ProtocolConf()

                val accessacomputersearched =
                    exec(
                      http("Access a Computer searched")
                        .get("${computerURL}")
                        .headers(protocolconfig.getHeaderGet())
                        .check(status.is(200), substring("Edit computer"))
                        .notSilent)
                    .pause(3)
}

     object  DeleteAComputerSearched {

          private val protocolconfig = new ProtocolConf()

                  val deleteacomputersearched =
                      exec(
                        http("Delete a Computer searched")
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
