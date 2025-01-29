package backend

import cats.Applicative
import cats.implicits.*

trait ZakatCalculatorAlg[F[_]] {
  def calculateZakat(
                      cash: BigDecimal,
                      gold: BigDecimal,
                      silver: BigDecimal,
                      businessAssets: BigDecimal,
                      debts: BigDecimal
                    ): F[BigDecimal]
}

object ZakatCalculatorAlg {
  def impl[F[_]: Applicative]: ZakatCalculatorAlg[F] = new ZakatCalculatorAlg[F] {
    private val nisab: BigDecimal = 85 * 75 // Example: Gold rate per gram is $75.

    override def calculateZakat(
                                 cash: BigDecimal,
                                 gold: BigDecimal,
                                 silver: BigDecimal,
                                 businessAssets: BigDecimal,
                                 debts: BigDecimal
                               ): F[BigDecimal] = {
      val totalAssets = cash + gold + silver + businessAssets
      val zakatBase = totalAssets - debts
      if (zakatBase >= nisab) (zakatBase * 0.025).pure[F] else BigDecimal(0).pure[F]
    }
  }
}
