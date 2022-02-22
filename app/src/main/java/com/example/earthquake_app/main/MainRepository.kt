package com.example.earthquake_app.main

import androidx.lifecycle.LiveData
import androidx.room.Database
import com.example.earthquake_app.DB.eqDatabase
import com.example.earthquake_app.Earthquake
import com.example.earthquake_app.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/*
Debido a que la db necesita un contexto y solo las activities
lo tienen, pasaremos dicho contexto como constructor en la clase
del repositorio
 */

class MainRepository (private val database: eqDatabase) {


    //Funcion para descargar datos de internet
    suspend fun fetchEarthquakes(sortbymagnitude : Boolean) : MutableList<Earthquake>{
        //Funcion que corre en segundo plano
        return withContext(Dispatchers.IO) {

            val eqstring = service.GetEarthquakes()
            val eqListString = parseEqResult(eqstring)


            //Guardar la lista en la base de datos local
            database.eqDao.insertAll(eqListString)


            fetchearthquakesdb(sortbymagnitude)

        }
    }

    //Funcion para optener los teremmotos desde la base de datos
    suspend fun fetchearthquakesdb(sortbymagnitude: Boolean) : MutableList<Earthquake>  {
        return withContext(Dispatchers.IO) {

            if (sortbymagnitude) {

                database.eqDao.getEarthquakesbymagnitude()
            } else {
                database.eqDao.getEarthquakes()

            }

        }
    }



    private fun parseEqResult(eqListString: String): MutableList<Earthquake> {
        //parsing data
        val eqJsonObject = JSONObject(eqListString)

        val features = eqJsonObject.getJSONArray("features")

        val eqList = mutableListOf<Earthquake>()
        for (i in 0 until features.length()){

            val featureobject = features[i] as JSONObject

            val id = featureobject.getString("id")
            val properties = featureobject.getJSONObject("properties")
            val magnitude = properties.getDouble("mag")
            val place = properties.getString("place")
            val time = properties.getLong("time")

            val geometry = featureobject.getJSONObject("geometry")
            val coordinate_array = geometry.getJSONArray("coordinates")

            val longitude = coordinate_array.getDouble(0)
            val latitude = coordinate_array.getDouble(1)

            val earthquake = Earthquake(id,place,magnitude,time,longitude,latitude)

            eqList.add(earthquake)

        }
        return eqList

    }
}