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
		super(0, 0, 4.0, "down");
		this.gp = gp;
		this.kh = kh;
		this.direction = "down";
		getPlayerImage();
	}

	public void update() {
		if (kh.upPressed) {
			positionY -= speed;
			direction="up";
			return;
		}
		if (kh.downPressed) {
			positionY += speed;
			direction="down";
			return;
		}
		if (kh.leftPressed) {
			positionX -= speed;
			direction="left";
			return;
		}
		if (kh.rightPressed) {
			positionX += speed;
			direction="right";
			return;
		}
	}

	public void draw(Graphics g) {
		BufferedImage image= null;;

		switch(direction){
			case "up":
				image=up1;
				break;
			case "down":
				image=down1;
				break;
			case "right":
				image = right1;
				break;
			case "left":
				image = left1;
				break;
		}
		g.drawImage(image, positionX, positionY, gp.tileSize,gp.tileSize,null);
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
