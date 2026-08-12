import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  jacoco
  alias(libs.plugins.spotless)
  alias(libs.plugins.versions)
  alias(libs.plugins.version.catalog.update)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  id("application")
}

repositories {
  mavenCentral()
}

java {
  toolchain { languageVersion = JavaLanguageVersion.of(25) }
}

dependencies {
  implementation(libs.springBootStarterWebmvc)
  testImplementation(libs.springBootStarterTest)
  testImplementation(libs.springBootStarterWebmvcTest)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

application {
  mainClass.set("io.hexlet.App")
}

spotless {
  java {
    importOrder()
    removeUnusedImports()
    googleJavaFormat().aosp()
    formatAnnotations()
    leadingTabsToSpaces(4)
    endWithNewline()
  }
}

// versionCatalogUpdate пишет свежие версии прямо в gradle/libs.versions.toml,
// поэтому руками их сверять не нужно. Ключи не сортируются: порядок в каталоге
// смысловой, по группам зависимостей.
versionCatalogUpdate {
  sortByKey = false
}
