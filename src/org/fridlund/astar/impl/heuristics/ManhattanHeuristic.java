/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl.heuristics;

import org.fridlund.astar.impl.Heuristic;
import org.fridlund.astar.impl.Node;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class ManhattanHeuristic implements Heuristic {

    @Override
    public float heuristic(Node current, Node goal) {
        Vector3f goalPosition = goal.getVertex().getPosition();
        Vector3f position = current.getVertex().getPosition();

        float dx = Math.abs(goalPosition.x - position.x);
        float dy = Math.abs(goalPosition.y - position.y);
        float dz = Math.abs(goalPosition.z - position.z);

        return dx + dy + dz;
    }
}
