package com.prasoon.task.data.core.dagger.builders

import com.prasoon.task.ui.main.view.SearchPhotosActivity
import com.prasoon.task.ui.main.view.SearchPhotoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SearchPhotoModule::class])
    abstract fun contributeSearchMovieActivity(): SearchPhotosActivity
}