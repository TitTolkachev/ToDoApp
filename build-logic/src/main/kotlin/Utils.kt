import com.android.build.gradle.BaseExtension

fun BaseExtension.baseAndroidConfig() {
    setCompileSdkVersion(AndroidConst.COMPILE_SDK)
    defaultConfig {
        targetSdk = AndroidConst.COMPILE_SDK
        minSdk = AndroidConst.MIN_SDK

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        create("staging") {
            initWith(getByName("debug"))
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConst.COMPILE_JDK_VERSION
        targetCompatibility = AndroidConst.COMPILE_JDK_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConst.KOTLIN_JVM_TARGET
    }
}