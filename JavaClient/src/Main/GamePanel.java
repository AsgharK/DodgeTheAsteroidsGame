

package Main;

import GameStateManager.GameStateManager;
import Handler.Keys;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;

	private static final int FPS = 60;
	private static final long targetTime = 1000 / FPS;
	private long start;
	private long elapsed;
	private long previous;
	private long lag;
	private long wait;

	private Thread thread;
	private boolean running;
	private GameStateManager gsm;

	private Graphics2D g;
	private BufferedImage image;

	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.setFocusable(true);
		requestFocus();

	}

	private void init() {

		image = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT,
				BufferedImage.TYPE_INT_RGB);

		g = (Graphics2D) image.getGraphics();

		running = true;
		
		gsm = new GameStateManager();

	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}

	}

	@Override
	public void run() {

		init();

		while (true) {
			draw();
			draw2Screen();
			start = System.nanoTime();
			elapsed = start - previous;
			previous = start;
			lag += (elapsed/1000000);
		
			while (lag >= targetTime)
			  {
			    update();
			    lag -= targetTime;
			  }
		}
	}

	private void update() {

		gsm.update();

		Keys.update();
	}

	private void draw() {
		gsm.draw(g);

	}

	private void draw2Screen() {
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
		g2.dispose();
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		Keys.KeySet(ke.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		Keys.KeySet(ke.getKeyCode(), false);
	}

}
