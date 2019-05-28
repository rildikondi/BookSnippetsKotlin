package com.akondi.booksnippetskotlin.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.akondi.booksnippetskotlin.application.BookSnippetsApp


abstract class BaseActivity : AppCompatActivity() {

//    protected fun getApplicationComponent(): ApplicationComponent =
//        (getApplication() as BookSnippetsApp).mApplicationComponent

    val Activity.app: BookSnippetsApp
        get() = application as BookSnippetsApp

    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onViewReady(savedInstanceState, intent)
    }

    @CallSuper
    protected open fun onViewReady(savedInstanceState: Bundle?, intent: Intent) =
        resolveDaggerDependency()

    protected abstract fun getContentView(): Int

    protected open fun resolveDaggerDependency() {}

    protected fun showDialog(message: String) {
        progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage(message)
        progressDialog.setCancelable(true)
        progressDialog.show()
    }

    protected fun hideDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }
}