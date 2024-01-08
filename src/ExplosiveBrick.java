import java.awt.*;
import java.util.*;
public class ExplosiveBrick extends Brick
{
    private int hp;
    private ArrayList<Image> levels = new ArrayList<Image>(hp);
    private boolean explode;
    public ExplosiveBrick(int x, int y, int width, int height, Image img, int h,ArrayList<Image> l,boolean e)
    {
        super(x,y,width,height,img);
        levels = l;
        hp = h;
        explode = e;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int h)
    {
        hp = h;
    }

    public void setExplode(boolean E)
    {
        explode = E;
    }

    public boolean getExplode()
    {
        return explode;
    }

    public void hit()
    {
        if(BrickBreakerGame.isDouble == true)
        {
            setNumScore(getNumScore()+2);
        }
        if(BrickBreakerGame.isDouble == false)
        {
            setNumScore(getNumScore()+1);
        }
        hp--;
        setImage(levels.get(hp));
        if(hp == 0)
        {
            setVisible(false);
            setExplode(true);
        }
    }
    
}