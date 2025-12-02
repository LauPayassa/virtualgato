package br.com.virtualpet.util;

import java.awt.*;
import java.io.IOException;

public class FrameAnimation {

    private final Image[] frames;

    public FrameAnimation(String basePath, String[] fileNames) throws IOException {
        frames = new Image[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            String fullPath = basePath + fileNames[i];
            frames[i] = ImageLoader.loadImage(fullPath);
        }
    }

    public int getFrameCount() {
        return frames.length;
    }

    public Image getFrame(int index) {
        if (frames.length == 0) return null;
        if (index < 0) index = 0;
        index = index % frames.length;
        return frames[index];
    }
}