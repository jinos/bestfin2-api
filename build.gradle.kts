plugins {
    val kotlinVersion = "1.3.61"
    val springBootVersion = "2.2.2.RELEASE"
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.springframework.boot") version springBootVersion apply false
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
}

repositories {
    mavenCentral()
}

apply(plugin = "kotlin")
apply(plugin = "kotlin-kapt")
apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-allopen")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

group = "com.fin.best"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

allOpen {
    annotation("javax.persistence.Entity")
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //basic
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")

    //data
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.3.0")
    compile("com.querydsl:querydsl-jpa:4.2.1")
    kapt("com.querydsl:querydsl-apt:4.2.1:jpa")

    //auth
    implementation("org.springframework.boot:spring-boot-starter-security")

    // oauth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.1.4.RELEASE")
    implementation("org.springframework.security:spring-security-jwt:1.0.10.RELEASE")

    //util
    compileOnly("org.projectlombok:lombok")
    implementation("commons-io:commons-io:2.6")
    implementation("commons-lang:commons-lang:2.6")
    implementation("commons-io:commons-io:2.6")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("org.apache.tika:tika-parsers:1.20")
    // excel
    implementation("org.apache.poi:poi-ooxml:4.0.1")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.fasterxml.uuid:java-uuid-generator:3.1.5")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
        dependsOn(processResources) // kotlin 에서 ConfigurationProperties
    }

    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}