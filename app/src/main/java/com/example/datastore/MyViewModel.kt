package com.example.datastore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val datastoreUtil: DatastoreUtil
) : ViewModel() {

    var name = mutableStateOf("Dave")
    var age = mutableStateOf("1")

    fun getName() = viewModelScope.launch {
        datastoreUtil.name.collect {
            name.value = it
        }
    }

    fun getAge() = viewModelScope.launch {
        datastoreUtil.age.collect {
            age.value = it.toString()
        }
    }

}