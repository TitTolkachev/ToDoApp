pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ToDoApp"
include(":app")
include(":feature:auth")
include(":feature:todo")
include(":core:model")
include(":core:data")
include(":core:datastore")
include(":core:database")
include(":core:network")
include(":core:designsystem")
