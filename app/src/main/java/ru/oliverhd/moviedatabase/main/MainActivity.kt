package ru.oliverhd.moviedatabase.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.mainfragment.MainFragment
import ru.oliverhd.moviedatabase.movielist.MovieListFragment
import ru.oliverhd.moviedatabase.settings.SettingsFragment
import ru.oliverhd.moviedatabase.utils.FAVORITE
import ru.oliverhd.moviedatabase.utils.HISTORY

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, MainFragment.newInstance())
                .commitNow()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, MainFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                R.id.navigation_favorite -> supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, MovieListFragment.newInstance(FAVORITE))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                R.id.navigation_search ->
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.flFragment, MovieListFragment.newInstance(HISTORY))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                R.id.navigation_account -> supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, SettingsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        val searchViewMenu = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchViewMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, MovieListFragment.newInstance(query))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                searchViewMenu.isIconified = true
                searchViewMenu.isIconified = true
                searchViewMenu.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, SettingsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.flFragment, MovieListFragment.newInstance(HISTORY))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}