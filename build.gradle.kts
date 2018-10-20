
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.2.71"
}

group = "com.dhorby.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap-1.1") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:1.1.0")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.30.2")
    compile("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.0")
    compile(kotlin("stdlib-jdk8"))
    compile("org.http4k:http4k-core:3.39.2")
    compile("org.http4k:http4k-testing-hamkrest:3.39.2")
    compile("org.http4k:http4k-client-okhttp:3.39.2")
    compile("org.http4k:http4k-template-thymeleaf:3.39.2")


    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}