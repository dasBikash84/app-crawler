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

## Integration steps for an existing app:

### Step-1 : Download latest releaseed `.aar` and add to your project.
- Download latest crawler release from 
[`here`](https://github.com/dasBikash84/app-crawler/tree/master/latestRelease)
- copy the downloaded `.aar` file inside your app modules `libs` directory.

![image info](https://raw.githubusercontent.com/dasBikash84/app-crawler/master/images/aar-add_location.png)

### Step-2 : Include required dependencies.

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

### Latest model library version:   ![](https://jitpack.io/v/dasBikash84/app-crawler-model.svg)

Then, add below portion in your app module's `build.gradle` file

```gradle

repositories{
    flatDir{
        dirs 'libs'
    }
}

dependencies {
    implementation(name:'aar_file_name_except_extension', ext:'aar')
    // Example: 
    // implementation(name:'app_crawler-1.0-beta', ext:'aar')
    implementation 'com.github.dasBikash84:app-crawler:latest-model-library-version'
    // Example: 
    // implementation 'com.github.dasBikash84:app-crawler:1.9'
}
```


### Step-3 : Add `optional` network activity listener.
Attach app crawler network interceptor with your `OkHttpClient` object to enable network acctivity logging.

```
private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addAppCrawlerNetworkInterceptor()
            .build()
    }
``` 

For interceptor url filter configuration examples visit [here](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/RequestMethodFilter.kt).

### Step-4 : Register `optional` test result listner for [`saved test result location information`](https://github.com/dasBikash84/app-crawler-model/blob/master/app_crawler_model/src/main/java/com/dasBikash/app_crawler_model/TestOutputDetails.kt) update on test completion (add listener(s) at the compent class(s) where your app will land upon test session completion).



```
AppCrawlerUtils
    .addRoboTestResultListener(
            context,lifecycleOwner,{ testOutputDetails ->
                // process provided test result disk locations
            }
        )
```

### Step-5 : Launching test with default configuration
From any place inside of your app issue test launch instruction:

```
AppCrawlerUtils.startTestAsync(context)
```
or from inside of any [`Coroutine`](https://kotlinlang.org/docs/coroutines-basics.html)

```
AppCrawlerUtils.startTest(context)
```

For all test launch options please visit [`here`](https://github.com/dasBikash84/app-crawler/blob/master/AppCrawlerUtils_doc.txt)

For more info on integration visit [`here`](https://drive.google.com/drive/folders/1kP6_CXNhVI-QNQ-0u_-NsT23nZlnw6Hz?usp=sharing) 

<br>

### Using the utility `.jar` file for joining test scripts:

- [`Download`](https://github.com/dasBikash84/app-crawler/blob/master/add_robo_scripts.jar) the jar.
- To merge multiple test scripts command format is as below:
```
    java -jar add_robo_scripts.jar file1.json file2.json file3.json
``` 
If provided files are valid test scripts then the jar will merge the scripts and print saved file path in command line.

