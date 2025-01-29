package frontend

import com.raquo.laminar.api.L._
import org.scalajs.dom

object FrontendApp {
  def main(args: Array[String]): Unit = {
    val appElement = div(
      h1("Zakat Calculator"),
      form(
        onSubmit.preventDefault.mapToValue --> { _ =>
          val cashInput = dom.document.getElementById("cash").asInstanceOf[dom.html.Input]
          val goldInput = dom.document.getElementById("gold").asInstanceOf[dom.html.Input]
          val silverInput = dom.document.getElementById("silver").asInstanceOf[dom.html.Input]
          val businessAssetsInput = dom.document.getElementById("businessAssets").asInstanceOf[dom.html.Input]
          val debtsInput = dom.document.getElementById("debts").asInstanceOf[dom.html.Input]

          val cash = cashInput.value.toDoubleOption.getOrElse(0.0)
          val gold = goldInput.value.toDoubleOption.getOrElse(0.0)
          val silver = silverInput.value.toDoubleOption.getOrElse(0.0)
          val businessAssets = businessAssetsInput.value.toDoubleOption.getOrElse(0.0)
          val debts = debtsInput.value.toDoubleOption.getOrElse(0.0)

          val zakat = (cash + gold + silver + businessAssets - debts) * 0.025
          dom.document.getElementById("result").textContent = f"Zakat Payable: $$zakat%.2f"
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
