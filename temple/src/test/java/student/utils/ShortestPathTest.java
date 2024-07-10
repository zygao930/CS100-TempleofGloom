package student.utils;
import game.GameState;

import game.Node;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathTest {

    // Invoke the escape method using reflection
    private static void invokeEscapeMethod(GameState gameState) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method escapeMethod = GameState.class.getDeclaredMethod("escape");
        escapeMethod.setAccessible(true);
        escapeMethod.invoke(gameState);
    }

    public void assertShortestPathDistance(long seed, Node fromStart, Node fromEnd, int expectedDistance){
        Node start = fromStart;
        Node end = fromEnd;

        Map<Node, Integer> shortestDistances = new HashMap<>();
        shortestDistances.put(start, expectedDistance);

        Map<Node, Node> previousNodes = new HashMap<>();
        previousNodes.put(start, start);

        var shortestPath = new ShortestPath(shortestDistances, previousNodes);
        var distance = shortestPath.getDistanceTo(end);

        assertEquals(expectedDistance, distance);
    }

    // Generate parameterized game states
    private static Stream<Long> seedProvider() {
        return Stream.of(10L, 800L, 757868709594414956L);
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void start_distance10_exit_ShouldCalculateShortestPathDistance(long seed) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Generate a game state
        var gameState = GameStateGenerator.generate(seed);

        // Invoke the escape method using reflection
        invokeEscapeMethod(gameState);

        // Get the start and end nodes
        Node start = gameState.getCurrentNode();
        Node end = gameState.getExit();

        assertShortestPathDistance(seed, start, end, 10);
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void start_end_distance_20_ShouldCalculateShortestPathDistance(long seed) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Generate a game state
        var gameState = GameStateGenerator.generate(seed);

        // Invoke the escape method using reflection
        invokeEscapeMethod(gameState);

        // Get the start and end nodes
        Node start = gameState.getCurrentNode();
        Node end = gameState.getExit();

        assertShortestPathDistance(seed, start, end, 50);
    }
}
