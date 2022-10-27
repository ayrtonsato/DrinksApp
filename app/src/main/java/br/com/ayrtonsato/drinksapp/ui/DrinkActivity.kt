package br.com.ayrtonsato.drinksapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.ayrtonsato.drinksapp.databinding.ActivityDrinkBinding

class DrinkActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDrinkBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}