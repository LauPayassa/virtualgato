package br.com.virtualpet.time;

import br.com.virtualpet.GameConfig;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages game time and tick events.
 */
public class GameClock {
    private Timer timer;
    private List<Runnable> tickListeners;
    private boolean running;
    
    public GameClock() {
        this.tickListeners = new ArrayList<>();
        this.running = false;
        
        ActionListener tickAction = e -> {
            for (Runnable listener : tickListeners) {
                listener.run();
            }
        };
        
        this.timer = new Timer(GameConfig.TICK_INTERVAL_MS, tickAction);
    }
    
    public void addTickListener(Runnable listener) {
        tickListeners.add(listener);
    }
    
    public void removeTickListener(Runnable listener) {
        tickListeners.remove(listener);
    }
    
    public void start() {
        if (!running) {
            timer.start();
            running = true;
        }
    }
    
    public void stop() {
        if (running) {
            timer.stop();
            running = false;
        }
    }
    
    public void pause() {
        stop();
    }
    
    public void resume() {
        start();
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setTickInterval(int intervalMs) {
        timer.setDelay(intervalMs);
    }
}
