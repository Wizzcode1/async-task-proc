Not everything is completed (check TODOS), sorry couldnt find time to finish

Project uses Redis (configuration in application.properties) -> 
example how to run it in docker:
docker run --name redis -p 6379:6379 -d redis

or set host & port to existing one

TaskController

note:
getTaskInfo returns progress if status not completed and task result (typos, position) when its 100%