package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.ports.output.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/s3/files")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<Void> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String url = s3Service.uploadFile(file);
        URI location = URI.create(url);
        return ResponseEntity.created(location).build();
    }
}
