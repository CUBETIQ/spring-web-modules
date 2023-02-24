import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.3" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    kotlin("jvm") version "1.8.10" apply false
    kotlin("plugin.spring") version "1.8.10" apply false
    kotlin("plugin.jpa") version "1.8.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.cubetiqs"
    version = "0.0.1-SNAPSHOT"

    val javaVersion = "17"

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion
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
