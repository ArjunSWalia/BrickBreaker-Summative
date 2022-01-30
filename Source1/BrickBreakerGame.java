import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sun.audio.*;
public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener, MouseListener
{
    //initializing all variables used
    public static Ball ball;
    private Ball b2;
    private Ball b3;
    private Ball b4;
    private Timer timer;
    private boolean play;
    private Paddle paddle;
    private Brick[][] bricks;
    private ArrayList<Ball> particles2 = new ArrayList<Ball>();
    private ArrayList<PowerUpBall> powerups = new ArrayList<PowerUpBall>(); //holds all the powerups
    private ArrayList<Ball> newBalls = new ArrayList<Ball>(); //the arraylist that holds the balls when you get the powerup
    private int PUCount;
    private ArrayList<Cannon> cannons = new ArrayList<Cannon>(); //holds the ammo
    private int powerCount = 3; 
    private boolean bombs = false;
    private boolean armed = false;
    private ArrayList<Smoke> smoke = new ArrayList<Smoke>();
    private boolean BombCollides = false;
    private boolean catched = false;
    private boolean justCaught = false;
    private int ballSpeedX;
    private int ballSpeedY;
    private int decayTimer = 0;
    private boolean paddleBotMode = false;
    boolean Trigger = false;
    private SpeedZone zone;
    public static LaserPaddle l1;
    private LaserPaddle l2;
    private Laser laser1;
    private int ZoneTimer = 0;
    private boolean hasCollided = false;
    private boolean lcond = false;
    private int MultiplyTimer = 0;
    private int SCORE = 0;
    private int temp = 0;
    public static boolean isDouble = false;
    private boolean laserSpawn = false;
    int screen = 1;
    Font Andromeda;
    public ArrayList<SynthBalls> TitleBalls = new ArrayList<SynthBalls>();
    public static boolean chaosMode = false;
    public static boolean easyMode = false;
    public static boolean moveBB = false;
    private boolean hardMode = false;
    private boolean ballCreation = false;
    public static int increaseLength = 0;
    public static boolean canActivate = false;
    public static ArrayList<Ball> lives = new ArrayList<Ball>();
    
    
    //Instruction PowerupBalls
    private PowerUpBall DemoPower1;
    private p1 DemoPower2;
    private p2 DemoPower3;
    private p3 DemoPower4; 
    private p4 DemoPower5;
    private p5 DemoPower6;
    private p6 DemoPower7;
    private p7 DemoPower8;
    private boolean Creation = false;
    private boolean gss = false;
    //Image Declaration
    private Image bg;
    private Image title;
    private Image info;
    private Image difficulty;
    private Image win;
    private Image lose;
    private Image ballimg;
    private Image bombimg;
    private Image cannonimg;
    private Image CatchPaddle;
    private Image ChaosBall;
    private Image DecayBrick;
    private Image ExplosiveBrickimg;
    private Image laserimg;
    private Image LaserPaddleimg;
    private Image Livesimg;
    private Image NewBallimg;
    private Image Paddleimg;
    private Image PowerUpBrickimg;
    private Image RegularBrickimg;
    private Image SlowBrickimg;
    private Image Smokeimg;
    private Image SpeedBrickimg;
    private Image SpeedZoneimg;
    private Image StarModeimg;
    private Image SuperBrick1;
    private Image SuperBrick2;
    private Image SuperBrick3;
    private Image SuperBrick4;
    private Image SuperStarBall;
    private Image DecayModeimg;
    private Image CannonModeimg;
    private Image ScoreMultiplierimg;
    private Image IncreaseSizeimg;
    private Image MoreBallsimg;
    private Image PaddleBotModeimg;
    private Image Updated;
    private Image InvertControlsimg;
    private ArrayList<Image> levels = new ArrayList<Image>();
    AudioStream titlemusic;
    public BrickBreakerGame()
    {
        init(); //calls the initialization method
    }
    public void TitleScreen(Graphics g)
    {
        //plays the titlemusic and draws the image along with moving the titleballs
        AudioPlayer.player.start(titlemusic);
        g.drawImage(title,0,0,null);
        for(int i = 0;i<TitleBalls.size();i++)
        {
            TitleBalls.get(i).move(g);
        }
    }

    public void DifficultyScreen(Graphics g) //draws the image and moves the title balls
    {
        g.drawImage(difficulty,0,0,null);
        for(int i = 0;i<TitleBalls.size();i++)
        {
            TitleBalls.get(i).move(g);
        }
    }

    public void InstructionScreen(Graphics g) //same thing here except it also draws and moves the demoballs
    {
        g.drawImage(info,0,0,null);
        for(int i = 0;i<TitleBalls.size();i++)
        {
            TitleBalls.get(i).move(g);
        }
        DemoPower1.draw(g);
        DemoPower2.draw(g);
        DemoPower3.draw(g);
        DemoPower4.draw(g);
        DemoPower5.draw(g);
        DemoPower6.draw(g);
        DemoPower7.draw(g);
        DemoPower8.draw(g);
        DemoPower1.moveDemo(530,580); //the two paramaters act as bounds for the powerupBalls
        DemoPower2.moveDemo(125,175);
        DemoPower3.moveDemo(125,175);
        DemoPower4.moveDemo(125,175);
        DemoPower5.moveDemo(125,175);
        DemoPower6.moveDemo(530,580);
        DemoPower7.moveDemo(530,580);
        DemoPower8.moveDemo(530,580);
    }

    public void WinScreen(Graphics g)
    {
        g.drawImage(win,0,0,null);
        for(int i = 0;i<TitleBalls.size();i++)
        {
            TitleBalls.get(i).move(g);
        }
    }

    public void LoseScreen(Graphics g)
    {
        g.drawImage(lose,0,0,null);
        for(int i = 0;i<TitleBalls.size();i++)
        {
            TitleBalls.get(i).move(g);
        }
    }

    public void paint(Graphics g) //
    {
        //draw the walls and the background
        if(screen == 1) //if the screen is 1(title screen), runs the function that displays the title screen
        {
            //play = true;
            TitleScreen(g);
            if(gss == true) //gss is a variable that is initially false, but acts as a creation variable. This if statement is true so that the play again button works effectively
            {
                //recreates the grid and the balls
                ball = new Ball(950,750,25,6,6,ballimg);
                paddle = new Paddle(960,940,120,10,Paddleimg,30);
                zone = new SpeedZone(950,650,150,75,SpeedZoneimg,3); //950,500,400,75,Color.yellow,3
                l1 = new LaserPaddle(850,100,100,35,LaserPaddleimg,3);
                laser1 = new Laser(l1.getX()+l1.getWidth()/2-4,l1.getY()+l1.getHeight(),10,500,laserimg);
                int x = 300;
                int y = 150;
                int val = 0;
                //creation of brick grid
                for(int i = 0;i<bricks.length;i++)
                {
                    for(int j = 0; j<bricks[i].length;j++)
                    {
                        bricks[i][j] = new Brick(x,y,100,25,RegularBrickimg);
                        int BrickChance = (int)(4* Math.random()+1); //determines what type of brick it will be 4*1
                        if(BrickChance  <=2 && BrickChance!= 0) //make the chances of getting a powerupbrick really low
                        {
                            //ONCE IMAGES ARE UPLOADED YOU CAN MAKE EACH THING HAVE A RANDOM HP VALUE
                            int rando = (int)(4*Math.random()); //this value determines the hp of the bricks
                            if(rando<= 0)
                            {
                                val = 1;
                            }
                            if(rando > 0)
                            {
                                val = rando;
                            }
                            bricks[i][j] = new SuperBrick(x,y,100,25,levels.get(rando),val,levels); //levels.get(rando)
                            int m = (int)(1* Math.random() + 1);
                        }
                        int specialChance = (int)(6*Math.random()+1); //6*1
                        int powerUpChance = 0;
                        if(specialChance == 1) //3
                        {
                            int SpecBrick = (int)(6*Math.random() + 1);
                            if(SpecBrick == 2) //ExplosiveBrick
                            {
                                bricks[i][j] = new ExplosiveBrick(x,y,100,25,ExplosiveBrickimg,1,levels,false); //replace levels with an arraylist of images
                            }
                            if(SpecBrick == 1) //SpeedBrick
                            {
                                bricks[i][j] = new SpeedBrick(x,y,100,25,SpeedBrickimg,1,levels); // //replace levels with an arraylist of images
                            }
                            if(SpecBrick == 3) //SlowBrick
                            {
                                bricks[i][j] = new SlowBrick(x,y,100,25,SlowBrickimg,1,levels); // //replace levels with an arraylist of images
                            }
                        }
                        powerUpChance = (int)(23*Math.random() +1); //11*1
                        if(powerUpChance == 1)
                        {
                            bricks[i][j] = new PowerUpBrick(x,y,100,25,PowerUpBrickimg,1,levels);
                            int m = (int)(8* Math.random() + 1); //8*1
                            //m is used to dictate which powerup spawns after htting a brick
                            if(m == 1) //StarMode
                            {
                                PowerUpBall entry = new p2(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,StarModeimg);
                                powerups.add(entry);
                            }
                            if(m == 6)//adds 2 balls
                            {
                                PowerUpBall entry = new PowerUpBall(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,MoreBallsimg);
                                powerups.add(entry);
                            }
                            if(m == 7) //increases the size of the ball
                            {
                                PowerUpBall entry = new p1(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,IncreaseSizeimg);
                                powerups.add(entry);
                            }
                            if(m == 2) //cannnon
                            {
                                PowerUpBall entry = new p3(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,CannonModeimg);
                                powerups.add(entry);
                            }
                            if(m == 3)//score multiplier
                            {
                                PowerUpBall entry = new p4(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,ScoreMultiplierimg);
                                powerups.add(entry);
                            }
                            if(m == 8) //decay
                            {
                                PowerUpBall entry = new p5(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,DecayModeimg);
                                powerups.add(entry);
                            }
                            if(m == 4) //invert controls
                            {
                                PowerUpBall entry = new p6(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,InvertControlsimg);
                                powerups.add(entry);
                            }
                            if(m == 5) //follow ball
                            {
                                PowerUpBall entry = new p7(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,PaddleBotModeimg);
                                powerups.add(entry);
                            }
                        }
                        int invisChance = (int)(11*Math.random()+1);
                        if(invisChance == 11 && bricks[i][j].getClass()!=PowerUpBrick.class)
                        {
                            bricks[i][j] = new Brick(x,y,100,25,RegularBrickimg);
                            bricks[i][j].setVisible(false);
                        }
                        x+=105;
                    }
                    x = 300;
                    y+=30;
                }
                //this for loop is used to create the lives
                int tt = 5;
                for(int i = 0;i<3;i++)
                {
                    lives.add(new Ball(tt,100,30,0,0,Livesimg));
                    tt+=40;
                }
                gss = false;
            }
        }
        if(screen == 3) //difficulty screen
        {
            //play = true;
            DifficultyScreen(g);
        }
        if(screen == 4) //instruction screen
        {
            //play = true;
            InstructionScreen(g);
        }
        if(screen == 5) //win screen
        {
            WinScreen(g);
        }
        if(screen == 6) //lose screen
        {
            LoseScreen(g);
        }
        if(screen == 2)
        {
            if(easyMode == true) //if easyMode is set to true, increases the size of the paddle and the ball and adds 2 lives
            {
                paddle.setWidth(paddle.getWidth()*2+10);
                paddle.setHeight(paddle.getHeight()*2);
                ball.setSize(ball.getSize()+3);
                easyMode = false;
                moveBB = true;
                lives.add(new Ball(125,100,30,0,0,Livesimg));
                lives.add(new Ball(165,100,30,0,0,Livesimg));
            }
            if(chaosMode == true && ballCreation == true) //if chaosMode is true, create three new balls
            {
                ballCreation = false;
                b2 = new Ball(800,750,25,8,6,ChaosBall);
                b3 = new Ball(900,750,25,-8,-6,ChaosBall);
                b4 = new Ball(500,750,25,9,5,ChaosBall);
            }
            g.drawImage(Updated,0,0,null); //draws the background image
            
            //draws the bomb meter bar
            g.setColor(Color.white);
            g.fillRect(30,200,210,60);
            g.setColor(Color.black);
            g.fillRect(35,205,200,50);
            g.setColor(Color.red);
            g.fillRect(35,205,increaseLength,50);
            
            //draws the speed zone and the laser paddle
            zone.draw(g);
            l1.draw(g);
            
            //draws the score string using fonts
            g.setColor(Color.white);
            int fontSize = 30;
            Font myFont = new Font("abnes", 40, 40);  
            g.setFont (myFont);
            String str = String.valueOf(bricks[0][0].getNumScore());
            g.drawString("Score:",20,50);
            g.setColor(new Color(0,255,125));
            g.drawString(str,300,50);
            if(isDouble) //if the 2x mode is activated draws the string
            {
                myFont = new Font ("abnes", 40, 40);
                g.setColor(Color.red);
                g.drawString("2x ACTIVATED",700,800);
            }
            if(powerCount>0) //draws the ammo
            {
                myFont = new Font ("abnes", 40, 40);
                g.setColor(new Color(0,255,125));
                String val = String.valueOf(powerCount);
                g.drawString("Ammo: " + val, 1500,50);
            }
            if(Trigger == true && paddleBotMode == true) //if follow mode is activated after pressing the 'p' key it displayes "follow "mode"
            {
                myFont = new Font("abnes",20,20);
                g.setColor(new Color(0,255,125));
                g.drawString("Follow Mode",650,50);
            }
            //draws the ball 
            ball.move(g);
            PUCount = -1;
            //checks the collisions for each brick and each type of ball
            for(int i = 0;i<bricks.length;i++)
            {
                for(int j = 0; j<bricks[i].length;j++)
                {
                    if(bricks[i][j].getVisible() == true)
                    {
                        int z = (int)(10 * Math.random() + 1);
                        bricks[i][j].draw(g);
                        if(bricks[i][j].getClass()==ExplosiveBrick.class)
                        {
                            ball.collidesWith(bricks[i][j]);
                            BombCollides = true;
                        }
                        if(bricks[i][j].getClass()!= ExplosiveBrick.class)
                        {
                            ball.collidesWith(bricks[i][j]);
                        }
                    }
                    for(int I = 0;I<newBalls.size();I++)
                    {
                        if(bricks[i][j].getVisible() == true)
                        {
                            newBalls.get(I).collidesWith(bricks[i][j]);
                            newBalls.get(I).collidesWith(l1);
                        }
                    }
                    if(chaosMode == true)
                    {
                        if(bricks[i][j].getVisible() == true)
                        {
                            b2.collidesWith(bricks[i][j]);
                            b3.collidesWith(bricks[i][j]);
                            b4.collidesWith(bricks[i][j]);
                        }
                    }
                    for(int J = 0;J<cannons.size();J++)
                    {
                        if(bricks[i][j].getVisible() == true)
                        {
                            cannons.get(J).collidesWith(bricks[i][j]);
                        }
                    }
                    if(bricks[i][j].getClass() == PowerUpBrick.class)
                    {
                        PUCount++;
                    }
                    if(bricks[i][j].getVisible() == false && bricks[i][j].getClass() == PowerUpBrick.class) //if the powerbrick is invisible and it was just hit
                    {
                        if(powerups.get(PUCount).getVisible() == true)
                        {
                            powerups.get(PUCount).move(g); 
                        }
                    }
                    if(BombCollides == true && bricks[i][j].getClass() == ExplosiveBrick.class && bricks[i][j].getVisible()== false) //if it is an explosive brick and it it was just hit
                    {
                        //this logic sequence goes through each brick in the array and using pythagorean's theorem it can determine the distance between the midpoints of the bricks, based on that distance it will hit the bricks
                        for(int first = 0;first<bricks.length;first++)
                        {
                            for(int second = 0; second<bricks[first].length;second++)
                            {
                                int brickRadX = bricks[first][second].getX()+ bricks[first][second].getWidth()/2;
                                int brickRadY = bricks[first][second].getY()+bricks[first][second].getHeight()/2;
                                brickRadX-=bricks[i][j].getX()+bricks[i][j].getWidth()/2;
                                brickRadY-=bricks[i][j].getY()+bricks[i][j].getHeight()/2;
                                brickRadX*=brickRadX;
                                brickRadY*=brickRadY;
                                int distance = brickRadX+brickRadY;
                                int radius = 16898;//squared of initial radius
                                if(bricks[first][second].getVisible() && distance<=radius)
                                {
                                    bricks[first][second].hit();
                                    for(int smokeCount = 0;smokeCount<2;smokeCount++)
                                    {
                                        int smokeX = (int) (32*Math.random() + 1); 
                                        int smokeY = (int) (32*Math.random() + 1);
                                        int addition = (int) (400 *Math.random()+1);
                                        int cf = (int) (2*Math.random()+1);
                                        int location;
                                        if(cf == 1)
                                        {
                                            location = bricks[i][j].getX()+smokeX;
                                        }
                                        else
                                        {
                                            location = bricks[i][j].getX()-addition;
                                        }
                                        smoke.add(new Smoke(location,bricks[i][j].getY()+smokeY,10,smokeX,smokeY,Smokeimg));
                                    }
                                }
                            }
                        }
                    }
                    BombCollides = false;
                }
            }
            if(justCaught == false) //if it is not in catch mode
            {
                int val = 0;
                //adds particles, that fade every call to the method since the size of each is decreasing, gives it that tail effect
                for(int i = 0;i<particles2.size();i++)
                {
                    particles2.get(i).setSize(particles2.get(i).getSize()-1);
                    particles2.get(i).draw(g);
                    if(particles2.get(i).getSize() <= 0)
                    {
                        particles2.remove(i);
                    }
                }
                if(ball.getDy()< 0)
                {
                    val = -10;
                }
                else
                {
                    val = 10;
                }
                particles2.add(new Ball(ball.getX(),ball.getY(),ball.getSize()-2,ball.getDy(),ball.getDx(),ball.getImg()));
            }
            paddle.draw(g);
            boolean justVisible = false;
            for(int i = 0;i<powerups.size();i++)//this for loop goes through each of the powerups checking if it is visible, if it collides with the paddle it will do specific things
            {
                if(powerups.get(i).getVisible() == true)
                {
                    powerups.get(i).collidesWith(paddle);
                    justVisible = true;
                }
                if(justVisible == true && powerups.get(i).getVisible() == false) //if the same ball was just visible and then it was collided with the paddle
                {
                    if(powerups.get(i).getClass() == PowerUpBall.class) //if it is a a powerup ball that spawns ball
                    {
                        for(int  I = 0;I<2;I++)
                        {
                            ballSpeedX = ball.getDx();
                            ballSpeedY = ball.getDy();
                            if(ball.getDx() == 0)
                            {
                                ballSpeedX = 5+3;
                            }
                            if(ball.getDy() == 0)
                            {
                                ballSpeedY = 5+5;
                            }
                            newBalls.add((new Ball(ball.getX()+I*2,ball.getY(),20,-ballSpeedX,-ballSpeedY,NewBallimg)));
                        }
                        ball.setSuperStar(false);
                    }
                    else if(powerups.get(i).getClass() == p1.class)//increases the ball's size
                    {
                        ball.setSize(ball.getSize()+1);
                        ball.setSuperStar(false);
                    }
                    else if(powerups.get(i).getClass() == p2.class) //activates star mode
                    {
                        ball.setSuperStar(true);
                    }
                    else if(powerups.get(i).getClass() == p3.class) //adds 3 balls to ammo
                    {
                        paddle.setCannonMode(true);
                        powerCount+=3;
                    }
                    else if(powerups.get(i).getClass() == p4.class) //activeates 2x mode
                    {
                        isDouble = true;
                        MultiplyTimer = 0;
                    }
                    else if(powerups.get(i).getClass() == p5.class) //activates decay mode
                    {
                        for(int k = 0;k<bricks.length;k++)
                        {
                            for(int j = 0;j<bricks[k].length;j++)
                            {
                                int decay = (int)(10 * Math.random() + 1);
                                if(decay <= 1)
                                {
                                    if(bricks[k][j].getVisible() == true)
                                    {
                                        //bricks[k][j].hit();
                                        decayTimer = 0;
                                        bricks[k][j].setImage(DecayBrick);
                                    }
                                }
                            }
                        }
                    }
                    else if(powerups.get(i).getClass() == p6.class)//inverts the controls
                    {
                        paddle.setInverted(!paddle.getInverted());
                    }
                    else if(powerups.get(i).getClass() == p7.class) //engages bot mode
                    {
                        paddleBotMode = true;
                    }
                }
                justVisible = false;
            }
            //cycles through the newballs that are created and checks their collisions with the other assets
            for(int i = 0;i<newBalls.size();i++)
            {
                newBalls.get(i).move(g);
                newBalls.get(i).collidesWith(paddle);
                newBalls.get(i).collidesWith(l1);
            }
            
            if(ball.getSuperStar() == false && armed == false && ball.getImg() != bombimg)
            {   
                ball.setImage(ballimg);
            }
            if(ball.getSuperStar())
            {   
                ball.setImage(SuperStarBall);
            }
            
            for(int i = 0;i<cannons.size();i++)
            {
                if(cannons.get(i).getVisible())
                {
                    cannons.get(i).move(g);
                }
            }
            
            for(int i = 0;i<smoke.size();i++)
            {
                if(smoke.get(i).getVisible())
                {
                    smoke.get(i).move(g);
                }
            }
            if(ball.getCatched() == false) //if the paddle is not in catchmode sets its image to the reg image and if it was just in catchmode, it releases the ball
            {
                paddle.setImage(Paddleimg);
                if(justCaught)
                {
                    ball.setDx(5);
                    ball.setDy(5);
                    justCaught = false;
                }
                ball.collidesWith(paddle);
            }
            else if(ball.getCatched() == true)
            {
                paddle.setImage(CatchPaddle);
                ball.moveB(paddle);
                Rectangle trappedBall = new Rectangle(ball.getX(),ball.getY(),ball.getSize(),ball.getSize());
                Rectangle brick = new Rectangle(paddle.getX(),paddle.getY(),paddle.getWidth(),paddle.getHeight());
                if(trappedBall.intersects(brick))
                {
                    justCaught = true;
                }
            }
            decayTimer++;
            if(decayTimer>=200)
            {
                decayTimer = 0;
                for(int i = 0;i<bricks.length;i++)
                {
                    for(int j = 0;j<bricks[i].length;j++)
                    {
                        if(bricks[i][j].getImage() == DecayBrick) //If they were marked by decay brick, destroy them
                        {
                            bricks[i][j].setVisible(false);
                        }
                    }
                }
            }
            //if paddlebot mode is engaged and the letter p is pressed, it automatically moves the ball
            if(paddleBotMode && Trigger == true)
            {
                paddle.AutomatedMove(ball);
            }
            //speed zones, the next if statements check if the ball has collided with the zone, if so, it temporarily increases its speed
            Rectangle ronx = new Rectangle(ball.getX(),ball.getY(),ball.getSize(),ball.getSize());
            Rectangle b = new Rectangle(zone.getX(),zone.getY(),zone.getWidth(),zone.getHeight());
            if(!(ronx.intersects(b)))
            {
                hasCollided = false;
            }
            if((ronx.intersects(b)) && hasCollided == false)
            {
                ZoneTimer = 0;
                //ball.setColor(Color.green);
                hasCollided = true;
                lcond = true;
            }
            if(ZoneTimer>=100 && lcond == true)
            {
                //ball.setColor(Color.white);
                ZoneTimer = 0;
                lcond = false;
                if(ball.getDx()<0)
                {
                    ball.setDx(-5);
                }
                if(ball.getDy()<0)
                {
                    ball.setDy(-5);
                }
                if(ball.getDx()>0)
                {
                    ball.setDx(5);
                }
                if(ball.getDy()>0)
                {
                    ball.setDy(5);
                }
            }
            ball.zoneCheck(zone);
            zone.move();
            l1.move();
            ZoneTimer++;
            MultiplyTimer++;
            //if the multiplytimer exceeds 600 it goes back to normal
            if(MultiplyTimer>= 600)
            {
                MultiplyTimer = 0;
                isDouble = false;
            }
            laser1.move();
            ball.collidesWith(l1);
            //if the laserSpawn is activated, which is only activated after pressing the 'L' key and being in chaosMode
            if(laserSpawn)
            {
                laser1.draw(g);
                //in order to prevent the laser from wiping out series of bricks, each brick has a boolean that determines if it was hit or not by the laser, depending on the state of that variable, the brick will be hit
                for(int z = 0;z<bricks.length;z++)
                {
                    for(int zz = 0;zz<bricks[z].length;zz++)
                    {
                        if(bricks[z][zz].getLaser() == false)
                        {
                            if(bricks[z][zz].getVisible() == true)
                            {
                                laser1.collidesWith(bricks[z][zz]);
                                Rectangle laser = new Rectangle(laser1.getX(),laser1.getY(),laser1.getWidth(),laser1.getHeight());
                                Rectangle brick = new Rectangle(bricks[z][zz].getX(),bricks[z][zz].getY(),bricks[z][zz].getWidth(),bricks[z][zz].getHeight());
                                if(laser.intersects(brick))
                                {
                                    bricks[z][zz].setLaser(true);
                                }
                            }
                        }
                    }
                }
            }
            boolean k = gameEnd(bricks); //checks if the game is over
            if(k == true)
            {
                screen = 5;
            }
            if(chaosMode == true)
            {
                b2.move(g);
                b3.move(g);
                b4.move(g);
                b2.collidesWith(paddle);
                b3.collidesWith(paddle);
                b4.collidesWith(paddle);
                b2.collidesWith(l1);
                b3.collidesWith(l1);
                b4.collidesWith(l1);
            }
            //sets the canActivate variable to true, which allows the bomb to be activated
            if(increaseLength>=200)
            {
                increaseLength = 200;
                canActivate = true;
            }
            for(int i = 0;i<lives.size();i++)
            {
                lives.get(i).draw(g);
            }
            if(lives.size() == 0 || (ball.getDx() == 0 || ball.getDy() == 0) && ball.getCatched() == false)
            {
                screen = 6;
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(play)
        {
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        int aj = e.getX(); 
        int ak = e.getY();
        System.out.println(aj);
        System.out.println(ak);
        if(screen == 1)
        {
            if(aj>=638 && aj<=1282 && ak>=407 && ak<=590) //play game button
            {
                screen = 3;
                //play = true;
                repaint();
            }
            else if(aj>=638 && aj<=1282 && ak>=738 && ak<=920)//information button
            {
                screen = 4;
                //play = true;
                repaint();
            }
        }
        else if(screen == 3) //difficulty screen
        {
            if(aj<=100 && ak<=100) //Arrow
            {
                screen = 1;
                repaint();
                // play = true;
            }

            else if(aj>=636 && aj<=1281 && ak>=78 && ak<=260) //easy mode
            {  
                easyMode = true;
                screen = 2;
                repaint();
                //play = true;
            }

            else if(aj>= 636 && aj<=1281 && ak>=409 && ak<=587)//hard mode
            {
                screen = 2;
                repaint();
                //play = true;
            }
            else if(aj>=636 && aj<= 1281 && ak>=737 && ak<=917)//chaosmode
            {
                chaosMode = true;
                easyMode = false;
                ballCreation = true;
                screen = 2;
                // play = true;
                repaint();
            }
        }
        else if(screen == 4)//information
        {
            if(aj<=100 && ak<=100)//back arrow
            {
                screen = 1;
                repaint();
                //play = true;
            }
        }
        else if(screen == 5)
        {
            if(aj<=1900 && ak<= 1000)
            {
                reset();
                screen = 1;
                repaint();
            }
        }
        else if(screen == 6)
        {
            if(aj<=1900 && ak<= 1000)
            {
                reset();
                screen = 1;
                repaint();
            }
        }
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if(screen == 2) //if the game is in playGame screen
        {
            play = true;
            if(paddleBotMode == false || Trigger == false) //if the paddle is not in bot mode
            {
                if(e.getKeyCode() == KeyEvent.VK_D)
                {
                    if(!paddle.getInverted()) //if the paddle is not inverted
                    {
                        paddle.moveRight();
                        if(paddle.getX() + paddle.getWidth() >= 1900)
                        {
                            paddle.setX(1900-paddle.getWidth()); //prevents paddle from hitting the right side of the screen
                        }
                    }
                    if(paddle.getInverted())//if it is inverted
                    {
                        paddle.moveLeft();
                        if(paddle.getX()<=5)
                        {
                            paddle.setX(5);
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_A)//same logic applies here
                {
                    if(!paddle.getInverted())
                    {
                        paddle.moveLeft();
                        if(paddle.getX()<=5)
                        {
                            paddle.setX(5);
                        }
                    }
                    if(paddle.getInverted())
                    {
                        paddle.moveRight();
                        if(paddle.getX() + paddle.getWidth() >= 1900)
                        {
                            paddle.setX(1900-paddle.getWidth()); //prevents paddle from hitting the right side of the screen
                        }
                    }
                }
            }
            if(e.getKeyCode()  == KeyEvent.VK_Q && paddle.getCannonMode() || e.getKeyCode() == KeyEvent.VK_Q && powerCount>0)//if you press q, it adds a cannon to the arraylist and minuses one from power count
            {
                cannons.add(new Cannon(paddle.getX()+paddle.getWidth()/2,paddle.getY(),20,20,cannonimg,5));
                paddle.setCannonMode(false);
                powerCount--;
            }
            if(e.getKeyCode() == KeyEvent.VK_SPACE && armed == true && !ball.getSuperStar())//if the bomb is armed through the bomb meter and the ball is not a super star
            {
                ball.setImage(bombimg); //sets the image to bomb
                for(int i = 0;i<bricks.length;i++)//checks the centers of each of the bricks and hits them if they are in range of the desired radius
                {
                    for(int j = 0; j<bricks[i].length;j++)
                    {
                        int brickRadX = bricks[i][j].getX()+ bricks[i][j].getWidth()/2;
                        int brickRadY = bricks[i][j].getY()+bricks[i][j].getHeight()/2;
                        brickRadX-=ball.getX()+ball.getSize()/2;
                        brickRadY-=ball.getY()+ball.getSize()/2;
                        brickRadX*=brickRadX;
                        brickRadY*=brickRadY;
                        int distance = brickRadX+brickRadY;
                        int radius = 60000;//squared of initial radius
                        if(bricks[i][j].getVisible() && distance<=radius)
                        {
                            bricks[i][j].hit();
                            bombs = true;
                            for(int smokeCount = 0;smokeCount<2;smokeCount++)
                            {
                                int smokeX = (int) (26*Math.random() + 1); 
                                int smokeY = (int) (26*Math.random() + 1);
                                int addition = (int) (400 *Math.random()+1);
                                int cf = (int) (2*Math.random()+1);
                                int location;
                                if(cf == 1)
                                {
                                    location = ball.getX()+smokeX;
                                }
                                else
                                {
                                    location = ball.getX()-addition;
                                }
                                smoke.add(new Smoke(location,ball.getY()+smokeY,10,smokeX,smokeY,Smokeimg));
                            }
                        }
                    }
                }
                if(bombs) //bombs will be set to true if a brick is in range and then after it goes through each it is turned off
                {
                    ball.setDx(-ball.getDx());
                    ball.setDy(-ball.getDy());
                    armed = false;
                    bombs = false;
                    ball.setImage(ballimg);
                    canActivate = false;
                    increaseLength = 0;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_E && canActivate == true)//arms the bomb if you press the letter 'E' and canActivate is true, which is set to true if the bomb meter is full
            {
                //System.out.println(armed);
                armed = !armed;
                if(armed == true)
                {
                    ball.setImage(bombimg);
                }
                if(armed == false)
                {
                    ball.setImage(ballimg);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_R) //if the r key is pressed it engages catch mode
            {
                if(ball.getCatched() == false)
                {
                    ball.setCatched(true);
                }
                else
                {
                    ball.setCatched(false);
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_P) //engages follow mode
            {
                Trigger = !Trigger; 
                if(Trigger == false && paddleBotMode == true)
                {
                    paddleBotMode = false;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_L && chaosMode == true) //spawns the laser if it is in chaosmode and goes changes the variable for each brick
            {
                laserSpawn =!laserSpawn;
                if(laserSpawn == false)
                {
                    for(int i = 0;i<bricks.length;i++)
                    {
                        for(int j = 0;j<bricks[i].length;j++)
                        {
                            if(bricks[i][j].getLaser() == true)
                            {
                                bricks[i][j].setLaser(false);
                            }
                        }
                    }
                }
            }
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public boolean gameEnd(Brick[][] bricks)
    {
        //checks if the game is over
        for(int i = 0;i<bricks.length;i++)
        {
            for(int j = 0;j<bricks[i].length;j++)
            {
                if(bricks[i][j].getVisible() == true)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void init()
    {
        addMouseListener(this);
        //initializing the game
        try
        {
            Andromeda = Font.createFont(Font.TRUETYPE_FONT, new File("abnes.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("abnes.ttf")));
            title = ImageIO.read(new File("title.png"));
            info = ImageIO.read(new File("info.png"));
            difficulty = ImageIO.read(new File("difficulty.png"));
            win = ImageIO.read(new File("win.png"));
            lose = ImageIO.read(new File("lose.png"));
            ballimg = ImageIO.read(new File("Ball.png"));
            bombimg = ImageIO.read(new File("Bomb.png"));
            cannonimg = ImageIO.read(new File("Cannon.png"));
            CatchPaddle = ImageIO.read(new File("CatchPaddle.png"));
            ChaosBall = ImageIO.read(new File("ChaosBall.png"));
            DecayBrick = ImageIO.read(new File("DecayBrick.png"));
            ExplosiveBrickimg = ImageIO.read(new File("ExplosiveBrick.png"));
            laserimg = ImageIO.read(new File("Laser.png"));
            LaserPaddleimg = ImageIO.read(new File("LaserPaddle.png"));
            Livesimg = ImageIO.read(new File("Lives.png")); 
            NewBallimg = ImageIO.read(new File("NewBall.png"));
            Paddleimg = ImageIO.read(new File("Paddle.png"));
            PowerUpBrickimg = ImageIO.read(new File("PowerUpBrick.png"));
            RegularBrickimg = ImageIO.read(new File("RegularBrick.png"));
            SlowBrickimg = ImageIO.read(new File("SlowBrick.png"));
            Smokeimg = ImageIO.read(new File("Smoke.png"));
            SpeedBrickimg = ImageIO.read(new File("SpeedBrick.png"));
            SpeedZoneimg = ImageIO.read(new File("SpeedZone.png"));
            StarModeimg = ImageIO.read(new File("StarMode.png"));
            SuperBrick1 = ImageIO.read(new File("SuperBrick1.png"));
            SuperBrick2 = ImageIO.read(new File("SuperBrick2.png"));
            SuperBrick3 = ImageIO.read(new File("SuperBrick3.png"));
            SuperBrick4 = ImageIO.read(new File("SuperBrick4.png"));
            SuperStarBall = ImageIO.read(new File("SuperStarBall.png"));
            DecayModeimg = ImageIO.read(new File("DecayMode.png"));
            CannonModeimg = ImageIO.read(new File("CannonMode.png")); 
            ScoreMultiplierimg = ImageIO.read(new File("ScoreMultiplier.png"));
            IncreaseSizeimg = ImageIO.read(new File("IncreaseSize.png"));
            MoreBallsimg = ImageIO.read(new File("MoreBalls.png"));
            PaddleBotModeimg = ImageIO.read(new File("PaddleBotMode.png"));
            InvertControlsimg = ImageIO.read(new File("InvertControls.png"));
            Updated = ImageIO.read(new File("bg2.png"));
            titlemusic = new AudioStream(new FileInputStream("Lost.wav"));
        }
        catch(IOException | FontFormatException e)
        {
        }
        ball = new Ball(950,750,25,6,6,ballimg);
        paddle = new Paddle(960,940,120,10,Paddleimg,30);
        bricks = new Brick[14][12];
        int x = 300;
        int y = 150;
        levels.add(SuperBrick4);
        levels.add(SuperBrick3);
        levels.add(SuperBrick2);
        levels.add(SuperBrick1);
        zone = new SpeedZone(950,650,150,75,SpeedZoneimg,3); //950,500,400,75,Color.yellow,3
        l1 = new LaserPaddle(850,100,100,35,LaserPaddleimg,3);
        laser1 = new Laser(l1.getX()+l1.getWidth()/2-4,l1.getY()+l1.getHeight(),10,500,laserimg);
        int val = 0;
        for(int i = 0;i<bricks.length;i++)
        {
            for(int j = 0; j<bricks[i].length;j++)
            {
                bricks[i][j] = new Brick(x,y,100,25,RegularBrickimg);
                int BrickChance = (int)(4* Math.random() + 1); //determines what type of brick it will be
                if(BrickChance  <=2 && BrickChance!= 0)
                {
                    int rando = (int)(4*Math.random()); 
                    if(rando<=0)
                    {
                        val = 1;
                    }
                    if(rando > 0)
                    {
                        val = rando;
                    }
                    bricks[i][j] = new SuperBrick(x,y,100,25,levels.get(rando),val,levels); //levels.get(rando)
                    int m = (int)(1* Math.random() + 1);
                }
                int specialChance = (int)(6*Math.random()+1); //6*1
                int powerUpChance = 0;
                if(specialChance == 1)
                {
                    int SpecBrick = (int)(6*Math.random() + 1);
                    if(SpecBrick == 2) //ExplosiveBrick
                    {
                        bricks[i][j] = new ExplosiveBrick(x,y,100,25,ExplosiveBrickimg,1,levels,false); //replace levels with an arraylist of images
                    }
                    if(SpecBrick == 1) //SpeedBrick
                    {
                        bricks[i][j] = new SpeedBrick(x,y,100,25,SpeedBrickimg,1,levels); // //replace levels with an arraylist of images
                    }
                    if(SpecBrick == 3) //SlowBrick
                    {
                        bricks[i][j] = new SlowBrick(x,y,100,25,SlowBrickimg,1,levels); // //replace levels with an arraylist of images
                    }
                }
                powerUpChance = (int)(23*Math.random() +1); //11*1
                if(powerUpChance == 1)
                {
                    bricks[i][j] = new PowerUpBrick(x,y,100,25,PowerUpBrickimg,1,levels);
                    int m = (int)(8* Math.random() + 1); //8*1
                    if(m == 1) //StarMode
                    {
                        PowerUpBall entry = new p2(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,StarModeimg);
                        powerups.add(entry);
                    }
                    if(m == 6)//adds 2 balls
                    {
                        PowerUpBall entry = new PowerUpBall(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,MoreBallsimg);
                        powerups.add(entry);
                    }
                    if(m == 7) //increases the size of the ball
                    {
                        PowerUpBall entry = new p1(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,IncreaseSizeimg);
                        powerups.add(entry);
                    }
                    if(m == 2) //cannnon
                    {
                        PowerUpBall entry = new p3(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,CannonModeimg);
                        powerups.add(entry);
                    }
                    if(m == 3)//score multiplier
                    {
                        PowerUpBall entry = new p4(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,ScoreMultiplierimg);
                        powerups.add(entry);
                    }
                    if(m == 8) //decay
                    {
                        PowerUpBall entry = new p5(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,DecayModeimg);
                        powerups.add(entry);
                    }
                    if(m == 4) //invert controls
                    {
                        PowerUpBall entry = new p6(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,InvertControlsimg);
                        powerups.add(entry);
                    }
                    if(m == 5) //follow ball
                    {
                        PowerUpBall entry = new p7(bricks[i][j].getX(),bricks[i][j].getY(),40,4,4,PaddleBotModeimg);
                        powerups.add(entry);
                    }
                }
                int invisChance = (int)(11*Math.random()+1);
                if(invisChance == 11 && bricks[i][j].getClass()!=PowerUpBrick.class)
                {
                    bricks[i][j] = new Brick(x,y,100,25,RegularBrickimg);
                    bricks[i][j].setVisible(false);
                }
                x+=105;
            }
            x = 300;
            y+=30;
        }
        int tt = 5;
        for(int i = 0;i<3;i++)
        {
            lives.add(new Ball(tt,100,30,0,0,Livesimg));
            tt+=40;
        }
        
        //creates the title balls that are seen on the screens
        for(int i = 0;i<50; i++)
        {
            int xx = (int)(1800*Math.random() + 100);
            int yy = (int)(900*Math.random() + 100);
            int dx1 = (int)(11*Math.random()-3);
            int dy1 = (int)(11*Math.random()-3);
            if(dx1 == 0)
            {
                dx1 = 5;
            }
            if(dy1 == 0)
            {
                dy1 = -5;
            }
            TitleBalls.add(new SynthBalls(xx,yy,15,dx1,dy1,Color.lightGray));
        }
        //creates the demo balls
        {
            DemoPower1 = new PowerUpBall(225,550,40,4,2,MoreBallsimg); //second row: Adds 2 balls
            DemoPower2 = new p1(700,150,40,4,2,IncreaseSizeimg); //first row:Increase Size
            DemoPower3 = new p2(225,150,40,4,2,StarModeimg); //first row: StarMode
            DemoPower4 = new p3(1175,150,40,4,2,CannonModeimg); //first row: Cannon
            DemoPower5 = new p4(1675,150,40,4,2,ScoreMultiplierimg); //first row: Score Multiplier
            DemoPower6 = new p5(700,550,40,4,2,DecayModeimg); //second row: Decay
            DemoPower7 = new p6(1175,550,40,4,2,InvertControlsimg); //second row: Invert
            DemoPower8 = new p7(1675,550,40,4,2,PaddleBotModeimg); //second row: Follow Ball
        }
        addKeyListener(this);
        timer = new 
        Timer(1,this); //every 10 ms, call the actionPerformed method and paint method
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer.start();
        /////////
        bricks[0][0].setNumScore(0);
        play = true;
    }

    public void reset()
    {
        //resets all the variables when you press the play again button
        gss = true;
        bricks[0][0].setNumScore(0);
        bombs = false;
        armed = false;
        catched = false;
        justCaught = false;
        ballSpeedX = 0;
        ballSpeedY = 0;
        decayTimer = 0;
        paddleBotMode = false;
        Trigger = false;
        ZoneTimer = 0;
        hasCollided = false;
        lcond = false;
        MultiplyTimer = 0;
        SCORE = 0;
        temp = 0;
        isDouble = false;
        laserSpawn = false;
        chaosMode = false;
        easyMode = false;
        moveBB = false;
        hardMode = false;
        ballCreation = false;
        increaseLength = 0;
        canActivate = false;
        powerCount = 3;
        powerups.clear();
        newBalls.clear();
        lives.clear();
    }

    public static void setEasyMode(boolean s)
    {
        easyMode = s;
    }

    public static void setLength()
    {
        increaseLength = 0;
    }

    public static void setActivate()
    {
        canActivate = false;
    }
}
//new