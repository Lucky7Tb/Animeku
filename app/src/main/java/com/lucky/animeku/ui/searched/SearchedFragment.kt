package com.lucky.animeku.ui.searched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucky.animeku.databinding.FragmentSearchedBinding
import com.lucky.animeku.model.ListAnime
import com.lucky.animeku.network.AnimeApiInterface
import com.lucky.animeku.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchedFragment : Fragment() {
    private lateinit var fragmentSearchedBinding: FragmentSearchedBinding
    private lateinit var searchFragmentAdapter: SearchedFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        fragmentSearchedBinding.progressBar.visibility = ProgressBar.GONE
        initRecyclerView()
        searchAnime()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchedBinding = FragmentSearchedBinding.inflate(inflater, container, false)
        fragmentSearchedBinding.searchButton.setOnClickListener { searchAnime() }
        return fragmentSearchedBinding.root
    }

    private fun initRecyclerView() {
        searchFragmentAdapter = SearchedFragmentAdapter(arrayListOf())

        with(fragmentSearchedBinding.listTopAnime) {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchFragmentAdapter
        }
    }

    private fun searchAnime() {
        fragmentSearchedBinding.progressBar.visibility = ProgressBar.VISIBLE
        val animeApi: AnimeApiInterface = Api.getInstance().create(AnimeApiInterface::class.java)
        animeApi.searchAnime(fragmentSearchedBinding.searchField.text.toString())
            .enqueue(object : Callback<ListAnime> {
                override fun onResponse(
                    call: Call<ListAnime>,
                    response: Response<ListAnime>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        searchFragmentAdapter.setData(data!!.data!!)
                        fragmentSearchedBinding.progressBar.visibility = ProgressBar.GONE
                    }
                }

                override fun onFailure(call: Call<ListAnime>, t: Throwable) {
                    val toast = Toast.makeText(context, "Gagal mencari anime", Toast.LENGTH_SHORT)
                    toast.show()
                    fragmentSearchedBinding.progressBar.visibility = ProgressBar.GONE
                }
            })
    }
}