// File generated from our OpenAPI spec by Stainless.

package com.openai.services.blocking.fineTuning

import com.openai.TestServerExtension
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.FineTuningJobCancelParams
import com.openai.models.FineTuningJobCreateParams
import com.openai.models.FineTuningJobListEventsParams
import com.openai.models.FineTuningJobRetrieveParams
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestServerExtension::class)
class JobServiceTest {

    @Test
    fun callCreate() {
        val client =
            OpenAIOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .apiKey("My API Key")
                .build()
        val jobService = client.fineTuning().jobs()
        val fineTuningJob =
            jobService.create(
                FineTuningJobCreateParams.builder()
                    .model(FineTuningJobCreateParams.Model.BABBAGE_002)
                    .trainingFile("file-abc123")
                    .hyperparameters(
                        FineTuningJobCreateParams.Hyperparameters.builder()
                            .batchSizeAuto()
                            .learningRateMultiplierAuto()
                            .nEpochsAuto()
                            .build()
                    )
                    .addIntegration(
                        FineTuningJobCreateParams.Integration.builder()
                            .wandb(
                                FineTuningJobCreateParams.Integration.Wandb.builder()
                                    .project("my-wandb-project")
                                    .entity("entity")
                                    .name("name")
                                    .addTag("custom-tag")
                                    .build()
                            )
                            .build()
                    )
                    .method(
                        FineTuningJobCreateParams.Method.builder()
                            .dpo(
                                FineTuningJobCreateParams.Method.Dpo.builder()
                                    .hyperparameters(
                                        FineTuningJobCreateParams.Method.Dpo.Hyperparameters
                                            .builder()
                                            .batchSizeAuto()
                                            .betaAuto()
                                            .learningRateMultiplierAuto()
                                            .nEpochsAuto()
                                            .build()
                                    )
                                    .build()
                            )
                            .supervised(
                                FineTuningJobCreateParams.Method.Supervised.builder()
                                    .hyperparameters(
                                        FineTuningJobCreateParams.Method.Supervised.Hyperparameters
                                            .builder()
                                            .batchSizeAuto()
                                            .learningRateMultiplierAuto()
                                            .nEpochsAuto()
                                            .build()
                                    )
                                    .build()
                            )
                            .type(FineTuningJobCreateParams.Method.Type.SUPERVISED)
                            .build()
                    )
                    .seed(42L)
                    .suffix("x")
                    .validationFile("file-abc123")
                    .build()
            )
        println(fineTuningJob)
        fineTuningJob.validate()
    }

    @Test
    fun callRetrieve() {
        val client =
            OpenAIOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .apiKey("My API Key")
                .build()
        val jobService = client.fineTuning().jobs()
        val fineTuningJob =
            jobService.retrieve(
                FineTuningJobRetrieveParams.builder()
                    .fineTuningJobId("ft-AF1WoRqd3aJAHsqc9NY7iL8F")
                    .build()
            )
        println(fineTuningJob)
        fineTuningJob.validate()
    }

    @Test
    fun callList() {
        val client =
            OpenAIOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .apiKey("My API Key")
                .build()
        val jobService = client.fineTuning().jobs()
        val listPaginatedFineTuningJobsResponse = jobService.list()
        println(listPaginatedFineTuningJobsResponse)
        listPaginatedFineTuningJobsResponse.data().forEach { it.validate() }
    }

    @Test
    fun callCancel() {
        val client =
            OpenAIOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .apiKey("My API Key")
                .build()
        val jobService = client.fineTuning().jobs()
        val fineTuningJob =
            jobService.cancel(
                FineTuningJobCancelParams.builder()
                    .fineTuningJobId("ft-AF1WoRqd3aJAHsqc9NY7iL8F")
                    .build()
            )
        println(fineTuningJob)
        fineTuningJob.validate()
    }

    @Test
    fun callListEvents() {
        val client =
            OpenAIOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .apiKey("My API Key")
                .build()
        val jobService = client.fineTuning().jobs()
        val listFineTuningJobEventsResponse =
            jobService.listEvents(
                FineTuningJobListEventsParams.builder()
                    .fineTuningJobId("ft-AF1WoRqd3aJAHsqc9NY7iL8F")
                    .build()
            )
        println(listFineTuningJobEventsResponse)
        listFineTuningJobEventsResponse.data().forEach { it.validate() }
    }
}
