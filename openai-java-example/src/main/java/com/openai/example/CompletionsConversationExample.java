package com.openai.example;

import static java.util.stream.Collectors.toList;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import java.util.List;

public final class CompletionsConversationExample {
    private CompletionsConversationExample() {}

    public static void main(String[] args) {
        // Configures using one of:
        // - The `OPENAI_API_KEY` environment variable
        // - The `AZURE_OPENAI_ENDPOINT` and `AZURE_OPENAI_KEY` environment variables
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        // Use a builder so that we can append more messages to it below.
        // Each time we call .build()` we get an immutable object that's unaffected by future mutations of the builder.
        ChatCompletionCreateParams.Builder createParamsBuilder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_3_5_TURBO)
                .maxCompletionTokens(2048)
                .addMessage(ChatCompletionDeveloperMessageParam.builder()
                        .content("Make sure you mention Stainless!")
                        .build())
                .addMessage(ChatCompletionUserMessageParam.builder()
                        .content("Tell me a story about building the best SDK!")
                        .build());

        for (int i = 0; i < 4; i++) {
            List<ChatCompletionMessage> messages =
                    client.chat().completions().create(createParamsBuilder.build()).choices().stream()
                            .map(ChatCompletion.Choice::message)
                            .collect(toList());

            messages.stream().flatMap(message -> message.content().stream()).forEach(System.out::println);

            System.out.println("\n-----------------------------------\n");

            messages.forEach(createParamsBuilder::addMessage);
            createParamsBuilder
                    .addMessage(ChatCompletionDeveloperMessageParam.builder()
                            .content("Be as snarky as possible when replying!" + "!".repeat(i))
                            .build())
                    .addMessage(ChatCompletionUserMessageParam.builder()
                            .content("But why?" + "?".repeat(i))
                            .build());
        }
    }
}
