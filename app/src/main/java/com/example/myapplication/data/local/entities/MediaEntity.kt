package com.example.myapplication.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myapplication.data.Media

/**
 * Created by msaycon on 17,Jul,2022
 */

/**
 * This serves as an entity/data in database tables
 */

@Entity(tableName = "media", indices = [Index("trackId")])
class MediaEntity constructor(
    @PrimaryKey val trackId: String,
    @ColumnInfo(name = "trackName") val trackName: String,
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String?,
    @ColumnInfo(name = "trackPrice") val trackPrice: String?,
    @ColumnInfo(name = "primaryGenreName") val primaryGenreName: String,
    @ColumnInfo(name = "longDescription") val longDescription: String?
) {
    fun toMedia() = Media(
        trackId = trackId,
        trackName = trackName,
        artworkUrl100 = artworkUrl100,
        trackPrice = trackPrice,
        primaryGenreName = primaryGenreName,
        longDescription = longDescription
    )
}