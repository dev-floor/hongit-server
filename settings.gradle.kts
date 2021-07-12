/**
 * build.gradle.kts plugins{} 에서 호출될 플러그인용 repository 지정
 */
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "hongit"

include("hongit-api", "hongit-core")
