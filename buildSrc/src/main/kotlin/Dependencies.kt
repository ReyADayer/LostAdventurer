object Dependencies {
    object Kotlin {
        val version = "1.4.0"
        val classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Spigot {
        val version = "1.15.2-R0.1-SNAPSHOT"
        val api = "org.spigotmc:spigot-api:$version"
        val annotations = "org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT"
        val repository = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    }

    object Citizens2 {
        val version = "2.0.27-SNAPSHOT"
        val core = "net.citizensnpcs:citizensapi:$version"
        val api = "net.citizensnpcs:citizens:$version"
        val repository = "http://repo.citizensnpcs.co/"
    }

    object SonaType {
        val repository = "https://oss.sonatype.org/content/groups/public/"
    }

    object Rx {
        val java = "io.reactivex.rxjava2:rxjava:2.2.17"
    }

    object JUnit {
        val core = "org.junit.jupiter:junit-jupiter:5.5.2"
    }
}