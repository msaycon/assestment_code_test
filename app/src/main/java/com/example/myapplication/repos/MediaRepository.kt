package com.example.myapplication.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.adapters.mediator.MediaMediator
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.entities.MediaEntity
import com.example.myapplication.utils.initialLoadSize
import com.example.myapplication.utils.initialPageSize
import com.example.myapplication.utils.prefetchDistance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by msaycon on 17,Jul,2022
 */
@Suppress("BlockingMethodInNonBlockingContext")
@OptIn(ExperimentalPagingApi::class)
class MediaRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {

    // This function fetch media list from local and remote repository
    fun getMedia(): Flow<PagingData<MediaEntity>> = Pager(
        config = getPagingConfig(),
        remoteMediator = MediaMediator(apiService, db)
    ) {
        db.mediaDao().getAll()
    }.flow

    // This function is a configuration for paging adapter
    private fun getPagingConfig(): PagingConfig = PagingConfig(
        pageSize = initialPageSize,
        prefetchDistance = prefetchDistance,
        initialLoadSize = initialLoadSize
    )
}