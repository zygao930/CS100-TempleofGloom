package student.escape;

import game.EscapeState;
import game.Node;
import java.util.*;
import student.utils.ShortestPath;

public abstract class BaseEscapeStrategy implements EscapeStrategy {
    protected abstract ShortestPath calculateShortestPaths(Node startNode, EscapeState state);

    /**
     * Escape the temple.
     * @param state the current state of the game.
     */
    @Override
    public void escape(EscapeState state) {
        // Calculate the shortest paths from the current node to all other nodes
        var startPathsResult = calculateShortestPaths(state.getCurrentNode(), state);
        var exitPathsResult = calculateShortestPaths(state.getExit(), state);
        collectGoldAndEscape(state, startPathsResult, exitPathsResult);
    }

    /**
     * Collect gold and escape the temple.
     * @param state the current state of the game.
     * @param pathsFromCurrent the shortest paths from the current node.
     * @param pathsToExit the shortest paths to the exit node.
     */
    protected void collectGoldAndEscape(EscapeState state, ShortestPath pathsFromCurrent, ShortestPath pathsToExit) {
        // Move to the next node with the highest score until the time remaining is less than the distance to the exit
        while (state.getTimeRemaining() > pathsFromCurrent.getDistanceTo(state.getExit())) {
            Node nextNode = findBestNextNode(state, pathsFromCurrent, pathsToExit);
            if (nextNode == null) break;
            moveToNode(state, nextNode, pathsFromCurrent);
            pathsFromCurrent = calculateShortestPaths(state.getCurrentNode(), state);
        }
        moveToNode(state, state.getExit(), pathsFromCurrent);
    }

    /**
     * Find the best next node to move to.
     * @param state the current state of the game.
     * @param pathsFromCurrent the shortest paths from the current node.
     * @param pathsToExit the shortest paths to the exit node.
     * @return the best next node to move to.
     */
    protected Node findBestNextNode(EscapeState state, ShortestPath pathsFromCurrent, ShortestPath pathsToExit) {
        // Find the node with the highest score
        Node currentNode = state.getCurrentNode();
        Node bestNode = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        // Iterate through all the nodes to find the best node to move to
        for (Node node : state.getVertices()) {
            if (node.equals(currentNode) || node.getTile().getGold() == 0) continue;

            int toNode = pathsFromCurrent.getDistanceTo(node);
            int toExit = pathsToExit.getDistanceTo(node);
            int totalTime = toNode + toExit;

            if (state.getTimeRemaining() >= totalTime) {
                double score = calculateScore(state, pathsFromCurrent, node);
                if (score > bestScore) {
                    bestScore = score;
                    bestNode = node;
                }
            }
        }
        return bestNode;
    }

    /**
     * Calculate the score of a node.
     * @param state the current state of the game.
     * @param pathsFromCurrent the shortest paths from the current node.
     * @param node the node to calculate the score of.
     * @return the score of the node.
     */
    protected double calculateScore(EscapeState state, ShortestPath pathsFromCurrent, Node node) {
        int toNode = pathsFromCurrent.getDistanceTo(node);
        int gold = node.getTile().getGold();
        double remainingTime = state.getTimeRemaining();
        return gold * remainingTime / (1 + toNode);
    }

    /**
     * Move to a node.
     * @param state the current state of the game.
     * @param destination the destination node.
     * @param shortestPath the shortest path to the destination node.
     */
    protected void moveToNode(EscapeState state, Node destination, ShortestPath shortestPath) {
        List<Node> path = shortestPath.reconstructPath(state.getCurrentNode(), destination);
        for (Node next : path) {
            state.moveTo(next);
            if (next.getTile().getGold() > 0) {
                state.pickUpGold();
            }
        }
    }
}
