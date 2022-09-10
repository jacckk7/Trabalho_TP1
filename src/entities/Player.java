package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import handlers.KeyHandler;
import main.App;

import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Character {
	App gp;
	KeyHandler kh;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

	public Player(App gp, KeyHandler kh) {
		super(0, 130, 4.0, "down");
		this.gp = gp;
		this.kh = kh;
		this.direction = "down";
		getPlayerImage();
	}

	public void update() {

		if (kh.upPressed || kh.leftPressed || kh.rightPressed || kh.downPressed) {
			spriteCounter++;

			if (spriteCounter > 2) {
				if (sprinteNum == 1) {
					sprinteNum = 2;
				} else if (sprinteNum == 2) {
					sprinteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		if (kh.upPressed) {
			int nextY = (int) Math.floor((positionY - speed) / 16);
			int nextX = (int) (positionX / 16);

			System.out.println("nextX: " + nextX);
			System.out.println("nextY: " + nextY);

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionY -= speed;
				direction = "up";
				return;
			}
		}
		if (kh.downPressed) {
			int nextY = (int) Math.ceil((positionY + speed) / 16);
			int nextX = (int) (positionX / 16);

			System.out.println("nextX: " + nextX);
			System.out.println("nextY: " + nextY);

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionY += speed;
				direction = "down";
				return;
			}

		}
		if (kh.leftPressed) {
			int nextY = (int) (positionY / 16);
			int nextX = (int) Math.floor((positionX - speed) / 16);

			System.out.println("nextX: " + nextX);
			System.out.println("nextY: " + nextY);

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionX -= speed;
				direction = "left";
				return;
			}
		}
		if (kh.rightPressed) {
			int nextY = (int) Math.floor(positionY / 16);
			int nextX = (int) Math.ceil((positionX + speed) / 16);

			System.out.println("nextX: " + nextX);
			System.out.println("nextY: " + nextY);

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionX += speed;
				direction = "right";
				return;
			}
		}
	}

	public void draw(Graphics g) {
		BufferedImage image = null;

		switch (direction) {
			case "up":
				if (sprinteNum == 1) {
					image = up2;
				}
				if (sprinteNum == 2) {
					image = up1;
				}
				break;
			case "down":
				if (sprinteNum == 1) {
					image =down2;
				}
				if (sprinteNum == 2) {
					image = down1;
				}
				break;
			case "right":
				if (sprinteNum == 1) {
					image = right2;
				}
				if (sprinteNum == 2) {
					image = right1;
				}
				break;
			case "left":
				if (sprinteNum == 1) {
					image = left2;
				}
				if (sprinteNum == 2) {
					image = left1;
				}
				break;
		}
		g.drawImage(image, positionX, positionY, gp.tileSize, gp.tileSize, null);

	}

	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(new FileInputStream("src/assets/link_up1.png"));
			up2 = ImageIO.read(new FileInputStream("src/assets/link_up2.png"));
			down1 = ImageIO.read(new FileInputStream("src/assets/link_down1.png"));
			down2 = ImageIO.read(new FileInputStream("src/assets/link_down2.png"));
			left1 = ImageIO.read(new FileInputStream("src/assets/link_left1.png"));
			left2 = ImageIO.read(new FileInputStream("src/assets/link_left2.png"));
			right1 = ImageIO.read(new FileInputStream("src/assets/link_right1.png"));
			right2 = ImageIO.read(new FileInputStream("src/assets/link_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
