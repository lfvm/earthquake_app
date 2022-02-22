package com.example.earthquake_app.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.earthquake_app.Earthquake

/*
En este archivo se creara la base de datos para
guardar los terremotos
 */

/*
Se crea una clase abstracta en la que depemos especificarle cuales son
las entities o tablas que se usaran.

Inicia con la version 1 pero cada vez que se quite o se agregue alguna columna
o tabla la version debera de aumentar para que se reflejen los cambios
 */
@Database(entities = [Earthquake::class],version = 1)
abstract class eqDatabase: RoomDatabase(){

    abstract val eqDao: EqDao
}

/*
El siguiente codigo permite acceder a la Db es un codigo muy repetitivo
que se reutilizara en casi toodos los proyectos
 */

private lateinit var INSTANCE: eqDatabase
fun getDatabase(context: Context): eqDatabase {
    synchronized(eqDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                eqDatabase::class.java,
                "earthquake_db" //Nombre de la base de datos
            ).build()
        }
        return INSTANCE
    }
}