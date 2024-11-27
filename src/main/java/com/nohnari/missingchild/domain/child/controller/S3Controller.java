package com.nohnari.missingchild.domain.child.controller;

import com.nohnari.missingchild.domain.child.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/s3/upload")
    public ResponseEntity<String> s3Upload(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam("folderPath") String folderPath) {
        String fileName = s3Service.uploadImage(image, folderPath);
        return ResponseEntity.ok().body(fileName);
    }

    @DeleteMapping("/s3/delete")
    public ResponseEntity<String> s3Delete(@RequestParam String imageAddress) {
        s3Service.deleteImage(imageAddress);
        return ResponseEntity.ok().body("삭제 완료: " + imageAddress);
    }

}
