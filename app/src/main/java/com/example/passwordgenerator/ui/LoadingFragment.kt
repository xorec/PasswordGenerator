package com.example.passwordgenerator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import androidx.navigation.Navigation
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.LoadingFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/* Экран загрузки. Поскольку все данные загружаются асинхронно, не содержит никакой логики. */
class LoadingFragment : BaseFragment() {
    override lateinit var binding: LoadingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.loading_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Чтобы продемонстрировать, что экран загрузки встроен в приложение, сделаем на нем задержку
        *  в 2 секунды, после чего перейдем в GeneratorFragment с помощью action */
        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.IO) {
            delay(2000)
            withContext(Dispatchers.Main) {
                Navigation.findNavController(view).navigate(R.id.action_loadingFragment_to_generatorFragment)
            }
        }
    }
}