/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.astar.impl;

/**
 *
 * @author Christoffer
 */
public class Color {

    public final static Color RED = new Color(1, 0, 0, 1);
    public final static Color GREEN = new Color(0, 1, 0, 1);
    public final static Color BLUE = new Color(0, 0, 1, 1);
    public final static Color YELLOW = new Color(1, 1, 0, 1);
    public final static Color MAGENTA = new Color(1, 0, 1, 1);
    public final static Color CYAN = new Color(0, 1, 1, 1);
    public final static Color GREY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    public final static Color BLACK = new Color(0, 0, 0, 1);
    /*
     * Variables
     */
    public float r;
    public float g;
    public float b;
    public float a;

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 1;
    }

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setAlpha(float a) {
        this.a = a;
    }
}
