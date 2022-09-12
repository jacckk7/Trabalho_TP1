package entities;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hearts {
    private BufferedImage full, tresQuartos, umMeio, umQuarto;
    private int positionX = 16, positionY = 224;
    private int life;
    Player player;

    public Hearts(Player player) {
        this.player = player;
        getHeartImage();
    }

    public void update() {
        life = player.getLife();
    }

    public void draw(Graphics g) {
        BufferedImage image1 = null, image2 = null, image3 = null;

        if (life > 0) {
            if (life < 5) {
                if (life % 4 == 0) {
                    image1 = full;
                    g.drawImage(image1, positionX, positionY, null);
                } else if (life % 4 == 3) {
                    image1 = tresQuartos;
                    g.drawImage(image1, positionX, positionY, null);
                } else if (life % 4 == 2) {
                    image1 = umMeio;
                    g.drawImage(image1, positionX, positionY, null);
                } else if (life % 4 == 1) {
                    image1 = umQuarto;
                    g.drawImage(image1, positionX, positionY, null);
                }
            } else if (life < 9) {
                image1 = full;
                g.drawImage(image1, positionX, positionY, null);
                if (life % 4 == 0) {
                    image2 = full;
                    g.drawImage(image2, positionX + 7, positionY, null);
                } else if (life % 4 == 3) {
                    image2 = tresQuartos;
                    g.drawImage(image2, positionX + 7, positionY, null);
                } else if (life % 4 == 2) {
                    image2 = umMeio;
                    g.drawImage(image2, positionX + 7, positionY, null);
                } else if (life % 4 == 1) {
                    image2 = umQuarto;
                    g.drawImage(image2, positionX + 7, positionY, null);
                }
            } else {
                image1 = full;
                g.drawImage(image1, positionX, positionY, null);
                image2 = full;
                g.drawImage(image2, positionX + 7, positionY, null);
                if (life % 4 == 0) {
                    image3 = full;
                    g.drawImage(image3, positionX + 14, positionY, null);
                } else if (life % 4 == 3) {
                    image3 = tresQuartos;
                    g.drawImage(image3, positionX + 14, positionY, null);
                } else if (life % 4 == 2) {
                    image3 = umMeio;
                    g.drawImage(image3, positionX + 14, positionY, null);
                } else if (life % 4 == 1) {
                    image3 = umQuarto;
                    g.drawImage(image3, positionX + 14, positionY, null);
                }
            }
        }
    }

    public void getHeartImage() {
		try {
			full = ImageIO.read(new FileInputStream("src/assets/heart_full.png"));
			tresQuartos = ImageIO.read(new FileInputStream("src/assets/heart_3_4.png"));
			umMeio = ImageIO.read(new FileInputStream("src/assets/heart_1_2.png"));
			umQuarto = ImageIO.read(new FileInputStream("src/assets/heart_1_4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
