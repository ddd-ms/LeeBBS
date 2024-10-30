package com.NJU.SWI.LeeBBS.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Value("${minio.bucketName}")
    @Getter
    private String bucketName;
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public BucketExistsArgs getBucketNameToCheckExist() {
        return BucketExistsArgs.builder().bucket(bucketName).build();
    }
    public MakeBucketArgs getBucketNameToMake(){
        return MakeBucketArgs.builder().bucket(bucketName).build();
    }

    public String getBucketUrl() {
        return "http://" + endpoint + "/" + bucketName;
    }
}
