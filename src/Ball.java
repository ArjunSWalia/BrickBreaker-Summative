import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
public class Ball
{
    private int x; //top left x value
    private int y; // " " y "
    private int size;
    private int dx; //direction
    private int dy;
    private Image img;
    private boolean superStar;
    private boolean catched;
    private boolean cond = false;
    public Ball(int a, int b, int c, int d, int e, Image f)
    {
        x = a;
        y = b;
        size = c;
        dx = d;
        dy = e;
        img = f;
        superStar = false;
        catched = false;
    }

    public Ball()
    {
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

    public Image getImg()
    {
        return img;
    }

    public void setImage(Image c)
    {
        img = c;
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
            superStar = false;
        }
        //right wall
        else if((x+size) > 1900) //495
        {
            x = 1900-size; //moves it to border
            dx = -dx;
            superStar = false;
        }
        y+=dy;
        //check collision with top wall
        if(y < 5)   
        {
            y = 5;
            dy=-dy;
            superStar = false;
        }

        else if(y >= 945) //480
        {
            //y = 495 - size;
            //dx = -dx;
            //cond = true;
            dy = -dy;
            superStar = false;
            if(BrickBreakerGame.lives.size()>0 && cond == true && BrickBreakerGame.ball.getY()>= 945 && BrickBreakerGame.chaosMode == false) 
            {
                cond = false;
                BrickBreakerGame.lives.remove(BrickBreakerGame.lives.size()-1); // if it hits the bottom of the game bounds, subtracta  life
            }
            if(BrickBreakerGame.lives.size() == 0)
            {
                BrickBreakerGame.ball.setX(950);
                BrickBreakerGame.ball.setY(950);
            }
        }
        else if(y>=5 && y<=800)
        {
            cond = true;
        }
        //check if game is over(ball falls to the bottom), make it stay there
        draw(g);
    }

    public void setSuperStar(boolean s)
    {
        superStar = s;
    }

    public boolean getSuperStar()
    {
        return superStar;
    }

    public void draw(Graphics g) //this draw method is more complex as it allows the image to be resized accoring to size manipulation
    {   
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform trans = new AffineTransform();
        g2d.transform(trans);
        g2d.drawImage(img,(int) x,(int) y, size, size, null);
        g2d.setTransform(backup);
    }

    public boolean getCatched()
    {
        return catched;
    }

    public void setCatched(boolean c)
    {
        catched = c;
    }

    public void collidesWith(Brick b)
    {
        Rectangle ball = new Rectangle(x,y,size,size);
        Rectangle brick = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
        if(ball.intersects(brick))
        {
            b.hit();
            if(b.getClass() != Paddle.class && BrickBreakerGame.canActivate == false) //if it hits a brick, increases the length of the bombmeter by 50
            {
                BrickBreakerGame.increaseLength+=50;
            }
            if(!getSuperStar() || b.getClass() == Paddle.class)
            {
                if(x< b.getX()  | x+ size > b.getX() + b.getWidth()) //side collision
                {
                    dx = -dx;
                }
                else
                {
                    dy = -dy;
                }
            }

        }
    }

    public void moveB(Brick b)
    {
        Rectangle ball = new Rectangle(x,y,size,size);
        Rectangle brick = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
        if(ball.intersects(brick))
        {
            dx = 0;
            dy = 0;
            b.hit();
            if(BrickBreakerGame.moveBB == false) //easyMode
            {
                setY(b.getY()-(b.getHeight()*2+3)); //b.getY()-b.getHeight()
                setX(b.getX()+b.getWidth()/2); //b.getX()+b.getWidth()/2
            }
            if(BrickBreakerGame.moveBB == true)//hardMode
            {
                setY(b.getY()-(b.getHeight()+5)); //b.getY()-(b.getHeight()*2)
                setX(b.getX()+b.getWidth()/2); //b.getX()+b.getWidth()/2
            }
        }
    }

    public void zoneCheck(Brick b)
    {
        //if the ball is in the zone, increase the speed
        Rectangle ball = new Rectangle(x,y,size,size);
        Rectangle brick = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
        if(ball.intersects(brick))
        {
            if(dx<0)
            {
                dx--;
            }
            if(dy<0)
            {
                dy--;
            }
            if(dy>0)
            {
                dy++;
            }
            if(dx>0)
            {
                dx++;
            }
        }
    }
}