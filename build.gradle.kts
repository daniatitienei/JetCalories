buildscript {
    dependencies {
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.androidTools)
        classpath(Build.kotlinGradlePlugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Build.androidApplication).version(Build.androidVersion).apply(false)
    id(Build.androidLibrary).version(Build.androidVersion).apply(false)
    id(Build.kotlin).version(Kotlin.version).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}