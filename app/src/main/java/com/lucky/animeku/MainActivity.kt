package com.lucky.animeku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.lucky.animeku.databinding.ActivityMainBinding
import com.lucky.animeku.ui.favorite.FavoriteFragment
import com.lucky.animeku.ui.searched.SearchedFragment
import com.lucky.animeku.ui.top.TopFragment

class MainActivity : AppCompatActivity() {
    private lateinit var topAnimeFragment: TopFragment
    private lateinit var seachedAnimeFragment: SearchedFragment
    private lateinit var favoriteAnimeFragment: FavoriteFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topAnimeFragment = TopFragment()
        seachedAnimeFragment = SearchedFragment()
        favoriteAnimeFragment = FavoriteFragment()
        changeFragment(topAnimeFragment)

        binding.bottomNavigation.setOnItemSelectedListener { changeView(it) }
    }

    private fun changeView(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.top_anime_menu -> {
                changeFragment(topAnimeFragment)
                return true
            }
            R.id.searched_anime_menu -> {
                changeFragment(seachedAnimeFragment)
                return true
            }
            else -> {
                changeFragment(favoriteAnimeFragment)
                return true
            }
        }

        return false
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()
        }
    }
}
