package com.namnguyen.weather.base

import androidx.lifecycle.ViewModel
import com.namnguyen.weather.ext.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    val progressDialogEvent = SingleLiveData<Boolean>()

    open fun showProgress() {
        progressDialogEvent.postValue(true)
    }

    open fun hideProgress() {
        progressDialogEvent.postValue(false)
    }

}
