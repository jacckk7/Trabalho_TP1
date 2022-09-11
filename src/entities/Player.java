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
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
			attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public boolean isAttacking;
	public int attackSpriteCounter;

	public Player(App gp, KeyHandler kh) {
		super(0, 130, 4.0, "down");
		this.gp = gp;
		this.kh = kh;
		this.direction = "down";
		this.isAttacking = false;
		getPlayerImage();
	}

	public void update() {

		if (kh.upPressed || kh.leftPressed || kh.rightPressed || kh.downPressed || kh.kPressed) {
			spriteCounter++;

			if (spriteCounter > 1) {
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

			isAttacking = false;

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionY -= speed;
				direction = "up";
				return;
			}
		}
		if (kh.downPressed) {
			int nextY = (int) Math.ceil((positionY + speed) / 16);
			int nextX = (int) (positionX / 16);

			isAttacking = false;

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionY += speed;
				direction = "down";
				return;
			}

		}
		if (kh.leftPressed) {
			int nextY = (int) (positionY / 16);
			int nextX = (int) Math.floor((positionX - speed) / 16);

			isAttacking = false;

			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionX -= speed;
				direction = "left";
				return;
			}
		}
		if (kh.rightPressed) {
			int nextY = (int) Math.floor(positionY / 16);
			int nextX = (int) Math.ceil((positionX + speed) / 16);

			isAttacking = false;
			if (gp.checkMapPosition(nextY, nextX) == 1) {
				positionX += speed;
				direction = "right";
				return;
			}
		}

		if (kh.kPressed) {
			isAttacking = true;
		}

	}

	public void draw(Graphics g) {
		BufferedImage image = null;

		switch (direction) {
			case "up":
				if (isAttacking) {
					if (attackSpriteCounter == 0) {
						image = attackUp1;
						g.drawImage(image, positionX, positionY - 12, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter == 1) {
						image = attackUp2;
						g.drawImage(image, positionX, positionY - 5, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter >= 2) {
						isAttacking = false;
						attackSpriteCounter = 0;
					}
				}

				if (!isAttacking) {
					if (sprinteNum == 1) {
						image = up2;
					}
					if (sprinteNum == 2) {
						image = up1;
					}
				}
				break;

			case "down":
				if (isAttacking) {
					if (attackSpriteCounter == 0) {
						image = attackDown1;
						g.drawImage(image, positionX, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter == 1) {
						image = attackDown2;
						g.drawImage(image, positionX, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter >= 2) {
						isAttacking = false;
						attackSpriteCounter = 0;
					}
				}

				if (!isAttacking) {
					if (sprinteNum == 1) {
						image = down2;
					}
					if (sprinteNum == 2) {
						image = down1;
					}
				}

				break;
			case "right":
				if (isAttacking) {
					if (attackSpriteCounter == 0) {
						image = attackRight1;
						g.drawImage(image, positionX, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter == 1) {
						image = attackRight2;
						g.drawImage(image, positionX, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter >= 2) {
						isAttacking = false;
						attackSpriteCounter = 0;
					}
				}
				if (!isAttacking) {
					if (sprinteNum == 1) {
						image = right2;
					}
					if (sprinteNum == 2) {
						image = right1;
					}
				}
				break;
			case "left":
				if (isAttacking) {
					if (attackSpriteCounter == 0) {
						image = attackLeft1;
						g.drawImage(image, positionX-11, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter == 1) {
						image = attackLeft2;
						g.drawImage(image, positionX-3, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter >= 2) {
						isAttacking = false;
						attackSpriteCounter = 0;
					}
				}

				if (!isAttacking) {
					if (sprinteNum == 1) {
						image = left2;
					}
					if (sprinteNum == 2) {
						image = left1;
					}
				}
				break;
		}
		g.drawImage(image, positionX, positionY, null);

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
			attackUp1 = ImageIO.read(new FileInputStream("src/assets/link_attack_up1.png"));
			attackUp2 = ImageIO.read(new FileInputStream("src/assets/link_attack_up2.png"));
			attackDown1 = ImageIO.read(new FileInputStream("src/assets/link_attack_down1.png"));
			attackDown2 = ImageIO.read(new FileInputStream("src/assets/link_attack_down2.png"));
			attackLeft1 = ImageIO.read(new FileInputStream("src/assets/link_attack_left1.png"));
			attackLeft2 = ImageIO.read(new FileInputStream("src/assets/link_attack_left2.png"));
			attackRight1 = ImageIO.read(new FileInputStream("src/assets/link_attack_right1.png"));
			attackRight2 = ImageIO.read(new FileInputStream("src/assets/link_attack_right2.png"));
			System.out.println(up1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
