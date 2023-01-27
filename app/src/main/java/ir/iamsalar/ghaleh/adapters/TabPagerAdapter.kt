package ir.iamsalar.ghaleh.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.iamsalar.ghaleh.view.fragment.ContetnFragment

class TabPagerAdapter(fragment: Fragment, private val unselectedTabs: List<String>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return unselectedTabs.size + 1
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ContetnFragment("MyCategories")
            }
            else -> {
                ContetnFragment(unselectedTabs.get(position - 1))
            }
        }
    }

}