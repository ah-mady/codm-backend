package com.codmtracker.service;

import com.codmtracker.dto.OcrResultDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OcrServiceImpl implements OcrService {

    @Override
    public OcrResultDto processGameScreenshot(MultipartFile file, Long teamId) {
        OcrResultDto res = OcrResultDto.builder().build();
        return res;
    }
}
