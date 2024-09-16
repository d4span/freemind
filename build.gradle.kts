plugins {
    kotlin("jvm") version "2.0.10"
    id("com.ncorti.ktfmt.gradle") version "0.19.0"
    id("java")
}

group = "com.github.d4span"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://josm.openstreetmap.de/nexus/content/repositories/releases")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("com.jgoodies:jgoodies-forms:1.8.0")
    implementation("org.jibx:jibx-run:1.3.3")
    implementation("org.jsoup:jsoup:1.10.3")
    implementation("io.github.geniot:jortho:1.1")
    implementation("org.openstreetmap.jmapviewer:jmapviewer:1.14")
    implementation("org.apache.lucene:lucene-queryparser:4.6.0")
    implementation("org.apache.lucene:lucene-core:4.6.0")
    implementation("org.apache.lucene:lucene-analyzers-common:4.6.0")
    implementation("org.apache.xmlgraphics:batik-svggen:1.14")
    implementation("org.apache.xmlgraphics:fop-transcoder:2.6")
    implementation("javax.help:javahelp:2.0.05")
    implementation("org.codehaus.groovy:groovy-astbuilder:3.0.17")
    implementation("org.freeplane.lightdev:simplyhtml:0.18.12")
    implementation("org.hsqldb:hsqldb:2.6.1")
    implementation("junit:junit:4.13.2")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
    testImplementation("io.kotest:kotest-property-jvm:5.9.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.compilerArgs.add("-Xlint:none")
}

ktfmt {
    googleStyle()
}



configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
