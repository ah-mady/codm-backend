package com.codmtracker.service;

import com.codmtracker.dto.ImportResultDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportServiceImpl implements ImportService {

    @Override
    public ImportResultDto importFromExcel(MultipartFile file, Long teamId) {
        ImportResultDto res = ImportResultDto.builder()
                .gamesImported(0)
                .playersImported(0)
                .errors(0)
                .build();
        return res;
    }
}
