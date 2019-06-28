package objects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ProtocolConf {

     private object Protocol {

             val httpProtocol =
               http
                .baseURL("http://computer-database.gatling.io")
                .inferHtmlResources()
                .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .acceptEncodingHeader("gzip, deflate")
                .acceptLanguageHeader("en-US,en;q=0.9,en-CA;q=0.8,fr-CA;q=0.7,fr;q=0.6,pt;q=0.5")
                .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
        }

     private object HeadersGet{

              val headers_0 = Map("Proxy-Connection" -> "keep-alive")
      }

      private object HeadersPost{

              val headers_4 = Map(
                "Origin" -> "http://computer-database.gatling.io",
                "Proxy-Connection" -> "keep-alive")
      }

      def getHttpProtocol(): HttpProtocolBuilder = {
            return Protocol.httpProtocol;
      }

      def getHeaderGet(): Map[String, String] = {
            return HeadersGet.headers_0;
       }

      def getHeaderPost(): Map[String, String] = {
          return HeadersPost.headers_4;
      }
}
