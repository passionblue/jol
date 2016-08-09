package com.joyliving.imaging;

import java.awt.BasicStroke;
import java.awt.Color;
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

public class GenerateImages2 {

    private static Logger m_logger = LoggerFactory.getLogger(GenerateImages2.class);

    public enum HORIZONTAL_POSITION_HINT {
        LEFT, CENTER, RIGHT
    };

    public enum VERTICAL_POSITION_HINT {
        TOP, CENTER, BOTTOM
    };

    public enum POSITION_HINT {
        TOP_LEFT, 
        TOP_CENTER, 
        TOP_RIGHT, 
        CENTER_LEFT, 
        CENTER_CENTER, 
        CENTER_RIGHT, 
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT
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

    private String watermarkImage;
    private String watermarkText; // Bottom Left

    private String topCenterWatermarkImage;
    private String topCenterWatermarkText;

    private Color  bgColor;
    
    /*
     * Lines
     * 
     */
    
    private List<LineDrawOption> lineOptions;
    
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
    public GenerateImages2() {
    }

    public void convert() throws Exception {

        File source = new File(sourceDirectory); // ... // base path
        File output = new File(outputDirectory); // ... // base path
                                                 // of the images
        String ext[] = new String[] { "png", "jpg" , "PNG", "JPG", "gif", "GIF" };

        List<File> files = (List) FileUtils.listFiles(source, ext, false);

        if (!output.exists()) {
            output.mkdir();
        }

        
        for (File file : files) {
            
            if (! ImageUtil.isMainImage(file.getName()))
                continue;
            m_logger.info("-------------------------------------------------------------------------------------------------------");
            m_logger.info("Creating Logo file {} from {} to {}", file.getName(), source.getAbsolutePath(), output.getAbsolutePath());
            
            createLogoFiles(source, output, file.getName());
            
            
        }        
        
        
        
        int index = 1001;
        for (File file : files) {
            m_logger.info("Resizing {} from {} to {}", file.getName(), source.getAbsolutePath(), output.getAbsolutePath());
            
            
            String fileSuffix = ""+index;
            boolean stretched = processImageForContents(source, output, file.getName(), HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, fileSuffix , true);
            
            if ( stretched ) 
                processImageForContents(source, output, file.getName(), HORIZONTAL_POSITION_HINT.CENTER, VERTICAL_POSITION_HINT.CENTER, fileSuffix + "-2", false);
            index++;
        }


    
    }

    private void createLogoFiles(File sourceDir, File outputDir, String sourceFileName ) throws Exception {
    

        // load source images
        BufferedImage imageSource = ImageIO.read(new File(sourceDir, sourceFileName));
        

        
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
        BufferedImage imageBase = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);

        /*
         * 
         * 
         */
        
        if ( bgColor != null) {
            Color bg = new Color(255, 255, 255);
            BasicDrawingRoutine.drawBackgroundColor(imageBase, bgColor);
        } else {
            Color dominantColor = ImageUtil.getBackgroundColor(imageSource);
            
            bgColor = new Color(255, 255, 255);
            if ( ImageUtil.isGray(dominantColor) ) {
                if ( dominantColor.getRed() > 200 ) // avoid dark   
                    bgColor = dominantColor;
            }
            
            BasicDrawingRoutine.drawBackgroundColor(imageBase, bgColor);            
        }
        /*
         * 
         */
        
        
        Graphics g = imageBase.getGraphics();
        BasicDrawingRoutine.drawAnotherImage(g, imageSource, leftTopPos[0], leftTopPos[1], sourceW, sourceH,  bgColor);


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
            BasicDrawingRoutine.drawTransparentText_BottomLeft(g, targetW, targetH, watermarkText, 20, textColor, 0.5f, Font.BOLD);
        }

        if (topCenterWatermarkText != null) {
            Color textColor = Color.BLACK;
            BasicDrawingRoutine.drawTransparentText_TopCenter(g, targetW, targetH, topCenterWatermarkText, 40, textColor, 0.5f, Font.BOLD);
        }        
        
        // BasicDrawingRoutine.drawTransparentImage(g, targetW, targetH,
        // imageWatermark, 0.5f); //How not working
        
        if (watermarkImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(watermarkImage));
            BasicDrawingRoutine.drawTransparentImage_TopRight(imageBase, imageWatermark, 0.5f);
        }
        if (topCenterWatermarkImage != null) {
            BufferedImage imageWatermark = ImageIO.read(new File(topCenterWatermarkImage));
            BasicDrawingRoutine.drawTransparentImage_TopCenter(imageBase, imageWatermark, 0.5f);
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
        
        ImageIO.write(imageBase, "PNG", new File(outputDir, outputFileName +".PNG"));
        
        
        
        /*
         * with Apache Imaging
         * 
         * 
         */
/*
        final ImageFormat format = ImageFormats.JPEG;
        final Map<String, Object> params = new HashMap<>();

        // set optional parameters if you like
//        params.put(ImagingConstants.PARAM_KEY_COMPRESSION, new Integer( JpegConstants.

        final byte[] bytes = Imaging.writeImageToBytes(imageCombined, format, null);
        
        FileUtils.writeByteArrayToFile(new File(outputFileName +"-2.JPG"), bytes);
*/        
        
        /*
//        AreaAveragingScaleFilter scaleFilter =
//                new AreaAveragingScaleFilter(
//                        Math.round(originalWidth / factorX),
//                        Math.round(originalHeight / factorY));
//            ImageProducer producer = new FilteredImageSource(original.getSource(), scaleFilter);
//            ImageGenerator generator = new ImageGenerator();
//            producer.startProduction(generator);
//            BufferedImage scaled = generator.getImage();

            for(int x = 0; x < imageCombined.getWidth(); x++) {
                for(int y = 0; y < imageCombined.getHeight(); y++) {
                    int rgb = imageCombined.getRGB(x, y);
                    int alpha = (rgb >> 24) & 0xff;
                    if(alpha != 255) {
                        imageCombined.setRGB(x, y,-1); //set white
                    }
                }
            }


            JPEGImageWriteParam param = new JPEGImageWriteParam(null);
            param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality((float) 0.85);
            java.util.Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("jpg");
            ImageWriter writer = it.next();
            
            writer.setOutput(new FileImageOutputStream(new File(outputFileName +"-2.JPG")));
            writer.write(null, new IIOImage(imageCombined, null, null), param);
            writer.dispose();        
        
        
        
        
        
        */
        
        
        
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

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public String getTopCenterWatermarkText() {
        return topCenterWatermarkText;
    }

    public void setTopCenterWatermarkText(String topCenterWatermarkText) {
        this.topCenterWatermarkText = topCenterWatermarkText;
    }
    
    public String getTopCenterWatermarkImage() {
        return topCenterWatermarkImage;
    }

    public void setTopCenterWatermarkImage(String topCenterWatermarkImage) {
        this.topCenterWatermarkImage = topCenterWatermarkImage;
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
    
    

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
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

        GenerateImages2 resize = new GenerateImages2();

        // resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black");
        // resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");

        // Nike FlyKnit
        resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\Testing");
        resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\Testing\\output");
//        resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");
        
        
        resize.setRenameOutFiles(true);

        
        List<LineDrawOption> opts = new ArrayList();
        
        opts.add(new LineDrawOption(0, -1, resize.targetWidth, -1, new Color(210, 69, 8), 0.5f, 20f));
        
        resize.setLineOptions(opts);
        
        
        // resize.setSourceDirectory("D:\\LivingLux\\images-lab");
        // resize.setOutputDirectory("D:\\LivingLux\\images-lab\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\products\\SAS\\bounce\\b1.jpg");
        resize.convert();
    }
    
    
    public static void process_Nike_FlyKnitRacer() throws Exception {

        GenerateImages2 resize = new GenerateImages2();

        // resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black");
        // resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");

        // Nike FlyKnit
        resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\FlyKnitRacer");
        resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\FlyKnitRacer\\output");
//        resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");
        
        
        resize.setRenameOutFiles(true);

        // resize.setSourceDirectory("D:\\LivingLux\\images-lab");
        // resize.setOutputDirectory("D:\\LivingLux\\images-lab\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\products\\SAS\\bounce\\b1.jpg");
        resize.convert();
    }
    
    public static void process_Nike_BigSwoosh() throws Exception {

        GenerateImages2 resize = new GenerateImages2();

        // resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black");
        // resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\AirMax95_Triple_Black\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");

        // Nike FlyKnit
        resize.setSourceDirectory("D:\\LivingLux\\products\\Nike\\BigSwoosh");
        resize.setOutputDirectory("D:\\LivingLux\\products\\Nike\\BigSwoosh\\output");
//        resize.setWatermarkImage("D:\\LivingLux\\logos\\nike_water.png");
        resize.setRenameOutFiles(true);

        // resize.setSourceDirectory("D:\\LivingLux\\images-lab");
        // resize.setOutputDirectory("D:\\LivingLux\\images-lab\\output");
        // resize.setWatermarkImage("D:\\LivingLux\\products\\SAS\\bounce\\b1.jpg");
        resize.convert();
    }    
    

    public static void process_SAS() throws Exception {

        GenerateImages2 resize = new GenerateImages2();

        // Nike FlyKnit
        resize.setSourceDirectory("D:\\LivingLux\\products\\SAS\\bounce");
        resize.setOutputDirectory("D:\\LivingLux\\products\\SAS\\bounce\\output");
        resize.setWatermarkImage("D:\\LivingLux\\products\\SAS\\water.png");
        resize.setRenameOutFiles(true);
        resize.setVariableHeight(true);
        resize.setResizeBiggerSource(true);
        resize.convert();
    }

}
