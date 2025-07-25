package com.codmtracker.controller;

import com.codmtracker.dto.ImportResultDto;
import com.codmtracker.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/excel")
    public ImportResultDto importExcel(@RequestParam("file") MultipartFile file, @RequestParam Long teamId) {
        return importService.importFromExcel(file, teamId);
    }
}
