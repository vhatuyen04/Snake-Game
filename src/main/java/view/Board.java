/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * 
 */
package view;

import model.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.GameLogic;
import java.awt.Point;

/**
 *
 * @author hatuy
 * This is the Board to render everything to the screen
 */
public class Board extends JPanel implements KeyListener {
    private int n;
    private int size;
    private GameLogic logic;
    public Board (GameLogic logic) {
        this.n = logic.boardSize;
        this.size = 30;
        this.logic = logic;
        setPreferredSize(new Dimension(n*this.size, n*this.size));
        setBackground(new Color(210, 180, 140));
        setLayout(null); 
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }
    @Override
    public void paintComponent (Graphics g) {
        //System.out.println("hihi");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < logic.rocks.size(); i++) {
            //System.out.println(logic.rocks.get(i).getLocation().x + " " + logic.rocks.get(i).getLocation().y);
            g2d.fillRect(logic.rocks.get(i).getLocation().x*size, logic.rocks.get(i).getLocation().y*size, size, size);
        }
        
//        for (int r = 0; r < n; r++) {
//            for (int c = 0; c < n; c++) {
//                int x = c*size;
//                int y = r*size;
//                g2d.drawRect(x, y, size, size);
//            }
//        }
        //System.out.println(logic.rocks.size());
        
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < logic.getSnake().ps.size()-1; i++) {
            if (logic.getSnake().ps.get(i).x == logic.getSnake().ps.get(i+1).x) {
                int minY = Math.min(logic.getSnake().ps.get(i).y, logic.getSnake().ps.get(i+1).y);
                int maxY = Math.max(logic.getSnake().ps.get(i).y, logic.getSnake().ps.get(i+1).y);
                for (int j = minY; j <= maxY; j++) {
                    g2d.fillRect(logic.getSnake().ps.get(i).x*size, j*size, size, size);
                }
            }
            if (logic.getSnake().ps.get(i).y == logic.getSnake().ps.get(i+1).y) {
                int minX = Math.min(logic.getSnake().ps.get(i).x, logic.getSnake().ps.get(i+1).x);
                int maxX = Math.max(logic.getSnake().ps.get(i).x, logic.getSnake().ps.get(i+1).x);
                for (int j = minX; j <= maxX; j++) {
                    g2d.fillRect(j*size, logic.getSnake().ps.get(i).y*size, size, size);
                }
            }
            g2d.fillRect(logic.getSnake().ps.get(i).x*size, logic.getSnake().ps.get(i).y*size, size, size);
        }
        
        g2d.setColor(Color.YELLOW);
        Point foodPos = logic.getFood().getPosition();
        g2d.fillRect(foodPos.x*size, foodPos.y*size, size, size);
        
        g2d.setColor(new Color(144, 238, 144));
        g2d.fillRect(logic.getSnake().getHead().x*size, logic.getSnake().getHead().y*size, size, size);
    }
    @Override
    public void keyPressed (KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                logic.updateNewDirection(0);
                //System.out.println(0);
                break;
            }
            case KeyEvent.VK_DOWN -> {
                logic.updateNewDirection(2);
                //System.out.println(2);
                break;
            }
            case KeyEvent.VK_LEFT -> {
                logic.updateNewDirection(1);
                //System.out.println(1);
                break;
            }
            case KeyEvent.VK_RIGHT -> {
                logic.updateNewDirection(3);
                //System.out.println(3);
                break;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
