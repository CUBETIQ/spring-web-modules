import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version "1.6.0" apply false
    kotlin("plugin.spring") version "1.6.0" apply false
    // kotlin("plugin.jpa") version "1.6.0" apply false
}

allprojects {
    // Fixed Zero-Day CVE-2021-44228: https://cubetiq.atlassian.net/browse/CERT-1
    ext["log4j2.version"] = "2.15.0"

    repositories {
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