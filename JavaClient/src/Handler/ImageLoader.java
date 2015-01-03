
package Handler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ImageLoader {

    public static BufferedImage playerI = load("/Object/spaceship.png");
    public static BufferedImage playerII = load("/Object/spaceship2.png");
    public static BufferedImage bg = load("/Background/Background.jpg");
    public static BufferedImage BulletI = load("/Object/asteroid.png");
    public static BufferedImage text_r_14 = load("/Object/text-14.png");
    public static BufferedImage text_w_14 = load("/Object/text-w-14.png");
    public static BufferedImage logo = load("/Object/asteroid.png");
    
    private static BufferedImage load(String s) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageLoader.class.getResourceAsStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
