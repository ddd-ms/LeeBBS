package com.NJU.SWI.LeeBBS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LeeBbsApplication.class)
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testStrings(){
        String redis_key = "test:count";
        redisTemplate.opsForValue().set(redis_key,1);
        System.out.println(redisTemplate.opsForValue().get(redis_key));
        redisTemplate.opsForValue().increment(redis_key,5);
        System.out.println(redisTemplate.opsForValue().get(redis_key));
    }
    @Test
    public void testHash(){
        String redis_key = "test:user";
        redisTemplate.opsForHash().put(redis_key,"id",1);
        redisTemplate.opsForHash().put(redis_key,"username","zhangsan");
    }
    @Test
    public void testList(){
        String redis_key = "test:ids";
        redisTemplate.opsForList().leftPush(redis_key,101);
        redisTemplate.opsForList().leftPush(redis_key,102);
        redisTemplate.opsForList().leftPush(redis_key,103);
        System.out.println(redisTemplate.opsForList().size(redis_key));
        System.out.println(redisTemplate.opsForList().index(redis_key,0));
    }
    @Test
    public void testSet(){
        String redis_key = "test:teachers";
        redisTemplate.opsForSet().add(redis_key,"刘备","关羽","张飞","赵云","诸葛亮");
        System.out.println(redisTemplate.opsForSet().size(redis_key));
        System.out.println(redisTemplate.opsForSet().pop(redis_key));
    }

}
