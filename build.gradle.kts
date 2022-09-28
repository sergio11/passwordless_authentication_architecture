val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.1.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("org.graalvm.buildtools.native") version "0.9.14"
}

group = "com.dreamsoftware"
version = "0.0.1"
application {
    mainClass.set("com.dreamsoftware.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

ktor {
    fatJar {
        archiveFileName.set("mfa_server.jar")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-metrics-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-id-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-locations-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

graalvmNative {
    binaries {
        named("main") {
            useFatJar.set(true)
            fallback.set(false)
            verbose.set(true)
            buildArgs.add("--initialize-at-build-time=io.ktor,kotlin,ch.qos.logback,kotlinx.serialization,org.slf4j,io.netty")
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
            imageName.set("mfa_graalvm_service")
        }
    }
}