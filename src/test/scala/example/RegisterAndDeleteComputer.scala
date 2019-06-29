
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
         scenario("Delete first Computer")
            .exec(
               MainMenu.mainmenu,
              AccessComputerInPage.accesscomputer,
              DeleteComputerSelectedInPage.deletecomputer
            )


          setUp(
           scnRegister
                .inject(
                    nothingFor(5),
                    atOnceUsers(20),
                    rampUsersPerSec(1) to 10 during (5 minutes) randomized
                   ),
           scnDelete
                .inject(
                   atOnceUsers(10),
                   constantUsersPerSec(10) during (300 seconds)
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
//                      global.responseTime.max.lt(6000),
//                      global.successfulRequests.percent.gt(90)
//                    )

}