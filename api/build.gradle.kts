import java.io.ByteArrayOutputStream

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
}

val kotlinVersion = "1.7.20"
val springBootVersion = "2.7.4"

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
			additional["commitId"] = getGitHashLastCommit()
			additional["springBootVersion"] = springBootVersion
			additional["kotlinVersion"] = kotlinVersion
		}
	}
}

dependencies {
	// Spring Data JPA (Required for Database Layer)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Migrating from SpringFox
	implementation("org.springdoc:springdoc-openapi-ui:1.6.12")

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
