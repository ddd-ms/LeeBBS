package com.NJU.SWI.LeeBBS.service;

import com.NJU.SWI.LeeBBS.config.MinioConfig;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioConfig minioConfig;

    public void createBucketIfNotExist(){
        try{
            if(!minioClient.bucketExists(minioConfig.getBucketNameToCheckExist())){
                minioClient.makeBucket(minioConfig.getBucketNameToMake());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String upload(MultipartFile file) throws IOException{
        createBucketIfNotExist();

        try(InputStream is = file.getInputStream()){
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(file.getOriginalFilename())
                            .stream(is,file.getSize(),-1)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return minioConfig.getBucketUrl()+"/"+file.getOriginalFilename();
    }
    public Resource download(String fileName){
        try(InputStream is = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .build()
        )){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int length;
            while ((length = is.read(buf))!=-1){
                outputStream.write(buf,0,length);
            }
            return new ByteArrayResource(outputStream.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
