/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.fridlund.astar.impl.Grid;

/**
 *
 * @author Christoffer
 */
public class GUI extends JFrame {
    
    private Grid grid;
    private JLabel[][] labels;
    
    public GUI(Grid grid) {
        this.grid = grid;
        
        this.setTitle("A* Test");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 600));
        
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        labels = new JLabel[grid.getGrid().length][grid.getGrid()[0].length];
        
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[0].length; j++) {
                c.gridx = i;
                c.gridy = j;
                labels[i][j] = new JLabel("" + grid.getGrid()[i][j].getG());
                this.add(labels[i][j], c);
            }
        }
        
        this.setVisible(true);
    }
    
    public void updateGui() {
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[0].length; j++) {
                labels[i][j].setText(grid.getNode(i, j).toString());
            }
        }
    }
}
