package br.com.ayrtonsato.drinksapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ayrtonsato.drinksapp.adapter.CategoryAdapter
import br.com.ayrtonsato.drinksapp.databinding.ActivityMainBinding
import br.com.ayrtonsato.drinksapp.model.DrinkCategory
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryContract
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryPresenter
import br.com.ayrtonsato.drinksapp.util.Constants


class MainActivity : AppCompatActivity(), CategoryContract.View {

    private val categoryAdapter by lazy {
        CategoryAdapter() {
            onClick(it)
        }
    }

    private lateinit var presenter: CategoryContract.Presenter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configRecycle()

        val dataSource = DrinksDataSource()
        presenter = CategoryPresenter(dataSource)
        presenter.requestCategories()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    private fun onClick(drink: DrinkCategory) {
        val intent = Intent(this, DrinkActivity::class.java)
        val category = drink.strCategory.replace(" ", "/")
        intent.putExtra(Constants.DRINK_CATGEGORY, category)
        startActivity(intent)
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

    override fun showProgressBar() {
        binding.rvDrinkCategories.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.clProgress.visibility = View.GONE
    }

    override fun showCategories(drinksCategory: List<br.com.ayrtonsato.drinksapp.model.DrinkCategory>) {
        categoryAdapter.updateList(drinksCategory)
    }

    override fun errorOnGetCategories() {
        Toast
            .makeText(
                this@MainActivity,
                "Erro ao buscar informação",
                Toast.LENGTH_LONG
            )
            .show()
    }
}