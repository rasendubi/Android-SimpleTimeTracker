import com.example.util.simpletimetracker.Base
import com.example.util.simpletimetracker.Deps
import com.example.util.simpletimetracker.applyAndroidLibrary

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

applyAndroidLibrary()

android {
    namespace = "${Base.namespace}.feature_change_record_type"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature_change_goals:api"))
    implementation(project(":feature_change_goals:views"))
    implementation(Deps.Google.dagger)
    kapt(Deps.Kapt.dagger)
}
