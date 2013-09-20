/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.ArrayList;

/**
 *
 * @author Christoffer
 */
public class Node {

    private boolean walkable;
    private Node parent;
    private ArrayList<Node> neighbours;
    private float g;
    private char token;

    public Node(boolean walkable) {
        this.walkable = walkable;
        this.parent = null;
        this.g = 0;
        this.neighbours = new ArrayList<>();
        this.setWalkable(walkable);
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public float getF() {
        return getG() + h();
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getG() {
        return g;
    }

    public float h() {
        return 0;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void setToken(char token) {
        this.token = token;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
        if (walkable) {
            this.token = 'o';
        } else {
            this.token = 'x';
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public String toString() {
        return "\t" + token + ": " + g;
    }
}
