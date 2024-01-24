package com.mjcode.ui;

import javax.swing.*;
import java.awt.*;

/**
 * PieceComponent.java
 * A component that will draw a circular checkers piece of the provided color
 * and diameter. If "kinged", the piece receives a 3-pixel white border.
 */
public class PieceComponent extends JComponent {

    // Declare fields
    protected Color color;
    protected int diameter;
    protected boolean kinged;

    /**
     * Constructor that accepts 3 arguments
     * @param color The color of the piece
     * @param diameter The size of the piece
     * @param kinged If pawn is a king
     */
    public PieceComponent(Color color, int diameter, boolean kinged) {
        this.color = color;
        this.diameter = diameter;
        this.kinged = kinged;
    }

    /**
     * The getPreferredSize gets the size of the checker piece
     * @return The diameter size
     */
    public Dimension getPreferredSize() {
        return new Dimension(this.diameter, this.diameter);
    }

    /**
     * The paintComponent provides the shape, color, and diameter of the checker pieces
     * @param g The Graphics object
     */
    public void paintComponent(Graphics g) {
        // Set the color for the piece
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.WHITE);

        // Check when piece is a king
        if (this.kinged) {
            graphics.fillOval(0,0,this.diameter, this.diameter);
        }

        int offset = 3;
        int innerDiameter = this.diameter - (2 * offset);

        graphics.setColor(this.color);
        graphics.fillOval(offset, offset, innerDiameter, innerDiameter);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(offset, offset, innerDiameter - 1, innerDiameter - 1);
    }

}
