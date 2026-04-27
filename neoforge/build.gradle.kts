import com.blamejared.Properties
import com.blamejared.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import net.darkhax.curseforgegradle.Constants
import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("blamejared-modloader-conventions")
    id("net.neoforged.moddev") version ("2.0.141")
    id("com.modrinth.minotaur")
}

neoForge {
    version = Versions.NEO_FORGE
//     accessTransformers.add(file('src/main/resources/META-INF/accesstransformer.cfg'))
    runs {
        register("client") {
            client()
        }
        register("server") {
            server()
            programArgument("--nogui")
        }
    }

    mods {
        register(Properties.MODID) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
}

tasks.register<TaskPublishCurseForge>("publishCurseForge") {
    dependsOn(tasks.jar)
    apiToken = GMUtils.locateProperty(project, "curseforgeApiToken") ?: 0

    val mainFile = upload(Properties.CURSE_PROJECT_ID, tasks.jar.get().archiveFile)
    mainFile.changelogType = "markdown"
    mainFile.changelog = ""
    mainFile.releaseType = Constants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.JAVA}")
    mainFile.addGameVersion(Versions.MINECRAFT)
    mainFile.addModLoader("NeoForge")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    versionName.set("${Properties.NAME}-${Versions.MINECRAFT}-$version (NeoForge)")
    versionType.set("release")
    gameVersions.set(listOf(Versions.MINECRAFT))
    uploadFile.set(tasks.jar.get())
    loaders.add("neoforge")
}
tasks.modrinth.get().dependsOn(tasks.jar)

tasks.named("publishCurseForge") {
    group = "publishing"
}
