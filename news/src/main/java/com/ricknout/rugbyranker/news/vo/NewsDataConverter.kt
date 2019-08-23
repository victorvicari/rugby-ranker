package com.ricknout.rugbyranker.news.vo

import com.ricknout.rugbyranker.core.api.WorldRugbyNewsResponse

object NewsDataConverter {

    fun getWorldRugbyNewsFromWorldRugbyNewsResponse(worldRugbyNewsResponse: WorldRugbyNewsResponse):List<WorldRugbyNews> {
        return worldRugbyNewsResponse.news.map {
            WorldRugbyNews(
                    newsId = it.newsId,
                    newsTitle = it.newsTitle,
                    newsDescription = it.newsDescription,
                    newsDate = it.newsDate,
                    newsCanonicalUrl = it.newsCanonicalUrl,
                    newsSubtitle = it.newsSubtitle,
                    newsImageUrl = it.newsImageUrl
            )
        }
    }

}