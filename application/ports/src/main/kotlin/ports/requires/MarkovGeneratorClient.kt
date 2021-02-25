package ports.requires

interface MarkovGeneratorClient {

    suspend fun createModel(): String
}