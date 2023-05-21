package com.example.templatesampleapp.ui.activmain

import androidx.lifecycle.MutableLiveData
import com.example.templatesampleapp.base.BaseViewModel
import com.example.templatesampleapp.model.SampleItem
import com.example.templatesampleapp.repo.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val  quotesRepository: QuotesRepository) : BaseViewModel() {

    var listOfItems:MutableLiveData<List<SampleItem>> = MutableLiveData(emptyList())
         fun getData():MutableLiveData<List<SampleItem>>   {
             listOfItems.value=
             return  listOfItems
//             MutableLiveData<List<SampleItem>>().apply {
//
//             }
         }
//             flow<List<SampleItem>> {
//
//                emit(listOfItems)
//            }.toList()

}