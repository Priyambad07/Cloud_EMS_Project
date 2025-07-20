package com.expenditure;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class S3Manager {
    public static void createBucketAndUploadFile() {
        S3Client s3 = S3Client.create();

        // Create S3 bucket
        String bucketName = "cloud-ems-bucket";
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        s3.createBucket(createBucketRequest);
        System.out.println("✅ S3 Bucket Created: " + bucketName);

        // Upload file to S3
        Path filePath = Paths.get("ExpenditureApp.jar");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("ExpenditureApp.jar")
                .build();
        s3.putObject(putObjectRequest, filePath);
        System.out.println("✅ File Uploaded to S3: " + filePath);
    }

    public static void downloadFileFromS3() {
        S3Client s3 = S3Client.create();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("cloud-ems-bucket")
                .key("ExpenditureApp.jar")
                .build();

        s3.getObject(getObjectRequest, Path.of("/home/ec2-user/ExpenditureApp.jar"));
        System.out.println("✅ File Downloaded from S3.");
    }
}
