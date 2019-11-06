package com.example.base.baseapp.dagger

import com.example.base.baseapp.modules.base.BaseFragment
import com.example.base.baseapp.modules.base.BasePresenter
import com.example.base.baseapp.modules.base.BaseUI

abstract class DaggerFragment<T : BasePresenter<BaseUI>> : BaseFragment<T, ActivityComponent>() {

    override val component: ActivityComponent
        get() = (activity as DaggerActivity<*>).component!!
}
