package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.App;

public class EnemyMeele extends Character {

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2, death;
    App gp;
    Player player;
    private boolean isLinkNear = false;
    private String movement;
    private int initial_positionX;
    private int initial_positionY;
    private boolean countSprite = false;

    public EnemyMeele(int positionX, int positionY, String movement, Player player, App gp) {
        super(positionX, positionY, 1.0, "up");
        super.life = 6;
        this.movement = movement;
        this.initial_positionX = positionX;
        this.initial_positionY = positionY;
        this.player = player;
        this.gp = gp;
        this.scoreForKilling = 80;
        if(movement.equals("horizontal")) {
            super.direction = "right";
        }
        getMeeleEnemyImage();
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        if (life > -1) {
            if (this.life > 0) {
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
            } else if (this.life == 0) {
                image = death;
                life -= 1;
            }
    
            g.drawImage(image, positionX, positionY, null);
        }
    }

    public void update() {
        if (life > 0) {
            if (!isLinkNear) {
                if (movement.equals("vertical")) {
                    if (positionY == initial_positionY - 64 && direction.equals("up")) {
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
                    if (positionX == initial_positionX + 64 && direction.equals("right")) {
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
            } else {
                if (positionY < player.getPositionY() && gp.checkMapPosition((int) (positionX / 16), (int) (Math.ceil((positionY + speed) / 16))) == 1 && !isColliding(positionX, (int) (positionY + speed))) {
                    positionY += (int)speed;
                    countSprite = !countSprite;
                    direction = "down";
                    hitLink(positionX, (int) (positionY + speed));
                } else if (positionY > player.getPositionY() && gp.checkMapPosition((int) (positionX /16), (int) (Math.floor((positionY - speed) / 16))) == 1 && !isColliding(positionX, (int) (positionY - speed))) {
                    positionY -= (int)speed;
                    countSprite = !countSprite;
                    direction = "up";
                    hitLink(positionX, (int) (positionY - speed));
                }
                
                if (positionX < player.getPositionX() && gp.checkMapPosition((int) (Math.ceil((positionX + speed) / 16)), (int) Math.floor((positionY / 16))) == 1 && !isColliding((int)  (positionX + speed), positionY)) {
                    positionX += (int)speed;
                    countSprite = !countSprite;
                    direction = "right";
                    hitLink((int)  (positionX + speed), positionY);
                } else if (positionX > player.getPositionX() && gp.checkMapPosition((int) (Math.floor((positionX - speed) / 16)), (int) (positionY / 16)) == 1 && !isColliding((int) (positionX - speed), positionY)) {
                    positionX -= (int)speed;
                    countSprite = !countSprite;
                    direction = "left";
                    hitLink((int) (positionX - speed), positionY);
                }
            }
    
            double distanceToLink = Math.pow(Math.pow((player.getPositionX() - positionX), 2) + Math.pow((player.getPositionY() - positionY), 2), 0.5);
            if (distanceToLink <= 32) {
                isLinkNear = true;
            }
        }
    }

    public boolean isColliding(int nextX, int nextY) {
        Rectangle enemyCurrent = new Rectangle(nextX, nextY, 16, 16);
        if (gp.tm.getCurrentMap().getName().equals("Bottom Right")) {
            for(int i = 0; i < App.enemiesBottomRight.size(); i++) {
                EnemyMeele e = App.enemiesBottomRight.get(i);
                if (e == this) {
                    continue;
                }
                Rectangle targetEnemy = new Rectangle(e.getPositionX(), e.getPositionY(), 16, 16);
                if(enemyCurrent.intersects(targetEnemy)) {
                    return true;
                }
            }
        } else if (gp.tm.getCurrentMap().getName().equals("Top left")) {
            for(int i = 0; i < App.enemiesTopLeft.size(); i++) {
                EnemyMeele e = App.enemiesTopLeft.get(i);
                if (e == this) {
                    continue;
                }
                Rectangle targetEnemy = new Rectangle(e.getPositionX(), e.getPositionY(), 16, 16);
                if(enemyCurrent.intersects(targetEnemy)) {
                    return true;
                }
            }
        }
        Rectangle link = new Rectangle(player.getPositionX(), player.getPositionY(), 16, 16);
        if(enemyCurrent.intersects(link)) {
            return true;
        }

        return false;
    }

    public void hitLink(int nextX, int nextY) {
        Rectangle enemyCurrent = new Rectangle(nextX, nextY, 16, 16);
        Rectangle link = new Rectangle(player.getPositionX(), player.getPositionY(), 16, 16);
        if(enemyCurrent.intersects(link)) {
            player.getHit();
        }
    }

    public void getHit() {
        this.life -= 1;
        if (this.life == 0) this.player.score += this.scoreForKilling;
        this.player.score += 3;
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

    public void getMeeleEnemyImage() {
		try {
			up1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_up1.png"));
            up2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_up2.png"));
			down1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_down1.png"));
            down2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_down2.png"));
			left1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_left1.png"));
			left2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_left2.png"));
			right1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_right1.png"));
			right2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_right2.png"));
            death = ImageIO.read(new FileInputStream("src/assets/enemy_down.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
