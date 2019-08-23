package com.ricknout.rugbyranker.news.ui


import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ricknout.rugbyranker.core.ui.dagger.DaggerAndroidXFragment
import com.ricknout.rugbyranker.core.util.doIfResumed
import com.ricknout.rugbyranker.news.R
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : DaggerAndroidXFragment(R.layout.fragment_news) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    private lateinit var refreshSnackBar: Snackbar
    private lateinit var newsRugbyPagedListAdapter: NewsRugbyPagedListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLoadNews()
        setupSnackbars()
        setupViewModel()
        setupSwipeRefreshLayout()
    }

    private fun setupLoadNews() {
        viewModel.loadRugbyNews {
            if (!it) {
                doIfResumed {
                    refreshSnackBar.setText(R.string.snackbar_failed_to_refresh_live_world_rugby_matches)
                    refreshSnackBar.show()
                }
            }
        }
    }

    private fun setupSnackbars() {
        val coordinatorLayout = ActivityCompat.requireViewById<CoordinatorLayout>(requireActivity(), R.id.coordinatorLayout)
        refreshSnackBar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_SHORT)
    }

    private fun setupViewModel() {
        viewModel.liveWorldRugbyNews.observe(viewLifecycleOwner, Observer {
            val isNull = it == null
            progressBar.isVisible = isNull
            if (isNull) {
                return@Observer
            }
            newsRugbyPagedListAdapter = NewsRugbyPagedListAdapter(it)
            newsRecyclerView.adapter = newsRugbyPagedListAdapter
        })
    }

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshColors = resources.getIntArray(R.array.colors_swipe_refresh)
        swipeRefreshLayout.setColorSchemeColors(*swipeRefreshColors)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_surface)
        swipeRefreshLayout.setProgressViewOffset(true,
                swipeRefreshLayout.progressViewStartOffset + resources.getDimensionPixelSize(R.dimen.spacing_double),
                swipeRefreshLayout.progressViewEndOffset)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadRugbyNews { success ->
                if (!success) {
                    doIfResumed {
                        refreshSnackBar.setText(R.string.snackbar_failed_to_refresh_live_world_rugby_matches)
                        refreshSnackBar.show()
                    }
                }
            }
        }
    }
}
