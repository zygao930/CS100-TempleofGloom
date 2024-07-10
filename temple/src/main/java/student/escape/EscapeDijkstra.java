package student.escape;

import game.EscapeState;
import game.Node;
import java.util.*;
import student.utils.ShortestPath;

public class EscapeDijkstra extends BaseEscapeStrategy {

    /**
     * Calculate the shortest paths from the start node to all other nodes in the graph.
     *
     * @param startNode the start node
     * @param state the escape state
     * @return the shortest paths from the start node to all other nodes
     */
    @Override
    protected ShortestPath calculateShortestPaths(Node startNode, EscapeState state) {
        // Initialise the shortest distances and previous nodes
        Map<Node, Integer> shortestDistances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();

        // Set the shortest distances to infinity for all nodes
        state.getVertices().forEach(node -> shortestDistances.put(node, Integer.MAX_VALUE));
        shortestDistances.put(startNode, 0);

        // Create a priority queue to store the nodes
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(shortestDistances::get));
        queue.add(startNode);

        // Dijkstra's algorithm
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentDist = shortestDistances.get(current);

            for (Node neighbour : current.getNeighbours()) {
                int distance = currentDist + current.getEdge(neighbour).length();
                if (distance < shortestDistances.get(neighbour)) {
                    shortestDistances.put(neighbour, distance);
                    previousNodes.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }
        return new ShortestPath(shortestDistances, previousNodes);
    }


    /**
     * Find the best next node to visit to maximize gold collection.
     *
     * @param state                     the escape state
     * @param DijkstraPathFromCurrent   the shortest path from the current node to all other nodes
     * @param DijkstraPathToExit        the shortest distances from all nodes to the exit node
     * @return the best next node to visit to maximize gold collection
     */
    @Override
    protected Node findBestNextNode(EscapeState state, ShortestPath DijkstraPathFromCurrent, ShortestPath DijkstraPathToExit) {
        Node currentNode = state.getCurrentNode();
        Node bestNode = null;

        // Priority queue to evaluate nodes based on a scoring system
        PriorityQueue<Node> scoreQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> {
            int toNode = DijkstraPathFromCurrent.getDistanceTo(node);
            int toExit = DijkstraPathToExit.getDistanceTo(node);
            int gold = node.getTile().getGold();
            double remainingTime = state.getTimeRemaining();
            double distanceFactor = (double) toNode;

            // More gold and closer nodes have higher scores
            // Dynamic Scoring and Threshold Adjustment: Adjust the scoring dynamically
            // based on remaining time and collected gold.
            // Max score: 70389 - Seed used: -757868709594414956

            int goldthreshold = 500;
            double score = (gold > goldthreshold)? gold/(1+distanceFactor): goldthreshold/(1+distanceFactor);
            return score;
        }));

        // Evaluate each node to find the best one to visit next
        for (Node node : state.getVertices()) {
            if (node == currentNode || node.getTile().getGold() == 0) continue;

            int toNode = DijkstraPathFromCurrent.getDistanceTo(node);
            int toExit = DijkstraPathToExit.getDistanceTo(node);
            int totalTime = toNode + toExit;

            // Only consider nodes that can be reached with the remaining time
            if (state.getTimeRemaining() >= totalTime) {
                scoreQueue.add(node);
            }
        }

        // Retrieve the best node from the priority queue
        while (!scoreQueue.isEmpty()) {
            bestNode = scoreQueue.poll();
        }
        return bestNode;
    }
}
