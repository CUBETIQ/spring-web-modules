import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version "1.7.0" apply false
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.7.0" apply false
}

allprojects {
    // Fixed Zero-Day CVE-2021-44228: https://cubetiq.atlassian.net/browse/CERT-1
    // Fixed Zero-Day CVE-2021-45046: https://cubetiq.atlassian.net/browse/CERT-3
    // Fixed Zero-Day CVE-2021-45105: https://cubetiq.atlassian.net/browse/CERT-4
    ext["log4j2.version"] = "2.17.0"

    // Fixed Zero-Day CVE-2021-42550: https://cubetiq.atlassian.net/browse/CERT-5
    ext["logback.version"] = "1.2.9"

    repositories {
        mavenCentral()
        maven("https://m.ctdn.net")
    }

    group = "com.cubetiqs"
    version = "0.0.1-SNAPSHOT"

    val javaVersion = "11"

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
    }

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }
}