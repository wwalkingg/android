pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://jitpack.io")
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/google")
    }
}
rootProject.name = "android"
include(":app")


include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:design-system")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:component-base")

include(":feature")
include(":feature:auth")
include(":feature:home")
include(":feature:course-all")
include(":feature:course-detail")
include(":feature:coach-all")
include(":feature:coach-detail")
include(":feature:partner-find")
include(":feature:person-health")
include(":feature:my-collect")
include(":feature:my-subscribe")
include(":feature:search")
include(":feature:splash")
