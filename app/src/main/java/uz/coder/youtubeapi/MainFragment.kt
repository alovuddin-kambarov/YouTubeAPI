package uz.coder.youtubeapi

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.coder.youtubeapi.adapters.MyAdapter
import uz.coder.youtubeapi.databinding.FragmentMainBinding
import uz.coder.youtubeapi.models.Item
import uz.coder.youtubeapi.retrofit.Status
import uz.coder.youtubeapi.retrofit.ViewModel


class MainFragment(private var q:String) : Fragment() {

    private lateinit var youTubeViewModel: ViewModel
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        youTubeViewModel = ViewModelProvider(this)[ViewModel::class.java]
        youTubeViewModel.getVideoByTag(binding.root.context,q).observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val arrayList = ArrayList<Item>()
                    arrayList.addAll(it.data!!.items)
                    binding.rv.adapter = MyAdapter(arrayList)
                    Log.d(TAG,"OnCreate: data1111 = = = = = ${it.data}")

                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG,"OnCreate: error = = = = = ${it.message}")

                }

            }


        }


        return binding.root
    }


}