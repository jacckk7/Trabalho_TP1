package main;
import java.awt.Graphics;

public class Player extends Character{
    GamePanel gp;
    KeyHandler kh;

    public Player(GamePanel gp, KeyHandler kh) {
        super(0,0,4.0,"down");
        this.gp = gp;
        this.kh = kh;
    }

    public void update(){
        if (kh.upPressed) {
			positionY -= speed;
			return;
		}
		if (kh.downPressed) {
			positionY += speed;
			return;
		}
		if (kh.leftPressed) {
			positionX -= speed;
			return;
		}
		if (kh.rightPressed) {
			positionX += speed;
			return;
		}
    }

    public void draw(Graphics g){

    }

}
