
import objects._
import scala.concurrent.duration._
import io.gatling.core.Predef._


class SimulationRegisterAndDeleteComputer extends Simulation {

  val protocolConfig = new ProtocolConf();


  val scnRegister =
         scenario("Register a new Computer")
            .exec(
              CleanAllCaches.cleanAll,
               MainMenu.mainmenu,
               AccessNewComputer.accessnewcomputer,
               RegisterNewComputer.registernewcomputer,
               SearchRegisteredComputer.searchregisteredcomputer
            )

  val scnDelete =
         scenario("Delete a Computer")
            .exec(
              CleanAllCaches.cleanAll,
              MainMenu.mainmenu,
              SearchAComputer.searchracomputer,
              AccessAComputerSearched.accessacomputersearched,
              DeleteAComputerSearched.deleteacomputersearched
            )


          setUp(
           scnRegister
                .inject(
                    atOnceUsers(20),
                    rampUsersPerSec(5) to 10 during (5 minutes) randomized //5min
                   ),
           scnDelete
                .inject(
                  nothingFor(5),
                  atOnceUsers(10),
                  constantUsersPerSec(5) during (300 seconds) //300 seconds
                       ).throttle(
                               reachRps(100) in (20 seconds),
                               holdFor(1 minute),
                               jumpToRps(50),
                               holdFor( 4 minute)
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
//                atOnceUsers(30),
//                rampUsersPerSec(5) to 10 during (10 minutes)
//                ),
//            scnDelete
//              .inject(
//                atOnceUsers(30),
//                rampUsers(300) over (600 seconds))
//              )
//            .protocols(protocolConfig.getHttpProtocol())
//                  .assertions(
//                     global.responseTime.max.lt(60000),
//                      global.successfulRequests.percent.gt(90),
//                      global.responseTime.mean.between(0,3000,true)
//                    )

}