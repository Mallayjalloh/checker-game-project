package com.mjcode.ui;

import javax.swing.*;
import java.awt.*;

public class PieceComponentDemo {

    public static void main(String[] args) {

        // Create a JFrame object
        JFrame frame = new JFrame("PieceComponent Demo");

        // Create instances of the PieceComponent class
        PieceComponent whitePawn = new PieceComponent(Color.WHITE,100, false);
        PieceComponent whiteKing = new PieceComponent(Color.WHITE, 100, true);
        PieceComponent blackPawn = new PieceComponent(Color.BLACK, 100, false);
        PieceComponent blackKing = new PieceComponent(Color.BLACK, 100, true);

        // Set layout manager to arrange components horizontally
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));

        // Add PieceComponent instances to the JFrame
        frame.add(whitePawn);
        frame.add(whiteKing);
        frame.add(blackPawn);
        frame.add(blackKing);

        // Set the background color of the content pane
        frame.getContentPane().setBackground(Color.GRAY);

        // Set the size of the JFrame and default closure
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make it visible
        frame.setVisible(true);

    }
}
