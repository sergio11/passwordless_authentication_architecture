val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val twilio_sdk_version: String by project
val koin_ktor: String by project
val hoplite_core_version: String by project
val jedis_client_version: String by project
val gson_version: String by project
val send_grid_sdk_version: String by project
val commons_lang3_version: String by project
val commons_text_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("org.graalvm.buildtools.native") version "0.9.11"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = "com.dreamsoftware"
version = "0.0.1"
application {
    mainClass.set("com.dreamsoftware.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf(
        "-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

ktor {
    fatJar {
        archiveFileName.set("mfa_server.jar")
    }
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-metrics-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-id-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-locations-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation-jvm:$ktor_version")
    implementation("com.google.code.gson:gson:$gson_version")
    // Twilio Java Helper Library
    implementation(group = "com.twilio.sdk", name = "twilio", version = twilio_sdk_version)
    // https://mvnrepository.com/artifact/com.sendgrid/sendgrid-java
    implementation("com.sendgrid:sendgrid-java:$send_grid_sdk_version")
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")
    // A boilerplate-free Kotlin config library for loading configuration files as data classes
    implementation("com.sksamuel.hoplite:hoplite-core:$hoplite_core_version")
    implementation("com.sksamuel.hoplite:hoplite-yaml:$hoplite_core_version")
    // Redis Java client designed for performance and ease of use.
    implementation("redis.clients:jedis:$jedis_client_version")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:$commons_lang3_version")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:$commons_text_version")
    // https://mvnrepository.com/artifact/commons-net/commons-net
    implementation("commons-net:commons-net:3.8.0")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

graalvmNative {
    binaries {
        named("main") {
            fallback.set(false)
            verbose.set(true)
            buildArgs.add("--initialize-at-build-time=io.ktor,kotlin,ch.qos.logback,kotlinx.serialization,org.slf4j,io.netty")
            buildArgs.add("--initialize-at-build-time=kotlinx.coroutines")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.DefaultFileRegion")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.epoll.Native")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.epoll.Epoll")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventLoop")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventArray")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.kqueue.KQueue")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventLoop")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventArray")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.kqueue.Native")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.unix.Limits")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.unix.Errors")
            buildArgs.add("--initialize-at-run-time=io.netty.channel.unix.IovArray")
            buildArgs.add("-H:+InstallExitHandlers")
            buildArgs.add("-H:+PrintClassInitialization")
            buildArgs.add("-H:+ReportUnsupportedElementsAtRuntime")
            buildArgs.add("-H:+ReportExceptionStackTraces")
            buildArgs.add("-H:ReflectionConfigurationFiles=${projectDir}/src/main/resources/META-INF/native-image/reflect-config.json")
            buildArgs.add("""-H:ResourceConfigurationFiles=
        |${projectDir}/src/main/resources/META-INF/native-image/kotlin-resource.json,
        |${projectDir}/src/main/resources/META-INF/native-image/resource-config.json""".trimMargin().replace(System.lineSeparator(), ""))
            imageName.set("mfa_graalvm_service")
        }
    }
}