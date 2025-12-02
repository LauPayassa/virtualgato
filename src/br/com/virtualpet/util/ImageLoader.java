package br.com.virtualpet.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(String path) throws IOException {
        InputStream is = ImageLoader.class.getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Imagem não encontrada: " + path);
        }
        return ImageIO.read(is);
    }
}