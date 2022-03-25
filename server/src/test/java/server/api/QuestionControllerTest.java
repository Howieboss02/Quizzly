package server.api;

import commons.Activity;
import commons.ComparativeQuestion;
import commons.EstimationQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.api.dependencies.TestActivityRepository;
import server.api.dependencies.TestRandom;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest {

    private TestRandom random;
    private TestActivityRepository repo;

    private List<Activity> activities;
    private QuestionController que;

    /**
     * Sets up a new question controller with testing dependencies
     * Runs before every test
     */
    @BeforeEach
    public void setup(){
        random = new TestRandom();
        repo = new TestActivityRepository();
        que = new QuestionController(random, repo);

        activities = List.of(
            new Activity("1", "image_a","a", 1L, "a"),
            new Activity("2", "image_b","b", 2L, "b"),
            new Activity("3", "image_c","c", 3L, "c"),
            new Activity("4", "image_d","d", 4L, "d"),
            new Activity("5", "image_e","e", 5L, "e"),
            new Activity("6", "image_f","f", 6L, "f"),
            new Activity("7", "image_g","g", 7L, "g")
        );
    }

    /**
     * Test for getting a random comparative question
     * Uses TestRandom implementation so random is predictable
     */
    @Test //repo and que have different TestRandom objects!
    void getRandomComparativeTest() {
        repo.activities.addAll(activities);

        List<Activity> e1_list = List.of(activities.get(0), activities.get(1), activities.get(2));
        ComparativeQuestion expected1 = new ComparativeQuestion(e1_list, true);
        assertEquals(expected1, que.getRandomComparative().getBody());

        List<Activity> e2_list = List.of(activities.get(0), activities.get(1), activities.get(2));
        ComparativeQuestion expected2 = new ComparativeQuestion(e2_list, false);
        assertEquals(expected2, que.getRandomComparative().getBody());
    }

    @Test
    void getRandomComparativeTestNoActivities() {
        assertEquals(ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build(), que.getRandomComparative());
    }

    @Test
    void getRandomEstimationTest() {
        repo.activities.addAll(activities);

        EstimationQuestion expected = new EstimationQuestion(activities.get(0));
        assertEquals(expected, que.getRandomEstimation().getBody());
    }

    @Test
    void getRandomEstimationTestNoActivities() {
        assertEquals(ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build(), que.getRandomEstimation());
    }

    @Test
    void getRandomQuestionTestComparative() {
        repo.activities.addAll(activities);

        ComparativeQuestion expected = new ComparativeQuestion(List.of(activities.get(0), activities.get(1), activities.get(2)), false);

        que.getRandomComparative(); // to move TestRandom to next number so it would return a comparative question
        assertEquals(expected, que.getRandomQuestion().getBody());
    }

    @Test
    void getRandomQuestionTestEstimation() {
        repo.activities.addAll(activities);

        EstimationQuestion expected = new EstimationQuestion(activities.get(0));

        assertEquals(expected, que.getRandomQuestion().getBody());
    }
}