plugins {
    application
    id("java")
}

application.mainClass = "fr.laptoff.Bot"
group = "fr.laptoff"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.dv8tion:JDA:5.0.0-beta.23")
    implementation("ch.qos.logback:logback-classic:1.4.12")

}

tasks.test {
    useJUnitPlatform()
}