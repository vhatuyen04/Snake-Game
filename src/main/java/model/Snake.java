/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/**
 *
 * @author hatuy
 * This is the Snake object
 */
public class Snake {
    public int direction; // random from 0 to 4
    int dx[] = {0, -1, 0, 1}; // up left down right 
    int dy[] = {-1, 0, 1, 0};
    int posX, posY;
    public List <Point> ps; // save the location of points
    final int velocity = 5;
    public Snake(int x, int y) {
        Random rand = new Random();
        direction = rand.nextInt(4);
        ps = new ArrayList <>();
        ps.add(new Point(x, y));
        ps.add(new Point(x+dx[direction], y+dy[direction]));
    }
    boolean collision () { // if the head collides with any parts 
        if (ps.size() < 3) {
            return false;
        }
        Point head = getHead();
        for (int i = 0; i < ps.size()-3; i++) {
            if (ps.get(i).x == head.x && 
                ps.get(i+1).x == head.x &&
                Math.min(ps.get(i).y, ps.get(i+1).y) <= head.y &&
                head.y <= Math.max(ps.get(i).y, ps.get(i+1).y) ||

                ps.get(i).y == head.y && 
                ps.get(i+1).y == head.y &&
                Math.min(ps.get(i).x, ps.get(i+1).x) <= head.x &&
                head.x <= Math.max(ps.get(i).x, ps.get(i+1).x))
                return true;
        }
        return false;
    }
    public Point getHead() {
        return ps.get(ps.size()-1);
    }
}
