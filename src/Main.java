import javax.swing.JFrame;

public class Main
{
    public static void main(String...args)
    {
        JFrame frame = new JFrame();
        
        frame.setBounds(0,0,1920,1000); 
        frame.setTitle("BrickBreaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BrickBreakerGame game1 = new BrickBreakerGame();
        
        frame.add(game1);
        frame.setVisible(true);
    }
}