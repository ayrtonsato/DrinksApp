package br.com.ayrtonsato.drinksapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import br.com.ayrtonsato.drinksapp.adapter.DrinksAdapter
import br.com.ayrtonsato.drinksapp.base.BaseActivity
import br.com.ayrtonsato.drinksapp.databinding.ActivityDrinkBinding
import br.com.ayrtonsato.drinksapp.model.DrinkList
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import br.com.ayrtonsato.drinksapp.presenter.drinks.DrinksContract
import br.com.ayrtonsato.drinksapp.presenter.drinks.DrinksPresenter
import br.com.ayrtonsato.drinksapp.util.Constants

class DrinkActivity : BaseActivity(), DrinksContract.DrinksView {

    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        category = intent.getStringExtra(Constants.DRINK_CATEGORY).toString()
        super.onCreate(savedInstanceState)
    }

    private val binding by lazy {
        ActivityDrinkBinding.inflate(layoutInflater)
    }

    private val drinksAdapter by lazy {
        DrinksAdapter()
    }

    private lateinit var presenter: DrinksContract.DrinksPresenter

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun getLayout(): ViewBinding = binding

    override fun onInject() {
        configRecycle()
        val dataSource = DrinksDataSource()
        presenter = DrinksPresenter(dataSource, category)
        presenter.requestDrinks()
    }

    private fun configRecycle() {
        with(binding.rvDrinks) {
            visibility = View.GONE
            adapter = drinksAdapter
            layoutManager = LinearLayoutManager(this@DrinkActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@DrinkActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun showDrinks(drinks: DrinkList) {
        binding.rvDrinks.visibility = View.VISIBLE
        drinksAdapter.submitNewList(drinks.drinks)
    }

    override fun errorOnGetDrinks() {
        Toast
            .makeText(
                this@DrinkActivity,
                "Erro ao pegar lista de drinks",
                Toast.LENGTH_LONG
            ).show()
    }

    override fun showProgressBar() {
        binding.clProgress.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.clProgress.visibility = View.GONE
    }
}