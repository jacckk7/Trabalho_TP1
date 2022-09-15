package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { 

	private boolean upPressed, downPressed, leftPressed, rightPressed, kPressed, anyPressed;

	public KeyHandler() {
	}

	public void keyTyped(KeyEvent event) {

	}

	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		setAnyPressed(true);
		
		switch (keyCode) {
			case KeyEvent.VK_W:
				setUpPressed(true);
				break;
			case KeyEvent.VK_S:
				setDownPressed(true);
				break;
			case KeyEvent.VK_A:
				setLeftPressed(true);
				break;
			case KeyEvent.VK_D:
				setRightPressed(true);
				break;
			case KeyEvent.VK_K:
				setKPressed(true);
				break;
		}

	}

	public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();
		setAnyPressed(false);

		switch (keyCode) {
			case KeyEvent.VK_W:
			setUpPressed(false);
			break;
		case KeyEvent.VK_S:
			setDownPressed(false);
			break;
		case KeyEvent.VK_A:
			setLeftPressed(false);
			break;
		case KeyEvent.VK_D:
			setRightPressed(false);
			break;
		case KeyEvent.VK_K:
			setKPressed(false);
			break;
		}
	}


	public boolean isUpPressed() {
		return this.upPressed;
	}

	public boolean getUpPressed() {
		return this.upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return this.downPressed;
	}

	public boolean getDownPressed() {
		return this.downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return this.leftPressed;
	}

	public boolean getLeftPressed() {
		return this.leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return this.rightPressed;
	}

	public boolean getRightPressed() {
		return this.rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isKPressed() {
		return this.kPressed;
	}

	public boolean getKPressed() {
		return this.kPressed;
	}

	public void setKPressed(boolean kPressed) {
		this.kPressed = kPressed;
	}

	public boolean isAnyPressed() {
		return this.anyPressed;
	}

	public boolean getAnyPressed() {
		return this.anyPressed;
	}

	public void setAnyPressed(boolean anyPressed) {
		this.anyPressed = anyPressed;
	}


}
