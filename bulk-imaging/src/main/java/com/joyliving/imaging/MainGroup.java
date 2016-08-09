package com.joyliving.imaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainGroup {

    private static Logger m_logger = LoggerFactory.getLogger(MainGroup.class);

    // Default Constructor
    
    GenerateMainImages genMain;
    GenerateImages resize;
    GenerateImages resize2;
    
    public MainGroup() {
        
    }

    public MainGroup setMainImageOptions( String sourceImageDirectory, String outputPath, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString , String ribbonImage ) {
        
        return null;
    }
    
    public MainGroup setOutputImageOptions( String sourceImageDirectory, String watermarkImageFilePath, String bannerImageFilePath, String waterMarkString ) {
        
    

        return null;

    }
    
    
    public void run() throws Exception {

    }
    
    public static void main(String[] args) throws Exception {
        
    }
}
