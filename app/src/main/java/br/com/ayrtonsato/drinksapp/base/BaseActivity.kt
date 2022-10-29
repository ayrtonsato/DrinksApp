package br.com.ayrtonsato.drinksapp.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import br.com.ayrtonsato.drinksapp.presenter.category.CategoryContract

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout().root)
        onInject()
    }

    protected abstract fun getLayout(): ViewBinding

    protected abstract fun onInject()
}