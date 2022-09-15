package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import entities.EnemyMeele;
import entities.EnemyRanged;
import entities.Player;
import entities.Hearts;
import handlers.KeyHandler;
import maps.TileManager;

public class App extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final short SCALE = 1;
	private final short WIDTH = 256;
	private final short HEIGHT = 240;
	public final short originalTileSize = 16;
	public final short tileSize = originalTileSize * SCALE;
	private KeyHandler keyHandler;
	private BufferedImage gameOver;
	public TileManager tm;
	Player player;
	Hearts hearts;

	public static ArrayList<EnemyRanged> enemiesBottomLeft;
	public static ArrayList<EnemyMeele> enemiesBottomRight;
	public static ArrayList<EnemyRanged> enemiesTopRight;
	public static ArrayList<EnemyMeele> enemiesTopLeft;

	public App() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		player = new Player(this, keyHandler);
		hearts = new Hearts(player);

		tm = new TileManager(this);
		System.out.println(tm.getCurrentMap());

		enemiesBottomLeft = new ArrayList<EnemyRanged>();
		enemiesBottomLeft.add(new EnemyRanged(192, 176, "vertical", player, this));
		enemiesBottomLeft.add(new EnemyRanged(32, 32, "horizontal", player, this));
		enemiesBottomLeft.add(new EnemyRanged(32, 192, "horizontal", player, this));

		enemiesBottomRight = new ArrayList<EnemyMeele>();
		enemiesBottomRight.add(new EnemyMeele(192, 160, "vertical", player, this));
		enemiesBottomRight.add(new EnemyMeele(48, 160, "vertical", player, this));
		enemiesBottomRight.add(new EnemyMeele(80, 80, "horizontal", player, this));
		enemiesBottomRight.add(new EnemyMeele(80, 176, "horizontal", player, this));

		enemiesTopRight = new ArrayList<EnemyRanged>();
		enemiesTopRight.add(new EnemyRanged(208, 160, "vertical", player, this));
		enemiesTopRight.add(new EnemyRanged(32, 176, "vertical", player, this));
		enemiesTopRight.add(new EnemyRanged(32, 48, "horizontal", player, this));

		enemiesTopLeft = new ArrayList<EnemyMeele>();
		enemiesTopLeft.add(new EnemyMeele(16, 144, "vertical", player, this));
		enemiesTopLeft.add(new EnemyMeele(208, 144, "vertical", player, this));
		enemiesTopLeft.add(new EnemyMeele(32, 192, "horizontal", player, this));
		enemiesTopLeft.add(new EnemyMeele(128, 32, "horizontal", player, this));

		try {
			gameOver = ImageIO.read(new FileInputStream("src/assets/game_over.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		MainMenu menu = new MainMenu();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(1000, 1000);
		menu.setResizable(false);
		menu.pack();
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
	}

	public void update() {
		player.update();
		int getIndex = -1;
		if (tm.getCurrentMap().getName().equals("Bottom left")) {
			for (int i = 0; i < enemiesBottomLeft.size(); i++) {
				if (enemiesBottomLeft.get(i).getLife() < 0) {
					getIndex = i;
				}
				enemiesBottomLeft.get(i).update();
			}
			if (getIndex != -1) {
				enemiesBottomLeft.remove(getIndex);
				getIndex = -1;
			}
		} else if (tm.getCurrentMap().getName().equals("Bottom Right")) {
			for (int i = 0; i < enemiesBottomRight.size(); i++) {
				if (enemiesBottomRight.get(i).getLife() < 0) {
					getIndex = i;
				}
				enemiesBottomRight.get(i).update();
			}
			if (getIndex != -1) {
				enemiesBottomRight.remove(getIndex);
				getIndex = -1;
			}
		} else if (tm.getCurrentMap().getName().equals("Top Right")) {
			for (int i = 0; i < enemiesTopRight.size(); i++) {
				if (enemiesTopRight.get(i).getLife() < 0) {
					getIndex = i;
				}
				enemiesTopRight.get(i).update();
			}
			if (getIndex != -1) {
				enemiesTopRight.remove(getIndex);
				getIndex = -1;
			}
		} else if (tm.getCurrentMap().getName().equals("Top left")) {
			for (int i = 0; i < enemiesTopLeft.size(); i++) {
				if (enemiesTopLeft.get(i).getLife() < 0) {
					getIndex = i;
				}
				enemiesTopLeft.get(i).update();
			}
			if (getIndex != -1) {
				enemiesTopLeft.remove(getIndex);
				getIndex = -1;
			}
		}
		hearts.update();
	}

	

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (player.getLife() > 0) {
			tm.drawMap(g);
			player.draw(g);

			if (tm.getCurrentMap().getName().equals("Bottom left")) {
				for (EnemyRanged enemies : enemiesBottomLeft) {
					enemies.draw(g);
				}
			} else if (tm.getCurrentMap().getName().equals("Bottom Right")) {
				for (EnemyMeele enemies : enemiesBottomRight) {
					enemies.draw(g);
				}
			} else if (tm.getCurrentMap().getName().equals("Top Right")) {
				for (EnemyRanged enemies : enemiesTopRight) {
					enemies.draw(g);
				}
			} else if (tm.getCurrentMap().getName().equals("Top left")) {
				for (EnemyMeele enemies : enemiesTopLeft) {
					enemies.draw(g);
				}
			}

			hearts.draw(g);
		} else {
			g.drawImage(gameOver, 0, 0, null);
			if (keyHandler.anyPressed) {
				main(null);
				stop();
				frame.dispose();
			}
		}

		bs.show();
		g.dispose();

	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 30.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();

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

	public int checkMapPosition(int line, int column) {
		if (line < 0) {
			tm.attLastMap();
			tm.changeMap("above");
			System.out.println("Map changed:");
			System.out.println(tm.getCurrentMap());
			player.setPositionY(224);
			return 1;
		}
		if (column < 0) {
			tm.changeMap("besideLeft");
			System.out.println("Map changed:");
			System.out.println(tm.getCurrentMap());
			player.setPositionX(224);
			return 1;
		}
		if (line > 14) {
			tm.changeMap("below");
			System.out.println("Map changed:");
			System.out.println(tm.getCurrentMap());
			player.setPositionY(0);
			return 1;
		}
		if (column > 15) {
			tm.changeMap("besideRight");
			System.out.println("Map changed:");
			System.out.println(tm.getCurrentMap());
			player.setPositionX(0);
			return 1;
		}
		return tm.getCurrentMap().mapTiles[line][column];
	}

}
