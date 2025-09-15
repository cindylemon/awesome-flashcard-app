package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawings extends JPanel{
    Constants c = new Constants();

    // Draws a rectangle as the header on the gui
    public Drawings() {
        setOpaque(false);
    }

    
    // MODIFIES: this
    // EFFECTS: draws the graphic
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(c.sand());
        g.fillRect(0, 0, 850, 50);
        
    }
}
