/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.gui;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.astar.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

/**
 * A GUI using the TWL (Theme Widget Library) for making a Swing-like gui in
 * OpenGL
 *
 * @author Christoffer
 */
public class LWJGLgui extends Widget {

    private Game game;
    private LWJGLRenderer renderer;
    private ThemeManager themeManager;
    private GUI gui;
    private int gridWidth;
    private int gridHeight;
    private EditField gridWidthEditField;
    private EditField gridHeightEditField;
    private EditField evaluationTimeEditField;
    private Button createGridButton;
    private Button startSimulationButton;
    private Button selectStartButton;
    private Button selectGoalButton;
    private Button drawWallButton;
    private Button drawFloorButton;
    private ArrayList<Widget> widgets;

    public LWJGLgui(Game game) {
        this.game = game;
        this.gridWidth = 30;
        this.gridHeight = 20;

        try {
            renderer = new LWJGLRenderer();
        } catch (LWJGLException ex) {
            Logger.getLogger(LWJGLgui.class.getName()).log(Level.SEVERE, null, ex);
        }

        gui = new GUI(this, renderer);

        try {
            themeManager = ThemeManager.createThemeManager(getClass().getResource("./original/theme.xml"), renderer);
        } catch (IOException ex) {
            Logger.getLogger(LWJGLgui.class.getName()).log(Level.SEVERE, null, ex);
        }
        gui.applyTheme(themeManager);

        widgets = new ArrayList<>();

        createEditFields();
        createButtons();

    }

    /**
     * Helper method for creating the TextFields
     */
    private void createEditFields() {
        gridWidthEditField = new EditField();
        gridWidthEditField.setText("" + gridWidth);
        gridWidthEditField.addCallback(new Callback() {
            @Override
            public void callback(int key) {
                gridWidthEditFieldCallback();
            }
        });
        gridWidthEditField.setTheme("editfield");
        this.add(gridWidthEditField);
        widgets.add(gridWidthEditField);

        gridHeightEditField = new EditField();
        gridHeightEditField.setText("" + gridHeight);
        gridHeightEditField.addCallback(new Callback() {
            @Override
            public void callback(int key) {
                gridHeightEditFieldCallback();
            }
        });
        gridHeightEditField.setTheme("editfield");
        this.add(gridHeightEditField);
        widgets.add(gridHeightEditField);


        evaluationTimeEditField = new EditField();
        evaluationTimeEditField.setText("" + (int) (game.getEvaluationStepTime() * 1000));
        evaluationTimeEditField.addCallback(new Callback() {
            @Override
            public void callback(int key) {
                evaluationTimeEditFieldCallback();
            }
        });
        evaluationTimeEditField.setTheme("editfield");
        this.add(evaluationTimeEditField);
        widgets.add(evaluationTimeEditField);
    }

    /**
     * Helper method for creating the Buttons
     */
    private void createButtons() {
        createGridButton = new Button("Create Grid");
        createGridButton.addCallback(new Runnable() {
            @Override
            public void run() {
                createGridButtonCallback();
            }
        });
        createGridButton.setTheme("button");
        this.add(createGridButton);
        widgets.add(createGridButton);

        selectStartButton = new Button("Select Start Node");
        selectStartButton.addCallback(new Runnable() {
            @Override
            public void run() {
                selectStartButtonCallback();
            }
        });
        selectStartButton.setTheme("button");
        selectStartButton.setEnabled(false);
        this.add(selectStartButton);
        widgets.add(selectStartButton);

        selectGoalButton = new Button("Select Goal Node");
        selectGoalButton.addCallback(new Runnable() {
            @Override
            public void run() {
                selectGoalButtonCallback();
            }
        });
        selectGoalButton.setTheme("button");
        selectGoalButton.setEnabled(false);
        this.add(selectGoalButton);
        widgets.add(selectGoalButton);

        drawWallButton = new Button("Draw Walls");
        drawWallButton.addCallback(new Runnable() {
            @Override
            public void run() {
                drawWallButtonCallback();
            }
        });
        drawWallButton.setTheme("button");
        drawWallButton.setEnabled(false);
        this.add(drawWallButton);
        widgets.add(drawWallButton);

        drawFloorButton = new Button("Draw Floors");
        drawFloorButton.addCallback(new Runnable() {
            @Override
            public void run() {
                drawFloorButtonCallback();
            }
        });
        drawFloorButton.setTheme("button");
        drawFloorButton.setEnabled(false);
        this.add(drawFloorButton);
        widgets.add(drawFloorButton);

        startSimulationButton = new Button("Start");
        startSimulationButton.addCallback(new Runnable() {
            @Override
            public void run() {
                startSimulationButtonCallback();
            }
        });
        startSimulationButton.setTheme("button");
        startSimulationButton.setEnabled(false);
        this.add(startSimulationButton);
        widgets.add(startSimulationButton);
    }

    /**
     * Callback method for the Grid Width field.
     *
     * It only accepts numbers. All other characters will be caught in a number
     * format exception and removed from the field.
     */
    private void gridWidthEditFieldCallback() {
        String text = gridWidthEditField.getText();
        int tempWidth = 0;
        if (text.length() > 0) {
            try {
                tempWidth = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                gridWidthEditField.setText(text.substring(0, text.length() - 1));
            }
        }
        gridWidth = tempWidth;
    }

    /**
     * Callback method for the Grid Height field.
     *
     * It only accepts numbers. All other characters will be caught in a number
     * format exception and removed from the field.
     */
    private void gridHeightEditFieldCallback() {
        String text = gridHeightEditField.getText();
        int tempHeight = 0;
        if (text.length() > 0) {
            try {
                tempHeight = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                gridHeightEditField.setText(text.substring(0, text.length() - 1));
            }
        }
        gridHeight = tempHeight;
    }

    private void evaluationTimeEditFieldCallback() {
        String text = evaluationTimeEditField.getText();
        int tempEvaluationTimeMillis = 0;
        if (text.length() > 0) {
            try {
                tempEvaluationTimeMillis = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                evaluationTimeEditField.setText(text.substring(0, text.length() - 1));
            }
        }
        game.setEvaluationStepTime(tempEvaluationTimeMillis / 1000.0);
    }

    /**
     * Create Grid button action
     *
     * Creates a grid, based of the values in the grid width and grid height
     * text fields. Then enables all the building buttons.
     */
    private void createGridButtonCallback() {
        this.requestKeyboardFocus(null);
        game.initialize();
        game.createGrid(gridWidth, gridHeight);

        selectGoalButton.setEnabled(true);
        selectStartButton.setEnabled(true);
        drawWallButton.setEnabled(true);
        drawFloorButton.setEnabled(true);
    }

    /**
     * Select start node button action.
     *
     * The update method will enable the start simulation button, after both the
     * start and goal nodes have been placed on the grid.
     */
    private void selectStartButtonCallback() {
        this.requestKeyboardFocus(null);
        game.selectStartNode();
    }

    /**
     * Select goal node button action.
     *
     * The update method will enable the start simulation button, after both the
     * start and goal nodes have been placed on the grid.
     */
    private void selectGoalButtonCallback() {
        this.requestKeyboardFocus(null);
        game.selectGoalNode();
    }

    /**
     * Draw wall button action
     */
    private void drawWallButtonCallback() {
        this.requestKeyboardFocus(null);
        game.drawWall();
    }

    /**
     * Draw floor button action
     */
    private void drawFloorButtonCallback() {
        this.requestKeyboardFocus(null);
        game.drawFloor();
    }

    /**
     * Starts the simulation and disables the building buttons. One could let
     * the building buttons be on, to try and prevent the AI from getting to the
     * goal.
     */
    private void startSimulationButtonCallback() {
        this.requestKeyboardFocus(null);
        game.startSimulation();

        gridWidthEditField.setEnabled(false);
        gridHeightEditField.setEnabled(false);

        createGridButton.setEnabled(false);
        selectStartButton.setEnabled(false);
        selectGoalButton.setEnabled(false);
        drawFloorButton.setEnabled(false);
        drawWallButton.setEnabled(false);
    }

    /**
     * Sets the GUI to be ready for a restart of the simulation
     */
    public void enableRestart() {
        gridWidthEditField.setEnabled(true);
        gridHeightEditField.setEnabled(true);

        createGridButton.setEnabled(true);
        startSimulationButton.setEnabled(false);
    }

    /**
     * Placing the GUI-components on the screen.
     */
    @Override
    protected void layout() {

        int buttonX = Display.getDisplayMode().getWidth() - 170;
        int buttonY = 20;


        gridWidthEditField.setPosition(buttonX, buttonY);
        gridWidthEditField.setSize(150, 33);

        buttonY += 33 + 20;

        gridHeightEditField.setPosition(buttonX, buttonY);
        gridHeightEditField.setSize(150, 33);

        buttonY += 33 + 50;

        createGridButton.setPosition(buttonX, buttonY);
        createGridButton.setSize(150, 33);

        buttonY += 33 + 50;

        selectStartButton.setPosition(buttonX, buttonY);
        selectStartButton.setSize(150, 33);

        buttonY += 33 + 20;

        selectGoalButton.setPosition(buttonX, buttonY);
        selectGoalButton.setSize(150, 33);

        buttonY += 33 + 20;

        drawWallButton.setPosition(buttonX, buttonY);
        drawWallButton.setSize(150, 33);

        buttonY += 33 + 20;

        drawFloorButton.setPosition(buttonX, buttonY);
        drawFloorButton.setSize(150, 33);





        evaluationTimeEditField.setPosition(buttonX, Display.getDisplayMode().getHeight() - 53 - 50);
        evaluationTimeEditField.setSize(150, 33);

        startSimulationButton.setPosition(buttonX, Display.getDisplayMode().getHeight() - 53);
        startSimulationButton.setSize(150, 33);
    }

    public boolean mouseCollision(int mouseX, int mouseY) {
        for (int i = 0; i < widgets.size(); i++) {
            Widget widget = widgets.get(i);
            int x0 = widget.getX();
            int y0 = widget.getY();
            int x1 = widget.getWidth() + x0;
            int y1 = widget.getHeight() + y0;
            if (x0 <= mouseX && mouseX <= x1 && y0 <= mouseY && mouseY <= y1) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        startSimulationButton.setEnabled(game.bothStartAndGoalNodeSelected());
    }

    /**
     * The GUI doesn't actually have a render method, it wants to get updated
     * instead. That's why I've chosen to do it in the render wrapper method,
     * for easier access.
     */
    public void render() {
        gui.update();
    }
}
