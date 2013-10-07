/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.collections.arraylist.ArrayList;
import org.fridlund.collections.priorityqueue.PriorityQueue;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class AStar {

    private PriorityQueue<Node> open;
    private ArrayList<Node> closed;
    private ArrayList<Node> path;
    private Node goalNode;

    public AStar(Node start, Node goal) {

        open = new PriorityQueue<>(new NodeComparator());
        closed = new ArrayList<>();
        path = new ArrayList<>();

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
            System.out.println("");
            System.out.println("Couldn't reach goal!");
            System.out.println("");
            throw new RuntimeException("Non-reachable node!");
        }
        if (n.equals(goalNode)) {
            System.out.println("");
            System.out.println("Goal reached!");
            System.out.println("");
            return true;
        }

        closed.add(n);
        n.setColor(Color.GREEN);

        for (Node neighbour : n.getNeighbours()) {

            if (!neighbour.isWalkable()) {
                continue;
            }

            float cost = n.getG() + movementCost(n, neighbour);

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

    private float movementCost(Node current, Node next) {
        Vector3f nextPos = next.getVertex().getPosition();
        Vector3f currPos = current.getVertex().getPosition();
        Vector3f output = new Vector3f();

        return Vector3f.sub(nextPos, currPos, output).length();
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
