
import objects._
import scala.concurrent.duration._
import io.gatling.core.Predef._


class SearchAndRegister extends Simulation {

  val protocolconfig = new ProtocolConf();


  val scnRegister = scenario("Register new Computer")
    .exec(
    MainMenu.mainmenu,
    NewComputer.newcomputer,
    RegisterNewComputer.registernewcomputer
    )

   val scnSearcher = scenario("Searcher registered Computer").exec(MainMenu.mainmenu, SearchRegisteredComputer.searchregisteredcomputer)

  setUp(
    scnRegister.inject(nothingFor(5), atOnceUsers(10),rampUsersPerSec(10) to 20 during (10 minutes) randomized),
    scnSearcher.inject(atOnceUsers(10), constantUsersPerSec(30) during (600 seconds) )
  ).protocols(protocolconfig.getHttpProtocol())
}