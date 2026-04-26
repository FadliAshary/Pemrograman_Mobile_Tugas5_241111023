package com.example.fadlizuhriputraashary_241111023

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.fadlizuhriputraashary_241111023.databinding.DialogTransactionBinding
import com.example.fadlizuhriputraashary_241111023.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe data dari Repository agar sinkron
        TransactionRepository.transactions.observe(viewLifecycleOwner) {
            updateUI()
        }

        binding.btnTambahDana.setOnClickListener {
            showTransactionDialog(isIncome = true)
        }

        binding.btnTarikDana.setOnClickListener {
            showTransactionDialog(isIncome = false)
        }
    }

    private fun showTransactionDialog(isIncome: Boolean) {
        val dialogBinding = DialogTransactionBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)

        dialogBinding.tvDialogTitle.text = if (isIncome) "Tambah Pemasukan" else "Tarik Pengeluaran"
        if (!isIncome) dialogBinding.tvDialogTitle.setTextColor(android.graphics.Color.parseColor("#D50000"))

        builder.setPositiveButton("Simpan") { _, _ ->
            val amountStr = dialogBinding.etAmount.text.toString()
            val note = dialogBinding.etNote.text.toString().ifEmpty { if (isIncome) "Pemasukan" else "Pengeluaran" }

            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toLong()
                val date = SimpleDateFormat("dd MMM yyyy", Locale("in", "ID")).format(Date())
                val amountFormatted = if (isIncome) "+ Rp %,d".format(amount).replace(',', '.') 
                                    else "- Rp %,d".format(amount).replace(',', '.')

                if (!isIncome && TransactionRepository.getTotalBalance() < amount) {
                    Toast.makeText(requireContext(), "Saldo tidak cukup!", Toast.LENGTH_SHORT).show()
                } else {
                    val newTransaction = Transaction(note, date, amountFormatted, isIncome)
                    TransactionRepository.addTransaction(newTransaction)
                    Toast.makeText(requireContext(), "Transaksi disimpan", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        builder.setNegativeButton("Batal") { d, _ -> d.dismiss() }
        builder.show()
    }

    private fun updateUI() {
        val localeID = Locale("in", "ID")
        val balance = TransactionRepository.getTotalBalance()
        val income = TransactionRepository.getTotalIncome()
        val expense = TransactionRepository.getTotalExpense()

        binding.tvTotalBalance.text = String.format(localeID, "Rp %,d", balance).replace(',', '.')
        binding.tvIncome.text = String.format(localeID, "Rp %,d", income).replace(',', '.')
        binding.tvExpense.text = String.format(localeID, "Rp %,d", expense).replace(',', '.')
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}