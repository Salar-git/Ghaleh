package ir.iamsalar.ghaleh.view.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import ir.iamsalar.ghaleh.App
import ir.iamsalar.ghaleh.R
import ir.iamsalar.ghaleh.databinding.FragmentCategorySelectionBinding
import ir.iamsalar.ghaleh.viewmodel.CategorySelectionViewModel
import ir.iamsalar.ghaleh.viewmodel.CategorySelectionViewModelFactory
import kotlinx.coroutines.launch


class CategorySelectionFragment : Fragment() {


    private var _binding: FragmentCategorySelectionBinding? = null
    val binding get() = _binding!!

    private val viewModel: CategorySelectionViewModel by viewModels {
        CategorySelectionViewModelFactory((requireActivity().application as App).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategorySelectionBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycle.coroutineScope.launch {
                viewModel.getAllCategory().collect() {
                    viewModel.myCategories = it
                    tvSize.text = "${it.size}"
                    if (it.size >= 5) {
                        btnContinue.isEnabled = true
                        cpbCategoryList.setProgressWithAnimation(100f, 500)
                    } else {
                        btnContinue.isEnabled = false
                        cpbCategoryList.setProgressWithAnimation((it.size * 20f), 500)
                    }

                    chipGroupMyCategories.removeAllViews()
                    it?.forEach {
                        val item = it
                        val chip: Chip = Chip(requireActivity())
                        chip.text = item.name
                        chip.isCloseIconVisible = true;
                        chip.closeIcon =
                            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_remove)
                        chip.closeIconTint =
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.red
                                )
                            )
                        chip.setOnCloseIconClickListener {
                            viewModel.removeCategory(item)
                        }
                        chipGroupMyCategories.addView(chip)


                    }
                }
            }
            viewModel.categoriesResponse.observe(viewLifecycleOwner, Observer {
                ContentDiscoveryFragment.allCategories = it
                it?.forEach {
                    val item = it
                    val chip: Chip = Chip(requireActivity())
                    chip.text = item
                    chip.isCloseIconVisible = true;
                    chip.closeIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_add)
                    chip.closeIconTint =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.main
                            )
                        )
                    chip.setOnCloseIconClickListener {
                        viewModel.addCategory(item)
                    }
                    binding.chipGroupCategories.addView(chip)

                }
            })
            btnContinue.setOnClickListener {
                ContentDiscoveryFragment.myCategories = viewModel.myCategories
                findNavController().navigate(CategorySelectionFragmentDirections.actionCategorySelectionFragmentToContentDiscoveryFragment())

            }

        }

    }


}