package com.parksathi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStorageUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) {

        try {
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            File destination = new File(dir, fileName);

            file.transferTo(destination);

            return destination.getAbsolutePath();

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file");
        }
    }
}