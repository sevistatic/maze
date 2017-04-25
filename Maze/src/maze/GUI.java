package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class GUI implements ActionListener {
  
   private JButton northButton = new JButton("North");
   private JButton southButton = new JButton("South");
   private JButton eastButton = new JButton("East");
   private JButton westButton = new JButton("West");
   private JButton solveButton = new JButton("Solve");
   private JButton saveButton = new JButton("Save");
   private MazeCrawler maze;
   private final String SAVE_FILE_NAME = "maze.txt";

   public static void pause() {
      try {
         Thread.sleep(25);        // wait 1/40 s
      } catch (Exception e) {
         e.printStackTrace();
      }
   } // end method pause

   private static ArrayList<String> getTextMaze(String filename) {
      ArrayList<String> textMaze = new ArrayList<String>();
      File handle = new File(filename);
      try {
         Scanner mazeFile = new Scanner(handle);
         while (mazeFile.hasNext()) {
            textMaze.add(mazeFile.nextLine());
         }
      } catch (Exception e) {
         System.out.println("Error reading file");
         System.exit(1);
      }
      return textMaze;
   } // end getTextMaze

   public void go() {
      JFrame frame = new JFrame();
      frame.setAlwaysOnTop(true);
      frame.setTitle("CS241 Maze Crawl");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      maze = new MazeCrawler(15, 32);
      //maze = new MazeCrawler(getTextMaze(SAVE_FILE_NAME));

      int xSize = Math.max(MazeCell.size * (maze.getColumns() + 1), 400);
      frame.setSize(xSize, MazeCell.size * (maze.getRows() + 1) + 60);
      frame.setLocation(300, 50);
      frame.getContentPane().add(BorderLayout.CENTER, maze);
      JPanel buttonPanel = new JPanel();

      buttonPanel.add(westButton);
      westButton.addActionListener(this);
      buttonPanel.add(northButton);
      northButton.addActionListener(this);
      buttonPanel.add(southButton);
      southButton.addActionListener(this);
      buttonPanel.add(eastButton);
      eastButton.addActionListener(this);
      buttonPanel.add(solveButton);
      solveButton.addActionListener(this);
      buttonPanel.add(saveButton);
      saveButton.addActionListener(this);
      
      frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
      frame.setVisible(true);
   } // end method main

   public static void win() {
      JOptionPane.showMessageDialog(null, "Path Found!");
      System.exit(0);
   } // end method 

   public static void lose() {
      JOptionPane.showMessageDialog(null, "Nowhere to go from here!");
      System.exit(1);
   } // end method lose

   public void actionPerformed(ActionEvent event) {
      if (event.getSource() == northButton) {
         maze.moveNorth();
      }
      if (event.getSource() == southButton) {
         maze.moveSouth();
      }
      if (event.getSource() == eastButton) {
         maze.moveEast();
      }
      if (event.getSource() == westButton) {
         maze.moveWest();
      }
      if (event.getSource() == saveButton) {
         maze.saveToFile(SAVE_FILE_NAME);
      }
      if (event.getSource() == solveButton) {
         maze.solveMaze();
      }

      if (maze.isWin()) {
         win();
      }
      if (maze.isNoMove()) {
         lose();
      }   
   } // end actionPerformed
   
} // end class GUI

