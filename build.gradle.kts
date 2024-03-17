plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://josm.openstreetmap.de/nexus/content/repositories/releases")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api(libs.com.jgoodies.jgoodies.forms)
    api(libs.org.jibx.jibx.run)
    api(libs.org.jsoup.jsoup)
    api(libs.junit.junit)
    api(libs.io.github.geniot.jortho)
    api(libs.org.openstreetmap.jmapviewer.jmapviewer)
    api(libs.org.apache.lucene.lucene.queryparser)
    api(libs.org.apache.lucene.lucene.core)
    api(libs.org.apache.lucene.lucene.analyzers.common)
    api(libs.org.apache.xmlgraphics.batik.svggen)
    api(libs.org.apache.xmlgraphics.fop.transcoder)
    api(libs.javax.help.javahelp)
    api(libs.org.codehaus.groovy.groovy.astbuilder)
    api(libs.org.freeplane.lightdev.simplyhtml)
    api(libs.org.hsqldb.hsqldb)
}

group = "com.github.d4span"
version = "0.0.1-SNAPSHOT"
description = "freemind"

java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
