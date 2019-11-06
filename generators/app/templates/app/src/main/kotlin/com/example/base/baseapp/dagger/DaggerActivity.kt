package com.example.base.baseapp.dagger

import com.example.base.baseapp.MyApplication
import com.example.base.baseapp.modules.base.BaseActivity
import com.example.base.baseapp.modules.base.BasePresenter
import com.example.base.baseapp.modules.base.BaseUI

abstract class DaggerActivity<T : BasePresenter<BaseUI>> : BaseActivity<T, ActivityComponent>() {

    override fun getComponent(): ActivityComponent? {
        if (component == null) { // TODO: REMOVE IF STATE
            component = lastCustomNonConfigurationInstance as ActivityComponent?
        }
        if (component == null) { // TODO: REMOVE IF STATE
            val application = application as MyApplication
            component = application.appComponent!!.nonConfiguration()
        }
        return component
    }
}
