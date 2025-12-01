package br.com.virtualpet.util;

import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles frame-based animations for sprites.
 */
public class FrameAnimation {
    private List<BufferedImage> frames;
    private int currentFrameIndex;
    private int frameDelayMs;
    private Timer animationTimer;
    private boolean running;
    private boolean loop;
    private Runnable onFrameChange;
    private Runnable onAnimationComplete;
    
    public FrameAnimation(int frameDelayMs) {
        this.frames = new ArrayList<>();
        this.currentFrameIndex = 0;
        this.frameDelayMs = frameDelayMs;
        this.running = false;
        this.loop = true;
        
        this.animationTimer = new Timer(frameDelayMs, e -> nextFrame());
    }
    
    public void addFrame(BufferedImage frame) {
        frames.add(frame);
    }
    
    public void addFrames(List<BufferedImage> frameList) {
        frames.addAll(frameList);
    }
    
    public void addFramesFromPaths(String... paths) {
        for (String path : paths) {
            BufferedImage image = ImageLoader.loadImage(path);
            if (image != null) {
                frames.add(image);
            }
        }
    }
    
    private void nextFrame() {
        if (frames.isEmpty()) {
            return;
        }
        
        currentFrameIndex++;
        
        if (currentFrameIndex >= frames.size()) {
            if (loop) {
                currentFrameIndex = 0;
            } else {
                currentFrameIndex = frames.size() - 1;
                stop();
                if (onAnimationComplete != null) {
                    onAnimationComplete.run();
                }
                return;
            }
        }
        
        if (onFrameChange != null) {
            onFrameChange.run();
        }
    }
    
    public void start() {
        if (!running && !frames.isEmpty()) {
            running = true;
            animationTimer.start();
        }
    }
    
    public void stop() {
        if (running) {
            running = false;
            animationTimer.stop();
        }
    }
    
    public void reset() {
        stop();
        currentFrameIndex = 0;
    }
    
    public BufferedImage getCurrentFrame() {
        if (frames.isEmpty()) {
            return null;
        }
        return frames.get(currentFrameIndex);
    }
    
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }
    
    public int getFrameCount() {
        return frames.size();
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
    public boolean isLoop() {
        return loop;
    }
    
    public void setFrameDelay(int delayMs) {
        this.frameDelayMs = delayMs;
        animationTimer.setDelay(delayMs);
    }
    
    public int getFrameDelay() {
        return frameDelayMs;
    }
    
    public void setOnFrameChange(Runnable callback) {
        this.onFrameChange = callback;
    }
    
    public void setOnAnimationComplete(Runnable callback) {
        this.onAnimationComplete = callback;
    }
    
    public void clearFrames() {
        stop();
        frames.clear();
        currentFrameIndex = 0;
    }
}
