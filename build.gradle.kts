import com.google.protobuf.gradle.id

group = "com.personio.my-grpc"
version = "local"

/**
 * Anything protobuf related could be moved into the submodule's build.gradle.kts file.
 * Not taking this up at the moment to keep everything build related in one place
 */
val protoCompilerVersion = "3.22.3"
val grpcCoreVersion = "1.54.0"
val grpcKotlinVersion = "1.3.0"

plugins {
    kotlin("jvm") version "1.8.20"
    id("com.google.protobuf") version "0.9.2"
}

repositories {
    google()
    mavenCentral()
}

// apply(plugin = "org.jetbrains.kotlin.jvm")
// apply(plugin = "com.google.protobuf")

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("com.google.protobuf:protobuf-kotlin:$protoCompilerVersion")
    implementation("io.grpc:grpc-all:$grpcCoreVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
}

sourceSets {
    main {
        proto {
            srcDir("proto")
        }
        java {
            /*srcDir("build/generated/source/proto/main/grpc")
            srcDir("build/generated/source/proto/main/grpckt")
            srcDir("build/generated/source/proto/main/java")
            srcDir("build/generated/source/proto/main/kotlin")*/
            srcDir("build/classes")
        }
    }
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:$protoCompilerVersion" }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:$grpcCoreVersion" }
        id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar" }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}
