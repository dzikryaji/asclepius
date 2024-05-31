package com.dicoding.asclepius.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<Article>,

	@field:SerializedName("status")
	val status: String,
)

data class Article(

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,
)
