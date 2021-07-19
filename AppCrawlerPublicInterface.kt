
/**
 * ```
 * Main public AppCrawlerPublicInterface to communicate with the `App crawler` module.
 * External classes will have to use the implementation of this interface namely
 * `AppCrawlerUtils`, to get access to the network interceptor, get report location details etc...
 *
 * ```
 *
 * @author Bikash Das(das.bikash.dev@gmail.com)
 */
interface AppCrawlerPublicInterface {

        /**
         * suspend function to launch test
         *
         * @param context Android context
         * @param testSettings object to provide test settings during test launch
         * @see https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestSettings.kt
         *
         * @return TestRequestResult according to test launch status
         *
         * @see (https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestRequestResult.kt)
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         *
         * */
        suspend fun startTest(
            context: Context,
            testSettings: TestSettings = TestSettings(testScriptPaths = null)
        ): TestRequestResult

        /**
         * async function to launch test
         *
         * @param context Android context
         * @param testSettings object to provide test settings during test launch
         * @see https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestSettings.kt
         *
         *  @return Task<TestRequestResult>  according to test launch status
         *
         *  @see (https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/Task.kt)
         *  @see (https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestRequestResult.kt)
         *
         *  @author Bikash Das(das.bikash.dev@gmail.com)
         * */
        fun startTestAsync(
            context: Context,
            testSettings: TestSettings = TestSettings(testScriptPaths = null)
        ): Task<TestRequestResult>

        /**
         *  Get Interceptor instance to attach on `OkkHttpClient.Builder`
         *
         *  @return Interceptor
         *
         * @author Bikash Das(das.bikash.dev@gmail.com)
         */
        fun getNetworkTrafficInterceptor(): Interceptor

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
        fun addRoboTestResultListener(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            handleTestResults: (TestOutputDetails) -> Any?
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
        fun getLastTestResultDetails(): TestOutputDetails?

        /**
         * Method to manually poll whether test is running currently or not
         *
         * @return true if test is running else false
         * */
        fun isTestRunning(): Boolean
}


/**
 * Extension on `OkHttpClient.Builder` to attach `AppCrawlerNetworkInterceptor`.
 *
 * */
@Keep
fun OkHttpClient.Builder.addAppCrawlerNetworkInterceptor() : OkHttpClient.Builder