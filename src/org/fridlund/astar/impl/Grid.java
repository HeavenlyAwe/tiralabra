/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

/**
 *
 * @author Christoffer
 */
public class Grid {

    private Node[][] grid;

    public Grid(int rows, int columns) {
        grid = new Node[rows][columns];
    }

    public void generateNodes() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Node(true);
            }
        }


//        for (int i = 0; i < grid.length - 1; i++) {
//            grid[i][4].setWalkable(false);
//            grid[i + 1][6].setWalkable(false);
//        }

        grid[0][1].setWalkable(false);
        grid[2][0].setWalkable(false);
        grid[2][1].setWalkable(false);
        grid[2][2].setWalkable(false);
        grid[2][3].setWalkable(false);
        grid[1][3].setWalkable(false);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].isWalkable()) {
                    if (i - 1 > 0 && grid[i - 1][j].isWalkable()) {
                        grid[i][j].addNeighbour(grid[i - 1][j]);
                    }
                    if (j - 1 > 0 && grid[i][j - 1].isWalkable()) {
                        grid[i][j].addNeighbour(grid[i][j - 1]);
                    }
                    if (i + 1 < grid.length && grid[i + 1][j].isWalkable()) {
                        grid[i][j].addNeighbour(grid[i + 1][j]);
                    }
                    if (j + 1 < grid[0].length && grid[i][j + 1].isWalkable()) {
                        grid[i][j].addNeighbour(grid[i][j + 1]);
                    }
                }
            }
        }

    }

    public Node getNode(int i, int j) {
        return grid[i][j];
    }

    public void showGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].toString());
            }
            System.out.println("");
        }
    }

    public Node[][] getGrid() {
        return grid;
    }
}
