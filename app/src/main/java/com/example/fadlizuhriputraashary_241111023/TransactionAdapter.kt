package com.example.fadlizuhriputraashary_241111023

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fadlizuhriputraashary_241111023.databinding.ItemTransactionBinding

data class Transaction(val title: String, val date: String, val amount: String, val isIncome: Boolean)

class TransactionAdapter(
    private val list: List<Transaction>,
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.date
        holder.binding.tvAmount.text = item.amount
        
        if (item.isIncome) {
            holder.binding.tvAmount.setTextColor(android.graphics.Color.parseColor("#00C853"))
        } else {
            holder.binding.tvAmount.setTextColor(android.graphics.Color.parseColor("#D50000"))
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}