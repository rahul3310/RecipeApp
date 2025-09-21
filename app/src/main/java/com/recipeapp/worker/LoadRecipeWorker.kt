package com.recipeapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import com.recipeapp.MainApplication
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class LoadRecipeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private var KEY_LOAD_RECIPE_WORK = "key_load_recipe_work"

        @JvmStatic
        fun get() =
            OneTimeWorkRequest.Builder(LoadRecipeWorker::class.java).addTag(KEY_LOAD_RECIPE_WORK)
                .build()
    }


    override suspend fun doWork(): Result {
        return try {
            MainApplication.instance.workManagerHelper.loadRecipeData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}
