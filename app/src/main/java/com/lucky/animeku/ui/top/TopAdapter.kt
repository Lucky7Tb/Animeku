package com.lucky.animeku.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lucky.animeku.databinding.FragmentTopItemBinding
import com.lucky.animeku.model.DataAnime

class TopAdapter(
    private var listDataAnime: ArrayList<DataAnime>,
    val listener: OnFavoriteButtonClick
): RecyclerView.Adapter<TopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentTopItemBinding.inflate(inflater, parent, false)
        binding.favoriteButton
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDataAnime[position])
    }

    override fun getItemCount(): Int {
        return listDataAnime.size
    }

    inner class ViewHolder(private val binding: FragmentTopItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: DataAnime) {
            return with(binding) {
                Glide.with(binding.root)
                    .load(anime.images.jpg.large_image_url)
                    .apply(RequestOptions.overrideOf(100, 150))
                    .into(binding.topAnimePoster)
                binding.animeTitle.text = anime.title
                binding.animeRank.text = "Rank: ${anime.rank}"
                binding.animeScore.text = "Score: ${anime.score}"
                binding.favoriteButton.setOnClickListener {
                    listener.onItemClicked(anime)
                }
            }
        }
    }

    fun setData(listAnime: ArrayList<DataAnime>) {
        listDataAnime.addAll(listAnime)
        notifyDataSetChanged()
    }

    interface OnFavoriteButtonClick {
        fun onItemClicked(dataAnime: DataAnime)
    }
}