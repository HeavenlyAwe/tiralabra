/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.astar.gui.GUI;

/**
 *
 * @author Christoffer
 */
public class Implementation {

    private PriorityQueue<Node> open;
    private ArrayList<Node> closed;
    private GUI grid;

    public Implementation(GUI grid) {
        this.grid = grid;
    }

    public boolean aStar(Node start, Node goal) {

        open = new PriorityQueue<>(10, new NodeComparator());
        closed = new ArrayList<>();

        open.add(start);
        closed.clear();

        while (true) {
            grid.updateGui();
            System.out.println("");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Implementation.class.getName()).log(Level.SEVERE, null, ex);
            }


            Node n = open.poll();

            if (n == null) {
                System.out.println("");
                System.out.println("Couldn't reach goal!");
                System.out.println("");
                return false;
            }
            if (n.equals(goal)) {
                System.out.println("");
                System.out.println("Goal reached!");
                System.out.println("");
                return true;
            }
            closed.add(n);

            for (Node neighbour : n.getNeighbours()) {
                if (!neighbour.isWalkable()) {
                    continue;
                }

                float cost = n.getG() + movementCost(n, neighbour);

                if (open.contains(neighbour) && cost < neighbour.getG()) {
                    open.remove(neighbour);
                }
                /*
                 * This should never happen if you have an monotone admissible heuristic.
                 * However in games we often have inadmissible heuristics.
                 */
                if (closed.contains(neighbour) && cost < neighbour.getG()) {
                    closed.remove(neighbour);
                }

                if (!closed.contains(neighbour) && !open.contains(neighbour)) {
                    neighbour.setG(cost);
                    open.add(neighbour);
                    neighbour.setParent(n);
                }

            }
        }
    }

    private float movementCost(Node current, Node next) {
        return 1;
    }

    public void showPath(Node goal, Node start) {

        Node n = goal;
        while (n != null) {
            n.setToken('p');
            n = n.getParent();
        }
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            if (o2.getF() < o1.getF()) {
                return 1;
            }
            if (o2.getF() > o1.getF()) {
                return -1;
            }
            return 0;
        }
    }
}
