package com.restaurant

import java.util.Scanner

import Console.{GREEN, MAGENTA, RED, RESET, UNDERLINED, WHITE, YELLOW, BOLD}
import scala.collection.mutable.ArrayBuffer


case class Good(typeOf:String,temperature:Boolean, name: String, price:Double)
class Restaurant {

  val temperatures = Map(1 -> false, 2 -> true)
  val goodTypes = Map(1 -> "Coffee", 2 -> "Cola", 3 -> "Cheese Sandwich",4 -> "Steak Sandwich")
  val prices = Map("Coffee" -> 1.00, "Cheese Sandwich" -> 2.00 , "Cola" -> 0.50 , "Steak Sandwich" -> 4.50)

  def addGoods(goods: List[Good]): Double = goods.map(e => e.price).reduce((x, y) => x + y)

  def calculateServiceCharge(goods: List[Good], hot: Double, defaultCharge: Double): Double = {
    def charge(sum: Double, charge: Double) = charge * sum / 100

    if (goods.filter(e => e.typeOf.equals("Food") && e.temperature == true).length > 0) {
      return if(charge(addGoods(goods), hot) > 20) 20 else charge(addGoods(goods), hot)
    } else if (goods.filter(e => e.typeOf.equals("Food")).length > 0) {
      return if(charge(addGoods(goods), defaultCharge) > 20) 20 else charge(addGoods(goods), defaultCharge)
    } else return 0.0
  }

    def detectTypeOf(typee: String) : String = {
      typee match {
        case "Coffee" | "Cola" => "Drink"
        case "Cheese Sandwich" | "Steak Sandwich" => "Food"
        case _ => "Food"
      }
    }
  //i dont like this because string matching is a messy business
  def resolveInput(goods :List[String]) : List[Good] = {
    var lOGoods : ArrayBuffer[Good] = ArrayBuffer()
    goods.map( e => e.trim match {
      case "Cola" =>
        lOGoods += Good(detectTypeOf(goodTypes.apply(2)),temperatures.apply(1),e,prices.apply("Cola"))
      case "Coffee" =>
        lOGoods += Good(detectTypeOf(goodTypes.apply(1)),temperatures.apply(2),e,prices.apply("Coffee"))
      case "Cheese Sandwich" =>
        lOGoods += Good(detectTypeOf(goodTypes.apply(3)),temperatures.apply(2),e,prices.apply("Cheese Sandwich"))
      case "Steak Sandwich" =>
        lOGoods += Good(detectTypeOf(goodTypes.apply(4)),temperatures.apply(1),e,prices.apply("Steak Sandwich"))

    })
    lOGoods.toList
  }

    def total(goods :List[Good]): Double ={
      val charge = calculateServiceCharge(goods,20,10)
      addGoods(goods) + charge
    }




}
object Restaurant extends App {

//  val goods = List(Good("Drink", false, "Cola - Cold", 0.50), Good("Drink", true, "cofee", 1.00), Good("Drink", false, "Cola - cold", 0.50))
//   val res = goods.filter(e => e.typeOf.equals("Food") && e.temperature == true)

  Console.println(s"Welcome to ${MAGENTA}Restaurant X, ${RESET}our menu includes the following are\n       the foods and drinks, which are available as cold or hot ")
  val restaurant = new Restaurant()
  Console.println()
//  restaurant.goodTypes.foreach(Console.println)
  Console.println(s"Be noted that hot foods attract${YELLOW} 20% ${RESET}of the total\n as service charge, and cold food ${YELLOW}10%${RESET}, kindly bear with us to serve you better")
  Console.println(s"you can choose the goods based on their number on the menu${RED} enter F to finish and get you order and bill${RESET}")

  restaurant.goodTypes.foreach(Console.println)
  var order : ArrayBuffer[Good]= scala.collection.mutable.ArrayBuffer()
  val sc = new Scanner(System.in)
  while(sc.hasNextInt){
    val selected = sc.nextInt()
    Console.println(s" kindly choose :    ${WHITE}Cold -> 1, ${RED}hot -> 2${RESET}")
    val temp = sc.nextInt()
    selected match {
      case 1 | 2 | 3 | 4 =>
        temp match {
          case 1 | 2 => order += Good(restaurant.detectTypeOf(restaurant.goodTypes.apply(selected)),restaurant.temperatures.apply(temp),restaurant.goodTypes.apply(selected),restaurant.prices.apply(restaurant.goodTypes.apply(selected)))
        }
      case _ => Console.println(s"${RED}that good is not in our menu, kindly select from the list${RESET}")
    }
    Console.println(s"order: ${restaurant.goodTypes.apply(selected)}\tprice: £${restaurant.prices.apply(restaurant.goodTypes.apply(selected))}")
  }
  Console.println(s"This is your bill ${WHITE}${UNDERLINED}${BOLD}£${restaurant.total(order.toList)}")


//  while(scala.io.StdIn.readInt() - 1 > 0){
//
//  }

//  val a = scala.io.StdIn.readInt()



}