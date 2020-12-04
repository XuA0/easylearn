package com.xuao.redislearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redistemplate")
public class RedisTemplateController {
	
	private static final Logger log = LoggerFactory.getLogger(RedisTemplateController.class);
	

	//redis学习1.0 , RedisTemplate
	@Autowired
	RedisTemplate<String,Object> redistemplate;
	
	@GetMapping("testRedis")
	public String testRedis() {
		redistemplate.opsForValue().set("mytest", "testsuccess");
		log.info(redistemplate.opsForValue().get("mytest").toString());
		return redistemplate.opsForValue().get("mytest").toString();
	}
	
	@GetMapping("addInteger")
	public synchronized void AddInteger() {
		Integer integer = (Integer)redistemplate.opsForValue().get("testInteger");
		redistemplate.opsForValue().set("testInteger", ++integer);
		log.info(integer.toString());
	}
	
	
	@GetMapping("testResult")
	public Integer testResult() {
		Integer integer = (Integer)redistemplate.opsForValue().get("testInteger");
		log.info(integer.toString());
		return integer;
	}
	
	@GetMapping("init")
	public void testRedisInit() {
		redistemplate.opsForValue().set("testInteger",new Integer(0));
	}
	
}
