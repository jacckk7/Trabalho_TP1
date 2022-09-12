package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.App;

public class EnemyRanged extends Character {

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2, bDown, bLeft, bUp, bRight, death;
    App gp;
    Player player;
    private String isLinkTargetable = "no";
    private String movement;
    private String bulletDirection;
    private int initial_positionX;
    private int initial_positionY;
    private int shootCount = 0;
    private int bulletX, bulletY;
    private boolean countSprite = false;
    private boolean isBullet = false;

    public EnemyRanged(int positionX, int positionY, String movement, Player player, App gp) {
        super(positionX, positionY, 1.0, "up");
        super.life = 6;
        this.movement = movement;
        this.initial_positionX = positionX;
        this.initial_positionY = positionY;
        this.player = player;
        this.gp = gp;
        if(movement.equals("horizontal")) {
            super.direction = "right";
        }
        getRangedEnemyImage();
    }

    public void draw(Graphics g) {
        BufferedImage image = null;
        BufferedImage imageBullet = null;

        if (life > -1) {
            if (life > 0) {
                if (direction.equals("up") && countSprite) {
                    image = up2;
                } else if (direction.equals("up")) {
                    image = up1;
                } else if (direction.equals("down") && countSprite) {
                    image = down2;
                } else if (direction.equals("down")) {
                    image = down1;
                } else if (direction.equals("right") && countSprite) {
                    image = right2;
                } else if (direction.equals("right")) {
                    image = right1;
                } else if (direction.equals("left") && countSprite) {
                    image = left2;
                } else if (direction.equals("left")) {
                    image = left1;
                }
            } else if (life == 0) {
                image = death;
                life -= 1;
            }
            g.drawImage(image, positionX, positionY, null);
    
            if (isBullet) {
                if (bulletDirection.equals("down")) {
                    imageBullet = bDown;
                } else if (bulletDirection.equals("left")) {
                    imageBullet = bLeft;
                } else if (bulletDirection.equals("up")) {
                    imageBullet = bUp;
                } else if (bulletDirection.equals("right")) {
                    imageBullet = bRight;
                }
    
                g.drawImage(imageBullet, bulletX, bulletY, null);
            }
        }
    }

    public void update() {
        if (life > 0) {
            if (isLinkTargetable.equals("no")) {
                if (movement.equals("vertical")) {
                    if (positionY == initial_positionY - 128 && direction.equals("up")) {
                        direction = "down";
                        positionY += (int)speed;
                        countSprite = false;
                    } else if (direction.equals("up")) {
                        positionY -= (int)speed;
                        countSprite = !countSprite;
                    } else if (positionY == initial_positionY && direction.equals("down")) {
                        direction = "up";
                        positionY -= (int)speed;
                        countSprite = false;
                    } else if (direction.equals("down")) {
                        positionY += (int)speed;
                        countSprite = !countSprite;
                    }
                } else if (movement.equals("horizontal")) {
                    if (positionX == initial_positionX + 144 && direction.equals("right")) {
                        direction = "left";
                        positionX -= (int)speed;
                        countSprite = false;
                    } else if (direction.equals("right")) {
                        positionX += (int)speed;
                        countSprite = !countSprite;
                    } else if (positionX == initial_positionX && direction.equals("left")) {
                        direction = "right";
                        positionX += (int)speed;
                        countSprite = false;
                    } else if (direction.equals("left")) {
                        positionX -= (int)speed;
                        countSprite = !countSprite;
                    }
                }
            } else if (movement.equals(isLinkTargetable)) {
                if (movement.equals("vertical")) {
                    if (positionY < player.getPositionY()) {
                        direction = "down";
                        shoot();
                    } else {
                        direction = "up";
                        shoot();
                    }
                } else if (movement.equals("horizontal")) {
                    if (positionX < player.getPositionX()) {
                        direction = "right";
                        shoot();
                    } else {
                        direction = "left";
                        shoot();
                    }
                }
            } else {
                if (movement.equals("vertical")) {
                    if (positionX < player.getPositionX()) {
                        direction = "right";
                        shoot();
                    } else {
                        direction = "left";
                        shoot();
                    }
                } else if (movement.equals("horizontal")) {
                    if (positionY < player.getPositionY()) {
                        direction = "down";
                        shoot();
                    } else {
                        direction = "up";
                        shoot();
                    }
                }
            }
    
            if (player.getPositionX() > positionX - 8 && player.getPositionX() < positionX + 8) {
                isLinkTargetable = "vertical";
            } else if (player.getPositionY() > positionY - 8 && player.getPositionY() < positionY + 8) {
                isLinkTargetable = "horizontal";
            } else if (isLinkTargetable.equals("vertical") || isLinkTargetable.equals("horizontal")){
                isLinkTargetable = "no";
                if(movement.equals("horizontal")) {
                    direction = "right";
                } else {
                    direction = "up";
                }
            }
    
            if (shootCount != 0) {
                shootCount--;
            }
    
            if (isBullet) {
                if (bulletDirection.equals("down")) {
                    bulletY += 2;
                    if (bulletY > 256) {
                        isBullet = false;
                    } else if (hitLink()) {
                        player.getHit();
                        isBullet = false;
                    }
                } else if (bulletDirection.equals("left")) {
                    bulletX -= 2;
                    if (bulletX < 0) {
                        isBullet = false;
                    } else if (hitLink()) {
                        player.getHit();
                        isBullet = false;
                    }
                } else if (bulletDirection.equals("up")) {
                    bulletY -= 2;
                    if (bulletY < 0) {
                        isBullet = false;
                    } else if (hitLink()) {
                        player.getHit();
                        isBullet = false;
                    }
                }  else if (bulletDirection.equals("right")) {
                    bulletX += 2;
                    if (bulletX > 240) {
                        isBullet = false;
                    } else if (hitLink()) {
                        player.getHit();
                        isBullet = false;
                    }
                }
            }
        }
    }
    
    public void shoot() {
        if (shootCount == 0) {
            isBullet = true;
            bulletDirection = direction;
            bulletX = positionX;
            bulletY = positionY;

            shootCount = 120;
        }
    }

    public boolean hitLink() {
        Rectangle link = new Rectangle(player.getPositionX(), player.getPositionY(), 16, 16);
        Rectangle bullet = new Rectangle(bulletX, bulletY, 16, 16);
        if (bulletDirection.equals("down") || bulletDirection.equals("up")) {
            bullet = new Rectangle(bulletX + 4, bulletY, 8, 8);
        } else if (bulletDirection.equals("left") || bulletDirection.equals("right")) {
            bullet = new Rectangle(bulletX, bulletY + 4, 8, 8);
        }
        if(bullet.intersects(link)) {
            return true;
        }

        return false;
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

    public void getRangedEnemyImage() {
		try {
			up1 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_up1.png"));
            up2 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_up2.png"));
			down1 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_down1.png"));
            down2 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_down2.png"));
			left1 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_left1.png"));
			left2 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_left2.png"));
			right1 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_right1.png"));
			right2 = ImageIO.read(new FileInputStream("src/assets/ranged_enemy_right2.png"));
            bDown = ImageIO.read(new FileInputStream("src/assets/bullet_down.png"));
            bLeft = ImageIO.read(new FileInputStream("src/assets/bullet_left.png"));
            bUp = ImageIO.read(new FileInputStream("src/assets/bullet_up.png"));
            bRight = ImageIO.read(new FileInputStream("src/assets/bullet_right.png"));
            death = ImageIO.read(new FileInputStream("src/assets/enemy_down.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

