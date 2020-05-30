import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
    id("com.github.ben-manes.versions") version "0.28.0"
}

application {
    mainClassName = "com.swiftmako.blocklogger.BlockLoggerApplicationKt"
}

object Versions {
    const val commonsIo = "2.7"
    const val coroutines = "1.3.7"
    const val joda = "2.10.6"
    const val moshi = "1.9.2"
}

group = "com.swiftmako"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}")

    implementation(kotlin("stdlib-jdk8"))

    implementation("commons-io:commons-io:${Versions.commonsIo}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.coroutines}")
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("joda-time:joda-time:${Versions.joda}")

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    // Example 1: reject all non stable versions
    rejectVersionIf {
        isNonStable(candidate.version)
    }

    // Example 2: disallow release candidates as upgradable versions from stable versions
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

    // Example 3: using the full syntax
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

//tasks {
//    bootJar {
//        launchScript()
//    }
//}