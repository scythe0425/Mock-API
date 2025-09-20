package com.example.mockserver.imageupload;

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

        return "http://3.37.89.181/images/" + fileName;
    }
}
