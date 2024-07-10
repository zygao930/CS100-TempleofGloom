package student.escape;

import student.utils.GameStateGenerator;
import game.GameState;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EscapeDijkstraTest {

    // Invoke the escape method using reflection
    private static void invokeEscapeMethod(GameState gameState) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method escapeMethod = GameState.class.getDeclaredMethod("escape");
        escapeMethod.setAccessible(true);
        escapeMethod.invoke(gameState);
    }

    // Generate parameterized game states
    private static Stream<Long> seedProvider() {
        return Stream.of(10L, 800L, 757868709594414956L);
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void escape_whenEscapeMethodInvoked_thenSuccessfulEscape(long seed) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var gameState = GameStateGenerator.generate(seed);
        invokeEscapeMethod(gameState);

        var escaper = new EscapeDijkstra();
        escaper.escape(gameState);

        assertEquals(0, gameState.getTimeRemaining());
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void escape_whenEscapeMethodInvoked_thenNeighboursAreNotEmpty(long seed) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var gameState = GameStateGenerator.generate(seed);
        invokeEscapeMethod(gameState);

        var escaper = new EscapeDijkstra();
        escaper.escape(gameState);

        assertNotEquals(0, gameState.getVertices().size());
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void escape_whenEscapeMethodInvoked_thenFoundExit(long seed) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var gameState = GameStateGenerator.generate(seed);
        invokeEscapeMethod(gameState);

        var escaper = new EscapeDijkstra();
        escaper.escape(gameState);

        assertNotEquals(0, gameState.getExit());
    }
}
