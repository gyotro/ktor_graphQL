val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kgraphql_version: String by project
val koin_version: String by project
val kmongo_version: String by project
val bcrypt_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.exampleGraphql"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

dependencies {
    implementation("org.koin:koin-ktor:$koin_version")
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.apurebase:kgraphql:$kgraphql_version")
    implementation("com.apurebase:kgraphql-ktor:$kgraphql_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("at.favre.lib:bcrypt:$bcrypt_version")
    // https://mvnrepository.com/artifact/com.auth0/java-jwt
    implementation("com.auth0:java-jwt:3.18.2")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}
kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")