package com.prasoon.task.data.core.dagger.components

import com.prasoon.task.MainApplication
import com.prasoon.task.data.core.dagger.builders.ActivityBuilder
import com.prasoon.task.data.core.dagger.modules.AppModule
import com.prasoon.task.data.core.dagger.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class,
    NetworkModule::class, ActivityBuilder::class])
interface TestAppMainComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}