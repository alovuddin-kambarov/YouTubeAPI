package uz.coder.youtubeapi.retrofit

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import uz.coder.youtubeapi.models.MyYouTube
import uz.coder.youtubeapi.repository.YoutubeRepository
import uz.coder.youtubeapi.utils.NetworkHelper

class ViewModel : ViewModel() {

    private var searchRepository = YoutubeRepository(ApiClient.apiService)
    private var liveData = MutableLiveData<Resource<MyYouTube>>()


    fun getVideoByTag(context: Context, q: String): LiveData<Resource<MyYouTube>> {

        searchRepository.q = q


        viewModelScope.launch {
            if (NetworkHelper(context).isNetworkConnected()) {
                coroutineScope {
                    supervisorScope {
                        try {
                            liveData.postValue(Resource.loading(null))
                            val video = searchRepository.getVideoByTag()

                            if (video.isSuccessful) {
                                liveData.postValue(Resource.success(video.body()))
                            } else {
                                liveData.postValue(
                                    Resource.error(
                                        video.raw().toString(),
                                        null
                                    )
                                )


                            }


                        } catch (e: Exception) {
                            liveData.postValue(Resource.error(e.message ?: "Error", null))
                        }
                    }
                }
            } else {
                liveData.postValue(
                    Resource.error(
                        "Internet no connection! Please, connect internet and try again!",
                        null
                    )
                )

            }

        }

        return liveData

    }


}

