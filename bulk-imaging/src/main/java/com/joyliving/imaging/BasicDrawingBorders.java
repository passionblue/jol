package com.joyliving.imaging;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BasicDrawingBorders {

    
    
    public static void drawDoubleLines(Graphics g, int width, int height, float lineWidth, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(lineWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2.setStroke(stroke);
        g2.setPaint(Color.BLUE);

        /*
         * Sets the paint mode of this graphics context to overwrite the
         * destination with this graphics context's current color. This sets the
         * logical pixel operation function to the paint or overwrite mode. All
         * subsequent rendering operations will overwrite the destination with
         * the current color.
         */

        g2.setPaintMode();

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2.setPaint(c);


        
        g2.drawLine(0, 0, width-1, 0);
        g2.drawLine(0, 0, 0, height-1);
        g2.drawLine(0, height-1, width-1, height-1);
        g2.drawLine(width-1, 0, width-1, height-1);

        
        g2.drawLine(3, 3, width-4, 3);
        g2.drawLine(3, 3, 3, height-4);
        g2.drawLine(3, height-4, width-4, height-4);
        g2.drawLine(width-4, 3, width-4, height-4);

        
    }
    
    public static void drawSingleLine(Graphics g, int width, int height, float lineWidth, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(lineWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2.setStroke(stroke);
        g2.setPaint(Color.BLUE);

        /*
         * Sets the paint mode of this graphics context to overwrite the
         * destination with this graphics context's current color. This sets the
         * logical pixel operation function to the paint or overwrite mode. All
         * subsequent rendering operations will overwrite the destination with
         * the current color.
         */

        g2.setPaintMode();

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2.setPaint(c);


        
        g2.drawLine(0, 0, width-1, 0);
        g2.drawLine(0, 0, 0, height-1);
        g2.drawLine(0, height-1, width-1, height-1);
        g2.drawLine(width-1, 0, width-1, height-1);
        
    }
}
