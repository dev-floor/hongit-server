import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = true
}
tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

plugins {
    id("org.springframework.boot")
    id("org.jlleitschuh.gradle.ktlint")

    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}

dependencies {
    implementation(project(":hongit-core"))
    implementation("org.springframework.boot:spring-boot-starter-mail")

    testImplementation(project(":hongit-core"))
}
