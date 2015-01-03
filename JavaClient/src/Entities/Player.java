package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class Player extends MapObject {

	public BufferedImage player;

	private int health;
	public BufferedImage[] playerSpriteSheet;
	
	public Player() {
		width = 50;
		height = 70;
		cwidth = 40;
		cheight = 40;

		health = 1;
		moveSpeed = 3;
	}

	public void isHit() {
		health-=1;
	}

	public void setPosition(int i, int i0) {
		this.x = i;
		this.y = i0;
	}

	public void setLeft(boolean s, OutputStream socketOutput) {
		left = s;
		if(left){
			try {
				byte[] sendBuffer = {'l','e','f','t'};
				socketOutput.write(sendBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setRight(boolean s, OutputStream socketOutput) {
		right = s;
		if(right){
			try {
				byte[] sendBuffer = {'r','i','g','h','t'};
				socketOutput.write(sendBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setUp(boolean s, OutputStream socketOutput) {
		up = s;
		if(up){
			byte[] sendBuffer = new byte [] {'u','p'};
			try {
				socketOutput.write(sendBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setDown(boolean s, OutputStream socketOutput) {
		down = s;
		if(down){
			byte [] sendBuffer = new byte [] {'d','o','w','n'};
			try {
				socketOutput.write(sendBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void getNextPosition() {
		if (left) {
			x = x - moveSpeed;
			if (x < width / 2) {
				x = width / 2;
			}

		}

		if (right) {
			x = x + moveSpeed;
			if (x > mapWidth - width / 2) {
				x = mapWidth - width / 2;
			}
		}
		
		if (up) {
			y = y - moveSpeed;
			if (y < height / 2) {
				y = height / 2;
			}
		}
		
		if (down) {
			y = y + moveSpeed;
			if (y > mapHeight - height/ 2) {
				y = mapHeight - height / 2;
			}
		}
		
		

	}

	public int getHealth()
    {
        return health;
    }
	
	public void update() {
		getNextPosition();
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
