package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import entities.Player;
import handlers.KeyHandler;
import java.io.IOException;
import java.io.File; // Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
// import java.awt.Color;
// import java.awt.Font; -> se for imprimir textos na tela

public class App extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final short SCALE = 1;
	private final short WIDTH = 240;
	private final short HEIGHT = 240;
	public final short originalTileSize = 16;
	public final short tileSize = originalTileSize * SCALE;
	private KeyHandler keyHandler;
	Player player;
	TileManager tm;

	public App() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		player = new Player(this, keyHandler);
		tm = new TileManager(this);
		tm.getMap();
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

	public int checkMapPosition(int line,int column){
		return tm.mapTiles[line][column];
	}
}

class TileManager {
	int[][] mapTiles = new int[15][16];
	App gp;

	public TileManager(App App) {
		this.gp = App;
	}

	public void getMap() {
		try {
			File map = new File("src/assets/map.txt");
			Scanner reader = new Scanner(map);
			int line = 0;
			while (reader.hasNextLine()) {
				String rawData = reader.nextLine();

				String charData[] = rawData.split(" ");

				ArrayList<Integer> codeArray = new ArrayList<Integer>();

				for (String c : charData) {
					codeArray.add(Integer.parseInt(c));
				}

				int column = 0;

				for (int c : codeArray) {
					mapTiles[line][column] = c;
					column++;
				}
				line++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void drawMap(Graphics g) {
		for (int line = 0; line < 15; line++) {
			for (int col = 0; col < 16; col++) {
				try {
					BufferedImage image = null;
					if (mapTiles[line][col] == 0) {
						image =ImageIO.read(new FileInputStream("src/assets/bush.png"));
					} else if (mapTiles[line][col] == 1) {
						image = ImageIO.read(new FileInputStream("src/assets/sand.png"));
					}
					g.drawImage(image, 16 * line, 16 * col, gp.tileSize, gp.tileSize, null);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}