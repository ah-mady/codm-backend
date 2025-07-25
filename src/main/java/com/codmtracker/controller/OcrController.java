package com.codmtracker.controller;

import com.codmtracker.dto.OcrResultDto;
import com.codmtracker.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/screenshot")
    public OcrResultDto processScreenshot(@RequestParam("file") MultipartFile file, @RequestParam Long teamId) {
        return ocrService.processGameScreenshot(file, teamId);
    }
}
