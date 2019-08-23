package com.ricknout.rugbyranker.news.repository

import com.ricknout.rugbyranker.core.api.WorldRugbyService
import com.ricknout.rugbyranker.news.vo.NewsDataConverter
import com.ricknout.rugbyranker.news.vo.WorldRugbyNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
        private val worldRugbyService: WorldRugbyService
) {

    suspend fun fetchWorldRugbyNewsSync(): Pair<Boolean, List<WorldRugbyNews>> {
        var success = false
        val worldRugbyNews = mutableListOf<WorldRugbyNews>()
        return try {
            val worldRugbyNewsResponse = worldRugbyService.getNews()
            val news = NewsDataConverter.getWorldRugbyNewsFromWorldRugbyNewsResponse(worldRugbyNewsResponse)
            success = true
            worldRugbyNews.addAll(news)
            success to worldRugbyNews
        } catch (_: Exception) {
            success to worldRugbyNews
        }
    }

    fun fetchWorldRugbyNewsAsync(coroutineScope: CoroutineScope, onComplete: (sucess: Boolean, worldRugbyNews: List<WorldRugbyNews>) -> Unit) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) { fetchWorldRugbyNewsSync() }
            val success = result.first
            val worldRugbyNews = result.second
            onComplete(success, worldRugbyNews)
        }
    }

}