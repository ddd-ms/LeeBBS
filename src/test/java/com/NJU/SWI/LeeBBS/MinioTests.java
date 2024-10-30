package com.NJU.SWI.LeeBBS;

import com.NJU.SWI.LeeBBS.service.MinioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ContextConfiguration
@RunWith(SpringRunner.class)
public class MinioTests {
    @Autowired
    private MinioService minioService;

    @Test
    public void testUpload(){
        MultipartFile file = null;
    }

}
