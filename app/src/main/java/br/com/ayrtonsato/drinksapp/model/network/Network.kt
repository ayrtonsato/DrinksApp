package br.com.ayrtonsato.drinksapp.model.network

import br.com.ayrtonsato.drinksapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    private fun getRetrofitInstance(path: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DrinksAPI by lazy {
        getRetrofitInstance(Constants.BASE_URL).create(DrinksAPI::class.java)
    }
}