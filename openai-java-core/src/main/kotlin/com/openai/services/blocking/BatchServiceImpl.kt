// File generated from our OpenAPI spec by Stainless.

package com.openai.services.blocking

import com.openai.core.ClientOptions
import com.openai.core.RequestOptions
import com.openai.core.handlers.errorHandler
import com.openai.core.handlers.jsonHandler
import com.openai.core.handlers.withErrorHandler
import com.openai.core.http.HttpMethod
import com.openai.core.http.HttpRequest
import com.openai.core.http.HttpResponse.Handler
import com.openai.core.json
import com.openai.core.prepare
import com.openai.errors.OpenAIError
import com.openai.models.Batch
import com.openai.models.BatchCancelParams
import com.openai.models.BatchCreateParams
import com.openai.models.BatchListPage
import com.openai.models.BatchListParams
import com.openai.models.BatchRetrieveParams

class BatchServiceImpl internal constructor(private val clientOptions: ClientOptions) :
    BatchService {

    private val errorHandler: Handler<OpenAIError> = errorHandler(clientOptions.jsonMapper)

    private val createHandler: Handler<Batch> =
        jsonHandler<Batch>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Creates and executes a batch from an uploaded file of requests */
    override fun create(params: BatchCreateParams, requestOptions: RequestOptions): Batch {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("batches")
                .body(json(clientOptions.jsonMapper, params._body()))
                .build()
                .prepare(clientOptions, params, deploymentModel = null)
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { createHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }

    private val retrieveHandler: Handler<Batch> =
        jsonHandler<Batch>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Retrieves a batch. */
    override fun retrieve(params: BatchRetrieveParams, requestOptions: RequestOptions): Batch {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("batches", params.getPathParam(0))
                .build()
                .prepare(clientOptions, params, deploymentModel = null)
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { retrieveHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }

    private val listHandler: Handler<BatchListPage.Response> =
        jsonHandler<BatchListPage.Response>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** List your organization's batches. */
    override fun list(params: BatchListParams, requestOptions: RequestOptions): BatchListPage {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("batches")
                .build()
                .prepare(clientOptions, params, deploymentModel = null)
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { listHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
            .let { BatchListPage.of(this, params, it) }
    }

    private val cancelHandler: Handler<Batch> =
        jsonHandler<Batch>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /**
     * Cancels an in-progress batch. The batch will be in status `cancelling` for up to 10 minutes,
     * before changing to `cancelled`, where it will have partial results (if any) available in the
     * output file.
     */
    override fun cancel(params: BatchCancelParams, requestOptions: RequestOptions): Batch {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("batches", params.getPathParam(0), "cancel")
                .apply { params._body().ifPresent { body(json(clientOptions.jsonMapper, it)) } }
                .build()
                .prepare(clientOptions, params, deploymentModel = null)
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { cancelHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }
}
