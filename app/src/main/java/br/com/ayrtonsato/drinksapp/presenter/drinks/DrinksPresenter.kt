package br.com.ayrtonsato.drinksapp.presenter.drinks

import br.com.ayrtonsato.drinksapp.model.DrinkList
import br.com.ayrtonsato.drinksapp.model.network.DrinksDataSource

class DrinksPresenter(
    private val dataSource: DrinksDataSource,
    private val category: String
): DrinksContract.DrinksPresenter {

    private var view: DrinksContract.DrinksView? = null

    override fun requestDrinks() {
        view?.showProgressBar()
        dataSource.getDrinksList(this, category = category)
    }

    override fun onSuccess(drinks: DrinkList) {
        view?.showDrinks(drinks)
    }

    override fun onError() {
        view?.errorOnGetDrinks()
    }

    override fun onComplete() {
        view?.hideProgressBar()
    }

    override fun attachView(view: DrinksContract.DrinksView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}