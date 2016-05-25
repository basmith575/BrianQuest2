package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.sound.sampled.*;

public class MapEditor extends JPanel implements ActionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * States
	 */
	public static final int SELECTINGTILE = 0;
	public static final int ADDINGTILE = 1;
	public static final int SELECTINGTHING = 2;
	public static final int ADDINGTHING = 3;
	public static final int VIEWINGMAP = 4;
	public static final int FILLTILERECT = 5;
	public static final int FILLTHINGRECT = 6;
	public static final int TOOLMENU = 7;
	public static final int SELECTTEMPLATE = 8;
	public static final int RESIZEMAPX = 9;
	public static final int RESIZEMAPY = 10;
	public static final int SELECTMAP = 11;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static int state;
	public static int prevState;
	
	static Map curMap;
	static int curX, curY;
	static int topX, topY;
	static int curTileIndex, curThingIndex;
	static int topTileIndex, topThingIndex;
	static int fillStartX, fillStartY;
	static int curTool;
	static int curTemplate;
	static int paletteState; //1-Tile, 2-Thing
	static int newWidth, newHeight;
	static int mapIndex, cursorAlign;
	
	public static Image tileImage[];
	public static Image thingImage[];

	public int[] sortedTiles;
	public int[] sortedTilesIndex;
	public int[] sortedThings;
	public int[] sortedThingsIndex;
	
	public static ArrayList<Image[]> movingTileImage;
	public static ArrayList<Image[]> movingThingImage;
	
	static int tileState = 0;
	static ArrayList<Integer> movingTileList;
	static ArrayList<Integer> movingThingList;
	
	public static ImageIcon icon;
	public static Image img;
	
	Color lightGray;
	Font arial10, arialBold10, arialBold12, arialBold14, arialBold16, arialBold18, arialBold20, arialBold28;
	
	public boolean holdingX;
	public boolean holdingUp;
	public boolean holdingDown;
	public boolean holdingRight;
	public boolean holdingLeft;
	
	static int frameCount; //25 frames per second (arbitrary)
	
	/***
	 * Cross-System Compatibility
	 */
	public static String FileSeparator = "";
	public static String OS = null;

	public MapEditor()
	{
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		setFocusable(true);
		requestFocus();
		
		setDoubleBuffered(true);
		
		lightGray = new Color(210,210,210);
		arialBold28 = new Font("Arial",Font.BOLD,28);
		arialBold20 = new Font("Arial",Font.BOLD,20);
		arialBold18 = new Font("Arial",Font.BOLD,18);
		arialBold16 = new Font("Arial",Font.BOLD,16);
		arialBold14 = new Font("Arial",Font.BOLD,14);
		arialBold12 = new Font("Arial",Font.BOLD,12);
		arialBold10 = new Font("Arial",Font.BOLD,11);
		arial10 = new Font("Arial",Font.PLAIN,10);
		
		movingTileList = Tile.movingTileList();
		movingThingList = Thing.movingThingList();
		
		tileImage = new Image[Tile.NUMTILES];
		for(int i=0; i<Tile.NUMTILES; i++)
		{
			if(!movingTileList.contains(i))
			{
				icon = new ImageIcon(getClass().getResource("img/tile/tile" + i + ".PNG"));
				tileImage[i] = icon.getImage();
			}
			else
			{
				icon = new ImageIcon(getClass().getResource("img/tile/tile" + i + "_0.PNG"));
				tileImage[i] = icon.getImage();
			}
		}
		
		thingImage = new Image[Thing.NUMTHINGS];
		for(int i=0; i<Thing.NUMTHINGS; i++)
		{
			if(!movingThingList.contains(i))
			{
				icon = new ImageIcon(getClass().getResource("img/thing/thing" + i + ".PNG"));
				thingImage[i] = icon.getImage();
			}
			else
			{
				icon = new ImageIcon(getClass().getResource("img/thing/thing" + i + "_0.PNG"));
				thingImage[i] = icon.getImage();
			}
		}
		
		movingTileImage = new ArrayList<Image[]>();
		for(int i=0; i<movingTileList.size(); i++)
		{
			int numFrames = Tile.numAnimationStates(movingTileList.get(i));
			Image[] altImage = new Image[numFrames];
			for(int j=0; j<numFrames; j++)
			{
				icon = new ImageIcon(getClass().getResource("img/tile/tile" + movingTileList.get(i) + "_" + j + ".PNG"));
				altImage[j] = icon.getImage();
			}
			
			movingTileImage.add(altImage);
		}
		
		movingThingImage = new ArrayList<Image[]>();
		for(int i=0; i<movingThingList.size(); i++)
		{
			int numFrames = Thing.numAnimationStates(movingThingList.get(i));
			Image[] altImage = new Image[numFrames];
			for(int j=0; j<numFrames; j++)
			{
				icon = new ImageIcon(getClass().getResource("img/thing/thing" + movingThingList.get(i) + "_" + j + ".PNG"));
				altImage[j] = icon.getImage();
			}
			
			movingThingImage.add(altImage);
		}
		
		getsortedTiles();
		getsortedThings();
		
		checkOS();
		
		gameLoop();
		
		setState(SELECTMAP);
	}
	
	public void gameLoop()
	{
	    new Thread()
	    {
	        public void run()
	        {
	        	
	            while(true)
	            {
	            	try
	                {	                	
	                    Thread.sleep(40);
	                }
	                catch (InterruptedException e)
	                {
	                    e.printStackTrace();
	                }

                	try
                	{
	                	frameCount++;
	                	
	                	if(state != SELECTMAP)
	                	{
	                		updateTileAndThingTimers();
	                	}
                	}
                	catch(Exception e) //if frameCount somehow gets too large... should be impossible, would take 2.7 years, but just to be safe
                	{
                		System.out.println("frameCount too large");
                		
                		frameCount = 0;
                	}
                	
                	repaint();
                }
	        }
	    }.start();
	}
	
	public void updateTileAndThingTimers()
	{
		for(int i=topX; i<topX+26; i++)
		{
			for(int j=topY; j<topY+19; j++)
			{
				if(Tile.hasOwnTimer(curMap.tile[i][j].type))
				{
					curMap.tile[i][j].timer++;
				}
				
				if(Thing.hasOwnTimer(curMap.tile[i][j].thing.type))
				{
					curMap.tile[i][j].thing.timer++;
				}
			}
		}
	}
	
	public void paint(Graphics g)
	{
		if(state == SELECTMAP)
		{
			drawMapSelect(g);
		}
		else
		{
			drawMapEditor(g);
		}
	}
	
	public void setState(int setState)
	{
		prevState = state;
		state = setState;
		
		if(state == SELECTINGTILE || state == ADDINGTILE)
		{
			paletteState = 1;
		}
		else if(state == SELECTINGTHING || state == ADDINGTHING)
		{
			paletteState = 2;
		}
	}
	
	public void drawTile(int tile, int i, int j, int timer, Graphics g)
	{
		try
		{
			if(Tile.movingTileList().contains(tile))
			{
				int numAnimationStates = Tile.numAnimationStates(tile);
				int framesPerAnimationState = Tile.framesPerAnimationState(tile);
				
				int actualAnimationState = (timer / framesPerAnimationState) % numAnimationStates;
				
				g.drawImage(movingTileImage.get(movingTileList.indexOf(tile))[actualAnimationState],i*50,j*50,this);
			}
			else
			{
				g.drawImage(tileImage[tile],i*50,j*50,this);
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to draw a tile " + tile + " at " + i + ", " + j);
			g.drawImage(tileImage[Tile.BLACK],i*50,j*50,this);
		}
	}
	
	public void drawThing(int thing, int i, int j, int timer, Graphics g)
	{
		try
		{
			if(Thing.movingThingList().contains(thing))
			{
				int numAnimationStates = Thing.numAnimationStates(thing);
				int framesPerAnimationState = Thing.framesPerAnimationState(thing);
				
				int actualAnimationState = (timer / framesPerAnimationState) % numAnimationStates;

				g.drawImage(movingThingImage.get(movingThingList.indexOf(thing))[actualAnimationState],i*50,j*50,this);
			}
			else
			{
				g.drawImage(thingImage[thing],i*50,j*50,this);
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to draw thing " + thing + " at " + i + ", " + j);
		}
	}
	
	public void draw(String name, int x, int y, Graphics g)
	{
		try
		{			
			icon = new ImageIcon(getClass().getResource("img/" + name + ".PNG")); //TODO: check for lowercase png as well?
			img = icon.getImage();
			g.drawImage(img,x,y,this);
		}
		catch(Exception e)
		{
			System.out.println("failed to draw image " + name + " at " + x + ", " + y);
		}
	}
	
	public void draw(String name, int x, int y, int h, int w, Graphics g)
	{
		try
		{
			icon = new ImageIcon(getClass().getResource("img/" + name + ".PNG"));
			img = icon.getImage();
			g.drawImage(img,x,y,h,w,this);
		}
		catch(Exception e)
		{
			icon = new ImageIcon(getClass().getResource("img/" + name + ".png"));
			img = icon.getImage();
			g.drawImage(img,x,y,h,w,this);
		}
	}
	
	public void draw(String name, String folder, int x, int y, Graphics g)
	{
		try
		{			
			icon = new ImageIcon(getClass().getResource("img/" + folder + "/" + name + ".PNG"));
			img = icon.getImage();
			
			g.drawImage(img,x,y,this);
		}
		catch(Exception e)
		{
			System.out.println("failed to draw " + name + ": " + e.getMessage());
		}
	}
	
	public void draw(String name, String folder, int x, int y, int h, int w, Graphics g)
	{
		try
		{
			icon = new ImageIcon(getClass().getResource("img/" + folder + "/" + name + ".PNG"));
			img = icon.getImage();
			g.drawImage(img,x,y,h,w,this);
		}
		catch(Exception e)
		{
			icon = new ImageIcon(getClass().getResource("img/" + folder + "/" + name + ".png"));
			img = icon.getImage();
			g.drawImage(img,x,y,h,w,this);
		}
	}
	
	public void fillTileRect()
	{
		int x1, x2, y1, y2;
		x1 = Math.min(fillStartX, curX);
		x2 = Math.max(fillStartX, curX);
		y1 = Math.min(fillStartY, curY);
		y2 = Math.max(fillStartY, curY);
		
		int tileType = sortedTiles[curTileIndex];
		
		if(tileType == Tile.WATER || tileType == Tile.DARKWATER || tileType == Tile.VERYDARKWATER)
		{
			curMap.fillWater(x1, y1, x2, y2, tileType);
		}
		else
		{
			curMap.fillRect(x1, y1, x2, y2, tileType);
		}
		
		setState(ADDINGTILE);
	}
	
	public void convertTiles(int x1, int y1, int x2, int y2, int oldTile, int newTile)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(curMap.tile[i][j].type == oldTile)
				{
					curMap.tile[i][j].type = newTile;
				}
			}
		}
	}
	
	public void convertThings(int x1, int y1, int x2, int y2, int oldThing, int newThing)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(curMap.tile[i][j].thing.type == oldThing)
				{
					curMap.tile[i][j].thing.type = newThing;
				}
			}
		}
	}
	
	public void fillThingRect()
	{
		int x1, x2, y1, y2;
		x1 = Math.min(fillStartX, curX);
		x2 = Math.max(fillStartX, curX);
		y1 = Math.min(fillStartY, curY);
		y2 = Math.max(fillStartY, curY);
		
		curMap.fillThingRect(x1, y1, x2, y2, sortedThings[curThingIndex]);
		
		setState(ADDINGTHING);
	}
	
	public void drawMapSelect(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1500,950);
		
		g.setColor(lightGray);
		g.fillRect(500,100,500,700);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold28);
		g.drawString("Select Map",685,140);
		
		int top = Math.max(0,mapIndex-cursorAlign);
		int bottom = Math.min(mapIndex+10,Map.numMaps-1);
		g.setFont(arialBold20);
		
		for(int i=top; i<=bottom; i++)
		{
			g.drawString("[" + i + "] - " + Map.getMapName(i),600,300+50*(i-top));
		}
		
		draw("pointer",560,280+50*(mapIndex-top),g);
	}

	public void drawMapEditor(Graphics g)
	{		
		for(int i=topX; i<topX+26; i++)
		{
			for(int j=topY; j<topY+19; j++)
			{
				if(Tile.hasOwnTimer(curMap.tile[i][j].type))
				{
					drawTile(curMap.tile[i][j].type,i-topX,j-topY,curMap.tile[i][j].timer,g);
				}
				else
				{
					drawTile(curMap.tile[i][j].type,i-topX,j-topY,frameCount,g);
				}
			}
		}
		
		for(int i=topX-5; i<topX+26; i++)
		{
			for(int j=topY-5; j<topY+19; j++)
			{
				if(i >= 0 && j >= 0)
				{
					if(curMap.tile[i][j].thing.type != Thing.NOTHING)
					{
						if(i > 0)
						{
							if(curMap.tile[i][j].thing.type == Thing.BIGROCK && !curMap.tile[i-1][j].isWater()
									&& curMap.tile[i][j].isWater())
							{
								drawThing(Thing.SHOREWEST,i-topX,j-topY,0,g);
							}
						}
						
						if(i > 0 && j > 0 && (curMap.tile[i][j].thing.type == Thing.ROCK || curMap.tile[i][j].thing.type == Thing.BIGROCK
								|| curMap.tile[i][j].thing.type == Thing.ICEBERG || curMap.tile[i][j].thing.type == Thing.BIGICEBERG))
						{
							if(curMap.tile[i][j].isWater() && (!curMap.tile[i+1][j].isWater() && curMap.tile[i+1][j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREEAST,i-topX,j-topY,0,g);
							}
							
							if(curMap.tile[i][j].isWater() && (!curMap.tile[i-1][j].isWater() && curMap.tile[i-1][j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREWEST,i-topX,j-topY,0,g);
							}
							
							if(curMap.tile[i][j].isWater() && (!curMap.tile[i][j+1].isWater() && curMap.tile[i][j+1].type != Tile.ICE))
							{
								drawThing(Thing.SHORESOUTH,i-topX,j-topY,0,g);
							}
							
							if(curMap.tile[i][j].isWater() && (!curMap.tile[i][j-1].isWater() && curMap.tile[i][j-1].type != Tile.ICE))
							{
								drawThing(Thing.SHORENORTH,i-topX,j-topY,0,g);
							}
						}
						
						if(Thing.hasOwnTimer(curMap.tile[i][j].thing.type))
						{
							drawThing(curMap.tile[i][j].thing.type,i-topX,j-topY,curMap.tile[i][j].thing.timer,g);
						}
						else
						{
							drawThing(curMap.tile[i][j].thing.type,i-topX,j-topY,frameCount,g);
						}
					}
					
					//shore correction
					try
					{
						if(i >= 1 && j >= 1 && i < curMap.tile.length-1 && j < curMap.tile[0].length-1)
						{
							if((curMap.tile[i+1][j].thing.type == Thing.SHORENORTH || curMap.tile[i+1][j].thing.type == Thing.SHORENORTHEAST)
									&& (curMap.tile[i][j-1].thing.type == Thing.SHOREEAST || curMap.tile[i][j-1].thing.type == Thing.SHORENORTHEAST))
							{
								drawThing(Thing.SHORENORTHEAST2,i,j,0,g);
							}
							if((curMap.tile[i-1][j].thing.type == Thing.SHORENORTH || curMap.tile[i-1][j].thing.type == Thing.SHORENORTHWEST)
									&& (curMap.tile[i][j-1].thing.type == Thing.SHOREWEST || curMap.tile[i][j-1].thing.type == Thing.SHORENORTHWEST))
							{
								drawThing(Thing.SHORENORTHWEST2,i,j,0,g);
							}
							if((curMap.tile[i+1][j].thing.type == Thing.SHORESOUTH || curMap.tile[i+1][j].thing.type == Thing.SHORESOUTHEAST)
									&& (curMap.tile[i][j+1].thing.type == Thing.SHOREEAST || curMap.tile[i][j+1].thing.type == Thing.SHORESOUTHEAST))
							{
								drawThing(Thing.SHORESOUTHEAST2,i,j,0,g);
							}
							if((curMap.tile[i-1][j].thing.type == Thing.SHORESOUTH || curMap.tile[i-1][j].thing.type == Thing.SHORESOUTHWEST)
									&& (curMap.tile[i][j+1].thing.type == Thing.SHOREWEST || curMap.tile[i][j+1].thing.type == Thing.SHORESOUTHWEST
											|| curMap.tile[i][j+1].thing.type == Thing.BIGROCK))
							{
								drawThing(Thing.SHORESOUTHWEST2,i,j,0,g);
							}
							
							if((curMap.tile[i+1][j].thing.type == Thing.DOCKNORTH || curMap.tile[i+1][j].thing.type == Thing.DOCKNORTHEAST)
									&& (curMap.tile[i][j-1].thing.type == Thing.DOCKEAST || curMap.tile[i][j-1].thing.type == Thing.DOCKNORTHEAST))
							{
								drawThing(Thing.DOCKNORTHEAST2,i,j,0,g);
							}
							if((curMap.tile[i-1][j].thing.type == Thing.DOCKNORTH || curMap.tile[i-1][j].thing.type == Thing.DOCKNORTHWEST)
									&& (curMap.tile[i][j-1].thing.type == Thing.DOCKWEST || curMap.tile[i][j-1].thing.type == Thing.DOCKNORTHWEST))
							{
								drawThing(Thing.DOCKNORTHWEST2,i,j,0,g);
							}
							if((curMap.tile[i+1][j].thing.type == Thing.DOCKSOUTH || curMap.tile[i+1][j].thing.type == Thing.DOCKSOUTHEAST)
									&& (curMap.tile[i][j+1].thing.type == Thing.DOCKEAST || curMap.tile[i][j+1].thing.type == Thing.DOCKSOUTHEAST))
							{
								drawThing(Thing.DOCKSOUTHEAST2,i,j,0,g);
							}
							if((curMap.tile[i-1][j].thing.type == Thing.DOCKSOUTH || curMap.tile[i-1][j].thing.type == Thing.DOCKSOUTHWEST)
									&& (curMap.tile[i][j+1].thing.type == Thing.DOCKWEST || curMap.tile[i][j+1].thing.type == Thing.DOCKSOUTHWEST))
							{
								drawThing(Thing.DOCKSOUTHWEST2,i,j,0,g);
							}
						}
					}
					catch(Exception e)
					{
						System.out.println("Error drawing shore at " + i + ", " + j);
					}
				}
			}
		}
		
		for(int i=topX; i<topX+26; i++)
		{
			for(int j=topY; j<topY+19; j++)
			{
				Event mapEvent = curMap.event(i,j);
				
				int drawX = i + mapEvent.xOffset;
				int drawY = j + mapEvent.yOffset;
				if(drawX < topX || drawX >= topX+26 || drawY < topY || drawY >= topY+26)
				{
					continue;
				}
				
				if(mapEvent.type == Event.PORTAL)
				{
					//draw nothing
				}
				else if(mapEvent.type == Event.NPC)
				{
					if(mapEvent.name.equals("Sign")) draw("mapSign","npc",50*(drawX-topX),50*(drawY-topY),g);
					else if(!mapEvent.imageName.equals("")) draw("map" + mapEvent.imageName + mapEvent.dir,"npc",50*(drawX-topX),50*(drawY-topY),g);
				}
				else if(mapEvent.type == Event.SHOP)
				{
					if(!mapEvent.imageName.equals(""))
					{
						draw("map" + mapEvent.imageName + mapEvent.dir,"npc",50*(drawX-topX),50*(drawY-topY),g);
					}
				}
				else if(mapEvent.type == Event.INNKEEPER)
				{
					draw("map" + mapEvent.imageName + mapEvent.dir,"npc",50*(drawX-topX),50*(drawY-topY),g);
				}
				else if(mapEvent.type == Event.CHEST)
				{
					draw("mapChest" + mapEvent.state,50*(drawX-topX),50*(drawY-topY),g);
				}
				else if(mapEvent.type == Event.ART)
				{
					draw("mapArt",50*(drawX-topX),50*(drawY-topY),g);
				}
				else if(mapEvent.type == Event.SAVEPOINT)
				{
					draw("mapSavePoint",50*(drawX-topX),50*(drawY-topY),g);
				}
			}
		}
		
		draw("mapEditorCursor",50*(curX-topX),50*(curY-topY),g);
		
		if(state == FILLTILERECT || state == FILLTHINGRECT)
		{
			draw("mapEditorCursor",50*(fillStartX-topX),50*(fillStartY-topY),g);
		}
		
		drawPalette(g);
		
		if(state == TOOLMENU || state == SELECTTEMPLATE || state == RESIZEMAPX || state == RESIZEMAPY)
		{
			drawToolMenu(g);
		}
	}
	
	public void drawToolMenu(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(400,200,500,500);
		g.setColor(lightGray);
		g.fillRect(405,205,490,490);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold28);
		
		if(state == TOOLMENU)
		{
			g.drawString("Tools",610,240);
			
			g.setFont(arialBold20);
			g.drawString("Select Template",500,300);
			g.drawString("Resize Map",500,350);
			g.drawString("Bucket Fill (curX,curY,width,height)",500,400);
			g.drawString("Select Current Tile",500,450);
			g.drawString("Select Current Thing",500,500);
			g.drawString("Convert Shore/Dock",500,550);
			g.drawString("Save Map",500,600);
			
			draw("pointer",460,280+50*curTool,g);
		}
		else if(state == SELECTTEMPLATE)
		{
			g.drawString("Templates",610,240);
			
			g.setFont(arialBold20);
			g.drawString("Inside House",500,300);
			g.drawString("Inside Shop",500,350);
			
			draw("pointer",460,280+50*curTool,g);
		}
		else if(state == RESIZEMAPX || state == RESIZEMAPY)
		{
			g.drawString("Resize Map",610,240);
			
			g.setFont(arialBold20);
			g.drawString("Width:",500,300);
			g.drawString(""+newWidth,650,300);
			g.drawString("Height",500,350);
			g.drawString(""+newHeight,650,350);
			
			if(state == RESIZEMAPX)
			{
				draw("pointer",460,280,g);
			}
			else if(state == RESIZEMAPY)
			{
				draw("pointer",460,330,g);
			}
		}
	}
	
	public void drawPalette(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(1300,0,200,950);
		g.setColor(lightGray);
		g.fillRect(1305,5,190,940);
		
		g.setColor(Color.BLACK);
		g.fillRect(1305,5,190,60);
		
		if(paletteState == 1)
		{
			g.setColor(Color.YELLOW);
			g.fillRect(1305,5,92,55);
			g.setColor(Color.GRAY);
			g.fillRect(1403,5,92,55);
		}
		else if(paletteState == 2)
		{
			g.setColor(Color.GRAY);
			g.fillRect(1305,5,92,55);
			g.setColor(Color.YELLOW);
			g.fillRect(1403,5,92,55);
		}
		else
		{
			g.setColor(Color.GRAY);
			g.fillRect(1305,5,92,55);
			g.setColor(Color.GRAY);
			g.fillRect(1403,5,92,55);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold20);
		g.drawString("Tile",1335,40);
		g.drawString("Thing",1420,40);
		
		if(paletteState == 1)
		{
			for(int i=topTileIndex; i<topTileIndex+16; i++)
			{
				if(i >= tileImage.length)
				{
					break;
				}
				
				g.drawImage(tileImage[sortedTiles[i]],1325+100*(i%2),100+100*((i-topTileIndex)/2),this);
				
				g.setFont(arialBold10);
				g.drawString(Tile.getTileName(sortedTiles[i]),1325+100*(i%2),165+100*((i-topTileIndex)/2));
			}
			
			if(state == SELECTINGTILE)
			{
				draw("pointer",1305+100*(curTileIndex%2),115+100*((curTileIndex-topTileIndex)/2),g);
			}
			else
			{
				draw("pointerSel",1305+100*(curTileIndex%2),115+100*((curTileIndex-topTileIndex)/2),g);
			}
		}
		else if(paletteState == 2)
		{
			for(int i=topThingIndex; i<topThingIndex+16; i++)
			{
				if(i >= thingImage.length)
				{
					break;
				}
				
				g.drawImage(thingImage[sortedThings[i]],1325+100*(i%2),100+100*((i-topThingIndex)/2),50,50,this);
				
				g.setFont(arialBold10);
				g.drawString(Thing.getThingName(sortedThings[i]),1325+100*(i%2),165+100*((i-topThingIndex)/2));
			}
			
			if(state == SELECTINGTHING)
			{
				draw("pointer",1305+100*(curThingIndex%2),115+100*((curThingIndex-topThingIndex)/2),g);
			}
			else
			{
				draw("pointerSel",1305+100*(curThingIndex%2),115+100*((curThingIndex-topThingIndex)/2),g);
			}
		}
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		g.drawString("Coord: " + curX + ", " + curY,1340,920);
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		
	}

	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	public void mouseExited(MouseEvent arg0)
	{
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		
	}

	public void mouseReleased(MouseEvent arg0)
	{
		
	}

	public void keyPressed(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_UP)
		{
			pressedUp();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN)
		{
			pressedDown();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			pressedRight();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT)
		{
			pressedLeft();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_X)
		{
			pressedX();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_Z)
		{
			pressedZ();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_SPACE)
		{
			pressedSpace();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		{
			pressedEnter();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DELETE)
		{
			pressedDelete();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_C)
		{
			pressedC();
		}
		else if(evt.getKeyCode() == KeyEvent.VK_I)
		{
			//No tool menu option yet, just do this manually when needed
			
			//convertTiles(x1, y1, x2, y2, oldTile, newTile)
			//convertThings(x1, y1, x2, y2, oldThing, newThing)
			
			convertTiles(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Tile.DARKWATER,Tile.VERYDARKWATER);
			
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORENORTH,Thing.DARKSHORENORTH);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHOREEAST,Thing.DARKSHOREEAST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORESOUTH,Thing.DARKSHORESOUTH);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHOREWEST,Thing.DARKSHOREWEST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORENORTHEAST,Thing.DARKSHORENORTHEAST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORENORTHWEST,Thing.DARKSHORENORTHWEST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORESOUTHWEST,Thing.DARKSHORESOUTHWEST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORESOUTHEAST,Thing.DARKSHORESOUTHEAST);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORENORTHEAST2,Thing.DARKSHORENORTHEAST2);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORENORTHWEST2,Thing.DARKSHORENORTHWEST2);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORESOUTHEAST2,Thing.DARKSHORESOUTHEAST2);
			convertThings(0,0,curMap.getWidth()-1,curMap.getHeight()-1,Thing.SHORESOUTHWEST2,Thing.DARKSHORESOUTHWEST2);
		}
		
		repaint();
	}
	
	public void pressedUp()
	{
		if(state == SELECTINGTILE)
		{
			if(curTileIndex >= 2)
			{
				curTileIndex = curTileIndex - 2;
				
				if(curTileIndex < topTileIndex)
				{
					topTileIndex = curTileIndex;
					if(topTileIndex % 2 == 1) topTileIndex--;
				}
			}
		}
		else if(state == SELECTINGTHING)
		{
			if(curThingIndex >= 2)
			{
				curThingIndex = curThingIndex - 2;
				
				if(curThingIndex < topThingIndex)
				{
					topThingIndex = curThingIndex;
					if(topThingIndex % 2 == 1) topThingIndex--;
				}
			}
		}
		else if(state == ADDINGTILE || state == ADDINGTHING || state == VIEWINGMAP || state == FILLTILERECT || state == FILLTHINGRECT)
		{
			if(curY > 0)
			{
				curY--;
				
				if(curY < topY)
				{
					topY--;
				}
			}
		}
		else if(state == TOOLMENU)
		{
			curTool--;
			
			if(curTool < 0)
			{
				curTool = 6;
			}
		}
		else if(state == SELECTTEMPLATE)
		{
			curTemplate--;
			
			if(curTemplate < 0)
			{
				curTemplate = 1;
			}
		}
		else if(state == RESIZEMAPY)
		{
			setState(RESIZEMAPX);
		}
		else if(state == SELECTMAP)
		{
			if(mapIndex > 0)
			{
				mapIndex--;
				
				if(cursorAlign > 0) cursorAlign--;
			}
		}
	}
	
	public void pressedDown()
	{
		if(state == SELECTINGTILE)
		{
			if(curTileIndex < tileImage.length - 2)
			{
				curTileIndex = curTileIndex + 2;
				
				if(curTileIndex >= topTileIndex + 16)
				{
					topTileIndex = topTileIndex + 2;
				}
			}
		}
		else if(state == SELECTINGTHING)
		{
			if(curThingIndex < thingImage.length - 2)
			{
				curThingIndex = curThingIndex + 2;
				
				if(curThingIndex >= topThingIndex + 16)
				{
					topThingIndex = topThingIndex + 2;
				}
			}
		}
		else if(state == ADDINGTILE || state == ADDINGTHING || state == VIEWINGMAP || state == FILLTILERECT || state == FILLTHINGRECT)
		{
			if(curY < curMap.tile[0].length-1)
			{
				curY++;
				
				if(curY >= topY+19)
				{
					topY++;
				}
			}
		}
		else if(state == TOOLMENU)
		{
			curTool++;
			
			if(curTool > 6)
			{
				curTool = 0;
			}
		}
		else if(state == SELECTTEMPLATE)
		{
			curTemplate++;
			
			if(curTemplate > 1)
			{
				curTemplate = 0;
			}
		}
		else if(state == RESIZEMAPX)
		{
			setState(RESIZEMAPY);
		}
		else if(state == SELECTMAP)
		{
			if(mapIndex < Map.numMaps - 1)
			{
				mapIndex++;
				
				if(cursorAlign < 10) cursorAlign++;
			}
		}
	}
	
	public void pressedRight()
	{
		if(state == SELECTINGTILE)
		{
			if(curTileIndex%2 == 0 && curTileIndex < tileImage.length - 1)
			{
				curTileIndex++;
			}
		}
		else if(state == SELECTINGTHING)
		{
			if(curThingIndex%2 == 0 && curThingIndex < thingImage.length - 1)
			{
				curThingIndex++;
			}
		}
		else if(state == ADDINGTILE || state == ADDINGTHING || state == VIEWINGMAP || state == FILLTILERECT || state == FILLTHINGRECT)
		{
			if(curX < curMap.tile.length-1)
			{
				curX++;
				
				if(curX >= topX+26)
				{
					topX++;
				}
			}
		}
		else if(state == RESIZEMAPX)
		{
			newWidth++;
		}
		else if(state == RESIZEMAPY)
		{
			newHeight++;
		}
	}
	
	public void pressedLeft()
	{
		if(state == SELECTINGTILE)
		{
			if(curTileIndex%2 == 1)
			{
				curTileIndex--;
			}
		}
		else if(state == SELECTINGTHING)
		{
			if(curThingIndex%2 == 1)
			{
				curThingIndex--;
			}
		}
		else if(state == ADDINGTILE || state == ADDINGTHING || state == VIEWINGMAP || state == FILLTILERECT || state == FILLTHINGRECT)
		{
			if(curX > 0)
			{
				curX--;
				
				if(curX < topX)
				{
					topX--;
				}
			}
		}
		else if(state == RESIZEMAPX)
		{
			if(newWidth > 50)
			{
				newWidth--;
			}
		}
		else if(state == RESIZEMAPY)
		{
			if(newHeight > 50)
			{
				newHeight--;
			}
		}
	}
	
	public void pressedX()
	{
		if(state == SELECTINGTILE)
		{
			setState(ADDINGTILE);
		}
		else if(state == ADDINGTILE)
		{
			curMap.tile(curX,curY,sortedTiles[curTileIndex]);
		}
		else if(state == SELECTINGTHING)
		{
			setState(ADDINGTHING);
		}
		else if(state == ADDINGTHING)
		{
			curMap.thing(curX,curY,sortedThings[curThingIndex]);
		}
		else if(state == VIEWINGMAP)
		{
			setState(SELECTINGTILE);
		}
		else if(state == FILLTILERECT)
		{
			fillTileRect();
		}
		else if(state == FILLTHINGRECT)
		{
			fillThingRect();
		}
		else if(state == TOOLMENU)
		{
			if(curTool == 0) //Select Template
			{
				setState(SELECTTEMPLATE);
				
				curTemplate = 0;
			}
			else if(curTool == 1)
			{
				setState(RESIZEMAPX);
				
				newWidth = curMap.tile.length;
				newHeight = curMap.tile[0].length;
			}
			else if(curTool == 2) //Bucket Fill
			{
				if(paletteState == 1)
				{
					curMap.fillRect(curX,curY,curMap.tile.length-1,curMap.tile[0].length-1,sortedTiles[curTileIndex]);
				}
				else if(paletteState == 2)
				{
					curMap.fillThingRect(curX,curY,curMap.tile.length-1,curMap.tile[0].length-1,sortedThings[curThingIndex]);
				}
			}
			else if(curTool == 3) //Get current tile
			{
				curTileIndex = sortedTilesIndex[curMap.tile[curX][curY].type];
				topTileIndex = Math.min(curTileIndex, Tile.NUMTILES - 14);
				if(topTileIndex%2 == 1) topTileIndex--;
				
				setState(SELECTINGTILE);
			}
			else if(curTool == 4) //Get current thing
			{
				if(curMap.tile[curX][curY].thing.type != Thing.NOTHING)
				{
					curThingIndex = sortedThingsIndex[curMap.tile[curX][curY].thing.type];
					
					topThingIndex = Math.min(curThingIndex, Thing.NUMTHINGS - 14);
					if(topThingIndex%2 == 1) topThingIndex--;
					
					setState(SELECTINGTHING);
				}
			}
			else if(curTool == 5) //Convert shore/dock
			{
				//TODO
			}
			else if(curTool == 6) //Save map
			{
				saveMap();
				
				if(prevState == SELECTINGTILE || prevState == ADDINGTILE || prevState == SELECTINGTHING || prevState == ADDINGTHING)
				{
					setState(prevState);
				}
				else
				{
					setState(VIEWINGMAP);
					
					paletteState = 0;
				}
			}
		}
		else if(state == SELECTTEMPLATE)
		{
			if(curTemplate == 0)
			{
				try
				{
					curMap.insideHouse(curX,curY,curX+10,curY+10);
				}
				catch(Exception e)
				{
					System.out.println("Trying to add house out of bounds");
				}
			}
			else if(curTemplate == 1) //Shop
			{
				//TODO
			}
		}
		else if(state == RESIZEMAPX || state == RESIZEMAPY)
		{
			Tile[][] newTiles = new Tile[newWidth][newHeight];
			
			for(int i=0; i<newTiles.length; i++)
			{
				for(int j=0; j<newTiles[0].length; j++)
				{
					if(i < curMap.tile.length && j < curMap.tile.length)
					{
						newTiles[i][j] = curMap.tile[i][j];
					}
					else
					{
						newTiles[i][j] = new Tile(Tile.BLACK);
					}
				}
			}
			
			curMap.tile = newTiles;
			
			setState(TOOLMENU);
		}
		else if(state == SELECTMAP)
		{
			int[] states = new int[100];
			curMap = Map.makeMap(mapIndex, states);
			
			setState(VIEWINGMAP);
		}
	}
	
	public void pressedZ()
	{
		if(state == ADDINGTILE)
		{
			setState(SELECTINGTILE);
		}
		else if(state == ADDINGTHING)
		{
			setState(SELECTINGTHING);
		}
		else if(state == SELECTINGTILE || state == SELECTINGTHING)
		{
			setState(VIEWINGMAP);
			
			paletteState = 0;
		}
		else if(state == FILLTILERECT)
		{
			setState(ADDINGTILE);
		}
		else if(state == FILLTHINGRECT)
		{
			setState(ADDINGTHING);
		}
		else if(state == TOOLMENU)
		{
			if(prevState == SELECTINGTILE || prevState == ADDINGTILE || prevState == SELECTINGTHING || prevState == ADDINGTHING)
			{
				setState(prevState);
			}
			else
			{
				setState(VIEWINGMAP);
				
				paletteState = 0;
			}
		}
		else if(state == SELECTTEMPLATE)
		{
			setState(TOOLMENU);
		}
		else if(state == RESIZEMAPX || state == RESIZEMAPY)
		{
			setState(TOOLMENU);
		}
	}
	
	public void pressedSpace()
	{
		if(state == SELECTINGTILE)
		{
			setState(SELECTINGTHING);
		}
		else if(state == SELECTINGTHING)
		{
			setState(SELECTINGTILE);
		}
		else if(state == VIEWINGMAP)
		{
			setState(SELECTINGTILE);
		}
	}
	
	public void pressedEnter() //Templates
	{
		if(state == VIEWINGMAP || state == SELECTINGTILE || state == ADDINGTILE || state == SELECTINGTHING || state == ADDINGTHING)
		{
			setState(TOOLMENU);
			
			curTool = 0;
		}
		else if(state == TOOLMENU)
		{
			setState(prevState);
		}
	}
	
	public void pressedC() //Fill Rect
	{
		if(state == ADDINGTILE)
		{
			setState(FILLTILERECT);
			
			fillStartX = curX;
			fillStartY = curY;
		}
		else if(state == ADDINGTHING)
		{
			setState(FILLTHINGRECT);
			
			fillStartX = curX;
			fillStartY = curY;
		}
		else if(state == FILLTILERECT)
		{
			fillTileRect();
		}
		else if(state == FILLTHINGRECT)
		{
			fillThingRect();
		}
	}
	
	public void pressedDelete()
	{
		if(state == ADDINGTHING || state == VIEWINGMAP)
		{
			curMap.thing(curX,curY,Thing.NOTHING);
		}
	}
	
	public void checkOS() 
	{
		if (OS == null)
		{
			OS = System.getProperty("os.name");
		}
		
		if (OS.startsWith("Windows"))
		{
			FileSeparator = "\\";
		}
		else
		{
			FileSeparator = "/";
		}
	}
	
	public void getsortedTiles()
	{
		sortedTiles = new int[Tile.NUMTILES];
		
		//use an ArrayList so we don't have to adjust all the indices when adding a new Tile
		ArrayList<Integer> sortedTilesList = new ArrayList<Integer>();
		
		//Grass/forest
		sortedTilesList.add(Tile.GRASS);
		sortedTilesList.add(Tile.LIGHTGRASS);
		sortedTilesList.add(Tile.DARKGRASS);
		sortedTilesList.add(Tile.SHADOWGRASS);
		sortedTilesList.add(Tile.VERYDARKGRASS);
		sortedTilesList.add(Tile.DIRT);
		sortedTilesList.add(Tile.DARKDIRT);
		sortedTilesList.add(Tile.SAND);
		sortedTilesList.add(Tile.WATER);
		sortedTilesList.add(Tile.DARKWATER);
		sortedTilesList.add(Tile.VERYDARKWATER);
		
		//City
		sortedTilesList.add(Tile.CITYFLOOR);
		sortedTilesList.add(Tile.STONEFLOOR);
		sortedTilesList.add(Tile.STONEWALL);
		sortedTilesList.add(Tile.STREET);
		sortedTilesList.add(Tile.BRICKFLOOR);
		sortedTilesList.add(Tile.STEEL);
		sortedTilesList.add(Tile.DARKSTONEFLOOR);
		sortedTilesList.add(Tile.DARKSTONEWALL);
		sortedTilesList.add(Tile.GRAY);
		sortedTilesList.add(Tile.PURPLESTONEFLOOR);
		
		//Inside House
		sortedTilesList.add(Tile.WOODFLOOR);
		sortedTilesList.add(Tile.WOODWALL);
		sortedTilesList.add(Tile.DARKWOODFLOOR);
		sortedTilesList.add(Tile.DARKWOODWALL);
		
		//Snow
		sortedTilesList.add(Tile.SNOWGRASS);
		sortedTilesList.add(Tile.SNOW);
		sortedTilesList.add(Tile.ICE);
		sortedTilesList.add(Tile.SNOWWALLSOUTH);
		sortedTilesList.add(Tile.SNOWWALLEAST);
		sortedTilesList.add(Tile.SNOWWALLNORTH);
		sortedTilesList.add(Tile.SNOWWALLWEST);
		sortedTilesList.add(Tile.SNOWWALLNORTHEAST);
		sortedTilesList.add(Tile.SNOWWALLSOUTHEAST);
		sortedTilesList.add(Tile.SNOWWALLSOUTHWEST);
		sortedTilesList.add(Tile.SNOWWALLNORTHWEST);
		sortedTilesList.add(Tile.SNOWWALLNORTHEAST2);
		sortedTilesList.add(Tile.SNOWWALLNORTHWEST2);
		
		//Mountain/lava
		sortedTilesList.add(Tile.DUST);
		sortedTilesList.add(Tile.DUSTTRAIL);
		sortedTilesList.add(Tile.LAVA);
		sortedTilesList.add(Tile.PLATEAU);
		sortedTilesList.add(Tile.PLATEAUWALLSOUTH);
		sortedTilesList.add(Tile.PLATEAUWALLEAST);
		sortedTilesList.add(Tile.PLATEAUWALLNORTH);
		sortedTilesList.add(Tile.PLATEAUWALLWEST);
		sortedTilesList.add(Tile.PLATEAUWALLNORTHEAST);
		sortedTilesList.add(Tile.PLATEAUWALLSOUTHEAST);
		sortedTilesList.add(Tile.PLATEAUWALLSOUTHWEST);
		sortedTilesList.add(Tile.PLATEAUWALLNORTHWEST);
		
		//Cave
		sortedTilesList.add(Tile.CAVEFLOOR);
		sortedTilesList.add(Tile.CAVEWALLNORTH);
		sortedTilesList.add(Tile.CAVEWALLEAST);
		sortedTilesList.add(Tile.CAVEWALLSOUTH);
		sortedTilesList.add(Tile.CAVEWALLWEST);
		sortedTilesList.add(Tile.CAVEWALLNORTHEAST);
		sortedTilesList.add(Tile.CAVEWALLSOUTHEAST);
		sortedTilesList.add(Tile.CAVEWALLSOUTHWEST);
		sortedTilesList.add(Tile.CAVEWALLNORTHWEST);
		
		//Misc
		sortedTilesList.add(Tile.BLACK);
		sortedTilesList.add(Tile.BLACKWALKABLE);
		
		if(sortedTilesList.size() != Tile.NUMTILES)
		{
			System.out.println("sortedTilesList.size() != Tile.NUMTILES");
		}
		
		for(int i=0; i<sortedTilesList.size(); i++)
		{
			sortedTiles[i] = sortedTilesList.get(i);
		}
		
		sortedTilesIndex = new int[Tile.NUMTILES];
		for(int i=0; i<sortedTiles.length; i++)
		{
			sortedTilesIndex[sortedTiles[i]] = i;
		}
	}
	
	public void getsortedThings()
	{
		sortedThings = new int[Thing.NUMTHINGS];
		
		//use an ArrayList so we don't have to adjust all the indices when adding a new Tile
		ArrayList<Integer> sortedThingsList = new ArrayList<Integer>();
		
		//Grass/forest
		sortedThingsList.add(Thing.TREE);
		sortedThingsList.add(Thing.DARKTREE);
		sortedThingsList.add(Thing.VERYDARKTREE);
		sortedThingsList.add(Thing.HAZELNUTTREE);
		sortedThingsList.add(Thing.FRUITTREE);
		sortedThingsList.add(Thing.DEADTREE);
		sortedThingsList.add(Thing.SHRUB);
		sortedThingsList.add(Thing.STUMP);
		sortedThingsList.add(Thing.FLOWER);
		sortedThingsList.add(Thing.FORESTDUST);
		sortedThingsList.add(Thing.MUSHROOM);
		
		//City
		sortedThingsList.add(Thing.STONEBLOCK);
		sortedThingsList.add(Thing.TORCH);
		sortedThingsList.add(Thing.HORIZONTALBRIDGE);
		sortedThingsList.add(Thing.VERTICALBRIDGE);
		sortedThingsList.add(Thing.BASICHOUSE);
		sortedThingsList.add(Thing.EMPTYHOUSE);
		sortedThingsList.add(Thing.INN);
		sortedThingsList.add(Thing.SNOWHOUSE);
		sortedThingsList.add(Thing.EMPTYSNOWHOUSE);
		
		//Inside House
		sortedThingsList.add(Thing.POTTEDPLANT);
		sortedThingsList.add(Thing.BOOKCASE);
		sortedThingsList.add(Thing.TABLE);
		sortedThingsList.add(Thing.LONGTABLE);
		sortedThingsList.add(Thing.SQUARETABLE);
		sortedThingsList.add(Thing.BIGSQUARETABLE);
		sortedThingsList.add(Thing.STONETABLE);
		sortedThingsList.add(Thing.LONGSTONETABLE);
		sortedThingsList.add(Thing.WOODENCHAIR);
		sortedThingsList.add(Thing.WINDOW);
		sortedThingsList.add(Thing.BOX);
		sortedThingsList.add(Thing.BED);
		sortedThingsList.add(Thing.DOOR);
		sortedThingsList.add(Thing.FIREPLACE);
		sortedThingsList.add(Thing.STAIRSUPNORTH);
		sortedThingsList.add(Thing.STAIRSDOWNNORTH);
		sortedThingsList.add(Thing.STAIRSUPSOUTH);
		sortedThingsList.add(Thing.STAIRSDOWNSOUTH);
		sortedThingsList.add(Thing.HOUSEEXIT);
		
		//Snow
		sortedThingsList.add(Thing.SNOWTREE);
		sortedThingsList.add(Thing.CHRISTMASTREE);
		sortedThingsList.add(Thing.ICEBERG);
		sortedThingsList.add(Thing.BIGICEBERG);
		
		//Mountain/lava
		sortedThingsList.add(Thing.ROCK);
		sortedThingsList.add(Thing.BIGROCK);
		
		//Cave
		sortedThingsList.add(Thing.CAVEENTRANCE);
		sortedThingsList.add(Thing.MOUNTAINCAVEENTRANCE);
		sortedThingsList.add(Thing.MOUNTAINCAVEENTRANCE2);
		sortedThingsList.add(Thing.LADDERDOWN);
		sortedThingsList.add(Thing.LADDERUP);
		
		//Spooky
		sortedThingsList.add(Thing.COFFIN);
		sortedThingsList.add(Thing.TOMBSTONE);
		
		//Fence
		sortedThingsList.add(Thing.HORIZONTALFENCE);
		sortedThingsList.add(Thing.VERTICALFENCE);
		sortedThingsList.add(Thing.FENCENORTHWEST);
		sortedThingsList.add(Thing.FENCENORTHEAST);
		sortedThingsList.add(Thing.FENCESOUTHEAST);
		sortedThingsList.add(Thing.FENCESOUTHWEST);
		sortedThingsList.add(Thing.FENCENORTHCONNECT);
		sortedThingsList.add(Thing.FENCEEASTCONNECT);
		sortedThingsList.add(Thing.FENCESOUTHCONNECT);
		sortedThingsList.add(Thing.FENCEWESTCONNECT);
		
		//Shore/dock
		sortedThingsList.add(Thing.SHORENORTH);
		sortedThingsList.add(Thing.SHOREEAST);
		sortedThingsList.add(Thing.SHORESOUTH);
		sortedThingsList.add(Thing.SHOREWEST);
		sortedThingsList.add(Thing.SHORENORTHEAST);
		sortedThingsList.add(Thing.SHORESOUTHEAST);
		sortedThingsList.add(Thing.SHORESOUTHWEST);
		sortedThingsList.add(Thing.SHORENORTHWEST);
		sortedThingsList.add(Thing.SHORENORTHWEST2);
		sortedThingsList.add(Thing.SHORENORTHEAST2);
		sortedThingsList.add(Thing.SHORESOUTHEAST2);
		sortedThingsList.add(Thing.SHORESOUTHWEST2);
		sortedThingsList.add(Thing.DARKSHORENORTH);
		sortedThingsList.add(Thing.DARKSHOREEAST);
		sortedThingsList.add(Thing.DARKSHORESOUTH);
		sortedThingsList.add(Thing.DARKSHOREWEST);
		sortedThingsList.add(Thing.DARKSHORENORTHEAST);
		sortedThingsList.add(Thing.DARKSHORESOUTHEAST);
		sortedThingsList.add(Thing.DARKSHORESOUTHWEST);
		sortedThingsList.add(Thing.DARKSHORENORTHWEST);
		sortedThingsList.add(Thing.DARKSHORENORTHWEST2);
		sortedThingsList.add(Thing.DARKSHORENORTHEAST2);
		sortedThingsList.add(Thing.DARKSHORESOUTHEAST2);
		sortedThingsList.add(Thing.DARKSHORESOUTHWEST2);
		sortedThingsList.add(Thing.DOCKNORTH);
		sortedThingsList.add(Thing.DOCKEAST);
		sortedThingsList.add(Thing.DOCKSOUTH);
		sortedThingsList.add(Thing.DOCKWEST);
		sortedThingsList.add(Thing.DOCKNORTHEAST);
		sortedThingsList.add(Thing.DOCKSOUTHEAST);
		sortedThingsList.add(Thing.DOCKSOUTHWEST);
		sortedThingsList.add(Thing.DOCKNORTHWEST);
		sortedThingsList.add(Thing.DOCKNORTHWEST2);
		sortedThingsList.add(Thing.DOCKNORTHEAST2);
		sortedThingsList.add(Thing.DOCKSOUTHEAST2);
		sortedThingsList.add(Thing.DOCKSOUTHWEST2);
		
		if(sortedThingsList.size() != Thing.NUMTHINGS)
		{
			System.out.println("sortedThingsList.size() != Thing.NUMTHINGS");
		}
		
		for(int i=0; i<sortedThingsList.size(); i++)
		{
			sortedThings[i] = sortedThingsList.get(i);
		}
		
		sortedThingsIndex = new int[Thing.NUMTHINGS];
		for(int i=0; i<sortedThings.length; i++)
		{
			sortedThingsIndex[sortedThings[i]] = i;
		}
	}
	
	public void saveMap()
	{
		boolean saveError = false;
		
		try
		{
			curMap.preSerialize();
			
			FileOutputStream fileOut = new FileOutputStream("src" + FileSeparator + "game" + FileSeparator + "map" + FileSeparator + "mapTile" + curMap.id + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(curMap.tileIDs);
			out.close();
			fileOut.close();
			
			fileOut = new FileOutputStream("src" + FileSeparator + "game" + FileSeparator + "map" + FileSeparator + "mapThing" + curMap.id + ".ser");
			out = new ObjectOutputStream(fileOut);
			out.writeObject(curMap.thingIDs);
			out.close();
			fileOut.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			saveError = true;
		}
		
		if(!saveError)
		{
			try
			{
				FileOutputStream fileOut = new FileOutputStream("src" + FileSeparator + "game" + FileSeparator + "mapBackup" + FileSeparator + "mapTile" + curMap.id + ".ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(curMap.tileIDs);
				out.close();
				fileOut.close();
				
				fileOut = new FileOutputStream("src" + FileSeparator + "game" + FileSeparator + "mapBackup" + FileSeparator + "mapThing" + curMap.id + ".ser");
				out = new ObjectOutputStream(fileOut);
				out.writeObject(curMap.thingIDs);
				out.close();
				fileOut.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
		
	}

	public void keyTyped(KeyEvent arg0)
	{
		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		
	}

}