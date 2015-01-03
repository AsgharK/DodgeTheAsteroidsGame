package Background;

import Handler.ImageLoader;
import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Background {
    
    private BufferedImage image;
    
    public Background()
    {
        image = ImageLoader.bg;
        image = image.getSubimage(0, 0, GamePanel.DEFAULT_WIDTH, GamePanel.DEFAULT_HEIGHT);
    }
    
    
    public void draw(Graphics2D g)
    {
        g.drawImage(image, 0, 0 , null);
    }
    
}
