package adapters

import adapters.config.EnvironmentVariables

class EnvironmentVariablesTestImpl : EnvironmentVariables {
    override val deployment: String
        get() = "test"
}
