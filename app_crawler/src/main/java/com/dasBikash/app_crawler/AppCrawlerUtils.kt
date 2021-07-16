package com.dasBikash.app_crawler

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.dasBikash.app_crawler_core.RoboAppCrawlerUtils
import com.dasBikash.app_crawler_model.Task
import com.dasBikash.app_crawler_model.TestOutputDetails
import com.dasBikash.app_crawler_model.TestSettings
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * ```
 * Main public class to interface with the `App crawler` module.
 * External classes will have to use the static methods
 * of this class to start test, get access to interceptor,
 * get report location details etc...
 * ```
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 */
class AppCrawlerUtils {

    companion object {

        /**
         * suspend function to launch test
         *
         * @param context Android context
         * @param testSettings object to provide test settings during test launch
         * @see https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestSettings.kt
         *
         * @return true for test launch success
         * @author Bikash Das(das.bikash.dev@gmail.com)
         *
         * */
        suspend fun startTest(
            context: Context,
            testSettings: TestSettings = TestSettings()
        ):Boolean = RoboAppCrawlerUtils.startTest(context,testSettings)

        /**
         * async function to launch test
         *
         * @param context Android context
         * @param testSettings object to provide test settings during test launch
         * @see https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestSettings.kt
         *
         *  @return Task<Boolean>
         *  [see also](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/Task.kt)
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         * */

        fun startTestAsync(
            context: Context,
            testSettings: TestSettings = TestSettings()
        ): Task<Boolean> = RoboAppCrawlerUtils.startTestAsync(context,testSettings)

        /**
         *  Get Interceptor instance to attach on `OkkHttpClient.Builder`
         *
         *  @return Interceptor
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
        */
        @JvmStatic
        fun getNetworkTrafficInterceptor()
                : Interceptor = RoboAppCrawlerUtils.getNetworkTrafficInterceptor()

        /**
         *  Method to register `TestOutputDetails` update listener.
         *
         *  At the end of robo test some reports are generated. Through this listener the crawler
         *  module will provide saved report locations to app module.
         *
         *  @param lifecycleOwner Lifecycle-owner of the listener. The listener will be notified only
         *  if the corresponding lifecycleOwner is in `Resumed` state. Before starting test please
         *  call this methods from the(those) lifecycleOwner component(s) which may remain resumed after
         *  the test finishes to get result details update vai registered listeners
         *
         *  @param handleTestResults functional param that will be invoked on test completion injecting
         *  `TestOutputDetails` related instance.
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
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


        /**
         *  Method to retrieve `TestOutputDetails` of last completed test.
         *
         *  At test completion report result details can also be retrieved by this method.
         *
         *  @return TestOutputDetails of last completed test.
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        @JvmStatic
        fun getLastTestResultDetails(): TestOutputDetails? =
            RoboAppCrawlerUtils.getLastTestResultDetails()

        /**
         * Method to manually poll whether test is running currently or not
         *
         * @return true if test is running else false
         * */
        @JvmStatic
        fun isTestRunning(): Boolean = RoboAppCrawlerUtils.isTestRunning()
    }
}

/**
 * Extension on `OkHttpClient.Builder` to attach `AppCrawlerNetworkInterceptor`.
 *
 * */
fun OkHttpClient.Builder.addAppCrawlerNetworkInterceptor()
        :OkHttpClient.Builder = addInterceptor(RoboAppCrawlerUtils.getNetworkTrafficInterceptor())