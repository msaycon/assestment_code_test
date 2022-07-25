package com.example.myapplication.ui.fragments.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.data.local.entities.MediaEntity
import com.example.myapplication.repos.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by msaycon on 18,Jul,2022
 */
class MainViewModel @Inject constructor(private val mediaRepository: MediaRepository) :
    BaseViewModel() {

    private lateinit var _mediaFlow: Flow<PagingData<MediaEntity>>
    val mediaFlow: Flow<PagingData<MediaEntity>>
        get() = _mediaFlow

    init {
        getMediaList()
    }

    private fun getMediaList() = launchPagingAsync({
        mediaRepository.getMedia()
            .cachedIn(viewModelScope)
    }, {
        _mediaFlow = it
    })
}