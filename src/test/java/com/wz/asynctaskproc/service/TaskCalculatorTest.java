package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.model.MatchResult;
import com.wz.asynctaskproc.model.Task;
import com.wz.asynctaskproc.service.event.TaskEventEmitter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Slf4j
class TaskCalculatorTest {

    private static TaskEventEmitter emitter;

    @BeforeAll
    public static void init() {
        emitter = new TaskEventEmitter(mock(ApplicationEventPublisher.class));
    }

    @TestConfiguration
    static class MockitoPublisherConfiguration {

        @Bean
        @Primary
        ApplicationEventPublisher publisher() {
            return mock(ApplicationEventPublisher.class);
        }
    }

    @Test
    void case1() {
        String input = "ABCD";
        String pattern = "BCD";
        Integer expectedPosition = 1;
        Integer expectedTypos = 0;

        MatchResult result = getProcessedTaskResult("case1", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    @Test
    void case2() {
        String input = "ABCD";
        String pattern = "BWD";
        Integer expectedPosition = 1;
        Integer expectedTypos = 1;

        MatchResult result = getProcessedTaskResult("case1", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    @Test
    void case3() {
        String input = "ABCDEFG";
        String pattern = "CFG";
        Integer expectedPosition = 4;
        Integer expectedTypos = 1;

        MatchResult result = getProcessedTaskResult("case3", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    @Test
    void case4() {
        String input = "ABCABC";
        String pattern = "ABC";
        Integer expectedPosition = 0;
        Integer expectedTypos = 0;

        MatchResult result = getProcessedTaskResult("case4", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    @Test
    void case5() {
        String input = "ABCDEFG";
        String pattern = "TDD";
        Integer expectedPosition = 1;
        Integer expectedTypos = 2;

        MatchResult result = getProcessedTaskResult("case5", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    @Test
    void case6NoMatch() {
        String input = "ZXYZXYZXY";
        String pattern = "ABCD";
        Integer expectedPosition = -1;
        Integer expectedTypos = -1;

        MatchResult result = getProcessedTaskResult("case6NoMatch", input, pattern, expectedPosition, expectedTypos);
        assertEquals(result.getPosition(), expectedPosition);
        assertEquals(result.getTypos(), expectedTypos);
    }

    private static MatchResult getProcessedTaskResult(String taskId, String input, String pattern, Integer expectedPosition, Integer expectedTypos) {
        TaskCalculator taskCalculator = new TaskCalculator(emitter);

        Task task = Task.builder()
                .id(taskId)
                .input(input)
                .pattern(pattern)
                .build();
        MatchResult result = taskCalculator.findBestMatch(task, task.getInput(), task.getPattern());

        log.info("[{}] Position: {} expected: {} | [{}] Typos: {}, expected: {} ",
                Objects.equals(result.getPosition(), expectedPosition) ? "✓" : "x",
                result.getPosition(),
                expectedPosition,
                Objects.equals(result.getTypos(), expectedTypos) ? "✓" : "x",
                result.getTypos(),
                expectedTypos
        );

        return new MatchResult(result.getPosition(), result.getTypos());
    }
}