package server.api;

import commons.Activity;
import commons.GameUpdatesPacket;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import server.api.dependencies.TestActivityRepository;
import server.api.dependencies.TestRandom;
import server.multiplayer.WaitingRoom;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplayerControllerTest {
    private MultiplayerController lpc;
    private ArrayList<Player> players;
    private TestActivityRepository tar;

    /**
     * Sets up environment before each test
     */
    @BeforeEach
    public void setup(){
        players = new ArrayList<>();
        players.addAll(List.of(
                new Player(1L,"a", 1),
                new Player(2L,"b", 2),
                new Player(3L,"c", 3)
        ));
        tar = new TestActivityRepository();
        tar.activities.addAll(List.of(
            new Activity("1", "image_a","a", 1L, "a"),
            new Activity("2", "image_b","b", 2L, "b"),
            new Activity("3", "image_c","c", 3L, "c"),
            new Activity("4", "image_d","d", 4L, "d"),
            new Activity("5", "image_e","e", 5L, "e"),
            new Activity("6", "image_f","f", 6L, "f"),
            new Activity("7", "image_g","g", 7L, "g")
        ));
        lpc = new MultiplayerController(new WaitingRoom(new ArrayList<>(), new ArrayList<>(), 0), new TestRandom(), tar);
        lpc.postPlayerToWaitingRoom(players.get(0));
        lpc.startGame();
    }

    /**
     * Test for getting list of all players
     */
    @Test
    void getPlayersTest() {
        assertEquals(List.of(players.get(0)), lpc.getPlayers(0).getBody());
    }

    /**
     * Test for updating score of player
     * Needs Mockito for proper testing
     */
    @Test
    void updateScoreTest() {
        Player newScore = new Player(1L,"a", 5);
        assertEquals(newScore, lpc.updateScore(0, newScore).getBody());
        assertEquals(ResponseEntity.badRequest().build(), lpc.updateScore(0, new Player(5L,"bob", 99)));
    }
    /**
     * Test for getting the update packet
     * Needs Mockito for proper testing
     */
    @Test
    void getUpdateTest() throws InterruptedException {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<GameUpdatesPacket>>(5000L,noContent);
        var res2 = lpc.getUpdate(0);
        Thread.sleep(5500);
        assertEquals(res.getClass(), res2.getClass());
    }
}
