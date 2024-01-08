import java.util.*;
import java.awt.*;
import javax.swing.*;
public class PowerUpBall extends Ball
{
    private boolean visible;
    private boolean direction = false;
    public PowerUpBall(int a, int b, int c, int d, int e, Image f)
    {
        super(a,b,c,d,e,f);
        visible = true;
    }

    public void move(Graphics g) //only moves down, and stops if it hits the end of the board
    {
        setY(getY()+getDy());
        if(getX() < 5)
        {
            setX(5); //moves it to border
        }
        //right wall
        else if((getX()+getSize()) > 1900)
        {
            setX(1900-getSize()); //moves it to border
        }

        //check collision with top wall
        if(getY() < 5)
        {
            setY(5);
        }

        else if(getY() > 920)
        {
            //y = 495 - size;
            setDx(0); 
            setDy(0);
        }

        //check if game is over(ball falls to the bottom), make it stay there
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

    public void moveDemo(int a,int b) // utilized in the demoballs
    {
        if(direction == false && getY()>= b)
        {
            direction = true;
        }
        if(direction == true && getY()<= a)
        {
            direction = false;
        }
        if(direction == false)
        {
            setY(getY() + getDy());
        }
        if(direction == true)
        {
            setY(getY() - getDy());
        }
    }
}