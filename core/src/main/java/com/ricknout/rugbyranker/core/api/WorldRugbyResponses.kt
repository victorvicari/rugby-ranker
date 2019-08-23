package com.ricknout.rugbyranker.core.api

import com.google.gson.annotations.SerializedName

data class Effective(
    val label: String,
    val millis: Long,
    val gmtOffset: Float
)

data class Team(
    val id: Long,
    val name: String,
    val abbreviation: String?
)

data class Entry(
    val pos: Int,
    val previousPos: Int,
    val pts: Float,
    val previousPts: Float,
    val matches: Int,
    val team: Team
)

data class WorldRugbyRankingsResponse(
    val effective: Effective,
    val entries: List<Entry>,
    val label: String
)

data class WorldRugbyNewsResponse(
        @SerializedName("content")
        val news: List<News>
)

data class News(
        @SerializedName("id")
        val newsId: String,
        @SerializedName("title")
        val newsTitle: String,
        @SerializedName("description")
        val newsDescription: String?,
        @SerializedName("date")
        val newsDate: String,
        @SerializedName("canonicalUrl")
        val newsCanonicalUrl: String,
        @SerializedName("subtitle")
        val newsSubtitle: String?,
        @SerializedName("imageUrl")
        val newsImageUrl: String?
)

data class PageInfo(
    val page: Int,
    val numPages: Int,
    val pageSize: Int,
    val numEntries: Int
)

data class Venue(
    val id: Long,
    val name: String,
    val city: String,
    val country: String
)

data class Event(
    val id: Long,
    val label: String,
    val sport: String,
    val start: Effective,
    val end: Effective,
    val rankingsWeight: Float
)

data class Match(
    val matchId: Long,
    val description: String?,
    val venue: Venue?,
    val time: Effective,
    val attendance: Int,
    val teams: List<Team>,
    val scores: List<Int>,
    val status: String,
    val events: List<Event>
)

data class WorldRugbyMatchesResponse(
    val pageInfo: PageInfo,
    val content: List<Match>
)

data class TeamDetail(
    val id: Long,
    val country: String,
    val naming: Naming
)

data class Naming(
    val name: String,
    val abbr: String
)

data class WorldRugbyTeamsResponse(
    val teams: List<TeamDetail>
)
