package recordComputerDatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SearchAndRegister extends Simulation {

  val httpProtocol = http
    .baseURL("http://computer-database.gatling.io")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,en-CA;q=0.8,fr-CA;q=0.7,fr;q=0.6,pt;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")

  val headers_0 = Map("Proxy-Connection" -> "keep-alive")

  val headers_4 = Map(
    "Origin" -> "http://computer-database.gatling.io",
    "Proxy-Connection" -> "keep-alive")

  val scnRegister = scenario("Register new Computer")
    .exec(http("request_0")
      .get("/")
      .headers(headers_0)
      .check(status.is(200))
      resources http("main page")
      .get("/")
      .headers(headers_0)
      .check(status.is(200))
      .notSilent)

    .pause(5)

    .exec(http("resquest new computer")
      .get("/computers/new")
      .headers(headers_0)
      .check(status.is(200))
      resources http("main page")
      .get("/computers/new")
      .headers(headers_0)
      .check(status.is(200), substring("Add a computer").find.exists)
      .notSilent)

    .pause(3)

    .exec(http("request register  new computer")
      .post("/computers")
      .headers(headers_4)
      .formParam("name", "Lenovo 2000")
      .formParam("introduced", "2009-01-01")
      .formParam("discontinued", "2019-01-01")
      .formParam("company", "36")
      resources http("Response register new computer")
      .get("/computers")
      .headers(headers_0)
      .check(status.is(200), substring("Done! Computer Lenovo 2000 has been created").find.exists)
      .notSilent)

  val scnSearcher = scenario("Searcher registered Computer")
    .exec(http("request_0")
         .get("/")
         .headers(headers_0)
         .check(status.is(200))
         resources http("main page")
           .get("/")
           .headers(headers_0)
           .check(status.is(200))
           .notSilent)
      .pause(5)

    .exec(http("request_1")
      .get("/computers")
      .queryParam("f", "macbook")
      .headers(headers_0)
      .check(status.is(200))
      resources http("main page")
      .get("/computers")
      .queryParam("f", "macbook")
      .headers(headers_0)
      .check(status.is(200), substring("Macbook").count.saveAs("founded")))
    .pause(3)


  setUp(
    scnRegister.inject(nothingFor(5), atOnceUsers(10),rampUsersPerSec(10) to 20 during (10 minutes) randomized),
    scnSearcher.inject(atOnceUsers(10), constantUsersPerSec(30) during (600 seconds) )
  ).protocols(httpProtocol)
}