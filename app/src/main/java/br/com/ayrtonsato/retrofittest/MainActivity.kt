package br.com.ayrtonsato.retrofittest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import br.com.ayrtonsato.retrofittest.adapter.CategoryAdapter
import br.com.ayrtonsato.retrofittest.databinding.ActivityMainBinding
import br.com.ayrtonsato.retrofittest.model.DrinkCategory
import br.com.ayrtonsato.retrofittest.network.Network
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecycle()

        getCategories()
    }

    private fun configRecycle() {
        with(binding.rvDrinkCategories) {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    fun getCategories() {
        lifecycleScope.launch {
            val result = Network.api.getCategories()
            if (result.isSuccessful) {
                result.body()?.let {
                    binding.clProgress.visibility = View.GONE
                    binding.rvDrinkCategories.visibility = View.VISIBLE
                    categoryAdapter.updateList(it.drinks)
                }
            } else {
                Toast
                    .makeText(
                        this@MainActivity,
                        "Erro ao buscar informação",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }
    }
}