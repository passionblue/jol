package com.joyliving.imaging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger m_logger = LoggerFactory.getLogger(Main.class);

    // Default Constructor
    
    GenerateMainImages genMain;
    GenerateImages resize;
    GenerateImages resize2;
    
    public Main() {
        
        genMain = new GenerateMainImages();
        resize = new GenerateImages();
        resize2 = new GenerateImages();
    }

    public Main setMainImageOptions( String sourceImageDirectory, String outputPath, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString , String ribbonImage ) {

        genMain.setSourceDirectory(sourceImageDirectory);
        
        
        String fullParentPath =  outputPath == null?sourceImageDirectory : outputPath ;
        
        genMain.setOutputDirectory(fullParentPath + "\\output");
        genMain.setMainDirectory(fullParentPath + "\\main");
        genMain.setWatermarkImage(watermarkImageFilePath);
        genMain.setLogoImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        genMain.setMainTitleBannerImage(bannerImageFilePath);
        genMain.setRibbonImage(ribbonImage);
        
        return this;
    }
    
    public Main setOutputImageOptions( String sourceImageDirectory, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString ) {
        
        String sourcePath = sourceImageDirectory == null ? genMain.getSourceDirectory(): sourceImageDirectory;

        List<LineDrawOption> opts = new ArrayList();
        opts.add(new LineDrawOption(0, -1, resize.getTargetWidth(), -1, new Color(210, 69, 8), 0.7f, 20f));
        
        
        resize.setSourceDirectory(sourcePath);
        resize.setOutputDirectory(genMain.getOutputDirectory());
        resize.setBottomLeftWatermarkImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        resize.setRenameOutFiles(true);
//        resize.setTopCenterWatermarkText(waterMarkString);
        resize.setTopCenterWatermarkImage(bannerImageFilePath);
        resize.setLineOptions(opts);        
        resize.setTopCenterWatermarkText(waterMarkString);
        
        /*
         * 
         */
        resize2.setSourceDirectory(sourcePath);
        resize2.setOutputDirectory(genMain.getOutputDirectory()+"2");
        resize2.setBottomLeftWatermarkImage("D:\\LivingLux\\logos\\logo-circle-130.png");
        resize2.setRenameOutFiles(true);
//        resize2.setTopCenterWatermarkText(waterMarkString);
        resize2.setTopCenterWatermarkImage(bannerImageFilePath);
        resize2.setLineOptions(opts);        
        resize2.setTopCenterWatermarkText(waterMarkString);
        
        resize2.setVariableHeight(false);
        resize2.setTargetHeight(800);
        
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
        resize2.convert();
    }
    
    public static void main(String[] args) throws Exception {
        
//      new Main().run("D:\\LivingLux\\products\\Nike\\Testing", "D:\\LivingLux\\logos\\nike_water.png");
//        new Main().run("D:\\LivingLux\\products\\Nike\\FlyKnitLunar3", "D:\\LivingLux\\logos\\nike_water.png");
//        new Main().run("D:\\LivingLux\\products\\Nike\\AirMax_Tavas", "D:\\LivingLux\\logos\\nike_water.png");

//        new Main().run("D:\\LivingLux\\products\\Nike\\BigSwoosh_Black", "D:\\LivingLux\\logos\\nike_water.png"); //, null, null, "D:\\LivingLux\\products\\banners\\448045255.png");
//        new Main().run("D:\\LivingLux\\products\\Nike\\BigSwoosh_White", "D:\\LivingLux\\logos\\nike_water.png"); //, null, null, "D:\\LivingLux\\products\\banners\\nike_bigswoosh_white.png");
//        new Main().run("D:\\LivingLux\\products\\Nike\\BigSwoosh_Red", "D:\\LivingLux\\logos\\nike_water.png"); //, null, null, "D:\\LivingLux\\products\\banners\\nike_bigswoosh_white.png");

//        new Main().run("D:\\LivingLux\\products\\Nike\\AirMax95_White", "D:\\LivingLux\\logos\\nike_water.png");
        
//        new Main().run("D:\\LivingLux\\products\\Nike\\AirMax95_Essential_Black", "D:\\LivingLux\\logos\\nike_water.png");
//        new Main().run("D:\\LivingLux\\products\\NB\\993\\women", "D:\\LivingLux\\logos\\nb-logo.png", "D:\\LivingLux\\products\\NB\\993\\women-image-banner.png");
//        new Main().run("D:\\LivingLux\\products\\NB\\993\\men",   "D:\\LivingLux\\logos\\nb-logo.png", "D:\\LivingLux\\products\\NB\\993\\women-image-banner-mens.png");

        
//        new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax95_Essential_RedWhite", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax95_essential_redwhite.png", null, null)
//        .setOutputImageOptions(null,null, null, "Nike Air Max 95 Essential Red/White/Black").run();
//
//        new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\FlyKnitRacer_Black", null, "D:\\LivingLux\\logos\\nike_water.png", null, null, null)
//        .setOutputImageOptions(null,null, null, "Nike Flyknit Racer Black/White").run();        

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\FlyKnitLunar3_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_flyknit_lunar3.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Flyknit Lunar 3").run();        
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\Huarache\\runultra\\men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_haurache_ultra_all.png", null, null)
//      .setOutputImageOptions(null,null, null, "Men's Nike Huarache Ultra").run();        

//        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\CortezBasic_BlackWhite_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_cortez_basic_leather.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Cortez Basic Leather").run();        
//        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\CortezBasic_WhiteWhite_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_cortez_basic_leather.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Cortez Basic Leather").run();        
//
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\CortezBasic_WhiteBlack_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_cortez_basic_leather.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Cortez Basic Leather").run();        
//      
        
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\FLyKnit_FreeRN_crimson", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_free_run_flyknit.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Free RN Flyknit").run();        
      
//    new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\BigSwoosh_White", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_bigswoosh_white.png", null, null)
//    .setOutputImageOptions(null,null, null, "Nike BigSwoosh White").run();      

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\Uptempo", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_uptempo.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air max Uptempo").run();      
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\Kyrie2", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_kyrie2.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Kyrie 2 Black").run();     
      

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax95_WhitePurple_Women", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_wpur_women.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "Women's Nike Air Max 95 ").run();     
//        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax95_WhiteBlue_Women", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_wb_women.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "Women's Nike Air Max 95 ").run();     

        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirPresto_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airpresto_black.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air Presto Black ").run();     
//        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirPresto_White_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airpresto_white.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air Presto White ").run();    

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Tavas", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_tavas_bw.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air Max Tavas Black/White ").run();    

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Tavas_GreySuede_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_tavas_suede_grey.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air Max Tavas Suede Grey/White ").run();          
//
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Tavas_Grey_Men", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_tavas_grey.png", null, null)
//      .setOutputImageOptions(null,null, null, "Nike Air Max Tavas Grey/White ").run();          
      

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Thea", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_thea_bw.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "Nike Air Max Thea Black/White ").run();          
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Thea_WiteCart", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_thea.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, "Nike Air Max Thea Print White ").run();      
      
      
//    new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax_Thea_White", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_thea.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//    .setOutputImageOptions(null,null, null, "Nike Air Max 95 Premium Brown/Bronz ").run();         
 
      new Main().setMainImageOptions("D:\\LivingLux\\products\\Nike\\AirMax95_Brown", null, "D:\\LivingLux\\logos\\nike_water.png", "D:\\LivingLux\\products\\banners\\nike_airmax_brown.png", null, null)
      .setOutputImageOptions(null,null, null, "Nike Air Max 95 Premium Brown/Bronz ").run();          


        /*
         * New Balance
         */
        
//        new Main().run("D:\\LivingLux\\products\\NB\\993_Grey\\men",   "D:\\LivingLux\\logos\\nb-logo2.png", null, null, "D:\\LivingLux\\products\\banners\\nb_993_grey.png");
//        new Main().run("D:\\LivingLux\\products\\NB\\993_Grey\\women", "D:\\LivingLux\\logos\\nb-logo2.png", null, null, "D:\\LivingLux\\products\\banners\\nb_993_grey.png");
        
//        new Main().run("D:\\LivingLux\\products\\NB\\993_Navy\\men",   "D:\\LivingLux\\logos\\nb-logo2.png", null, null, "D:\\LivingLux\\products\\banners\\nb_993_navy.png");
//        new Main().run("D:\\LivingLux\\products\\NB\\993_Black\\men",   "D:\\LivingLux\\logos\\nb-logo2.png", null, null, null);

//        new Main().run("D:\\LivingLux\\products\\NB\\990v4_Grey_Men",   "D:\\LivingLux\\logos\\nb-logo2.png", null, null, null);
//      new Main().run("D:\\LivingLux\\products\\NB\\990v4_Black_Women",   "D:\\LivingLux\\logos\\nb-logo2.png", null, null, null);
//      new Main().run("D:\\LivingLux\\products\\NB\\580_Sorbet_Women",   "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_580_pink_coral_womens.png", null, null);
        
      

//        new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Grey\\men", "D:\\LivingLux\\products\\NB\\993_Grey\\women", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_grey.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//        .setOutputImageOptions(null,null, null, null).run();

//        new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Grey\\men", null, "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_grey.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//        .setOutputImageOptions(null,null, null, null).run();
        
//        new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\580_Sorbet_Women", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_580_pink_coral_womens.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//        .setOutputImageOptions(null,null, null, null).run();
      
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\990v4_Grey_Men", "D:\\LivingLux\\products\\NB\\990v4_Grey_Women", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_990v4_grey.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, null).run();

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\990v4_Grey_Men", "D:\\LivingLux\\products\\NB\\990v4_Grey_Men", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_990v4_grey.png", null, null)
//      .setOutputImageOptions(null,null, null, null).run();
        
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\990v4_Black_Women", "D:\\LivingLux\\products\\NB\\990v4_Black_Women", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_990v4_black.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, null).run();

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\990v4_Navy_Men", null, "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_990v4_navy.png", null, null)
//      .setOutputImageOptions(null,null, null, null).run();

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Navy\\men", "D:\\LivingLux\\products\\NB\\993_Navy\\women", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_navy.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, null).run();
        
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\580_Sorbet_Women", null, "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_580_pink_coral_womens.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, null).run();
        

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Black\\men", "D:\\LivingLux\\products\\NB\\993_Black\\test", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_black.png", null, "D:\\LivingLux\\products\\banners\\ribbon-women-{size}.png")
//      .setOutputImageOptions(null,null, null, null).run();

//      new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Black\\men", null, "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_black.png", null, null)
//      .setOutputImageOptions(null,null, null, null).run();
        

//  new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Black\\men", "D:\\LivingLux\\products\\NB\\993_Black\\men2", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_black.png", null, "D:\\LivingLux\\products\\banners\\ribbon-men-{size}.png")
//  .setOutputImageOptions(null,null, null, null).run();
//    
//
//  new Main().setMainImageOptions("D:\\LivingLux\\products\\NB\\993_Navy\\men", "D:\\LivingLux\\products\\NB\\993_Navy\\men2", "D:\\LivingLux\\logos\\nb-logo2.png", "D:\\LivingLux\\products\\banners\\nb_993_navy.png", null, "D:\\LivingLux\\products\\banners\\ribbon-men-{size}.png")
//  .setOutputImageOptions(null,null, null, null).run();
//  
        /*
         * FitFlopt
         */
//        new Main().run("D:\\LivingLux\\products\\fitflop\\AIX\\slide",   "D:\\LivingLux\\logos\\fflogo.png");
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\fitflop\\AIX\\slide", null, "D:\\LivingLux\\logos\\fflogo.png", null, null, null)
//      .setOutputImageOptions(null,null, null, null).run();
      
//      new Main().setMainImageOptions("D:\\LivingLux\\products\\fitflop\\Gladie", null, "D:\\LivingLux\\logos\\fflogo.png", "D:\\LivingLux\\products\\banners\\fitflop_gladdie_slide.png", null, null)
//      .setOutputImageOptions(null,null, null, null).run();
      
      /*
         * SAS
         */
//        new Main().run("D:\\LivingLux\\products\\SAS\\bounce",   "D:\\LivingLux\\logos\\sas.png");
        
    }
    
    
    
}
