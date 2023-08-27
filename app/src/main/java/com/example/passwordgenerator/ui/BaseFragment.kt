package com.example.passwordgenerator.ui

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.passwordgenerator.viewmodel.MainActivityViewModel

/* Класс, от которого наследуются все фрагменты приложения. Все фрагменты
*  приложения в качестве viewmodel используют viewmodel MainActivity, а также имеют binding */
abstract class BaseFragment: Fragment() {
    protected val viewModel: MainActivityViewModel by activityViewModels()
    protected abstract val binding: ViewDataBinding
}