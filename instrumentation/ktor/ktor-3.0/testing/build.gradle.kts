import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("otel.java-conventions")

  id("org.jetbrains.kotlin.jvm")
}

val ktorVersion = "3.0.0-beta-1"

dependencies {
  api(project(":testing-common"))

  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-server-core:$ktorVersion")

  implementation("io.opentelemetry:opentelemetry-extension-kotlin")

  compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compileOnly("io.ktor:ktor-server-netty:$ktorVersion")
  compileOnly("io.ktor:ktor-client-cio:$ktorVersion")
}

tasks {
  withType(KotlinCompile::class).configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }
}
