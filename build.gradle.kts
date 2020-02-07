import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.31"
}


group = "com.dhorby.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap-1.1") }
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1")
    compile("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.0")
    compile(kotlin("stdlib-jdk8"))
    compile("org.http4k:http4k-core:3.39.2")
    compile("org.http4k:http4k-testing-hamkrest:3.39.2")
    compile("org.http4k:http4k-client-okhttp:3.39.2")
    compile("org.http4k:http4k-template-thymeleaf:3.39.2")
    compile("org.http4k:http4k-server-jetty:3.103.2")
    compile("org.apache.commons:commons-text:1.6")
    compile ("com.warrenstrange:googleauth:1.1.2")

    testImplementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks.withType<KotlinCompile> {
    doFirst { println("Started") }
    kotlinOptions.jvmTarget = "1.8"
    doLast { println("Completed") }
}

tasks.withType<Test> {
    useJUnitPlatform ()
}





   





