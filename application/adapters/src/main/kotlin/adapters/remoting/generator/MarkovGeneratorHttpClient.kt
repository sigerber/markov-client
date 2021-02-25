package adapters.remoting.generator

import adapters.remoting.HttpClientFactory
import io.ktor.client.request.*
import ports.requires.MarkovGeneratorClient

internal class MarkovGeneratorHttpClient(httpClientFactory: HttpClientFactory) : MarkovGeneratorClient {
    private val client = httpClientFactory.httpClient()

    override suspend fun createModel(): String {
        val response = client.post<CreateModelResponse>(port = 8080, path = "/model")

        return response.id
    }
}

data class CreateModelResponse(val id: String)