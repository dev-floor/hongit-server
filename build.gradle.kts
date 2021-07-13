import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

plugins {
    id("org.springframework.boot") version "2.4.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"

    val kotlinVersion = "1.5.20"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

val springProjects = listOf(
    project(":hongit-core"),
    project(":hongit-api")
)

val querydslProjects = listOf(
    project(":hongit-api")
)

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.devfloor"
    version = "0.0.1-SNAPSHOT"
}

configure(springProjects) {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude("junit-platform-commons")
        }
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
        testImplementation("io.rest-assured:kotlin-extensions:4.3.3")
    }
}
