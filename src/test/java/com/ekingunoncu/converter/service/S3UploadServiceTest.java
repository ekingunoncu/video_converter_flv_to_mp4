package com.ekingunoncu.converter.service;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
public class S3UploadServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3UploadService s3UploadService;

    @Captor
    private ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor;

    @BeforeEach
    public void setup() {
        s3UploadService.setBucketName("test-bucket");
    }

    /**
     * Test that the S3UploadService can upload a file to S3.
     * 
     * @throws IOException if an I/O error occurs while uploading the file
     */
    @Test
    public void testUploadFileToS3() throws IOException {
        // Create a byte array to simulate a file
        byte[] fileBytes = "test file".getBytes();
        // Create a file name
        String fileName = "test.txt";

        // Call the method under test
        s3UploadService.uploadFileToS3(fileBytes, fileName);

        // Verify that the putObject method was called with the expected arguments
        verify(amazonS3).putObject(putObjectRequestCaptor.capture());
        PutObjectRequest putObjectRequest = putObjectRequestCaptor.getValue();

        // Verify that the putObjectRequest has the expected attributes
        ObjectMetadata metadata = putObjectRequest.getMetadata();
        assert metadata.getContentLength() == fileBytes.length;
        assert putObjectRequest.getBucketName().equals("test-bucket");
        assert putObjectRequest.getKey().equals(fileName);
    }
}
