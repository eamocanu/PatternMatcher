package brain.image;
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Robot;


public class ImageUtils {
    
    /**
     * Scales an image to the new width/height given
     */
    public static BufferedImage scaleImage(Image image, int newWidth, int newHeight) {
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.setTransform(AffineTransform.getScaleInstance(newWidth / (double)image.getWidth(null), newHeight / (double)image.getHeight(null)));
        g.drawImage(image, 0, 0, null);
        return newImage;
    }
    
    /**
     * Scales the image uniformly (preserving aspect ratio) to the max width/height given.
     * The function guarantees that the image is no larger than either the max width or height
     * given.
     */
    public static BufferedImage scaleImageUniformly(Image image, int maxWidth, int maxHeight) {
        double scaleFactor = 0.0;
        scaleFactor = Math.min(maxWidth / (double)image.getWidth(null), maxHeight / (double)image.getHeight(null));
        int newWidth = Math.max(1, (int)(scaleFactor * image.getWidth(null)));
        int newHeight = Math.max(1, (int)(scaleFactor * image.getHeight(null)));
        return scaleImage(image, newWidth, newHeight);
    }
    
    /**
     * Convenience routine for opening an image file
     */
    public static BufferedImage openImage(String filename) throws IOException {
        return ImageIO.read(new File(filename));
    }
    
    /**
     * Copies topImage on top of baseImage, through the mask specified,
     * putting the result in dstImage (if non-null) and returns the resulting image.
     */
    public static BufferedImage copyImageThroughMask(BufferedImage baseImage, BufferedImage topImage, BufferedImage dstImage, Shape mask) {
        if (dstImage == null) {
            dstImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g = (Graphics2D)dstImage.createGraphics();
        g.drawImage(baseImage, null, 0, 0);
        Shape oldClip = g.getClip();
        g.setClip(mask);
        g.drawImage(topImage, null, 0, 0);
        g.setClip(oldClip);
        return dstImage;
    }
    
    
    /**
     * Another exceedingly simple method to save jpeg images to file
     */
    public static void saveJPEGImage(BufferedImage image, String filename) throws IOException {
        // OK, this is kind of lame, but the encoder will only write out TYPE RGB, rather than TYPE ARGB
        BufferedImage saveImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = saveImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        ImageIO.write(saveImage, "jpeg", new File(filename));
    }
    
    
    public static BufferedImage captureFullScreen() throws AWTException {
    	Robot robot = new Robot();//lame -> make non static
    	BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//    	ImageIO.write(screenShot, "JPG", new File("screenShot.jpg"));
    	robot = null;
    	return screenShot;
    }
    
    
    public static BufferedImage captureScreen(int x, int y, int width, int height) throws AWTException {
    	Robot robot = new Robot();//lame -> make non static
    	BufferedImage screenShot = robot.createScreenCapture(new Rectangle(x,y,width,height));
//    	ImageIO.write(screenShot, "JPG", new File("screenShot.jpg"));
    	robot = null;
    	return screenShot;
    }
    
    
}