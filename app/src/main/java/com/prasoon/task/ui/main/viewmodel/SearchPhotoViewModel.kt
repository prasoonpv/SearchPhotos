package com.prasoon.task.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.prasoon.task.ui.main.model.SearchListResp
import com.prasoon.task.ui.main.model.SearchPhotoRepository
import javax.inject.Inject

class SearchPhotoViewModel @Inject constructor(private val repository: SearchPhotoRepository) : ViewModel (){

    fun doSearch(text : String, apikey : String, page : Int, perPage : Int)
            :LiveData<SearchListResp> = repository.getSearchResult(text, apikey, page, perPage)

}