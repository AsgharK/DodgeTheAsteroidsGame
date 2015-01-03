package Entities;

import java.awt.image.BufferedImage;

public class Animation {
    
    BufferedImage[] frames;
    private int currentFrame;
    private int delay;
    private boolean playedOnce;
    private long startTime;
    
    public Animation()
    {
        playedOnce = false;
    }
    
    public void setFrames(BufferedImage[] frames)
    {
        
        this.frames = frames;
        currentFrame = 0;
        playedOnce = false;
        startTime = System.nanoTime();
        
    }
    public void setFrame(int currentFrame)
    {
        this.currentFrame = currentFrame;
    }
    
    public void setDelay(int delay)
    {
        this.delay = delay;
    }
    
    public void update()
    {
        if (delay == -1)
            return;
        
        long elapsed = (System.nanoTime() - startTime)/1000000;
        
        if (elapsed > delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        
        if (currentFrame == frames.length )
        {
            currentFrame = 0;
            playedOnce = true;
            
        }
    }
    
    public int getFrame()
    {
        return currentFrame;
    }
    
    public boolean hasPlayedOnce()
    {
        return playedOnce;
    }
    
    public BufferedImage getImage()
    {
        return frames[currentFrame];
    }
}
