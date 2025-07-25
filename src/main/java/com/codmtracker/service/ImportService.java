package com.codmtracker.service;

import com.codmtracker.dto.ImportResultDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    ImportResultDto importFromExcel(MultipartFile file, Long teamId);
}
