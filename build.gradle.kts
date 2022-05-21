import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}
val kotestVersion = "4.6.3"
val allureVersion = "2.17.2"
val logbackVersion = "1.2.3"
val gsonVersion = "2.8.6"
val fuelVersion = "2.3.1"
val reflectVersion = "1.4.32"
val tomakehurstVersion = "2.28.0"
val junitversion = "4.5.0"
val generaxVersion = "1.0.2"
val jsonPathVersion = "2.0.0"
val exposedVersion = "0.20.1"
val postgresqlVersion = "42.2.2"
val hikariCPVersion = "2.3.2"
val jedisVercion = "3.7.0"
val jsoupVersion = "1.14.3"
val minioVersion = "8.0.3"
val csvVersion = "1.9.0"
val jacksonVersion = "2.13.0"
val awaitilityVersion = "4.1.0"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:29.0-jre")
    implementation(kotlin("script-runtime"))
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.22.0")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    implementation("com.github.kittinunf.fuel:fuel-jackson:$fuelVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$reflectVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.awaitility:awaitility-kotlin:$awaitilityVersion")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")
    implementation("io.kotest:kotest-assertions-json:$kotestVersion")
    implementation("io.kotest:kotest-common-jvm:$kotestVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
    //implementation("io.qameta.allure:allure-java-commons:$allureVersion")
    implementation("io.github.microutils:kotlin-logging:1.12.5")
    implementation("com.github.tomakehurst:wiremock-jre8:$tomakehurstVersion")
    implementation("com.github.mifmif:generex:$generaxVersion")
    implementation("com.nfeld.jsonpathkt:jsonpathkt:$jsonPathVersion")
}

tasks.test {
    useJUnitPlatform()
}

val kotlinCompileConfig: org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.() -> Unit = {
    jvmTarget = "11"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.apply {
    kotlinOptions(kotlinCompileConfig)
}

val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions(kotlinCompileConfig)