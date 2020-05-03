package com.infinitumcode.mymovieapp.util

class QueryHelper {
    companion object {
        fun popularMovies(): HashMap<String, String> {
            val map = HashMap<String, String>()
            map["sort_by"] = "popularity.desc"
            map["include_video"] = "false"
            return map
        }
        fun childMovies(): HashMap<String, String> {
            val map = HashMap<String, String>()
            map["sort_by"] = "popularity.desc"
            map["include_video"] = "false"
            map["with_genres"] = "10751"
            return map
        }
        fun trendingMovies(): HashMap<String, String> {
            val map = HashMap<String, String>()
            map["time_window"] = "week"
            return map
        }
    }
}