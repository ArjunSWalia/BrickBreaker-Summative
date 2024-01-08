import java.awt.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
public class Brick
{
    //instance vars
    private int x;
    private int y;
    private int width;
    private int height;
    private Image img;
    private boolean visible;
    private static int numScore = 0;
    private boolean hasLasered = false;
    //constructors
    public Brick(int x, int y, int width, int height, Image f)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        img = f;
        this.visible = true; 
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public static int getNumScore() //this variable is static and corresponds to all bricks, and if they are hit, it increases the score by 1
    {
        return numScore;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean getVisible()
    {
        return visible;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setWidth(int w)
    {
        width = w;
    }

    public void setHeight(int h)
    {
        height = h;
    }

    public void setVisible(boolean v)
    {
        visible = v;
    }

    public void setImage(Image c)
    {
        img = c;
    }

    public static void setNumScore(int x)
    {
        numScore = x;
    }
    public void draw(Graphics g)
    {   
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform trans = new AffineTransform();
        g2d.transform(trans);
        g2d.drawImage(img, (int) x, (int) y, width, height, null);
        g2d.setTransform(backup);
    }

    public Image getImage()
    {
        return img;
    }

    public boolean getLaser()
    {
        return hasLasered;
    }

    public void setLaser(boolean s)
    {
        hasLasered = s;
    }
    
    public void hit() //if it his hit, increase the score depends on the isDouble variable
    {
        visible = false;
        if(BrickBreakerGame.isDouble == true)
        {
            numScore+=2;
        }
        if(BrickBreakerGame.isDouble == false)
        {
            setNumScore(getNumScore()+1);
        }
    }

}