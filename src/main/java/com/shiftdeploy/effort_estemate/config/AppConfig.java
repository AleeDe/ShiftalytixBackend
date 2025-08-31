package com.shiftdeploy.effort_estemate.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    private final String frontendUrl;
    private final String openAiKey;
    private final String model;


    public AppConfig(Dotenv dotenv) {
        this.frontendUrl = dotenv.get("FRONTEND_URL");
        this.openAiKey = dotenv.get("OPENAI_API_KEY");
        this.model = dotenv.get("MODEL");
        
    }

    public String getFrontendUrl() { return frontendUrl; }
    public String getOpenAiKey() { return openAiKey; }
    public String getModel() { return model; }
    
}
