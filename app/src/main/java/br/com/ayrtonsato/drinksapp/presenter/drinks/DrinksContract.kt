package br.com.ayrtonsato.drinksapp.presenter.drinks

import br.com.ayrtonsato.drinksapp.base.BaseContract
import br.com.ayrtonsato.drinksapp.model.DrinkList

interface DrinksContract {

    interface DrinksView: BaseContract.View {
        fun showDrinks(drinks: DrinkList)
        fun errorOnGetDrinks()
    }

    interface DrinksPresenter: BaseContract.Presenter<DrinksView> {
        fun requestDrinks()
        fun onSuccess(drinks: DrinkList)
        fun onError()
        fun onComplete()
    }
}