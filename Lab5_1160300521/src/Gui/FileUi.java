package Gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import Exception.ExceptionofDirection;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import factory.GraphPoetFactory;
import graph.Graph;
import graph.GraphPoet;

public class FileUi extends JFrame implements ActionListener {
  Graph graph;
  JTextField jtf = null;
  JLabel jl1, jl2, jl3;
  JPanel jp0, jp01, jp1, jp2, jp3;
  JButton jb1 = null;
  JButton jb2 = null;
  JRadioButton jrb1, jrb2, jrb3, jrb4;

  public FileUi(Graph g) {
    this.graph = g;
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

  public Graph getgraph() {
    return this.graph;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "确定") {
      System.out.println(e.getActionCommand());
      String filepath = jtf.getText();
      System.out.println(filepath);
      try {
        graph = (GraphPoet) new GraphPoetFactory().createGraph(filepath);
      } catch (ExceptionofInput e1) {
        System.out.println(e1);
      } catch (ExceptionofUnproperEdge e1) {
        System.out.println(e1);
      } catch (ExceptionofDirection e1) {
        System.out.println(e1);
      }
      JOptionPane.showMessageDialog(null, "成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
      System.out.println(graph.vertices().size());
    } else if (e.getActionCommand() == "清空") {
      System.out.println(e.getActionCommand());
      clear();
    }
  }

  private void clear() {
    jtf.setText("");
  }

}
