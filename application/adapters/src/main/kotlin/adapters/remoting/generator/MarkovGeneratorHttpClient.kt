package adapters.remoting.generator

import adapters.config.AppConfig
import adapters.remoting.HttpClientFactory
import io.ktor.client.request.*
import ports.requires.MarkovGeneratorClient

internal class MarkovGeneratorHttpClient(httpClientFactory: HttpClientFactory, appConfig: AppConfig) : MarkovGeneratorClient {
    private val client = httpClientFactory.httpClient()
    private val clientConfig = appConfig.markovGeneratorClient

    override suspend fun createModel(): String {
        val response = client.post<CreateModelResponse>(
            scheme = clientConfig.scheme,
            host = clientConfig.host,
            port = clientConfig.port,
            path = "/model"
        )

        return response.id
    }
}

data class CreateModelResponse(val id: String)