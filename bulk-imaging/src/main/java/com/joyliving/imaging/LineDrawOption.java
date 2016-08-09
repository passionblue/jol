package com.joyliving.imaging;

import java.awt.Color;

public class LineDrawOption {

    
    private int startX;
    private int startY; 
    private int endX; 
    private int endY; 
    
    private Color color = new Color(210, 69, 8);;
    private float alpha = 1.0f;
    private float width = 10f;;
    
    public LineDrawOption(int startX, int startY, int endX, int endY, Color color, float alpha, float width) {
        super();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.alpha = alpha;
        this.width = width;
    }
    
    
    
    
    public LineDrawOption() {
    }

    public int applyStartX(int x ) {
        return apply(startX, x);
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int applyStartY(int y) {
        return apply(startY, y);
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public int applyEndX(int x) {
        return apply(startY, x);
    }    
    
    public void setEndX(int endX) {
        this.endX = endX;
    }

    
    
    public int getEndY() {
        return endY;
    }

    public int applyEndY(int y) {
        return apply(startY, y);
    }  
    public void setEndY(int endY) {
        this.endY = endY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    
    private int apply(int org, int second){
        if ( org >= 0 ) return org; 
        if( org == Integer.MAX_VALUE ) return second;
        if ( org < 0 ) return second - org;
        return org;
    }
    
    
}
