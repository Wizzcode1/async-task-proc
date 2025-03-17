Project uses Redis (configuration in application.properties) -> 
example how to run it in docker:
docker run --name redis -p 6379:6379 -d redis

or set host & port to existing one

and

docker build -t async_task_proc .

docker run -p 8080:8080 async_task_proc

note:
TaskController.getTaskInfo returns progress if status not completed and task result (typos, position) when its 100%
