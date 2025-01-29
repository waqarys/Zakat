import cats.effect.*
import cats.effect.unsafe.implicits.global
import org.http4s.implicits.*
import org.http4s.server.blaze.*

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
