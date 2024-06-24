package com.example.prm2.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prm2.model.Entry

fun <GenericViewModel : ViewModel> viewModelFactory(init: () -> GenericViewModel): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val viewModel = init()
            if (modelClass.isInstance(viewModel))
                return modelClass.cast(viewModel)!!
            else
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
