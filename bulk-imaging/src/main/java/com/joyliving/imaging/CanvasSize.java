package com.joyliving.imaging;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanvasSize {

    private static Logger m_logger = LoggerFactory.getLogger(CanvasSize.class);

    // Default Constructor
    public CanvasSize() {
    }

    public void run() throws Exception {

        File path = new File("D:\\LivingLux\\images-lab"); // ... // base path
                                                           // of the images

        // load source images
        BufferedImage image     = ImageIO.read(new File(path, "test.png"));
        BufferedImage overlay   = ImageIO.read(new File(path, "layout.png"));
        BufferedImage logo      = ImageIO.read(new File(path, "logo.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        
        Image logoFinal = logo.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);
        g.drawImage(logoFinal, 200, 100, null);

        // Save as new image
        ImageIO.write(combined, "PNG", new File(path, "combined2.png"));

    }

    public static void main(String[] args) throws Exception {
        new CanvasSize().run();
    }
}
