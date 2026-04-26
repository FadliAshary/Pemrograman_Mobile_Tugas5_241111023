package com.example.fadlizuhriputraashary_241111023

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fadlizuhriputraashary_241111023.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        // Mengamati data dari Repository secara real-time
        TransactionRepository.transactions.observe(viewLifecycleOwner) { transactions ->
            binding.recyclerView.adapter = TransactionAdapter(transactions) { transaction ->
                val bundle = bundleOf(
                    "title" to transaction.title,
                    "amount" to transaction.amount,
                    "date" to transaction.date
                )
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}