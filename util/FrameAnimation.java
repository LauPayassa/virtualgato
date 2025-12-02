package br.com.virtualpet.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FrameAnimation {

    private final Image[] frames;

    public FrameAnimation(String basePath, String[] fileNames) throws IOException {
        frames = new Image[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            String fullPath = basePath + fileNames[i];
            frames[i] = ImageLoader.loadImage(fullPath);
        }
    }

    public static FrameAnimation fromFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Pasta não encontrada: " + folderPath);
        }

        File[] files = folder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".png") || 
            name.toLowerCase().endsWith(".jpg") || 
            name.toLowerCase().endsWith(".gif")
        );

        if (files == null || files.length == 0) {
            throw new IOException("Nenhum sprite encontrado em: " + folderPath);
        }

        Arrays.sort(files);
        Image[] frames = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            frames[i] = ImageLoader.loadImageFromFile(files[i].getAbsolutePath());
        }

        return new FrameAnimation(frames);
    }

    private FrameAnimation(Image[] frames) {
        this.frames = frames;
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