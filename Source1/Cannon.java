import java.awt.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
public class Cannon extends Brick
{

    //instance vars
    //constructors
    private int dy;
    public Cannon(int x, int y, int width, int height, Image img, int Dy)
    {
        super(x,y,width,height,img);
        dy = Dy;
    }

    //accessors
    //draw method
    public void move(Graphics g)
    {
        setY(getY()-dy);
        draw(g);
    }

    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform trans = new AffineTransform();
        g2d.transform(trans);
        g2d.drawImage(getImage(),(int) getX(),(int)getY(),getWidth(),getHeight(), null);
        g2d.setTransform(backup);
    }

    public void collidesWith(Brick b)
    {
        Rectangle cannon = new Rectangle(getX(),getY(),getWidth(),getHeight());
        Rectangle brick = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
        if(cannon.intersects(brick))
        {
            setVisible(false);
            b.hit();
        }
    }
}
