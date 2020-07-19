package com.prasoon.task.ui.main.model

import com.google.gson.annotations.SerializedName

data class SearchListResp(

	@SerializedName("photos")
	val photos: PhotosData? = null,

	@SerializedName("stat")
    val response : String ? =null
)

data class PhotosData(
	@SerializedName("page")
	val page: Int? = null,

	@SerializedName("perpage")
	val perPage: Int? = null,
	@SerializedName("photo")
	val photo: List<PhotoResp>? = null

)

data class PhotoResp(@SerializedName("id")
				 val id: String? = null,
				 @SerializedName("farm")
				 val farm: Int? = null,
				 @SerializedName("server")
				 val server: String? = null,
				 @SerializedName("secret")
				 val secret: String? = null ,
                 @SerializedName("title")
				 val title: String? = null)

