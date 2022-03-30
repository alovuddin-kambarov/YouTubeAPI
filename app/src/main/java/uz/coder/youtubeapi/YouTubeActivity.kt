package uz.coder.youtubeapi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import uz.coder.youtubeapi.adapters.MyAdapter
import uz.coder.youtubeapi.databinding.ActivityYouTubeBinding
import uz.coder.youtubeapi.models.Item
import uz.coder.youtubeapi.retrofit.ApiClient
import uz.coder.youtubeapi.retrofit.Status
import uz.coder.youtubeapi.retrofit.ViewModel
import uz.coder.youtubeapi.utils.YouTubeFailureRecoveryActivity

class YouTubeActivity : AppCompatActivity() {
    lateinit var video_id: String
    lateinit var youTubeViewModel:ViewModel
    lateinit var binding: ActivityYouTubeBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        video_id = intent.getStringExtra("video_id")!!
        val channel_title = intent.getStringExtra("channel_title")!!
        val caption = intent.getStringExtra("caption")!!
        val time = intent.getStringExtra("views")!!


        youTubeViewModel = ViewModelProvider(this)[ViewModel::class.java]
        youTubeViewModel.getVideoByTag(binding.root.context, channel_title).observe(this) {

            when (it.status) {
                Status.SUCCESS -> {
                    val arrayList = ArrayList<Item>()
                    arrayList.addAll(it.data!!.items)
                    binding.rv.adapter = MyAdapter(arrayList)
                    Log.d(ContentValues.TAG,"OnCreate: data1111 = = = = = ${it.data}")

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.d(ContentValues.TAG,"OnCreate: error = = = = = ${it.message}")

                }

            }


        }



        binding.youtubePlayerView.id
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {


            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                super.onReady(youTubePlayer)

                youTubePlayer.loadVideo(video_id, 0F)

            }

        })


        var check = false
        var check2 = false
        binding.apply {

            titleName.text = caption
            channelName.text = channel_title

            views1.text = "19.9k views • $time"

            click1.setOnClickListener {
                check = if (!check) {
                    likeIcon.setBackgroundResource(R.drawable.ic_liked_icon)
                    disLikeIcon.setBackgroundResource(R.drawable.ic_dis_like_icon)
                    true
                } else {
                    likeIcon.setBackgroundResource(R.drawable.ic_like_icon)
                    false
                }
            }
            click2.setOnClickListener {
                check2 = if (!check2) {
                    likeIcon.setBackgroundResource(R.drawable.ic_like_icon)
                    disLikeIcon.setBackgroundResource(R.drawable.ic_dis_liked_icon)
                    true
                } else {
                    disLikeIcon.setBackgroundResource(R.drawable.ic_dis_like_icon)
                    false
                }
            }

            click3.setOnClickListener { share("https://www.youtube.com/watch?v=$video_id") }
            click4.setOnClickListener { Snackbar.make(it, "Downloading...", 3000).show() }
            click5.setOnClickListener { Snackbar.make(it, "Saved", 3000).show() }


        }


    }

    fun share(string: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, string)
            type = "text/plain"
        }
        startActivity(sendIntent)

    }
}