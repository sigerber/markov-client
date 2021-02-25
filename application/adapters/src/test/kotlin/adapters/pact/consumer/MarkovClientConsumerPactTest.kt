package adapters.pact.consumer

import adapters.IntegrationTestContext.withApp
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.ktor.ext.inject
import ports.requires.MarkovGeneratorClient

@ExtendWith(PactConsumerTestExt::class)
class MarkovClientConsumerPactTest {

    @Pact(provider = "markov-generator", consumer = "markov-client")
    fun newModelProvider(builder: PactDslWithProvider) = builder
        .given("no model exists")
        .uponReceiving("create new model")
            .path("/model")
            .method("POST")
        .willRespondWith()
            .status(200)
            .body(PactDslJsonBody()
                .stringType("id", "1234-5678")
            )
        .toPact()

    @Test
    @PactTestFor(providerName = "markov-generator", port = "8080")
    fun `should create model and return id`() {
        withApp {
            val generatorClient: MarkovGeneratorClient by application.inject()

            val id = generatorClient.createModel()
            id shouldBe "1234-5678"
        }
    }
}

