package com.ricknout.rugbyranker.live.ui

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ricknout.rugbyranker.core.livedata.EventObserver
import com.ricknout.rugbyranker.core.ui.dagger.DaggerAndroidXFragment
import com.ricknout.rugbyranker.core.util.doIfResumed
import com.ricknout.rugbyranker.core.vo.Sport
import com.ricknout.rugbyranker.live.NavGraphLiveDirections
import com.ricknout.rugbyranker.live.R
import com.ricknout.rugbyranker.matches.ui.WorldRugbyMatchListAdapter
import com.ricknout.rugbyranker.matches.ui.WorldRugbyMatchSpaceItemDecoration
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_live_matches.*

class LiveMatchesFragment : DaggerAndroidXFragment(R.layout.fragment_live_matches) {

    private val args: LiveMatchesFragmentArgs by navArgs()

    private val sport: Sport by lazy { args.sport }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LiveMatchesViewModel by lazy {
        when (sport) {
            Sport.MENS -> activityViewModels<MensLiveMatchesViewModel> { viewModelFactory }.value
            Sport.WOMENS -> activityViewModels<WomensLiveMatchesViewModel> { viewModelFactory }.value
        }
    }

    private lateinit var refreshSnackBar: Snackbar

    private lateinit var worldRugbyMatchListAdapter: WorldRugbyMatchListAdapter
    private lateinit var worldRugbyMatchSpaceItemDecoration: WorldRugbyMatchSpaceItemDecoration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupSnackbars()
        setupViewModel()
        setupSwipeRefreshLayout()
    }

    private fun setupRecyclerView() {
        worldRugbyMatchSpaceItemDecoration = WorldRugbyMatchSpaceItemDecoration(requireContext())
        matchesRecyclerView.addItemDecoration(worldRugbyMatchSpaceItemDecoration, 0)
        worldRugbyMatchListAdapter = WorldRugbyMatchListAdapter { worldRugbyMatch ->
            viewModel.predict(worldRugbyMatch)
        }
        matchesRecyclerView.adapter = worldRugbyMatchListAdapter
        matchesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.onScroll(delta = dy)
            }
        })
    }

    private fun setupSnackbars() {
        val coordinatorLayout = ActivityCompat.requireViewById<CoordinatorLayout>(requireActivity(), R.id.coordinatorLayout)
        refreshSnackBar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_SHORT)
    }

    private fun setupViewModel() {
        viewModel.liveWorldRugbyMatches.observe(viewLifecycleOwner, Observer { liveWorldRugbyMatches ->
            val isNull = liveWorldRugbyMatches == null
            progressBar.isVisible = isNull
            if (isNull) {
                return@Observer
            }
            worldRugbyMatchListAdapter.submitList(liveWorldRugbyMatches)
            val isEmpty = liveWorldRugbyMatches.isEmpty()
            emptyState.isVisible = isEmpty
        })
        viewModel.refreshingLiveWorldRugbyMatches.observe(viewLifecycleOwner, Observer { refreshingLiveWorldRugbyMatches ->
            swipeRefreshLayout.isRefreshing = refreshingLiveWorldRugbyMatches
        })
        viewModel.worldRugbyRankingsTeamIds.observe(viewLifecycleOwner, Observer { worldRugbyRankingsTeamIds ->
            worldRugbyMatchListAdapter.worldRugbyRankingsTeamIds = worldRugbyRankingsTeamIds.associateBy({ it }, { true })
        })
        viewModel.scrollToTop.observe(viewLifecycleOwner, EventObserver {
            doIfResumed { matchesRecyclerView.smoothScrollToPosition(0) }
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
            viewModel.refreshLiveWorldRugbyMatches { success ->
                if (!success) {
                    doIfResumed {
                        refreshSnackBar.setText(R.string.snackbar_failed_to_refresh_live_world_rugby_matches)
                        refreshSnackBar.show()
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "LiveMatchesFragment"
        fun newInstance(sport: Sport): LiveMatchesFragment {
            val matchesFragment = LiveMatchesFragment()
            matchesFragment.arguments = NavGraphLiveDirections.liveMatchesFragmentAction(sport).arguments
            return matchesFragment
        }
    }
}
