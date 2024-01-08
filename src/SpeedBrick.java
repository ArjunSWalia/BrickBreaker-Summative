import java.awt.*;
import java.util.*;
public class SpeedBrick extends SuperBrick
{
    public SpeedBrick(int x, int y, int width, int height, Image img, int h,ArrayList<Image> l)
    {
        super(x,y,width,height,img,h,l);
    }

    public void hit() //changes the ball's speed
    {
        if(BrickBreakerGame.isDouble == true)
        {
            setNumScore(getNumScore()+2);
        }
        if(BrickBreakerGame.isDouble == false)
        {
            setNumScore(getNumScore()+1);
        }
        sethp(getHp()-1);
        setImage(getLevels().get(getHp()));
        if(BrickBreakerGame.ball.getDx()<0)
        {
            BrickBreakerGame.ball.setDx(BrickBreakerGame.ball.getDx()-1);
        }
        if(BrickBreakerGame.ball.getDy()<0)
        {
            BrickBreakerGame.ball.setDy(BrickBreakerGame.ball.getDy()-1);
        }
        if(BrickBreakerGame.ball.getDy()>0)
        {
            BrickBreakerGame.ball.setDy(BrickBreakerGame.ball.getDy()+1);
        }
        if(BrickBreakerGame.ball.getDx()>0)
        {
            BrickBreakerGame.ball.setDx(BrickBreakerGame.ball.getDx()+1);
        }
        if(getHp() == 0)
        {
            setVisible(false);
        }
        //setColor(levels.get(hp-1));
    }
}
