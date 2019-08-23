package com.ricknout.rugbyranker.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ricknout.rugbyranker.core.viewmodel.ScrollableViewModel
import com.ricknout.rugbyranker.news.repository.NewsRepository
import com.ricknout.rugbyranker.news.vo.WorldRugbyNews
import com.ricknout.rugbyranker.news.work.NewsWorkManager
import javax.inject.Inject

class NewsViewModel @Inject constructor(
        newsWorkManager: NewsWorkManager,
        private val newsRepository: NewsRepository
) : ScrollableViewModel() {

    private val _refreshingLiveWorldRugbyNews = MutableLiveData<Boolean>().apply { value = false }
    val refreshingLiveWorldRugbyNews: LiveData<Boolean>
        get() = _refreshingLiveWorldRugbyNews

    private val _liveWorldRugbyNews = MutableLiveData<List<WorldRugbyNews>>()
    val liveWorldRugbyNews: LiveData<List<WorldRugbyNews>>
        get() = _liveWorldRugbyNews

    init {
        newsWorkManager.fetchRugbyNews()
    }

    fun loadRugbyNews(showRefresh: Boolean = true, onComplete: (success: Boolean) -> Unit) {
        if (showRefresh) _refreshingLiveWorldRugbyNews.value = true
        newsRepository.fetchWorldRugbyNewsAsync(viewModelScope) { sucess, worldRugbyNews ->
            _liveWorldRugbyNews.value = worldRugbyNews
            if (showRefresh) _refreshingLiveWorldRugbyNews.value = false
            onComplete(sucess)
        }
    }

}