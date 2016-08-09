package com.joyliving.imaging;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * resize without scaling of the images, which would just create empty canvas and put images in the middle. 
 * 
 */

public class GenerateMainImages {

    private static Logger m_logger = LoggerFactory.getLogger(GenerateMainImages.class);

    public enum HORIZONTAL_POSITION_HINT {
        LEFT, CENTER, RIGHT
    };

    public enum VERTICAL_POSITION_HINT {
        TOP, CENTER, BOTTOM
    };

    private String sourceDirectory;
    private String outputDirectory;
    private String mainDirectory;

    private boolean renameOutFiles = true; // if true, rename file with parent directory name

    private boolean variableHeight = true; // if true, it would create source

    // image + 100
    private int targetWidth = 900;
    private int targetHeight = 900;
    private int paddingSize = 30;
    
    /*
     * Watermark
     */

    private String logoImage;
    private String watermarkImage;
    private String watermarkText;
    private String mainTitleBannerImage;
    
    /*
     *  ribbon
     */
    
    private String ribbonImage;
    
    /*
     * Lines
     * 
     */
    
    private List<LineDrawOption> lineOptions;
    
    
    /*
     * bg. force the bg, in case bg is not consistent
     */
    
    private Color mandatoryBgColor;
    
    /*
     * Resizing Options
     */

    private boolean resizeBiggerSource;
    private int topPadding = 0;
    private int leftPadding = 0;
    
    private boolean stretchSmallSource = true;
    private int stretchCutoff  = 600;
    private int stretchPadding = 100;
    
    
    // Default Constructor
    public GenerateMainImages() {
    }

    public void convert() throws Exception {

        File source = new File(sourceDirectory); // ... // base path
        File output = new File(mainDirectory); // ... // base path
                                                 // of the images
        String ext[] = new String[] { "png", "jpg" , "PNG", "JPG", "gif", "GIF" };

        List<File> files = (List) FileUtils.listFiles(source, ext, false);

        if (!output.exists()) {
            output.mkdir();
        }

        
        for (File file : files) {
            
//            if (! ImageUtil.isMainImage(file.getName()))
//                continue;
            
            
            m_logger.info("-------------------------------------------------------------------------------------------------------");
            m_logger.info("Creating Logo file {} from {} to {}", file.getName(), source.getAbsolutePath(), output.getAbsolutePath());
            
            createLogoFiles(source, output, file.getName());
            
            
        }        
    
    }

    private void createLogoFiles(File sourceDir, File outputDir, String sourceFileName ) throws Exception {

        /*
         * Main logo image dimension
         */
        
        int targetW = 640;
        int targetH = 640;

        // load source images
        BufferedImage imageSource = ImageIO.read(new File(sourceDir, sourceFileName));
        
        
//        ImageRect rect = ImageUtil.getContentZone(imageSource);
        
        Color activeBackground = new Color(255, 255, 255);

        
        if ( mandatoryBgColor == null) {
            Color dominantColor = ImageUtil.getBackgroundColor(imageSource);
    
            if ( ImageUtil.isGray(dominantColor) ) {
                if ( dominantColor.getRed() > 200 ) // avoid dark   
                    activeBackground = dominantColor;
            }else {
                return;
            }
        } else {
            activeBackground = mandatoryBgColor;
        }
        
        imageSource = BasicDrawingRoutine.convertToSquareImage(imageSource, activeBackground);
        
        int sourceW = imageSource.getWidth();
        int sourceH = imageSource.getHeight();
        
/*        
        
        int[] leftTopPos = calculatePosition(targetW, targetH, sourceW, sourceH, HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, 0);
        BufferedImage imageCombined = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
        
        Color bg = new Color(245, 245, 245); //TODO need to figure out background Color Automatically
        Graphics g = imageCombined.getGraphics();
        BasicDrawingRoutine.drawAnotherImage(g, imageSource, leftTopPos[0], leftTopPos[1], sourceW, sourceH,  bg);

*/
        
        
        Image scaledImage = imageSource.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        imageSource = ImageUtil.toBufferedImage(scaledImage);

        
/*
 * 
 *       tryng to use back ground image but not working  
        BufferedImage backgroundImage = ImageIO.read(new File("D:\\LivingLux\\products\\banners\\background.png"));
        Graphics2D bg2g = backgroundImage.createGraphics();
        BasicDrawingRoutine.drawAnotherImage(bg2g, imageSource, 0, 0, 640, 640, null);
        bg2g.dispose();
        
        
        imageSource = backgroundImage;
*/        
        
        /*
        // Tried to pring same size of logo on all images, but logo is too big for some images. so reverted back to original which does print logos before scaling
//        String baseName = FilenameUtils.getBaseName(sourceFileName);
//        String outputFileName = baseName + "-" + targetW + ".png";        
//        BasicDrawingRoutine.embedLogoAndWatermarkSaveToFile(imageSource, watermarkImage, logoImage, outputDir, outputFileName);
        */
        
        // ========================
        if (watermarkImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(watermarkImage));
            BasicDrawingRoutine.drawTransparentImage_TopRight(imageSource, imageWatermark, 1.0f);
        }
        
        
        if (logoImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(logoImage));
            BasicDrawingRoutine.drawTransparentImage_BottomLeft(imageSource, imageWatermark, 1.0f);
        }
        
        
        /*
         * Save to file as <basename>-targetW.png
         */
        
        String baseName = FilenameUtils.getBaseName(sourceFileName);
        String outputFileName = baseName + "-" + targetW + ".png";
        ImageIO.write(imageSource, "PNG", new File(outputDir, outputFileName));
        
        BufferedImage imageSourceWithBanner = null;
        if ( mainTitleBannerImage != null ) {
            imageSourceWithBanner = BasicDrawingRoutine.deepCopy(imageSource);
            BufferedImage mainBannerImage = ImageIO.read(new File(mainTitleBannerImage));
            BasicDrawingRoutine.drawTransparentImage_BottomPaddingFromLeft(imageSourceWithBanner, mainBannerImage, 1.0f, 135);
        
            
            String bannerOutputFileName = baseName + "-" + targetW + "-banner.png";
            ImageIO.write(imageSourceWithBanner, "PNG", new File(outputDir, bannerOutputFileName));
        }
        


        if ( ribbonImage != null ) {
            
            String fullRibbonImagePath = ribbonImage == null?null:ribbonImage.replace("{size}", "640");
            
            BufferedImage imageSourceWithRibbon = BasicDrawingRoutine.deepCopy(imageSource);
            
            BufferedImage ribbon = null;
            
            
            try {
                Graphics gr = imageSourceWithRibbon.getGraphics();
                ribbon = ImageIO.read(new File(fullRibbonImagePath));
                BasicDrawingRoutine.drawAnotherImage(gr, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
                gr.dispose();
                
                String ribbon640OutputFileName = baseName + "-" + targetW + "-ribbon.png";
                ImageIO.write(imageSourceWithRibbon, "PNG", new File(outputDir, ribbon640OutputFileName));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage() ,e);
            }
            
        }
        
        /*
         * 640 image is ready 
         */
        
        
        List<Dimension> supportedImages = getSupportDimensions();
        
        for (int i = 0; i < 2; i++) {
            
            
            if ( i == 1 && imageSourceWithBanner == null)
                continue;
            
            if ( i == 1 ) 
                imageSource = imageSourceWithBanner;
            
        
        for (Dimension dim : supportedImages) {
            
            BufferedImage scaledImageFinal = null;
            Graphics g2 = null;
            
            
            String fullRibbonImagePath = ribbonImage == null?null:ribbonImage.replace("{size}", "" + (int) dim.getWidth());
            
            if ( dim.getWidth() == dim.getHeight()) {
                
                scaledImage = imageSource.getScaledInstance((int) dim.getWidth(), (int) dim.getHeight(), Image.SCALE_SMOOTH);
                scaledImageFinal = ImageUtil.toBufferedImage(scaledImage);
                g2 = scaledImageFinal.getGraphics();

                
//                /* ribon */
//                
//                if ( (dim.getWidth() == 220 || dim.getWidth() == 160 )  && fullRibbonImagePath != null ) {
//                
//                    BufferedImage ribbon = ImageIO.read(new File(fullRibbonImagePath));
//                    BasicDrawingRoutine.drawAnotherImage(g2, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
//                    
//                }
                
               
                
            } else if ( dim.getWidth() > dim.getHeight()) {
                
                int interimWidth = (int) (   ((double) targetW )* ((double) dim.getHeight() ) /((double) targetH ) );
                
                scaledImage = imageSource.getScaledInstance(interimWidth, (int) dim.getHeight(), Image.SCALE_SMOOTH);
                scaledImageFinal = ImageUtil.toBufferedImage(scaledImage);

                int[] pos = calculatePosition((int)dim.getWidth(), (int)dim.getHeight(), interimWidth, (int)dim.getHeight(), HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, 0);
                BufferedImage newCombined = new BufferedImage((int)dim.getWidth(), (int)dim.getHeight(), BufferedImage.TYPE_INT_ARGB);
                

                BasicDrawingRoutine.drawBackgroundColor(newCombined, activeBackground);  
                
                g2 = newCombined.getGraphics();
                BasicDrawingRoutine.drawAnotherImage(g2, scaledImageFinal, pos[0], pos[1], sourceW, sourceH,  activeBackground);
                scaledImageFinal = newCombined;
                
            } else {
                
                int interimHeight = (int) (   ((double) targetH )* ((double) dim.getWidth() ) /((double) targetW ) );
                
                scaledImage = imageSource.getScaledInstance( (int) dim.getWidth(), interimHeight, Image.SCALE_SMOOTH);
                scaledImageFinal = ImageUtil.toBufferedImage(scaledImage);

                int[] pos = calculatePosition((int)dim.getWidth(), (int)dim.getHeight(), (int)dim.getWidth(), interimHeight, HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, 0);
                BufferedImage newCombined = new BufferedImage((int)dim.getWidth(), (int)dim.getHeight(), BufferedImage.TYPE_INT_ARGB);
                
                
                Color bg2 = new Color(245, 245, 245); //TODO need to figure out background Color Automatically
                BasicDrawingRoutine.drawBackgroundColor(newCombined, activeBackground);                
                
                g2 = newCombined.getGraphics();
                BasicDrawingRoutine.drawAnotherImage(g2, scaledImageFinal, pos[0], pos[1], scaledImageFinal.getWidth(), scaledImageFinal.getHeight(),  activeBackground);
                

                
                scaledImageFinal = newCombined;
            }

            /* ribon */
            
/* selective making to save number images from disk space.             
            
            if ( (dim.getWidth() == 220 || dim.getWidth() == 160 )  && fullRibbonImagePath != null ) {
            
                BufferedImage ribbon = null;
                try {
                    ribbon = ImageIO.read(new File(fullRibbonImagePath));
                    BasicDrawingRoutine.drawAnotherImage(g2, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }
                
            } else if (  fullRibbonImagePath != null && (dim.getWidth() == 450 ) && i == 1 ) {
                
                BufferedImage ribbon = null;
                try {
                    ribbon = ImageIO.read(new File(fullRibbonImagePath));
                    BasicDrawingRoutine.drawAnotherImage(g2, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }
            } else if (  fullRibbonImagePath != null && ( dim.getWidth() == 120 ) && i == 0 ) {
                
                BufferedImage ribbon = null;
                try {
                    ribbon = ImageIO.read(new File(fullRibbonImagePath));
                    BasicDrawingRoutine.drawAnotherImage(g2, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }
            }
            
            
*/            

            if (i == 0 ) 
                outputFileName = baseName + "-" + ((int) dim.getWidth()) + ".png";
            else 
                outputFileName = baseName + "-" + ((int) dim.getWidth()) + "-banner.png";
            
            ImageIO.write(scaledImageFinal, "PNG", new File(outputDir, outputFileName));
            
            
            if (  fullRibbonImagePath != null ) {
                
                BufferedImage ribbon = null;
                try {
                    ribbon = ImageIO.read(new File(fullRibbonImagePath));
                    BasicDrawingRoutine.drawAnotherImage(g2, ribbon, 0, 0 , ribbon.getWidth(), ribbon.getHeight(),  null);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage() ,e);
                }

                if (i == 0 ) 
                    outputFileName = baseName + "-" + ((int) dim.getWidth()) + "-ribbon.png";
                else 
                    outputFileName = baseName + "-" + ((int) dim.getWidth()) + "-ribbon-banner.png";            
                
                ImageIO.write(scaledImageFinal, "PNG", new File(outputDir, outputFileName));
                
            } 
            
            
            g2.dispose();
            
            
            
        }
        
        }
        
        
    }

    
    private List<Dimension> getSupportDimensions() {
        
        List<Dimension> ret = new ArrayList();
        
        ret.add(new Dimension(220,300));
        ret.add(new Dimension(120,120));
        ret.add(new Dimension(160,160));
        ret.add(new Dimension(450,450));
        
        
        return ret;
    }
    
    
    
    private boolean processImageForContents(File sourceDir, File outputDir, String sourceFileName, HORIZONTAL_POSITION_HINT hHint, VERTICAL_POSITION_HINT vHint, String suffix, boolean stretchIfConditionMet) throws Exception {

        // load source images
        BufferedImage imageSource = ImageIO.read(new File(sourceDir, sourceFileName));
        
        int targetH = targetHeight;
        int targetW = targetWidth;
        int sourceW = imageSource.getWidth();
        int sourceH = imageSource.getHeight();

        /*
         * Filter out images that are obviously not for this processing
         */
        if (imageSource.getHeight() < 200) {
            return false;
        }

        boolean imageStretched = false;
        
        //only compare width. 
        if ( stretchIfConditionMet && sourceW > targetW ) {
            sourceH = (int) (   ((double) sourceH )* ((double) targetW ) /((double) sourceW ) );
            sourceW = targetW;
            Image simageSource = imageSource.getScaledInstance(sourceW, sourceH, Image.SCALE_SMOOTH);
            imageSource = ImageUtil.toBufferedImage(simageSource);
            
            m_logger.info("#### SCALED SMALLER " + sourceFileName );

            imageStretched = true;
        }
        
        /*
         * Stretch out smaller image to fit
         */
        if ( stretchIfConditionMet && imageSource.getWidth() < stretchCutoff ) {

            int originalSourceW = sourceW;
            sourceW = targetW - stretchPadding;

            sourceH = (int) (   ((double) sourceH )* ((double) sourceW ) /((double) originalSourceW ) );
            Image simageSource = imageSource.getScaledInstance(sourceW, sourceH, Image.SCALE_SMOOTH);
            imageSource = ImageUtil.toBufferedImage(simageSource);
            
            m_logger.info("#### STRETCHED " + sourceFileName );
            
            imageStretched = true;
        }
        
        if (variableHeight) {
            targetH = sourceH + paddingSize * 2;
        }
        
        int[] leftTopPos = calculatePosition(targetW, targetH, sourceW, sourceH, hHint, vHint, topPadding);

        /*
         * Create new empty Buffered Image based dimension
         */
        BufferedImage imageCombined = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);

        /*
         * 
         * 
         */
        
        Color bg = new Color(255, 255, 255);
        BasicDrawingRoutine.drawBackgroundColor(imageCombined, bg);
        
        /*
         * 
         */
        
        
        Graphics g = imageCombined.getGraphics();
        BasicDrawingRoutine.drawAnotherImage(g, imageSource, leftTopPos[0], leftTopPos[1], sourceW, sourceH,  bg);


        /*
         * Transparent Line
         */
        // BasicDrawingRoutine.drawTransparentLine_Box(g, targetW, targetH,
        // 50.0f, mianColor, 0.9f);
//        Color lineColor = new Color(210, 69, 8);
//        BasicDrawingRoutine.drawTransparentLine_Bottom(g, targetW, targetH, 30.0f, lineColor, 0.7f);

        if (lineOptions != null) {
            
            for (LineDrawOption opt : lineOptions) {

                BasicDrawingRoutine.drawTransparentLine(g, opt.applyStartX(0), opt.applyStartY(targetH), opt.applyEndX(targetW), opt.applyEndY(targetH), opt.getWidth() , opt.getColor(), opt.getAlpha());
                
            }
            
        }
        
        
        /*
         * Draw the transparent text style could be Font.ITALIC | Font.BOLD
         */
        if (watermarkText != null) {
            Color textColor = Color.BLACK;
            BasicDrawingRoutine.drawTransparentText_BottomLeft(g, targetW, targetH, watermarkText, 20, textColor, 0.9f, Font.BOLD);
        }

        // BasicDrawingRoutine.drawTransparentImage(g, targetW, targetH,
        // imageWatermark, 0.5f); //How not working

        if (watermarkImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(watermarkImage));
            BasicDrawingRoutine.drawTransparentImage_TopRight(imageCombined, imageWatermark, 0.5f);
        }

        
        /*
         * save to file
         * 
         */
        
        String outputFileName = sourceFileName;

        if (renameOutFiles) {

            String parentName = outputDir.getParent();
            parentName = FilenameUtils.getBaseName(parentName);
            parentName = parentName.replaceAll(" ", "_");

            // FileNameUtils.

            String ext = FilenameUtils.getExtension(sourceFileName);
            outputFileName = parentName + "_" + suffix + "." + ext;
        }
        
        ImageIO.write(imageCombined, "PNG", new File(outputDir, outputFileName +".PNG"));
        
        return imageStretched;
    }

    private void drawLine(BufferedImage mazeImage) {

        for (int i = 400; i < 510; i++) {
            for (int vl = 0; vl < 500; vl++) {
                mazeImage.setRGB(i, vl, Color.WHITE.getRGB());
            }
        }

        // draw line horizontal
        for (int vh = 0; vh < 600; vh++) {
            mazeImage.setRGB(vh, 157, Color.WHITE.getRGB());
        }

    }

    public void drawTransparentLineTest(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(100, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2.setStroke(stroke);
        g2.setPaint(Color.BLUE);
        g2.setPaintMode();

        float alpha = 0.3f; // higher the darker
        Color color = new Color(1, 0, 0, alpha); // Red
        g2.setPaint(color);
        g2.drawLine(0, 0, targetWidth, targetHeight);
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
    
    public String getMainDirectory() {
        return mainDirectory;
    }

    public void setMainDirectory(String mainDirectory) {
        this.mainDirectory = mainDirectory;
    }

    public String getWatermarkImage() {
        return watermarkImage;
    }

    public void setWatermarkImage(String watermarkImage) {
        this.watermarkImage = watermarkImage;
    }

    
    
    public Color getMandatoryBgColor() {
        return mandatoryBgColor;
    }

    public void setMandatoryBgColor(Color mandatoryBgColor) {
        this.mandatoryBgColor = mandatoryBgColor;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
    
    public String getRibbonImage() {
        return ribbonImage;
    }

    public void setRibbonImage(String ribbonImage) {
        this.ribbonImage = ribbonImage;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public boolean isRenameOutFiles() {
        return renameOutFiles;
    }

    public void setRenameOutFiles(boolean renameOutFiles) {
        this.renameOutFiles = renameOutFiles;
    }

    public boolean isVariableHeight() {
        return variableHeight;
    }

    public void setVariableHeight(boolean variableHeight) {
        this.variableHeight = variableHeight;
    }

    public boolean isResizeBiggerSource() {
        return resizeBiggerSource;
    }

    public void setResizeBiggerSource(boolean resizeBiggerSource) {
        this.resizeBiggerSource = resizeBiggerSource;
    }
    
    

    public String getMainTitleBannerImage() {
        return mainTitleBannerImage;
    }

    public void setMainTitleBannerImage(String mainTitleBannerImage) {
        this.mainTitleBannerImage = mainTitleBannerImage;
    }

    public boolean isStretchSmallSource() {
        return stretchSmallSource;
    }

    public void setStretchSmallSource(boolean stretchSmallSource) {
        this.stretchSmallSource = stretchSmallSource;
    }

    public int getStretchCutoff() {
        return stretchCutoff;
    }

    public void setStretchCutoff(int stretchCutoff) {
        this.stretchCutoff = stretchCutoff;
    }

    public int getStretchPadding() {
        return stretchPadding;
    }

    public void setStretchPadding(int stretchPadding) {
        this.stretchPadding = stretchPadding;
    }
    
    

    public List<LineDrawOption> getLineOptions() {
        return lineOptions;
    }

    public void setLineOptions(List<LineDrawOption> lineOptions) {
        this.lineOptions = lineOptions;
    }

    public static int[] calculatePosition(int targetW, int targetH, int imageW, int imageH, HORIZONTAL_POSITION_HINT hHint, VERTICAL_POSITION_HINT vHint, int padding) {

        int startW = 0;
        int startH = 0;

        if (hHint == HORIZONTAL_POSITION_HINT.RIGHT) {

            if (imageW > targetW) {
                startW = targetW - imageW;
            }
            else {
                startW = targetW - imageW;
            }

        }
        else if (hHint == HORIZONTAL_POSITION_HINT.CENTER) {
            if (imageW > targetW) {
                startW = targetW / 2 - imageW / 2;
            }
            else {
                startW = targetW / 2 - imageW / 2;
            }
        }
        else {
            startW = 0;
        }

        if (vHint == VERTICAL_POSITION_HINT.BOTTOM) {
            if (imageH > targetH) {
                startH = targetH - imageH;
            }
            else {
                startH = targetH - imageH;
            }

        }
        else if (vHint == VERTICAL_POSITION_HINT.CENTER) {
            if (imageH > targetH) {
                startH = targetH / 2 - imageH / 2;
            }
            else {
                startH = targetH / 2 - imageH / 2;
            }
        }
        else {
            startH = padding;
        }

        int ret[] = new int[] { startW, startH };

        return ret;
    }

    public static void main(String[] args) throws Exception {
        process_Nike_Testing();
//        process_Nike_FlyKnitRacer();
//        process_Nike_BigSwoosh();
//        process_SAS();
    }


    public static void process_Nike_Testing() throws Exception {

        GenerateMainImages resize = new GenerateMainImages();

        // Nike FlyKnit
        resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\BigSwoosh");
        resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\BigSwoosh\\main");
        resize.setMainDirectory("D:\\LivingLux\\products\\Nike\\BigSwoosh\\main");
        resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");
        resize.setLogoImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        
        // resize.setSourceDirectory("D:\\LivingLux\\images-lab");
        // resize.setOutputDirectory("D:\\LivingLux\\images-lab\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\products\\SAS\\bounce\\b1.jpg");
        resize.convert();
    }
    

}
