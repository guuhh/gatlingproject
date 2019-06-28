package objects
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

  object MainMenu {

    private val protocolconfig = new ProtocolConf()

    val mainmenu = exec(http("Main Page")
      .get("/")
      .headers(protocolconfig.getHeaderGet())
      .check(status.is(200))
      .resources(http("Main page")
      .get("/")
      .headers(protocolconfig.getHeaderGet())
      .check(status.is(200))
      .notSilent))
      .pause(5)
}

object NewComputer {

  private val protocolconfig = new ProtocolConf()

  val newcomputer = exec(http("New computer page")
    .get("/computers/new")
    .headers(protocolconfig.getHeaderGet())
    .check(status.is(200))
    .resources(http("New computer page")
    .get("/computers/new")
    .headers(protocolconfig.getHeaderGet())
    .check(status.is(200), substring("Add a computer").find.exists)
    .notSilent))
    .pause(3)
}

object RegisterNewComputer {

  private val protocolconfig = new ProtocolConf()

  val feedercomputername = jsonFile("src/test/scala/feeders/computerNames.json").random

  val feeder = Iterator.continually(Map("randomValue" -> (Random.alphanumeric.take(10).mkString)))

  val registernewcomputer = feed(feedercomputername).feed(feeder)
    .exec(http("Register new computer")
    .post("/computers")
    .headers(protocolconfig.getHeaderPost())
    .formParam("name", "${computerName} ${randomValue}")
    .formParam("introduced", "2009-01-01")
    .formParam("discontinued", "2019-01-01")
    .formParam("company", "${companyCode}")
    .check(status.is(200))
    .resources(http("Register new computer")
    .post("/computers")
    .headers(protocolconfig.getHeaderPost())
    .check(status.is(303), substring("Done! Computer ${computerName} ${randomValue} has been created").find.exists)
    .notSilent))
}

object  SearchRegisteredComputer {

  private val protocolconfig = new ProtocolConf()

  val searchregisteredcomputer = exec(http("Search registered computer")
    .get("/computers")
    .queryParam("f", "macbook")
    .headers(protocolconfig.getHeaderGet())
    .check(status.is(200))
    .resources(http("Search registered computer")
    .get("/computers")
    .queryParam("f", "macbook")
    .headers(protocolconfig.getHeaderGet())
    .check(status.is(200), substring("Macbook").count.saveAs("founded"))
    .notSilent))
    .pause(3)
}
