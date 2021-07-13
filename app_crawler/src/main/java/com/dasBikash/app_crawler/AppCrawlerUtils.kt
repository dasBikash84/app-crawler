package com.dasBikash.app_crawler

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.dasBikash.app_crawler_core.RoboAppCrawlerUtils
import com.dasBikash.app_crawler_model.RequestMethodFilter
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


        suspend fun startTest(
            context: Context,
            testSettings: TestSettings = TestSettings()
        ):Boolean = RoboAppCrawlerUtils.startTest(context,testSettings)

        fun startTestAsync(
            context: Context,
            testSettings: TestSettings = TestSettings()
        ): Task<Boolean> = RoboAppCrawlerUtils.startTestAsync(context,testSettings)

        /**
         * suspend method to start robo test with a script
         *
         *  @param context Android Context
         *  @param roboScriptPath `Remote/local(Private app storage)/assets` script path
         *  @param maxRunTimeMinutes Maximum test run time in minutes (>=30 will be truncated to 30)
         *  @param runOnlyScript whether to run only script or run unscripted robo test
         *                          also at the end of scripted action execution.
         *
         *  @return true if test starts successfully else returns false
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        /*@JvmStatic
        suspend fun startRoboTestWithScript(
            context: Context,
            roboScriptPath: String,
            maxRunTimeMinutes: Int = 5,
            runOnlyScript: Boolean = false
        ): Boolean = RoboAppCrawlerUtils.startTestWithScript(
            context,
            roboScriptPath,
            maxRunTimeMinutes,
            runOnlyScript
        )*/

        /**
         * suspend method to start unscripted robo test
         *
         *  @param context Android Context
         *  @param maxRunTimeMinutes Maximum test run time in minutes (>=30 will be truncated to 30)
         *
         *  @return true if test starts successfully else returns false
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        /*@JvmStatic
        suspend fun startAutoRoboTest(
            context: Context,
            maxRunTimeMinutes: Int = 5
        ): Boolean = RoboAppCrawlerUtils.startAutoTest(
            context,
            maxRunTimeMinutes
        )*/

        /**
         *  Starts robo test with a script asynchronously
         *
         *  @param context Android Context
         *  @param roboScriptPath `Remote/local(Private app storage)/assets` script path
         *  @param maxRunTimeMinutes Maximum test run time in minutes  (>=30 will be truncated to 30)
         *  @param runOnlyScript whether to run only script or run unscripted robo test
         *                          also at the end of scripted action execution.
         *
         *  @return Task<Boolean>
         *  [see also](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/Task.kt)
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        /*@JvmStatic
        fun startRoboTestWithScriptAsync(
            context: Context,
            roboScriptPath: String,
            maxRunTimeMinutes: Int = 5,
            runOnlyScript: Boolean = false
        ): Task<Boolean> = RoboAppCrawlerUtils.startTestWithScriptAsync(
            context,
            roboScriptPath,
            maxRunTimeMinutes,
            runOnlyScript
        )*/

        /**
         *  Starts unscripted robo test without a script asynchronously
         *
         *  @param context Android Context
         *  @param maxRunTimeMinutes Maximum test run time in minutes  (>=30 will be truncated to 30)
         *
         *  @return Task<Boolean>
         *  [see also](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/Task.kt)
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        /*@JvmStatic
        fun startAutoRoboTestAsync(
            context: Context,
            maxRunTimeMinutes: Int = 5
        ): Task<Boolean> = RoboAppCrawlerUtils.startAutoTestAsync(
            context,
            maxRunTimeMinutes
        )*/

        /**
         *  Get Interceptor instance to attach on `OkkHttpClient.Builder`
         *
         * few calling examples can be as below
         *
         * #### To allow only `GET` api calls
         * ```
         * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetFilter())
         *
         * #### To allow only `GET` and any other api containing '/login' in path
         * ```
         * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetFilter(listOf("/login")))
         * ```
         * #### To allow only `GET` and `POST` api calls
         * ```
         * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetPostFilter())
         * ```
         * #### To allow only `GET` , `POST` and `DELETE` api calls
         * ```
         * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetPostDeleteFilter())
         * ```
         *
         * With `null` RequestMethodFilter no api call will be restricted.
         *
         *  @param requestMethodFilter optional RequestMethodFilter instance to restrict specific api calls
         *  [see also](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/RequestMethodFilter.kt)
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
 * few calling examples can be as below
 *
 * #### To allow only `GET` api calls
 * ```
 * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetFilter())
 *
 * #### To allow only `GET` and any other api containing '/login' in path
 * ```
 * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetFilter(listOf("/login")))
 * ```
 * #### To allow only `GET` and `POST` api calls
 * ```
 * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetPostFilter())
 * ```
 * #### To allow only `GET` , `POST` and `DELETE` api calls
 * ```
 * OkHttpClient.Builder().addAppCrawlerNetworkInterceptor(RequestMethodFilter.methodGetPostDeleteFilter())
 * ```
 *
 * With `null` RequestMethodFilter no api call will be restricted.
 *
 *  @param requestMethodFilter optional RequestMethodFilter instance to restrict specific api calls
 *  [see also](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/RequestMethodFilter.kt)
 *
 * */
fun OkHttpClient.Builder.addAppCrawlerNetworkInterceptor(requestMethodFilter: RequestMethodFilter? = null)
        :OkHttpClient.Builder = addInterceptor(RoboAppCrawlerUtils.getNetworkTrafficInterceptor())