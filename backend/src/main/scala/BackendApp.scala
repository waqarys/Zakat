import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.staticcontent.ResourceServiceBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.StaticFile
import com.comcast.ip4s._
import cats.syntax.semigroupk._  // Import to get the `<+>` operator

object BackendApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    // Build static routes from the "public" directory (i.e. backend/src/main/resources/public)
    val staticRoutes: HttpRoutes[IO] = ResourceServiceBuilder[IO]("public").toRoutes

    // Define a route that serves index.html on GET requests to the root path.
    val indexRoute: HttpRoutes[IO] = HttpRoutes.of[IO] {
      case req @ GET -> Root =>
        StaticFile
          .fromResource("public/index.html", Some(req))
          .getOrElseF(NotFound())
    }

    // Combine the static routes and the index route,
    // explicitly specifying the effect type with Router[IO] to get the required Monad instance.
    val httpApp = Router[IO](
      "/" -> (staticRoutes <+> indexRoute)
    ).orNotFound

    // Build and start the server using Ember with ip4s host and port types.
    EmberServerBuilder.default[IO]
      .withHost(host"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}