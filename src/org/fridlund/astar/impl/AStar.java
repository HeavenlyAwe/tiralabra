/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import org.fridlund.collections.arraylist.ArrayList;
import org.fridlund.collections.priorityqueue.PriorityQueue;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class AStar {

    public static enum Heuristics {

        MANHATTAN(new Heuristic() {
    @Override
    public float heuristic(Node current, Node goal) {
        Vector3f goalPosition = goal.getVertex().getPosition();
        Vector3f position = current.getVertex().getPosition();

        float dx = Math.abs(goalPosition.x - position.x);
        float dy = Math.abs(goalPosition.y - position.y);
        float dz = Math.abs(goalPosition.z - position.z);

        return dx + dy + dz;
    }
}),
        DIJKSTRA(new Heuristic() {
    @Override
    public float heuristic(Node current, Node goal) {
        return 0;
    }
}),
        CHEBYSHEV(new Heuristic() {
    @Override
    public float heuristic(Node current, Node goal) {
        Vector3f goalPosition = goal.getVertex().getPosition();
        Vector3f position = current.getVertex().getPosition();

        float dx = Math.abs(goalPosition.x - position.x) / Grid.TILE_SIZE;
        float dy = Math.abs(goalPosition.y - position.y) / Grid.TILE_SIZE;
        float dz = Math.abs(goalPosition.z - position.z) / Grid.TILE_SIZE;

        float D = Grid.TILE_SIZE;
        float D2 = (float) Math.sqrt(2) * Grid.TILE_SIZE;
        float D3 = (float) Math.sqrt(3) * Grid.TILE_SIZE;

        float max = Math.max(dx, Math.max(dy, dz));
        float mid = dx == max ? (dy > dz ? dy : dz)
                : (dy == max ? (dx > dz ? dx : dz) : (dx > dy ? dx : dy));
        float min = Math.min(dx, Math.min(dy, dz));

        return D3 * min + D2 * (mid - min) + D * (max - mid);
    }
}),
        EUCLIDIAN(new Heuristic() {
    @Override
    public float heuristic(Node current, Node goal) {
        Vector3f goalPosition = goal.getVertex().getPosition();
        Vector3f position = current.getVertex().getPosition();

        float dx = Math.abs(goalPosition.x - position.x);
        float dy = Math.abs(goalPosition.y - position.y);
        float dz = Math.abs(goalPosition.z - position.z);

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
});
        /*
         * Implementation of the Enum
         */
        private Heuristic heuristic;

        Heuristics(Heuristic heuristic) {
            this.heuristic = heuristic;
        }

        public Heuristic getHeuristic() {
            return heuristic;
        }
    }
    private Queue<Node> open;
    private Collection<Node> closed;
    private Collection<Node> path;
    private Node goalNode;

    public AStar(Node start, Node goal, boolean useOwnCollections) {

        if (useOwnCollections) {
            open = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {

                    if (o2.getF(goalNode) < o1.getF(goalNode)) {
                        return -1;
                    }
                    if (o2.getF(goalNode) > o1.getF(goalNode)) {
                        return 1;
                    }
                    return 0;
                }
            });
            closed = new ArrayList<>();
            path = new ArrayList<>();
        } else {
            open = new java.util.PriorityQueue<>(10, new Comparator<Node>() {
                @Override
                public int compare(Node n1, Node n2) {

                    if (n2.getF(goalNode) < n1.getF(goalNode)) {
                        return 1;
                    }
                    if (n2.getF(goalNode) > n1.getF(goalNode)) {
                        return -1;
                    }
                    return 0;
                }
            });
            closed = new java.util.ArrayList<>();
            path = new java.util.ArrayList<>();
        }

        restart(start, goal);
    }

    public final void restart(Node start, Node goal) {
        goalNode = goal;

        open.clear();
        closed.clear();
        path.clear();

        start.setG(0);
        open.add(start);
    }

    public boolean evaluateNextStep() {

        Node n = open.poll();

        if (n == null) {
//            System.out.println("");
//            System.out.println("Couldn't reach goal!");
//            System.out.println("");
            return true;
        }
        if (n.equals(goalNode)) {
//            System.out.println("");
//            System.out.println("Goal reached!");
//            System.out.println("");
            return true;
        }

        closed.add(n);
        n.setColor(Color.GREEN);

        for (Node neighbour : n.getNeighbours()) {

            if (!neighbour.isWalkable()) {
                continue;
            }

            float cost = n.getG() + n.getCostTo(neighbour);

            if (open.contains(neighbour) && cost < neighbour.getG()) {
                open.remove(neighbour);
                neighbour.setColor(Color.GREY);
            }
//            /*
//             * This should never happen if you have an monotone admissible heuristic.
//             * However in games we often have inadmissible heuristics.
//             */
//            if (closed.contains(neighbour) && cost < neighbour.getG()) {
//                System.out.println("Removing from Closed");
//                closed.remove(neighbour);
//            }



            if (!closed.contains(neighbour) && !open.contains(neighbour)) {
                neighbour.setG(cost);
                open.add(neighbour);
                neighbour.setColor(Color.BLUE);
                neighbour.setParent(n);
            }

        }

        return false;
    }

    public void showPath() {
        Node n = goalNode;

        while (n != null) {
            path.add(n);
            n.setColor(Color.MAGENTA);
            n = n.getParent();
        }

        open.clear();
        closed.clear();
    }

    public boolean isPath(Node n) {
        return path.contains(n);
    }

    public boolean isOpened(Node n) {
        return open.contains(n);
    }

    public boolean isClosed(Node n) {
        return closed.contains(n);
    }

    public Collection getClosedList() {
        return closed;
    }

    public Collection getOpenList() {
        return open;
    }

    public Collection getPath() {
        return path;
    }

    /**
     *
     * @param goalNode
     */
    public void setGoalNode(Node goalNode) {
        // TODO: add support to change the goalNode on the fly
        this.goalNode = goalNode;
    }

    public class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            if (o2.getF(goalNode) < o1.getF(goalNode)) {
                return -1;
            }
            if (o2.getF(goalNode) > o1.getF(goalNode)) {
                return 1;
            }
            return 0;
        }
    }
}
