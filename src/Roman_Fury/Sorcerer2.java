package Roman_Fury;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//This class loads all the atributes for one of the sorcerers.
public class Sorcerer2 {

    private final int POSITIONX, POSITIONY;
    private int attackNum = 900, healthNum = 50, changeNum = 0, stateNum = 1, imageNum = 1;
    private final int nAtkSpeed = 1300;
    private static boolean isAttack;
    private BufferedImage BImgSor2;
    private final BufferedImage BImgSor2Portrait;
    private final static BufferedImage[][] arBImgSor = new BufferedImage[3][2];
    private Rectangle recHealth;

    public Sorcerer2() throws IOException {
        arBImgSor[1][1] = ImageIO.read(getClass().getResourceAsStream("/sorcerer2.png"));
        arBImgSor[2][1] = ImageIO.read(getClass().getResourceAsStream("/sorcereratk2.png"));
        BImgSor2 = arBImgSor[stateNum][imageNum];
        BImgSor2Portrait = ImageIO.read(getClass().getResourceAsStream("/sorcerer2portrait.png"));
        POSITIONX = 40;
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
        BImgSor2 = arBImgSor[stateNum][imageNum];
        return BImgSor2;
    }

    public boolean getAttack() {
        return isAttack;
    }

    public int getHealth() {
        return healthNum;
    }

    public Rectangle getBounds() {
        return new Rectangle(POSITIONX - 20, POSITIONY, BImgSor2.getWidth(), BImgSor2.getHeight());
    }

    public void setHealth(int health) {
        healthNum -= health;
    }
    
    public void setAttack(int num) {
        attackNum = num;
    }
    
    public void setChange() {
        changeNum = 0;
    }

    public void Health(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(BImgSor2Portrait, 0, 100, null);
        recHealth = new Rectangle(50, 115, healthNum, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void Attack() {
        attackNum++;
        isAttack = false;
        if (attackNum > (int) (Math.random() * nAtkSpeed * 2 + nAtkSpeed)) {
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
        healthNum = 50;
        changeNum = 0;
        stateNum = imageNum = 1;
        isAttack = false;
    }
}