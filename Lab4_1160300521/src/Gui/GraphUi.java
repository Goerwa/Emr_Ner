package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Exception.ExceptionofInput;
import graph.Graph;
import vertex.Word;

public class GraphUi extends JFrame implements ActionListener{
    Graph graph;
    JTextField jtf1,jtf2,jtf3; 
    JLabel jl1,jl2,jl3,jl4,jl5;
    JPanel jp5,jp4,jp1,jp2,jp3,jp6;
    JButton jb1 = null;
    JButton jb2 = null;
    JCheckBox jrb1,jrb2,jrb3,jrb4;
    public GraphUi(Graph g) {
	this.graph = g;
        jl1 = new JLabel("请选操作类型：");
        jp1 = new JPanel();
        jp1.add(jl1);
        this.add(jp1);
        
        jl2 = new JLabel("类型");
        jtf1 = new JTextField(10);
        jp2 = new JPanel();
        jp2.add(jl2);
        jp2.add(jtf1);
        this.add(jp2);
        
        jl3 = new JLabel("名称");
        jtf2 = new JTextField(10);
        jp3 = new JPanel();
        jp3.add(jl3);
        jp3.add(jtf2);
        this.add(jp3);
        
        jl4 = new JLabel("权值");
        jtf3 = new JTextField(10);
        jp4 = new JPanel();
        jp4.add(jl4);
        jp4.add(jtf3);
        this.add(jp4);
        
        jrb1 = new JCheckBox("加入点");
        jrb2 = new JCheckBox("加入边");
        jrb3 = new JCheckBox("删除点");
        jrb4 = new JCheckBox("删除边");
        jp5 = new JPanel();
        jp5.add(jrb1);
        jp5.add(jrb2);
        jp5.add(jrb3);
        jp5.add(jrb4);
        this.add(jp5);
        
        jb1 = new JButton("确定");
        jb2 = new JButton("清空");
        jp6 = new JPanel();
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jp6.add(jb1);
        jp6.add(jb1);
        this.add(jp6);
        
        this.setLayout(new GridLayout(8,1));
        this.setSize(400,400);         
	this.setLocation(400, 200); 
	//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置当关闭窗口时，保证JVM也退出 
        this.setVisible(true);  
        this.setResizable(true);
    }
    public Graph getgraph() {
	return this.graph;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand()=="确定") {
	    String str1,str2,str3;
	    str1 = jtf1.getText();
	    str2 = jtf2.getText();
	    str2 = jtf3.getText();
	    if(jrb1.isSelected()) {
		Word w = new Word(str2);
		try {
		    graph.addVertex(w);
		} catch (ExceptionofInput e1) {
		    System.out.println(e1);
		}
		System.out.println(graph.vertices().size());
	    }
	
	}
    }
    
}
