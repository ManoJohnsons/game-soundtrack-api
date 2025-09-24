plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.manojohnsons"
version = "0.0.1-SNAPSHOT"
description = "A RESTful API for a video game music catalog."

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Lombok Dependencies
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Docs Dependencies
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

	// JWT Dependencies
	val jjwtVersion = "0.13.0"
	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

	// Database Dependencies
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	// Test Dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
