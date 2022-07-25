package com.example.myapplication.adapters.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.myapplication.api.ApiService
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.entities.MediaEntity
import com.example.myapplication.utils.isNetworkConnected
import timber.log.Timber
import java.io.IOException

/**
 * Created by msaycon on 17,Jul,2022
 */

/**
 *  This class serves as a mediator between remote and local data used by paging adapter
 */

@OptIn(ExperimentalPagingApi::class)
class MediaMediator(private val apiService: ApiService, private val appDatabase: AppDatabase) :
    RemoteMediator<Int, MediaEntity>() {

    private val mediaDao = appDatabase.mediaDao()

    override suspend fun initialize(): InitializeAction {
        return if (!isNetworkConnected) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MediaEntity>
    ): MediatorResult {
        return try {
            val result = apiService.getMedia()
            val mediaList = arrayListOf<MediaEntity>()
            if (result.isSuccessful && result.body() != null) {
                result.body()?.let {
                    it.results.filter { media -> media.trackId != null }.forEach { media ->
                        mediaList.add(media.toMediaEntity())
                    }
                }
            }

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    mediaDao.clearAll()
                }

                if (mediaList.isNotEmpty()) {
                    mediaDao.insertAll(mediaList)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch (e: IOException) {
            Timber.e(e)
            // IOException for network failures.
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            Timber.e(e)
            // HttpException for any non-2xx HTTP status codes.
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }
}