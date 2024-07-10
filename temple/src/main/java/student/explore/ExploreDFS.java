package student.explore;

import game.ExplorationState;
import game.NodeStatus;
import java.util.*;

public class ExploreDFS implements ExplorationStrategy {
  @Override
  public void explore(ExplorationState state) {
    // Depth-first search Exploration

    // Set to keep track of visited nodes
    Set<Long> visitedNodeIds = new HashSet<>();
    // Deque serving as a Stack to keep track of the exploration path
    Deque<Long> explorationPath = new ArrayDeque<>();

    // Start the exploration from the current location
    long startNodeId = state.getCurrentLocation();
    // Add the starting node to the exploration path and mark it as visited
    explorationPath.push(startNodeId);
    visitedNodeIds.add(startNodeId);

    // Check if the starting node is the target
    if (state.getDistanceToTarget() == 0) {
      return;
    }

    // DFS Loop - Continue exploring until the exploration path is empty
    while (!explorationPath.isEmpty()) {
      // Move to the current node
      long currentNodeId = explorationPath.peek();
      moveToNode(state, currentNodeId);

      // Check if the current node is the target
      if (state.getDistanceToTarget() == 0) {
        return;
      }

      // Get the unvisited neighbours of the current node
      List<NodeStatus> unvisitedNeighbours = getUnvisitedNeighbours(state, visitedNodeIds);

      // If there are unvisited neighbours, select the next move
      if (!unvisitedNeighbours.isEmpty()) {
        NodeStatus nextMove = selectNextMove(unvisitedNeighbours);
        explorationPath.push(nextMove.nodeID());
        visitedNodeIds.add(nextMove.nodeID());
      } else {
        // If there are no unvisited neighbours, backtrack
        explorationPath.pop();
        if (!explorationPath.isEmpty()) {
          // Get the previous node from the top of the stack
          long previousNodeId = explorationPath.peek();
          // Move to the previous node
          moveToNode(state, previousNodeId);
        }
      }
    }
  }

  /** Move to a specific node in the cavern. */
  private void moveToNode(ExplorationState state, long nodeId) {
    // Safety check to avoid moving to the same node and throwing an exception
    if (state.getCurrentLocation() != nodeId) {
      state.moveTo(nodeId);
    }
  }

  /**
   * Select the next move based on a heuristic.
   *
   * @return NodeStatus
   */
  private NodeStatus selectNextMove(List<NodeStatus> neighbours) {
    // Implements the heuristic logic
    return neighbours.stream()
        .min(Comparator.comparingInt(NodeStatus::distanceToTarget))
        .orElse(null);
  }

  /**
   * Get a list of unvisited neighbours sorted by distance to the target.
   *
   * @param state The current exploration state.
   * @param visitedNodes The set of visited node IDs.
   * @return A list of unvisited neighbours sorted by distance to the target.
   */
  private List<NodeStatus> getUnvisitedNeighbours(ExplorationState state, Set<Long> visitedNodes) {
    return state.getNeighbours().stream()
        .filter(neighbour -> !visitedNodes.contains(neighbour.nodeID()))
        .sorted(Comparator.comparingInt(NodeStatus::distanceToTarget))
        .toList();
  }
}
