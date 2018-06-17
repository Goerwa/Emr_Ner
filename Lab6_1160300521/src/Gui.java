import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {
  private static final int WIDTH = 400;
  private static final int HEIGHT = 650;

  public static void main(String[] args) {
    Gui myGui = new Gui();
    myGui.ShowWindow();
  }


  // 构造函数，初始化程序界面
  public Gui() {
    this.setSize(WIDTH, HEIGHT);
    this.setTitle("monkey and ladder");
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }


  // 开启游戏线程
  private void ShowWindow() {
    GamePanel gamePanel = new GamePanel();
    this.add(gamePanel);
    Thread th = new Thread(gamePanel);
    th.start();

  }
}


class GamePanel extends JPanel implements Runnable {
  
  private void initial() {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("src/source/test.txt"));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String headline;
    try {
      while ((headline = br.readLine()) != null) {
        List<Monkey> monkeys = new ArrayList<>();
        List<Ladder> ladders = new ArrayList<>();
        headline = headline.replace(" ", "").replaceAll("=", "").replaceAll("[a-zA-Z]", "");
        String[] parameter = headline.split(";");
        int n = Integer.parseInt(parameter[0]);
        int h = Integer.parseInt(parameter[1]);
        int t = Integer.parseInt(parameter[2]);
        int N = Integer.parseInt(parameter[3]);
        int k = Integer.parseInt(parameter[4]);
        int MV = Integer.parseInt(parameter[5]);
        MonkeyGenerator mkl = new MonkeyGenerator(N);
        mkl.build(n,h,N,MV,monkeys,ladders);
        for(int i=0;i<n;i++) {
          
        }
      }
    } catch (NumberFormatException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  private void drawladder() {
    
  }
  @Override
  public void run() {

  }
}

class Bullet{  
  
  private int speed;  
  private int poisition_x;  
  private double poisition_y;  
  private int bullet_r;  
  private Color color;  
    
  private static final int UP = 0;  
  private static final int DOWN = 1;  
    
  private static final int ENEMY = 0;  
  private static final int SHOOT = 1;  
    
  public Bullet(){}  
    
  /** 
   *  
   * @param speed 速度 
   * @param r 半径 
   * @param x 横坐标 
   * @param y 纵坐标 
   * @param c 颜色 
   */  
  public Bullet(int speed,int r,int x,int y,Color c){  
      this.speed = speed;  
      this.bullet_r = r;  
      this.poisition_x = x;  
      this.poisition_y = y;  
      this.color = c;  
  }  
    
  public void DrawBullet(Graphics g,int Kinds){  
      g.setColor(color);  
      switch(Kinds){  
        
      case ENEMY:  
          g.fillOval(poisition_x, (int) poisition_y, bullet_r/2, bullet_r/2);  
          break;  
      case SHOOT:{  
          g.setColor(Color.WHITE);  
          g.fillOval(poisition_x, (int) poisition_y, bullet_r/4, bullet_r/4);  
          }  
          break;  
      default:  
          break;  
      }  
        
  }  

    
  public void Move(int flag){  
      if(flag == UP)  
          this.poisition_y--;  
      else  
          this.poisition_y++;  
  }  
    
    
  public int getSpeed() {  
      return speed;  
  }  

  public void setSpeed(int speed) {  
      this.speed = speed;  
  }  

  public int getPoisition_x() {  
      return poisition_x;  
  }  

  public void setPoisition_x(int poisition_x) {  
      this.poisition_x = poisition_x;  
  }  

  public double getPoisition_y() {  
      return poisition_y;  
  }  

  public void setPoisition_y(double bullet_Position) {  
      this.poisition_y = bullet_Position;  
  }  

  public int getBullet_r() {  
      return bullet_r;  
  }  

  public void setBullet_r(int bullet_r) {  
      this.bullet_r = bullet_r;  
  }  

  public Color getColor() {  
      return color;  
  }  

  public void setColor(Color color) {  
      this.color = color;  
  }  
      
    
}  

