package ir.iamsalar.ghaleh.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ir.iamsalar.ghaleh.adapters.ContetnItemAdapter
import ir.iamsalar.ghaleh.databinding.FragmentContetnBinding
import ir.iamsalar.ghaleh.viewmodel.ContentViewModel
import ir.iamsalar.ghaleh.viewmodel.ContentViewModelFactory


class ContetnFragment(private val searchForWhat: String) : Fragment() {


    private var _binding: FragmentContetnBinding? = null
    val binding get() = _binding!!
    private val viewModel: ContentViewModel by viewModels {
        ContentViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContetnBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.clearCategories()
            if (searchForWhat.equals("MyCategories")) {
                ContentDiscoveryFragment.myCategories?.forEach {
                    viewModel.addCategory(it.name)
                }
            } else {
                viewModel.addCategory(searchForWhat)
            }
            viewModel.fetchContents()

            viewModel.contetnResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    val adapter =
                        ContetnItemAdapter(it, viewModel)
                    rcyContetn.adapter = adapter
                  adapter.notifyItemChanged(viewModel.currentPosition)
                }
            })


        }
    }

}