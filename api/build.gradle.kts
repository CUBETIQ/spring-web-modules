import java.io.ByteArrayOutputStream

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
}

val kotlinVersion = "1.8.10"
val springBootVersion = "3.0.4"

// find the last commit
fun getGitHashLastCommit(): String {
	val stdout = ByteArrayOutputStream()
	exec {
		commandLine("git", "rev-parse", "HEAD")
		standardOutput = stdout
	}

	return stdout.toString().trim()
}

springBoot {
	buildInfo {
		properties {
			this.additional.put("commitId", getGitHashLastCommit())
			this.additional.put("springBootVersion", springBootVersion)
			this.additional.put("kotlinVersion", kotlinVersion)
		}
	}
}

dependencies {
	// Spring Data JPA (Required for Database Layer)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Migrating from SpringDoc API (Swagger) for Support Spring Boot 3.x
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

	// SPRING FRAMEWORK AND CORE
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Development Runtime
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	runtimeOnly("com.h2database:h2")
	// runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	archiveFileName.set("api.jar")
}