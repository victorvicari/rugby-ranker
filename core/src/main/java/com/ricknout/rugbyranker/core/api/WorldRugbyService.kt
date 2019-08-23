package com.ricknout.rugbyranker.core.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WorldRugbyService {

    @GET("rugby/rankings/{sports}")
    suspend fun getRankings(
        @Path("sports") sports: String,
        @Query("date") date: String
    ): WorldRugbyRankingsResponse

    @GET("rugby/match")
    suspend fun getMatches(
        @Query("sports") sports: String,
        @Query("states") states: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): WorldRugbyMatchesResponse

    @GET("rugby/team/{id}")
    suspend fun getTeams(
        @Path("id") id: Long
    ): WorldRugbyTeamsResponse

    @GET("content/worldrugby/text/{lang}")
    suspend fun getNews(
        @Path("lang") lang: String = "en"
    ): WorldRugbyNewsResponse

    companion object {
        const val SPORT_MENS = "mru"
        const val SPORT_WOMENS = "wru"
        const val STATE_UNPLAYED = "U"
        const val STATE_COMPLETE = "C"
        const val STATE_LIVE_1ST_HALF = "L1"
        const val STATE_LIVE_2ND_HALF = "L2"
        const val STATE_LIVE_HALF_TIME = "LHT"
        const val SORT_ASC = "asc"
        const val SORT_DESC = "desc"
        const val BASE_URL = "https://cmsapi.pulselive.com/"
    }
}
