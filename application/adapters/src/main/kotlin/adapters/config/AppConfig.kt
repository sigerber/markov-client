package adapters.config

import com.typesafe.config.Config
import java.util.Properties

/**
 * Central repository of all application's settings.
 */
class AppConfig(configRepository: ConfigRepository) {

    // you can keep your application-specific data in main.conf
    // "application.conf" is already reserved by ktor and we don't want to keep our configs in there,
    // otherwise we are going to have more complicated code

    private val config = configRepository.config.getConfig("app-config")

    val deployment by lazy {
        Deployment.create(config = config.getConfig("deployment"))
    }

    val markovGeneratorClient by lazy {
        MarkovGeneratorClient.create(config = config.getConfig("markov-generator"))
    }

    class Deployment private constructor(private val config: Config) {
        val env: String by lazy {
            config.getString("env")
        }
        val version: String by lazy {
            config.getString("version")
        }

        companion object {
            internal fun create(config: Config) = Deployment(config = config)
        }
    }

    class MarkovGeneratorClient private constructor(private val config: Config) {
        val scheme: String by lazy {
            config.getString("scheme")
        }

        val host: String by lazy {
            config.getString("host")
        }

        val port: Int by lazy {
            config.getInt("port")
        }

        companion object {
            internal fun create(config: Config) = MarkovGeneratorClient(config = config)
        }
    }

}

// Convert HOCON Config object into Properties (some libraries, e.g. HikariCP don't understand HOCON format).
private fun Config.toProperties() = Properties().also {
    for (e in this.entrySet()) {
        it.setProperty(e.key, this.getString(e.key))
    }
}
