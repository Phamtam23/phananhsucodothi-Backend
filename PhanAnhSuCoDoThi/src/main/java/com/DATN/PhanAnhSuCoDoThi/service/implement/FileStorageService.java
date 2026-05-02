package com.DATN.PhanAnhSuCoDoThi.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            "image/jpeg", "image/png", "image/jpg", "image/webp"
    );
    private static final List<String> ALLOWED_VIDEO_TYPES = List.of(
            "video/mp4", "video/mpeg", "video/quicktime", "video/x-msvideo"
    );

    public List<String> saveFiles(List<MultipartFile> files, String type) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(saveFile(file, type));
        }
        return urls;
    }

    public String saveFile(MultipartFile file, String type) {
        validateFile(file);
        try {
            String subFolder = type + "/" + (isImage(file) ? "images" : "videos");
            Path uploadPath = Paths.get(uploadDir, subFolder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID() + "." + extension;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ← Thêm dòng này để xem đường dẫn thật
            System.out.println("File saved at: " + filePath.toAbsolutePath());

            return "/" + uploadDir + "/" + subFolder + "/" + newFileName;

        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu file: " + e.getMessage());
        }
    }
    // ===================== XÓA FILE =====================
    public void deleteFile(String fileUrl) {
        try {
            Path filePath = Paths.get(fileUrl.substring(1)); // bỏ dấu / đầu
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể xóa file: " + e.getMessage());
        }
    }
    // ===================== VALIDATE =====================
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new RuntimeException("File không được để trống");

        String contentType = file.getContentType();
        if (!ALLOWED_IMAGE_TYPES.contains(contentType) && !ALLOWED_VIDEO_TYPES.contains(contentType))
            throw new RuntimeException("Định dạng file không được hỗ trợ: " + contentType);

        // Giới hạn dung lượng: ảnh 10MB, video 100MB
        long maxSize = isImage(file) ? 10 * 1024 * 1024L : 100 * 1024 * 1024L;
        if (file.getSize() > maxSize)
            throw new RuntimeException("File vượt quá dung lượng cho phép");
    }

    private boolean isImage(MultipartFile file) {
        return ALLOWED_IMAGE_TYPES.contains(file.getContentType());
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains("."))
            return "bin";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
