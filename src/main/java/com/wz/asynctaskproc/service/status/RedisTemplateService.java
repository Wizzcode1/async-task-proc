package com.wz.asynctaskproc.service.status;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public abstract class RedisTemplateService {

    protected final StringRedisTemplate redisTemplate;

    protected RedisTemplateService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected abstract String getPrefix();

    protected String getKey(String taskId) {
        return getPrefix() + taskId;
    }

}
