package simulations
import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, _}

import scala.concurrent.duration._
import scala.util.Random

class SimpleSimulation extends Simulation{

  val selectFirstComputer = scenario("Visit main page")
      .exec(
        http("main page")
          .get("/")
          .check(
             regex("""<a href=\"/computers/(\d+)\">""")
               .count
               .transform(count => Random.nextInt(count) + 1)
               .gt(0)
               .saveAs("ComputerIndexList")
          )
          .check(
            css("#main > table > tbody > tr:nth-child(${ComputerIndexList}) > td:nth-child(1) > a","href")
              .saveAs("computerURL")
          )
      )
      .exec(
        http("Visit computer page")
          .get("${computerURL}")
      )

  setUp(selectFirstComputer.inject(
    atOnceUsers(20),
    rampUsersPerSec(5) to 10 during (5 minutes)
  ))
  .assertions(
    global.responseTime.max.lt(60000),
    global.successfulRequests.percent.gt(90),
    global.responseTime.mean.between(0,3000,true)
  )
  .protocols(
      http.baseUrl(
        "http://computer-database.gatling.io"
      )
    )
}
