package com.prasoon.task.ui.main.view

import androidx.lifecycle.ViewModelProvider
import com.prasoon.task.data.core.network.NetworkAPI
import com.prasoon.task.ui.main.model.SearchPhotoRepository
import com.prasoon.task.ui.main.viewmodel.SearchPhotoViewModel
import com.prasoon.task.util.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class SearchPhotoModule {

    @Provides
    fun provideSearchPhotoViewModel(repository: SearchPhotoRepository): SearchPhotoViewModel {
        return SearchPhotoViewModel(repository)
    }

    @Provides
    fun provideSearchPhotoRepository(apiService: NetworkAPI): SearchPhotoRepository {
        return SearchPhotoRepository(apiService)
    }

    @Provides
    fun provideViewModelProvider(viewModel: SearchPhotoViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(viewModel)
    }
}