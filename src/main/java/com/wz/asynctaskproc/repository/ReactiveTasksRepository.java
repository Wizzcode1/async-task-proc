package com.wz.asynctaskproc.repository;

import com.wz.asynctaskproc.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveTasksRepository extends ReactiveMongoRepository<Task, String> {

}
