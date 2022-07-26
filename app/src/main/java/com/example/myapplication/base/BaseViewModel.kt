package com.example.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by msaycon on 17,Jul,2022
 */
abstract class BaseViewModel : ViewModel() {

    var resource: MutableLiveData<Resource<Any>> = MutableLiveData()
}