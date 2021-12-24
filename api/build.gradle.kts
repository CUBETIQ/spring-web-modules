import java.io.ByteArrayOutputStream

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

val kotlinVersion = "1.6.10"
val springBootVersion = "2.6.2"

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
	// Migrating from SpringFox
	implementation("org.springdoc:springdoc-openapi-ui:1.6.2")

	// SPRING FRAMEWORK AND CORE
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Development Runtime
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
