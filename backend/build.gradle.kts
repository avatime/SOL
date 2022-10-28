import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
//	id("org.springframework.experimental.aot") version "0.12.1"
	id ("org.jetbrains.kotlin.plugin.allopen") version "1.3.61"
	id ("org.jetbrains.kotlin.plugin.noarg") version "1.3.61"
}

group = "com.finance"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
	/* kafka */
	implementation("org.springframework.kafka:spring-kafka")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("mysql:mysql-connector-java")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

noArg {
	annotation("javax.persistence.Entity")
}
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<BootBuildImage> {
	builder = "paketobuildpacks/builder:tiny"
	environment = mapOf("BP_NATIVE_IMAGE" to "true")
}
