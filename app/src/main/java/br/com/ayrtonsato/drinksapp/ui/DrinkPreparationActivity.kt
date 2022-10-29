package br.com.ayrtonsato.drinksapp.ui

import android.widget.Toast
import androidx.viewbinding.ViewBinding
import br.com.ayrtonsato.drinksapp.R
import br.com.ayrtonsato.drinksapp.base.BaseActivity
import br.com.ayrtonsato.drinksapp.databinding.ActivityDrinkPreparationBinding
import br.com.ayrtonsato.drinksapp.model.FormatDrinkPreparation
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import br.com.ayrtonsato.drinksapp.presenter.drink.DrinkPreparationContract
import br.com.ayrtonsato.drinksapp.presenter.drink.DrinkPreparationPresenter
import br.com.ayrtonsato.drinksapp.util.Constants
import com.bumptech.glide.Glide

class DrinkPreparationActivity : BaseActivity(), DrinkPreparationContract.DrinkPreparationView {

    private val binding by lazy {
        ActivityDrinkPreparationBinding.inflate(layoutInflater)
    }

    private lateinit var presenter: DrinkPreparationContract.DrinkPreparationPresenter

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
        val id = intent.getStringExtra(Constants.DRINK_ID)
        val dataSource = DrinksDataSource()
        if (id == null) {
            this.finish()
        }
        presenter = DrinkPreparationPresenter(dataSource, id!!)
        presenter.requestDrink()
    }

    override fun showDrink(drink: FormatDrinkPreparation.DrinkPreparation) {
        with(binding) {
            var ingredients: String = ""
            Glide
                .with(this@DrinkPreparationActivity)
                .load(drink.thumb)
                .fitCenter()
                .into(ivDrinkThumb)
            tvDrinkName.text = drink.name
            tvCategory.text = drink.category
            tvPreparation.text = drink.preparation
            drink.ingredients.entries.forEach { entry ->
                ingredients += "\u2022 ${entry.key} - ${entry.value ?: ""}\n"
            }
            tvIngredientsList.text = ingredients
        }
    }

    override fun onErrorToGetDrink() {
        Toast
            .makeText(
                this@DrinkPreparationActivity,
                getString(R.string.error_fetch_drink),
                Toast.LENGTH_LONG
            ).show()
    }

    override fun showProgressBar() = Unit

    override fun hideProgressBar() = Unit
}