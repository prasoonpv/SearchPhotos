package com.prasoon.task.data.core.dagger.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.prasoon.task.MainApplication
import com.prasoon.task.util.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: MainApplication): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideUtils(context: Context): Utils {
        return Utils(context)
    }


}