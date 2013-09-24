/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.astar.impl.AStar;
import org.fridlund.astar.impl.Grid;
import org.fridlund.astar.impl.Node;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class Main {
    
    private static int grid_width = 20;
    private static int grid_height = 20;

    /**
     * Loads the natives for LWJGL, from the natives folder inside the lib
     * directory. By using this method you don't have to explicitly have to
     * choose natives (because they are different for different platforms).
     */
    public static void setupNativesLWJGL() {
        String lwjglPath = "org.lwjgl.librarypath";
        String userDir = System.getProperty("user.dir");
        String nativePath = "lib/natives/";
        
        File nativeFile = new File(new File(userDir, nativePath),
                LWJGLUtil.getPlatformName());
        
        System.setProperty(lwjglPath, nativeFile.getAbsolutePath());
        
        String inputPath = "net.java.games.input.librarypath";
        String lwjglProperty = System.getProperty(lwjglPath);
        
        System.setProperty(inputPath, lwjglProperty);
    }

    /**
     * Setup the LWJGL display object
     *
     * @param title
     * @param width
     * @param height
     */
    private static void setupDisplay(String title, int width, int height) {
        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    private static int evaluationTimeStep = 200; // milliseconds

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int width = 800;
        int height = 600;
        
        setupNativesLWJGL();
        setupDisplay("A* test", 800, 600);
        
        Grid grid = new Grid(grid_width, grid_height);
        grid.generateNodes();
        grid.generateLabyrinth(11001500);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        
        
        Random random = new Random(10);
        Node start = grid.getNode(0, 0);
        Node goal = grid.getNode(19, 19);
        
        AStar aStar = new AStar(start, goal);
        
        boolean startSimulation = false;
        
        boolean finished = false;
        
        long lastTime = System.nanoTime();
        long currentTime;
        
        
        boolean[] keysHeldDown = new boolean[Keyboard.getKeyCount()];
        System.out.println(keysHeldDown.length);
        
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            currentTime = System.nanoTime();
            
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                startSimulation = true;
                if (finished) {
                    aStar.restart(start, goal);
                    finished = false;
                }
            }
            
            if (finished == false) {
                
                if (currentTime - lastTime >= evaluationTimeStep * 1000000) {
                    if (startSimulation) {
                        finished = aStar.evaluateNextStep();
                    }
                    
                    if (finished) {
                        aStar.showPath();
                        startSimulation = false;
                    }
                    lastTime = currentTime;
                }

//                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !keyButtons.contains(Keyboard.KEY_RIGHT)) {
//                    keyButtons.add(Keyboard.KEY_RIGHT);
//                }
//
//                if (keyButtons.contains(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
//                    keyButtons.remove(Keyboard.KEY_RIGHT);
//                    finished = aStar.evaluateNextStep();
//                }
            }
            
            if (Mouse.isButtonDown(0)) {
                int i = Mouse.getX() / Grid.TILE_SIZE;
                int j = Mouse.getY() / Grid.TILE_SIZE;
                if (i >= 0 && i < grid_width && j >= 0 && j < grid_height) {
                    grid.setTileWalkable(i, j);
                }
            } else if (Mouse.isButtonDown(1)) {
                int i = Mouse.getX() / Grid.TILE_SIZE;
                int j = Mouse.getY() / Grid.TILE_SIZE;
                if (i >= 0 && i < grid_width && j >= 0 && j < grid_height) {
                    grid.setTileNonWalkable(i, j);
                }
            }
            
            glBegin(GL_QUADS);
            {
                for (int i = 0; i < grid.getGrid().length; i++) {
                    for (int j = 0; j < grid.getGrid()[0].length; j++) {
                        
                        Node n = grid.getNode(i, j);
                        Vector3f position = n.getVertex().getPosition();
                        
                        
                        if (finished) {
                            /*
                             * Render only the path and the non-walkable tiles when finished.
                             */
                            if (n.isWalkable()) {
                                glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
                            } else {
                                glColor4f(0.5f, 0, 0, 1.0f);
                            }
                            if (aStar.getPath().contains(n)) {
                                glColor4f(0.5f, 0f, 0.5f, 1f);
                            }
                        } else {
                            /*
                             * Render the tiles in different colors based on their evaluation.
                             */
                            if (n.isWalkable()) {
                                glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
                            } else {
                                glColor4f(0.5f, 0, 0, 1.0f);
                            }
                            
                            if (aStar.getOpenList().contains(n)) {
                                glColor4f(0, 0, 1f, 1f);
                            }
                            
                            if (aStar.getClosedList().contains(n)) {
                                glColor4f(0, 1f, 0, 1f);
                            }
                        }
                        
                        glVertex2f(position.x, position.z);
                        glVertex2f(position.x + Grid.TILE_SIZE - 1, position.z);
                        glVertex2f(position.x + Grid.TILE_SIZE - 1, position.z + Grid.TILE_SIZE - 1);
                        glVertex2f(position.x, position.z + Grid.TILE_SIZE - 1);
                    }
                }
            }
            glEnd();
            
            
            Display.update();
        }
        
    }
}
