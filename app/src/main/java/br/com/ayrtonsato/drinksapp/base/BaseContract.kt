package br.com.ayrtonsato.drinksapp.base

interface BaseContract {
    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter<T: View> {
        fun attachView(view: T)
        fun detachView()
    }
}