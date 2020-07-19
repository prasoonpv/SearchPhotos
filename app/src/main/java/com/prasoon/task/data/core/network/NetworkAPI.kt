package com.prasoon.task.data.core.network

import com.prasoon.task.ui.main.model.SearchListResp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NetworkAPI {

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun getSearchList(
                      @Query("text") text: String,
                      @Query("api_key")  api_key : String,
                      @Query("page")  page : Int,
                      @Query("per_page")  per_page : Int) : Call<SearchListResp>

}