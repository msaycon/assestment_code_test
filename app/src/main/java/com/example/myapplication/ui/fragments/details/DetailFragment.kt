package com.example.myapplication.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.Media
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.utils.loadBackgroundFromUrl
import com.example.myapplication.utils.loadFromUrl

/**
 * Created by msaycon on 18,Jul,2022
 */
class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding

    private var media: Media? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        media = arguments?.get("media") as Media
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        media?.let {
            setupToolbar(it.trackName)
            initializeViews()
        }
    }

    override fun initializeViews() {
        media?.let { media ->
            media.artworkUrl100?.let {
                binding.artWorkBg.loadBackgroundFromUrl(it)
                binding.artWork.loadFromUrl(it)
            }

            binding.trackName.text = media.trackName
            binding.genre.text = media.primaryGenreName
            binding.price.text = getString(R.string.price_format, media.trackPrice)

            media.longDescription?.let {
                binding.descTitle.visibility = View.VISIBLE
                binding.desc.text = it
            }

        }
    }
}