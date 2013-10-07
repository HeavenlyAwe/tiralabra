/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.io.File;
import org.fridlund.astar.impl.AStar;
import org.fridlund.astar.impl.Grid;
import org.fridlund.astar.impl.Node;
import org.fridlund.collections.priorityqueue.PriorityQueue;
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

//        Grid g = new Grid(10, 10);
//        g.generateNodes();
//
//        Node start = g.getNode(0, 0);
//        Node goal = g.getNode(9, 9);
//
//        AStar aStar = new AStar(start, goal);
//
//        AStar.NodeComparator comp = aStar.new NodeComparator();
//        PriorityQueue<Node> q = new PriorityQueue<>(comp);
//
//        q.add(g.getNode(0, 0));
//        q.add(g.getNode(0, 1));
//        q.add(g.getNode(1, 0));
//        q.add(g.getNode(1, 1));
//
//        System.out.println("");
//        System.out.println("");
//
//        System.out.println("");
//        Object[] nodes = q.toArray();
//        for (int i = 0; i < nodes.length; i++) {
//            System.out.println((Node) nodes[i]);
//        }
//        System.out.println("");
//
//        Node n = q.poll();
//        System.out.println("");
//        System.out.println("n = " + n);
//        System.out.println("first = " + g.getNode(0, 0));
//        System.out.println("last = " + g.getNode(1, 1));
//
//        System.out.println("");
//        nodes = q.toArray();
//        for (int i = 0; i < nodes.length; i++) {
//            System.out.println((Node) nodes[i]);
//        }
//        System.out.println("");
    }
}
