/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Christoffer
 */
public class Node {

    // Parent for retracking the path
    private Node parent;
    // Neighbouring nodes (independent of the world presentation; grid, mesh etc)
    private ArrayList<Node> neighbours;
    // Position data
    private Vertex vertex;
    private boolean walkable;
    private float g;
    private Heuristic heuristic;
    private float h;
    private Color color;

    public Node(Vertex vertex, Heuristic heuristic, boolean walkable) {
        this.vertex = vertex;
        this.walkable = walkable;
        this.heuristic = heuristic;
        this.parent = null;
        this.g = Float.POSITIVE_INFINITY;
        this.neighbours = new ArrayList<>();
        this.color = new Color(0.5f, 0.5f, 0.5f, 1.0f);
        this.setWalkable(walkable);
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }

    public float getF(Node goal) {
        return getG() + getH(goal);
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getG() {
        return g;
    }

    public float getH(Node goal) {
        if (goal == null) {
            return Float.POSITIVE_INFINITY;
        }
        return heuristic.heuristic(this, goal);
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

    public final void setWalkable(boolean walkable) {
        this.walkable = walkable;
        if (!walkable) {
            this.color = Color.RED;
        } else {
            this.color = Color.GREY;
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = new Color(r, g, b, a);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void render() {
        glBegin(GL_QUADS);
        {
            glColor4f(color.r, color.g, color.b, color.a);
            glVertex3f(vertex.getPosition().x, 0, vertex.getPosition().z);
            glVertex3f(vertex.getPosition().x + Grid.TILE_SIZE - 1, 0, vertex.getPosition().z);
            glVertex3f(vertex.getPosition().x + Grid.TILE_SIZE - 1, 0, vertex.getPosition().z + Grid.TILE_SIZE - 1);
            glVertex3f(vertex.getPosition().x, 0, vertex.getPosition().z + Grid.TILE_SIZE - 1);
        }
        glEnd();
    }

    @Override
    public String toString() {
        return "Node: " + vertex.getPosition() + ": H = " + h + ", G = " + g;
    }
}
