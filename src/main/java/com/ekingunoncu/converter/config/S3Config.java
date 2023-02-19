
package com.ekingunoncu.converter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * 
 * Configuration class for creating an AmazonS3 client object with the specified
 * AWS S3 bucket name and region.
 */
@Configuration
public class S3Config {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    /**
     * Creates and returns an AmazonS3 client object with the specified AWS S3
     * bucket name and region.
     *
     * @return AmazonS3 client object
     */
    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();

        // Check if the bucket exists, and if it doesn't, create it
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }

        return amazonS3;
    }

}