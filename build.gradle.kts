plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.4.1"
}

group = "org.cataloguemanager"
version = "0.1"

repositories {
    mavenCentral()
}

tasks.shadowJar {
    archiveBaseName.set("CatalogueManager")
    archiveClassifier.set("shadowjar")  // Remove '-all' suffix
    manifest {
        attributes["Main-Class"] = "org.cataloguemanager.App"
    }
}

application {
    mainClass.set("org.cataloguemanager.App")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.28")
    implementation("org.apache.commons:commons-lang3:3.20.0")
    implementation("com.github.cliftonlabs:json-simple:4.0.1")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.cataloguemanager.App"
    }

    // Include dependencies
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Handle duplicate files
}

tasks.test {
    useJUnitPlatform()
}