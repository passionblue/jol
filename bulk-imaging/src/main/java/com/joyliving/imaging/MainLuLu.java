package com.joyliving.imaging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLuLu {

    private static Logger m_logger = LoggerFactory.getLogger(MainLuLu.class);

    // Default Constructor
    
    GenerateMainImages genMain;
    GenerateLuLuImages resize;
    
    public MainLuLu() {
        
        genMain = new GenerateMainImages();
        resize = new GenerateLuLuImages();
    }

    public MainLuLu setMainImageOptions( String sourceImageDirectory, String outputPath, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString , String ribbonImage ) {

        genMain.setSourceDirectory(sourceImageDirectory);
        
        
        String fullParentPath =  outputPath == null?sourceImageDirectory : outputPath ;
        
        genMain.setOutputDirectory(fullParentPath + "\\output");
        genMain.setMainDirectory(fullParentPath + "\\main");
        genMain.setWatermarkImage(watermarkImageFilePath);
        genMain.setLogoImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        genMain.setMainTitleBannerImage(bannerImageFilePath);
        genMain.setRibbonImage(ribbonImage);
        genMain.setMandatoryBgColor(new Color(255, 255, 255));
        
        return this;
    }
    
    public MainLuLu setOutputImageOptions( String sourceImageDirectory, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString ) {
        
        String sourcePath = sourceImageDirectory == null ? genMain.getSourceDirectory(): sourceImageDirectory;
        
        resize.setSourceDirectory(sourcePath);
        resize.setOutputDirectory(genMain.getOutputDirectory());
        resize.setBottomLeftWatermarkImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        resize.setRenameOutFiles(true);
//        resize.setTopCenterWatermarkText(waterMarkString);
        resize.setTopCenterWatermarkImage(bannerImageFilePath);
        
        List<LineDrawOption> opts = new ArrayList();
        
        opts.add(new LineDrawOption(0, -1, resize.getTargetWidth(), -1, new Color(210, 69, 8), 0.7f, 20f));
        
        resize.setLineOptions(opts);        
        
        resize.setTopCenterWatermarkText(waterMarkString);
        
        
        return this;
    }

    
    public void run(String sourceImageDirectory) throws Exception {
        run(sourceImageDirectory, null, null, null, null);
    }    
    
    public void run(String sourceImageDirectory, String watermarkImageFilePath) throws Exception {
        run(sourceImageDirectory, watermarkImageFilePath, null, null, null);
    }    

    public void run(String sourceImageDirectory, String watermarkImageFilePath, String bannerImageFilePath) throws Exception {
        run(sourceImageDirectory, watermarkImageFilePath, bannerImageFilePath, null, null);
    }    
    
    public void run(String sourceImageDirectory, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString, String mainImageBannerFile) throws Exception {
        
        /*
         * MAIN IMAGES
         */
        
        
        GenerateMainImages genMain = new GenerateMainImages();
        
        genMain.setSourceDirectory(sourceImageDirectory);
        genMain.setOutputDirectory(sourceImageDirectory + "\\main");
        genMain.setMainDirectory(sourceImageDirectory + "\\main");
        genMain.setWatermarkImage(watermarkImageFilePath);
        genMain.setLogoImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        genMain.setMainTitleBannerImage(mainImageBannerFile);
        genMain.setMainTitleBannerImage(bannerImageFilePath);
//        genMain.setWatermarkText("뉴발 990"); not working well with Korean text
        genMain.convert();
        
        /*
         * PRODUCT IMAGES
         * 
         */
        
        GenerateImages resize = new GenerateImages();

        resize.setSourceDirectory(sourceImageDirectory);
        resize.setOutputDirectory(sourceImageDirectory + "\\output");
        resize.setBottomLeftWatermarkImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        resize.setRenameOutFiles(true);
//        resize.setTopCenterWatermarkText(waterMarkString);
        resize.setTopCenterWatermarkImage(bannerImageFilePath);
        
        List<LineDrawOption> opts = new ArrayList();
        
        opts.add(new LineDrawOption(0, -1, resize.getTargetWidth(), -1, new Color(210, 69, 8), 0.7f, 20f));
        
        resize.setLineOptions(opts);        
        
        resize.convert();
    }
    
    
    public void run() throws Exception {
        genMain.convert();
        resize.convert();
    }
    
    public static void main(String[] args) throws Exception {
        

//      new MainLuLu().setMainImageOptions("D:\\LivingLux\\products\\LuLu\\WunderUnderCropFOL", null, "D:\\LivingLux\\logos\\lulu-logo.png", null, null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "LLULULEMON Wunder Under Crop (Hi-Rise) Full-On LUON").run();     

//      new MainLuLu().setMainImageOptions("D:\\LivingLux\\products\\LuLu\\FreeToBeBraWild", null, "D:\\LivingLux\\logos\\lulu-logo.png", null, null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "LLULULEMON Free To Be Bra (Wild)").run();     
      

//      new MainLuLu().setMainImageOptions("D:\\LivingLux\\products\\LuLu\\CoolRacerBack", null, "D:\\LivingLux\\logos\\lulu-logo.png", "D:\\LivingLux\\products\\banners\\lulu_coolracerback.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "LLULULEMON Cool Racer Back").run();     
      
      new MainLuLu().setMainImageOptions("D:\\LivingLux\\products\\LuLu\\SweatDateTank", null, "D:\\LivingLux\\logos\\lulu-logo.png", "D:\\LivingLux\\products\\banners\\lulu_sweatdatetank.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
      .setOutputImageOptions(null,null, null, "LLULULEMON SWEAT DATE TANK").run();     
       
      
    }
    
    
    
}
