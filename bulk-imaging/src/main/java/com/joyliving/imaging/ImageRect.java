package com.joyliving.imaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ImageRect {

    private static Logger m_logger = LoggerFactory.getLogger(ImageRect.class);

    //Default Constructor

    int xleft, xright, ytop, ybottom;
    
    public ImageRect() {
    }
    
    public ImageRect(int xleft, int xright, int ytop, int ybottom) {
        super();
        this.xleft = xleft;
        this.xright = xright;
        this.ytop = ytop;
        this.ybottom = ybottom;
    }

    public int getXleft() {
        return xleft;
    }

    public void setXleft(int xleft) {
        this.xleft = xleft;
    }

    public int getXright() {
        return xright;
    }

    public void setXright(int xright) {
        this.xright = xright;
    }

    public int getYtop() {
        return ytop;
    }

    public void setYtop(int ytop) {
        this.ytop = ytop;
    }

    public int getYbottom() {
        return ybottom;
    }

    public void setYbottom(int ybottom) {
        this.ybottom = ybottom;
    }

    
    
}
