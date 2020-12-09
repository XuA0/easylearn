package com.xuao.redislearn.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.xuao.redislearn.redislock.RedisLock;

@RestController
@RequestMapping("/api/jetcache")
public class JetCacheController {

	private static final Logger log = LoggerFactory.getLogger(JetCacheController.class);

	// redis学习2.0 , jetCache
	@CreateCache
	Cache<String, String> testCache;

	@CreateCache
	Cache<String, Integer> integerCache;

	@Resource
	RedisLock redisLock;

	private static int i = 0;

	@GetMapping("testRedis")
	public void testRedis() {
		testCache.put("mytest", "testsuccess");
		log.info(testCache.get("mytest"));
	}

	@GetMapping("addInteger")
	public void AddInteger() {
		Integer integer = integerCache.get("testInteger");
		integerCache.put("testInteger", ++integer);
		log.info(integer.toString());
	}

	@GetMapping("testResult")
	public Integer testResult() {
		Integer integer = integerCache.get("testInteger");
		log.info(integer.toString());
		return integer;
	}

	@GetMapping("init")
	public void testRedisInit() {
		integerCache.put("testInteger", new Integer(0));
	}

	@GetMapping("testlock")
	public void testLock() {
		try {
			while (true) {
				if (redisLock.lock("testlock")) {
					i++;
					log.info(String.valueOf(i));
					break;
				}
				;
			}
			if (i < 5) {
				testLock();
			} else {
				i = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisLock.unlock("testlock");
		}
	}

}
