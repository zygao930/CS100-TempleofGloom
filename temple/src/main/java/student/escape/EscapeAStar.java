package student.escape;

import game.EscapeState;
import game.Node;
import java.util.*;
import student.utils.ShortestPath;

public class EscapeAStar extends BaseEscapeStrategy {

    /**
     * Calculate the shortest paths from the start node to all other nodes in the graph.
     *
     * @param startNode the start node
     * @param state the escape state
     * @return the shortest paths from the start node to all other nodes
     */
    @Override
    protected ShortestPath calculateShortestPaths(Node startNode, EscapeState state) {
        // Initialise the shortest distances, previous nodes, and fCost
        Map<Node, Integer> shortestDistances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        Map<Node, Double> fCost = new HashMap<>();
        Node exitNode = state.getExit();

        // Set the shortest distances and fCost to infinity for all nodes
        state.getVertices().forEach(node -> {
            shortestDistances.put(node, Integer.MAX_VALUE);
            fCost.put(node, Double.POSITIVE_INFINITY);
        });

        // Set the shortest distance from the start node to itself to 0
        shortestDistances.put(startNode, 0);
        fCost.put(startNode, heuristicFunction(startNode, exitNode));

        // Create a priority queue to store the nodes
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(fCost::get));
        queue.add(startNode);

        // A* algorithm
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int currentDist = shortestDistances.get(currentNode);

            for (Node neighbourNode : currentNode.getNeighbours()) {
                int distance = currentDist + currentNode.getEdge(neighbourNode).length();
                if (distance < shortestDistances.get(neighbourNode)) {
                    shortestDistances.put(neighbourNode, distance);
                    fCost.put(neighbourNode, distance + heuristicFunction(neighbourNode, exitNode));
                    previousNodes.put(neighbourNode, currentNode);
                    queue.add(neighbourNode);
                }
            }
        }
        return new ShortestPath(shortestDistances, previousNodes);
    }

    /**
     * Calculate the heuristic function for the A* algorithm.
     *
     * @param node the current node
     * @param goal the goal node
     * @return the heuristic function value
     */
    private double heuristicFunction(Node node, Node goal) {
        double D = 1.0;
        // Manhattan distance
        double rowDiff = Math.abs(node.getTile().getRow() - goal.getTile().getRow());
        double colDiff = Math.abs(node.getTile().getColumn() - goal.getTile().getColumn());
        double distance = rowDiff + colDiff;

        // Gold factor
        double goldValue = node.getTile().getGold();
        double goldFactor = (goldValue > 0) ? 2.0 / goldValue : 0;

        return distance + D * distance - goldFactor;
    }
}
