package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MapWriter {
	
	static Map map;
	static int states[];
	
	static int index;
	
	static int height=0;
	static int width=0;
	static int total=0;
	
	public static void main(String[] args)
	{		
		int[] states = new int[100];
		map = new TestTown(states);
		createImage(false);
	}
	
	public static void getMap(int index)
	{
		states = new int[Map.mapStates[index-1]];
		map = Map.makeMap(index, states);
	}
	
	public static void createImage(boolean showWalkable)
	{
		System.out.println("Writing map " + map.name + "...");
		
		height = map.tile[0].length;
		width = map.tile.length;
		total = height*width;
		
		File eventFile = null;
		BufferedImage eventImage = null;
		
		File[][] tileFiles = new File[width][height];
		
        for (int i=0; i <width; i++)
        {
        	for(int j=0; j <height; j++)
        	{
        		try
        		{
        			//NOTE: I don't feel like improving this, so for now it will only work for me
        			tileFiles[i][j] = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\tile\\tile" + map.tile[i][j].type + ".PNG");
        		}
        		catch(Exception e)
        		{
        			System.out.println(e.getMessage());
        		}
        	}  
        }  
        
        BufferedImage[][] buffTileImages = new BufferedImage[width][height];
        
        for(int i=0; i<width; i++)
        {
        	for(int j=0; j<height; j++)
        	{
        		try
        		{
        			buffTileImages[i][j] = ImageIO.read(tileFiles[i][j]);
        		}
        		catch(Exception e)
        		{
        			System.out.println("I am Error");
        		}
        	}
        }
        
        int type = buffTileImages[0][0].getType(); 
        
        BufferedImage finalImg = new BufferedImage(25*width, 25*height, type); 
        
        for(int i=0; i<width; i++)
        {
        	for(int j=0; j<height; j++)
        	{
        		finalImg.createGraphics().drawImage(buffTileImages[i][j], 25*i, 25*j, 25, 25, null);
        		
        		if(showWalkable)
        		{
        			finalImg.createGraphics().setColor(Color.YELLOW);
            		if(map.tile[i][j].walkable) finalImg.createGraphics().drawRect(25*i+10,25*j+10,5,5);
        		}
        	}
        }
        
        for(int i=0; i<width; i++)
        {
        	for(int j=0; j<height; j++)
        	{
        		int eventType = map.tile[i][j].event.type;
        		
        		if(eventType != Event.PORTAL && eventType != Event.NOEVENT)
        		{
        			if(eventType == Event.CHEST)
        			{
        				eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\mapChest0.png");
        			}
        			else
        			{
        				try
        				{
	        				if(map.tile[i][j].event.name.equals("Sign")) eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\npc\\mapSign.PNG");
	        				else eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\npc\\map" + map.tile[i][j].event.imageName + map.tile[i][j].event.dir + ".PNG");
        				}
        				catch(Exception e)
        				{
        					eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\alert.PNG");
        				}
        			}
        			try
        			{
        				eventImage = ImageIO.read(eventFile);
        			}
        			catch(Exception e)
        			{
        				eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\alert.PNG");
        				
        				try
        				{
        					eventImage = ImageIO.read(eventFile);
        				}
        				catch(Exception e2)
        				{
        					System.out.println("This message should never appear.");
        				}
        			}
        			
        			finalImg.createGraphics().drawImage(eventImage,25*i,25*j,25,25,null);
        		}
        		else if(map.tile[i][j].thing.type != Thing.NOTHING)
        		{
        			eventFile = new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\thing\\mapThing" + map.tile[i][j].thing.type + ".PNG");
        			try
        			{
        				eventImage = ImageIO.read(eventFile);
        			}
        			catch(Exception e)
        			{
        				System.out.println("I am thing Error... map # " + index + " (" + i + "," + j + ")");
        			}
        			
        			finalImg.createGraphics().drawImage(eventImage,25*i,25*j,25*Math.max(map.tile[i][j].thing.width,1),25*Math.max(map.tile[i][j].thing.height,1),null);
        		}
        	}
        }
        
        try
        {
        	ImageIO.write(finalImg, "PNG", new File("C:\\Users\\Brian\\workspace\\BrianQuest2\\src\\game\\img\\maps\\map" + map.id + " - " + map.name + ".PNG")); 
        }
        catch(Exception e)
        {
        	System.out.println("I am also Error");
        }
        
        System.out.println("Done writing " + map.name);
	}
}