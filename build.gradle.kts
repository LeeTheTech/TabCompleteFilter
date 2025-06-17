plugins {
  id("java")
}

group = "dev.lee.tcf"
version = "1.3.0"

tasks.compileJava {
  options.release.set(16)
  options.encoding = Charsets.UTF_8.name()
}

tasks.processResources {
  val props = mapOf("version" to project.version)
  inputs.properties(props)
  filesMatching("plugin.yml") {
    expand(props)
  }
}

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://libraries.minecraft.net")
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  // Spigot
  compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
  compileOnly("com.mojang:authlib:3.13.56")

  // Lombok
  compileOnly("org.projectlombok:lombok:1.18.38")
  annotationProcessor("org.projectlombok:lombok:1.18.38")
}
