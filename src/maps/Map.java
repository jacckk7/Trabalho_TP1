package maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	public int[][] mapTiles = new int[15][16];
	public String name;
	public Map above;
	public Map besideLeft;
	public Map below;
    public Map besideRight;

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

	public String getName() {
		return name;
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