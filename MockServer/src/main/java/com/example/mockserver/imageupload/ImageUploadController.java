package com.example.mockserver.imageupload;

import com.example.mockserver.Base64ImageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
public class ImageUploadController {

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadDir = "/home/ubuntu/app/images/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Directory creation failed!");
            }
        }
        File savedFile = new File(uploadDir + fileName);
        file.transferTo(savedFile);

        return "http://13.124.206.192/images/" + fileName;
    }

    @PostMapping("/uploadBase64")
    public String uploadBase64Image(@RequestBody Base64ImageRequest request) throws IOException {
        String uploadDir = "/home/ubuntu/app/images/";
        String fileName = System.currentTimeMillis() + ".png";

        // base64 디코딩
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(request.getImageBase64());
        java.nio.file.Files.write(java.nio.file.Paths.get(uploadDir + fileName), decodedBytes);

        return "http://13.124.206.192/images/" + fileName;
    }

}
