package com.example.split_eat.presentation.user_interface.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "тут будут чаты"
    }
    val text: LiveData<String> = _text
}