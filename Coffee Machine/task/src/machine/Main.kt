package machine

enum class CoffeeDrinks
    (
    val drinkName: String,
    val reqWater: Int,
    val reqMilk: Int,
    val reqBeans: Int,
    val drinkPrice: Int,
) {
    ESPRESSO("Espresso", 250, 0, 16, 4),
    LATTE("Latte", 350, 75, 20, 7),
    CAPPUCCINO("Cappuccino", 200, 100, 12, 6)
}

class CoffeeMachine(
    private var waterInStock: Int = 400,
    private var milkInStock: Int = 540,
    private var beansInStock: Int = 120,
    private var cupsInStock: Int = 9,
    private var cashInMachine: Int = 550,
) {
    //Check if there are enough supplies to serve a drink
    private fun checkIfEnoughSuppliesForDrink(drink: CoffeeDrinks): String {
        return when {
            waterInStock < drink.reqWater -> "Sorry, not enough water!"
            milkInStock < drink.reqMilk -> "Sorry, not enough milk!"
            beansInStock < drink.reqBeans -> "Sorry, not enough coffee beans!"
            cupsInStock < 1 -> "Sorry, not enough coffee cups!"
            else -> "I have enough resources, making you a coffee!"
        }
    }

    //Add supplies to the machine
    fun actionFillMachine() {
        println("Write how many ml of water you want to add:")
        waterInStock += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milkInStock += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        beansInStock += readln().toInt()
        println("Write how many disposable cups you want to add:")
        cupsInStock += readln().toInt()
    }

    //Take cash from the machine
    fun actionTakeCash() {
        println("I gave you \$$cashInMachine")
        cashInMachine = 0
    }

    //State of coffee machine
    fun actionPrintRemainingSupplies() {

        println(
            """
                |The coffee machine has:
                |$waterInStock ml of water
                |$milkInStock ml of milk
                |$beansInStock g of coffee beans
                |$cupsInStock disposable cups
                |$cashInMachine of money
                """.trimMargin()
        )
    }

    fun actionBuyADrink() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
        when(readln()) {
            "1" -> attemptToServeADrink(CoffeeDrinks.ESPRESSO)
            "2" -> attemptToServeADrink(CoffeeDrinks.LATTE)
            "3" -> attemptToServeADrink(CoffeeDrinks.CAPPUCCINO)
            "back" -> println()
            else -> println("Error! Such a drink doesn't exist.")

        }

    }

    private fun attemptToServeADrink(drink: CoffeeDrinks) {
        when(val checkSuppliesResult = checkIfEnoughSuppliesForDrink(drink)) {
            "I have enough resources, making you a coffee!" -> {
                waterInStock -= drink.reqWater
                milkInStock -= drink.reqMilk
                beansInStock -= drink.reqBeans
                cupsInStock--
                cashInMachine += drink.drinkPrice
                println(checkSuppliesResult)
            }
            else -> println(checkSuppliesResult)
        }
    }

}

fun main() {
    val coffeeMachine = CoffeeMachine()

    while (true) {
        println("Write action (buy, fill, take, exit):")

        when(readln()) {
            "buy" -> coffeeMachine.actionBuyADrink()
            "fill" -> coffeeMachine.actionFillMachine()
            "take" -> coffeeMachine.actionTakeCash()
            "remaining" -> coffeeMachine.actionPrintRemainingSupplies()
            "exit" -> break
            else -> println("Error, such option doesn't exist.")
        }
    }
}