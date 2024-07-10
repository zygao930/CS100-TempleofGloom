package student.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import student.StrategyFactory;
import student.explore.ExplorationStrategy;
import student.explore.ExploreDFS;
import student.escape.EscapeStrategy;
import student.escape.EscapeAStar;
import student.escape.EscapeDijkstra;

import static org.junit.jupiter.api.Assertions.*;

class StrategyFactoryTest {

    private StrategyFactory factory;

    // This method is run before each test
    @BeforeEach
    void setUp() {
        factory = StrategyFactory.getInstance();
    }

    // This test is to ensure that the factory creates the correct explore strategy for the input
    @Test
    @DisplayName("createExploreStrategy should return ExploreDFS for 'dfs' input")
    void createExploreStrategy_shouldReturnExploreDFS_whenGivenDFS() {
        ExplorationStrategy strategy = factory.createExploreStrategy("dfs");
        assertTrue(strategy instanceof ExploreDFS);
    }

    // This test is to ensure that the factory throws an exception when an unknown strategy is provided
    @Test
    @DisplayName("createExploreStrategy should throw exception for unknown strategy")
    void createExploreStrategy_shouldThrowException_whenGivenUnknownStrategy() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createExploreStrategy("unknown");
        });
    }

    // This test is to ensure that the factory creates the correct escape strategy for the input
    @Test
    @DisplayName("createEscapeStrategy should return EscapeAStar for 'a*' input")
    void createEscapeStrategy_shouldReturnEscapeAStar_whenGivenAStar() {
        EscapeStrategy strategy = factory.createEscapeStrategy("a*");
        assertTrue(strategy instanceof EscapeAStar);
    }

    // This test is to ensure that the factory creates the correct escape strategy for the input
    @Test
    @DisplayName("createEscapeStrategy should return EscapeDijkstra for 'dijkstra' input")
    void createEscapeStrategy_shouldReturnEscapeDijkstra_whenGivenDijkstra() {
        EscapeStrategy strategy = factory.createEscapeStrategy("dijkstra");
        assertTrue(strategy instanceof EscapeDijkstra);
    }

    // This test is to ensure that the factory throws an exception when an unknown strategy is provided
    @Test
    @DisplayName("createEscapeStrategy should throw exception for unknown strategy")
    void createEscapeStrategy_shouldThrowException_whenGivenUnknownStrategy() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createEscapeStrategy("unknown");
        });
    }

    // This test is to ensure that the singleton pattern is implemented correctly
    @Test
    @DisplayName("getInstance should always return the same instance")
    void getInstance_shouldAlwaysReturnSameInstance() {
        StrategyFactory instance1 = StrategyFactory.getInstance();
        StrategyFactory instance2 = StrategyFactory.getInstance();
        assertSame(instance1, instance2);
    }
}