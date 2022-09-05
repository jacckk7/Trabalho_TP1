package main;

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
