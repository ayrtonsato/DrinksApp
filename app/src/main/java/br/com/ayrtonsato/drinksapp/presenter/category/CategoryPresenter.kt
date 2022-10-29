package br.com.ayrtonsato.drinksapp.presenter.category

import android.util.Log
import br.com.ayrtonsato.drinksapp.base.BaseContract
import br.com.ayrtonsato.drinksapp.model.DrinkCategoryList
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource
import kotlinx.coroutines.launch

class CategoryPresenter(
    private val dataSource: DrinksDataSource
): CategoryContract.Presenter {

    private var view: CategoryContract.View? = null

    override fun requestCategories() {
        view?.showProgressBar()
        dataSource.getCategories(this)
    }

    override fun onSuccess(drinkCategoryList: DrinkCategoryList) {
        view?.showCategories(drinkCategoryList.drinks)
    }

    override fun onError() {
        view?.errorOnGetCategories()
    }

    override fun onComplete() {
        view?.hideProgressBar()
    }

    override fun attachView(view: CategoryContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}