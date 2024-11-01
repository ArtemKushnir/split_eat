package com.example.split_eat.presentation.user_interface.ui.newApplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewApplicationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "создание новой заявки"
    }
    val text: LiveData<String> = _text
}