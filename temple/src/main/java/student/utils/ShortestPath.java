package student.utils;

import game.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public record ShortestPath(Map<Node, Integer> shortestDistances, Map<Node, Node> previousNodes) {
    /**
     * Reconstruct the path from the start node to the end node.
     *
     * @param start the start node
     * @param end the end node
     * @return the path from the start node to the end node as a list of nodes
     */
    public List<Node> reconstructPath(Node start, Node end) {
        // Reconstruct the path from the end node to the start node
        List<Node> path = new ArrayList<>();
        Node current = end;

        // Traverse the path from the end node to the start node
        while (current != null && !current.equals(start)) {
            path.add(current);
            current = previousNodes.get(current);
        }

        // Reverse the path to get the correct order
        Collections.reverse(path);
        return path;
    }

    public int getDistanceTo(Node node) {
        return shortestDistances.get(node);
    }
}

