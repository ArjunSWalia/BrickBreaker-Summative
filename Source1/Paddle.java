import java.awt.*;
public class Paddle extends Brick
{
    private int speed;
    private boolean CannonMode;
    private boolean isInverted;
    public Paddle(int x, int y, int width, int height, Image img, int sp)
    {
        super(x,y,width,height,img);
        speed = sp;
        CannonMode = false;
        isInverted = false; 
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int sp)
    {
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

    public void setCannonMode(boolean s)
    {
        CannonMode = s;
    }

    public boolean getCannonMode()
    {
        return CannonMode;
    }

    public boolean getInverted()
    {
        return isInverted;
    }

    public void setInverted(boolean s)
    {
        isInverted = s;
    }

    public void AutomatedMove(Ball b)
    {
        setX(b.getX()-10);
    }
}