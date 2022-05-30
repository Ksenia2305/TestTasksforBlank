package execution

import execution.LaunchEnvironment.*

class ConfigStorage(
    val host: String,
    val schema: String = "https",
    val port: String = "443",


) {
    fun getRootUrl() = "$schema://$host"

    fun getRootUrlWithoutPort() = "$schema://$host"
}

enum class LaunchEnvironment {
    TEST
}

fun LaunchEnvironment.reset() {
    launchEnvironment = TEST
}

var launchEnvironment = TEST
    set(value) {
        field = value
        configStorage = setConfigStorage(field)
    }

var configStorage = setConfigStorage(launchEnvironment)

private fun setConfigStorage(launchEnvironment: LaunchEnvironment) =
    when (launchEnvironment) {
        TEST -> ConfigStorage("test-api-market.herokuapp.com")
    }



