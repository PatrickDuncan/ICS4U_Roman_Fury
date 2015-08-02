package Roman_Fury;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//This class loads all the atributes for one of the sorcerers.
public class Sorcerer1 {

    private final int POSITIONX, POSITIONY;
    private int attackNum = 1299, health = 50, changeNum = 0, stateNum = 1, imageNum = 1;
    private final int ATTACKSPEED = 1300;
    private static boolean isAttack;
    private BufferedImage bImage;
    private final BufferedImage portrait;
    private final static BufferedImage[][] arrayImage = new BufferedImage[3][2];
    private Rectangle recHealth;

    public Sorcerer1() throws IOException {
        arrayImage[1][1] = ImageIO.read(getClass().getResourceAsStream("/sorcerer1.png"));
        arrayImage[2][1] = ImageIO.read(getClass().getResourceAsStream("/sorcereratk1.png"));
        bImage = arrayImage[stateNum][imageNum];
        portrait = ImageIO.read(getClass().getResourceAsStream("/sorcerer1portrait.png"));
        POSITIONX = 1140;
        POSITIONY = 508;
    }

    public int getX() {
        return POSITIONX;
    }

    public int getY() {
        return POSITIONY;
    }

    public BufferedImage getImage() {
        Attack();
        bImage = arrayImage[stateNum][imageNum];
        return bImage;
    }

    public boolean getAttack() {
        return isAttack;
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getBounds() {
        return new Rectangle(POSITIONX + 15, POSITIONY, bImage.getWidth() - 10, bImage.getHeight());
    }

    public void setHealth(int health) {
        this.health -= health;
    }
    
    public void setAttack(int num) {
        attackNum = num;
    }
    
    public void setChange() {
        changeNum = 0;
    }

    public void Health(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(portrait, 0, 50, null);
        recHealth = new Rectangle(50, 65, health, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void Attack() {
        attackNum++;
        isAttack = false;
        if (attackNum > (int) (Math.random() * ATTACKSPEED * 2 + ATTACKSPEED)) {
            stateNum = 2;
            imageNum = 1;
            isAttack = true;
            attackNum = 0;
        }
        if (changeNum == 150) {
            stateNum = imageNum = 1;
            changeNum = 0;
        }
        changeNum++;
    }

    public void Restart() {
        health = 50;
        changeNum = 0;
        stateNum = imageNum = 1;
        isAttack = false;
    }
}