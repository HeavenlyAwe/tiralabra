/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.io.File;
import org.lwjgl.LWJGLUtil;

/**
 *
 * @author Christoffer
 */
public class Main {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final String TITLE = "A* Test";

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
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        setupNativesLWJGL();
        Screen.setupDisplay(TITLE, WIDTH, HEIGHT);

        Game game = new Game();
        game.start();

        Screen.destroy();

    }
}
