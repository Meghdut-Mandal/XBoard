package com.xboard.app.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xboard.app.BuildConfig

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ${BuildConfig.DISCORD_KEY} Fragment"
    }
    val text: LiveData<String> = _text
}