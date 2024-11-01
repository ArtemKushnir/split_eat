package com.example.split_eat.presentation.user_interface.ui.personalAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalAccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ТУТ ЛИЧНЫЙ КАБИНЕТ ЕБАТЬ"
    }
    val text: LiveData<String> = _text
}