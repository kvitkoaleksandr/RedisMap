plugins {
    java
    application
    `java-library`
}

group = "org.redis"
version = "1.0"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("redis.clients:jedis:5.1.0")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.redis.Main")
}