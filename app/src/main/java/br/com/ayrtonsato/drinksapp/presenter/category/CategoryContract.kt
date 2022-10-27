package br.com.ayrtonsato.drinksapp.presenter.category

import br.com.ayrtonsato.drinksapp.base.BaseContract
import br.com.ayrtonsato.drinksapp.model.DrinkCategory
import br.com.ayrtonsato.drinksapp.model.DrinkCategoryList

interface CategoryContract {
    interface View: BaseContract.View {
        fun showProgressBar()
        fun hideProgressBar()
        fun showCategories(drinksCategory: List<DrinkCategory>)
        fun errorOnGetCategories()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun requestCategories()
        fun onSuccess(drinkCategoryList: DrinkCategoryList)
        fun onError()
        fun onComplete()
    }
}