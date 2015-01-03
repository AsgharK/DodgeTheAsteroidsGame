/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Main.GamePanel;
import Handler.ImageLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile extends MapObject {
    public BufferedImage[] BulletImages;
    public BufferedImage[] BulletHitedImages;
    protected BufferedImage BulletI;

    public Projectile(double x, double y)
    {
        BulletI = ImageLoader.BulletI;
        width = 100;
        height = 100;
        cwidth = 50;
        cheight = 50;
        this.x = x;
        this.y = y;
        
        moveSpeed = 2;
        
        BulletImages = new BufferedImage[1];
        BulletImages[0] = BulletI;
        animation = new Animation();

        animation.setFrames(BulletImages);
        animation.setDelay(90);
    }

    public boolean checkRemove()
    {
        if (y>GamePanel.DEFAULT_HEIGHT)
        {
            return true;
        }
        return false;
    }

    public void update() {
        animation.update();
        y = y + moveSpeed;
    }
    
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
