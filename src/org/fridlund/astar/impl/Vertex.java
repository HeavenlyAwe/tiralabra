/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class Vertex {

    private Vector3f position;

    public Vertex() {
        this(new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }
}
