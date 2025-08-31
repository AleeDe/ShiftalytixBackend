package com.shiftdeploy.effort_estemate.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class FileExtractor {

    private FileExtractor() {
        // Utility class, prevent instantiation
    }

    public static String extractText(MultipartFile file) throws Exception {
        if (file == null || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("File must not be null and must have a valid name.");
        }

        String filename = file.getOriginalFilename().toLowerCase();

        if (filename.endsWith(".pdf")) {
            try (InputStream is = file.getInputStream();
                 PDDocument document = PDDocument.load(is)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } else if (filename.endsWith(".txt")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } else if (filename.endsWith(".docx")) {
            try (InputStream is = file.getInputStream();
                 XWPFDocument doc = new XWPFDocument(is);
                 XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
                return extractor.getText();
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format. Please upload PDF, TXT, or DOCX.");
        }
    }
}