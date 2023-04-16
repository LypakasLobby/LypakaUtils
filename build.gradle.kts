plugins {
    id("java")
    id("dev.architectury.loom") version ("0.12.0-SNAPSHOT")
}

group = "com.lypaka"
version = "1.19.2-0.0.9"
base {
    archivesName.set("LypakaUtils")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

loom {
    silentMojangMappingsLicense()
}

repositories {
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    minecraft("net.minecraft:minecraft:1.19.2")
    mappings(loom.officialMojangMappings())
    forge("net.minecraftforge:forge:1.19.2-43.2.0")
    modImplementation("ca.stellardrift:confabricate:3.0.0-SNAPSHOT")
    include("ca.stellardrift:confabricate:3.0.0-SNAPSHOT")


}

// Example for how to get properties into the manifest for reading at runtime.
//jar {
//    manifest {
//        attributes([
//                "Specification-Title": "lypakautils",
//                            //"Specification-Vendor": "lypakautils authors",
//                            "Specification-Version": "1", // We are version 1 of ourselves
//                "Implementation-Title": project.name,
//                "Implementation-Version": project.jar.archiveVersion,
//                           //"Implementation-Vendor": "lypakautils authors",
//                           "Implementation-Timestamp": new Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
//        ])
//    }
//}
//}