/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

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
    
    public Node(Vertex vertex, Heuristic heuristic, boolean walkable) {
        this.vertex = vertex;
        this.walkable = walkable;
        this.heuristic = heuristic;
        this.parent = null;
        this.g = Float.POSITIVE_INFINITY;
        this.neighbours = new ArrayList<>();
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

//    /**
//     * Heuristic method using the Manhattan algorithm
//     *
//     * @param goal
//     */
//    public void setH(Node goal) {
//        Vector3f goalPosition = goal.getVertex().getPosition();
//        Vector3f position = vertex.getPosition();
//        
//        float dx = goalPosition.x - position.x;
//        float dy = goalPosition.y - position.y;
//        float dz = goalPosition.z - position.z;
//        
//        this.h = dx + dy + dz;
//    }
    
    public float getH(Node goal) {
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
    
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
    
    public boolean isWalkable() {
        return walkable;
    }
    
    public Vertex getVertex() {
        return vertex;
    }
    
    @Override
    public String toString() {
        return "Node: " + vertex.getPosition() + ": H = " + h + ", G = " + g;
    }
}
