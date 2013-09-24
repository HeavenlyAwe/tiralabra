/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

/**
 *
 * @author Christoffer
 */
public interface Heuristic {

    public float heuristic(Node current, Node goal);
}
