package com.langchain4j.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LocalAIController {

    Logger logger = LoggerFactory.getLogger(LocalAIController.class);

    @Autowired
    LocalAIService llmService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateSQLQuery(@RequestParam String prompt) {
        logger.info("Prompt: "+prompt);
        String response = llmService.chat(prompt);
        return ResponseEntity.ok(response);
    }

}
