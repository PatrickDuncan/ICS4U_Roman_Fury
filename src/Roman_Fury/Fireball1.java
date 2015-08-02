package Roman_Fury;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//This class loads all of the attributes for one of the fireballs
public class Fireball1 {

    private int positionX;
    private final int POSITIONY;
    private final BufferedImage bImage;
    private boolean visible;

    public Fireball1() throws IOException {
        bImage = ImageIO.read(getClass().getResourceAsStream("/fireball1.png"));
        positionX = 1120;
        POSITIONY = 565;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return POSITIONY;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, POSITIONY, bImage.getWidth(), bImage.getHeight());
    }

    public BufferedImage getImage() {
        if (positionX < -30 || positionX > 1250) {
            visible = false;
            positionX = 1120;
        }
        return bImage;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setX(int x) {
        positionX = x;
    }

    public boolean isVisible() {
        return visible;
    }

    public void move() {
        positionX -= 1;
    }

    public void Restart() {
        positionX = 1120;
    }
}
