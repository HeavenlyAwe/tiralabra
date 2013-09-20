/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.util.Comparator;
import org.fridlund.astar.gui.GUI;
import org.fridlund.astar.impl.Grid;
import org.fridlund.astar.impl.Implementation;

/**
 *
 * @author Christoffer
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Grid grid = new Grid(10, 20);
        grid.generateNodes();
        grid.showGrid();

        GUI gui = new GUI(grid);

        Implementation imp = new Implementation(gui);
        if (imp.aStar(grid.getNode(0, 0), grid.getNode(9, 19))) {
            imp.showPath(grid.getNode(9, 19), grid.getNode(0, 0));
            grid.showGrid();
        }
    }

    public class EvaluationComparator implements Comparator<Float> {

        @Override
        public int compare(Float o1, Float o2) {
            return (int) (o1 - o2);
        }
    }
}
