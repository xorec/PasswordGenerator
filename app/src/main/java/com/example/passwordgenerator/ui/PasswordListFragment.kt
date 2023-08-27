package com.example.passwordgenerator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.passwordgenerator.*
import com.example.passwordgenerator.databinding.PasswordListFragmentBinding
import com.example.passwordgenerator.databinding.PasswordListItemBinding
import com.example.passwordgenerator.ui.adapter.*

/* Экран списков паролей. Отображает как списки паролей, так и сгенерированные пароли. По заданию при
*  экспорте с этого экрана необходимо экспортировать сгенерированные пароли */
class PasswordListFragment : BaseFragment() {
    override lateinit var binding: PasswordListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.password_list_fragment, container, false)

        object: ListAdapter<String>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ListItemViewHolder<String> {
                val binding = PasswordListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                binding.passwordListNameCv.setOnClickListener {
                    Navigation.findNavController(it).navigate(
                        R.id.action_listFragment_to_passwordFragment, bundleOf(Pair(
                            PASSWORD_FRAGMENT_ARG, binding.passwordListNameTv.text))
                    )
                }

                return PasswordListViewHolder(binding, list)
            }
        }.let { adapter ->
            viewModel.listNames.observe(viewLifecycleOwner) { listNames ->
                adapter.setData(listNames)
            }
            binding.passwordListFragmentListsRv.adapter = adapter
        }

        ItemTouchHelper(SwipeCallback()).attachToRecyclerView(binding.passwordListFragmentListsRv)

        PasswordsAdapter().let { adapter ->
            viewModel.getPasswords(getString(R.string.generated_passwords_list_name)).observe(viewLifecycleOwner) { listNames ->
                adapter.setData(listNames)
            }
            binding.passwordListFragmentGeneratedRv.adapter = adapter
        }

        ItemTouchHelper(SwipeCallback()).attachToRecyclerView(binding.passwordListFragmentGeneratedRv)

        binding.passwordListFragmentTb.setNavigationOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.passwordListFragmentTb.setOnMenuItemClickListener { it ->
            when (it.itemId)  {
                R.id.password_list_fragment_menu_see_all -> {
                    Navigation.findNavController(view!!).navigate(
                        R.id.action_listFragment_to_passwordFragment,
                    bundleOf(Pair(PASSWORD_FRAGMENT_ARG, null))
                    )
                    true
                }
                R.id.password_list_fragment_menu_export -> {
                    (activity as MainActivity).exportData(getString(R.string.generated_passwords_list_name))
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