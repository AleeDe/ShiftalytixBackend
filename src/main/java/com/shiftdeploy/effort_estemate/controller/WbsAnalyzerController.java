package com.shiftdeploy.effort_estemate.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.shiftdeploy.effort_estemate.service.WbsAnalyzerService;
import com.shiftdeploy.effort_estemate.util.FileExtractor;

@RestController
@RequestMapping("/api/wbs")
public class WbsAnalyzerController {


    private final WbsAnalyzerService analyzerService;

    public WbsAnalyzerController(WbsAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    // For manual input
    @PostMapping("/analyze/text")
    public ResponseEntity<String> analyzeWbsText(@RequestBody String wbsText) {
        String result = analyzerService.analyzeWbs(wbsText);
        return ResponseEntity.ok(result);
    }

    // For file upload
    @PostMapping("/analyze/file")
    public ResponseEntity<String> analyzeWbsFile(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = FileExtractor.extractText(file);
            String result = analyzerService.analyzeWbs(extractedText);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Failed to process file: " + e.getMessage());
        }
    }
}