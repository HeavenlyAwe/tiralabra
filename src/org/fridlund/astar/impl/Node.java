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
    private boolean checked;
    private Node parent;
    private ArrayList<Node> neighbours;
    private float g;
    private float h;
    private char token;
    public final int x;
    public final int y;

    public Node(boolean walkable, int x, int y) {
        this.walkable = walkable;
        this.checked = false;
        this.parent = null;
        this.g = Float.POSITIVE_INFINITY;
        this.neighbours = new ArrayList<>();
        this.setWalkable(walkable);

        this.x = x;
        this.y = y;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public float getF() {
        return getG() + getH();
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getG() {
        return g;
    }

    public void setH(Node goal) {
        this.h = Math.abs((goal.x - x) * (goal.x - x) + (goal.y - y) * (goal.y - y));
    }

    public float getH() {
        return h;
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

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return "\t" + token + ": " + g;
    }
}
