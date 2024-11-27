package com.nohnari.missingchild.domain.child.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadImage(MultipartFile image, String folderPath);
    void deleteImage(String imageAddress);
}
