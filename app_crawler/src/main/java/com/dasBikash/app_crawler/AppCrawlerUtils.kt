package com.dasBikash.app_crawler

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.dasBikash.app_crawler_core.RoboAppCrawlerUtils
import com.dasBikash.app_crawler_core.utils.AppCrawlerNetworkInterceptor
import com.dasBikash.app_crawler_model.RequestMethodFilter
import com.dasBikash.app_crawler_model.Task
import com.dasBikash.app_crawler_model.TestOutputDetails
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class AppCrawlerUtils {

    companion object {

        @JvmStatic
        suspend fun startRoboTestWithScript(
            context: Context,
            roboScriptPath: String,
            lifecycleOwner: LifecycleOwner? = null,
            handleTestResults: ((TestOutputDetails) -> Any?)? = null,
            maxRunTimeMinutes: Int = 5,
            runOnlyScript: Boolean = false
        ): Boolean = RoboAppCrawlerUtils.startTestWithScript(
            context,
            roboScriptPath,
            lifecycleOwner,
            handleTestResults,
            maxRunTimeMinutes,
            runOnlyScript
        )

        @JvmStatic
        suspend fun startAutoRoboTest(
            context: Context,
            lifecycleOwner: LifecycleOwner? = null,
            handleTestResults: ((TestOutputDetails) -> Any?)? = null,
            maxRunTimeMinutes: Int = 5
        ): Boolean = RoboAppCrawlerUtils.startAutoTest(
            context,
            lifecycleOwner,
            handleTestResults,
            maxRunTimeMinutes
        )

        @JvmStatic
        fun startRoboTestWithScriptAsync(
            context: Context,
            roboScriptPath: String,
            lifecycleOwner: LifecycleOwner? = null,
            handleTestResults: ((TestOutputDetails) -> Any?)? = null,
            maxRunTimeMinutes: Int = 5,
            runOnlyScript: Boolean = false
        ): Task<Boolean> = RoboAppCrawlerUtils.startTestWithScriptAsync(
            context,
            roboScriptPath,
            lifecycleOwner,
            handleTestResults,
            maxRunTimeMinutes,
            runOnlyScript
        )

        @JvmStatic
        fun startAutoRoboTestAsync(
            context: Context,
            lifecycleOwner: LifecycleOwner? = null,
            handleTestResults: ((TestOutputDetails) -> Any?)? = null,
            maxRunTimeMinutes: Int = 5
        ): Task<Boolean> = RoboAppCrawlerUtils.startAutoTestAsync(
            context,
            lifecycleOwner,
            handleTestResults,
            maxRunTimeMinutes
        )

        @JvmStatic
        fun getNetworkTrafficInterceptor(requestMethodFilter: RequestMethodFilter? = null)
                : Interceptor = AppCrawlerNetworkInterceptor
            .getInstance()
            .apply { this.requestMethodFilter = requestMethodFilter }

        @JvmStatic
        fun addRoboTestResultListener(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            handleTestResults: (TestOutputDetails) -> Any?
        ) = RoboAppCrawlerUtils.addRoboTestResultListener(
            context,
            lifecycleOwner,
            handleTestResults
        )

        @JvmStatic
        fun getLastTestResultDetails(): TestOutputDetails? =
            RoboAppCrawlerUtils.getLastTestResultDetails()

        @JvmStatic
        fun isTestRunning(): Boolean = RoboAppCrawlerUtils.isTestRunning()
    }
}

fun OkHttpClient.Builder.addAppCrawlerNetworkInterceptor(requestMethodFilter: RequestMethodFilter? = null)
        :OkHttpClient.Builder = addInterceptor(
    AppCrawlerNetworkInterceptor.getInstance()
        .apply { this.requestMethodFilter = requestMethodFilter }
)