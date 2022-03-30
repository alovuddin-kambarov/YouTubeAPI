package uz.coder.youtubeapi

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import uz.coder.youtubeapi.adapters.ViewPagerAdapter
import uz.coder.youtubeapi.databinding.FragmentHomeBinding
import uz.coder.youtubeapi.databinding.ItemTabBinding
import uz.coder.youtubeapi.retrofit.ViewModel


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        categoryList = ArrayList()
        categoryList.add("All")
        categoryList.add("Football")
        categoryList.add("Game")
        categoryList.add("Graphic")
        categoryList.add("Live")
        categoryList.add("Future")
        categoryList.add("Travel")
        binding.vp.adapter = ViewPagerAdapter(childFragmentManager, categoryList)
        binding.tab.setupWithViewPager(binding.vp)
        setTabs()

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }


        return binding.root
    }


    private fun setTabs() {

        for (i in 0 until binding.tab.tabCount) {

            val tab_item: ItemTabBinding =
                ItemTabBinding.inflate(LayoutInflater.from(binding.root.context))


            tab_item.itemTv.text = categoryList[i]
            binding.tab.getTabAt(i)?.customView = tab_item.root
            if (i == 0) {
                tab_item.itemTv.setTextColor(Color.WHITE)
                tab_item.back.setBackgroundResource(R.drawable.design2)

            } else {
                tab_item.itemTv.setTextColor(resources.getColor(R.color.black_dark))
                tab_item.back.setBackgroundResource(R.drawable.design1)

            }

        }

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                try {

                    val tab_item = ItemTabBinding.bind(customView!!)

                    tab_item.itemTv.setTextColor(Color.WHITE)
                    tab_item.back.setBackgroundResource(R.drawable.design2)
                } catch (e: Exception) {

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                try {


                } catch (e: Exception) {

                }
                val tab_item = ItemTabBinding.bind(customView!!)

                tab_item.itemTv.setTextColor(resources.getColor(R.color.black_dark))
                tab_item.back.setBackgroundResource(R.drawable.design1)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }

}