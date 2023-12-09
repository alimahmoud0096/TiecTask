package com.smart4apps.gopathontask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import com.smart4apps.gopathontask.databinding.ItemConvertedHistoryBinding
import javax.inject.Inject

class ConvertedHistoryAdapter @Inject constructor(
) : RecyclerView.Adapter<ConvertedHistoryAdapter.DataViewHolder>() {

    private var convertHistories: ArrayList<ConvertHistory> = ArrayList()

    class DataViewHolder(val itemViewBinding: ItemConvertedHistoryBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(convertHistory: ConvertHistory) {
            itemViewBinding.base.text = convertHistory.from
            itemViewBinding.keyCountry.text = convertHistory.to
            itemViewBinding.value.text = convertHistory.ratio

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemConvertedHistoryBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun getItemCount(): Int = convertHistories.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(convertHistories[position])

    fun addData(convertHistories: List<ConvertHistory>) {
        this.convertHistories.apply {
            clear()
            addAll(convertHistories)
        }
    }

}