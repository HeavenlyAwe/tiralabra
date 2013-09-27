/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

import java.util.Random;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class Grid {

    public static final int TILE_SIZE = 20;
    private Node[][] grid;
    private Heuristic manhattanHeuristic;
    private Heuristic dijkstraHeuristic;

    public Grid(int rows, int columns) {
        grid = new Node[rows][columns];
    }

    public void generateNodes() {
        manhattanHeuristic = new Heuristic() {
            @Override
            public float heuristic(Node current, Node goal) {
                Vector3f goalPosition = goal.getVertex().getPosition();
                Vector3f position = current.getVertex().getPosition();

                float dx = Math.abs(goalPosition.x - position.x);
                float dy = Math.abs(goalPosition.y - position.y);
                float dz = Math.abs(goalPosition.z - position.z);

                return dx + dy + dz;
            }
        };
        dijkstraHeuristic = new Heuristic() {
            @Override
            public float heuristic(Node current, Node goal) {
                return 0;
            }
        };

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Node(new Vertex(new Vector3f(i * TILE_SIZE, 0, j * TILE_SIZE)), manhattanHeuristic, true);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // North
                if (j - 1 >= 0 && grid[i][j - 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i][j - 1]);
                }
                // North East
                if (j - 1 >= 0 && i + 1 < grid.length && grid[i + 1][j - 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i + 1][j - 1]);
                }
                // East
                if (i + 1 < grid.length && grid[i + 1][j].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i + 1][j]);
                }
                // South East
                if (i + 1 < grid.length && j + 1 < grid[0].length && grid[i + 1][j + 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i + 1][j + 1]);
                }
                // South
                if (j + 1 < grid[0].length && grid[i][j + 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i][j + 1]);
                }
                // South West
                if (j + 1 < grid[0].length && i - 1 >= 0 && grid[i - 1][j + 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i - 1][j + 1]);
                }
                // West
                if (i - 1 >= 0 && grid[i - 1][j].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i - 1][j]);
                }
                // North West
                if (i - 1 >= 0 && j - 1 >= 0 && grid[i - 1][j - 1].isWalkable()) {
                    grid[i][j].addNeighbour(grid[i - 1][j - 1]);
                }

            }
        }

    }

    public void setTileWalkable(int i, int j) {
        grid[i][j].setWalkable(true);
    }

    public void setTileNonWalkable(int i, int j) {
        grid[i][j].setWalkable(false);
    }

    public Node getNode(int i, int j) {
        return grid[i][j];
    }

    public void generateLabyrinth(long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int n = random.nextInt(100);
                if (n < 25) {
                    grid[i][j].setWalkable(false);
                }
            }
        }
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

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public void render() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].render();
            }
        }
    }
}
