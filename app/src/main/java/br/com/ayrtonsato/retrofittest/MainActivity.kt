package br.com.ayrtonsato.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.ayrtonsato.retrofittest.databinding.ActivityMainBinding
import br.com.ayrtonsato.retrofittest.model.DrinkCategory
import br.com.ayrtonsato.retrofittest.network.Network

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    /*suspend fun getCategories(): DrinkCategory {
        return Network.api.getCategories()
    }*/
}