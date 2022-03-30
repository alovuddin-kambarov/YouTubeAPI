package uz.coder.youtubeapi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.coder.youtubeapi.adapters.MyAdapter
import uz.coder.youtubeapi.databinding.FragmentResultBinding
import uz.coder.youtubeapi.models.Item
import uz.coder.youtubeapi.retrofit.Status
import uz.coder.youtubeapi.retrofit.ViewModel

class ResultFragment : Fragment() {
    private var str: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            str = it.getString("str")

        }
    }

    private lateinit var youTubeViewModel: ViewModel
    lateinit var binding: FragmentResultBinding
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater)
        youTubeViewModel = ViewModelProvider(this)[ViewModel::class.java]

        getVideo(str!!)
        pressDone()

        binding.mic.setOnClickListener { askSpeechInput() }

        binding.search.setText(str)




        binding.back.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment,true)
        }



        return binding.root
    }

    private fun getVideo(q: String) {


        youTubeViewModel.getVideoByTag(binding.root.context,q).observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val arrayList = ArrayList<Item>()
                    arrayList.addAll(it.data!!.items)
                    binding.rv.adapter = MyAdapter(arrayList)
                    Log.d(ContentValues.TAG, "OnCreate: data22222 = = = = = ${it.data}")

                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(ContentValues.TAG, "OnCreate: error22222 = = = = = ${it.message}")
                    Log.d(ContentValues.TAG, "OnCreate: data22222 = = = = = ${it.message}")

                }

            }


        }


    }

    override fun onDestroy() {
        findNavController().popBackStack(R.id.homeFragment,true)
        super.onDestroy()

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                val s = results?.get(0) ?: "Hi"

                val bundle = Bundle()
                bundle.putString("str",s)
                findNavController().navigate(R.id.resultFragment,bundle)

                getVideo(s)

                s
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun askSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en")

        startActivityForResult(intent, 1)
    }

    private fun pressDone() {
        binding.search.setOnEditorActionListener(
            TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {


                    getVideo(binding.search.text.toString())

                    closeKerBoard()
                    return@OnEditorActionListener true
                }
                false
            })
    }


    private fun closeKerBoard() {
        val imm =
            requireActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}