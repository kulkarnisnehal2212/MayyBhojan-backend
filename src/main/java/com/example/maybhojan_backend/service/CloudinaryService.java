package com.example.maybhojan_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile multipartFile) {

        try {

            // create temporary file
            File tempFile = File.createTempFile("upload_", ".tmp");

            // transfer multipart file to temp file
            multipartFile.transferTo(tempFile);

            // upload to cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    tempFile,
                    ObjectUtils.asMap("resource_type", "auto")
            );

            // delete temp file after upload
            tempFile.delete();

            // return uploaded file URL
            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}