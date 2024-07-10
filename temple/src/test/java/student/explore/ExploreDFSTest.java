package student.explore;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import student.utils.GameStateGenerator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExploreDFSTest {

    private static Stream<Long> seedProvider() {
        return Stream.of(10L, 500L, 757868709594414956L);
    }

    @ParameterizedTest
    @MethodSource("seedProvider")
    void explore_Seed10_SucessfulExploration(long seed) {
        var gameState = GameStateGenerator.generate(seed);
        var explorer = new ExploreDFS();

        explorer.explore(gameState);

        assertEquals(0, gameState.getDistanceToTarget());
    }
}
