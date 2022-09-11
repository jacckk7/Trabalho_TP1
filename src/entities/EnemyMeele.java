package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.App;

public class EnemyMeele extends Character {

    public BufferedImage up, down, right1, right2, left1, left2;
    App gp;
    Player player;
    private boolean isLinkNear = false;
    private String movement;
    private int initial_positionX;
    private int initial_positionY;
    private boolean countSprite = false;

    public EnemyMeele(int positionX, int positionY, String movement, Player player, App gp) {
        super(positionX, positionY, 1.0, "up");
        this.movement = movement;
        this.initial_positionX = positionX;
        this.initial_positionY = positionY;
        this.player = player;
        this.gp = gp;
        if(movement.equals("horizontal")) {
            super.direction = "right";
        }
        getMeeleEnemyImage();
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        if (direction.equals("up")) {
            image = up;
        } else if (direction.equals("down")) {
            image = down;
        } else if (direction.equals("right") && countSprite) {
            image = right2;
        } else if (direction.equals("right")) {
            image = right1;
        } else if (direction.equals("left") && countSprite) {
            image = left2;
        } else if (direction.equals("left")) {
            image = left1;
        }

        g.drawImage(image, positionX, positionY, null);
    }

    public void update() {
        if (!isLinkNear) {
            if (movement.equals("vertical")) {
                if (positionY == initial_positionY - 64 && direction.equals("up")) {
                    direction = "down";
                    positionY += (int)speed;
                } else if (direction.equals("up")) {
                    positionY -= (int)speed;
                } else if (positionY == initial_positionY && direction.equals("down")) {
                    direction = "up";
                    positionY -= (int)speed;
                } else if (direction.equals("down")) {
                    positionY += (int)speed;
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
                direction = "down";
            } else if (positionY > player.getPositionY() && gp.checkMapPosition((int) (positionX /16), (int) (Math.floor((positionY - speed) / 16))) == 1 && !isColliding(positionX, (int) (positionY - speed))) {
                positionY -= (int)speed;
                direction = "up";
            }
            
            if (positionX < player.getPositionX() && gp.checkMapPosition((int) (Math.ceil((positionX + speed) / 16)), (int) Math.floor((positionY / 16))) == 1 && !isColliding((int)  (positionX + speed), positionY)) {
                positionX += (int)speed;
                countSprite = !countSprite;
                direction = "right";
            } else if (positionX > player.getPositionX() && gp.checkMapPosition((int) (Math.floor((positionX - speed) / 16)), (int) (positionY / 16)) == 1 && !isColliding((int) (positionX - speed), positionY)) {
                positionX -= (int)speed;
                countSprite = !countSprite;
                direction = "left";
            }
        }

        double distanceToLink = Math.pow(Math.pow((player.getPositionX() - positionX), 2) + Math.pow((player.getPositionY() - positionY), 2), 0.5);
        if (distanceToLink <= 32) {
            isLinkNear = true;
        }
    }

    public boolean isColliding(int nextX, int nextY) {
        Rectangle enemyCurrent = new Rectangle(nextX, nextY, 16, 16);
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

        return false;
    }

    public void getMeeleEnemyImage() {
		try {
			up = ImageIO.read(new FileInputStream("src/assets/meele_enemy_up.png"));
			down = ImageIO.read(new FileInputStream("src/assets/meele_enemy_down.png"));
			left1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_left1.png"));
			left2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_left2.png"));
			right1 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_right1.png"));
			right2 = ImageIO.read(new FileInputStream("src/assets/meele_enemy_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
