package com.joyliving.imaging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Themes {

    private static Logger m_logger = LoggerFactory.getLogger(Themes.class);

    public static Color color1 = new Color(210, 69, 8);
    public static Color color2 = new Color(7, 168, 7);
    public static Color color3 = new Color(210, 69, 8);
    public static Color color4 = new Color(210, 69, 8);
    
    
    public static void applyBasic( BufferedImage imageSource ) {

        Graphics2D g = imageSource.createGraphics();
        
        int width = imageSource.getWidth();
        int height = imageSource.getHeight();
        int lineWidth = 6;     
        int halfLineWidth = lineWidth/2;
        
        BasicDrawingRoutine.drawTransparentLine(g, 0, height-halfLineWidth, width , height-halfLineWidth, lineWidth, color2, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, 0, halfLineWidth, width , halfLineWidth, lineWidth, color2, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, halfLineWidth, 0, halfLineWidth , height, lineWidth, color2, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, width - halfLineWidth, 0, width - halfLineWidth , height, lineWidth, color2, 0.7f); 

        halfLineWidth = 0;
        BasicDrawingRoutine.drawTransparentLine(g, 0, height, width , height, lineWidth, Color.BLACK, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, 0, halfLineWidth, width , halfLineWidth, lineWidth, Color.BLACK, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, halfLineWidth, 0, halfLineWidth , height, lineWidth, Color.BLACK, 0.7f); 
        BasicDrawingRoutine.drawTransparentLine(g, width - halfLineWidth, 0, width - halfLineWidth , height, lineWidth, Color.BLACK, 0.7f); 
        
    }
    
}
