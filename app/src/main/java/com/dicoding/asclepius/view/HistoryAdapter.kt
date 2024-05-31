package com.dicoding.asclepius.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.Analysis
import com.dicoding.asclepius.databinding.ItemHistoryBinding
import java.text.DecimalFormat

class HistoryAdapter: ListAdapter<Analysis, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val analysis = getItem(position)
        holder.bind(analysis)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(analysis) }
    }
    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(analysis: Analysis){
            val scoreInPercent = DecimalFormat("##%").format(analysis.score)
            val imageUri = Uri.parse(analysis.imageUri)
            binding.ivImage.setImageURI(imageUri)
            binding.tvLabel.text = analysis.label
            binding.tvScore.text = scoreInPercent
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Analysis>() {
            override fun areItemsTheSame(oldItem: Analysis, newItem: Analysis): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Analysis, newItem: Analysis): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Analysis)
    }
}