package game;

import java.awt.Color;
import javax.swing.*;

public class Frame
{
	public Frame()
	{
		JFrame frame = new JFrame();
		frame.add(new Game());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("BrianQuest 2");
		frame.setSize(850+6,650+28);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		/*ImageIcon i = new ImageIcon(getClass().getResource("img/icon.PNG")); TODO: make this icon
		frame.setIconImage(i.getImage());*/
	}
	
	public static void main(String[] args)
	{
		new Frame();
	}
}