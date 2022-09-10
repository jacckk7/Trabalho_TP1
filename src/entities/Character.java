package entities;

public class Character {
	public int life;
	public int positionX;
	public int positionY;
	public double speed;
	public String direction;
	public int scoreForKilling;
	public int spriteCounter;
	public int sprinteNum;

	public Character(int positionX, int positionY, double speed, String direction) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.direction = direction;
		this.spriteCounter=0;
		this.sprinteNum=1;
	}


	public int getLife() {
		return this.life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPositionX() {
		return this.positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return this.positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getScoreForKilling() {
		return this.scoreForKilling;
	}

	public void setScoreForKilling(int scoreForKilling) {
		this.scoreForKilling = scoreForKilling;
	}

	public String getInLineDirection(Character other){
		if(other.positionY+16 >= positionY && other.positionY<=positionY+16){ // está na mesma direção horizontal
			if(other.positionX<=positionX){
				return "left";
			}else{
				return "right";
			}
		}else{
			if(other.positionX+16 >= positionX && other.positionX<=positionX+16){ //está na mesma direção vertica;
				if(other.positionY<=positionY){
					return "up";
				}else{
					return "down";
				}
			}
		}
		return null;
	}

}
