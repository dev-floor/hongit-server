import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Dependencies.Versions.springBoot apply false
    id("io.spring.dependency-management") version Dependencies.Versions.springDependencyManagement apply false
    id("org.jlleitschuh.gradle.ktlint") version Dependencies.Versions.ktlint
    id("org.asciidoctor.convert") version Dependencies.Versions.asciiDoctor

    kotlin("jvm") version Dependencies.Versions.kotlin
    kotlin("plugin.spring") version Dependencies.Versions.kotlin apply false
    kotlin("plugin.jpa") version Dependencies.Versions.kotlin apply false
    kotlin("kapt") version Dependencies.Versions.kotlin
    kotlin("plugin.allopen") version Dependencies.Versions.kotlin
}

val springProjects = listOf(
    project(":hongit-core"),
    project(":hongit-api")
)

val restDocsProjects = listOf(
    project(":hongit-api")
)

allprojects {
    apply {
        plugin("idea")
    }

    repositories {
        mavenCentral()
    }

    group = "${property("projectGroup")}"
    version = "${property("projectVersion")}"
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

        implementation("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude("junit-platform-commons")
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
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

configure(restDocsProjects) {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.asciidoctor.convert")
    }

    extra["snippetsDir"] = file("build/generated-snippets")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    }
}
