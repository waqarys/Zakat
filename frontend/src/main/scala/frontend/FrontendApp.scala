package frontend

import com.raquo.laminar.api.L._

object FrontendApp {
  def main(args: Array[String]): Unit = {
    val appElement = div(
      h1("Zakat Calculator"),
      form(
        onSubmit.preventDefault --> { e =>
          val cash = e.target.cash.value.toDouble
          val gold = e.target.gold.value.toDouble
          val silver = e.target.silver.value.toDouble
          val businessAssets = e.target.businessAssets.value.toDouble
          val debts = e.target.debts.value.toDouble

          val zakat = (cash + gold + silver + businessAssets - debts) * 0.025
          dom.document.getElementById("result").textContent = s"Zakat Payable: $$zakat"
        },
        div(label("Cash"), input(idAttr := "cash", tpe := "number")),
        div(label("Gold"), input(idAttr := "gold", tpe := "number")),
        div(label("Silver"), input(idAttr := "silver", tpe := "number")),
        div(label("Business Assets"), input(idAttr := "businessAssets", tpe := "number")),
        div(label("Debts"), input(idAttr := "debts", tpe := "number")),
        button("Calculate Zakat")
      ),
      div(idAttr := "result")
    )

    renderOnDomContentLoaded(dom.document.getElementById("app"), appElement)
  }
}
