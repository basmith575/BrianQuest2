package game;

import java.awt.Color;
import javax.swing.*;

public class MapEditorFrame
{
	public MapEditorFrame()
	{
		JFrame frame = new JFrame();
		frame.add(new MapEditor());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Editor");
		frame.setSize(1500+6,950+28);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args)
	{
		new MapEditorFrame();
	}
}