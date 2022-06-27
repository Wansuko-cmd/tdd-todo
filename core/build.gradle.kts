plugins {
    kotlin("jvm") version "1.6.10"
}

subprojects {
    apply(plugin = "kotlin")

    dependencies {
        implementation(project(":utils"))

        testImplementation(kotlin("test"))
        testImplementation("org.javalite:javalite-common:3.1-j11")
        testImplementation("io.mockk:mockk:1.12.3")
    }
}
