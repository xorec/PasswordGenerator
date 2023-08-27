package com.example.passwordgenerator.ui.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.passwordgenerator.PasswordGeneratorApplication
import com.example.passwordgenerator.databinding.PasswordItemBinding
import com.example.passwordgenerator.model.db.Password

class PasswordsAdapter: ListAdapter<Password>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder<Password> {
        val binding = PasswordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.passwordItemCopy.setOnClickListener {
            (ContextCompat.getSystemService(
                PasswordGeneratorApplication.instance.applicationContext,
                ClipboardManager::class.java
            ) as ClipboardManager)
                .setPrimaryClip(
                    ClipData.newPlainText("", binding.passwordItemTv.text))
        }

        return PasswordViewHolder(binding, list)
    }
}