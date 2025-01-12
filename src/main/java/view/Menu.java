/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import data.DatabaseManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import model.GameLogic;


/**
 *
 * @author hatuy
 * This is the Snake menu game
 */
public class Menu extends JFrame {
    private JFrame gameFrame;
    private JButton startButton;
    private JButton highScoreButton;
    private JButton exitButton;
    private DatabaseManager dbManager;
    private GameLogic logic;
    private Board board;
    public Menu() {
        dbManager = new DatabaseManager();
        dbManager.initializeDatabase(dbManager.connect());
        
        setTitle ("Snake: Menu");
        setSize (400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        
        startButton = new JButton("Start");
        highScoreButton = new JButton("High score");
        exitButton = new JButton("Exit");
        
        add(startButton);
        add(highScoreButton);
        add(exitButton);

        startButton.setFocusable(false);
        highScoreButton.setFocusable(false);
        exitButton.setFocusable(false);
        
        startButton.addActionListener(e -> startGame(17));
        highScoreButton.addActionListener(e -> showHighScores());
        exitButton.addActionListener(e -> System.exit(0));
        
        setVisible(true);
    }
    private void startGame(int boardSize) {
        SwingUtilities.invokeLater(() -> {
            gameFrame = new JFrame("Snake");
            
            logic = new GameLogic (boardSize);
            board = new Board(logic);
            
            gameFrame.add(board);
            gameFrame.pack();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gameFrame.setResizable(false);
            
            logic.startGame();
            
            
            Timer renderTimer = new Timer(16, (ActionEvent e) -> { // ~60 FPS rendering
                board.repaint();
                //System.out.println(logic.isRunning());
                // Check game state
                if (!logic.isRunning()) {
                    ((Timer) e.getSource()).stop();
                    gameFrame.dispose();
                    handleGameEnd(logic);
                } else if (logic.checkWin()) {
                    ((Timer) e.getSource()).stop();
                    gameFrame.dispose();
                    handleGameEnd(logic);
                }
            });
            
            renderTimer.start();
            dispose();
        });
    }
    private void handleGameEnd (GameLogic logic) {
        if (logic.isLose) {
            JOptionPane.showMessageDialog(gameFrame, "Game over! Your score: " + logic.score,
            "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            
            String name = JOptionPane.showInputDialog(gameFrame, 
            "Enter your name:", 
            "Game Over", 
            JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                dbManager.saveScore(name.trim(), logic.score);
                JOptionPane.showMessageDialog(gameFrame, 
                    "Score saved!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (logic.isWin) {
            JOptionPane.showMessageDialog(gameFrame, "Congratulations! You won the game! Your score: " + logic.score,
            "Game Win!", JOptionPane.INFORMATION_MESSAGE);
            
            String name = JOptionPane.showInputDialog(gameFrame, 
            "Enter your name:", 
            "Game Win", 
            JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                dbManager.saveScore(name.trim(), logic.score);
                JOptionPane.showMessageDialog(gameFrame, 
                    "Score saved!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
        gameFrame.dispose();
        setVisible(true);
    }
    private void showHighScores() {
        String topScores = dbManager.getTopScores();
        JOptionPane.showMessageDialog(gameFrame, 
            topScores, 
            "Top 10 high scores", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
