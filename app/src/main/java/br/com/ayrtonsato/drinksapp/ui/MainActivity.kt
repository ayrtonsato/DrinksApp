package br.com.ayrtonsato.drinksapp.ui

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import br.com.ayrtonsato.drinksapp.R
import br.com.ayrtonsato.drinksapp.adapter.CategoryAdapter
import br.com.ayrtonsato.drinksapp.base.BaseActivity
import br.com.ayrtonsato.drinksapp.databinding.ActivityMainBinding
import br.com.ayrtonsato.drinksapp.model.DrinkCategory
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryContract
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryPresenter
import br.com.ayrtonsato.drinksapp.util.Constants


class MainActivity : BaseActivity(), CategoryContract.View {

    private val categoryAdapter by lazy {
        CategoryAdapter() {
            onClick(it)
        }
    }

    private lateinit var presenter: CategoryContract.Presenter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getLayout(): ViewBinding = binding

    override fun onInject() {
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
        val intent = Intent(this, DrinkListActivity::class.java)
        val category = drink.strCategory.replace(" ", "_")
        intent.putExtra(Constants.DRINK_CATEGORY, category)
        startActivity(intent)
    }

    private fun configRecycle() {
        with(binding.rvDrinkCategories) {
            visibility = View.GONE
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
        binding.clProgress.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.clProgress.visibility = View.GONE
    }

    override fun showCategories(drinksCategory: List<DrinkCategory>) {
        binding.rvDrinkCategories.visibility = View.VISIBLE
        categoryAdapter.updateList(drinksCategory)
    }

    override fun errorOnGetCategories() {
        Toast
            .makeText(
                this@MainActivity,
                getString(R.string.error_fetch_categories),
                Toast.LENGTH_LONG
            )
            .show()
    }
}