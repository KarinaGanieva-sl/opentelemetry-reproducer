import com.google.protobuf.gradle.*

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("com.google.protobuf") version "0.8.17"
    id("idea")
}

repositories {
    mavenCentral()
    google()
}

val grpcVersion = "1.56.0"
val grpcKotlinVersion = "1.4.0"
val protobufVersion = "3.21.12"
val coroutinesVersion = "1.7.1"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-core:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-netty:$grpcVersion")
    implementation("io.vertx:vertx-core:4.5.6")
    implementation("io.vertx:vertx-lang-kotlin:4.5.6")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:4.5.6")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.56.0"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
        }
    }
}