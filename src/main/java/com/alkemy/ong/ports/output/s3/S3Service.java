package com.alkemy.ong.ports.output.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file);
    String uploadFile(String fileBase64, String fileName);
}
