package br.com.ayrtonsato.drinksapp.model.network

import br.com.ayrtonsato.drinksapp.presenter.category.CategoryContract
import br.com.ayrtonsato.drinksapp.presenter.drinks.DrinksContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrinksDataSource {
    fun getCategories(catPresenter: CategoryContract.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = Network.api.getCategories()
            if (result.isSuccessful) {
                result.body()?.let {
                    catPresenter.onSuccess(it)
                    catPresenter.onComplete()
                }
            } else {
                catPresenter.onError()
                catPresenter.onComplete()
            }
        }
    }

    fun getDrinksList(drinksPresenter: DrinksContract.DrinksPresenter, category: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = Network.api.getDrinksByCategory(category)
            if (result.isSuccessful) {
                result.body()?.let {
                    drinksPresenter.onSuccess(it)
                    drinksPresenter.onComplete()
                }
            } else {
                drinksPresenter.onError()
                drinksPresenter.onComplete()
            }

        }
    }
}