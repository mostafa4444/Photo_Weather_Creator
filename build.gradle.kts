// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies{
        classpath(Build.androidBuildTools)
        classpath(Build.saveArgsClassPath)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroid)
    }
}

tasks.register("clean" , Delete::class){
    delete(rootProject.buildDir)
}
