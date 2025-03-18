package com.wz.asynctaskproc.service.status;

import com.wz.asynctaskproc.enums.TaskStatus;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusService extends RedisTemplateService {


    private final static String PREFIX = "STATUS_";

    public TaskStatusService(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public void storeTaskStatus(String taskId, TaskStatus status) {
        redisTemplate.opsForValue().set(getKey(taskId), status.toString());
    }

    public String retrieveTaskStatus(String taskId) {
        return redisTemplate.opsForValue().get(getKey(taskId));
    }

    public void deleteTaskStatus(String taskId) {
        redisTemplate.delete(getKey(taskId));
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

}