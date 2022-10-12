plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.allopen") version "1.7.20"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    /**
     * Kotlin
     */
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /**
     * Dependency Injecction
     */
    implementation("io.quarkus:quarkus-arc")
    /**
     * Vert.x
     */
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-vertx-web")
    /**
     * Reactive
     */
    implementation("io.quarkus:quarkus-mutiny")
    implementation("io.quarkus:quarkus-reactive-routes")
    /**
     * RESTEasy Reactive
     */
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    /**
     * REST Client Reactive
     */
    implementation("io.quarkus:quarkus-rest-client-reactive")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")
    /**
     * Bean Validation
     */
    implementation("io.quarkus:quarkus-hibernate-validator")
    /**
     * Fault Tolerance
     */
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    /**
     * Health Check
     */
    implementation("io.quarkus:quarkus-smallrye-health")
    /**
     * Logging
     */
    implementation("io.quarkus:quarkus-smallrye-opentracing")
//    Uncomment the below dependency if you want to display your logs in json format
//    implementation("io.quarkus:quarkus-logging-json")
    /**
     * Security
     */
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
    /**
     * Others
     */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
    /**
     * Test
     */
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "ph.skybridge"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
