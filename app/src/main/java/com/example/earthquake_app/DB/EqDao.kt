package com.example.earthquake_app.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.earthquake_app.Earthquake


/*
Dao nos ayudara a manipular la base de datos local de la aplicacion.

en este archivo se escriben las funciones para agregar datos a las tablas
asi como modificarlas.

Importante, para cada tabla o entity debera de haber una interfaz dao
 */

@Dao
interface EqDao {
    /*
    Funcion para insertar data
    en este caso se le inserta una lista que contiene datos del tipo Earthquake

    Insert es la funcion para dar datos, y en caso de que haya un error por insertar un
    dato con el mismo id, se remplazara con el nuevo id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    /*
    Funcion para recibir informacion, nos devolvera la lista de terremotos

    tambien existen los siguientes metodos para la db:

    @Update
    @Delete

     */
    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>

    //Seleccionar terremotos  con una magnitud especifica
    @Query("SELECT * FROM earthquakes WHERE magnitude < :value")
    fun getLowEarthquakes(value : Double): MutableList<Earthquake>


    //Da los terremotos ordenados por magnitud
    @Query("SELECT * FROM earthquakes order by  magnitude ASC ")
    fun getEarthquakesbymagnitude(): MutableList<Earthquake>



}