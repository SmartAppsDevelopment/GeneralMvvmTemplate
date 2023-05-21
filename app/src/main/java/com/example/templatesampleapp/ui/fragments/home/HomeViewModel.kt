package com.example.templatesampleapp.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templatesampleapp.model.network.quotes.RemoteQuoteData
import com.example.templatesampleapp.model.network.quotetype.QuotesType
import com.example.templatesampleapp.model.ui.UiQuoteModel
import com.example.templatesampleapp.repo.DatabaseHelper
import com.example.templatesampleapp.repo.QuotesAppDb
import com.example.templatesampleapp.repo.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 5/18/2023 at 9:28 PM
 */

@HiltViewModel
class CommonViewModel @Inject constructor(
    val existingdb: DatabaseHelper,
    val quotesRepository: QuotesRepository,
    val localDb: QuotesAppDb
) : ViewModel() {

    private var _mainUiEvents = Channel<MainUiEventsGeneral>()
    var mainUiEvents = _mainUiEvents.receiveAsFlow()

    private var _getQuotesType = MutableLiveData<List<QuotesType>>()
    var getQuotesType:LiveData<List<QuotesType>> = _getQuotesType

    private var _getRemoteQuotesData = MutableLiveData<List<RemoteQuoteData>>()
    var getRemoteQuotesData:LiveData<List<RemoteQuoteData>> = _getRemoteQuotesData

     var sharedUIQuoteModel = MutableLiveData<List<UiQuoteModel>>()



    fun getQuotesType()=viewModelScope.launch {
        _mainUiEvents.send(MainUiEventsGeneral.ShowProgressDialog)
        quotesRepository.getListOfQuotesType().onSuccess {
            _mainUiEvents.send(MainUiEventsGeneral.HideProgressDialog)
            _getQuotesType.postValue(it)
        }.onFailure {
            _mainUiEvents.send(MainUiEventsGeneral.HideProgressDialog)
            Log.e("TAG", "getDataFromDb: Fail ")
            _mainUiEvents.send(MainUiEventsGeneral.Error(it))
        }

    }

    fun getQuotesList(type:String,page:String) = viewModelScope.launch {
        _mainUiEvents.send(MainUiEventsGeneral.ShowProgressDialog)

        quotesRepository.getQuotesByType(type, page).onSuccess {
            _mainUiEvents.send(MainUiEventsGeneral.HideProgressDialog)
            _getRemoteQuotesData.postValue(it)
        }.onFailure {
            _mainUiEvents.send(MainUiEventsGeneral.HideProgressDialog)
            Log.e("TAG", "getDataFromDb: Fail ")
            _mainUiEvents.send(MainUiEventsGeneral.Error(it))
        }
    }



    sealed class MainUiEventsGeneral {
        data class Error(val msg: String) : MainUiEventsGeneral()
        object ShowProgressDialog : MainUiEventsGeneral()
        object HideProgressDialog : MainUiEventsGeneral()
    }

}