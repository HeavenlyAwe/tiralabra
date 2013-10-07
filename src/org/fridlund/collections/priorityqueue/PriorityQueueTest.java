/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.collections.priorityqueue;

import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Christoffer
 */
public class PriorityQueueTest {

    private PriorityQueue<Double> queue;

    public PriorityQueueTest() {

        queue = new PriorityQueue<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                if (o1.doubleValue() > o2.doubleValue()) {
                    return 1;
                } else if (o1.doubleValue() < o2.doubleValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        Random random = new Random(1);

        for (int i = 0; i < 5; i++) {
            queue.add(i / 2.0);
        }

        queue.poll();

        queue.add(10.0);
    }

    public static void main(String[] args) {
        new PriorityQueueTest();
    }
}
