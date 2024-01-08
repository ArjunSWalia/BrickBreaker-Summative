import java.awt.*;
import java.util.*;
public class LaserPaddle extends Brick
{
    private int speed;
    public LaserPaddle(int x, int y, int width, int height, Image img,int sp)
    {
        super(x,y,width,height,img);
        speed = sp;
    }

    public void hit()
    {
        setVisible(true); //the paddle never dissapears
    }

    public void moveRight()
    {
        setX(getX() + speed);
    }

    public void moveLeft()
    {
        setX(getX() - speed);
    }

    public void move()
    {
        setX(getX()+speed);
        if(getX()< 250)
        {
            setX(250); //moves it to border
            speed = -speed;
        }
        //right wall
        else if((getX()+getWidth()) >= 1600) //495
        {
            setX(1600-getWidth()); //moves it to border
            speed = -speed;
        }
    }
}
