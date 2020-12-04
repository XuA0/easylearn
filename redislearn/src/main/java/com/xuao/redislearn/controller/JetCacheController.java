package com.xuao.redislearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;

@RestController
@RequestMapping("/api/jetcache")
public class JetCacheController {

	private static final Logger log = LoggerFactory.getLogger(JetCacheController.class);

	// redis学习2.0 , jetCache
	@CreateCache
	Cache<String, String> testCache;
	
	@CreateCache
	Cache<String, Integer> integerCache;

	@GetMapping("testRedis")
	public void testRedis() {
		testCache.put("mytest", "testsuccess");
		log.info(testCache.get("mytest"));
	}

	@GetMapping("addInteger")
	public  void AddInteger() {
		Integer integer = integerCache.get("testInteger");
		integerCache.put("testInteger", ++integer);
		log.info(integer.toString());
	}

	@GetMapping("testResult")
	public Integer testResult() {
		Integer integer =  integerCache.get("testInteger");
		log.info(integer.toString());
		return integer;
	}

	@GetMapping("init")
	public void testRedisInit() {
		integerCache.put("testInteger", new Integer(0));
	}

}

