package com.example.fadlizuhriputraashary_241111023

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fadlizuhriputraashary_241111023.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title") ?: "N/A"
        val amount = arguments?.getString("amount") ?: "N/A"
        val date = arguments?.getString("date") ?: "N/A"

        binding.tvTransactionName.text = title
        binding.tvTransactionAmount.text = amount
        binding.tvTransactionDate.text = date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}