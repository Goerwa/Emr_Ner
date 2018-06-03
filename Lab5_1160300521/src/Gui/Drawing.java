package Gui;
import java.awt.*;  
import javax.swing.*;  
public class Drawing extends JFrame{  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
      Drawing c1= new Drawing();  
    }  
    MyPanel mp=null;  
    public Drawing(){  
        MyPanel mp=new MyPanel();  
        this.add(mp);  
        this.setSize(400,300);  
        this.setVisible(true);  
    }  
}  
class MyPanel extends JPanel{  
//  重写jpanel的方法  
    public void paint(Graphics g){  
//      调用父类完成初始化  
        super.paint(g);  
//      得到图片  
        Image im=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("Users/goerwa/learngit/Lab5_1160300521/src/Gui/g.jpg"));  
        g.drawImage(im, 50, 50, 240, 190, this);  
    }  
}  