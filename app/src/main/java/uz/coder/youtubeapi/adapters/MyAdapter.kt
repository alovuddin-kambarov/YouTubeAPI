package uz.coder.youtubeapi.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.coder.youtubeapi.YouTubeActivity
import uz.coder.youtubeapi.databinding.ItemBinding
import uz.coder.youtubeapi.models.Item

class MyAdapter(private val arraylist: ArrayList<Item>) : RecyclerView.Adapter<MyAdapter.ViewH>() {

    inner class ViewH(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(video: Item) {


            Picasso.get().load(video.snippet.thumbnails.high.url).into(binding.image)
            binding.title.text = video.snippet.title
            itemView.setOnClickListener {

                binding.root.context.startActivity(
                    Intent(
                        binding.root.context,
                        YouTubeActivity::class.java
                    ).putExtra("video_id", video.id.videoId)
                        .putExtra("caption", video.snippet.title).putExtra("channel_title",video.snippet.channelTitle).putExtra("views",video.snippet.publishTime)
                )

            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewH {

        return ViewH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    override fun onBindViewHolder(holder: ViewH, position: Int) {
        holder.onBind(arraylist[position])

    }

}