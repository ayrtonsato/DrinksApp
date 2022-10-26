package br.com.ayrtonsato.retrofittest.network

import br.com.ayrtonsato.retrofittest.model.DrinkCategory
import retrofit2.http.GET
import retrofit2.Response

interface DrinksAPI {
    @GET("list.php?c=list")
    suspend fun getCategories(): Response<DrinkCategory>
}