import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true

    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5") {
        into("BOOT-INF/classes/static/docs")
    }
}

plugins {
    id("org.springframework.boot")
    id("org.jlleitschuh.gradle.ktlint")

    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}

dependencies {
    // sub-module
    implementation(project(":hongit-core"))
    implementation(project(":mail-client"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // test
    testImplementation(project(":hongit-core"))
}

val snippetsDir = file("build/generated-snippets")

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    doFirst { delete("src/main/resources/static/docs") }

    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

tasks.register<Copy>("copyDocs") {
    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5")
    into("src/main/resources/static/docs")
}

tasks.build {
    dependsOn(tasks.asciidoctor)
    dependsOn(tasks.getByName("copyDocs"))
}
