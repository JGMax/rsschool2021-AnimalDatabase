package gortea.jgmax.animalstorage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.ui.fragments.AnimalListFragment
import gortea.jgmax.animalstorage.ui.fragments.SettingsFragment
import gortea.jgmax.animalstorage.ui.navigation.ActionBarController
import gortea.jgmax.animalstorage.ui.navigation.FragmentNavigator

class MainActivity : AppCompatActivity(), FragmentNavigator, ActionBarController {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openFragment(AnimalListFragment(), addToBackStack = false)
        }
    }

    override fun openFragment(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.commit {
            replace(R.id.container, fragment)
            if(addToBackStack) addToBackStack(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_item ->  {
                openFragment(SettingsFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setActionBarVisibility(visibility: Boolean) {
        if (visibility) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }
}