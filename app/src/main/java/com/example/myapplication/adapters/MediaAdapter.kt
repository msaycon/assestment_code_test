package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.MediaEntity
import com.example.myapplication.databinding.ItemMediaBinding
import com.example.myapplication.listeners.AdapterEventListener
import com.example.myapplication.utils.loadFromUrl
import javax.inject.Inject

/**
 * Created by msaycon on 17,Jul,2022
 */

/**
 *  This is adapter class that holds data to be displayed
 */
class MediaAdapter @Inject constructor() :
    PagingDataAdapter<MediaEntity, MediaAdapter.ViewHolder>(MediaComparator) {

    private lateinit var adapterListener: AdapterEventListener

    fun setItemClickListener(listener: AdapterEventListener) {
        adapterListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemMediaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    inner class ViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaEntity, position: Int) {
            val context = itemView.context

            if (item.artworkUrl100.isNullOrEmpty()) {
                binding.artWork.setImageResource(R.drawable.ic_baseline_image_24)
            } else {
                item.artworkUrl100.let {
                    binding.artWork.loadFromUrl(it)
                }
            }

            binding.trackName.text = item.trackName
            binding.genre.text = item.primaryGenreName
            binding.price.text = context.getString(R.string.price_format, item.trackPrice)

            itemView.setOnClickListener {
                adapterListener.onItemSelected(it, item, position)
            }
        }
    }

    object MediaComparator : DiffUtil.ItemCallback<MediaEntity>() {
        override fun areItemsTheSame(oldItem: MediaEntity, newItem: MediaEntity) =
            oldItem.trackId == newItem.trackId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MediaEntity, newItem: MediaEntity) =
            oldItem == newItem
    }
}