plugins {
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.multiplatform)
}

android {
    namespace = "dev.amd.imagepicker.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    explicitApiWarning()
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
//            export(dependency = libs.essenty.lifecycle)
//            export(dependency = libs.decompose.core)
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(dependencyNotation = libs.bundles.compose)
            implementation(dependencyNotation = libs.bundles.decompose)
            implementation(dependencyNotation = libs.peekaboo.ui)
            implementation(dependencyNotation = libs.peekaboo.imagepicker)
        }

        iosMain {
            dependsOn(commonMain.get())
            iosX64 { dependsOn(this@iosMain) }
            iosArm64 { dependsOn(this@iosMain) }
            iosSimulatorArm64 { dependsOn(this@iosMain) }
        }
    }
}