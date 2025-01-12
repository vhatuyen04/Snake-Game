/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author hatuy
 * This is the Logic of the game
 */
public class GameLogic {
    private Snake snake;
    private Food food;
    public int boardSize;
    private boolean running;
    private boolean board[][];
    private Timer timer;
    private int direction;
    public boolean isWin;
    public boolean isLose;
    public int score = 0;
    public Snake getSnake() {
        return snake;
    }
    public Food getFood() {
        return food;
    }
    public List <Rock> rocks;
    public GameLogic (int size) {
        snake = new Snake(size/2, size/2);
        direction = snake.direction;
        boardSize = size;
        food = new Food(size, size);
        running = true;
        isWin = false;
        isLose = false;
        board = new boolean [size+10][size+10];
        rocks = new ArrayList <> ();
        Random rand = new Random();
        int rockSize = rand.nextInt(13, 17);
        for (int i = 0; i < rockSize; i++) {
            rocks.add(new Rock(boardSize, boardSize));
        }
    }
    public boolean snakeMove1Unit () {
        //System.out.println("Snake: " + direction + " " + this.direction);
        if (snake.collision()) return false;
        if (snake.direction == (2+direction)%4) {
            direction = snake.direction;
        }
        boolean willEat = false;
        Point newHead = new Point (snake.getHead().x + snake.dx[direction], snake.getHead().y + snake.dy[direction]);
        if (newHead.x < 0 || newHead.x >= boardSize || newHead.y < 0 || newHead.y >= boardSize) {
            isLose = true;
            return false;
        }
        if (food.getPosition().x == newHead.x && food.getPosition().y == newHead.y){
            willEat = true;
        }
        for (int i = 0; i < rocks.size(); i++) {
            if (rocks.get(i).getLocation().x == newHead.x && rocks.get(i).getLocation().y == newHead.y) {
                isLose = true;
                return false;
            }
        }
        if (!willEat) {
            if (snake.ps.get(0).x == snake.ps.get(1).x) {
                if (snake.ps.get(0).y < snake.ps.get(1).y) {
                    snake.ps.get(0).y += 1;
                }
                else {
                    snake.ps.get(0).y -= 1;
                }
            }
            else if (snake.ps.get(0).y == snake.ps.get(1).y) {
                if (snake.ps.get(0).x < snake.ps.get(1).x) {
                    snake.ps.get(0).x += 1;
                }
                else {
                    snake.ps.get(0).x -= 1;
                }
            }
        }
        
        if (snake.direction == direction) {
            snake.ps.get(snake.ps.size()-1).x += snake.dx[direction];
            snake.ps.get(snake.ps.size()-1).y += snake.dy[direction];
        }
        else {
            snake.ps.add(new Point(snake.ps.get(snake.ps.size()-1).x + snake.dx[direction], snake.ps.get(snake.ps.size()-1).y + snake.dy[direction]));
            snake.direction = direction;
        }
        if (snake.ps.get(0).y == snake.ps.get(1).y && snake.ps.get(0).x == snake.ps.get(1).x) {
            snake.ps.remove(0);
        }
//        if (snake.getHead().x == food.getPosition().x && snake.getHead().y == food.getPosition().y) {
//            System.out.println("Huhu");
//        }
        return true;
    }
//    public boolean checkLose () {
//        Point head = snake.ps.get(snake.ps.size()-1);
//        if (head.x < 0 || head.x > boardSize || head.y < 0 || head.y > boardSize) {
//            isLose = true;
//            System.out.println("Igen");
//            return true;
//        }
//        for (int i = 0; i < rocks.size(); i++) {
//            if (head.x == rocks.get(i).getLocation().x && head.y == rocks.get(i).getLocation().y) {
//                isLose = true;
//                return true;
//            }
//        }
//        isLose = snake.collision();
//        return isLose;
//    }
    public boolean checkWin () {
        isWin = (score+2 == boardSize*boardSize-rocks.size());
        return isWin;
    }
    public boolean snakeAteFood () {
//        System.out.println("NO");
//        System.out.println(snake.getHead());
//        System.out.println(food.getPosition());
//        System.out.println(snake.getHead() == food.getPosition());
//        if (snake.getHead().x == food.getPosition().x && snake.getHead().y == food.getPosition().y) {
//            System.out.println("YES");
//        }
        return (snake.getHead().x == food.getPosition().x && snake.getHead().y == food.getPosition().y);
    }
    public void foodRespawning () {
        for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    board[i][j] = false;
                }
            }
        if (snakeAteFood()) {
            //System.out.println(">>>>");
            score += 1;
            for (int i = 0; i < snake.ps.size()-1; i++) {
                if (snake.ps.get(i).x == snake.ps.get(i+1).x) {
                    for (int j = Math.min(snake.ps.get(i).y, snake.ps.get(i+1).y); j <= Math.max(snake.ps.get(i).y, snake.ps.get(i+1).y); j++) {
                        board[snake.ps.get(i).x][j] = true;
                    }
                }
                else if (snake.ps.get(i).y == snake.ps.get(i+1).y) {
                    for (int j = Math.min(snake.ps.get(i).x, snake.ps.get(i+1).x); j <= Math.max(snake.ps.get(i).x, snake.ps.get(i+1).x); j++) {
                        board[j][snake.ps.get(i).y] = true;
                    }
                }
            }
            for (int i = 0; i < rocks.size(); i++) {
                board[rocks.get(i).getLocation().x][rocks.get(i).getLocation().y] = true;
            }
            List <Point> chosen = new ArrayList <>();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] != true) {
                        chosen.add(new Point(i, j));
                    }
                }
            }
            Random rand = new Random();
            int ind = rand.nextInt(chosen.size());
            food = new Food(chosen.get(ind));
        }
    }
    public void updateNewDirection (int d) {
        //System.out.println(d);
        direction = d;
    }
    public void running () {
        //System.out.println(running);
        if (!running) return;
        if (snake.collision()) {
            running = false;
        }
        if (!snakeMove1Unit() || checkWin()) {
            running = false;
        }
        foodRespawning();
    }
    public void startGame() {
        timer = new Timer(1000 / snake.velocity, e -> running()); // Timer callback
        timer.start();
    }
    public void stopGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        running = false;
    }
    public boolean isRunning() {
        return running;
    }
}
