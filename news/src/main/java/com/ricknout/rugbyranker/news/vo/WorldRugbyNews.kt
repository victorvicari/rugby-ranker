package com.ricknout.rugbyranker.news.vo

data class WorldRugbyNews(
        val newsId: String,
        val newsTitle: String,
        val newsDescription: String?,
        val newsDate: String,
        val newsCanonicalUrl: String,
        val newsSubtitle: String?,
        val newsImageUrl: String?
)