package ir.iamsalar.ghaleh.view.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ir.iamsalar.ghaleh.R
import ir.iamsalar.ghaleh.adapters.TabPagerAdapter
import ir.iamsalar.ghaleh.data.db.entities.category.Category
import ir.iamsalar.ghaleh.databinding.DialogLogOutBinding
import ir.iamsalar.ghaleh.databinding.FragmentContentDiscoveryBinding
import ir.iamsalar.ghaleh.prefs


class ContentDiscoveryFragment : Fragment() {


    private var _binding: FragmentContentDiscoveryBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContentDiscoveryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tabsHandler(binding)
            btnFilter.setOnClickListener {
                findNavController().navigate(ContentDiscoveryFragmentDirections.actionContentDiscoveryFragmentToCategorySelectionFragment())
            }
            btnLogOut.setOnClickListener {
                openLogOutDialog()
            }
        }
    }

    private fun openLogOutDialog() {

        var dialog = Dialog(requireActivity())


        val dialogBinding: DialogLogOutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                requireActivity()
            ), R.layout.dialog_log_out, null, false
        )
        dialog.setContentView(dialogBinding.root)

        dialog.window!!.setWindowAnimations(R.style.LoginDialogAnimation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialogBinding.apply {

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnSignOut.setOnClickListener {
                dialog.dismiss()
                prefs.setBoolean("login", false)
                prefs.setString("token", "")
                findNavController().navigate(ContentDiscoveryFragmentDirections.actionContentDiscoveryFragmentToOnboardFragment())
            }
        }

        dialog.show()
    }

    private fun tabsHandler(binding: FragmentContentDiscoveryBinding) {
        binding.apply {
            val unselectedCategories: MutableList<String> = mutableListOf()

            allCategories?.forEach {
                val categoryName = it
                var unselected = true

                myCategories?.forEach {
                    if (it.name.equals(categoryName)) {
                        unselected = false
                    }
                }

                if (unselected) {
                    unselectedCategories.add(categoryName)
                }
            }

            unselectedCategories.forEach {
                tabContainer.addTab(tabContainer.newTab().setText("${it}"));
            }
            tabPagerHandler(binding, unselectedCategories)


        }
    }

    private fun tabPagerHandler(binding: FragmentContentDiscoveryBinding, list: List<String>) {
        binding.apply {
            val pagerAdapter = TabPagerAdapter(this@ContentDiscoveryFragment, list)
            pager.adapter = pagerAdapter
            TabLayoutMediator(tabContainer, pager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "For You"
                    }
                    else -> {
                        tab.text = list.get(position - 1)
                    }

                }
                pagerAdapter.createFragment(position)
            }.attach()
            tabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    //viewModel.fragment = tab!!.position

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //  viewModel.fragment = tab!!.position
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // viewModel.fragment = tab!!.position
                }

            })
        }
    }


    companion object {
        var myCategories: List<Category>? = null
        var allCategories: List<String>? = null

    }

}