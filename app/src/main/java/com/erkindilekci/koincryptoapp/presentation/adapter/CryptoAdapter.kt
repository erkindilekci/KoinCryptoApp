package com.erkindilekci.koincryptoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erkindilekci.koincryptoapp.databinding.CryptoItemBinding
import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto

class CryptoAdapter(
    private val cryptoList: ArrayList<CryptoDto>,
    private val listener: Listener
) : RecyclerView.Adapter<CryptoAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoDto)
    }

    class RowHolder(val binding: CryptoItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding =
            CryptoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }
        holder.binding.cryptoNameText.text = cryptoList[position].currency
        holder.binding.cryptoPriceText.text = cryptoList[position].price
    }
}
