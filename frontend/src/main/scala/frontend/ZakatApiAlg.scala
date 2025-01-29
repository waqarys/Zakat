package frontend

trait ZakatApiAlg[F[_]] {
  def calculateZakat(
                      cash: Double,
                      gold: Double,
                      silver: Double,
                      businessAssets: Double,
                      debts: Double
                    ): F[Double]
}

object ZakatApiAlg {
  def impl: ZakatApiAlg[Option] = new ZakatApiAlg[Option] {
    override def calculateZakat(
                                 cash: Double,
                                 gold: Double,
                                 silver: Double,
                                 businessAssets: Double,
                                 debts: Double
                               ): Option[Double] = {
      val totalAssets = cash + gold + silver + businessAssets
      val zakatBase = totalAssets - debts
      if (zakatBase >= 85 * 75) Some(zakatBase * 0.025) else Some(0)
    }
  }
}
