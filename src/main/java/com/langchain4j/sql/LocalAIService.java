package com.langchain4j.sql;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import dev.langchain4j.model.output.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


@Service
public class LocalAIService {

    Logger logger = LoggerFactory.getLogger(LocalAIService.class);

    @Value("${localai.baseURL}")
    private String baseURL;

    @Value("${localai.modelName}")
    private String modelName;

    @Value("${localai.temperature}")
    private Double temperature;

    @Value("${localai.timout.minutes}")
    private Integer timout;


        public String chat(String prompt) {
        Instant start = Instant.now();
        logger.info("Query started: "+start);
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl(baseURL)
                .modelName(modelName)
                .temperature(temperature)
                .timeout(Duration.ofMinutes(timout))
                .logRequests(true)
                .logResponses(true)
                .build();

        SystemMessage responseInDutch = new SystemMessage(prompt);
        var chatMessages = new ArrayList<ChatMessage>();
        chatMessages.add(responseInDutch);
        logger.info("Processing request.....");
        Response<AiMessage> response = model.generate(chatMessages);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMinutes();
        logger.info("Query end: "+finish);
        logger.info("Total minutes took: "+ timeElapsed);
        logger.info(response.content().text());
        logger.info("Processing completed.");
        return response.content().text();
    }
}
