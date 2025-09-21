package com.recipeapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.recipeapp.ui.screens.recipeDetails.model.EnumTime
import com.recipeapp.worker.LoadRecipeWorker
import com.recipeapp.worker.ReminderWorker
import com.recipeapp.worker.WorkManagerHelper
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManagerHelper: WorkManagerHelper

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        loadRecipes()
    }

    private fun loadRecipes() {
        // Enqueue worker on app launch
        val request = LoadRecipeWorker.get()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "load_recipe_on_launch",  // unique name
            ExistingWorkPolicy.REPLACE, // replace if already running
            request
        )
    }

    fun setNotifyReminder(recipeName: String, time: EnumTime) {
        val workData = workDataOf(
            "title" to "Time to Cook $recipeName"
        )

        val delayMinutes: Long = when (time) {
            EnumTime.ThirtyMinutes -> 30L
            EnumTime.OneAndHalfHour -> 90L
            EnumTime.TwoHours -> 120L
        }
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .setInputData(workData)
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "recipe_reminder",
            ExistingWorkPolicy.REPLACE,
            request
        )

    }

}
