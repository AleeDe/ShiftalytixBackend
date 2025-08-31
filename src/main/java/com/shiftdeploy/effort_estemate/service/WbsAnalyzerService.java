package com.shiftdeploy.effort_estemate.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class WbsAnalyzerService {

    private final ChatClient chatClient;

    public WbsAnalyzerService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String analyzeWbs(String wbsText) {
        String prompt = """
            You are a software metrics expert. Analyze this WBS and return **JSON** with the following structure:
            {
              "reportText": ["array"],       // Full detailed explanation GPT generates
              "functions": {
                "EI": {"Easy": int, "Medium": int, "Hard": int},
                "EO": {"Easy": int, "Medium": int, "Hard": int},
                "EQ": {"Easy": int, "Medium": int, "Hard": int},
                "ILF": {"Easy": int, "Medium": int, "Hard": int},
                "EIF": {"Easy": int, "Medium": int, "Hard": int}
              },  the  is may every function ke be pf defone karo table form may  or desciption may add karo is ka scorce kia hai easy hai ya hard etc
              "UFP": int,
              "GSC": {"factor1": int, ..., "factor14": int},
              "CAF": float,
              "AFP": float,
              "SLOC": int,
              "teamMembers": int,
              "effortPersonMonths": float,
              "timelineMonths": float,
              "perPersonBreakdown": [
                  {"role": "Frontend", "members": int, "effortPM": float, "timelineMonths": float},
                  {"role": "Backend", "members": int, "effortPM": float, "timelineMonths": float},
                  {"role": "Tester", "members": int, "effortPM": float, "timelineMonths": float}
              ]
               {
              "reportText": ["array"],           // Step-by-step detailed explanation in simple words for non-technical people
              "projectSize": "string",           // Define project size (Small/Medium/Large) based on total SLOC and also effort based and give actual size
              "approachAnalysis": {              // Explain estimation approaches
                "TopDown": "string",
                "BottomUp": "string",
                "AlgorithmicLibrary": "string",
                "RecommendedApproach": "string"
              },
            }

            Include in "reportText" a **step-by-step explanation** of all calculations and analysis.
            Do not omit any details.

            WBS Content:
            """ + wbsText;

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
