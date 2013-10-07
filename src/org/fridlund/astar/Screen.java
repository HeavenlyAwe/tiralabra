/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 * Wrapper class for the LWGJL Display object
 *
 * @author Christoffer
 */
public class Screen {

    /**
     * Setup the LWJGL display object.
     *
     * Activates the Mouse and the Keyboard as well.
     *
     * @param title
     * @param width
     * @param height
     */
    public static void setupDisplay(String title, int width, int height) {
        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setVSyncEnabled(true);

            Display.create();
            Keyboard.create();
            Mouse.create();

        } catch (LWJGLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void update() {
        Display.update();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public static void destroy() {
        Keyboard.destroy();
        Mouse.destroy();
        Display.destroy();
        System.exit(0);
    }
}
