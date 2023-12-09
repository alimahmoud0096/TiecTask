package com.smart4apps.gopathontask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import com.smart4apps.gopathontask.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    var convertedHistoryListLiv: MutableStateFlow<List<ConvertHistory>> =
        MutableStateFlow(arrayListOf())

    fun getAllConvertedHistory() {
        viewModelScope.launch {
            currencyRepository.getAllConvertedHistory().collectLatest {
                convertedHistoryListLiv.emit(it)
            }
        }
    }

    fun insertConvertHistory(convertHistory: ConvertHistory) {
        viewModelScope.launch {
            currencyRepository.insertConvertHistory(convertHistory)
        }
    }

//    fun deleteUser(user: User) {
//        viewModelScope.launch {
//            userRepository.deleteUser(user)
//        }
//    }
}