package com.example.mockserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/puzzle")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "/home/ubuntu/uploads/";

    @PostMapping("/picture/upload")
    public ResponseEntity<Map<String, String>> uploadPicture(@RequestBody ImageUploadRequest request) {
        try {
            String base64Data = request.getImageBase64();
            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            String fileName = "img_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".png";
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageBytes);

            String fileUrl = "http://ec2-3-37-89-181.ap-northeast-2.compute.amazonaws.com/uploads/" + fileName;

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "파일 저장 실패"));
        }
    }
}
