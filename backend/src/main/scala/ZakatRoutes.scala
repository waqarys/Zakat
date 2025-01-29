import cats.Monad
import cats.effect.*
import cats.implicits.*
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes}

class ZakatRoutes[F[_] : Concurrent : Monad](zakatService: ZakatCalculatorAlg[F]) extends Http4sDsl[F] {

  // Explicit type annotations for Circe
  implicit val zakatDecoder: EntityDecoder[F, ZakatRequest] = jsonOf[F, ZakatRequest]
  implicit val zakatEncoder: EntityEncoder[F, ZakatResponse] = jsonEncoderOf[F, ZakatResponse]

  def routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case req@POST -> Root / "calculate-zakat" =>
      for {
        request <- req.as[ZakatRequest] // `Monad[F]` ensures `.as` works without warnings
        zakatAmount <- zakatService.calculateZakat(
          request.cash, request.gold, request.silver, request.businessAssets, request.debts
        )
        response <- Ok(ZakatResponse(zakatAmount))
      } yield response
  }
}

case class ZakatRequest(
                         cash: BigDecimal,
                         gold: BigDecimal,
                         silver: BigDecimal,
                         businessAssets: BigDecimal,
                         debts: BigDecimal
                       )

case class ZakatResponse(zakatPayable: BigDecimal)
