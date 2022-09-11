package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.EnemyMeele;
import entities.Player;
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
	Player player;
	TileManager tm;

	public static ArrayList<EnemyMeele> enemiesBottomRight;

	public App() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		player = new Player(this, keyHandler);
		tm = new TileManager(this);
		System.out.println(tm.getCurrentMap());

		enemiesBottomRight = new ArrayList<EnemyMeele>();
		enemiesBottomRight.add(new EnemyMeele(192, 160, "vertical", player, this));
		enemiesBottomRight.add(new EnemyMeele(48, 160, "vertical", player, this));
		enemiesBottomRight.add(new EnemyMeele(80, 80, "horizontal", player, this));
		enemiesBottomRight.add(new EnemyMeele(80, 176, "horizontal", player, this));
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
		App game = new App();
		game.start();
	}

	public void update() {
		player.update();

		if (tm.getCurrentMap().getName().equals("Bottom Right")) {
			for(EnemyMeele enemies : enemiesBottomRight) {
				enemies.update();
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		tm.drawMap(g);
		player.draw(g);

		if (tm.getCurrentMap().getName().equals("Bottom Right")) {

			for(EnemyMeele enemies : enemiesBottomRight) {
				enemies.draw(g);
			}
		}

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
