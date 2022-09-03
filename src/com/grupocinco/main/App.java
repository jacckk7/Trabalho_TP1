package com.grupocinco.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// import java.awt.Font; -> se for imprimir textos na tela

public class App extends Canvas implements Runnable {
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final short WIDTH = 960;
	private final short HEIGHT = 720;
	private final short SCALE = 1;
	private static short playerX =0; // feito para testar keyHandler. (pode/deve) estar dentro e classe Link
	private static short playerY =0; // feito para testar keyHandler. (pode/deve) estar dentro e classe Link
	private final short playerSpeed=3; // feito para testar keyHandler. (pode/deve) estar dentro e classe Link
	private BufferedImage image;
	private KeyHandler keyHandler;

	public App() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
		if(keyHandler.upPressed){
			playerY-=playerSpeed;
			System.out.printf("Player X: %d,PLayerY %d",playerX,playerY);
			return;
		}
		if(keyHandler.downPressed){
			playerY+=playerSpeed;
			System.out.printf("Player X: %d,PLayerY %d",playerX,playerY);
			return;
		}
		if(keyHandler.leftPressed){
			playerX-=playerSpeed;
			System.out.printf("Player X: %d,PLayerY %d",playerX,playerY);
			return;
		}
		if(keyHandler.rightPressed){
			playerX+=playerSpeed;
			System.out.printf("Player X: %d,PLayerY %d",playerX,playerY);
			return;
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();

				render();
				
				frames++;
				delta = 0;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
}

 
class KeyHandler implements KeyListener {  //coloquei aqui porque estav tendo erro "cant find symbol" quando declarava em outro arquivo

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