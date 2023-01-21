object AndroidX {
    private const val coreKtxVersion = "1.9.0"
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

    private const val appCompatVersion = "1.4.1"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    private const val lifeCycleVersion = "2.5.1"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion"

}

object AndroidXTest {
    private const val version = "1.4.0"
    const val runner = "androidx.test:runner:$version"
    const val core = "androidx.test:core-ktx:$version"
    const val arch ="androidx.arch.core:core-testing:2.1.0"
}