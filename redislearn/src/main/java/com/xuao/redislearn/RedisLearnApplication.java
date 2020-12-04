package com.xuao.redislearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.xuao")
@EnableCreateCacheAnnotation
public class RedisLearnApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RedisLearnApplication.class, args);
	}
}
