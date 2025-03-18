package com.wz.asynctaskproc.repository;

import com.wz.asynctaskproc.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends MongoRepository<Task, String> {

}
