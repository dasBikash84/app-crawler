# app-crawler

### A free `Local Robo testing` tool for Native Android apps.



## Main Features:

- Running supervised local tests with [`Firebase Robo test scripts`](https://firebase.google.com/docs/test-lab/android/robo-ux-test).
- Running un-superviced auto robo tests.
- Detailed network activity logging during tests.
- Customizable api request filter to ensure restricted network activity.
- Comprehensive test report containing:
    
    1) Detailed activity report of `robo crawler actions`.

    2) Detailed `network activity data` during test. 
    
    3) Generated `logcat` during robo test.

    4) Screen-shots of test journey screens.
- Support for remote/local test scripts.
- Very simple integration interface.

<br>

![](https://jitpack.io/v/dasBikash84/app-crawler.svg)

## Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
        maven { 
            url "https://jitpack.io" 
        }
    }
}
```

Then, add the library in your app module `build.gradle`
```gradle
dependencies {
    implementation 'com.github.dasBikash84:app-crawler:latest-verion-here'
}
```
<br>

## Sample integration steps for `Auto Robo test`:

### Step-1 : Include required dependencies.

Add above dependencies in your build.gradle files.

### Step-2 : Add `optional` network activity listener.
Attach app crawler network interceptor with your `OkHttpClient` object to enable network acctivity logging.

```
private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addAppCrawlerNetworkInterceptor()
            .build()
    }
```

### Step-3 : Register test result listner on activity/fragment for [`saved test result location information`](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestOutputDetails.kt) update on test completion.



```
AppCrawlerUtils
    .addRoboTestResultListener(
            context,lifecycleOwner,{ testOutputDetails ->
                // process provided test result disk locations
            }
        )
```

### Step-4 : 
From any place inside of your app issue test launch instruction:

```
AppCrawlerUtils.startAutoRoboTestAsync(context)
```
or from inside of any [`Coroutine`](https://kotlinlang.org/docs/coroutines-basics.html)

```
AppCrawlerUtils.startAutoRoboTest(context)
```

For all test launch options please visit [`here`](https://github.com/dasBikash84/app-crawler/blob/master/app_crawler/src/main/java/com/dasBikash/app_crawler/AppCrawlerUtils.kt):