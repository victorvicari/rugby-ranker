package com.ricknout.rugbyranker.rankings.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.ricknout.rugbyranker.core.prefs.LongSharedPreferenceLiveData
import com.ricknout.rugbyranker.core.vo.Sport

class RankingsSharedPreferences(private val sharedPreferences: SharedPreferences) {

    fun setLatestWorldRugbyRankingsEffectiveTimeMillis(millis: Long, sport: Sport) {
        when (sport) {
            Sport.MENS -> setMensEffectiveTimeMillis(millis)
            Sport.WOMENS -> setWomensEffectiveTimeMillis(millis)
        }
    }

    private fun setMensEffectiveTimeMillis(millis: Long) = sharedPreferences.edit { putLong(KEY_EFFECTIVE_TIME_MILLIS_MENS, millis) }

    private fun setWomensEffectiveTimeMillis(millis: Long) = sharedPreferences.edit { putLong(KEY_EFFECTIVE_TIME_MILLIS_WOMENS, millis) }

    fun getLatestWorldRugbyRankingsEffectiveTimeMillis(sport: Sport) = when (sport) {
        Sport.MENS -> getMensEffectiveTimeMillis()
        Sport.WOMENS -> getWomensEffectiveTimeMillis()
    }

    private fun getMensEffectiveTimeMillis() = sharedPreferences.getLong(KEY_EFFECTIVE_TIME_MILLIS_MENS, DEFAULT_EFFECTIVE_TIME_MILLIS)

    private fun getWomensEffectiveTimeMillis() = sharedPreferences.getLong(KEY_EFFECTIVE_TIME_MILLIS_WOMENS, DEFAULT_EFFECTIVE_TIME_MILLIS)

    fun getLatestWorldRugbyRankingsEffectiveTimeMillisLiveData(sport: Sport): LiveData<Long> = when (sport) {
        Sport.MENS -> getMensEffectiveTimeMillisLiveData()
        Sport.WOMENS -> getWomensEffectiveTimeMillisLiveData()
    }

    private fun getMensEffectiveTimeMillisLiveData() = LongSharedPreferenceLiveData(sharedPreferences, KEY_EFFECTIVE_TIME_MILLIS_MENS, DEFAULT_EFFECTIVE_TIME_MILLIS)

    private fun getWomensEffectiveTimeMillisLiveData() = LongSharedPreferenceLiveData(sharedPreferences, KEY_EFFECTIVE_TIME_MILLIS_WOMENS, DEFAULT_EFFECTIVE_TIME_MILLIS)

    companion object {
        const val DEFAULT_EFFECTIVE_TIME_MILLIS = -1L
        private const val KEY_EFFECTIVE_TIME_MILLIS_MENS = "effective_time_millis_mens"
        private const val KEY_EFFECTIVE_TIME_MILLIS_WOMENS = "effective_time_millis_womens"
    }
}
