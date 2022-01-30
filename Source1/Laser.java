import java.awt.*;
import java.util.*;
public class Laser extends Brick
{
    public Laser(int x, int y, int width, int height, Image img)
    {
        super(x,y,width,height,img);
    }

    public void move()
    {
        setX(BrickBreakerGame.l1.getX()+BrickBreakerGame.l1.getWidth()/2-4);
    }

    public void collidesWith(Brick b)
    {
        Rectangle laser = new Rectangle(getX(),getY(),getWidth(),getHeight());
        Rectangle brick = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
        if(laser.intersects(brick))
        {
            setVisible(false);
            b.hit();
        }
    }
}