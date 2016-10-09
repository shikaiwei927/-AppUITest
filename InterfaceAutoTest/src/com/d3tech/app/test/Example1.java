package com.d3tech.app.test;
import java.awt.*;
import javax.swing.*;

public class Example1 extends JFrame {
	public void CreateJFrame(String title) {
		JFrame jf = new JFrame(title);
		Container container =jf.getContentPane();
		JLabel jl=new JLabel("����һ��JFrame����");
				
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);
		container.setBackground(Color.white);
		jf.setVisible(true);
		jf.setSize(200, 150);
		
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		new Example1().CreateJFrame("����һ��JFrame����");
	}

}
