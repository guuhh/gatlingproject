package example
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class MercuryTourSpec extends Simulation {

  val httpProtocol = http
    .baseURL("http://newtours.demoaut.com")
    .inferHtmlResources()
    .acceptHeader("image/webp,image/apng,image/*,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,en-CA;q=0.8,fr-CA;q=0.7,fr;q=0.6,pt;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Proxy-Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map("Proxy-Connection" -> "keep-alive")

  val scn = scenario("RecordedSimulation1")
    .exec(http(" access home page")
        .get("/")
        .headers(headers_0)
        .check(status.is(200))
        .resources(http("logotype")
          .get("/images/nav/logo.gif")
          .headers(headers_1)
          .check(status.is(200)),
          http("Featured Destination Banner")
            .get("/images/featured_destination.gif")
            .headers(headers_1)
            .check(status.is(200))))
    .pause(1)
    .exec(http("access vacation destination")
      .get("/mercuryunderconst.php")
      .headers(headers_0)
      .resources(http("back button")
        .get("/images/forms/home.gif")
        .headers(headers_1)
        .check(status.is(200)),
        http("nav title")
          .get("/images/masts/mast_construction.gif")
          .headers(headers_1)
          .check(status.is(200))))
    .pause(1)
    .exec(http("Back to home")
      .get("/mercurywelcome.php")
      .headers(headers_0)
      .resources(http("Featured Destination Banner")
        .get("/images/featured_destination.gif")
        .headers(headers_1)
        .check(status.is(200))))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

}