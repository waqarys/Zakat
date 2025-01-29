package backend

import cats.effect._
import org.http4s.server.blaze._
import org.http4s.implicits._
import cats.effect.unsafe.implicits.global

object BackendApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val zakatService = ZakatCalculatorAlg.impl[IO]
    val zakatRoutes = new ZakatRoutes[IO](zakatService) // Instantiate ZakatRoutes

    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(zakatRoutes.routes.orNotFound) // Call instance method
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
