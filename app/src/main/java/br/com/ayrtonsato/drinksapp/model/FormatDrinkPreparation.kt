package br.com.ayrtonsato.drinksapp.model

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class FormatDrinkPreparation(private val drink: br.com.ayrtonsato.drinksapp.model.DrinkPreparation) {
    data class DrinkPreparation(
        val name: String,
        val category: String,
        val thumb: String,
        val ingredients: Map<String, String?>,
        val preparation: String
    )

    private fun insertIntoMap(
        regex: Regex,
        map: MutableMap<Int, String>,
        member: KProperty1<out br.com.ayrtonsato.drinksapp.model.DrinkPreparation, *>
    ) {
        val matchResult = regex.find(member.name)
        if (matchResult != null) {
            val (digitString) = matchResult.destructured
            val digit = digitString.toInt()
            val value = member.getter.call(drink) as String?
            if (value != null) {
                map[digit] = value
            }
        }
    }

    private fun iterateAndReturnMapOfIngredients(
        ingredients: Map<Int, String>,
        measurements: Map<Int, String>
    ): Map<String, String?> {
        val ingMeasurement = mutableMapOf<String, String?>()
        ingredients.forEach { entry ->
            if (measurements.containsKey(entry.key)) {
                ingMeasurement[entry.value] = measurements[entry.key]
            } else {
                ingMeasurement[entry.value] = null
            }
        }
        return ingMeasurement.toMap()
    }


    fun format(): DrinkPreparation {
        val ingredients = mutableMapOf<Int, String>()
        val measurements = mutableMapOf<Int, String>()
        drink::class.memberProperties.forEach { member ->
            member.isAccessible = true
            val regexIngredients = """strIngredient(\d+)""".toRegex()
            val regexMeasure = """strMeasure(\d+)""".toRegex()
            insertIntoMap(regexIngredients, ingredients, member)
            insertIntoMap(regexMeasure, measurements, member)
        }
        val mapOfIngAndMea = iterateAndReturnMapOfIngredients(
            ingredients.toMap(),
            measurements.toMap()
        )
        return DrinkPreparation(
            name = drink.strDrink!!,
            category = drink.strCategory!!,
            thumb = drink.strDrinkThumb!!,
            preparation = drink.strInstructions!!,
            ingredients = mapOfIngAndMea
        )
    }
}