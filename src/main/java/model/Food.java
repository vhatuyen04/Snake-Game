/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author hatuy
 * This is the Food object
 */
public class Food {
    private Point pos;
    public Food (Point p) {
        pos = p;
    }
    public Food (int sizeX, int sizeY) {
        Random rand = new Random();
        int x = rand.nextInt(sizeX);
        int y = rand.nextInt(sizeY);
        pos = new Point(x, y);
    }
    public Point getPosition () {
        return pos;
    }
}
