import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
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
    kotlin("kapt")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

sourceSets["main"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    api("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt:${Dependencies.Versions.querydsl}:jpa")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}
