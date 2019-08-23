package com.ricknout.rugbyranker.news.work

import androidx.work.*
import java.util.concurrent.TimeUnit

class NewsWorkManager(private val workManager: WorkManager) {

    private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    private val requestNewsUpdate = PeriodicWorkRequestBuilder<NewsRugbyWorker>(
            WORK_REQUEST_REPEAT_INTERVAL, WORK_REQUEST_REPEAT_INTERVAL_TIME_UNIT
    ).setConstraints(constraints).build()

    fun fetchRugbyNews() {
        workManager.enqueueUniquePeriodicWork("news_rugby", WORK_REQUEST_EXISTING_PERIODIC_WORK_POLICY, requestNewsUpdate)
    }

    companion object {
        private const val WORK_REQUEST_REPEAT_INTERVAL = 1L
        private val WORK_REQUEST_REPEAT_INTERVAL_TIME_UNIT = TimeUnit.DAYS
        private val WORK_REQUEST_EXISTING_PERIODIC_WORK_POLICY = ExistingPeriodicWorkPolicy.KEEP
    }
}