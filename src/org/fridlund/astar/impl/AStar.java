/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class AStar {

    private PriorityQueue<Node> open;
    private ArrayList<Node> closed;
    private ArrayList<Node> path;
    private Node goal_node;

    public AStar(Node start, Node goal) {

        open = new PriorityQueue<>(10, new NodeComparator());
        closed = new ArrayList<>();
        path = new ArrayList<>();

        restart(start, goal);
    }

    public final void restart(Node start, Node goal) {
        goal_node = goal;

        open.clear();
        closed.clear();
        path.clear();

        start.setG(0);
        open.add(start);
    }

    public boolean evaluateNextStep() {

        Node n = open.poll();

        if (n == null) {
            System.out.println("");
            System.out.println("Couldn't reach goal!");
            System.out.println("");
            throw new RuntimeException("Non-reachable node!");
        }
        if (n.equals(goal_node)) {
            System.out.println("");
            System.out.println("Goal reached!");
            System.out.println("");
            return true;
        }
        closed.add(n);

        for (Node neighbour : n.getNeighbours()) {
//            neighbour.setH(goal_node);

            if (!neighbour.isWalkable()) {
                System.out.println("Not walkable neighbour");
                continue;
            }

            float cost = n.getG() + movementCost(n, neighbour);
            System.out.println("Cost: " + cost);

            if (open.contains(neighbour) && cost < neighbour.getG()) {
                System.out.println("Removing from Open");
                open.remove(neighbour);
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
                neighbour.setParent(n);
            }

        }

        return false;
    }

    private float movementCost(Node current, Node next) {
        Vector3f nextPos = next.getVertex().getPosition();
        Vector3f currPos = current.getVertex().getPosition();
        Vector3f output = new Vector3f();

        return Vector3f.sub(nextPos, currPos, output).length();
    }

    public void showPath() {
        Node n = goal_node;
        while (n != null) {
            path.add(n);
            n = n.getParent();
        }

        open.clear();
        closed.clear();
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

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            if (o2.getF(goal_node) < o1.getF(goal_node)) {
                return 1;
            }
            if (o2.getF(goal_node) > o1.getF(goal_node)) {
                return -1;
            }
            return 0;
        }
    }
}
