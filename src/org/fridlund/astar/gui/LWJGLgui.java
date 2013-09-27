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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.astar.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

/**
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
    private Button createGridButton;
    private Button startSimulationButton;
    private Button selectStartButton;
    private Button selectGoalButton;
    private Button drawWallButton;
    private Button drawFloorButton;
    private boolean startSelected = false;
    private boolean goalSelected = false;

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

        createEditFields();
        createButtons();
    }

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
    }

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
    }

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

    private void createGridButtonCallback() {
        this.requestKeyboardFocus(null);
        game.initialize();
        game.createGrid(gridWidth, gridHeight);

        selectGoalButton.setEnabled(true);
        selectStartButton.setEnabled(true);
        drawWallButton.setEnabled(true);
        drawFloorButton.setEnabled(true);
    }

    private void selectStartButtonCallback() {
        this.requestKeyboardFocus(null);
        game.selectStartNode();

        startSelected = true;
        if (startSelected && goalSelected) {
            startSimulationButton.setEnabled(true);
        }
    }

    private void selectGoalButtonCallback() {
        this.requestKeyboardFocus(null);
        game.selectGoalNode();

        goalSelected = true;
        if (startSelected && goalSelected) {
            startSimulationButton.setEnabled(true);
        }
    }

    private void drawWallButtonCallback() {
        this.requestKeyboardFocus(null);
        game.drawWall();
    }

    private void drawFloorButtonCallback() {
        this.requestKeyboardFocus(null);
        game.drawFloor();
    }

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

    public void enableRestart() {

        startSelected = false;
        goalSelected = false;

        gridWidthEditField.setEnabled(true);
        gridHeightEditField.setEnabled(true);

        createGridButton.setEnabled(true);
        startSimulationButton.setEnabled(false);
    }

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

        startSimulationButton.setPosition(buttonX, Display.getDisplayMode().getHeight() - 53);
        startSimulationButton.setSize(150, 33);
    }

    public void update() {
        gui.update();
    }

    public void render() {
    }
}
