plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.PBW"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.0.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation ("javax.servlet:javax.servlet-api:4.0.1")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-validation")

	// JFreeChart untuk chart
    implementation("org.jfree:jfreechart:1.5.3")

    // iText untuk eksport ke PDF (jika diperlukan)
    implementation("com.itextpdf:itext7-core:7.2.5")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
