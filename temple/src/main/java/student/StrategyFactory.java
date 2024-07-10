package student;

import java.util.Locale;
import student.escape.EscapeAStar;
import student.escape.EscapeDijkstra;
import student.escape.EscapeStrategy;
import student.explore.ExplorationStrategy;
import student.explore.ExploreDFS;

public class StrategyFactory {
    // Private constructor to prevent instantiation
    private StrategyFactory() { }

    // Static inner class - SingletonHelper
    private static class SingletonHelper {
        // The instance of the StrategyFactory
        private static final StrategyFactory INSTANCE = new StrategyFactory();
    }

    // Global access point to get the instance of the StrategyFactory
    public static StrategyFactory getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public ExplorationStrategy createExploreStrategy(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            // Add new exploration strategies here
            case "dfs" -> new ExploreDFS();
            default -> throw new IllegalArgumentException("Unknown exploration strategy type: " + type);
        };
    }

    public EscapeStrategy createEscapeStrategy(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            // Add new escape strategies here
            case "a*" -> new EscapeAStar();
            case "dijkstra" -> new EscapeDijkstra();
            default -> throw new IllegalArgumentException("Unknown escape strategy type: " + type);
        };
    }
}
