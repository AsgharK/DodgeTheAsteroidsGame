
package GameStateManager.LevelState;


import Entities.Player;
import Main.GamePanel;
import Background.Background;
import GameStateManager.GameState;
import GameStateManager.GameStateManager;
import GameStateManager.connectionThread;
import Handler.ImageLoader;
import Handler.Keys;
import static Handler.Keys.ESC;
import static Handler.Keys.LEFT;
import static Handler.Keys.RIGHT;
import static Handler.Keys.DOWN;
import static Handler.Keys.UP;
import static Handler.Keys.SPACE;
import Entities.Projectile;

import java.util.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

import Entities.Animation;

public class LevelState extends GameState {
	protected Background bg;
	protected Player player;
	protected Player opponent;
	protected ArrayList<Projectile> asteroid;
	public connectionThread connection;

	protected boolean down = false;

	public LevelState(GameStateManager gsm) {
		super(gsm);
		connection = new connectionThread();
    	connection.start();
    	JLabel label = new JLabel("Waiting for player 2...");
    	JDialog dialog = new JDialog();
    	dialog.setLocationRelativeTo(null);
    	dialog.setTitle("Warning");
    	dialog.add(label);
    	dialog.pack();
    	while(!connection.ready()){
    		dialog.setVisible(true);
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	dialog.setVisible(false);
		init();
	}

	@Override
	public void init() {
		bg = new Background();
		player = new Player();
		opponent = new Player();
		if(connection.pNum.equals("1")) {
			player.player = ImageLoader.playerI;
			opponent.player = ImageLoader.playerII;
			player.setPosition(350, 440);
			opponent.setPosition(250, 440);
		}
		else if(connection.pNum.equals("2")) {
			player.player = ImageLoader.playerII;
			opponent.player = ImageLoader.playerI;
			player.setPosition(250, 440);
			opponent.setPosition(350, 440);
		}
		player.playerSpriteSheet = new BufferedImage[1];
		player.playerSpriteSheet[0] = player.player;
		player.animation = new Animation();
		player.animation.setFrames(player.playerSpriteSheet);
		opponent.playerSpriteSheet = new BufferedImage[1];
		opponent.playerSpriteSheet[0] = opponent.player;
		opponent.animation = new Animation();
		opponent.animation.setFrames(opponent.playerSpriteSheet);
		asteroid = new ArrayList<Projectile>();
		
	}

	@Override
	public void update() {
		if(connection.checkwin){
			JOptionPane.showMessageDialog(null, "You are the Winner.");
			System.exit(0);
		}
		handleInput();
		if(connection.pNum.equals("1")){
			player.setPosition(connection.p1x, connection.p1y);
			opponent.setPosition(connection.p2x,connection.p2y);
		}else if(connection.pNum.equals("2")){
			player.setPosition(connection.p2x, connection.p2y);
			opponent.setPosition(connection.p1x,connection.p1y);
		}
		checkAttack(player);
		updateProjectile();
		if (checkLose()) {
			JOptionPane.showMessageDialog(null, "You are the loser.");
			System.exit(0);
		}
	}
	public void updateProjectile(){ 
	
		int x=connection.x;
		
		if(x>0) {
			asteroid.add(new Projectile(x, 0));
			connection.x=0;
		}
		
		for (int i = 0; i < asteroid.size(); i++) {
			if(asteroid.get(i).checkRemove()) {
				asteroid.remove(i);
			}
			else {
				asteroid.get(i).update();
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		player.draw(g);
		opponent.draw(g);
		for (int i = 0; i < asteroid.size(); i++) {
            asteroid.get(i).draw(g);
        }
	}

	@Override
	public void handleInput() {
		player.setLeft(Keys.isPress(LEFT), connection.socketOutput);
		player.setRight(Keys.isPress(RIGHT), connection.socketOutput);
		player.setUp(Keys.isPress(UP), connection.socketOutput);
		player.setDown(Keys.isPress(DOWN), connection.socketOutput);
		player.setFiring(Keys.isFirstPress(SPACE));

		if (Keys.isPress(ESC)) {
			gsm.loadPausedState();
		}
	}

	public boolean checkLose() {
		if (player.getHealth() <= 0) {
			return true;
		}

		return false;
	}
	
	public void checkAttack(Player player) {
        for (int i = 0; i < asteroid.size(); i++) {
            if (player.intersects(asteroid.get(i))) {
            	player.isHit();
                asteroid.remove(i);
    			byte[] sendBuffer = {'l','o','s','e'};
    			try {
    				connection.socketOutput.write(sendBuffer);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			connection.breakout = true;
            }
        }
    }
}
