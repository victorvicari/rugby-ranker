package com.ricknout.rugbyranker.news.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ricknout.rugbyranker.news.repository.NewsRepository
import javax.inject.Inject

class NewsRugbyWorker @Inject constructor(
        context: Context,
        workerParameters: WorkerParameters,
        private val newsRepository: NewsRepository
) :  CoroutineWorker(context, workerParameters) {

    override suspend fun doWork() = fetchNewsRugby()

    private suspend fun fetchNewsRugby(): Result {
        val result = newsRepository.fetchWorldRugbyNewsSync()
        val success = result.first
        return if (success) Result.success() else Result.retry()
    }

}