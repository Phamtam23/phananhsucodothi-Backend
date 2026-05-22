package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.service.implement.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;
    @PostMapping("/upload")  // ← bỏ consumes
    public ResponseEntity<?> upload(
            @RequestParam("files") List<MultipartFile> files,  // ← @RequestParam
            @RequestParam(value = "type", defaultValue = "suco") String type) {
        List<String> urls = fileStorageService.saveFiles(files, type);
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Upload thành công",
                "data", urls
        ));
    }
}
