/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fridlund.astar.impl.AStar;
import org.fridlund.astar.impl.Grid;
import org.fridlund.astar.impl.Node;

/**
 *
 * @author Christoffer
 */
public class StressTest {

    private static ArrayList<Double> times;
    private static int loops = 1;
    private static int iterations = 1;
    private static int widthFactor = 50;
    private static int heightFactor = 50;

    public static boolean runStressTest(String[] args) {
        if (args[0].equals("-s")) {
            if (args.length > 3) {
                try {
                    widthFactor = Integer.parseInt(args[1]);
                    heightFactor = Integer.parseInt(args[2]);
                    iterations = Integer.parseInt(args[3]);

                    if (args.length > 4) {

                        loops = Integer.parseInt(args[4]);

                        if (args.length > 5) {
                            boolean ownCollections = args[5].equals("yes");
                            if (ownCollections) {
                                System.out.println("OWN COLLECTIONS");
                                System.out.println("-------------------------");
                            } else {
                                System.out.println("UTILS COLLECTIONS");
                                System.out.println("-------------------------");
                            }
                            stress(ownCollections);
                            return true;
                        }

                    }

                    System.out.println("OWN COLLECTIONS");
                    System.out.println("-------------------------");
                    stress(true);
                    System.out.println("");
                    System.out.println("UTILS COLLECTIONS");
                    System.out.println("-------------------------");
                    stress(false);
                } catch (NumberFormatException ex) {
                    showUsage();
                }
            } else {
                showUsage();
            }
        } else {
            showUsage();
        }
        return false;
    }
    private static boolean indicesAppended = false;
    private static DecimalFormat df = new DecimalFormat("#.###");

    private static void stress(boolean withOwnCollections) {

        for (int l = 1; l <= loops; l++) {
            times = new ArrayList<>();

            for (int i = 1; i <= iterations; i++) {
                int width = widthFactor * i;
                int height = heightFactor * i;

                Grid grid = new Grid(width, height, AStar.Heuristics.MANHATTAN.getHeuristic());
                grid.generateNodes(true);

                Node startNode = grid.getNode(0, 0);
                Node goalNode = grid.getNode(width - 1, height - 1);

                AStar aStar = new AStar(startNode, goalNode, withOwnCollections);



                long startTime = System.nanoTime();

                while (true) {
                    if (aStar.evaluateNextStep()) {
                        break;
                    }
                }

                double millis = (System.nanoTime() - startTime) / 1000000.0;

                times.add(millis);
                System.out.println("i = " + i + ", WIDTH = " + width + ", HEIGHT = " + height + ", Time = " + millis + " ms");
            }

            try {
                File timeOutput = new File("time_output.txt");
                FileWriter fw = new FileWriter(timeOutput, true);

                if (indicesAppended == false) {
                    appendIndices(fw);
                }

                if (withOwnCollections) {
                    fw.append("own collection: ");
                } else {
                    fw.append("utils collection: ");
                }
                fw.append("time (ms):\t");
                for (int i = 0; i < times.size(); i++) {
                    fw.append(df.format(times.get(i)) + "\t");
                }

                if (l == loops) {
                    fw.append(System.getProperty("line.separator"));
                }
                fw.append(System.getProperty("line.separator"));
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(StressTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void appendIndices(FileWriter fw) {
        indicesAppended = true;
        try {
            fw.append(Calendar.getInstance().getTime().toString());
            fw.append(System.getProperty("line.separator"));

            fw.append("index:\t\t\t\t");
            for (int i = 0; i < times.size(); i++) {
                fw.append((i + 1) + "\t");
            }
            fw.append(System.getProperty("line.separator"));
            fw.append(System.getProperty("line.separator"));

        } catch (IOException ex) {
            Logger.getLogger(StressTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void showUsage() {
        System.out.println("usage: java -jar tiralabra.jar [-s] [width] [height] [number] [yes/no]");
        System.out.println("The [-s] command stands for 'Stress Test'");
        System.out.println("The [width] command stands for 'Grid WIDTH'");
        System.out.println("The [height] command stands for 'Grid HEIGHT'");
        System.out.println("The [number] command stands for 'Number of 'Stress Tests''");
        System.out.println("The [yes/no] command stands for 'yes: Use provieded Collections, no: Use java.utils Collections'");
        System.out.println("\tThe [yes/no] command is optional, if you don't provieded it, the stress test will test both collections");
        System.out.println("Leave out the extra commands for a normal run");
        System.out.println("\t(eg. java -jar tiralabra.jar)");
    }
}
