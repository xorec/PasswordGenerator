package com.example.passwordgenerator.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/* В приложении есть два типа viewholder с аналогичным поведением, поэтому удобно сделать общий класс. */
abstract class ListItemViewHolder<T>(protected open val binding: ViewDataBinding, protected val list: ArrayList<T>): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(position: Int)
    abstract fun remove()
}