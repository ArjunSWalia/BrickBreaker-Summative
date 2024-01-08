import java.util.*;
import java.awt.*;
import javax.swing.*;
public class SynthBalls //this class is essentially the ball class, except for the fact that they only collide with walls and that colour is a parameter rather than an image
{
    private int x; //top left x value
    private int y; // " " y "
    private int size;
    private int dx; //direction
    private int dy;
    private Color color;

    public SynthBalls(int a, int b, int c, int d, int e, Color f)
    {
        x = a;
        y = b;
        size = c;
        dx = d;
        dy = e;
        color = f;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getDx()
    {
        return dx;
    }

    public int getDy()
    {
        return dy;
    }

    public int getSize()
    {
        return size;
    }

    public void setX(int val)
    {
        x = val;
    }

    public void setY(int val)
    {
        y = val;
    }

    public void setDx(int x)
    {
        dx = x;
    }

    public void setDy(int y)
    {
        dy = y;
    }

    public void setSize(int s)
    {
        size = s;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color c)
    {
        color = c;
    }

    public void move(Graphics g)
    {
        x+=dx;
        //check collision with the left and right walls
        //left wall
        if(x < 5)
        {
            x = 5; //moves it to border
            dx = -dx;
        }
        //right wall
        else if((x+size) > 1900) //495
        {
            x = 1900-size; //moves it to border
            dx = -dx;
        }
        y+=dy;
        //check collision with top wall
        if(y < 5)   
        {
            y = 5;
            dy=-dy;
        }

        else if(y >= 945) //480
        {
            //y = 495 - size;
            //dx = -dx;
            //cond = true;
            dy = -dy;
        }
        //check if game is over(ball falls to the bottom), make it stay there
        draw(g);
    }

    public void draw(Graphics g)
    {   
        g.setColor(color);
        g.fillOval(x,y,size,size);
    }
}