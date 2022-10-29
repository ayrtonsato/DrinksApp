package br.com.ayrtonsato.drinksapp.presenter.drink

import br.com.ayrtonsato.drinksapp.base.BaseContract
import br.com.ayrtonsato.drinksapp.model.FormatDrinkPreparation

interface DrinkPreparationContract {
    interface DrinkPreparationView: BaseContract.View {
        fun showDrink(drink: FormatDrinkPreparation.DrinkPreparation)
        fun onErrorToGetDrink()
    }

    interface DrinkPreparationPresenter: BaseContract.Presenter<DrinkPreparationView> {
        fun requestDrink()
        fun onSuccess(drink: FormatDrinkPreparation.DrinkPreparation)
        fun onError()
        fun onComplete()
    }
}