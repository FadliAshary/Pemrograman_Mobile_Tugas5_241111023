package com.example.fadlizuhriputraashary_241111023

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object TransactionRepository {
    private val _transactions = MutableLiveData<MutableList<Transaction>>(mutableListOf(
        Transaction("Gaji Bulanan", "01 Okt 2023", "+ Rp 5.000.000", true),
        Transaction("Makan Siang", "02 Okt 2023", "- Rp 25.000", false)
    ))
    val transactions: LiveData<MutableList<Transaction>> = _transactions

    fun addTransaction(transaction: Transaction) {
        val currentList = _transactions.value ?: mutableListOf()
        currentList.add(0, transaction) // Tambah di paling atas
        _transactions.value = currentList
    }

    fun getTotalBalance(): Long {
        var balance = 0L
        _transactions.value?.forEach {
            val amount = it.amount.replace("[^0-9]".toRegex(), "").toLong()
            if (it.isIncome) balance += amount else balance -= amount
        }
        return balance
    }

    fun getTotalIncome(): Long {
        return _transactions.value?.filter { it.isIncome }?.sumOf { 
            it.amount.replace("[^0-9]".toRegex(), "").toLong() 
        } ?: 0L
    }

    fun getTotalExpense(): Long {
        return _transactions.value?.filter { !it.isIncome }?.sumOf { 
            it.amount.replace("[^0-9]".toRegex(), "").toLong() 
        } ?: 0L
    }
}