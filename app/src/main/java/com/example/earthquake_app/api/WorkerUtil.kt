package com.example.earthquake_app.api

import android.content.Context
import android.content.SyncRequest
import androidx.work.*
import java.util.concurrent.TimeUnit

/*
En el worker util van todas las restricciones y especificaciones que le
daremos al worker manager
en este caso se establesera el tipo de conexion y el perido de tiempo en el que
se hara el trabajo

 */
object WorkerUtil {
    fun scheduleSync(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest =
            PeriodicWorkRequestBuilder<SyncWorkManager>(1,TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP,syncRequest)

    }

}