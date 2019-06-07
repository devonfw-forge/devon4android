package com.example.base.baseapp.modules.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BasePresenter<UI : BaseUI> {

    private val commandQueue: Queue<UICommand<UI>> = LinkedList<UICommand<UI>>()
    private zvar ui: UI? = null
    private val compositeDisposable : CompositeDisposable? = null

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    fun attach(ui: UI) {
        this.ui = ui
        do {
            val uiCommand = commandQueue.poll()
            uiCommand?.execute(ui)
        } while (uiCommand != null)
    }

    fun detach() {
        compositeDisposable?.clear()
        this.ui = null
    }

    protected fun send(uiCommand: UICommand<UI>)  {
        ui?.let { uiCommand.execute(it) }
    }

    protected fun sendSticky(uiCommand: UICommand<UI>) {
        ui?.let { uiCommand.execute(it) } ?: commandQueue.add(uiCommand)
    }

    interface UICommand<in UI : BaseUI> {
        fun execute(ui : UI)
    }
}


