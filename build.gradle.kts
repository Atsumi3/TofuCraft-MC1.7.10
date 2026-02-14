plugins {
    id("java")
    id("net.neoforged.moddev") version "2.0.78-beta"
}

version = project.property("mod_version") as String
group = project.property("mod_group_id") as String

repositories {
    mavenLocal()
    maven { url = uri("https://maven.neoforged.net/releases") }
}

base {
    archivesName.set(project.property("mod_id") as String)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(project.property("java_version") as String))

neoForge {
    version = project.property("neo_version") as String

    parchment {
        minecraftVersion = project.property("parchment_minecraft_version") as String
        mappingsVersion = project.property("parchment_mappings_version") as String
    }

    runs {
        register("client") {
            client()
        }
        register("server") {
            server()
        }
        register("data") {
            data()
            programArguments.addAll(
                "--mod", project.property("mod_id") as String,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }
    }

    mods {
        register(project.property("mod_id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

dependencies {
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
        "minecraft_version" to project.property("minecraft_version"),
        "neo_version" to project.property("neo_version"),
        "mod_id" to project.property("mod_id"),
        "mod_name" to project.property("mod_name"),
        "mod_license" to project.property("mod_license"),
        "mod_version" to project.property("mod_version"),
        "mod_authors" to project.property("mod_authors"),
        "mod_description" to project.property("mod_description")
    )
    inputs.properties(replaceProperties)
    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand(replaceProperties)
    }
}
