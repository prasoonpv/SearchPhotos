package com.prasoon.task.ui.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prasoon.task.data.core.network.NetworkAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchPhotoRepository @Inject constructor(apiService: NetworkAPI) {

    var apiService: NetworkAPI = apiService

    fun getSearchResult(text : String, apikey : String, page : Int, perPage : Int) : MutableLiveData<SearchListResp> {

        val searchResponse = MutableLiveData<SearchListResp>()

        apiService.getSearchList( text, apikey, page, perPage).enqueue(object :
            Callback<SearchListResp> {

            override fun onResponse(call: Call<SearchListResp>, response: Response<SearchListResp>) {
                if(response.isSuccessful){
                    searchResponse.value = response.body()
                } else{
                    searchResponse.value = SearchListResp(null, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<SearchListResp>, t: Throwable) {
                searchResponse.value = SearchListResp(null, null)
            }

        })

        return searchResponse;
    }
}