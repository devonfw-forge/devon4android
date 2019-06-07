package com.example.base.baseapp.dagger;

import com.example.base.baseapp.dagger.scope.ActivityScope;
import com.example.base.baseapp.module.main.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {})
@ActivityScope
public interface ActivityComponent {
    void inject(MainActivity activity);
}
