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
    implementation("net.dv8tion:JDA:5.0.0-beta.23")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("org.kohsuke:github-api:1.321")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}