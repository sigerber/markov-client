package ports.requires

interface MarkovGeneratorClient {

    fun createModel(): String
}