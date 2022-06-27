plugins {
    kotlin("jvm") version "1.6.10"
}

subprojects {
    apply(plugin = "kotlin")

    dependencies {
        testImplementation(kotlin("test"))
        testImplementation("org.javalite:javalite-common:3.1-j11")
    }
}
