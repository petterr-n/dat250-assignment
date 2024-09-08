plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "no.peron"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Data JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Spring Web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// H2 (embedded database)
	runtimeOnly("com.h2database:h2")

	// Spring Boot Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// JUnit platform launcher for tests
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
