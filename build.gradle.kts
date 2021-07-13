import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"

    val kotlinVersion = "1.5.20"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion
}

val springProjects = listOf(
    project(":hongit-core"),
    project(":hongit-api")
)

val querydslProjects = listOf(
    project(":hongit-api")
)

allprojects {
    apply {
        plugin("idea")
    }

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
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude("junit-platform-commons")
        }
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = "11"

        kotlinOptions {
            freeCompilerArgs.plus("-Xjsr305=strict")
            freeCompilerArgs.plus("-Xjvm-default=enable")
            freeCompilerArgs.plus("-progressive")
            freeCompilerArgs.plus("-XXLanguage:+InlineClasses")

            jvmTarget = "11"
        }

        dependsOn("processResources")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
