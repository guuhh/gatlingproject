
import objects._
import scala.concurrent.duration._
import io.gatling.core.Predef._


class SearchAndRegister extends Simulation {

  val protocolConfig = new ProtocolConf();


  val scnRegister =
         scenario("Register new Computer")
            .exec(
               MainMenu.mainmenu,
               AccessNewComputer.newcomputer,
               RegisterNewComputer.registernewcomputer,
               SearchRegisteredComputer.searchregisteredcomputer
            )

  val scnDelete =
         scenario("Searcher registered Computer")
            .exec(
               MainMenu.mainmenu,
               AccessFirstComputer.accessfirstcomputer,
               DeleteFirstComputer.deletefirstcomputer
            )


          setUp(
           scnRegister
                .inject(
                    nothingFor(5),
                    atOnceUsers(10),
                    rampUsersPerSec(10) to 15 during (5 minutes) randomized
                   ),
           scnDelete
                .inject(
                   atOnceUsers(10),
                   constantUsersPerSec(5) during (300 seconds)
                       )
                )
           .protocols(protocolConfig.getHttpProtocol())
                  .assertions(
                      global.responseTime.max.lt(60000),
                      global.successfulRequests.percent.gt(90),
                      global.responseTime.mean.between(0,3000,true)
                       )


//          setUp(
//            scnRegister
//              .inject(
//                nothingFor(5),
//                atOnceUsers(100),
//                rampUsersPerSec(100) to 150 during (10 minutes)
//                ),
//            scnDelete
//              .inject(
//                atOnceUsers(100),
//                rampUsers(1000) over (600 seconds))
//              )
//            .protocols(protocolConfig.getHttpProtocol())
//                  .assertions(
//                      global.responseTime.max.lt(50),
//                      global.successfulRequests.percent.gt(95)
//                    )

}