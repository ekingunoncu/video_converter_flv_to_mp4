package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.Setter;

@Service
@Setter
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Autowired
    public S3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFileToS3(byte[] fileBytes, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                new ByteArrayInputStream(fileBytes), metadata);

        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}
