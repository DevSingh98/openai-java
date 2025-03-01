// File generated from our OpenAPI spec by Stainless.

package com.openai.services.blocking.beta

import com.openai.core.ClientOptions
import com.openai.core.RequestOptions
import com.openai.core.handlers.errorHandler
import com.openai.core.handlers.jsonHandler
import com.openai.core.handlers.withErrorHandler
import com.openai.core.http.Headers
import com.openai.core.http.HttpMethod
import com.openai.core.http.HttpRequest
import com.openai.core.http.HttpResponse.Handler
import com.openai.core.json
import com.openai.core.prepare
import com.openai.errors.OpenAIError
import com.openai.models.Assistant
import com.openai.models.AssistantDeleted
import com.openai.models.BetaAssistantCreateParams
import com.openai.models.BetaAssistantDeleteParams
import com.openai.models.BetaAssistantListPage
import com.openai.models.BetaAssistantListParams
import com.openai.models.BetaAssistantRetrieveParams
import com.openai.models.BetaAssistantUpdateParams

class AssistantServiceImpl internal constructor(private val clientOptions: ClientOptions) :
    AssistantService {

    companion object {

        private val DEFAULT_HEADERS = Headers.builder().put("OpenAI-Beta", "assistants=v2").build()
    }

    private val errorHandler: Handler<OpenAIError> = errorHandler(clientOptions.jsonMapper)

    private val createHandler: Handler<Assistant> =
        jsonHandler<Assistant>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Create an assistant with a model and instructions. */
    override fun create(
        params: BetaAssistantCreateParams,
        requestOptions: RequestOptions,
    ): Assistant {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("assistants")
                .putAllHeaders(DEFAULT_HEADERS)
                .body(json(clientOptions.jsonMapper, params._body()))
                .build()
                .prepare(clientOptions, params, params.model().toString())
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { createHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }

    private val retrieveHandler: Handler<Assistant> =
        jsonHandler<Assistant>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Retrieves an assistant. */
    override fun retrieve(
        params: BetaAssistantRetrieveParams,
        requestOptions: RequestOptions,
    ): Assistant {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("assistants", params.getPathParam(0))
                .putAllHeaders(DEFAULT_HEADERS)
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

    private val updateHandler: Handler<Assistant> =
        jsonHandler<Assistant>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Modifies an assistant. */
    override fun update(
        params: BetaAssistantUpdateParams,
        requestOptions: RequestOptions,
    ): Assistant {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("assistants", params.getPathParam(0))
                .putAllHeaders(DEFAULT_HEADERS)
                .body(json(clientOptions.jsonMapper, params._body()))
                .build()
                .prepare(clientOptions, params, params.model().map { it.toString() }.orElse(null))
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { updateHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }

    private val listHandler: Handler<BetaAssistantListPage.Response> =
        jsonHandler<BetaAssistantListPage.Response>(clientOptions.jsonMapper)
            .withErrorHandler(errorHandler)

    /** Returns a list of assistants. */
    override fun list(
        params: BetaAssistantListParams,
        requestOptions: RequestOptions,
    ): BetaAssistantListPage {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("assistants")
                .putAllHeaders(DEFAULT_HEADERS)
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
            .let { BetaAssistantListPage.of(this, params, it) }
    }

    private val deleteHandler: Handler<AssistantDeleted> =
        jsonHandler<AssistantDeleted>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /** Delete an assistant. */
    override fun delete(
        params: BetaAssistantDeleteParams,
        requestOptions: RequestOptions,
    ): AssistantDeleted {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.DELETE)
                .addPathSegments("assistants", params.getPathParam(0))
                .putAllHeaders(DEFAULT_HEADERS)
                .apply { params._body().ifPresent { body(json(clientOptions.jsonMapper, it)) } }
                .build()
                .prepare(clientOptions, params, deploymentModel = null)
        val response = clientOptions.httpClient.execute(request, requestOptions)
        return response
            .use { deleteHandler.handle(it) }
            .also {
                if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                    it.validate()
                }
            }
    }
}
