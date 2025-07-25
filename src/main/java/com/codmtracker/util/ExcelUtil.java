package com.codmtracker.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static List<Map<String, String>> readExcel(MultipartFile file) {
        return List.of();
    }

    public static byte[] writeExcel(List<Map<String, Object>> data) {
        return new byte[0];
    }
}
