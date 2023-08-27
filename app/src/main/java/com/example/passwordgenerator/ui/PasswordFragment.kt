package com.example.passwordgenerator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.PasswordFragmentBinding
import com.example.passwordgenerator.model.ALL_PASSWORDS_EXPORT_KEY
import com.example.passwordgenerator.ui.adapter.*

const val PASSWORD_FRAGMENT_ARG = "LIST_NAME"

/* Экран отображения паролей из папки. Может отображать как пароли из определенной папки, так и
*  все пароли. */
class PasswordFragment : BaseFragment() {
    override lateinit var binding: PasswordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.password_fragment, container, false)

        val listName = arguments!!.getString(PASSWORD_FRAGMENT_ARG)

        binding.passwordFragmentTb.title = if (listName != null) {
            arguments!!.getString(PASSWORD_FRAGMENT_ARG)
        } else getString(R.string.password_fragment_all_passwords)

        PasswordsAdapter().let { adapter ->
            viewModel.getPasswords(listName).observe(viewLifecycleOwner) { listNames ->
                adapter.setData(listNames)
            }
            binding.passwordFragmentRv.adapter = adapter
        }

        ItemTouchHelper(SwipeCallback()).attachToRecyclerView(binding.passwordFragmentRv)

        binding.passwordFragmentTb.setNavigationOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.passwordFragmentTb.setOnMenuItemClickListener {
            when (it.itemId)  {
                R.id.password_fragment_menu_export -> {
                    if (listName == null) {
                        (activity as MainActivity).exportData(ALL_PASSWORDS_EXPORT_KEY)
                    } else (activity as MainActivity).exportData(listName)

                    true
                }
                else -> {
                    false
                }
            }
        }

        return binding.root
    }
}