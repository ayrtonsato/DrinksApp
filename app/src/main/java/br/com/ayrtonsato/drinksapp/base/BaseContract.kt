package br.com.ayrtonsato.drinksapp.base

interface BaseContract {
    interface View

    interface Presenter<T : View> {
        fun attachView(view: T)
        fun detachView()
    }
}