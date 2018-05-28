package application;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Gui.FileUi;
import Gui.GraphUi;
import Gui.LogUi;
import Gui.OberserveUi;
import debug.calculator.CalculatorGUI;
import graph.Graph;
import graph.GraphPoet;

public class graphapp extends JFrame implements ActionListener {
  Graph graph;
  JButton jb1, jb2, jb3, jb4;
  JTextField jtf = null;
  JLabel jl1, jl2, jl3;
  JPanel jp0, jp01, jp1, jp2, jp3;
  JRadioButton jrb1, jrb2, jrb3, jrb4;

  public graphapp(Graph graph) {
    this.graph = graph;
    jb1 = new JButton("读取文件");
    jb2 = new JButton("对图的操作");
    jb3 = new JButton("显示图信息");
    jb4 = new JButton("Log相关操作");
    jl1 = new JLabel("请选择操作类型:");
    this.setSize(400, 400);
    jl1.setFont(new java.awt.Font("Dialog", 0, 20));
    // 设置监听
    jb1.addActionListener(this);
    jb2.addActionListener(this);
    jb3.addActionListener(this);
    jb4.addActionListener(this);
    jp1 = new JPanel();
    jp2 = new JPanel();
    this.add(jl1);
    this.add(jb1);
    this.add(jb2);
    this.add(jb3);
    this.add(jb4);
    this.setLayout(new GridLayout(5, 1));
    this.setTitle("graphapp");
    this.setLocation(400, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置当关闭窗口时，保证JVM也退出
    this.setVisible(true);
    this.setResizable(true);

  }

  private void dealfilenext() {
    jtf = new JTextField(10);
    jl1 = new JLabel("输入读取的文件路径：");
    jl2 = new JLabel("路径：");
    jl3 = new JLabel("请选择图类型：");
    jp0 = new JPanel();
    jp01 = new JPanel();
    jp1 = new JPanel();
    jp2 = new JPanel();
    jp3 = new JPanel();
    jrb1 = new JRadioButton("GraphPoet");
    jrb2 = new JRadioButton("SocialNetwork");
    jrb3 = new JRadioButton("NetworkTopology");
    jrb4 = new JRadioButton("MovieGraph");
    this.add(jl3);
    jp0.add(jrb1);
    jp0.add(jrb2);
    jp01.add(jrb3);
    jp01.add(jrb4);
    jb1 = new JButton("确定");
    jb2 = new JButton("清空");
    jp3.add(jb1);
    jp3.add(jb2);
    jb1.addActionListener(this);
    jb2.addActionListener(this);
    jp2.add(jl1);
    jp1.add(jl2);
    jp1.add(jtf);
    this.add(jp0);
    this.add(jp01);
    this.add(jp2);
    this.add(jp1);
    this.add(jp3);
    this.setLayout(new GridLayout(6, 1));
    this.setSize(400, 400);
    this.setLocation(400, 200);
    // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置当关闭窗口时，保证JVM也退出
    this.setVisible(true);
    this.setResizable(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "读取文件") {
      System.out.println(e.getActionCommand());
      FileUi f = new FileUi(graph);
    } else if (e.getActionCommand() == "对图的操作") {
      System.out.println(e.getActionCommand());
      GraphUi g = new GraphUi(graph);
    } else if (e.getActionCommand() == "显示图信息") {
      System.out.println(e.getActionCommand());
      OberserveUi o = new OberserveUi();
    } else if (e.getActionCommand() == "Log相关操作") {
      System.out.println(e.getActionCommand());
      LogUi l = new LogUi();
    }
  }

  public static void main(String[] args) {
    // System.out.println("aaaa");
    // graphapp g = new graphapp();
  }
}
