plugins {
    id("com.android.application")
}

/* android.applicationVariants.all { variant ->
    task("generate${variant.name.capitalize()}Javadoc", type = Javadoc) {
        description = "Generates Javadoc for $variant.name."
        source = variant.javaCompile.source
        destinationDir = file("$rootDir/javadoc/")
        failOnError = false
        doFirst {
            ext.androidJar =
                    "${android.sdkDirectory}/platforms/${android.compileSdkVersion}/android.jar"
            classpath = files(variant.javaCompile.classpath.files) +
                    files(ext.androidJar)
            options.addStringOption "-show-members", "package"
        }
    }
}
*/
android {
    namespace = "algonquin.cst2335.prabhpreetsandroidlabs"
    compileSdk = 34

    defaultConfig {
        applicationId = "algonquin.cst2335.prabhpreetsandroidlabs"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true;
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.android.volley:volley:1.2.1")
}