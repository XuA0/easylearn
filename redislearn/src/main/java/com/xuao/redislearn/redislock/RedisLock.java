package com.xuao.redislearn.redislock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheResultCode;
import com.alicp.jetcache.anno.CreateCache;

public class RedisLock {

	@CreateCache(name = "lockCache")
	Cache<String, Map<String, Integer>> lockCache;
	

	public boolean lock(String key) {
		Map<String, Integer> lockValue = lockCache.get(key);
		if (lockValue == null || lockValue.isEmpty()) {
			lockValue = new HashMap<String, Integer>();
			lockValue.put(Thread.currentThread().getName(), 0);
		} else {
			if (lockValue.containsKey(Thread.currentThread().getName())) {
				Integer count = lockValue.get(Thread.currentThread().getName());
				lockValue.put(key, ++count);
				lockCache.PUT(key, lockValue, 20, TimeUnit.SECONDS);
				return true;
			}
		}
		if (lockCache.PUT_IF_ABSENT(key, lockValue, 20, TimeUnit.SECONDS).getResultCode()
				.equals(CacheResultCode.SUCCESS)) {
			return true;
		}
		return false;
	}

	public void unlock(String key) {
		Map<String, Integer> lockValue = lockCache.get(key);
		if (lockValue == null || lockValue.isEmpty()) {
			return;
		} else {
			if (lockValue.containsKey(Thread.currentThread().getName())) {
				Integer count = lockValue.get(Thread.currentThread().getName());
				if (count.equals(0)) {
					lockCache.remove(key);
				} else {
					lockValue.put(key, --count);
					lockCache.PUT(key, lockValue, 20, TimeUnit.SECONDS);
					lockCache.remove(key);
				}
			}
		}
	}
}
