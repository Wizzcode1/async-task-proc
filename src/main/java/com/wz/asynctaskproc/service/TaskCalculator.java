package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.enums.TaskStatus;
import com.wz.asynctaskproc.model.MatchResult;
import com.wz.asynctaskproc.model.Task;
import com.wz.asynctaskproc.service.event.TaskEventEmitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskCalculator {

    private final TaskEventEmitter emitter;

    public MatchResult findBestMatch(Task task, String input, String pattern) {
        emitter.emitEvent(new ProcessedTaskInfoEvent(task.getId(), 0, TaskStatus.IN_PROGRESS));

        int stepsTaken = 0;
        int totalSteps = input.length() - pattern.length() + 1;

        List<MatchResult> matches = new ArrayList<>();
        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            int typos = countTypos(input, pattern, i);
            matches.add(new MatchResult(i, typos));

            // TODO calculating progress can be more detailed
            stepsTaken++;
            int progress = calculateProgress(stepsTaken, totalSteps);
            emitter.emitEvent(new ProcessedTaskInfoEvent(task.getId(),  progress == 100 ? 99 : progress , TaskStatus.IN_PROGRESS));
            log.info("Task {} progress: {}%", task.getId(), progress);

            // TODO FILLER FOR TESTING PURPOSES
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MatchResult bestMatch = null;
        int minTypos = Integer.MAX_VALUE;
        for (MatchResult match : matches) {
            if (match.getTypos() < minTypos) {
                bestMatch = match;
                minTypos = match.getTypos();
            }
        }

        if (pattern.length() == bestMatch.getTypos()) {
            return new MatchResult(-1, -1);
        }

        return bestMatch;
    }

    private int countTypos(String input, String pattern, int index) {
        int typos = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (input.charAt(index + i) != pattern.charAt(i)) {
                typos++;
            }
        }
        return typos;
    }

    private int calculateProgress(int stepsTaken, int totalSteps) {
        return (int) Math.round(100.0 * stepsTaken / totalSteps);
    }

//    private int calculateProgress(int stepsTaken, int totalSteps) {
//        return (int) (100.0 * stepsTaken / totalSteps);
//    }

}
