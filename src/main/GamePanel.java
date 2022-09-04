package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

// import java.awt.Font; -> se for imprimir textos na tela

public class GamePanel extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final short SCALE = 1;
	private final short WIDTH = 960;
	private final short HEIGHT = 720;
	public final short originalTileSize = 16;
	public final short tileSize = originalTileSize * SCALE;
	private KeyHandler keyHandler;
	Player player;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		player = new Player(this, keyHandler);
	}

	public void initFrame() {
		frame = new JFrame("Zelda");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GamePanel game = new GamePanel();
		game.start();
	}

	public void update() {
		player.update();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.draw(g);
		bs.show();
		g.dispose();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();

				render();

				frames++;
				delta = 0;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}
}

class KeyHandler implements KeyListener { // coloquei aqui porque estav tendo erro "cant find symbol" quando declarava
											// em outro arquivo

	public boolean upPressed, downPressed, leftPressed, rightPressed;

	public KeyHandler() {
	}

	public void keyTyped(KeyEvent event) {

	}

	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_W:
				upPressed = true;
				break;
			case KeyEvent.VK_S:
				downPressed = true;
				break;
			case KeyEvent.VK_A:
				leftPressed = true;
				break;
			case KeyEvent.VK_D:
				rightPressed = true;
				break;
		}

	}

	public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_W:
				upPressed = false;
				break;
			case KeyEvent.VK_S:
				downPressed = false;
				break;
			case KeyEvent.VK_A:
				leftPressed = false;
				break;
			case KeyEvent.VK_D:
				rightPressed = false;
				break;
		}
	}

}

class Character {
	public int life;
	public int positionX;
	public int positionY;
	public double speed;
	public String direction;
	public int scoreForKilling;

	public Character(int positionX, int positionY, double speed, String direction) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.direction = direction;
	}

}

class Player extends Character {
	GamePanel gp;
	KeyHandler kh;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

	public Player(GamePanel gp, KeyHandler kh) {
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
			up1 = ImageIO.read(getClass().getResourceAsStream("/link_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/link_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/link_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/link_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/link_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/link_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/link_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/link_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
