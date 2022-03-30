package uz.coder.youtubeapi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    const val apiKey = "AIzaSyDu60jVxffL36_Z0YQInfflH3Vn2y_sVJY"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()

    }

    var apiService: ApiService = getRetrofit().create(ApiService::class.java)

}