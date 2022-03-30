package uz.coder.youtubeapi.repository

import uz.coder.youtubeapi.retrofit.ApiClient
import uz.coder.youtubeapi.retrofit.ApiService

class YoutubeRepository(var apiService: ApiService) {

    var q = ""

    suspend fun getVideoByTag() = apiService.getVideoByTag("snippet",q,ApiClient.apiKey)

}