package com.erkindilekci.koincryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.erkindilekci.koincryptoapp.presentation.adapter.CryptoAdapter
import com.erkindilekci.koincryptoapp.databinding.FragmentListBinding
import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(), CryptoAdapter.Listener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var adapter = CryptoAdapter(arrayListOf(), this)

    private val viewModel by viewModel<ListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        viewModel.getDataFromApi()

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer { cryptoList ->
            binding.errorText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter = CryptoAdapter(ArrayList(cryptoList.data ?: arrayListOf()), this@ListFragment)
            binding.recyclerView.adapter = adapter
        })

        viewModel.showError.observe(viewLifecycleOwner, Observer { showError ->
            if (showError.data == true) {
                binding.errorText.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.INVISIBLE
            } else {
                binding.errorText.visibility = View.GONE
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading.data == true) {
                binding.errorText.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoDto) {
        Toast.makeText(
            requireContext(),
            "${cryptoModel.currency} clicked.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
