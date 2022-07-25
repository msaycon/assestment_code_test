package com.example.myapplication.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.MediaAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.listeners.AdapterEventListener
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by msaycon on 18,Jul,2022
 */
class MainFragment : BaseFragment(), AdapterEventListener {

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var mediaAdapter: MediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        initializeListeners()
    }

    override fun initializeViews() {
        binding.srlRefresh.setOnRefreshListener {
            mediaAdapter.refresh()
        }

        mediaAdapter.setItemClickListener(this)
        mediaAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.srlRefresh.setColorSchemeColors(resources.getColor(R.color.purple_200, null))

        binding.rcvList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rcvList.itemAnimator = DefaultItemAnimator()
        binding.rcvList.setHasFixedSize(true)
        binding.rcvList.adapter = mediaAdapter
    }

    override fun initializeListeners() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                mediaFlow.collectLatest {
                    mediaAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mediaAdapter.loadStateFlow
                .collectLatest {
                    when (it.refresh) {
                        is LoadState.NotLoading -> {
                            binding.srlRefresh.isRefreshing = false
                            binding.loadingView.shimmerViewContainer.visibility = View.GONE
                            if (it.append.endOfPaginationReached && mediaAdapter.itemCount < 1) {
                                binding.rcvList.visibility = View.GONE
                            } else {
                                binding.rcvList.visibility = View.VISIBLE
                            }
                        }
                        is LoadState.Loading -> {
                            if (mediaAdapter.itemCount == 0) {
                                binding.loadingView.shimmerViewContainer.visibility = View.VISIBLE
                            } else {
                                binding.loadingView.shimmerViewContainer.visibility = View.GONE
                            }
                        }
                        else -> {
                            Timber.e("Error Loading Data")
                        }
                    }
                }
        }
    }

    override fun onItemSelected(view: View, item: Any?, position: Int) {
        val media = mediaAdapter.peek(position)
        media?.let {
            val navDirection = MainFragmentDirections.actionNavMainToNavDetails(it.toMedia())
            findNavController().navigate(navDirection)
        }
    }

    override fun onItemLongClick(item: Any?, position: Int) {

    }
}