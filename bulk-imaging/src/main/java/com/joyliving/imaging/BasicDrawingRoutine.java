package com.joyliving.imaging;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joyliving.imaging.GenerateImages.HORIZONTAL_POSITION_HINT;
import com.joyliving.imaging.GenerateImages.VERTICAL_POSITION_HINT;

public class BasicDrawingRoutine {

    private static Logger m_logger = LoggerFactory.getLogger(BasicDrawingRoutine.class);

    public static void drawAnotherImage(Graphics g, BufferedImage imageSource, int x, int y, int w, int h, Color bg) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(imageSource, x, y, w, h, bg, null);

    }

    public static void drawTransparentLine_CrossLine(Graphics g, int targetW, int targetH, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(100, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2.setStroke(stroke);
        g2.setPaint(Color.BLUE);
        g2.setPaintMode();

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2.setPaint(c);
        g2.drawLine(0, 0, targetW, targetH);
    }

    public static void drawTransparentLine_Box(Graphics g, int targetW, int targetH, float width, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
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

        g2.drawLine(0, 0, targetW, 0);
        g2.drawLine(0, 0, 0, targetH);
        g2.drawLine(targetW, 0, targetW, targetH);
        g2.drawLine(0, targetH, targetW, targetW);
    }

    public static void drawTransparentLine_Bottom(Graphics g, int targetW, int targetH, float width, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
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

        g2.drawLine(0, targetH, targetW, targetH);
    }

    public static void drawTransparentLine(Graphics g, int startX, int startY, int endX, int endY, float width, Color color, float opacity) {

        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
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

        g2.drawLine(startX, startY, endX, endY);
    }
    
    
    

    public static void drawTransparentText_BottomCenter(Graphics g, int targetW, int targetH, String text, int fontSize, Color color, float opacity, int style) {

        Graphics2D g2d = (Graphics2D) g;

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2d.setPaint(c);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setFont(new Font("MONOSPACED", style, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int fw = fm.stringWidth(text);
        int fh = fm.getHeight();

        int pos[] = GenerateImages.calculatePosition(targetW, targetH, fw, fh, HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.BOTTOM, 0);

        g2d.drawString(text, pos[0], pos[1] + fh);
//        g2d.dispose();
    }

    public static void drawTransparentText_BottomLeft(Graphics g, int targetW, int targetH, String text, int fontSize, Color color, float opacity, int style) {

        Graphics2D g2d = (Graphics2D) g;

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2d.setPaint(c);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setFont(new Font("ARIAL", style, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int fw = fm.stringWidth(text);
        int fh = fm.getHeight();

        int pos[] = GenerateImages.calculatePosition(targetW, targetH, fw, fh, HORIZONTAL_POSITION_HINT.LEFT, VERTICAL_POSITION_HINT.BOTTOM, 0);

        g2d.drawString(text, pos[0], pos[1] + fh - 5);
//        g2d.dispose();
    }

    public static void drawTransparentText_TopCenter(Graphics g, int targetW, int targetH, String text, int fontSize, Color color, float opacity, int style) {

        Graphics2D g2d = (Graphics2D) g;

        float rgb[] = color.getRGBColorComponents(null);

        Color c = new Color(rgb[0], rgb[1], rgb[2], opacity); // Red

        g2d.setPaint(c);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Verdana", style, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int fw = fm.stringWidth(text);
        int fh = fm.getHeight();

        int pos[] = GenerateImages.calculatePosition(targetW, targetH, fw, fh, HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.TOP, 0);

        g2d.drawString(text, pos[0], pos[1] + fh );
//        g2d.dispose();
    }

    public static void drawTransparentImage(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // initializes necessary graphic properties
        Graphics2D g2d = (Graphics2D) sourceImage.createGraphics();
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaChannel);

        // calculates the coordinate where the image is painted
        int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
        int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight()) / 2;

        // paints the image watermark
        g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);
        g2d.dispose();
    }

    public static void drawTransparentImage_TopRight(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // calculates the coordinate where the image is painted
        int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth());
        int topLeftY = 0;

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }

    public static void drawTransparentImage_BottomLeft(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // calculates the coordinate where the image is painted
        int topLeftX = 0;
        int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight());

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }

    public static void drawTransparentImage_TopCenter(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // calculates the coordinate where the image is painted
        int topLeftX = (sourceImage.getWidth() / 2 - watermarkImage.getWidth() / 2);;
        int topLeftY = 0;

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }
    
    public static void drawTransparentImage_TopLeft(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // calculates the coordinate where the image is painted
        int topLeftX = 0;
        int topLeftY = 0;

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }

    public static void drawTransparentImage_BottomCenter(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha) {

        // calculates the coordinate where the image is painted
        int topLeftX = (sourceImage.getWidth() / 2 - watermarkImage.getWidth() / 2);;
        int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight());

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }


    public static void drawTransparentImage_BottomPaddingFromLeft(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha, int paddingFromLeft) {

        // calculates the coordinate where the image is painted
        int topLeftX = paddingFromLeft;
        int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight());

        drawTransparentImage(sourceImage, watermarkImage, alpha, topLeftX, topLeftY);

    }    
    
    
    public static void drawTransparentImage(BufferedImage sourceImage, BufferedImage watermarkImage, float alpha, int x, int y) {

        // initializes necessary graphic properties
        Graphics2D g2d = (Graphics2D) sourceImage.createGraphics();
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaChannel);

        // paints the image watermark
        g2d.drawImage(watermarkImage, x, y, null);
        g2d.dispose();
    }
    

    /*
     * public static void drawTransparentImage( Graphics g, int targetW, int
     * targetH, BufferedImage watermarkImage, float alpha ) {
     * 
     * Graphics2D g2d = (Graphics2D) g; AlphaComposite alphaChannel =
     * AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
     * g2d.setComposite(alphaChannel);
     * 
     * // calculates the coordinate where the image is painted int topLeftX =
     * (targetW - watermarkImage.getWidth()) / 2; int topLeftY = (targetH -
     * watermarkImage.getHeight()) / 2;
     * 
     * // paints the image watermark g2d.drawImage(watermarkImage, topLeftX,
     * topLeftY, null);
     * 
     * g2d.dispose(); }
     */

    public static void drawBackgroundColor(BufferedImage sourceImage, Color color) {

        // initializes necessary graphic properties
        Graphics2D graphics = sourceImage.createGraphics();

        graphics.setPaint(color);
        graphics.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());

        graphics.dispose();
    }

    public static BufferedImage convertToSquareImage(BufferedImage sourceImage, Color bg) {

        int targetSize = 0;

        if (sourceImage.getWidth() > sourceImage.getHeight()) {

            targetSize = sourceImage.getWidth();

        }
        else if (sourceImage.getWidth() < sourceImage.getHeight()) {

            targetSize = sourceImage.getHeight();
        }
        else { // samel
            targetSize = sourceImage.getHeight();
        }

        int remain = targetSize % 40;
        targetSize = targetSize + (40 - remain);

        int[] pos = GenerateImages.calculatePosition(targetSize, targetSize, sourceImage.getWidth(), sourceImage.getHeight(), HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, 0);
        BufferedImage squareImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);

        // Color bg = new Color(245, 245, 245); //TODO need to figure out
        // background Color Automatically
        Graphics2D g =  squareImage.createGraphics();
        g.setPaint(bg);
        g.fillRect(0, 0, squareImage.getWidth(), squareImage.getHeight());

        BasicDrawingRoutine.drawAnotherImage(g, sourceImage, pos[0], pos[1], sourceImage.getWidth(), sourceImage.getHeight(), bg);
        g.dispose();
        return squareImage;

    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static void embedLogoAndWatermarkSaveToFile(BufferedImage imageSource, String watermarkImage, String logoImage, File outputDir , String outputFileName) throws Exception {
        
        BufferedImage newImageSource = deepCopy(imageSource);
        
        if (watermarkImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(watermarkImage));
            BasicDrawingRoutine.drawTransparentImage_TopRight(newImageSource, imageWatermark, 1.0f);
        }

        if (logoImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(logoImage));
            BasicDrawingRoutine.drawTransparentImage_BottomLeft(newImageSource, imageWatermark, 1.0f);
        }

        
        
//        String baseName = FilenameUtils.getBaseName(sourceFileName);
//        String outputFileName = baseName + "-" + targetW + ".png";
        ImageIO.write(newImageSource, "PNG", new File(outputDir, outputFileName));        
    }
    
    
    
    

}
