package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // coloquei aqui porque estav tendo erro "cant find symbol" quando declarava
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