package br.com.ayrtonsato.drinksapp.model.network

import br.com.ayrtonsato.drinksapp.model.DrinkCategoryList
import br.com.ayrtonsato.drinksapp.model.DrinkList
import br.com.ayrtonsato.drinksapp.model.DrinkPreparationList
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query

interface DrinksAPI {
    @GET("list.php?c=list")
    suspend fun getCategories(): Response<DrinkCategoryList>

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): Response<DrinkList>

    @GET("lookup.php")
    suspend fun getDrinkById(@Query("i") id: String): Response<DrinkPreparationList>
}