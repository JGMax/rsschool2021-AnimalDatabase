package gortea.jgmax.animalstorage.ui.navigation

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun openFragment(fragment: Fragment, addToBackStack: Boolean = true)
}