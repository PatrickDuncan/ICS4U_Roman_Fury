package Roman_Fury;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//This class loads all of the attributes for the boss
public class Boss {

    private final int POSITIONX, POSITIONY;
    private int health = 200, beamNum1 = 860, beamNum2 = 0, stateNum, imageNum,
            visibleNum, delayNum, blastNum, counter;
    private static boolean isBeam = true, isGrow = true, isBlast;
    private BufferedImage bImage;
    private final BufferedImage portrait;
    private final static BufferedImage[][] imageArray = new BufferedImage[3][3];
    public Rectangle recHealth, recBeam;
    private final Hero hero;
    private AudioInputStream AISBeam;

    public Boss() throws Exception {
        hero = new Hero();
        stateNum = imageNum = visibleNum = delayNum = 1;
        imageArray[1][1] = ImageIO.read(getClass().getResourceAsStream("/bossleft.png"));
        imageArray[2][1] = ImageIO.read(getClass().getResourceAsStream("/bossleftatk1.png"));
        imageArray[2][2] = ImageIO.read(getClass().getResourceAsStream("/bossleftatk2.png"));
        bImage = imageArray[stateNum][imageNum];
        portrait = ImageIO.read(getClass().getResourceAsStream("/bossportrait.png"));
        POSITIONX = 770;
        POSITIONY = 479;
    }

    public int getX() {
        if (hero.getX() > 619 && hero.getX() < 700) {
            stateNum = imageNum = 1;
            blastNum = 0;
        }
        return POSITIONX;
    }

    public int getY() {
        return POSITIONY;
    }

    public int getHealth() {
        return health;
    }

    public int getDelay() {
        return delayNum;
    }

    public int getVisible() {
        return visibleNum;
    }

    public BufferedImage getImage() {
        try {
            Clip clipBeam = AudioSystem.getClip();
            AISBeam = AudioSystem.getAudioInputStream(getClass().getResource("/Fireball.wav"));
            if (beamNum2 == 1 && stateNum == 2 && imageNum == 1) {
                clipBeam.open(AISBeam);
                clipBeam.start();
            } else {
                clipBeam.stop();
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
        if (counter != 0) {
            stateNum = imageNum = 2;
        }
        if (stateNum == 2 && imageNum == 2) {
            counter++;
            if (counter > 100) {
                counter = 0;
            }
        }
        bImage = imageArray[stateNum][imageNum];
        return bImage;
    }

    public boolean getBeam() {
        return isBeam;
    }

    public boolean getBlast() {
        return isBlast;
    }

    public Rectangle getBounds() {
        if (stateNum == 1 && imageNum == 1) {
            return new Rectangle(POSITIONX + 40, POSITIONY, bImage.getWidth(), bImage.getHeight());
        } else if (stateNum == 2 && imageNum == 1) {
            return new Rectangle(POSITIONX + 40, POSITIONY, bImage.getWidth(), bImage.getHeight());
        } else {
            return new Rectangle(POSITIONX, POSITIONY, bImage.getWidth(), bImage.getHeight());
        }
    }

    public void setHealth(int health) {
        this.health -= health;
    }

    public void setBeam(boolean beam) {
        isBeam = beam;
    }

    public void setGrow(boolean grow) {
        isGrow = grow;
    }

    public void setBlast(boolean blast) {
        isBlast = blast;
    }

    public void Restart() {
        health = 200;
        beamNum1 = 860;
        beamNum2 = 0;
        isGrow = isBeam = true;
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

    public void Beam(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (visibleNum < 860) {
            recBeam = new Rectangle(beamNum1, 562, beamNum2, 23);
            g.setColor(Color.yellow);
            g2.fill(recBeam);
            g.setColor(Color.black);
            g2.draw(recBeam);
            if (isGrow) {
                beamNum1--;
                beamNum2++;
            }
            visibleNum++;
            stateNum = 2;
            imageNum = 1;
        } else {
            delayNum++;
            if (delayNum >= 350) {
                visibleNum = delayNum = 1;
                beamNum1 = 860;
                beamNum2 = 1;
            }
            stateNum = imageNum = 1;
            isBeam = false;
        }
    }

    public void Blast() {
        if (blastNum == 300) {
            isBlast = true;
            stateNum = imageNum = 2;
            blastNum = 0;
            try {
                Clip clipBeam = AudioSystem.getClip();
                AISBeam = AudioSystem.getAudioInputStream(getClass().getResource("/Blast.wav"));
                clipBeam.open(AISBeam);
                clipBeam.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            }
        } else {
            stateNum = imageNum = 1;
        }
        blastNum++;
    }
}
