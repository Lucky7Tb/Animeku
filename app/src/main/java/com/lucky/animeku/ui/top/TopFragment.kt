package com.lucky.animeku.ui.top

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
import com.lucky.animeku.databinding.FragmentTopBinding
import com.lucky.animeku.model.ListAnime
import com.lucky.animeku.network.AnimeApiInterface
import com.lucky.animeku.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [TopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopFragment : Fragment() {
    private var _binding: FragmentTopBinding? = null
    private val binding get() = _binding!!
    private lateinit var topFragmentAdapter: TopFragmentAdapter
    private var page: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = 1
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        getTopAnime()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        topFragmentAdapter = TopFragmentAdapter(arrayListOf())

        with(binding.listTopAnime) {
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = topFragmentAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        page += 1
                        getTopAnime()
                    }
                }
            })
        }
    }

    private fun getTopAnime() {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        val animeApi: AnimeApiInterface = Api.getInstance().create(AnimeApiInterface::class.java)
        animeApi.topAnime(page)
            .enqueue(object : Callback<ListAnime> {
                override fun onResponse(
                    call: Call<ListAnime>,
                    response: Response<ListAnime>
                ) {
                    val data = response.body()
                    if (response.isSuccessful) {
                        topFragmentAdapter.setData(data!!.data!!)
                        binding.progressBar.visibility = ProgressBar.GONE
                    }
                }

                override fun onFailure(call: Call<ListAnime>, t: Throwable) {
                    val toast = Toast.makeText(context, "Gagal mengambil data top anime", Toast.LENGTH_SHORT)
                    toast.show()
                    binding.progressBar.visibility = ProgressBar.GONE
                }
            })
    }

}