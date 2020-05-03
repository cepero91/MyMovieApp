package com.infinitumcode.mymovieapp.domain.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "MovieLocal")
@Parcelize
data class MovieResult(
    @PrimaryKey
    val id: Long,
    val original_title: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val popularity: Double,
    val title: String,
    val vote_average: Double
) : Parcelable

data class MovieDetail(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("genres")
    val genres: List<Genere>? = listOf(),
    @SerializedName("credits")
    val credits: Credits? = null,
    @SerializedName("popularity")
    val popularity: Double? = 0.0,
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0,
    @SerializedName("runtime")
    val runtime: Int? = 0,
    @SerializedName("tagline")
    val tagline: String? = ""
)


data class Actor(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profile_path: String?
)

data class Credits(
    @SerializedName("cast") var cast: List<Actor>? = listOf()
)

data class Genere(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)

data class MovieQuery(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<MovieResult>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)