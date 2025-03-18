package com.wz.asynctaskproc.service.status;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskProgressService extends RedisTemplateService {

    private final static String PREFIX = "PROGRESS_";

    public TaskProgressService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }
    public void storeTaskProgress(String taskId, int progress) {
        redisTemplate.opsForValue().set(getKey(taskId), String.valueOf(progress));
    }

    public String retrieveTaskProgress(String taskId) {
        return redisTemplate.opsForValue().get(getKey(taskId));
    }

    public void deleteTaskProgress(String taskId) {
        redisTemplate.delete(getKey(taskId));
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

}