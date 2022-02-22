package com.example.earthquake_app.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/*
Codigo repetitivo

se utiliza para crear un constructor para el viewmodel

en este caso sera para  poder pasarle un contexto al mainviewmodel con "application" para poder usar la db

y tambien se utilizara para pasar un valor de lass shared preferences, en este caso es la variable sortType y es de tipo boolean
 */
class ViewModelFactory (private val application: Application, private val sortType : Boolean):

    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MainViewModel(application,sortType) as T
    }
    }