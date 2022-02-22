package com.example.earthquake_app

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/*Para hacer que la clase pueda funcionar con la base de datos local
 debemos de poner la palabra data antes de iniciar, tambien deberemos de
 poner lo siguiente: @Entity(). LLevara de argumento el nombre de la tabla
 de la base de datos

 @PrimaryKey nos ayudara a decirle a la db cual es el atributo que se usara como id
 */
@Parcelize
@Entity(tableName = "earthquakes")
data class Earthquake (@PrimaryKey val  id : String, val place: String, val magnitude : Double, val time: Long, val longitude : Double,
                       val latitude: Double): Parcelable
