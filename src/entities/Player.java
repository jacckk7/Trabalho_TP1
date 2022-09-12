package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
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
		super(16, 130, 4.0, "down");
		this.gp = gp;
		this.kh = kh;
		this.direction = "down";
		this.isAttacking = false;
		super.life = 12;
		getPlayerImage();
	}

	public void update() {

		int nextY, nextX;
		boolean needsToCheckNext;

		if (kh.upPressed || kh.leftPressed || kh.rightPressed || kh.downPressed || kh.kPressed) {
			spriteCounter++;

			if (spriteCounter > 10) {
				if (sprinteNum == 1) {
					sprinteNum = 2;
				} else if (sprinteNum == 2) {
					sprinteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		if (kh.upPressed) {
			nextY = (int) Math.floor((positionY - speed) / 16);
			nextX = (int) (positionX / 16);

			needsToCheckNext = (positionX % 16 >= 8);

			isAttacking = false;

<<<<<<< HEAD
			if (gp.checkMapPosition(nextY, nextX) == 1 && !isColliding(positionX, (int) (positionY - speed))) {
				positionY -= speed;
				direction = "up";
				return;
=======
			if (!needsToCheckNext) {
				if (gp.checkMapPosition(nextY, nextX) == 1) {
					positionY -= speed;
				}
			} else {
				if (gp.checkMapPosition(nextY, nextX + 1) == 1) {
					positionY -= speed;
				}
>>>>>>> main
if (!needsToCheckNext) {
	if (gp.checkMapPosition(nextY, nextX) == 1  && !isColliding(positionX, (int) (positionY - speed))) {
		positionY -= speed;
	}
} else {
	if (gp.checkMapPosition(nextY, nextX + 1) == 1  && !isColliding(positionX, (int) (positionY - speed))) {
		positionY -= speed;
	}
}
direction = "up";
return;
		if (kh.downPressed) {
			nextY = (int) Math.ceil((positionY + speed) / 16);
			nextX = (int) (positionX / 16);

			needsToCheckNext = (positionX% 16 >= 8);

			isAttacking = false;

<<<<<<< HEAD
			if (gp.checkMapPosition(nextY, nextX) == 1 && !isColliding(positionX, (int) (positionY + speed))) {
				positionY += speed;
				direction = "down";
				return;
=======
			if (!needsToCheckNext) {
				if (gp.checkMapPosition(nextY, nextX) == 1) {
					positionY += speed;
				}
			} else {
				if (gp.checkMapPosition(nextY, nextX+1) == 1) {
					positionY += speed;
				}
>>>>>>> main
if (!needsToCheckNext) {
	if (gp.checkMapPosition(nextY, nextX) == 1  && !isColliding(positionX, (int) (positionY + speed))) {
		positionY += speed;
	}
} else {
	if (gp.checkMapPosition(nextY, nextX+1) == 1  && !isColliding(positionX, (int) (positionY + speed))) {
		positionY += speed;
	}
}
direction = "down";
return;
		if (kh.leftPressed) {
			nextY = (int) (positionY / 16);
			nextX = (int) Math.floor((positionX - speed) / 16);

			boolean needsToCheckNext = (positionY% 16 >= 10);

			isAttacking = false;

<<<<<<< HEAD
			if (gp.checkMapPosition(nextY, nextX) == 1 && !isColliding((int) (positionX - speed), positionY)) {
				positionX -= speed;
				direction = "left";
				return;
=======
			if (!needsToCheckNext) {
				if (gp.checkMapPosition(nextY, nextX) == 1) {
					positionX -= speed;
				}
			} else {
				if (gp.checkMapPosition(nextY+1, nextX) == 1) {
					positionX -= speed;
				}
>>>>>>> main
if (!needsToCheckNext) {
	if (gp.checkMapPosition(nextY, nextX) == 1  && !isColliding((int) (positionX - speed), positionY)) {
		positionX -= speed;
	}
} else {
	if (gp.checkMapPosition(nextY+1, nextX) == 1  && !isColliding((int) (positionX - speed), positionY)) {
		positionX -= speed;
	}
}
direction = "left";
return;
}

		if (kh.rightPressed) {
			nextY = (int) (positionY / 16);
			nextX = (int) Math.ceil((positionX + speed) / 16);
			isAttacking = false;
<<<<<<< HEAD
			if (gp.checkMapPosition(nextY, nextX) == 1 && !isColliding((int) (positionX + speed), positionY)) {
				positionX += speed;
				direction = "right";
				return;
=======

			boolean needsToCheckNext = (positionY% 16 >= 10);
	
			isAttacking = false;

			if (!needsToCheckNext) {
				if (gp.checkMapPosition(nextY, nextX) == 1) {
					positionX += speed;
				}
			} else {
				if (gp.checkMapPosition(nextY+1, nextX) == 1) {
					positionX += speed;
				}
>>>>>>> main
if (!needsToCheckNext) {
	if (gp.checkMapPosition(nextY, nextX) == 1  && !isColliding((int) (positionX + speed), positionY)) {
		positionX += speed;
	}
} else {
	if (gp.checkMapPosition(nextY+1, nextX) == 1  && !isColliding((int) (positionX + speed), positionY)) {
		positionX += speed;
	}
}
direction = "right";
return;
		}

		if (kh.kPressed) {
			isAttacking = true;
			isHiting();
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
						g.drawImage(image, positionX - 11, positionY, null);
						attackSpriteCounter++;
						return;
					}
					if (attackSpriteCounter == 1) {
						image = attackLeft2;
						g.drawImage(image, positionX - 3, positionY, null);
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

	public boolean isColliding(int nextX, int nextY) {
        Rectangle link = new Rectangle(nextX, nextY, 16, 16);
        if (gp.tm.getCurrentMap().getName().equals("Bottom Right")) {
            for(int i = 0; i < App.enemiesBottomRight.size(); i++) {
                EnemyMeele e = App.enemiesBottomRight.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(link.intersects(targetEnemy)) {
                    return true;
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Top left")) {
            for(int i = 0; i < App.enemiesTopLeft.size(); i++) {
                EnemyMeele e = App.enemiesTopLeft.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(link.intersects(targetEnemy)) {
                    return true;
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Bottom left")) {
            for(int i = 0; i < App.enemiesBottomLeft.size(); i++) {
                EnemyRanged e = App.enemiesBottomLeft.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(link.intersects(targetEnemy)) {
                    return true;
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Top Right")) {
            for(int i = 0; i < App.enemiesTopRight.size(); i++) {
                EnemyRanged e = App.enemiesTopRight.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(link.intersects(targetEnemy)) {
                    return true;
                }
            }
        }

        return false;
    }

	public void isHiting() {
		int swordX = 0, swordY = 0, swordW = 0, swordH = 0;
		if (direction.equals("down")) {
			swordX = positionX + 4;
			swordY = positionY + 16;
			swordW = 8;
			swordH = 16;
		} else if (direction.equals("left")) {
			swordX = positionX - 16;
			swordY = positionY + 4;
			swordW = 16;
			swordH = 8;
		} else if (direction.equals("up")) {
			swordX = positionX + 4;
			swordY = positionY - 16;
			swordW = 8;
			swordH = 16;
		} else if (direction.equals("right")) {
			swordX = positionX + 16;
			swordY = positionY + 4;
			swordW = 16;
			swordH = 8;
		}
        Rectangle linkSword = new Rectangle(swordX, swordY, swordW, swordH);
        if (gp.tm.getCurrentMap().getName().equals("Bottom Right")) {
            for(int i = 0; i < App.enemiesBottomRight.size(); i++) {
                EnemyMeele e = App.enemiesBottomRight.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(linkSword.intersects(targetEnemy)) {
                    e.getHit();
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Top left")) {
            for(int i = 0; i < App.enemiesTopLeft.size(); i++) {
                EnemyMeele e = App.enemiesTopLeft.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(linkSword.intersects(targetEnemy)) {
                    e.getHit();
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Bottom left")) {
            for(int i = 0; i < App.enemiesBottomLeft.size(); i++) {
                EnemyRanged e = App.enemiesBottomLeft.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(linkSword.intersects(targetEnemy)) {
                    e.getHit();
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Top Right")) {
            for(int i = 0; i < App.enemiesTopRight.size(); i++) {
                EnemyRanged e = App.enemiesTopRight.get(i);
                Rectangle targetEnemy = new Rectangle(e.getPositionX() + 2, e.getPositionY() + 2, 12, 12);
                if(linkSword.intersects(targetEnemy)) {
                    e.getHit();
                }
            }
        }
    }

	public void getHit() {
		this.life -= 1;
		if (direction.equals("down")) {
            positionY -= 4;
        } else if (direction.equals("left")) {
            positionX += 4;
        } else if (direction.equals("up")) {
            positionY += 4;
        } else if (direction.equals("right")) {
            positionX -= 4;
        }
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}