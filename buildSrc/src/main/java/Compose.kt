object Compose {
    const val composeVersion = "1.4.0-beta01"
    const val composeCompilerVersion = "1.4.2"
    private const val materialVersion = "1.1.0-alpha06"
    const val material = "androidx.compose.material3:material3:$materialVersion"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeCompilerVersion"
    const val icons = "androidx.compose.material:material-icons-extended:$composeVersion"

    private const val navigationVersion = "2.5.3"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.1.0-alpha01"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.4.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.5.1"
    const val viewModelLifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
}