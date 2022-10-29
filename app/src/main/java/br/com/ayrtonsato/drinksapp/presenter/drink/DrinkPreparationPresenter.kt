package br.com.ayrtonsato.drinksapp.presenter.drink

import br.com.ayrtonsato.drinksapp.model.FormatDrinkPreparation
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource

class DrinkPreparationPresenter(
    private val dataSource: DrinksDataSource,
    private val id: String
): DrinkPreparationContract.DrinkPreparationPresenter {

    private var view: DrinkPreparationContract.DrinkPreparationView? = null

    override fun requestDrink() {
        view?.showProgressBar()
        dataSource.getDrinkById(this, id)
    }

    override fun onSuccess(drink: FormatDrinkPreparation.DrinkPreparation) {
        view?.showDrink(drink)
    }

    override fun onError() {
        view?.onErrorToGetDrink()
    }

    override fun onComplete() {
        view?.hideProgressBar()
    }

    override fun attachView(view: DrinkPreparationContract.DrinkPreparationView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}