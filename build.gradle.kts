import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.utils.asPath
import kotlin.io.path.absolutePathString

fun prop(name: String) = providers.gradleProperty(name).get()

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.6.1")
    }
}

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()
sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/gen")
        }
    }
}
// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(17)
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    testImplementation(libs.junit)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        pluginVerifier()
        zipSigner()
        testFramework(TestFrameworkType.Platform)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            untilBuild = providers.gradleProperty("pluginUntilBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

// Configure Gradle Kover Plugin - read more: https://github.com/Kotlin/kotlinx-kover#configuration
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }

    // https://plugins.jetbrains.com/docs/marketplace/obfuscate-the-plugin.html#proguard
    // see https://www.guardsquare.com/manual/setup/gradle
    register<proguard.gradle.ProGuardTask>("proguard") {
        dependsOn(buildPlugin)
        dependsOn(instrumentedJar)
        verbose()

        val javaHome = System.getProperty("java.home")
        File("$javaHome/jmods/").listFiles().forEach { libraryjars(it.absolutePath) }

        // Use the jar task output as a input jar. This will automatically add the necessary task dependency.
        val pluginName = rootProject.name + "-" + prop("pluginVersion")
        val inputDir = "build/libs/"
        val outputDir = "build/distributions/"

        injars("$inputDir$pluginName-instrumented.jar")
        outjars("$outputDir$pluginName-obfuscated.jar")

        libraryjars(configurations.compileClasspath.get())

        dontshrink()
        dontoptimize()
//        dontpreverify()
//        dontskipnonpubliclibraryclassmembers()

        adaptclassstrings("**.xml")
        adaptresourcefilecontents("**.xml")

        // Allow methods with the same signature, except for the return type,
        // to get the same obfuscation name.
        overloadaggressively()

        // Put all obfuscated classes into the nameless root package.
        repackageclasses("")
        dontwarn()

        printmapping("$outputDir$pluginName-ProGuard-ChangeLog.txt")
        target(prop("pluginVersion"))

        adaptresourcefilenames()
        optimizationpasses(9)
        allowaccessmodification()
//        mergeinterfacesaggressively()

        keepattributes("Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod")

        keep(
            """
            class * implements com.intellij.openapi.components.PersistentStateComponent {*;}
            """.trimIndent()
        )

        keepclassmembers(
            """
            class * {public static ** INSTANCE;}
            """.trimIndent()
        )
        keepclassmembers(
            """
            enum * {
                <fields>;
            }
            """.trimIndent()
        )
        keepclassmembers(
            """
            enum * {
                public static **[] values();
                public static ** valueOf(java.lang.String);
            }
            """.trimIndent()
        )
        keep("class com.intellij.util.* {*;}")

        println("Output directory: ${layout.projectDirectory.dir(outputDir).asPath.absolutePathString()}:1")
    }
}

intellijPlatformTesting {
    runIde {
        register("runIdeForUiTests") {
            task {
                jvmArgumentProviders += CommandLineArgumentProvider {
                    listOf(
                        "-Drobot-server.port=8082",
                        "-Dide.mac.message.dialogs.as.sheets=false",
                        "-Djb.privacy.policy.text=<!--999.999-->",
                        "-Djb.consents.confirmation.enabled=false",
                    )
                }
            }

            plugins {
                robotServerPlugin()
            }
        }
    }
}
