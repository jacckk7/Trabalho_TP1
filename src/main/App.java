package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
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
	private final short WIDTH = 256;
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
		System.out.println(tm.currentMap);
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

	public int checkMapPosition(int line, int column) {
		if (line < 0) {
			tm.changeMap("above");
			System.out.println("Map changed:");
			System.out.println(tm.currentMap);
			player.setPositionY(224);
			return 1;
		}
		if (column < 0) {
			tm.changeMap("besideLeft");
			System.out.println("Map changed:");
			System.out.println(tm.currentMap);
			player.setPositionX(224);
			return 1;
		}
		if (line > 14) {
			tm.changeMap("below");
			System.out.println("Map changed:");
			System.out.println(tm.currentMap);
			player.setPositionY(0);
			return 1;
		}
		if (column > 15) {
			tm.changeMap("besideRight");
			System.out.println("Map changed:");
			System.out.println(tm.currentMap);
			player.setPositionX(0);
			return 1;
		}
		return tm.currentMap.mapTiles[line][column];
	}

}

class TileManager {
	Map currentMap;
	App gp;

	public TileManager(App App) {
		this.gp = App;
		currentMap = new Map("Bottom left");
		currentMap.setMap(new File("src/assets/maps/mapBottomLeft.txt"));

		Map topRight = new Map("Top Right");
		topRight.setMap(new File("src/assets/maps/mapTopRight.txt"));

		Map topLeft = new Map("Top left");
		topLeft.setMap(new File("src/assets/maps/mapTopLeft.txt"));

		Map bottomRight = new Map("Bottom Right");
		bottomRight.setMap(new File("src/assets/maps/mapBottomRight.txt"));

		currentMap.setAbove(topLeft);
		currentMap.setBesideRight(bottomRight);

		bottomRight.setBesideLeft(currentMap);
		bottomRight.setAbove(topRight);

		topRight.setBelow(bottomRight);
		topRight.setBesideLeft(topLeft);

		topLeft.setBesideRight(topRight);
		topLeft.setBelow(currentMap);

	}

	public void drawMap(Graphics g) {
		for (int line = 0; line < 15; line++) {
			for (int col = 0; col < 16; col++) {
				try {
					BufferedImage image = null;
					if (currentMap.mapTiles[line][col] == 0) {
						image = ImageIO.read(new FileInputStream("src/assets/bush.png"));
					} else if (currentMap.mapTiles[line][col] == 1) {
						image = ImageIO.read(new FileInputStream("src/assets/sand.png"));
					}
					g.drawImage(image, 16 * col, 16 * line, gp.tileSize, gp.tileSize, null);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void changeMap(String direction) {
		switch (direction) {
			case "above":
				currentMap = currentMap.getAbove();
				break;
			case "below":
				currentMap = currentMap.getBelow();
				break;
			case "besideLeft":
				currentMap = currentMap.getBesideLeft();
				break;
			case "besideRight":
				System.out.print("This was requested/n");
				this.currentMap = this.currentMap.getBesideRight();
				break;
		}
	}
}

class Map {
	int[][] mapTiles = new int[15][16];
	String name;
	Map above;
	Map besideLeft;
	Map below;
	Map besideRight;

	public Map(int[][] mapTiles, Map above, Map besideLeft, Map below, Map besideRight) {
		this.mapTiles = mapTiles;
		this.above = above;
		this.besideLeft = besideLeft;
		this.below = below;
		this.besideRight = besideRight;
	}

	public Map(String s) {
		this.name = s;
		above = null;
		below=null;
		besideLeft=null;
		besideRight=null;
	}

	public int[][] getMapTiles() {
		return this.mapTiles;
	}

	public void setMapTiles(int[][] mapTiles) {
		this.mapTiles = mapTiles;
	}

	public Map getAbove() {
		return this.above;
	}

	public void setAbove(Map above) {
		this.above = above;
	}

	public Map getBesideLeft() {
		return this.besideLeft;
	}

	public void setBesideLeft(Map besideLeft) {
		this.besideLeft = besideLeft;
	}

	public Map getBelow() {
		return this.below;
	}

	public void setBelow(Map below) {
		this.below = below;
	}

	public Map getBesideRight() {
		return this.besideRight;
	}

	public void setBesideRight(Map besideRight) {
		this.besideRight = besideRight;
	}

	public void setMap(File map) {
		try {
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

	@Override
	public String toString(){
		String ans= "Name: " +this.name+"\n";
		if(below!=null){
			ans+="below: "+below.name+"\n";
		}
		if(besideLeft!=null){
			ans+="besideLeft: "+besideLeft.name+"\n";
		}
		if(besideRight!=null){
			ans+="besideRight: "+besideRight.name+"\n";
		}
		if(above!=null){
			ans+="above: "+above.name+"\n";
		}
		return ans;
	} 

};