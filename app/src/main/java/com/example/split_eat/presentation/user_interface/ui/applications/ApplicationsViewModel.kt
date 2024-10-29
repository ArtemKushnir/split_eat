package com.example.split_eat.presentation.user_interface.ui.applications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApplicationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ВАНЯ ПОХ ВОНЯЕТ ОЧЕНЬ СИЛЬНО"
    }
    val text: LiveData<String> = _text
}