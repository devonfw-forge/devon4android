package com.example.base.baseapp.dagger.annotation

import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.RUNTIME)
annotation class UiScheduler
