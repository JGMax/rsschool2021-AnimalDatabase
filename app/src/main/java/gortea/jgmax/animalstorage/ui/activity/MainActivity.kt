package gortea.jgmax.animalstorage.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.ui.fragments.AnimalListFragment
import gortea.jgmax.animalstorage.ui.navigation.FragmentNavigator

class MainActivity : AppCompatActivity(), FragmentNavigator {
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
}