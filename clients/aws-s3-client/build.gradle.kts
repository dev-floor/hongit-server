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

    implementation("software.amazon.awssdk:s3")
    implementation(platform("software.amazon.awssdk:bom:2.15.0"))

    testImplementation(project(":hongit-core"))
}

tasks.processResources {
    dependsOn(tasks.getByName("copySecretYaml"))
}

tasks.register<Copy>("copySecretYaml") {
    from("../../hongit-secret/application-secret.yml")
    into("src/main/resources")
}
