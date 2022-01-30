import java.awt.*;
import java.util.*;
public class SuperBrick extends Brick
{
    private int hp;
    private ArrayList<Image> levels = new ArrayList<Image>(hp);
    public SuperBrick(int x, int y, int width, int height, Image img, int h,ArrayList<Image> l)
    {
        super(x,y,width,height,img);
        hp = h;
        levels = l;
    }

    public int getHp()
    {
        return hp;
    }

    public void sethp(int h)
    {
        hp = h;
    }

    public ArrayList<Image> getLevels()
    {
        return levels;
    }

    public void hit() //sets the image to a value in its arraylist based on the hp value
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
        if(hp <= 0)
        {
            setVisible(false);
        }
    }
}