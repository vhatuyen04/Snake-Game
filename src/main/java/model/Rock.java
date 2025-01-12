/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Point;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author hatuy
 * This is the Rock object
 */
public class Rock {
    private Point pos;
    public Rock (int x1, int y1) {
        Random rand = new Random();
        int x = rand.nextInt(x1);
        int y = rand.nextInt(y1);
        pos = new Point (x, y);
    }
    public Point getLocation() {
        return pos;
    }
}
