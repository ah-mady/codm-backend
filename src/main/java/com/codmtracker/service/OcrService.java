package com.codmtracker.service;

import com.codmtracker.dto.OcrResultDto;
import org.springframework.web.multipart.MultipartFile;

public interface OcrService {
    OcrResultDto processGameScreenshot(MultipartFile file, Long teamId);
}
