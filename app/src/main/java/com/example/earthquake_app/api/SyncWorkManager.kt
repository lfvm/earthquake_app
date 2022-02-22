package com.example.earthquake_app.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.earthquake_app.DB.getDatabase
import com.example.earthquake_app.main.MainRepository


/* Esta clase "workmanager" se utilizara para programar eventos periodicos
 En este caso sera para que las descargas de la api solo se realizen cuando
 el celular tenga internet y cada hora
 */


class SyncWorkManager(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext,params) {
    companion object{
        const val WORK_NAME = "SyncWorkManager"
    }

    private val database = getDatabase(appContext)
    private val repository = MainRepository(database)
    override suspend fun doWork(): Result {

        repository.fetchEarthquakes(true)
        return Result.success()
    }

}