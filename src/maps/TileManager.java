package maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
import main.App;

public class TileManager {
	Map currentMap;
	App gp;
	ArrayList<BufferedImage> images;
	private String lastMapName;

	public TileManager(App App) {
		lastMapName = "";
		try {
			images = new ArrayList<BufferedImage>();
			images.add(ImageIO.read(new FileInputStream("src/assets/bush.png")));
			images.add(ImageIO.read(new FileInputStream("src/assets/sand.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public Map getCurrentMap() {
		return this.currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public App getGp() {
		return this.gp;
	}

	public void setGp(App gp) {
		this.gp = gp;
	}

	public void drawMap(Graphics g) {
		for (int line = 0; line < 15; line++) {
			for (int col = 0; col < 16; col++) {
				int tile = currentMap.getMapTiles()[line][col];
				g.drawImage(images.get(tile), 16 * col, 16 * line, gp.tileSize, gp.tileSize, null);
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

	public void attLastMap(){
		lastMapName = currentMap.getName();
	}

	public String getLastMapName() {
		return this.lastMapName;
	}

	public void setLastMapName(String e) {
		this.lastMapName = e;
	}
}
