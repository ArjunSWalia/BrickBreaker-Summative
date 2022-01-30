import java.util.*;
import java.awt.*;
import javax.swing.*;
public class Smoke extends Ball
{
    private boolean visible;
    public Smoke(int a, int b, int c, int d, int e, Image f)
    {
        super(a,b,c,d,e,f);
        visible = true;
    }

    public void move(Graphics g)
    {
        setY(getY()+getDy());
        setX(getX()+getDx());
        draw(g);
    }

    public boolean getVisible()
    {
        return visible;
    }

    public void draw(Graphics g)
    {
        g.drawImage(getImg(),getX(),getY(),null);
    }

    public void collidesWith(Brick p)
    {
        Rectangle ball = new Rectangle(getX(),getY(),getSize(),getSize());
        Rectangle brick = new Rectangle(p.getX(),p.getY(),p.getWidth(),p.getHeight());
        if(ball.intersects(brick))
        {
            //p.hit(); //you can use this method to change how the ball interacts like it could be b.StarMode
            hit();
            //System.out.println("asda");
        }
    }

    public void hit()
    {
        visible = false;
    }
}