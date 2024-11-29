pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()

        maven {
            setUrl("https://maven.google.com")
        }
        maven {
            setUrl("https://jitpack.io")
        }

        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io") // Pastikan JitPack ditambahkan di sini
        }
    }
}

rootProject.name = "ToDoBebas"
include(":app")
