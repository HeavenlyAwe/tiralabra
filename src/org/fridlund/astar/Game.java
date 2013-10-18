/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import org.fridlund.astar.gui.LWJGLgui;
import org.fridlund.astar.impl.AStar;
import org.fridlund.astar.impl.Color;
import org.fridlund.astar.impl.Grid;
import org.fridlund.astar.impl.Heuristic;
import org.fridlund.astar.impl.Node;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Christoffer
 */
public class Game {

    private LWJGLgui gui;
    private boolean running;
    private boolean runSimulation;
    private boolean finishedSimulation;
    private Grid grid;
    private AStar aStar;
    private Node startNode;
    private Node goalNode;
    private double evaluationStepTime = 0.5; // seconds
    private Vector2f offset = new Vector2f();
    private long lastTime;
    private long currentTime;
    private double cumDT = 0;

    /**
     * Constructor
     *
     * creates the GUI and primary setup for OpenGL
     */
    public Game() {
        this.gui = new LWJGLgui(this);

        this.running = false;
        this.initialize();


        setupOpenGL();
    }

    /**
     * Creates an Orthogonal Perspective matrix for rendering stuff without
     * depth.
     *
     * Defines the clear color for OpenGL
     *
     * Rotates the table (in the X-Z plane), to show it from above.
     */
    private void setupOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getDisplayMode().getWidth(), 0, Display.getDisplayMode().getHeight(), 1000, -1000);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        // turn the board towards the camera
        glRotatef(-90, 1, 0, 0);
    }

    /**
     * Sets the running flag to true and starts the main loop
     */
    public void start() {
        running = true;

        mainLoop();
    }

    /**
     * Assigns a couple of flags for initialization
     */
    public final void initialize() {
        this.runSimulation = false;
        this.finishedSimulation = false;
    }

    /**
     * Generates the grid and the labyrinth
     *
     * @param width
     * @param height
     */
    public void createGrid(int width, int height, boolean allowDiagonals, Heuristic heuristic) {

        this.grid = new Grid(width, height, heuristic);
        this.grid.generateNodes(allowDiagonals);
//        this.grid.generateLabyrinth(1);
    }
    int fps = 0;

    private void mainLoop() {

        lastTime = System.nanoTime();

        double fpsTimer = 0;
        while (running) {
            Screen.clear();

            currentTime = System.nanoTime();
            // Calculates the delta time in seconds, from nanosecond scale
            double dt = (currentTime - lastTime) / 1000000000.0;
            fpsTimer += dt;
            fps++;
            if (fpsTimer >= 1.0) {
                Screen.setTitle("A* TEST, fps = " + fps);
                fps = 0;
                fpsTimer = 0;
            }
            input();
            update(dt);
            render();

            lastTime = currentTime;

            Screen.update();
        }
    }

    /**
     * Sets the running flag to false
     */
    public void stop() {
        running = false;
    }
    private boolean selectStartNode = false;
    private boolean selectGoalNode = false;
    private boolean drawWall = false;
    private boolean drawFloor = false;

    public void selectStartNode() {
        changeDrawType(false, true, false, false);
    }

    public void selectGoalNode() {
        changeDrawType(true, false, false, false);
    }

    public void drawWall() {
        changeDrawType(false, false, true, false);
    }

    public void drawFloor() {
        changeDrawType(false, false, false, true);
    }

    public boolean bothStartAndGoalNodeSelected() {
        return startNode != null && goalNode != null;
    }

    private void changeDrawType(boolean goal, boolean start, boolean wall, boolean floor) {
        this.selectGoalNode = goal;
        this.selectStartNode = start;
        this.drawWall = wall;
        this.drawFloor = floor;
    }

    public void startSimulation() {
        changeDrawType(false, false, false, false);
        runSimulation = true;
        aStar = new AStar(startNode, goalNode, false);
    }

    public double getEvaluationStepTime() {
        return evaluationStepTime;
    }

    public void setEvaluationStepTime(double evaluationStepTime) {
        this.evaluationStepTime = evaluationStepTime;
    }

    private void input() {
        if (grid != null) {
            int i = (Mouse.getX() - (int) offset.x) / Grid.TILE_SIZE;
            int j = (Mouse.getY() - (int) offset.y) / Grid.TILE_SIZE;

            if (Mouse.isButtonDown(0)) {
                if (i >= 0 && i < grid.getWidth() && j >= 0 && j < grid.getHeight() && !gui.mouseCollision(Mouse.getX(), Display.getDisplayMode().getHeight() - Mouse.getY())) {
                    selectStartAndGoalNode(i, j);

                    if (drawWall) {
                        grid.setTileNonWalkable(i, j);
                    } else if (drawFloor) {
                        grid.setTileWalkable(i, j);
                    }

//                    Node n = grid.getNode(i, j);
//                    System.out.println("F = " + n.getF(goalNode));
//                    System.out.println("G = " + n.getG());
//                    System.out.println("H = " + n.getH(goalNode));
//                    System.out.println("");
                }
            }
            if (Mouse.isButtonDown(1)) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                    int dy = Mouse.getDY();
                    glTranslatef(0, dy, 0);
                } else {
                    int dx = Mouse.getDX();
                    int dy = Mouse.getDY();

                    offset.x += dx;
                    offset.y += dy;

                    glTranslatef(dx, 0, dy);
                }
            }
        }

        if (Screen.isCloseRequested()) {
            stop();
        }
    }

    /**
     * Helper method for selecting marking start and goal nodes on the grid
     *
     * @param i
     * @param j
     */
    private void selectStartAndGoalNode(int i, int j) {
        if (grid.getNode(i, j).isWalkable()) {
            if (selectGoalNode) {
                if (goalNode != null) {
                    goalNode.setColor(Color.GREY);
                }
                goalNode = grid.getNode(i, j);
                goalNode.setColor(Color.BLACK);
            } else if (selectStartNode) {
                if (startNode != null) {
                    startNode.setColor(Color.GREY);
                }
                startNode = grid.getNode(i, j);
                startNode.setColor(Color.CYAN);
            }
        }
    }

    /**
     * Updates the simulation based on the different flags. When the simulation
     * is done it shows the path found.
     *
     * Enables the GUI for another run after it's finished.
     *
     * @param dt
     */
    private void update(double dt) {
        cumDT += dt;

        gui.update();

        if (runSimulation) {
            if (!finishedSimulation) {
                if (cumDT >= evaluationStepTime) {
                    finishedSimulation = aStar.evaluateNextStep();
                    cumDT = 0;
                }
            } else {
                runSimulation = false;
                aStar.showPath();
                gui.enableRestart();
                startNode = null;
                goalNode = null;
            }
        }
    }

    /**
     * Renders the grid (if it's not null) and updates the GUI
     */
    private void render() {
        if (grid != null) {
            grid.render();
        }
        gui.render();
    }
}
