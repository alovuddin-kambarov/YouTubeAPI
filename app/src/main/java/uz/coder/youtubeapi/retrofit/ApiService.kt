package uz.coder.youtubeapi.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.coder.youtubeapi.models.MyYouTube

interface ApiService {


    @GET("search")
    suspend fun getVideoByTag(
        @Query("part") part: String = "snippet,id",
        @Query("q") q: String ,
        @Query("key") key: String = ApiClient.apiKey,
        @Query("maxResults") maxResults: Int = 3
    ): Response<MyYouTube>


}


