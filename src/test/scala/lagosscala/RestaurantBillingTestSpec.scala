package lagosscala

import org.scalatest._

class RestaurantBillingTestSpec extends FlatSpec with Matchers {

  val firstPurchase = List("Cola", "Coffee", "Cheese Sandwish")
  val allDrinks = List("Cola", "Coffee", "Cola", "Coffee")
  val hotFoodPurchase = List("Coffee", "Steak Sandwish")
  val foodPurchase = List("Cola", "Coffee", "Cheese Sandwish")
  val oddFoods = List("Cola", "Fish Sandwish", "Coffee")

  val moreFoods = allDrinks ::: hotFoodPurchase ::: allDrinks :::
    hotFoodPurchase :::  allDrinks ::: allDrinks ::: hotFoodPurchase :::
    hotFoodPurchase ::: allDrinks ::: allDrinks ::: hotFoodPurchase :::
    allDrinks ::: hotFoodPurchase ::: allDrinks :::
    hotFoodPurchase :::  allDrinks ::: allDrinks ::: hotFoodPurchase :::
    hotFoodPurchase ::: allDrinks ::: allDrinks ::: hotFoodPurchase :::
    allDrinks ::: hotFoodPurchase ::: allDrinks :::
    hotFoodPurchase :::  allDrinks ::: allDrinks ::: hotFoodPurchase :::
    hotFoodPurchase ::: allDrinks ::: allDrinks ::: hotFoodPurchase


  it should "give total amount of purchases" in {
    val (_, totalPrice) = ServiceCharge.billWithCharge(firstPurchase)
    totalPrice shouldEqual 3.5
    totalPrice.isInstanceOf[BigDecimal] shouldEqual true
  }

  it should "not apply any service charge when all purchases are drinks" in {
    val (totalAfterServiceCharge, totalDrinkOnly) = ServiceCharge.billWithCharge(allDrinks)
    totalAfterServiceCharge shouldEqual totalPrice
  }

  it should "return 10% of service charge in case there is FOOD" in {
    val (totalAfterServiceCharge, totalFoodOnly) = ServiceCharge.billWithCharge(foodPurchase)
    totalAfterServiceCharge shouldEqual 0.35 + totalPrice
  }

  it should "return 20% of service charge in case there is hot FOOD" in {
    val (totalAfterServiceCharge, totalHotFoodOnly) = ServiceCharge.billWithCharge(hotFoodPurchase)
    totalAfterServiceCharge shouldEqual 1.10 + totalFoodOnly
  }

  it should "not add more than 20 pounds to service charge no matter how much orderItems were ordered" in {
    val (totalAfterServiceCharge, totalBillOnly) = ServiceCharge.billWithCharge(moreFoods)
    val billDifference = totalAfterServiceCharge - totalBillOnly
    billDifference.toDouble should be <= 20.0
  }

}
