package br.com.ayrtonsato.drinksapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import br.com.ayrtonsato.drinksapp.R
import br.com.ayrtonsato.drinksapp.adapter.DrinkListAdapter
import br.com.ayrtonsato.drinksapp.base.BaseActivity
import br.com.ayrtonsato.drinksapp.databinding.ActivityDrinkListBinding
import br.com.ayrtonsato.drinksapp.model.Drink
import br.com.ayrtonsato.drinksapp.model.DrinkList
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import br.com.ayrtonsato.drinksapp.presenter.drinks.DrinksContract
import br.com.ayrtonsato.drinksapp.presenter.drinks.DrinksPresenter
import br.com.ayrtonsato.drinksapp.util.Constants

class DrinkListActivity : BaseActivity(), DrinksContract.DrinksView {

    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        category = intent.getStringExtra(Constants.DRINK_CATEGORY).toString()
        super.onCreate(savedInstanceState)
    }

    private val binding by lazy {
        ActivityDrinkListBinding.inflate(layoutInflater)
    }

    private val drinkListAdapter by lazy {
        DrinkListAdapter {
            onItemClick(it)
        }
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
            adapter = drinkListAdapter
            layoutManager = LinearLayoutManager(this@DrinkListActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@DrinkListActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun onItemClick(drink: Drink) {
        val intent = Intent(this, DrinkPreparationActivity::class.java)
        intent.putExtra(Constants.DRINK_ID, drink.idDrink)
        startActivity(intent)
    }

    override fun showDrinks(drinks: DrinkList) {
        binding.rvDrinks.visibility = View.VISIBLE
        drinkListAdapter.submitNewList(drinks.drinks)
    }

    override fun errorOnGetDrinks() {
        Toast
            .makeText(
                this@DrinkListActivity,
                getString(R.string.error_fetch_drink_list),
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