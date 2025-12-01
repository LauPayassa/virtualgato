package br.com.virtualpet.util;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for loading and caching images.
 */
public class ImageLoader {
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();
    
    private ImageLoader() {
        // Prevent instantiation
    }
    
    public static BufferedImage loadImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }
        
        try {
            InputStream inputStream = ImageLoader.class.getResourceAsStream(path);
            if (inputStream != null) {
                BufferedImage image = ImageIO.read(inputStream);
                imageCache.put(path, image);
                return image;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static ImageIcon loadIcon(String path) {
        BufferedImage image = loadImage(path);
        if (image != null) {
            return new ImageIcon(image);
        }
        return null;
    }
    
    public static ImageIcon loadIcon(String path, int width, int height) {
        BufferedImage image = loadImage(path);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
    
    public static BufferedImage loadScaledImage(String path, int width, int height) {
        BufferedImage original = loadImage(path);
        if (original != null) {
            BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            scaled.getGraphics().drawImage(
                original.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null
            );
            return scaled;
        }
        return null;
    }
    
    public static void clearCache() {
        imageCache.clear();
    }
    
    public static void removeFromCache(String path) {
        imageCache.remove(path);
    }
}
