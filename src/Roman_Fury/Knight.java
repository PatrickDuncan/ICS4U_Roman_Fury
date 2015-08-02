package Roman_Fury;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//This class loads all of the attributes for the knight
public class Knight {

    private final int POSITIONX, POSITIONY;
    private int health = 150, attackNum = 500, blockNum, stateNum = 1, imageNum = 1;
    private static boolean isAttack, isHeroAtk, isBlock;
    private BufferedImage bImage;
    private final BufferedImage portrait;
    private final static BufferedImage[][] iamgeArray = new BufferedImage[3][3];
    private Rectangle recHealth;
    private final Hero hero;
    private final static Timer DELAY = new Timer();
    private DelayTask delayTask;
    private AudioInputStream AISAttack;

    public Knight() throws Exception {
        hero = new Hero();
        iamgeArray[1][1] = ImageIO.read(getClass().getResourceAsStream("/knightleft.png"));
        iamgeArray[2][1] = ImageIO.read(getClass().getResourceAsStream("/knightleftattack.png"));
        iamgeArray[2][2] = ImageIO.read(getClass().getResourceAsStream("/knightleftblock.png"));
        bImage = iamgeArray[stateNum][imageNum];
        portrait = ImageIO.read(getClass().getResourceAsStream("/knightportrait.png"));
        POSITIONX = 1000;
        POSITIONY = 451;
    }

    public int getX() {
        return POSITIONX;
    }

    public int getY() {
        return POSITIONY;
    }

    public int getHealth() {
        return health;
    }

    public int getState() {
        return stateNum;
    }

    public BufferedImage getImage() {
        Action();
        if (isHeroAtk) {
            blockNum++;
            if (blockNum == 30) {
                blockNum = 0;
                isBlock = true;
                stateNum = imageNum = 2;
            }
        }
        bImage = iamgeArray[stateNum][imageNum];
        return bImage;
    }

    public boolean getAttack() {
        return isAttack;
    }

    public boolean getBlock() {
        return isBlock;
    }

    public Rectangle getBounds() {
        if (stateNum == 1 && imageNum == 1) {
            return new Rectangle(POSITIONX + 50, POSITIONY, bImage.getWidth() - 50, bImage.getHeight());
        } else if (stateNum == 2 && imageNum == 1) {
            return new Rectangle(POSITIONX, POSITIONY, bImage.getWidth(), bImage.getHeight());
        } else {
            return new Rectangle(POSITIONX + 58, POSITIONY, bImage.getWidth() - 50, bImage.getHeight());
        }
    }

    public void setHealth(int health) {
        this.health -= health;
        Block();
    }

    public void setAttack(boolean bool) {
        isAttack = bool;
    }

    public void Restart() {
        health = 150;
        attackNum = 500;
    }

    public void KnightHealth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(portrait, 0, 50, null);
        recHealth = new Rectangle(50, 65, health, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void Action() {
        if (hero.getX() > 850) {
            if (attackNum == 550) {
                stateNum = 2;
                imageNum = 1;
                isAttack = true;
                delayTask = new DelayTask();
                DELAY.schedule(delayTask, 0, 2500);
                attackNum = 0;
                try {
                    Clip clipAttack = AudioSystem.getClip();
                    AISAttack = AudioSystem.getAudioInputStream(getClass().getResource("/StrongKnight.wav"));
                    clipAttack.open(AISAttack);
                    clipAttack.start();
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                }
            }
            attackNum++;
        }
    }

    public void Block() {
        isHeroAtk = true;
        delayTask = new DelayTask();
        DELAY.schedule(delayTask, 0, 2500);
    }

    //http://www.javaprogrammingforums.com/java-se-api-tutorials/883-how-use-DELAY-java.html
    class DelayTask extends TimerTask {

        public int nTimes = 0;

        @Override
        public void run() {
            nTimes++;
            if (nTimes == 2) {
                stateNum = imageNum = 1;
                isAttack = isBlock = isHeroAtk = false;
                hero.setPush(false);
            }
        }
    }
}
