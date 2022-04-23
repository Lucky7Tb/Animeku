package com.lucky.animeku.ui.searched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucky.animeku.databinding.FragmentSearchedBinding
import com.lucky.animeku.db.AnimeDb
import com.lucky.animeku.model.DataAnime
import com.lucky.animeku.model.ListAnime
import com.lucky.animeku.network.AnimeApiInterface
import com.lucky.animeku.network.Api
import com.lucky.animeku.ui.top.TopViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchedFragment : Fragment() {
    private lateinit var fragmentSearchedBinding: FragmentSearchedBinding
    private lateinit var searchFragmentAdapter: SearchedAdapter

    private val viewModel: SearchedViewModel by lazy {
        val db = AnimeDb.getInstace(requireContext())
        val factory = SearchedViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[SearchedViewModel::class.java]
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
        fragmentSearchedBinding.searchButton.setOnClickListener { onClickSearchAnime() }
        return fragmentSearchedBinding.root
    }

    private fun initRecyclerView() {
        searchFragmentAdapter = SearchedAdapter(arrayListOf(), object : SearchedAdapter.OnFavoriteButtonClick {
            override fun onItemClicked(dataAnime: DataAnime) {
                viewModel.addToFavorite(dataAnime)
                val toast = Toast.makeText(context, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT)
                toast.show()
            }
        })

        with(fragmentSearchedBinding.listTopAnime) {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchFragmentAdapter
        }
    }

    private fun onClickSearchAnime() {
        val keyword: String = fragmentSearchedBinding.searchField.text.toString()
        if (keyword.isEmpty() || keyword.isBlank()) {
            val toast = Toast.makeText(context, "Keyword anime tidak boleh kosong", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            searchAnime()
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