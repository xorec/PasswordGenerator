package com.example.passwordgenerator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.GeneratorFragmentBinding

/* Экран генерации паролей. Здесь можно поменять настройки и нажать кнопки, чтобы перейти в другие
*  части приложения. */
class GeneratorFragment : BaseFragment() {
    override lateinit var binding: GeneratorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.generator_fragment, container, false)
        binding.viewmodel = viewModel

        /* Результат смены настроек может быть как положительный, так и отрицательный.
        *  Подробнее см. updatePreferences(). */
        binding.generatorFragmentSavePrefs.setOnClickListener {
            val result = viewModel.updatePreferences(binding.generatorFragmentVocabularyEt.text.toString(),
                binding.generatorFragmentLengthEt.text.toString())
            if (!result.first) {
                Toast.makeText(context, getString(R.string.generator_fragment_error), LENGTH_SHORT).show()
            } else {
                binding.generatorFragmentVocabularyEt.setText(result.second)
                Toast.makeText(context, getString(R.string.generator_fragment_prefs_saved), LENGTH_SHORT).show()
            }
        }

        binding.generatorFragmentArchive.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_generatorFragment_to_listFragment)
        }

        binding.generatorFragmentGenerate.setOnClickListener {
            viewModel.generatePassword()
            Toast.makeText(context, getString(R.string.generator_fragment_password_generated), LENGTH_SHORT).show()
        }

        binding.generatorFragmentImport.setOnClickListener {
            (activity as MainActivity).importData()
        }

        return binding.root
    }
}