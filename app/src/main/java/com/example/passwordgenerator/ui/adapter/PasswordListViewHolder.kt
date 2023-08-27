package com.example.passwordgenerator.ui.adapter

import com.example.passwordgenerator.PasswordGeneratorApplication
import com.example.passwordgenerator.databinding.PasswordListItemBinding

class PasswordListViewHolder(binding: PasswordListItemBinding, list: ArrayList<String>): ListItemViewHolder<String>(binding, list) {
    override val binding: PasswordListItemBinding = binding

    override fun bind(position: Int) {
        binding.passwordListNameTv.text = list[position]
    }

    override fun remove() {
        PasswordGeneratorApplication.instance.passwords.removeList(list[adapterPosition])
    }
}