package com.lucky.animeku.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.lucky.animeku.databinding.FragmentFavoriteBinding
import com.lucky.animeku.db.AnimeDb
import com.lucky.animeku.db.AnimeEntity

class FavoriteFragment : Fragment() {
    private lateinit var fragmentFavoritBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteViewModel by lazy {
        val db = AnimeDb.getInstace(requireContext())
        val factory = FavoriteViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoritBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentFavoritBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoriteAdapter = FavoriteAdapter(object : FavoriteAdapter.OnDeleteFavoriteButtonClick {
            override fun onItemClick(id: Long) {
                viewModel.deleteFavoriteAnime(id)
            }
        })
        with(fragmentFavoritBinding.favoriteRecyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        viewModel.favoriteAnimeData.observe(viewLifecycleOwner) {
            fragmentFavoritBinding.emptyTextView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            favoriteAdapter.submitList(it)
        }
    }

}