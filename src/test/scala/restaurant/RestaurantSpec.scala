package restaurant

import com.restaurant.{Good, Restaurant}
import org.scalatest._

class RestaurantSpec extends FlatSpec with Matchers {

  "calculateServiceCharge" should
      "calculate Accurately the amount of charges to add to a the total good given the list of goods that contains food"  in {

      val restaurant = new Restaurant()
      val goods = List(Good("Food",true,"cheese - Sandwch",2.00),Good("Drink",true,"cofee",1.00),Good("Food",true,"Steak - sandwich",4.50),Good("Drink", false, "Cola - cold", 0.50))
      val serviceChargeWithFood = restaurant.calculateServiceCharge(goods,20,10)

      serviceChargeWithFood should  be(1.6 )

      }

    "calculateServiceCharge" should
        "calculate Accurately the amount of charges to add to a the total good given the list of goods that doesnt contains food" in {

            val restaurant = new Restaurant()
            val goods = List(Good("Drink", false, "Cola - Cold", 0.50), Good("Drink", true, "cofee", 1.00), Good("Drink", false, "Cola - cold", 0.50))
            val serviceChargeWithoutFood = restaurant.calculateServiceCharge(goods, 20, 10)

            serviceChargeWithoutFood should be(0.0)

        }

    "calculateServiceCharge" should
      "calculate Accurately the amount of charges to add to a the total good given the list and should never default the max service charge to $20" in {

        val restaurant = new Restaurant()
        val goods = List(Good("Food",true,"cheese - Sandwch",2000.09),Good("Drink",true,"cofee",200.0),Good("Food",false,"Steak - sandwich",100.0))
        val serviceChargeWithoutFood = restaurant.calculateServiceCharge(goods, 20, 10)

        serviceChargeWithoutFood should be(20.0)

    }

    "Restaurant.total" should
        "return the total bill of of what was consumed without any sundry charges..LOL when there isnt any food in the order" in {
        val restaurant = new Restaurant()
        val goods = List(Good("Drink", false, "Cola - Cold", 0.50), Good("Drink", true, "cofee", 1.00), Good("Drink", false, "Cola - cold", 0.50))
        val total = restaurant.total(goods)

        total should be(2)

    }
    "Restaurant.total" should
      "return the total bill of of what was consumed with charges ..LOL but the charges shouldnt be more than 20 when there is food in the order" in {
        val restaurant = new Restaurant()
        val goods = List(Good("Food",true,"cheese - Sandwch",2000.09),Good("Drink",true,"cofee",200.0),Good("Food",false,"Steak - sandwich",100.0))
        val total = restaurant.total(goods)

        total should be(2320.09)

    }
  "Restaurant.resolveInput" should
    "return a structured list of goods" in {
    val restaurant = new Restaurant()
    val rawGoods = List("Coffee" ,"Cheese Sandwich","Cola", "Steak Sandwich" )
    val resolved = restaurant.resolveInput(rawGoods)

    val goods = List(Good("Drink",true,"Coffee",1.00),Good("Food",true,"Cheese Sandwich",2.00),Good("Drink",false,"Cola",0.50),Good("Food",false,"Steak Sandwich",4.50))

    resolved should equal(goods)



  }


}
