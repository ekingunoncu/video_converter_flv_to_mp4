package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.Setter;

/**
 * Service class for uploading files to Amazon S3.
 */
@Service
@Setter
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Autowired
    public S3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Uploads the given file bytes to Amazon S3 with the given file name and
     * returns
     * the URL of the uploaded file.
     * 
     * @param fileBytes The byte array of the file to upload.
     * @param fileName  The name of the file to upload.
     * @return The URL of the uploaded file.
     */
    public String uploadFileToS3(byte[] fileBytes, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);

        // Creates a PutObjectRequest with the specified bucket name, file name,
        // file input stream, and metadata.
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                new ByteArrayInputStream(fileBytes), metadata);

        // Uploads the file to Amazon S3.
        amazonS3.putObject(putObjectRequest);

        // Returns the URL of the uploaded file.
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    
    }
}
