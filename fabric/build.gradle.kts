import com.blamejared.Properties
import com.blamejared.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Constants

plugins {
    id("blamejared-modloader-conventions")
    id("net.fabricmc.fabric-loom") version "1.16.0-alpha.10"
    id("com.modrinth.minotaur")
}

repositories {
    maven { name = "Terraformers"; url = uri("https://maven.terraformersmc.com/")}
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
    implementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    implementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC}")
    implementation("com.terraformersmc:modmenu:${Versions.MODMENU}")
}

loom {
    mixin {
        defaultRefmapName.set("${Properties.MODID}.refmap.json")
    }
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks.register<TaskPublishCurseForge>("publishCurseForge") {
    dependsOn(tasks.jar)
    apiToken = GMUtils.locateProperty(project, "curseforgeApiToken")

    val mainFile = upload(Properties.CURSE_PROJECT_ID, tasks.jar.get().archiveFile)
    mainFile.changelogType = "markdown"
    mainFile.releaseType = CFG_Constants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.JAVA}")
    mainFile.addGameVersion(Versions.MINECRAFT)
    mainFile.addOptional("modmenu")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    versionName.set("${Properties.NAME}-${Versions.MINECRAFT}-$version (Fabric)")
    versionType.set("release")
    uploadFile.set(tasks.jar.get())
    dependencies {
        optional.project("mOgUt4GM") // Modmenu
    }
}
tasks.modrinth.get().dependsOn(tasks.jar)

tasks.named("publishCurseForge") {
    group = "publishing"
}