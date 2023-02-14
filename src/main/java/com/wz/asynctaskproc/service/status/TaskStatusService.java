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

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

}

// TODO
//    public Mono<Void> storeTaskStatus(String taskId, TaskStatus status) {
//        return Mono.fromRunnable(() -> redisTemplate.opsForValue().set(getKey(taskId), status.toString()));
//    }
//
//    public Mono<String> retrieveTaskStatus(String taskId) {
//        return Mono.justOrEmpty(redisTemplate.opsForValue().get(getKey(taskId)));
//    }
