import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("otel.library-instrumentation")

  id("org.jetbrains.kotlin.jvm")
  `maven-publish`
}

val ktorVersion = "3.0.0-beta-2"
// we only publish this library, the rest should keep the original version
// and be fetched from upstream repositories
project.version = "$version-snoty.2"

dependencies {
  library("io.ktor:ktor-client-core:$ktorVersion")
  library("io.ktor:ktor-server-core:$ktorVersion")

  implementation(project(":instrumentation:ktor:ktor-common:library"))
  implementation("io.opentelemetry:opentelemetry-extension-kotlin")

  compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  testImplementation(project(":instrumentation:ktor:ktor-3.0:testing"))
  testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  testLibrary("io.ktor:ktor-server-netty:$ktorVersion")
  testLibrary("io.ktor:ktor-client-cio:$ktorVersion")
}

tasks {
  withType(KotlinCompile::class).configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }

  compileKotlin {
    kotlinOptions {
      languageVersion = "1.6"
    }
  }
}

publishing {
  repositories {
    maven {
      name = "simulatanRepositorySnapshots"
      url = uri("https://maven.simulatan.me/snapshots")
      credentials(PasswordCredentials::class)
    }
    maven {
      name = "simulatanRepositoryReleases"
      url = uri("https://maven.simulatan.me/releases")
      credentials(PasswordCredentials::class)
    }
  }
}
