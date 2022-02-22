package com.example.earthquake_app.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.earthquake_app.DB.ApiResponseStatus
import com.example.earthquake_app.DB.getDatabase
import com.example.earthquake_app.Earthquake
import kotlinx.coroutines.*
import java.net.UnknownHostException


private val tag = MainViewModel::class.java.simpleName

/*
Normalmente el viewmodel inherita la clase ViewModel()
sin embargo es necesario el AndroidViewModel() porque necesitamos el
contexto de las activities para acceder a la db
 */


class MainViewModel (application: Application, private val sortType : Boolean ): AndroidViewModel (application) {


    //Return live data to main activity from repository


    private val database = getDatabase(application)
    private val repository = MainRepository(database)


    //Obteniendo el status de la api llamando al objeto "ApiResponseStatus" que fue declarado en "ApiResponseStatus.kt"
    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status


    private var _eqlist = MutableLiveData<MutableList<Earthquake>>()
    val eqlist: LiveData<MutableList<Earthquake>>
        get() = _eqlist

    // This will run when mainViewModel is callled
    init {
        fetchearthquakesdb(sortType)
    }

    fun reload_eq(sortType: Boolean) {

        viewModelScope.launch {
            /*
            Para que la app siga funcionando si tenemos internet implementamos un try:
             */

            try {

                _status.value = ApiResponseStatus.LOADING
                _eqlist.value = repository.fetchEarthquakes(sortType)
                _status.value = ApiResponseStatus.DONE

            } catch (e: UnknownHostException) {
                Log.d(tag, "Sin conexion a internet")
                _status.value = ApiResponseStatus.ERROR
            }

        }

    }

    fun fetchearthquakesdb(sortbymagnitude: Boolean) {

        viewModelScope.launch {

            _eqlist.value = repository.fetchearthquakesdb(sortbymagnitude)
            if(_eqlist.value!!.isEmpty()){
                reload_eq(sortbymagnitude)
            }
        }


    }




}
