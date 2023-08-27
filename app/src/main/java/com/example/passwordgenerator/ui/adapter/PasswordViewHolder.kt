package com.example.passwordgenerator.ui.adapter

import com.example.passwordgenerator.PasswordGeneratorApplication
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.PasswordItemBinding
import com.example.passwordgenerator.model.db.Password
import com.example.passwordgenerator.model.db.PasswordGrade

class PasswordViewHolder(binding: PasswordItemBinding, list: ArrayList<Password>): ListItemViewHolder<Password>(binding, list) {
    override val binding: PasswordItemBinding = binding

    override fun bind(position: Int) {
        binding.passwordItemTv.text = list[position].password
        binding.passwordItemListName.text = list[position].listName
        binding.passwordItemSymbols.text = list[position].vocabulary
        binding.passwordItemEntropy.text = String.format("%.2f", list[position].entropy)

        val context = PasswordGeneratorApplication.instance.applicationContext

        when(list[position].grade) {
            PasswordGrade.PASSWORD_GRADE_POOR -> {
                binding.passwordItemGrade.text = context.getString(
                    R.string.password_grade_poor)
                binding.passwordItemGrade.setTextColor(context.resources.getColor(R.color.dark_red, context.theme))
            }
            PasswordGrade.PASSWORD_GRADE_WEAK -> {
                binding.passwordItemGrade.text = context.getString(
                    R.string.password_grade_weak)
                binding.passwordItemGrade.setTextColor(context.resources.getColor(R.color.red, context.theme))
            }
            PasswordGrade.PASSWORD_GRADE_REASONABLE -> {
                binding.passwordItemGrade.text = context.getString(
                    R.string.password_grade_reasonable)
                binding.passwordItemGrade.setTextColor(context.resources.getColor(R.color.yellow, context.theme))
            }
            PasswordGrade.PASSWORD_GRADE_GOOD -> {
                binding.passwordItemGrade.text = context.getString(
                    R.string.password_grade_good)
                binding.passwordItemGrade.setTextColor(context.resources.getColor(R.color.green, context.theme))
            }
        }

    }

    override fun remove() {
        PasswordGeneratorApplication.instance.passwords.removePassword(list[adapterPosition])
    }
}