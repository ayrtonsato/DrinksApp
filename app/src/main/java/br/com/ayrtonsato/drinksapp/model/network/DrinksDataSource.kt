package br.com.ayrtonsato.drinksapp.model.network

import android.util.Log
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrinksDataSource {
    fun getCategories(catPresenter: CategoryContract.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            Log.i("DrinksDataSource", "Fazendo requisição")
            val result = Network.api.getCategories()
            if (result.isSuccessful) {
                result.body()?.let {
                    catPresenter.onSuccess(drinkCategoryList = it)
                    catPresenter.onComplete()
                }
            } else {
                catPresenter.onError()
                catPresenter.onComplete()
            }
        }
    }
}