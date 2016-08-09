package com.joyliving.imaging;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public class ImageUtil {
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static boolean isMainImage(String filename) {

        String baseName = FilenameUtils.getBaseName(filename);

        if (baseName.startsWith("main-") || baseName.endsWith("-main"))
            return true;

        return false;

    }

    public static ImageRect getContentZone(BufferedImage image) {
        
        
        int width = image.getWidth();
        int height = image.getHeight();
        int backRgb = Integer.MIN_VALUE;
        
        ImageRect rect = new ImageRect();
        
        for (int d = 1; d <= 4; d++) {
            
            int outerLoop = 0;
            int innerLoop = 0;
            switch(d) {
            case 1: case 2: outerLoop = width; innerLoop = height; break;
            case 3: case 4: innerLoop = width; outerLoop  = height;break; 
            }
            
            
            int limit = Integer.MAX_VALUE;
            for (int i = 0; i < outerLoop; i++) {
                for (int j = 0; j < innerLoop; j++) {
                    
                    int x = 0;
                    int y = 0;
                    int idx = 0;
                    
                    switch(d) {
                    case 1: x = i; y = j; idx = j;break; 
                    case 2: x = i; y = height-j - 1; idx = height-j - 1;break;
                    case 3: x = j; y = i; idx = j; break;
                    case 4: x = width - j - 1; y = i; idx = width-j - 1; break;
                    }
                    
                    int rgb = image.getRGB(x, y);
                    if ( backRgb == Integer.MIN_VALUE ) { 
                        backRgb = rgb;
                        continue;
                    }
                    
                    if ( backRgb != rgb ) {
                        if ( limit > idx ) 
                            limit = idx;
                        break;
                    }
                }
            }
            
            switch(d) {
            case 1: rect.setYtop(limit); break; 
            case 2: rect.setYbottom(height - limit);break;
            case 3: rect.setXleft(limit); break; 
            case 4: rect.setXright(width - limit);break;
            }
            
            
            
        }        

        return rect;
    }
    
    public static Color getBackgroundColor(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                // Filter out grays....
                Integer counter = (Integer) m.get(rgb);
                if (counter == null)
                    counter = 0;

                counter++;
                m.put(rgb, counter);
            }
        }

//        String colourHex = getMostCommonColour(m);
//        System.out.println(colourHex);
        
        return new Color(getMostCommonColourToRgb(m));
    }
    
    
    
    public static void setBackgroundTransparentColor(BufferedImage image, int bg) {
        int height = image.getHeight();
        int width = image.getWidth();

        int trRGB = (new Color(0.0f, 0.0f, 0.0f, 0.0f)).getRGB();        
        
        Map m = new HashMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);

                if (rgb == bg) 
                    image.setRGB(i, j, trRGB);
                
            }
        }

    }
    
    public static String getMostCommonColour(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
              public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
              }
        });    
        Map.Entry me = (Map.Entry )list.get(list.size()-1);
        int[] rgb= getRGBArr((Integer)me.getKey());
        return Integer.toHexString(rgb[0])+" "+Integer.toHexString(rgb[1])+" "+Integer.toHexString(rgb[2]);        
    }      
    
    public static int getMostCommonColourToRgb(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
              public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
              }
        });    
        Map.Entry me = (Map.Entry )list.get(list.size()-1);
        return (Integer)me.getKey();
    } 

    public static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[] { red, green, blue };

    }
    
    
    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) 
            if (rbDiff > tolerance || rbDiff < -tolerance) { 
                return false;
            }                 
        return true;
    }    
    
    public static boolean isGray(Color color) {
        
        return isGray(convertToRgbArr(color));
    }  

    
    public static int [] convertToRgbArr(Color color) {

        int [] arr = new int[3];
        
        arr[0] = color.getRed();
        arr[1] = color.getGreen();
        arr[2] = color.getBlue();

        return arr;
        
    }
}
