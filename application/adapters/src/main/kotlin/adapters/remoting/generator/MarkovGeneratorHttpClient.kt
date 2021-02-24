package adapters.remoting.generator

import adapters.remoting.HttpClientFactory
import ports.requires.MarkovGeneratorClient

internal class MarkovGeneratorHttpClient(private val httpClientFactory: HttpClientFactory) : MarkovGeneratorClient  {
    override fun createModel(): String {
        TODO("Not yet implemented")
    }
}