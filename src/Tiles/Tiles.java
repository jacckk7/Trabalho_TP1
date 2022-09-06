package Tiles;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Tiles {
    public BufferedImage bush,sand;
    public boolean colision;

    public Tiles() {
        getTileImage();
    }

    public void getTileImage(){
        try{
            bush = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));
            sand = ImageIO.read(getClass().getResourceAsStream("/tiles/SAND.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
