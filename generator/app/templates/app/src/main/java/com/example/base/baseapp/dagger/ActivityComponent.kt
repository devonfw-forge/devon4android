package com.example.base.baseapp.dagger

import com.capgemini.templateapp.dagger.scope.ActivityScope
import com.example.base.baseapp.modules.main.MainActivity

import dagger.Subcomponent

@Subcomponent(modules = [])
@ActivityScope
interface ActivityComponent : BaseActivityComponent {
    fun inject(activity: MainActivity)
}
