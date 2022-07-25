package com.example.myapplication.data

import android.os.Parcelable
import com.example.myapplication.data.local.entities.MediaEntity
import kotlinx.parcelize.Parcelize

/**
 * Created by msaycon on 17,Jul,2022
 */
@Parcelize
data class Media(
    val trackId: String?,
    val trackName: String,
    val artworkUrl100: String?,
    val trackPrice: String?,
    val primaryGenreName: String,
    val longDescription: String?
) : Parcelable {
    fun toMediaEntity() = MediaEntity(
        trackId = trackId!!,
        trackName = trackName,
        artworkUrl100 = artworkUrl100,
        trackPrice = trackPrice,
        primaryGenreName = primaryGenreName,
        longDescription = longDescription
    )
}