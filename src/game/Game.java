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

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener
{
	public static int state;		//the current state
	public static int prevState; 	//determine the last state
	public static int newState;		//new state (for TaskTimer)
	
	/**
	 * Game states
	 */
	public static final int TITLESCREEN = 0;			//title screen
	public static final int INTRO = 1;					//introduction to the game
	public static final int MAP = 2;					//not in menu - on map
	public static final int MAINMENU = 3;				//main menu
	public static final int STATUSMENU = 4;				//viewing a party member's status
	public static final int INVENTORY = 5;				//viewing/selecting items
	public static final int INVENTORYREARRANGE = 6;		//rearranging items
	public static final int INVENTORYSORT = 7;			//choosing how to sort items
	public static final int INVENTORYUSE = 8;			//using selected item
	public static final int EQUIPMENU = 9;				//viewing equipment
	public static final int EQUIPMENUSEL = 10;			//selecting what to equip
	public static final int MAPDIALOGUE = 11;			//on map, event dialogue
	public static final int SHOPOPTION = 12;			//choosing whether to buy, sell, or leave
	public static final int SHOPBUY = 13;				//browing items to buy
	public static final int SHOPBUY2 = 14;				//choosing how many to buy
	public static final int SHOPSELL = 15;				//browsing items to sell
	public static final int SHOPSELL2 = 16;				//choosing how many to sell
	public static final int INN = 17; 					//inn - rest or leave?
	public static final int ENCOUNTER = 18;				//encountered a battle
	public static final int BATTLE = 19;				//generic battle state
	public static final int BATTLECHOICE = 20;			//choosing what to do in battle
	public static final int BATTLETARGET = 21;			//selecting a target in battle
	public static final int BATTLEEFFECT = 22;			//displaying the effect of a turn in battle
	public static final int BATTLERECALCULATE = 23;		//placeholder, need to recalculate the next battle state
	public static final int BATTLEWON = 24;				//won the battle - do a dance
	public static final int BATTLELOST = 25;			//lost the battle - lie on the ground
	public static final int AFTERBATTLE = 26;			//after battle - display experience, items, level ups, etc.
	public static final int GAMEOVER = 27;				//game over screen after losing a battle
	public static final int WORLDMAP = 28;				//viewing a map of the world
	public static final int GAMESAVED = 29;				//game saved message appears
	public static final int VIEWART = 30;				//viewing a beautiful piece of art
	public static final int BATTLESELECTSKILL = 31;		//selecting a skill to use
	public static final int BATTLESELECTITEM = 32;		//selecting an item to use
	public static final int MAINMENUSELECTCHAR = 33;	//selecting a character from the main menu
	public static final int ACTIVESKILLMENU = 34;		//looking at a character's active skills
	public static final int PASSIVESKILLMENU = 35;		//looking at a character's passive skills
	public static final int BATTLEFLED = 36;			//escaped from battle
	public static final int EDITPARTY = 37;				//Edit Party screen
	public static final int EDITPARTYSWITCH = 38;		//switching party members around
	public static final int STATUSMENUMOREINFO = 39;	//looking at more information in the status menu
	public static final int SKILLMENUSEL = 40;			//selecting passive or active skills to look at
	public static final int INNCHOICE = 41;				//choosing Yes or No for an inn
	
	/**
	 * Battle states (for drawing units)
	 */
	public static final int NORMAL = 0;					//not attacking or being damaged
	public static final int ATTACK = 1;					//attacking (for now this is the same as NORMAL, may want attack animations later)
	public static final int DAMAGED = 2;				//being damaged
	public static final int DEAD = 3;					//dead
	public static final int ASLEEP = 4;					//"asleep" status
	public static final int DEFENDING = 5;				//"defend" status (e.g. Alex's Cower)
	//TODO: some sort of "cast skill" animation as a catch-all for skills
	//otherwise, use the current skill ID
	
	/**
	 * Elements
	 */
	public static final int NUMELEMENTS = 6;
	public static final int NOELEMENT = -1;
	public static final int SNACK = 0;
	public static final int FIRE = 1;
	public static final int LIGHTNING = 2;
	public static final int WATER = 3;
	public static final int EARTH = 4;
	public static final int POISON = 5;
	
	/**
	 * Attack animations
	 */
	public static final String HIT = "hit";
	public static final String SLICE = "slice";

	/**
	 * Directions
	 */
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public boolean moveNorth = false;
	public boolean moveEast = false;
	public boolean moveSouth = false;
	public boolean moveWest = false;
	
	public Unit[] party;
	public Unit placeholderCharacter;
	public ArrayList<Unit> monsters;
	
	//public boolean[] leveledUp;
	public ArrayList<AfterBattleAlert> afterBattleAlerts;
	
	public Unit currentUnit;
	public Action selectedAction;
	public BattleAction battleAction;
	
	public long moveTimer;
	static Random rand;
	
	Timer timer;
	
	static Map curMap;
	static Map[] map;
	static int curX, curY;
	static int respawnMap, respawnX, respawnY;

	static int money;
	
	public int direction;
	
	static int wildRate;
	
	public static ImageIcon icon;
	public static Image img;
	
	//cached images
	public static Image tileImage[];
	public static Image thingImage[];
	public static Image tileAltImage[];
	public static Image thingAltImage[];
	public static Image mapCharacterImage[][];
	
	public static int numFrames;
	public static int frameWaitTime;
	
	Event curEvent;
	
	BufferedReader inputParty0, inputParty1, inputParty2, inputParty3, inputParty4, inputParty5, inputParty6, inputParty7, inputPosition, inputInventory, inputMaps; 
	PrintWriter outputParty0, outputParty1, outputParty2, outputParty3, outputParty4, outputParty5, outputParty6, outputParty7, outputPosition, outputInventory, outputMaps;
	
	Font arial10, arialBold10, arialBold12, arialBold14, arialBold16, arialBold18, arialBold20, arialBold28;
	Color menuGreen, menuBlue, menuDarkGreen, menuLightBlue, hpGreen, lightGray, mpBlue, orange, expPurple, bluegreen, darkRed, flashOrange, statUpGreen, darkGray;
	
	BufferedReader input0;
	PrintWriter output0;
	
	public static int animationID = 0;
	public static boolean startLoop = true;

	public static int walkCycle;

	NumberFormat format = NumberFormat.getCurrencyInstance();
	
	static Clip clip;
	static LineListener listener;
	static FloatControl gainControl;
	
	static String currentSong;
	static int songFrame;
	
	static float volume = -17;
	static boolean mute = false;

	static int tileState = 0;
	static ArrayList<Integer> movingTileList;
	static ArrayList<Integer> movingThingList;
	
	static int xOffset = 0;
	static int yOffset = 0;
	
	public ArrayList<Integer> index;
	
	public int cursorAlign;
	
	public static ArrayList<Item> inventory = new ArrayList<Item>();
	public static ArrayList<Item> itemDrops = new ArrayList<Item>();
	public static ArrayList<Integer> equippableIndexList = new ArrayList<Integer>();
	
	/**
	 * Double buffering
	 */
	static Image dbImage;
	static Graphics dbGraphics;

	static long baseTime = System.currentTimeMillis();
	static long lastPaintTime = System.currentTimeMillis();
	
	static boolean walkCycling = false;
	
	static boolean inputDisabled = false;
	
	static int gameTime;

	static long lastBonk = 0; //don't spam the bonk sound
	static int sliding = -1; //determines if you're sliding on ice
	static int steps = 0; //save to backup data every 250 steps
	
	public boolean saveOverride = false; //set this to save anywhere, obviously won't be in the actual game

	public void init()
	{
		setDoubleBuffered(true);
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
	                
	                boolean timeToMove = false;
	                long time = System.currentTimeMillis();
	                
	                if(sliding != -1 && time > moveTimer + 160) timeToMove = true;
	                else if(time > moveTimer + 240) timeToMove = true;
	                
	                if(state == MAP && timeToMove)
	                {
	                	 if(moveNorth || sliding == NORTH)
	                	 {
	                		 move(NORTH);
	                	 }
	                	 else if(moveEast || sliding == EAST)
	                	 {
	                		 move(EAST);
	                	 }
	                	 else if(moveSouth || sliding == SOUTH)
	                	 {
	                		 move(SOUTH);
	                	 }
	                	 else if(moveWest || sliding == WEST)
	                	 {
	                		 move(WEST);
	                	 }
	                	 
	                	 repaint();
	                }
	            }
	        }
	    }.start();
	}
	
	public void tileLoop()
	{
	    new Thread()
	    {
	        public void run()
	        {
	            while(true)
	            {
	                try
	                {	                	
	                    Thread.sleep(1000);
	                }
	                catch (InterruptedException e)
	                {
	                    e.printStackTrace();
	                }
	                
	                tileState = tileState * -1 + 1;
	                
	                gameTime++; //another second passed
	                
	                repaint();
	            }
	        }
	    }.start();
	}
	
	public void walkLoop()
	{
	    new Thread()
	    {
	        public void run()
	        {
	        	walkCycling = true;
	        	
        		for(int i=0; i<2; i++)
        		{
        			walkCycle++;
     	            if(walkCycle == 4) walkCycle = 0;
 	                
 	                if(direction == NORTH)
 	                {
 	                	if(yOffset == -24) yOffset = -25;
 	                	else yOffset = 0;
 	                }
 	                else if(direction == SOUTH)
 	                {
 	                	if(yOffset == 24) yOffset = 25;
 	                	else yOffset = 0;
 	                }
 	                else if(direction == EAST) 
 	                {
 	                	if(xOffset == 24) xOffset = 25;
 	                	else xOffset = 0;
 	                }
 	                else if(direction == WEST)
 	                {
 	                	if(xOffset == -24) xOffset = -25;
 	                	else xOffset = 0;
 	                }
 	                
 	                repaint();
 	                
 	                try
 	                {
 	                	Thread.sleep(110);
 	                }
 	                catch (InterruptedException e)
 	                {
 	                    e.printStackTrace();
 	                }
 	            }
        		
        		//reset everything to be safe
        		if(xOffset != 0 || yOffset != 0 || walkCycle == 1 || walkCycle == 3)
        		{
        			xOffset = 0;
        			yOffset = 0;
        			walkCycle = 0;
        			repaint();
        		}
        		
        		walkCycling = false;
	        }
	    }.start();
	}
	
	public void animationLoop(int frames, int waitTime)
	{
		numFrames = frames;
		frameWaitTime = waitTime;
		
		animationID = 0;
		
	    new Thread()
	    {
	        public void run()
	        {
	        	startLoop = false;
	        	//boolean breakLoop = false;
	        	
	        	int threadNumFrames = numFrames;			//set local variables since numFrames/frameWaitTime could be changed by another thread (results in bad things)
	        	int threadFrameWaitTime = frameWaitTime;
	        	
        		for(int i=0; i<threadNumFrames; i++)
  	           	{
        			animationID = i;

        			if(i == threadNumFrames-1 && (state == BATTLEEFFECT || state == BATTLERECALCULATE))
        			{
        				setState(BATTLE); //1 frame with no effect between turns
        			}
        			
        			repaint();
        			
        			//if(i == numFrames-1) breakLoop = true;
  	        	   
        			try 
  	        	   	{
        				Thread.sleep(threadFrameWaitTime);
  	        	   	}
  	        	   	catch(Exception e)
  	        	   	{
  	        	   		
  	        	   	}
  	        	   	
  	        	   	//if(breakLoop) break; //stupid race condition
  	           	}
	           
	        	animationID = 0;
	        	
	        	startLoop = true;
	        }
	    }.start();
	}
	
	public String getStateName(int state)
	{
		switch(state)
		{
		case TITLESCREEN: return "Title Screen";
		case INTRO: return "Intro";
		case MAP: return "Map";
		case MAINMENU: return "Main menu";
		case STATUSMENU: return "Status menu";
		case INVENTORY: return "Inventory";
		case INVENTORYREARRANGE: return "Inventory rearrange";
		case INVENTORYSORT: return "Inventory sort";
		case INVENTORYUSE: return "Inventory use";
		case EQUIPMENU: return "Viewing equipment";
		case EQUIPMENUSEL: return "Selecting equipment";
		case MAPDIALOGUE: return "Map dialogue";
		case SHOPOPTION: return "Shop option";
		case SHOPBUY: return "Shop buy";
		case SHOPBUY2: return "Shop buy 2";
		case SHOPSELL: return "Shop sell";
		case SHOPSELL2: return "Shop sell 2";
		case INN: return "Inn";
		case ENCOUNTER: return "Encounter";
		case BATTLE: return "Battle";
		case BATTLECHOICE: return "Battle choice";
		case BATTLETARGET: return "Battle select target";
		case BATTLEEFFECT: return "Battle effect";
		case BATTLERECALCULATE: return "Battle recalculate";
		case BATTLEWON: return "Won battle";
		case BATTLELOST: return "Lost battle";
		case AFTERBATTLE: return "After battle";
		case GAMEOVER: return "Game over";
		case WORLDMAP: return "World map";
		case GAMESAVED: return "Game saved";
		case VIEWART: return "View art";
		case BATTLESELECTSKILL: return "Battle select skill";
		case BATTLESELECTITEM: return "Battle select item";
		case MAINMENUSELECTCHAR: return "Main menu select char";
		}
		
		return "Unknown state";
	}
	
	public Game()
	{
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		setFocusable(true);
		requestFocus();
		
		setState(TITLESCREEN);
		
		mute = true; //TODO: remove
		party = new Unit[8]; //6 party members, 2 extra space in case 1 in party and 5 in "storage"
		for(int i=0; i<party.length; i++) party[i] = new None();
		
		//leveledUp = new boolean[3];
		afterBattleAlerts = new ArrayList<AfterBattleAlert>();
		
		monsters = new ArrayList<Unit>();
		
		arialBold28 = new Font("Arial",Font.BOLD,28);
		arialBold20 = new Font("Arial",Font.BOLD,20);
		arialBold18 = new Font("Arial",Font.BOLD,18);
		arialBold16 = new Font("Arial",Font.BOLD,16);
		arialBold14 = new Font("Arial",Font.BOLD,14);
		arialBold12 = new Font("Arial",Font.BOLD,12);
		arialBold10 = new Font("Arial",Font.BOLD,11);
		arial10 = new Font("Arial",Font.PLAIN,10);
		
		menuBlue = new Color(30,65,100);
		menuGreen = new Color(55,60,50);
		menuDarkGreen = new Color(30,35,30);
		menuLightBlue = new Color(140,160,155); //old: 75,135,210
		hpGreen = new Color(35,140,35);
		lightGray = new Color(210,210,210);
		mpBlue = new Color(0,150,255);
		orange = new Color(250,170,60);
		expPurple = new Color(165,175,225);
		bluegreen = new Color(220,250,240);
		darkRed = new Color(165,5,5);
		flashOrange = new Color(255,85,0);
		statUpGreen = new Color(105,255,0);
		darkGray = new Color(70,70,70);
		
		tileImage = new Image[Tile.NUMTILES];
		for(int i=0; i<Tile.NUMTILES; i++)
		{
			icon = new ImageIcon(getClass().getResource("img/tile/tile" + i + ".PNG"));
			tileImage[i] = icon.getImage();
		}
		
		thingImage = new Image[Thing.NUMTHINGS+1];
		for(int i=1; i<Thing.NUMTHINGS+1; i++)
		{
			icon = new ImageIcon(getClass().getResource("img/thing/mapThing" + i + ".PNG"));
			thingImage[i] = icon.getImage();
		}

		movingTileList = Tile.movingTileList();
		
		tileAltImage = new Image[movingTileList.size()];
		for(int i=0; i<movingTileList.size(); i++)
		{
			icon = new ImageIcon(getClass().getResource("img/tile/tile" + movingTileList.get(i) + "a.PNG"));
			tileAltImage[i] = icon.getImage();
		}
		
		movingThingList = Thing.movingThingList();
		
		thingAltImage = new Image[movingThingList.size()];
		for(int i=0; i<movingThingList.size(); i++)
		{
			icon = new ImageIcon(getClass().getResource("img/thing/mapThing" + movingThingList.get(i) + "a.PNG"));
			thingAltImage[i] = icon.getImage();
		}
		
		mapCharacterImage = new Image[4][4];
		for(int dir=0; dir<4; dir++)
		{
			for(int cycle=0; cycle<4; cycle++)
			{
				icon = new ImageIcon(getClass().getResource("img/mapCharacter/mapCharacter" + dir + cycle + ".PNG"));
				mapCharacterImage[dir][cycle] = icon.getImage();
			}
		}
		
		menuBlue = new Color(30,65,100);
		
		rand = new Random();

		curEvent = new NoEvent();
		
		index = new ArrayList<Integer>();
		index.add(0);
		
		//playSong("Main Menu");
		//clip.setFramePosition(564656);
		
		gameLoop();
		tileLoop();
	}
	
	public boolean canSave()
	{
		if(saveOverride) return true;
		
		return (curMap.tile[curX][curY].event.type == Event.SAVEPOINT);
	}
	
	public String moneyFormat(int money)
	{
		String stringMoney = format.format(money);	
		
		stringMoney = stringMoney.substring(0,stringMoney.length()-3);
		
		return stringMoney;
	}
	
	public int getTotalExpGain()
	{
		if(monsters == null)
		{
			return 0;
		}
		
		int totalExp = 0;
		for(int i=0; i<monsters.size(); i++)
		{
			totalExp += monsters.get(i).expGain;
		}
		
		return totalExp;
	}
	
	public int getTotalSPGain()
	{
		if(monsters == null)
		{
			return 0;
		}
		
		double totalSP = 0;
		for(int i=0; i<monsters.size(); i++)
		{
			totalSP += monsters.get(i).spGain;
		}
		
		int actualTotalSP = (int) totalSP;
		if(actualTotalSP < 1) actualTotalSP = 1;
		
		return actualTotalSP;
	}
	
	public int getTotalMoneyGain()
	{
		if(monsters == null)
		{
			return 0;
		}
		
		int totalMoney = 0;
		for(int i=0; i<monsters.size(); i++)
		{
			totalMoney += monsters.get(i).moneyGain;
		}
		
		return totalMoney;
	}
	
	public int[] getLoopPoints(String name)
	{
		int[] loopPoints = new int[2];
		
		//TODO
		
		if(name.equals("Main Menu"))
		{
			loopPoints[0] = 0;
			loopPoints[1] = 0;
		}
		
		else
		{
			loopPoints[0] = 0;
			loopPoints[1] = clip.getFrameLength()-1;
		}
		
		return loopPoints;
	}
	
	public void switchCharacters(int index1, int index2)
	{
		if(index1 >= party.length || index2 >= party.length) return;
		
		Unit temp = party[index1];
		party[index1] = party[index2];
		party[index2] = temp;
	}
	
	public void playMapSong(boolean setFrame)
	{
		if(!mute)
		{
			if(curMap.tile[curX][curY].music != null)
			{
				playSong(curMap.tile[curX][curY].music); 
			}
			else playSong(curMap.song);
			
			if(setFrame) clip.setFramePosition(songFrame);
		}
	}
	
	public void playSong(String song)
	{
		currentSong = song;
		
		if(!mute)
		{
			try
			{
				try
				{
					if(clip != null)
					{
						clip.stop();
						clip.close();
					}
				}
				catch(Exception e)
				{
					System.out.println("Couldn't stop the clip: " + e.getMessage());
				}
				
				URL url = getClass().getResource("bgm/" + song + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

				clip = AudioSystem.getClip();
				
				clip.open(audioIn);
				
				clip.setLoopPoints(getLoopPoints(song)[0],getLoopPoints(song)[1]);
				
				gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

				playRunnable();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println("Error playing song: " + song);
			}
		}
	}
	
	public void playRunnable()
	{
		Runnable soundPlayer = new Runnable()
		{
			public void run()
			{
				try
				{
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				catch(Exception e)
				{
					
				}
			}
		};
		new Thread(soundPlayer).start();
	}
	
	public void playSong(String name, boolean repeat)
	{
		currentSong = name;
		
		if(!mute)
		{
			try
			{
				try
				{
					clip.stop();
					clip.close();
				}
				catch(Exception e)
				{
					System.out.println("Couldn't stop the clip: " + e.getMessage());
				}
				
				URL url = getClass().getResource("bgm/" + name + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				
				clip = AudioSystem.getClip();
				
				clip.open(audioIn);
				
				gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
				
				clip.start();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println("Error playing song: " + name);
			}
		}
	}
	
	public void playSound(String name)
	{
		if(!mute)
		{
			try
			{				
				URL url = getClass().getResource("sound/" + name + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				
				Clip sound;
				
				sound = AudioSystem.getClip();
				sound.open(audioIn);
				
				float soundVolume = volume + 5;
				
				if(name.equals("bonk")) soundVolume = volume;
				
				gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(soundVolume);
				
				sound.start();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println("Error playing sound: " + name);
			}
		}
	}
	
	public void playSound(String name, String folder)
	{
		if(!mute)
		{
			try
			{				
				URL url = getClass().getResource("sound/" + folder + "/" + name + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				
				Clip sound;
				
				sound = AudioSystem.getClip();
				
				sound.open(audioIn);
				
				gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
				
				sound.start();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println("Error playing sound: " + name);
			}
		}
	}
	
	public void changeMap(int id, int x, int y)
	{
		String prevSong = "";
		if(curMap.tile[curX][curY].music != null)
		{
			prevSong = curMap.tile[curX][curY].music;
		}
		else prevSong = curMap.song;
		
		curMap.recalculateStates();
		map[curMap.id].states = curMap.states;
		
		curMap = Map.makeMap(id, map[id].states);
		curX = x;
		curY = y;
		
		String newSong = curMap.song;
		
		if(curMap.tile[curX][curY].music != null)
		{
			newSong = curMap.tile[curX][curY].music;
		}
		
		if(!prevSong.equals(newSong)) playSong(newSong);
	}
	
	public void paint(Graphics g)
	{
		if(state == TITLESCREEN)
		{
			drawTitleScreen(g);
		}
		else if(state == MAP)
		{
			drawMap(g);
		}
		else if(state == MAPDIALOGUE)
		{
			drawMap(g);
			drawMapDialogue(g);
		}
		else if(state == MAINMENU || state == MAINMENUSELECTCHAR || state == INVENTORYUSE)
		{
			drawMainMenu(g);
		}
		else if(state == STATUSMENU || state == EQUIPMENU || state == EQUIPMENUSEL || state == STATUSMENUMOREINFO)
		{
			drawStatusMenu(g);
		}
		else if(state == INVENTORY || state == INVENTORYREARRANGE || state == INVENTORYSORT)
		{
			drawInventory(g);
		}
		else if(state == GAMESAVED)
		{
			drawMainMenu(g);
			
			g.setColor(Color.BLACK);
			g.fillRect(300,10,250,30);
			g.setColor(Color.YELLOW);
			g.fillRect(302,12,246,26);
			g.setColor(Color.BLACK);
			g.setFont(arialBold20);
			g.drawString("Game saved",360,30);
		}
		/*else if(state == CUBEGRID || state == CUBEGRIDCONFIRM)
		{
			drawCubeGrid(g);
		}*/
		else if(state == ENCOUNTER)
		{
			drawMap(g);
			drawEncounter(g);
		}
		else if(state == BATTLE)
		{
			drawBattle(g);
		}
		else if(state == BATTLEEFFECT || state == BATTLERECALCULATE)
		{
			drawBattle(g);
			drawBattleEffect(g);
		}
		else if(state == BATTLECHOICE)
		{
			drawBattle(g);
			drawBattleChoice(g);
		}
		else if(state == BATTLESELECTSKILL)
		{
			drawBattle(g);
			drawBattleChoice(g);
		}
		else if(state == BATTLESELECTITEM)
		{
			drawBattle(g);
			drawBattleChoice(g);
		}
		else if(state == BATTLETARGET)
		{
			drawBattle(g);
			drawBattleChoice(g);
			drawBattleTarget(g);
		}
		else if(state == BATTLEFLED)
		{
			drawBattle(g);
		}
		else if(state == BATTLELOST)
		{
			drawBattle(g);
		}
		else if(state == BATTLEWON)
		{
			drawBattle(g);
		}
		else if(state == AFTERBATTLE)
		{
			drawAfterBattle(g);
		}
		else if(state == GAMEOVER)
		{
			drawGameOver(g);
		}
		else if(state == SHOPOPTION)
		{
			drawShop(SHOPOPTION,g);
		}
		else if(state == SHOPBUY)
		{
			drawShop(SHOPBUY,g);
		}
		else if(state == SHOPBUY2)
		{
			drawShop(SHOPBUY2,g);
		}
		else if(state == SHOPSELL)
		{
			drawShop(SHOPSELL,g);
		}
		else if(state == SHOPSELL2)
		{
			drawShop(SHOPSELL2,g);
		}
		else if(state == WORLDMAP)
		{
			drawWorldMap(g);
		}
		else if(state == EDITPARTY || state == EDITPARTYSWITCH)
		{
			drawEditParty(g);
		}
		else if(state == VIEWART)
		{
			drawMap(g);
			
			draw(curEvent.name,"art",175,100,g);
		}
		else if(state == ACTIVESKILLMENU)
		{
			drawSkillMenu(ACTIVESKILLMENU, g);
		}
		else if(state == PASSIVESKILLMENU)
		{
			drawSkillMenu(PASSIVESKILLMENU, g);
		}
		else if(state == SKILLMENUSEL)
		{
			drawSkillMenu(SKILLMENUSEL, g);
		}
		else if(state == INNCHOICE)
		{
			drawInnChoice(g);
		}
	}
	
	public void drawTitleScreen(Graphics g)
	{
		draw("titleScreen",0,0,g);
		
		draw("pointer",260,500+50*curIndex(),g);
	}	
	
	public void drawWorldMap(Graphics g)
	{
		draw("worldMap","maps",0,0,g);
	}
	
	public void drawShop(int state, Graphics g)
	{
		if(state == SHOPOPTION)
		{
			draw("shop",0,0,g);
			
			draw("shopOptionSel",99+171*curIndex(),19,g);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			
			g.drawString("Money: " + moneyFormat(money),665,180);
		}
		else if(state == SHOPBUY || state == SHOPBUY2)
		{
			draw("shop",0,0,g);

			draw("shopOptionSel",99+171*0,19,g);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);

			g.drawString("Money: " + moneyFormat(money),665,180);
			
			int itemIndex = 0;
			if(state == SHOPBUY) itemIndex = curIndex();
			else if(state == SHOPBUY2) itemIndex = prevIndex(1);
			
			int top = Math.max(0,itemIndex-cursorAlign);
			int bottom = Math.min(top+14,curEvent.inventory.length-1);
			
			for(int i=top; i<=bottom; i++)
			{
				g.fillRect(170,90+32*(i-top),350,25);
				g.setColor(lightGray);
				g.fillRect(171,91+32*(i-top),348,23);
				g.setColor(Color.BLACK);
				g.setFont(arialBold18);
				g.drawString(curEvent.inventory[i].name,206,110+32*(i-top));
				draw(curEvent.inventory[i].getIconString(),170,90+32*(i-top),g);
				
				g.setFont(arialBold14);
				g.setColor(Color.BLACK);
				
				drawStringRightAligned(moneyFormat(curEvent.inventory[i].price),515,110+32*(i-top),g);
			}
			
			if(state == SHOPBUY) draw("pointer",140,86+32*(itemIndex-top),g);
			else draw("pointerSelected",140,86+32*(itemIndex-top),g);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold14);
			
			FontMetrics font;
			int width;
			
			if(curEvent.inventory[itemIndex].isEquipment())
			{
				drawEquipInfo(curEvent.inventory[itemIndex], false, g);
			}
			else
			{
				font = g.getFontMetrics();
				width = font.stringWidth(curEvent.inventory[itemIndex].desc);
				g.drawString(curEvent.inventory[itemIndex].desc,350-(width/2),610);
			}
			
			//side slider
			g.setColor(menuBlue);
			if(curEvent.inventory.length > 15)
			{
				double dheight = 486.0*(15.0/curEvent.inventory.length);
				int height = (int) dheight;
				
				int topIndex = itemIndex - cursorAlign;
				int offset = (486 - height)/(curEvent.inventory.length - 15);
				
				int drawHeight = 75+topIndex*offset;
				if(topIndex == curEvent.inventory.length - 15) drawHeight = 75+486-height; //in case of decimals, make sure to fill the whole bar

				g.fillRect(577,drawHeight,21,height);
			}
			else
			{
				g.fillRect(577,75,21,486);
			}
			
			if(state == SHOPBUY2)
			{
				g.setFont(arialBold12);
				g.setColor(Color.BLACK);
				g.drawString("Buying " + curEvent.inventory[itemIndex].name + ".",650,280);
				g.drawString("Stock: " + howManyOfItem(curEvent.inventory[itemIndex].id),650,305);
				g.drawString(""+curIndex(),675,340);
				g.drawString(moneyFormat(curIndex()*curEvent.inventory[itemIndex].price),750,340);
				
				draw("leftArrow",650,330,g);
				draw("rightArrow",700,330,g);
			}
		}
		else if(state == SHOPSELL || state == SHOPSELL2)
		{
			draw("shop",0,0,g);
			
			draw("shopOptionSel",99+171*1,19,g);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString("Money: " + moneyFormat(money),665,180);
			
			int itemIndex = 0;
			if(state == SHOPSELL) itemIndex = curIndex();
			else if(state == SHOPSELL2) itemIndex = prevIndex(1);
			
			int top = Math.max(0,itemIndex-cursorAlign);
			int bottom = Math.min(top+14,inventory.size()-1);
			
			for(int i=top; i<=bottom; i++)
			{
				g.fillRect(150,90+32*(i-top),390,25);
				g.setColor(lightGray);
				g.fillRect(151,91+32*(i-top),388,23);
				g.setColor(Color.BLACK);
				g.setFont(arialBold18);
				g.drawString(inventory.get(i).name,186,110+32*(i-top));
				draw(inventory.get(i).getIconString(),150,90+32*(i-top),g);
				
				g.setFont(arialBold14);
				g.setColor(Color.BLACK);
				
				if(inventory.get(i).price/2 > 0) drawStringRightAligned(moneyFormat(inventory.get(i).price/2),510,110+32*(i-top),g);
				drawStringRightAligned(""+inventory.get(i).qty,535,110+32*(i-top),g);
			}
			
			if(state == SHOPSELL) draw("pointer",120,86+32*(itemIndex-top),g);
			else draw("pointerSelected",120,86+32*(itemIndex-top),g);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold14);

			FontMetrics font;
			int width;
			
			if(inventory.get(itemIndex).isEquipment())
			{
				drawEquipInfo(inventory.get(itemIndex), false, g);
			}
			else
			{
				font = g.getFontMetrics();
				width = font.stringWidth(inventory.get(itemIndex).desc);
				g.drawString(inventory.get(itemIndex).desc,350-(width/2),610);
			}
			
			//side slider
			g.setColor(menuBlue);
			if(inventory.size() > 15)
			{
				double dheight = 486.0*(15.0/inventory.size());
				int height = (int) dheight;
				
				int topIndex = itemIndex - cursorAlign;
				int offset = (486 - height)/(inventory.size() - 15);
				
				int drawHeight = 75+topIndex*offset;
				if(topIndex == inventory.size() - 15) drawHeight = 75+486-height; //in case of decimals, make sure to fill the whole bar

				g.fillRect(577,drawHeight,21,height);
			}
			else
			{
				g.fillRect(577,75,21,486);
			}
			
			if(state == SHOPSELL2)
			{
				g.setFont(arialBold12);
				g.setColor(Color.BLACK);
				g.drawString("Selling " + inventory.get(itemIndex).name + ".",650,280);
				g.drawString("Stock: " + inventory.get(itemIndex).qty,650,305);
				g.drawString(""+curIndex(),675,340);
				g.drawString(moneyFormat(curIndex()*inventory.get(itemIndex).price/2),750,340);
				
				draw("leftArrow",650,330,g);
				draw("rightArrow",700,330,g);
			}
		}
	}
	
	public void drawEquipInfo(Item equip, boolean forInventory, Graphics g)
	{
		int boxLeft, boxLength;
		if(forInventory)
		{
			boxLeft = 682;
			boxLength = 163; 
		}
		else
		{
			boxLeft = 631;
			boxLength = 200;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(boxLeft,380,boxLength,180);
		g.setColor(lightGray);
		g.fillRect(boxLeft+1,381,boxLength-2,178);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold14);
		g.drawString("Equippable by:",boxLeft+10,395);
		int xPos = 0, yPos = 0;
		int xOffset;
		if(forInventory)
		{
			xOffset = 75;
		}
		else
		{
			xOffset = 90;
		}
		for(int i=0; i<party.length; i++)
		{
			if(party[i].id != Character.NONE)
			{
				if(party[i].id == Character.BRIAN)
				{
					xPos = boxLeft + 20;
					yPos = 420;
				}
				else if(party[i].id == Character.ALEX)
				{
					xPos = boxLeft + 20 + xOffset;
					yPos = 420;
				}
				else if(party[i].id == Character.KITTEN)
				{
					xPos = boxLeft + 20;
					yPos = 440;
				}
				else if(party[i].id == Character.RYAN)
				{
					xPos = boxLeft + 20 + xOffset;
					yPos = 440;
				}
				else if(party[i].id == Character.MYCHAL)
				{
					xPos = boxLeft + 20;
					yPos = 460;
				}
				/*else if(party[i].id == Character.ALEX) TODO: Kev-Bot
				{
					xPos = boxLeft + 20 + xOffset;
					yPos = 420;
				}*/
				if(equip.equippableBy(party[i].id)) g.setColor(Color.BLACK);
				else g.setColor(Color.GRAY);
				
				g.drawString(party[i].name,xPos,yPos);
			}
		}
		
		int numActiveSkills = equip.activeSkills.size();
		int numPassiveSkills = equip.passiveSkills.size();
		yPos = 500;
		for(int i=0; i<numActiveSkills; i++)
		{
			draw("iconActive",boxLeft+20,yPos-20,g);
			g.drawString(equip.activeSkills.get(i).action.name,boxLeft+45,yPos);
			yPos += 25;
		}
		for(int i=0; i<numPassiveSkills; i++)
		{
			draw("iconPassive",boxLeft+20,yPos-20,g);
			g.drawString(equip.passiveSkills.get(i).name,boxLeft+45,yPos);
			yPos += 25;
		}
		
		int center;
		if(forInventory)
		{
			center = 450;
		}
		else
		{
			center = 350;
		}
		
		g.setColor(Color.BLACK);
		FontMetrics font = g.getFontMetrics();
		int width = font.stringWidth(equip.desc);
		g.drawString(equip.desc,center-(width/2),600);
		
		width = font.stringWidth(equip.equipDesc);
		g.drawString(equip.equipDesc,center-(width/2),620); 
	}
	
	public Color healthColor(Unit character)
	{
		if(character.hp > character.maxHp/2) return hpGreen;
		else if(character.hp > character.maxHp/5) return orange;
		else return Color.RED;
	}
	
	public void drawBattleBackground(Graphics g)
	{
		if(battleAction != null)
		{
			if(battleAction.action == Action.KAGESHADOWS)
			{
				draw("battleBackground_Kage","battleBackground",0,0,g);
				return;
			}
		}
		
		int backgroundID = curTile().type;
		
		try
		{
			draw("battleBackground" + "_" + backgroundID,"battleBackground",0,0,g);
		}
		catch(Exception e)
		{
			draw("battleBackground_0","battleBackground",0,0,g);
		}
	}
	
	public void drawBattle(Graphics g)
	{
		drawBattleBackground(g);
		
		int[] animation = new int[7];
		for(int i=0; i<7; i++) animation[i] = Action.NOACTION;
		
		if(battleAction != null)
		{
			animation = battleAction.animation;
		}
		
		/**
		 * Draw party
		 */
		if(state != BATTLEFLED)
		{
			for(int i=0; i<3; i++)
			{
				Unit character = party[i];
				if(character.id != Character.NONE)
				{
					drawBattleCharacter(character,animation[i],g);
				}
			}
		}
		
		/**
		 * Draw monsters
		 */
		for(int i=0; i<monsters.size(); i++)
		{
			Unit monster = monsters.get(i);
			
			drawBattleMonster(monster,animation[i+3],g);
		}
		
		/**
		 * Draw health bars, etc.
		 */
		g.setColor(Color.BLACK);
		g.fillRect(0,520,850,130);
		g.setColor(Color.GRAY);
		g.fillRect(2,522,846,126);
		g.setColor(Color.BLACK);
		g.fillRect(280,520,290,130);
		g.setColor(darkGray);
		g.fillRect(282,522,286,126);
		
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE)
			{
				if(party[i].hp == 0) g.setColor(Color.RED);
				else if(currentUnit.equals(party[i]))
				{
					g.setColor(Color.YELLOW);
				}
				else g.setColor(Color.BLACK);
				g.setFont(arialBold16);
				g.drawString(party[i].name,25,550+40*i);
				
				g.setFont(arialBold12);
				g.setColor(Color.BLACK);
				drawStringRightAligned(""+party[i].hp,130,542+40*i,g);
				
				double hpBarLength = 100.0*((double)party[i].hp/party[i].maxHp);
				if(hpBarLength < 1) hpBarLength = 1;
				drawBar(140,534+40*i,104,10,(int)hpBarLength,healthColor(party[i]),g);
				
				g.setColor(Color.BLACK);
				drawStringRightAligned(""+party[i].mp,130,555+40*i,g);
				double mpBarLength = 100.0*((double)party[i].mp/party[i].maxMp);
				if(mpBarLength < 1) mpBarLength = 1;
				drawBar(140,547+40*i,104,10,(int)mpBarLength,mpBlue,g);
			}
		}
		
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp == 0) g.setColor(Color.RED);
			else if(currentUnit.equals(monsters.get(i))) g.setColor(Color.YELLOW);
			else g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString(monsters.get(i).species,595,550+27*i);
			
		}
		
		/**
		 * Fled from battle?
		 */
		if(state == BATTLEFLED)
		{
			drawBattleMessage("Escaped from the battle like a coward.",g);
		}
		
		/**
		 * Lost Battle?
		 */
		else if(state == BATTLELOST)
		{
			drawBattleMessage("Annihilated",g);
		}
		
		/**
		 * Won Battle?
		 */
		else if(state == BATTLEWON)
		{
			drawBattleMessage("You are a winner",g);
		}
	}
	
	public void drawAfterBattle(Graphics g)
	{
		g.setColor(menuBlue);
		g.fillRect(0,0,850,650);
		
		int numParty = 0;
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE) numParty++;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(50,50,750,550);
		g.setColor(Color.GRAY);
		g.fillRect(54,54,742,542);
		g.setColor(Color.BLACK);
		g.fillRect(54,400,742,4);
		g.fillRect(423,404,4,196);
		
		g.setColor(Color.BLACK);
		int width;
		String message;
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE)
			{
				int x = 425 - (75 + 125*(numParty-1)) + 250*i;
				
				g.setColor(Color.BLACK);
				g.fillRect(x-2,68,154,154);
				
				if(prevState == BATTLEFLED && party[i].hp > 0) draw("port" + party[i].name + "Fled",x,70,g);
				else draw("port" + party[i].name,x,70,g);
				
				if(party[i].hp == 0) draw("dead",x,70,g);
				
				g.setFont(arialBold18);
				g.setColor(Color.BLACK);
				width = getStringWidth(party[i].name + " - Lv " + party[i].level,g);
				g.drawString(party[i].name + " - Lv " + party[i].level,x+75-width/2,240);
				
				if(prevState != BATTLEFLED)
				{
					if(party[i].hp > 0)
					{
						g.setFont(arialBold14);
						message = "Gained " + getTotalExpGain() + " exp.";
						width = getStringWidth(message,g);
						g.drawString(message,x+75-width/2,270);
						
						int totalCubes = getTotalSPGain();
						if(totalCubes == 1) message = "Gained " + getTotalSPGain() + " SP.";
						else message = "Gained " + getTotalSPGain() + " SP.";
						width = getStringWidth(message,g);
						g.drawString(message,x+75-width/2,295);
						
						ArrayList<AfterBattleAlert> alerts = AfterBattleAlert.getAlertsForCharacterIndex(afterBattleAlerts, i);
						
						for(int alertIndex=0; alertIndex<alerts.size(); alertIndex++)
						{
							g.setColor(alerts.get(alertIndex).color);
							message = alerts.get(alertIndex).message;
							width = getStringWidth(message,g);
							g.drawString(message,x+75-width/2,320+25*alertIndex);
						}
					}
				}
			}
		}
		
		g.setColor(menuBlue);
		g.setFont(arialBold18);
		g.drawString("Money:",80,425);
		g.drawString("Items:",455,425);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		
		if(prevState == BATTLEFLED)
		{
			g.setColor(Color.BLACK);
			g.drawString("Fled from the battle and got nothing.",100,460);
			g.drawString("Fled from the battle and got nothing.",475,460);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.drawString("Got " + getTotalMoneyGain() + " money.",100,460);
			
			for(int i=0; i<itemDrops.size(); i++)
			{
				draw(itemDrops.get(i).getIconString(),455,440+30*i,g);
				g.drawString("Found " + itemDrops.get(i).name + " (" + itemDrops.get(i).qty + ").",485,460+30*i);
			}
		}
	}
	
	public void drawGameOver(Graphics g)
	{
		draw("gameOver",0,0,g);
	}
	
	public void drawDamageString(String displayString, int x, int y, Graphics g)
	{
		int damageFrame = Action.actionFromID(battleAction.action).damageFrame;
		if(animationID >= damageFrame && animationID < damageFrame+19)
		{
			y += getDamageStringOffset(animationID-damageFrame);
			g.drawString(displayString,x,y);
		}
	}
	
	public void drawBattleEffect(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(arialBold12);
		
		if(battleAction == null) return; //race condition?
		
		if(battleAction.action == Action.FLEE) //no targets/damages/effects to show
		{
			String battleMessage = battleAction.user.displayName() + " attempts to flee like a coward, but fails.";
			
			drawBattleMessage(battleMessage,g);
			
			return;
		}
		
		for(int i=0; i<battleAction.targets.size(); i++)
		{
			int[] coord;
			int coordXOffset;
			
			if(battleAction.targets.get(i) < 3)
			{
				coord = getPlayerCoordinates(battleAction.targets.get(i));
				coordXOffset = getUnitFromTargetIndex(battleAction.targets.get(i)).imageWidth + 20;
			}
			else
			{
				coord = getMonsterCoordinates(battleAction.targets.get(i) - 3);
				coordXOffset = -20;
			}
			
			ArrayList<Integer> valuesToDisplay = new ArrayList<Integer>(); //this is really dumb, but I don't know a better way to handle it
			valuesToDisplay.add(i);
			if(battleAction.values.size() == battleAction.targets.size()*2) valuesToDisplay.add(i+battleAction.values.size()/2);
			
			/**
			 * Drawing damage/healing/MP
			 */
			g.setFont(arialBold20);
			for(int j=0; j<valuesToDisplay.size(); j++)
			{
				if(battleAction.values.size() > 0)
				{
					int index = valuesToDisplay.get(j);
					int value = battleAction.values.get(index);
					String displayString;
					
					if(battleAction.action == Action.MURDER)
					{
						g.setColor(Color.WHITE);
						
						if(value == -1) //immune
						{
							displayString = "Immune";
						}
						else if(value == 0)
						{
							displayString = "Miss";
						}
						else
						{
							g.setColor(Color.RED);
							displayString = "Murder";
						}
					}
					else
					{
						boolean miss = false;
						if(battleAction.miss.size() > index) //miss array may be empty
						{
							if(battleAction.miss.get(index))
							{
								miss = true;
							}
						}
						
						String damageDisplay = "" + value;
						if(battleAction.crit.size() > index) //crit array may be empty
						{
							if(battleAction.crit.get(index))
							{
								damageDisplay += " !!";
							}
						}
						
						if(battleAction.mp.get(index) == true) g.setColor(mpBlue);
						else if(value >= 0) g.setColor(Color.WHITE);
						else g.setColor(hpGreen);
						
						if(value < 0) displayString = ""+value*-1;
						else if(value > 0) displayString = damageDisplay;
						else if(miss) displayString = "Miss";
						else displayString = "Immune";
					}
					
					int yOffset = 0;
					if(valuesToDisplay.size() == 2 && j == 0) yOffset = -50;
					drawDamageString(displayString,coord[0]+coordXOffset,coord[1]+100+yOffset,g);
				}
				else if(battleAction.miss.size() > 0) //status move missed
				{
					if(battleAction.miss.get(valuesToDisplay.get(j)))
					{
						g.setColor(Color.WHITE);
						
						int yOffset = 0;
						if(valuesToDisplay.size() == 2 && j == 0) yOffset = -50;
						drawDamageString("Miss",coord[0]+coordXOffset,coord[1]+100+yOffset,g);
					}
				}
			}
			
			String battleMessage = "";
			
			if(animationID > Action.actionFromID(battleAction.action).numFrames-2) animationID = Action.actionFromID(battleAction.action).numFrames-2; //race condition in animationLoop?
			int animationMap = Action.animationMap(battleAction.action)[animationID];
			int xOffset = Action.xOffset(battleAction.action)[animationID];
			int yOffset = Action.yOffset(battleAction.action)[animationID];
			
			//adjust for image height/width
			int actionImageWidth = Action.actionFromID(battleAction.action).imageWidth;
			if(actionImageWidth == 0) actionImageWidth = 125;
			int actionImageHeight = Action.actionFromID(battleAction.action).imageHeight;
			if(actionImageHeight == 0) actionImageHeight = 125;
			xOffset += (getUnitFromTargetIndex(battleAction.targets.get(i)).imageWidth-actionImageWidth)/2;
			yOffset += (getUnitFromTargetIndex(battleAction.targets.get(i)).imageHeight-actionImageHeight)/2;
			
			g.setFont(arialBold16);
			
			/**
			 * Attack
			 */
			if(battleAction.action == Action.ATTACK)
			{
				String attackAnimation = "";
				if(battleAction.user.unitType == Unit.MONSTER)
				{
					attackAnimation = battleAction.user.attackAnimation;
				}
				else
				{
					if(battleAction.user.equip[Unit.WEAPON].id != Item.NOITEM)
					{
						attackAnimation = battleAction.user.equip[Unit.WEAPON].attackAnimation;
					}
					else
					{
						attackAnimation = HIT;
					}
				}
				
				draw(attackAnimation + "_" + animationMap,"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Brian
			 */
			else if(battleAction.action == Action.BRIANPUNCH)
			{
				draw("brianPunch_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.CHEEZITBLAST)
			{
				draw("cheezItBlast_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.MTNDEWWAVE)
			{
				draw("mtnDewWave_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.GOTTAGOFAST || battleAction.action == Action.GOTTAGOFASTER)
			{
				draw("gottaGoFast_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.COOLRANCHLASER)
			{
				draw("coolRanchLaser_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset-50,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.MURDER || battleAction.action == Action.MASSMURDER)
			{
				draw("murder_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset-50,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BRIANSMASH)
			{
				draw("brianPunch_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); //TODO: different animation
			}
			else if(battleAction.action == Action.FLAVOREXPLOSION)
			{
				draw("flavorExplosion_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Alex
			 */
			else if(battleAction.action == Action.BARF || battleAction.action == Action.VOMITERUPTION)
			{
				draw("barf_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.FLAIL)
			{
				draw("hit_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.ANNOY)
			{
				draw("annoy_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.COWER)
			{
				draw("cower_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.SHRIEK)
			{
				draw("shriek_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.EATPOPTART)
			{
				draw("item0_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.KAMIKAZE)
			{
				draw("kamikaze_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.SUMMONTRAINS)
			{
				draw("summonTrains_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Ryan
			 */
			else if(battleAction.action == Action.BLESSINGOFARINO)
			{
				draw("blessingOfArino_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+50+xOffset,coord[1]-30+yOffset,g);
			}
			else if(battleAction.action == Action.SILLYDANCE)
			{
				draw("sillyDance_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BAJABLAST)
			{
				draw("bajaBlast_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BLESSINGOFMIKU)
			{
				draw("blessingOfMiku_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+50+xOffset,coord[1]-30+yOffset,g);
			}
			else if(battleAction.action == Action.RADICALRIFF)
			{
				draw("radicalRiff_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BLUESHIELD || battleAction.action == Action.BLUEBARRIER)
			{
				draw("blueShield_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.AMP)
			{
				//draw("sillyDance_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); TODO
			}
			else if(battleAction.action == Action.MYSTERIOUSMELODY)
			{
				draw("mysteriousMelody_" + battleAction.element + "_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.CHIPTUNEOFLIFE)
			{
				draw("chiptuneOfLife_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Michael
			 */
			else if(battleAction.action == Action.SHURIKEN)
			{
				draw("shuriken_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.KAGESHADOWS)
			{
				draw("kageShadows_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.DEFENDHONOR || battleAction.action == Action.HONORFORALL)
			{
				draw("defendHonor_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.NINJUTSUSLICE)
			{
				draw("slice_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.SAMURAISLASH)
			{
				draw("slice_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BUSHIDOBLADE)
			{
				draw("slice_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.MURAMASAMARA)
			{
				draw("slice_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.INFLICTSHAME)
			{
				draw("inflictShame_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Kitten
			 */
			else if(battleAction.action == Action.FIRE)
			{
				draw("fire_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.BIGFIRE)
			{
				draw("fire_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); //TODO: big fire image
			}
			else if(battleAction.action == Action.PURR)
			{
				//draw("lightningBolt_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); TODO
			}
			else if(battleAction.action == Action.CATNAP)
			{
				draw("catNap_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.LIGHTNINGBOLT)
			{
				draw("lightningBolt_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.LIGHTNINGSTORM)
			{
				draw("lightningBolt_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); //TODO: lightning storm image
			}
			else if(battleAction.action == Action.DEVOUR)
			{
				draw("devour_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.STEAL)
			{
				//draw("lightningBolt_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g); TODO
			}
			else if(battleAction.action == Action.EARTHSPIKE) //TODO: different image for this
			{
				draw("earthquake_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.EARTHQUAKE)
			{
				draw("earthquake_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.CATSCRATCH)
			{
				draw("catScratch_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Item
			 */
			else if(battleAction.action == Action.HPITEM || battleAction.action == Action.MPITEM || battleAction.action == Action.REVIVEITEM)
			{
				Item usedItem = battleAction.item;
				
				draw("item" + usedItem.id + "_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			/**
			 * Poison/regen 
			 */
			else if(battleAction.action == Action.POISON)
			{
				draw("poison_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action == Action.REGEN)
			{
				draw("regen_" + Action.animationMap(battleAction.action)[animationID],"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			if(battleAction.action == Action.STEAL)
			{
				if(battleAction.stealOutcome == 1)
				{
					battleMessage = "Stole " + battleAction.item.name + " (" + battleAction.item.qty + ").";
				}
				else if(battleAction.stealOutcome == -1)
				{
					battleMessage = "Nothing to steal.";
				}
				else
				{
					battleMessage = "Failed to steal anything.";
				}
			}
			else if(battleAction.item != null)
			{
				battleMessage = battleAction.item.name;
			}
			else if(battleAction.action >= 10) //skills are 10+, don't show a message for stuff like attack/poison/flee
			{
				battleMessage = Action.actionFromID(battleAction.action).name;
			}
			
			if(!battleMessage.equals("")) drawBattleMessage(battleMessage,g);
		}
	}
	
	public int getDamageStringOffset(int aID)
	{
		if(aID < 2) return 0;
		else if(aID < 4) return -10;
		else if(aID < 6) return -20;
		else if(aID < 8) return -30;
		else if(aID < 10) return -20;
		else if(aID < 12) return -10;
		else if(aID < 14) return 0;
		else if(aID < 16) return -10;
		
		return 0;
	}
	
	public void drawBattleMessage(String string, Graphics g)
	{
		if(string.equals("")) return;
		
		int width = getStringWidth(string,g);
		
		g.setColor(Color.BLACK);
		g.fillRect(425-width/2-30,15,width+60,30);
		g.setColor(menuBlue);
		g.fillRect(425-width/2-28,17,width+56,26);
		g.setColor(Color.WHITE);
		
		g.drawString(string,425-width/2,35);
	}
	
	public static int calculateDamage(Unit attack, int action, Unit target)
	{
		ArrayList<Boolean> crit = new ArrayList<Boolean>();
		ArrayList<Boolean> miss = new ArrayList<Boolean>();
		return calculateDamage(attack,action,target,crit,miss);
	}
	
	public static int calculateDamage(Unit attack, int action, Unit target, ArrayList<Boolean> crit, ArrayList<Boolean> miss)
	{
		return calculateDamage(attack,action,target,crit,miss,NOELEMENT);
	}
	
	public static int calculateDamage(Unit attack, int action, Unit target, ArrayList<Boolean> crit, ArrayList<Boolean> miss, int element)
	{
		int value = 1;
		
		crit.add(false);
		miss.add(false);
		
		if(rand == null)
		{
			rand = new Random();
		}
		
		//TODO: refactor more, this is a mess
		
		/**
		 * Attack
		 */
		if(action == Action.ATTACK)
		{
			int hitRate = Unit.getHitRate(attack,target,0);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 0;
			if(attack.unitType == Unit.CHARACTER)
			{
				ddmg = (5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			}
			else
			{
				ddmg = (5.0 + ((2 * Math.pow(attack.str,3.0))/32.0))*(1.0 - (2.5*target.def)/100.0) - target.def;
			}
			
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.BERSERK] > 0)
			{
				ddmg *= 1.25;
			}
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(rand.nextInt(100) < attack.critRate)
			{
				ddmg *= 2.0;
				crit.clear();
				crit.add(true);
			}
			
			value = (int) ddmg;
			if(value < 1) value = 1;
			
			return value;
		}
		
		/**
		 * Brian
		 */
		else if(action == Action.BRIANPUNCH)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			value = (int) ddmg;
			if(value < 1) value = 1;
			
			return value;
		}
		else if(action == Action.CHEEZITBLAST)
		{
			int hitRate = Unit.getHitRate(attack,target,30);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[SNACK] == 100) return 0;
			
			double ddmg = (10.0 + 0.8*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.8))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[SNACK]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.COOLRANCHLASER)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[SNACK] == 100) return 0;
			
			double ddmg = (40.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[SNACK]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.BRIANSMASH)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			//stronger Brian Punch that ignores defense
			double ddmg = (40.0 + 10.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0);
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.FLAVOREXPLOSION)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[SNACK] == 100) return 0;
			
			double ddmg = (20.0 + 1.7*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.8))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[SNACK]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		
		/**
		 * Alex
		 */
		else if(action == Action.BARF || action == Action.VOMITERUPTION)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[POISON] == 100) return 0;
			
			double ddmg = (20.0 + 1.5*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[POISON]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.FLAIL)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 2.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.SHRIEK)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = (25.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.KAMIKAZE)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 7.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.SUMMONTRAINS)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 4.0*(30.0 + ((attack.str + (attack.level/7.0) + attack.dex)*(attack.str * attack.mag))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		
		/**
		 * Ryan
		 */
		else if(action == Action.BAJABLAST)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[WATER] == 100) return 0;
			
			double ddmg = (20.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[WATER]/100.0);
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.MYSTERIOUSMELODY)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[element] == 100) return 0;
			
			double ddmg = (20.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[element]/100.0);
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		
		/**
		 * Michael 
		 */
		else if(action == Action.SHURIKEN)
		{
			double ddmg = (5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.NINJUTSUSLICE)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 1.25*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk + 1.5*target.mag)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.SAMURAISLASH)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 1.25*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			int halfChance = 40 + attack.dex;
			if(rand.nextInt(100) < halfChance) //TODO: work on this forumla, some monsters should be immune, etc.
			{
				if(target.hp/2 > ddmg) ddmg = target.hp/2;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.BUSHIDOBLADE)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double mod = 7.0/(6.0*((double)attack.hp/attack.maxHp)+1.0);
			
			double ddmg = mod*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.MURAMASAMARA)
		{
			int hitRate = Unit.getHitRate(attack,target,-50);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			double ddmg = 5.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		
		/**
		 * Kitten
		 */
		else if(action == Action.FIRE)
		{
			int hitRate = Unit.getHitRate(attack,target,10); //TODO: figure out how these hit rate boosts should work
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[FIRE] == 100) return 0;
			
			double ddmg = (20.0 + Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[FIRE]/100.0);
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.BIGFIRE)
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[FIRE] == 100) return 0;
			
			double ddmg = (40.0 + 1.6*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[FIRE]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.LIGHTNINGBOLT || action == Action.LIGHTNINGSTORM) //TODO: make Storm do more damage? or no?
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[LIGHTNING] == 100) return 0;
			
			double ddmg = (30.0 + 1.15*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[LIGHTNING]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.DEVOUR)
		{
			double ddmg = (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def; //same as Brian Punch
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.EARTHSPIKE || action == Action.EARTHQUAKE) //TODO: same damage, or no?
		{
			int hitRate = Unit.getHitRate(attack,target,10);
			
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
			
			if(target.elementResistance[EARTH] == 100) return 0;
			
			double ddmg = (35.0 + 1.25*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(1.0 - (2.5*target.mdef)/100.0) - target.mdef;
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(target.status[Unit.SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			ddmg -= ddmg*(target.elementResistance[EARTH]/100.0);
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		else if(action == Action.CATSCRATCH)
		{
			double ddmg = (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(1.0 - (2.5*target.def)/100.0) - target.def; //same as Brian Punch
			ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
			
			if(attack.status[Unit.ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(target.status[Unit.PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[Unit.DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
			
			if(ddmg < 0 && ddmg > -1) ddmg = -1;
			
			value = (int) ddmg;
			if(value == 0) value = 1;
			
			return value;
		}
		
		/**
		 * Poison
		 */
		else if(action == Action.POISON)
		{
			//shouldn't care about resistance for poison status damage
			//if(target.elementResistance[POISON] == 100) return 0; 
			
			double ddmg = attack.maxHp * 0.05;
			ddmg = randomize(ddmg,85,115); //randomize
			
			value = (int) ddmg;
			if(value < 1) value = 1;
			
			//ddmg -= ddmg*(target.elementResistance[POISON]/100.0);
			
			return value;
		}
		
		return value;
	}
	
	public static double randomize(double value, int lowRange, int highRange)
	{
		int range = highRange - lowRange;
		
		return value * (rand.nextInt(range) + lowRange)/100.0;
	}
	
	public int topAliveMonsterIndex()
	{
		//the monster that should be selected by default when targeting monsters
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) return i;
		}
		
		return 0;
	}
	
	public int aboveMonsterIndex(int currentIndex)
	{
		//the monster that should be selected when you press up
		for(int i=currentIndex-1; i>=0; i--)
		{
			if(monsters.get(i).hp > 0) return i;
		}
		
		return currentIndex;
	}
	
	public int belowMonsterIndex(int currentIndex)
	{
		//the monster that should be selected when you press down
		for(int i=currentIndex+1; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) 
			{
				return i;
			}
		}
		
		return currentIndex;
	}
	
	public int topAlivePartyIndex()
	{
		for(int i=0; i<3; i++)
		{
			if(party[i].hp > 0) return i;
		}
		
		return 0;
	}
	
	public int topDeadPartyIndex()
	{
		for(int i=0; i<3; i++)
		{
			if(party[i].hp == 0) return i;
		}
		
		return 0;
	}
	
	public int abovePartyIndex(int currentIndex)
	{
		//the monster that should be selected when you press up
		for(int i=currentIndex-1; i>=0; i--)
		{
			if(party[i].id != Character.NONE) return i;
		}
		
		return currentIndex;
	}
	
	public int belowPartyIndex(int currentIndex)
	{
		//the monster that should be selected when you press down
		for(int i=currentIndex+1; i<3; i++)
		{
			if(party[i].id != Character.NONE) return i;
		}
		
		return currentIndex;
	}
	
	public int[] getPlayerCoordinates(int index)
	{		
		int[] coord = new int[2]; //x,y
		
		int partySize = partySize();
		
		coord[0] = 100-25*index;
		if(partySize == 1)
		{
			coord[1] = 250;
		}
		else if(partySize == 2)
		{
			coord[1] = 188+125*index;
		}
		else
		{
			coord[1] = 125+125*index;
		}
		
		if(battleAction != null && state != BATTLE)
		{
			if(battleAction.user.unitType == Unit.CHARACTER && battleAction.action != Action.POISON && battleAction.action != Action.REGEN)
			{
				if(battleAction.user.getBattleIndex() == party[index].getBattleIndex())
				{
					coord[0] += 50;
				}
			}
		}
		
		return coord;
	}
	
	public int[] getMonsterCoordinates(int index)
	{
		int[] coord = new int[2];
		
		int totalHeight = 0;
		int heightOffset = 0;
		for(int i=0; i<monsters.size(); i++)
		{
			int height = monsters.get(i).imageHeight;
			totalHeight += height;
			if(i < index) heightOffset += height;
		}
		
		coord[0] = 550 + 25*index;
		coord[1] = (260 - totalHeight/2) + heightOffset;
		
		if(battleAction != null && state != BATTLE)
		{
			if(battleAction.user.unitType == Unit.MONSTER && battleAction.action != Action.POISON && battleAction.action != Action.REGEN)
			{
				if(battleAction.user.getBattleIndex() == monsters.get(index).getBattleIndex())
				{
					coord[0] -= 50;
				}
			}
		}
		
		return coord;
	}
	
	public int partySize()
	{
		int partySize = 0;
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE) partySize++;
		}
		
		return partySize;
	}
	
	public void drawBattleTarget(Graphics g)
	{
		int targetType = selectedAction.targetType;
		
		if(targetType == Action.ONEENEMY || (targetType == Action.ONEUNIT && curIndex() >= 3))
		{
			int index;
			if(targetType == Action.ONEENEMY) index = curIndex();
			else index = curIndex() - 3;
			
			int[] coord = getMonsterCoordinates(index);
			coord[0] -= 50;
			coord[1] += monsters.get(index).imageHeight/2;
			
			draw("pointer",coord[0],coord[1],g);
		}
		else if(targetType == Action.ALLENEMIES)
		{
			for(int i=0; i<monsters.size(); i++)
			{
				if(monsters.get(i).hp > 0)
				{
					int[] coord = getMonsterCoordinates(i);
					coord[0] -= 50;
					coord[1] += monsters.get(i).imageHeight/2;
					
					draw("pointer",coord[0],coord[1],g);
				}
			}
		}
		else if(targetType == Action.ONEALLY || targetType == Action.SELF || (targetType == Action.ONEUNIT && curIndex() < 3))
		{
			int[] coord = getPlayerCoordinates(curIndex());
			coord[0] += -40;
			coord[1] += 75;
			
			draw("pointer",coord[0],coord[1],g);
			
		}
		else if(targetType == Action.ALLALLIES)
		{
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE && party[i].hp > 0)
				{
					int[] coord = getPlayerCoordinates(i);
					coord[0] += - 40;
					coord[1] += 75;
					
					draw("pointer",coord[0],coord[1],g);
				}
			}
		}
	}
	
	public void setState(int setState)
	{
		prevState = state;
		state = setState;
	}
	
	public void drawBattleChoice(Graphics g)
	{
		int coord[] = getPlayerCoordinates(currentUnit.index);
		int x = coord[0];
		int y = coord[1];
		
		int choiceIndex = curIndex();
		if(state == BATTLETARGET)
		{
			choiceIndex = prevIndex(1);
		}
		
		if(state == BATTLECHOICE || (state == BATTLETARGET && prevState == BATTLECHOICE))
		{
			g.setColor(Color.WHITE);
			g.drawString("Attack",330,550);
			g.drawString(currentUnit.getSkillString(),330,577);
			g.drawString("Item",330,604);
			g.drawString("Flee",330,631);
			
			draw("pointer",290,530+27*choiceIndex,g);
		}
		else if(state == BATTLESELECTSKILL || (state == BATTLETARGET && prevState == BATTLESELECTSKILL))
		{
			int numActiveSkills = currentUnit.getLearnedActiveSkills().size();
			
			int top = Math.max(0,choiceIndex-cursorAlign);
			int bottom = Math.min(top+3,numActiveSkills-1);
			
			for(int i=top; i<=bottom; i++)
			{
				if(currentUnit.canUseSkill(currentUnit.getLearnedActiveSkills().get(i).action)) g.setColor(Color.WHITE);
				else g.setColor(Color.GRAY);
				
				g.drawString(currentUnit.getLearnedActiveSkills().get(i).action.name,330,550+27*(i-top));
				drawStringRightAligned(currentUnit.getLearnedActiveSkills().get(i).action.mp + " MP",535,550+27*(i-top),g);
			}
			
			if(numActiveSkills > 4)
			{
				double dheight = 126.0*(4.0/numActiveSkills);
				int height = (int) dheight;
				
				int barStart = 522 + Math.max(0, (126-height)*(choiceIndex-cursorAlign))/(numActiveSkills-4);
				
				g.setColor(Color.BLACK);
				g.fillRect(553,522,17,126);
				g.setColor(menuBlue);
				g.fillRect(555,barStart,13,height);
			}
			
			if(state == BATTLETARGET)
			{
				draw("pointerSelected",290,530+27*cursorAlign,g);
			}
			else
			{
				draw("pointer",290,530+27*cursorAlign,g);
			}
		}
		else if(state == BATTLESELECTITEM || (state == BATTLETARGET && prevState == BATTLESELECTITEM))
		{
			int numItems = inventory.size();
			
			int top = Math.max(0,choiceIndex-cursorAlign);
			int bottom = Math.min(top+3,numItems-1);
			
			g.setColor(Color.WHITE);
			
			for(int i=top; i<=bottom; i++)
			{
				draw(inventory.get(i).getIconString(),330,530+27*(i-top),g);
				g.drawString(inventory.get(i).name,362,550+27*(i-top));
				drawStringRightAligned(""+inventory.get(i).qty,535,550+27*(i-top),g);
			}
			
			if(numItems > 4)
			{
				double dheight = 126.0*(4.0/numItems);
				int height = (int) dheight;
				
				int barStart = 522 + Math.max(0, (126-height)*(choiceIndex-cursorAlign))/(numItems-4);
				
				g.setColor(Color.BLACK);
				g.fillRect(553,522,17,126);
				g.setColor(menuBlue);
				g.fillRect(555,barStart,13,height);
			}
			
			if(state == BATTLETARGET)
			{
				draw("pointerSelected",290,530+27*cursorAlign,g);
			}
			else
			{
				draw("pointer",290,530+27*cursorAlign,g);
			}
		}
		
		draw("battleCurrentUnit",x+35,y-20,g);
	}
	
	public void drawBattleCharacter(Unit character, int animation, Graphics g)
	{
		if(animation == NORMAL && character.status[Unit.SLEEP] > 0)
		{
			animation = ASLEEP;
		}
		
		int[] coord = getPlayerCoordinates(character.index);
		
		if(state == BATTLE) //in the "transition" frame, override the battleAction animation
		{
			animation = Action.NOACTION;
		}
		
		if(character.hp == 0)
		{
			animation = DEAD;
		}
		else if(character.status[Unit.DEFEND] > 0) //defend animation should override others (like damaged)
		{
			animation = DEFENDING;
		}
		
		try
		{
			draw(character.name + "_" + animation,"battleCharacter",coord[0],coord[1],g);
		}
		catch(Exception e)
		{
			draw(character.name + "_" + NORMAL,"battleCharacter",coord[0],coord[1],g);
		}
		
		if(drawWeaponForAnimation(animation) && character.equip[Unit.WEAPON].id != Item.NOITEM)
		{
			draw(character.equip[Unit.WEAPON].imageName + character.id,"weapon",coord[0],coord[1],g);
		}
		
		drawPlayerStatusIcons(character,coord[0]+(character.imageWidth-100),coord[1]+(character.imageHeight-character.spriteHeight),g);
	}
	
	public boolean drawWeaponForAnimation(int animation)
	{
		if(animation == NORMAL || animation == ATTACK || animation == ASLEEP)
		{
			return true;
		}
		
		/**
		 * Michael's sword skills
		 */
		if(animation == Action.NINJUTSUSLICE || animation == Action.SAMURAISLASH || animation == Action.BUSHIDOBLADE || animation == Action.MURAMASAMARA)
		{
			return true;
		}
		
		return false;
	}
	
	public void drawBattleMonster(Unit monster, int animation, Graphics g)
	{
		if(animation == NORMAL && monster.status[Unit.SLEEP] > 0)
		{
			animation = ASLEEP;
		}
		
		if(monster.hp == 0) return;
		
		int[] coord = getMonsterCoordinates(monster.index);
		
		if(state == BATTLE)
		{
			animation = Action.NOACTION;
		}
		
		try
		{
			draw(monster.species + "_" + animation,"monster",coord[0],coord[1],g);
		}
		catch(Exception e)
		{
			draw(monster.species + "_" + NORMAL,"monster",coord[0],coord[1],g);
		}
		
		drawMonsterStatusIcons(monster,coord[0],coord[1]+(monster.imageHeight-monster.spriteHeight),g);
	}
	
	public void drawPlayerStatusIcons(Unit unit, int x, int y, Graphics g)
	{
		ArrayList<Integer> statusList = new ArrayList<Integer>();
		for(int i=0; i<unit.status.length; i++)
		{
			if(i == Unit.DEFEND) continue; //don't draw an icon for Defend
			
			if(unit.status[i] != 0) //also need to consider negatives for "flags"
			{
				statusList.add(i);
			}
		}
		
		for(int i=0; i<statusList.size(); i++)
		{
			draw("iconStatus" + statusList.get(i),x+100+25*(i%2),y+25*(i/2),g);
		}
	}
	
	public void drawMonsterStatusIcons(Unit unit, int x, int y, Graphics g)
	{
		ArrayList<Integer> statusList = new ArrayList<Integer>();
		for(int i=0; i<unit.status.length; i++)
		{
			if(i == Unit.DEFEND) continue; //don't draw an icon for Defend
			
			if(unit.status[i] != 0)
			{
				statusList.add(i);
			}
		}
		
		for(int i=0; i<statusList.size(); i++)
		{
			draw("iconStatus" + statusList.get(i),x-25-25*(i%2),y+25*(i/2),g);
		}
	}
	
	public void drawInventory(Graphics g)
	{
		draw("inventory",0,0,g);
		
		int top = Math.max(0, curIndex() - cursorAlign);
		int bottom = Math.min(top+14,inventory.size()-1);
		g.setFont(arialBold16);
		g.setColor(Color.BLACK);
		for(int i=top; i<=bottom; i++)
		{
			g.setColor(Color.BLACK);
			g.fillRect(300,90+32*(i-top),230,25);
			g.setColor(lightGray);
			g.fillRect(301,91+32*(i-top),228,23);
			g.setColor(Color.BLACK);
			g.setFont(arialBold18);
			g.drawString(inventory.get(i).name,340,110+32*(i-top));
			draw(inventory.get(i).getIconString(),300,90+32*(i-top),g);
			
			g.setFont(arialBold14);
			g.setColor(Color.BLACK);
			g.drawString(""+inventory.get(i).qty,510,108+32*(i-top));
			
			if(state == INVENTORYREARRANGE && i == prevIndex(1))
			{
				draw("pointerSelected",270,90+32*(i-top),g);
			}
		}
		
		if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			draw("pointer",270,90+32*cursorAlign,g);
		}
		
		if(state != INVENTORYSORT)
		{
			Item selectedItem = inventory.get(curIndex());
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold14);
			FontMetrics font;
			int width;
			
			if(selectedItem.isEquipment())
			{
				drawEquipInfo(selectedItem, true, g);
			}
			else
			{
				font = g.getFontMetrics();
				width = font.stringWidth(selectedItem.desc);
				g.drawString(selectedItem.desc,425-(width/2),610);
			}
		}
		else
		{
			draw("pointer",200+curIndex()%2*240,21,g);
		}
		
		if(inventory.size() > 15)
		{
			int barIndex = 0;
			if(state == INVENTORY || state == INVENTORYREARRANGE)
			{
				barIndex = curIndex() - cursorAlign;
			}
			double dheight = 486.0*(15.0/inventory.size());
			int height = (int) dheight;
			
			int barStart = 77 + Math.max(0, (486-height)*barIndex)/(inventory.size()-15);
			
			g.setColor(menuBlue);
			g.fillRect(642,barStart,21,height);
		}
	}
	
	public void drawMapDialogue(Graphics g)
	{
		draw("dialogueBox",350,100,g);
		
		if(curEvent.type == Event.NPC && curIndex() < curEvent.getDialogue(curEvent.state).size()-1)
		{
			draw("npcNextDialogue",705,225,g);
		}
		
		g.setColor(menuBlue);
		g.fillRect(360,115,110,110);
		
		try
		{
			if(curEvent.type == Event.CHEST) draw("npcPortChest","npc",365,120,g);
			else if(curEvent.name.equals("Sign")) draw("npcPortSign","npc",365,120,g);
			else draw("npcPort" + curEvent.imageName,"npc",365,120,g);	
		}
		catch(Exception e)
		{
			System.out.println("port error: " + curEvent.name);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		if(curEvent.type == Event.NPC) g.drawString(curEvent.name,485,130);
		else if(curEvent.type == Event.CHEST) g.drawString("Item",485,130);
		
		g.setColor(Color.BLACK);
		g.fillRect(480,150,225,75);
		g.setColor(Color.WHITE);
		g.fillRect(482,152,221,71);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold12);
		g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[0],490,170);
		g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[1],490,190);
		g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[2],490,210);
	}
	
	public void drawInnChoice(Graphics g)
	{
		draw("dialogueBox",350,100,g);
		
		g.setColor(menuBlue);
		g.fillRect(360,115,110,110);
		
		try
		{
			draw("npcPort" + curEvent.imageName,"npc",365,120,g);	
		}
		catch(Exception e)
		{
			System.out.println("port error: " + curEvent.name);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		g.drawString(curEvent.name,485,130);
		
		g.setColor(Color.BLACK);
		g.fillRect(480,150,225,75);
		g.setColor(Color.WHITE);
		g.fillRect(482,152,221,71);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold12);
		g.drawString("Sleep at the inn for " + curEvent.innPrice + " money?",490,170);
		
		g.drawString("No",530,210);
		g.drawString("Yes",630,210);
		
		draw("pointer",490+100*curIndex(),190,g);
	}
	
	public void drawEditParty(Graphics g)
	{
		g.setColor(menuGreen);
		g.fillRect(0,0,850,650);
		
		/**
		 * In Party
		 */
		g.setColor(menuDarkGreen);
		g.fillRect(15,30,400,590);
		g.setColor(menuLightBlue);
		g.fillRect(25,40,380,570);
		
		g.setColor(Color.WHITE);
		g.setFont(arialBold20);
		g.drawString("Party",195,30);
		
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE)
			{
				g.setColor(Color.BLACK);
				g.fillRect(40,67+180*i,154,154);
				draw("port" + party[i].name,42,69+180*i,g);
				if(party[i].hp == 0) draw("dead",42,69+180*i,g);
				
				g.setFont(arialBold20);
				g.drawString(party[i].name,215,85+180*i);
				g.setFont(arialBold16);
				g.drawString(party[i].getClassName(),215,110+180*i);
				
				g.setFont(arialBold14);
				g.drawString("Lv " + party[i].level,215,145+180*i);
				int barLength = 126*party[i].exp/party[i].getExpNext();
				drawBar(265,133+180*i,130,14,barLength,expPurple,g);
				g.setColor(Color.BLACK);
				g.setFont(arialBold12);
				FontMetrics font = g.getFontMetrics();
				int width = font.stringWidth(party[i].exp + " / " + party[i].getExpNext());
				g.drawString(party[i].exp + " / " + party[i].getExpNext(),330-(width/2),130+180*i);
				
				g.setColor(Color.BLACK);
				g.setFont(arialBold14);
				g.drawString("HP",215,180+180*i);
				barLength = 126*party[i].hp/party[i].maxHp;
				if(party[i].hp > 0 && barLength == 0) barLength = 1;
				drawBar(265,168+180*i,130,14,barLength,healthColor(party[i]),g);
				g.setFont(arialBold12);
				font = g.getFontMetrics();
				width = font.stringWidth(party[i].hp + " / " + party[i].maxHp);
				g.setColor(Color.BLACK);
				g.drawString(party[i].hp + " / " + party[i].maxHp,330-(width/2),165+180*i);
				
				g.setColor(Color.BLACK);
				g.setFont(arialBold14);
				g.drawString("MP",215,215+180*i);
				barLength = 126*party[i].mp/party[i].maxMp;
				if(party[i].mp > 0 && barLength == 0) barLength = 1;
				drawBar(265,203+180*i,130,14,barLength,mpBlue,g);
				g.setFont(arialBold12);
				width = font.stringWidth(party[i].mp + " / " + party[i].maxMp);
				g.setColor(Color.BLACK);
				g.drawString(party[i].mp + " / " + party[i].maxMp,330-(width/2),200+180*i);
			}
		}
		
		/**
		 * Not In Party
		 */
		if(party[3].id != Character.NONE) //only draw this section if there are abandoned party members
		{
			g.setColor(menuDarkGreen);
			g.fillRect(435,30,400,590);
			g.setColor(menuLightBlue);
			g.fillRect(445,40,380,570);
			
			g.setColor(Color.WHITE);
			g.setFont(arialBold20);
			g.drawString("Abandoned",595,30);
			
			for(int i=3; i<party.length; i++)
			{
				if(party[i].id != Character.NONE)
				{
					int xPos = (i+1)%2;
					int yPos = (i-3)/2;
					
					g.setColor(Color.BLACK);
					g.fillRect(470+175*xPos,67+180*yPos,154,154);
					draw("port" + party[i].name,472+175*xPos,69+180*yPos,g);
					if(party[i].hp == 0) draw("dead",472+175*xPos,69+180*yPos,g);
				}
			}
		}
		
		if(state == EDITPARTYSWITCH)
		{
			if(prevIndex(1) < 3)
			{
				draw("pointerSelected",15,120+180*prevIndex(1),g);
			}
			else
			{
				int xPos = (prevIndex(1)+1)%2;
				int yPos = (prevIndex(1)-3)/2;
				
				draw("pointerSelected",445+175*xPos,120+180*yPos,g);
			}
		}
		
		if(curIndex() < 3)
		{
			draw("pointer",15,120+180*curIndex(),g);
		}
		else
		{
			int xPos = (curIndex()+1)%2;
			int yPos = (curIndex()-3)/2;
			
			draw("pointer",445+175*xPos,120+180*yPos,g);
		}
	}
	
	public void drawMainMenu(Graphics g)
	{
		g.setColor(menuGreen);
		g.fillRect(0,0,850,650);
		
		g.setColor(menuDarkGreen);
		g.fillRect(15,30,560,590);
		g.setColor(menuLightBlue);
		g.fillRect(25,40,540,570);
		
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE)
			{
				g.setColor(Color.BLACK);
				g.fillRect(40,67+180*i,154,154);
				draw("port" + party[i].name,42,69+180*i,g);
				if(party[i].hp == 0) draw("dead",42,69+180*i,g);
				
				int statusIndex = 0;
				for(int j=0; j<party[i].status.length; j++)
				{
					if(party[i].status[j] > 0)
					{
						draw("iconStatus" + j,480-25*statusIndex,70+180*i,g);
						statusIndex++;
					}
				}
				
				g.setFont(arialBold20);
				g.drawString(party[i].name,215,85+180*i);
				g.setFont(arialBold16);
				g.drawString(party[i].getClassName(),215,110+180*i);
				
				g.setFont(arialBold14);
				g.drawString("Lv " + party[i].level,215,145+180*i);
				int barLength = 271*party[i].exp/party[i].getExpNext();
				drawBar(265,129+180*i,275,8,barLength,expPurple,g);
				g.setColor(Color.BLACK);
				g.setFont(arialBold12);
				FontMetrics font = g.getFontMetrics();
				int width = font.stringWidth(party[i].exp + " / " + party[i].getExpNext());
				g.drawString(party[i].exp + " / " + party[i].getExpNext(),400-(width/2),126+180*i);
				
				g.setColor(Color.BLACK);
				g.setFont(arialBold14);
				g.drawString("HP",215,180+180*i);
				barLength = 271*party[i].hp/party[i].maxHp;
				if(party[i].hp > 0 && barLength == 0) barLength = 1;
				drawBar(265,168+180*i,275,14,barLength,healthColor(party[i]),g);
				g.setFont(arialBold12);
				font = g.getFontMetrics();
				width = font.stringWidth(party[i].hp + " / " + party[i].maxHp);
				g.setColor(Color.BLACK);
				g.drawString(party[i].hp + " / " + party[i].maxHp,400-(width/2),165+180*i);
				
				g.setColor(Color.BLACK);
				g.setFont(arialBold14);
				g.drawString("MP",215,215+180*i);
				barLength = 271*party[i].mp/party[i].maxMp;
				if(party[i].mp > 0 && barLength == 0) barLength = 1;
				drawBar(265,203+180*i,275,14,barLength,mpBlue,g);
				g.setFont(arialBold12);
				width = font.stringWidth(party[i].mp + " / " + party[i].maxMp);
				g.setColor(Color.BLACK);
				g.drawString(party[i].mp + " / " + party[i].maxMp,400-(width/2),200+180*i);
			}
		}
		
		if(state == MAINMENU || state == MAINMENUSELECTCHAR || state == GAMESAVED)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(605,30,230,430);
			g.setColor(menuLightBlue);
			g.fillRect(615,40,210,410);
			g.setColor(Color.WHITE);
			g.setFont(arialBold18);
			g.drawString("Status",675,80);
			g.drawString("Skills",675,136);
			g.drawString("Equipment",675,192);
			g.drawString("Inventory",675,248);
			g.drawString("Edit Party",675,304);
			g.drawString("World Map",675,360);
			
			if(!canSave()) g.setColor(lightGray);
			g.drawString("Save",675,416);
			
			if(state == MAINMENU) draw("pointer",625,60+56*curIndex(),g);
			else if(state == GAMESAVED) draw("pointerSelected",625,60+56*6,g);
			else
			{
				draw("pointerSelected",625,60+56*prevIndex(1),g);
				draw("pointer",15,120+180*curIndex(),g);
			}
			
			g.setColor(menuDarkGreen);
			g.fillRect(605,490,230,130);
			g.setColor(menuLightBlue);
			g.fillRect(615,500,210,110);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold18);
			g.drawString(curMap.name,625,528);
			g.setFont(arialBold16);
			g.drawString("Money",625,563);
			drawStringRightAligned(""+moneyFormat(money),800,563,g);
			g.drawString("Play time",625,593);
			drawStringRightAligned(gameTimeString(),800,593,g);
		}
		
		if(state == INVENTORYUSE)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(605,30,230,100);
			g.setColor(menuLightBlue);
			g.fillRect(615,40,210,80);
			
			Item usingItem = inventory.get(prevIndex(1));
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString("Using item:",625,70);
			
			draw(usingItem.getIconString(),617,78,g);
			g.setFont(arialBold20);
			g.drawString(usingItem.name,650,100);
			g.drawString(""+usingItem.qty,800,100);
			
			draw("pointer",15,120+180*curIndex(),g);
		}
	}
	
	public void drawStringRightAligned(String string, int x, int y, Graphics g)
	{
		FontMetrics font = g.getFontMetrics();
		int width = font.stringWidth(string);
		
		g.drawString(string,x-width,y);
	}
	
	public void drawCenteredString(String string, int x, int y, Graphics g)
	{
		FontMetrics font = g.getFontMetrics();
		int width = font.stringWidth(string);

		g.drawString(string,x-(width/2),y);
	}
	
	public void drawBar(int x, int y, int width, int height, int fillWidth, Color color, Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x,y,width,height);
		g.setColor(Color.GRAY);
		g.fillRect(x+2,y+2,width-4,height-4);
		g.setColor(color);
		g.fillRect(x+2,y+2,fillWidth,height-4);
	}
	
	public void drawBorderedIcon(String icon, int x, int y, Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x-1,y-1,22,22);
		g.setColor(lightGray);
		g.fillRect(x,y,20,20);
		draw(icon,x,y,g);
	}
	
	public void drawStatusMenu(Graphics g)
	{
		g.setColor(menuGreen);
		g.fillRect(0,0,850,650);
		
		String displayString;
		
		Unit calculatingCharacter, selectedCharacter;
		
		if(state == EQUIPMENUSEL)
		{
			calculatingCharacter = placeholderCharacter;
			selectedCharacter = party[prevIndex(2)];
		}
		else if(state == EQUIPMENU)
		{
			calculatingCharacter = party[prevIndex(1)];
			selectedCharacter = party[prevIndex(1)];
		}
		else
		{
			calculatingCharacter = party[prevIndex(1)];
			selectedCharacter = party[prevIndex(1)];
		}
		
		/**
		 * Info
		 */
		g.setColor(menuDarkGreen);
		g.fillRect(15,30,480,205);
		g.setColor(menuLightBlue);
		g.fillRect(25,40,460,185);
		
		g.setColor(Color.BLACK);
		g.fillRect(40,57,154,154);
		draw("port" + calculatingCharacter.name,42,59,g);
		if(calculatingCharacter.hp == 0) draw("dead",42,59,g);

		int statusIndex = 0;
		for(int j=0; j<calculatingCharacter.status.length; j++)
		{
			if(calculatingCharacter.status[j] > 0)
			{
				draw("iconStatus" + j,400-25*statusIndex,60,g);
				statusIndex++;
			}
		}
		
		g.setFont(arialBold20);
		g.drawString(calculatingCharacter.name,215,75);
		g.setFont(arialBold16);
		g.drawString(calculatingCharacter.getClassName(),215,100);
		
		g.setFont(arialBold14);
		g.drawString("Lv " + calculatingCharacter.level,215,137);
		drawBar(265,128,195,8,191*calculatingCharacter.exp/calculatingCharacter.getExpNext(),expPurple,g);
		g.setFont(arialBold12);
		FontMetrics font = g.getFontMetrics();
		int width = font.stringWidth(calculatingCharacter.exp + " / " + calculatingCharacter.getExpNext());
		g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.exp + " / " + calculatingCharacter.getExpNext(),360-(width/2),122);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold14);
		g.drawString("HP",215,172);
		int displayHP = calculatingCharacter.hp;
		if(displayHP > calculatingCharacter.maxHp) displayHP = calculatingCharacter.maxHp; 
		int barLength = 191*displayHP/calculatingCharacter.maxHp;
		if(displayHP > 0 && barLength == 0) barLength = 1;
		drawBar(265,160,195,14,barLength,healthColor(calculatingCharacter),g);
		g.setFont(arialBold12);
		font = g.getFontMetrics();
		displayString = displayHP + " / " + calculatingCharacter.maxHp;
		width = font.stringWidth(displayString);
		if(state == EQUIPMENUSEL && calculatingCharacter.maxHp > selectedCharacter.maxHp) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.maxHp < selectedCharacter.maxHp) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(displayString,360-(width/2),157);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold14);
		g.drawString("MP",215,207);
		int displayMP = calculatingCharacter.mp;
		if(displayMP > calculatingCharacter.maxMp) displayMP = calculatingCharacter.maxMp;
		barLength = 191*displayMP/calculatingCharacter.maxMp;
		if(displayMP > 0 && barLength == 0) barLength = 1;
		drawBar(265,195,195,14,191*displayMP/calculatingCharacter.maxMp,mpBlue,g);
		displayString = displayMP + " / " + calculatingCharacter.maxMp;
		g.setFont(arialBold12);
		width = font.stringWidth(displayString);
		if(state == EQUIPMENUSEL && calculatingCharacter.maxMp > selectedCharacter.maxMp) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.maxMp < selectedCharacter.maxMp) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(displayString,360-(width/2),192);
		
		/**
		 * Stats
		 */
		g.setColor(menuDarkGreen);
		g.fillRect(15,245,480,380);
		g.setColor(menuLightBlue);
		g.fillRect(25,265,460,350);
		g.setColor(menuDarkGreen);
		g.fillRect(250,255,10,360);
		g.fillRect(260,430,225,20);
		
		g.setFont(arialBold14);
		g.setColor(Color.WHITE);
		g.drawString("Stats",30,260);
		
		g.setFont(arialBold16);
		g.setColor(Color.BLACK);
		g.drawString("Strength",45,290);
		if(state == EQUIPMENUSEL && calculatingCharacter.str > selectedCharacter.str) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.str < selectedCharacter.str) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.str,230,290,g);
		g.setColor(Color.BLACK);
		g.drawString("Magic",45,320);
		if(state == EQUIPMENUSEL && calculatingCharacter.mag > selectedCharacter.mag) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.mag < selectedCharacter.mag) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.mag,230,320,g);
		g.setColor(Color.BLACK);
		g.drawString("Dexterity",45,350);
		if(state == EQUIPMENUSEL && calculatingCharacter.dex > selectedCharacter.dex) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.dex < selectedCharacter.dex) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.dex,230,350,g);
		g.setColor(Color.BLACK);
		g.drawString("Speed",45,380);
		if(state == EQUIPMENUSEL && calculatingCharacter.spd > selectedCharacter.spd) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.spd < selectedCharacter.spd) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.spd,230,380,g);
		
		g.setColor(Color.BLACK);
		g.drawString("Attack",45,435);
		if(state == EQUIPMENUSEL && calculatingCharacter.atk > selectedCharacter.atk) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.atk < selectedCharacter.atk) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.atk,230,435,g);
		g.setColor(Color.BLACK);
		g.drawString("Defense",45,465);
		if(state == EQUIPMENUSEL && calculatingCharacter.def > selectedCharacter.def) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.def < selectedCharacter.def) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.def,230,465,g);
		g.setColor(Color.BLACK);
		g.drawString("M Defense",45,495);
		if(state == EQUIPMENUSEL && calculatingCharacter.mdef > selectedCharacter.mdef) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.mdef < selectedCharacter.mdef) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.mdef,230,495,g);
		g.setColor(Color.BLACK);
		g.drawString("Hit Rate",45,525);
		if(state == EQUIPMENUSEL && calculatingCharacter.hitRate > selectedCharacter.hitRate) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.hitRate < selectedCharacter.hitRate) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.hitRate,230,525,g);
		g.setColor(Color.BLACK);
		g.drawString("Evasion",45,555);
		if(state == EQUIPMENUSEL && calculatingCharacter.evasion > selectedCharacter.evasion) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.evasion < selectedCharacter.evasion) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(""+calculatingCharacter.evasion,230,555,g);
		g.setColor(Color.BLACK);
		g.drawString("Crit Rate",45,585);
		if(state == EQUIPMENUSEL && calculatingCharacter.critRate > selectedCharacter.critRate) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.critRate < selectedCharacter.critRate) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		drawStringRightAligned(calculatingCharacter.critRate + "%",230,585,g);
		
		/**
		 * Element Resistance
		 */
		g.setFont(arialBold14);
		g.setColor(Color.WHITE);
		g.drawString("Element Resistance",265,260);
		
		drawBorderedIcon("iconElement0",280,285,g);
		drawBorderedIcon("iconElement1",400,285,g);
		drawBorderedIcon("iconElement2",280,335,g);
		drawBorderedIcon("iconElement3",400,335,g);
		drawBorderedIcon("iconElement4",280,385,g);
		drawBorderedIcon("iconElement5",400,385,g);
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[SNACK] > selectedCharacter.elementResistance[SNACK]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[SNACK] < selectedCharacter.elementResistance[SNACK]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[SNACK] + "%",310,300);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[FIRE] > selectedCharacter.elementResistance[FIRE]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[FIRE] < selectedCharacter.elementResistance[FIRE]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[FIRE] + "%",430,300);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[LIGHTNING] > selectedCharacter.elementResistance[LIGHTNING]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[LIGHTNING] < selectedCharacter.elementResistance[LIGHTNING]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[LIGHTNING] + "%",310,350);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[WATER] > selectedCharacter.elementResistance[WATER]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[WATER] < selectedCharacter.elementResistance[WATER]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[WATER] + "%",430,350);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[EARTH] > selectedCharacter.elementResistance[EARTH]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[EARTH] < selectedCharacter.elementResistance[EARTH]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[EARTH] + "%",310,400);
		if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[POISON] > selectedCharacter.elementResistance[POISON]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[POISON] < selectedCharacter.elementResistance[POISON]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.elementResistance[POISON] + "%",430,400);
		
		/**
		 * Status Resistance
		 */
		g.setFont(arialBold14);
		g.setColor(Color.WHITE);
		g.drawString("Status Resistance",265,445);
		
		drawBorderedIcon("iconStatus0",280,465,g);
		drawBorderedIcon("iconStatus1",400,465,g);
		drawBorderedIcon("iconStatus2",280,500,g);
		drawBorderedIcon("iconStatus3",400,500,g);
		drawBorderedIcon("iconStatus7",280,535,g);
		drawBorderedIcon("iconStatus9",400,535,g);
		drawBorderedIcon("iconStatus13",280,570,g);
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.POISON] > selectedCharacter.statusResistance[Unit.POISON]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.POISON] < selectedCharacter.statusResistance[Unit.POISON]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.POISON] + "%",310,480);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SILENCE] > selectedCharacter.statusResistance[Unit.SILENCE]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SILENCE] < selectedCharacter.statusResistance[Unit.SILENCE]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.SILENCE] + "%",430,480);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.BLIND] > selectedCharacter.statusResistance[Unit.BLIND]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.BLIND] < selectedCharacter.statusResistance[Unit.BLIND]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.BLIND] + "%",310,515);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SLEEP] > selectedCharacter.statusResistance[Unit.SLEEP]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SLEEP] < selectedCharacter.statusResistance[Unit.SLEEP]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.SLEEP] + "%",430,515);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SLOW] > selectedCharacter.statusResistance[Unit.SLOW]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SLOW] < selectedCharacter.statusResistance[Unit.SLOW]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.SLOW] + "%",310,550);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.BERSERK] > selectedCharacter.statusResistance[Unit.BERSERK]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.BERSERK] < selectedCharacter.statusResistance[Unit.BERSERK]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.BERSERK] + "%",430,550);
		if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SHAME] > selectedCharacter.statusResistance[Unit.SHAME]) g.setColor(statUpGreen);
		else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[Unit.SHAME] < selectedCharacter.statusResistance[Unit.SHAME]) g.setColor(Color.RED); 
		else g.setColor(Color.BLACK);
		g.drawString(calculatingCharacter.statusResistance[Unit.SHAME] + "%",310,585);
		
		/**
		 * Equipment
		 */
		if(state == EQUIPMENU || state == EQUIPMENUSEL)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(505,430,330,195);
			g.setColor(lightGray);
			g.fillRect(515,450,310,165);
			
			g.setColor(Color.WHITE);
			g.setFont(arialBold14);
			g.drawString("Equipment Description",520,445);
			
			Item selectedEquip = new NoItem();
			if(state == EQUIPMENU)
			{
				selectedEquip = selectedCharacter.equip[curIndex()];
			}
			else if(state == EQUIPMENUSEL)
			{
				if(equippableIndexList.get(curIndex()) != -1)
				{
					selectedEquip = inventory.get(equippableIndexList.get(curIndex()));
				}
			}
			
			if(selectedEquip.id != -1)
			{
				g.setColor(Color.BLACK);
				g.setFont(arialBold14);
				g.drawString(selectedEquip.desc,525,475);
				g.drawString(selectedEquip.equipDesc,525,500);
				
				int yIndex = 0;
				int currentSP;
				for(int i=0; i<selectedEquip.activeSkills.size(); i++)
				{
					draw("iconActive",525,520+25*yIndex,g);
					
					if(selectedEquip.activeSkills.get(i).canLearn(selectedCharacter))
					{
						g.setColor(Color.BLACK);
						g.drawString(selectedEquip.activeSkills.get(i).action.name,550,535+25*yIndex);
						
						int activeSkillIndex = selectedCharacter.indexOfActiveSkill(selectedEquip.activeSkills.get(i).action.id);
						if(activeSkillIndex != -1)
						{
							currentSP = selectedCharacter.activeSkills.get(activeSkillIndex).currentSP;
						}
						else
						{
							currentSP = 0;
						}
						
						int cost = selectedEquip.activeSkills.get(i).getCost(selectedCharacter);
						if(currentSP >= cost)
						{
							drawBar(700,522+25*yIndex,110,20,106,Color.BLUE,g);
							g.setFont(arialBold14);
							g.setColor(Color.WHITE);
							font = g.getFontMetrics();
							width = font.stringWidth("Learned");
							g.drawString("Learned",753-(width/2),537+25*yIndex);
						}
						else
						{
							int length = (106 * currentSP) / cost;
							drawBar(700,522+25*yIndex,110,20,length,Color.YELLOW,g);
							g.setFont(arialBold14);
							g.setColor(Color.BLACK);
							font = g.getFontMetrics();
							width = font.stringWidth(currentSP + "/" + cost);
							g.drawString(currentSP + "/" + cost,753-(width/2),537+25*yIndex);
						}	
					}
					else
					{
						g.setColor(Color.GRAY);
						g.drawString(selectedEquip.activeSkills.get(i).action.name,550,535+25*yIndex);
					}
					
					yIndex++;
				}
				
				for(int i=0; i<selectedEquip.passiveSkills.size(); i++)
				{
					draw("iconPassive",525,520+25*yIndex,g);
					
					if(selectedEquip.passiveSkills.get(i).canLearn(selectedCharacter))
					{
						g.setColor(Color.BLACK);
						g.drawString(selectedEquip.passiveSkills.get(i).name,550,535+25*yIndex);
						
						int passiveSkillIndex = selectedCharacter.indexOfPassiveSkill(selectedEquip.passiveSkills.get(i).id);
						if(passiveSkillIndex != -1)
						{
							currentSP = selectedCharacter.passiveSkills.get(passiveSkillIndex).currentSP;
						}
						else
						{
							currentSP = 0;
						}
						
						int cost = selectedEquip.passiveSkills.get(i).getCost(selectedCharacter);
						if(currentSP >= cost)
						{
							drawBar(700,522+25*yIndex,110,20,106,Color.BLUE,g);
							g.setFont(arialBold14);
							g.setColor(Color.WHITE);
							font = g.getFontMetrics();
							width = font.stringWidth("Learned");
							g.drawString("Learned",753-(width/2),537+25*yIndex);
						}
						else
						{
							int length = (106 * currentSP) / cost;
							drawBar(700,522+25*yIndex,110,20,length,Color.YELLOW,g);
							g.setFont(arialBold14);
							g.setColor(Color.BLACK);
							font = g.getFontMetrics();
							width = font.stringWidth(currentSP + "/" + cost);
							g.drawString(currentSP + "/" + cost,753-(width/2),537+25*yIndex);
						}	
					}
					else
					{
						g.setColor(Color.GRAY);
						g.drawString(selectedEquip.passiveSkills.get(i).name,550,535+25*yIndex);
					}
					
					yIndex++;
				}
			}
		}
		
		if(state == STATUSMENU || state == STATUSMENUMOREINFO || state == EQUIPMENU)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(505,245,330,175);
			g.setColor(lightGray);
			g.fillRect(515,265,310,145);
			
			g.setFont(arialBold14);
			g.setColor(Color.WHITE);
			g.drawString("Equipment",520,260);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString("Weapon",525,290);
			g.drawString("Hat",525,317);
			g.drawString("Armor",525,344);
			g.drawString("Shoe",525,371);
			g.drawString("Accessory",525,398);
			for(int i=0; i<selectedCharacter.equip.length; i++)
			{
				Item equip = selectedCharacter.equip[i];
				if(equip.id != Item.NOITEM)
				{
					draw(equip.getIconString(),620,270+27*i,g);
					g.drawString(equip.name,655,290+27*i);
				}
				else
				{
					g.drawString("---",655,290+27*i);
				}
			}
			
			if(state == EQUIPMENU) draw("pointer",490,270+27*curIndex(),g);
		}
		else if(state == EQUIPMENUSEL)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(505,245,330,160);
			g.setColor(lightGray);
			g.fillRect(515,265,310,130);
			
			g.setFont(arialBold14);
			g.setColor(Color.WHITE);
			String equipString = "";
			if(prevIndex(1) == 0) equipString = "Weapons";
			else if(prevIndex(1) == 1) equipString = "Hats";
			else if(prevIndex(1) == 2) equipString = "Armors";
			else if(prevIndex(1) == 3) equipString = "Shoes";
			else if(prevIndex(1) == 4) equipString = "Accessories";
			g.drawString(equipString,520,260);
			
			int top = Math.max(0,curIndex()-cursorAlign);
			int bottom = Math.min(top+4,equippableIndexList.size()-1);
			
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				int index = equippableIndexList.get(i);
				
				if(index == -1) //No Item
				{
					g.setFont(arialBold16);
					g.drawString("---",560,290+27*(i-top));
				}
				else
				{
					g.setFont(arialBold16);
					draw(inventory.get(index).getIconString(),525,270+27*(i-top),g);
					g.drawString(inventory.get(index).name,560,290+27*(i-top));
					g.drawString(""+inventory.get(index).qty,780,290+27*(i-top));
				}
			}
			
			draw("pointer",490,270+27*cursorAlign,g);
		}
		
		if(state == STATUSMENU || state == STATUSMENUMOREINFO)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(505,155,330,80);
			g.setColor(menuLightBlue);
			g.fillRect(515,175,310,50);
			
			g.setColor(Color.WHITE);
			g.setFont(arialBold14);
			g.drawString("More Info",525,170);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold12);
			
			if(state == STATUSMENU)
			{
				g.drawString("Press Space for more info.",525,205);
			}
			else if(state == STATUSMENUMOREINFO)
			{
				String moreInfo = "";
				
				switch(curIndex())
				{
					case 0: moreInfo = "Character's class"; break;
					case 1: moreInfo = "Character's level and exp needed for next level"; break;
					case 2: moreInfo = "Character's HP"; break;
					case 3: moreInfo = "Character's MP"; break;
					case 4: moreInfo = "Increases physical damage"; break;
					case 5: moreInfo = "Increases magic damage"; break;
					case 6: moreInfo = "Increases skill effectiveness and Hit/Crit rates"; break;
					case 7: moreInfo = "Increases evasion and attack speed in battle"; break;
					case 8: moreInfo = "Weapon attack damage"; break;
					case 9: moreInfo = "Reduces physical damage taken"; break;
					case 10: moreInfo = "Reduces magical damage taken"; break;
					case 11: moreInfo = "Chance to hit enemy (affected by enemy evasion)"; break;
					case 12: moreInfo = "Chance to evade attacks (affected by enemy hit rate)"; break;
					case 13: moreInfo = "Chance to critically hit"; break;
					case 14: moreInfo = "Reduces damage from Snack element attacks"; break;
					case 15: moreInfo = "Reduces damage from Fire element attacks"; break;
					case 16: moreInfo = "Reduces damage from Lightning element attacks"; break;
					case 17: moreInfo = "Reduces damage from Water element attacks"; break;
					case 18: moreInfo = "Reduces damage from Earth element attacks"; break;
					case 19: moreInfo = "Reduces damage from Poison element attacks"; break;
					case 20: moreInfo = "Poison status - take damage each turn"; break;
					case 21: moreInfo = "Silence status - can't use skills in battle"; break;
					case 22: moreInfo = "Blind status - greatly reduces hit rate"; break;
					case 23: moreInfo = "Sleep status - can't take action until woken up"; break;
					case 24: moreInfo = "Slow status - take fewer actions in battle"; break;
					case 25: moreInfo = "Berserk status - auto-attack with boosted strength"; break;
					case 26: moreInfo = "Shame status - prevents positive status effects"; break;
				}
				
				int x = 0, y = 0;
				
				switch(curIndex())
				{
					case 0: x = 180; y = 82; break;
					case 1: x = 180; y = 119; break;
					case 2: x = 180; y = 154; break;
					case 3: x = 180; y = 189; break;
					case 4: x = 10; y = 272; break;
					case 5: x = 10; y = 302; break;
					case 6: x = 10; y = 332; break;
					case 7: x = 10; y = 362; break;
					case 8: x = 10; y = 417; break;
					case 9: x = 10; y = 447; break;
					case 10: x = 10; y = 477; break;
					case 11: x = 10; y = 507; break;
					case 12: x = 10; y = 537; break;
					case 13: x = 10; y = 567; break;
					case 14: x = 234; y = 283; break;
					case 15: x = 354; y = 283; break;
					case 16: x = 234; y = 333; break;
					case 17: x = 354; y = 333; break;
					case 18: x = 234; y = 383; break;
					case 19: x = 354; y = 383; break;
					case 20: x = 234; y = 463; break;
					case 21: x = 354; y = 463; break;
					case 22: x = 234; y = 498; break;
					case 23: x = 354; y = 498; break;
					case 24: x = 234; y = 533; break;
					case 25: x = 354; y = 533; break;
					case 26: x = 234; y = 568; break;
				}
				
				g.drawString(moreInfo,525,205);
				
				draw("pointer",x,y,g);
			}
		}
	}
	
	public void drawSkillMenu(int state, Graphics g)
	{
		g.setColor(menuGreen);
		g.fillRect(0,0,850,650);
		
		/**
		 * Info
		 */
		g.setColor(menuDarkGreen);
		g.fillRect(15,30,480,205);
		g.setColor(menuLightBlue);
		g.fillRect(25,40,460,185);
		g.setColor(menuDarkGreen);
		
		int partyIndex;
		if(state == SKILLMENUSEL)
		{
			partyIndex = prevIndex(1);
		}
		else
		{
			partyIndex = prevIndex(2);
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(40,57,154,154);
		draw("port" + party[partyIndex].name,42,59,g);
		if(party[partyIndex].hp == 0) draw("dead",42,59,g);

		int statusIndex = 0;
		for(int j=0; j<party[partyIndex].status.length; j++)
		{
			if(party[partyIndex].status[j] > 0)
			{
				draw("iconStatus" + j,400-25*statusIndex,60,g);
				statusIndex++;
			}
		}
		
		g.setFont(arialBold20);
		g.drawString(party[partyIndex].name,215,75);
		g.setFont(arialBold16);
		g.drawString(party[partyIndex].getClassName(),215,100);
		
		g.setFont(arialBold14);
		g.drawString("Lv " + party[partyIndex].level,215,137);
		drawBar(265,125,195,14,191*party[partyIndex].exp/party[partyIndex].getExpNext(),expPurple,g);
		g.setFont(arialBold12);
		FontMetrics font = g.getFontMetrics();
		int width = font.stringWidth(party[partyIndex].exp + " / " + party[partyIndex].getExpNext());
		g.setColor(Color.BLACK);
		g.drawString(party[partyIndex].exp + " / " + party[partyIndex].getExpNext(),360-(width/2),122);		
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold14);
		g.drawString("HP",215,172);
		int displayHP = party[partyIndex].hp;
		if(displayHP > party[partyIndex].maxHp) displayHP = party[partyIndex].maxHp; 
		int barLength = 191*displayHP/party[partyIndex].maxHp;
		if(displayHP > 0 && barLength == 0) barLength = 1;
		drawBar(265,160,195,14,barLength,healthColor(party[partyIndex]),g);
		g.setFont(arialBold12);
		g.setColor(Color.BLACK);
		font = g.getFontMetrics();
		String displayString = displayHP + " / " + party[partyIndex].maxHp;
		width = font.stringWidth(displayString);
		g.drawString(displayString,360-(width/2),157);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold14);
		g.drawString("MP",215,207);
		int displayMP = party[partyIndex].mp;
		if(displayMP > party[partyIndex].maxMp) displayMP = party[partyIndex].maxMp;
		barLength = 191*displayMP/party[partyIndex].maxMp;
		if(displayMP > 0 && barLength == 0) barLength = 1;
		drawBar(265,195,195,14,191*displayMP/party[partyIndex].maxMp,mpBlue,g);
		displayString = displayMP + " / " + party[partyIndex].maxMp;
		g.setFont(arialBold12);
		g.setColor(Color.BLACK);
		width = font.stringWidth(displayString);
		g.drawString(displayString,360-(width/2),192);
		
		g.setColor(menuDarkGreen);
		g.fillRect(15,245,820,380);
		g.setColor(menuLightBlue);
		g.fillRect(25,265,800,350);
		g.setColor(menuDarkGreen);
		g.fillRect(412,265,10,350);
		
		g.setColor(Color.BLACK);
		g.fillRect(720,247,15,15);
		g.setColor(Color.ORANGE);
		g.fillRect(722,249,11,11);
		
		g.setFont(arialBold14);
		g.setColor(Color.WHITE);
		g.drawString(party[partyIndex].cubes + " / " + party[partyIndex].getTotalCubes(),740,260);
		
		if(state == ACTIVESKILLMENU)
		{
			g.setColor(Color.YELLOW);
			drawCenteredString("Active Skills",220,260,g);
			if(party[partyIndex].passiveSkills.size() > 0) g.setColor(Color.WHITE);
			else g.setColor(lightGray);
			drawCenteredString("Passive Skills",615,260,g);
			
			draw("pointerSelected",140,240,g);
			
			//draw active skills
			int top = Math.max(0, curIndex()-cursorAlign);
			int bottom = Math.min(top+8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setFont(arialBold16);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				drawStringRightAligned(party[partyIndex].getLearnedActiveSkills().get(i).action.mp + " MP",350,305+35*(i-top),g);
			}
			
			draw("pointer",33,285+35*(curIndex()-top),g);
			
			//draw passive skills (only the top 9)
			top = 0;
			bottom = Math.min(8,party[partyIndex].getLearnedPassiveSkills().size()-1);
			for(int i=top; i<=bottom; i++)
			{
				g.setColor(Color.BLACK);
				g.fillRect(465,291+35*(i-top),15,15);
				if(party[partyIndex].getLearnedPassiveSkills().get(i).equipped)
				{
					g.setColor(Color.ORANGE);
				}
				else
				{
					g.setColor(Color.GRAY);
				}
				g.fillRect(467,293+35*(i-top),11,11);
				
				if(party[partyIndex].cubes >= party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost || party[partyIndex].getLearnedPassiveSkills().get(i).equipped) g.setColor(Color.BLACK);
				else g.setColor(Color.GRAY);
				g.drawString(party[partyIndex].getLearnedPassiveSkills().get(i).name,495,305+35*(i-top));
				
				g.setColor(Color.BLACK);
				g.fillRect(740,291+35*(i-top),15,15);
				g.setColor(Color.ORANGE);
				g.fillRect(742,293+35*(i-top),11,11);
				g.setColor(Color.BLACK);
				g.drawString(""+party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost,765,305+35*(i-top));
			}
		}
		else if(state == PASSIVESKILLMENU)
		{
			if(party[prevIndex(2)].activeSkills.size() > 0) g.setColor(Color.WHITE);
			else g.setColor(lightGray);
			drawCenteredString("Active Skills",220,260,g);
			g.setColor(Color.YELLOW);
			drawCenteredString("Passive Skills",615,260,g);
			
			draw("pointerSelected",530,240,g);
			
			//draw passive skills
			int top = Math.max(0, curIndex()-cursorAlign);
			int bottom = Math.min(top+8,party[partyIndex].getLearnedPassiveSkills().size()-1);
			g.setFont(arialBold16);
			for(int i=top; i<=bottom; i++)
			{
				g.setColor(Color.BLACK);
				g.fillRect(465,291+35*(i-top),15,15);
				if(party[partyIndex].getLearnedPassiveSkills().get(i).equipped)
				{
					g.setColor(Color.ORANGE);
				}
				else
				{
					g.setColor(Color.GRAY);
				}
				g.fillRect(467,293+35*(i-top),11,11);
				
				if(party[partyIndex].cubes >= party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost || party[partyIndex].getLearnedPassiveSkills().get(i).equipped) g.setColor(Color.BLACK);
				else g.setColor(Color.GRAY);
				g.drawString(party[partyIndex].getLearnedPassiveSkills().get(i).name,495,305+35*(i-top));
				
				g.setColor(Color.BLACK);
				g.fillRect(740,291+35*(i-top),15,15);
				g.setColor(Color.ORANGE);
				g.fillRect(742,293+35*(i-top),11,11);
				g.setColor(Color.BLACK);
				g.drawString(""+party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost,765,305+35*(i-top));
			}
			
			draw("pointer",430,285+35*(curIndex()-top),g);
			
			//draw active skills (only the top 9)
			top = 0;
			bottom = Math.min(8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				drawStringRightAligned(party[partyIndex].getLearnedActiveSkills().get(i).action.mp + " MP",350,305+35*(i-top),g);
			}
		}
		else if(state == SKILLMENUSEL)
		{
			if(party[partyIndex].getLearnedActiveSkills().size() > 0) g.setColor(Color.WHITE);
			else g.setColor(lightGray);
			drawCenteredString("Active Skills",220,260,g);
			if(party[partyIndex].getLearnedPassiveSkills().size() > 0) g.setColor(Color.WHITE);
			else g.setColor(lightGray);
			drawCenteredString("Passive Skills",615,260,g);
			
			draw("pointer",140+390*curIndex(),240,g);
			
			//draw active skills (only the top 9)
			int top = 0;
			int bottom = Math.min(8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setFont(arialBold16);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				drawStringRightAligned(party[partyIndex].getLearnedActiveSkills().get(i).action.mp + " MP",350,305+35*(i-top),g);
			}
			
			//draw passive skills (only the top 9)
			top = 0;
			bottom = Math.min(8,party[partyIndex].getLearnedPassiveSkills().size()-1);
			for(int i=top; i<=bottom; i++)
			{
				g.setColor(Color.BLACK);
				g.fillRect(465,291+35*(i-top),15,15);
				if(party[partyIndex].getLearnedPassiveSkills().get(i).equipped)
				{
					g.setColor(Color.ORANGE);
				}
				else
				{
					g.setColor(Color.GRAY);
				}
				g.fillRect(467,293+35*(i-top),11,11);
				
				if(party[partyIndex].cubes >= party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost || party[partyIndex].getLearnedPassiveSkills().get(i).equipped) g.setColor(Color.BLACK);
				else g.setColor(Color.GRAY);
				g.drawString(party[partyIndex].getLearnedPassiveSkills().get(i).name,495,305+35*(i-top));
				
				g.setColor(Color.BLACK);
				g.fillRect(740,291+35*(i-top),15,15);
				g.setColor(Color.ORANGE);
				g.fillRect(742,293+35*(i-top),11,11);
				g.setColor(Color.BLACK);
				g.drawString(""+party[partyIndex].getLearnedPassiveSkills().get(i).cubeCost,765,305+35*(i-top));
			}
		}
		
		//active side slider
		if(party[partyIndex].getLearnedActiveSkills().size() > 9)
		{
			int barIndex = 0;
			if(state == ACTIVESKILLMENU)
			{
				barIndex = curIndex() - cursorAlign;
			}
			double dheight = 348.0*(9.0/party[partyIndex].getLearnedActiveSkills().size());
			int height = (int) dheight;
			
			int barStart = 266 + Math.max(0, (348-height)*barIndex)/(party[partyIndex].getLearnedActiveSkills().size()-9);
			
			g.setColor(Color.BLACK);
			g.fillRect(395,265,17,350);
			g.setColor(Color.GRAY);
			g.fillRect(396,266,15,348);
			g.setColor(menuBlue);
			g.fillRect(396,barStart,15,height);
		}
		
		//passive side slider
		if(party[partyIndex].getLearnedPassiveSkills().size() > 9)
		{
			int barIndex = 0;
			if(state == PASSIVESKILLMENU)
			{
				barIndex = curIndex() - cursorAlign;
			}
			double dheight = 348.0*(9.0/party[partyIndex].getLearnedPassiveSkills().size());
			int height = (int) dheight;
			
			int barStart = 266 + Math.max(0, (348-height)*barIndex)/(party[partyIndex].getLearnedPassiveSkills().size()-9);
			
			g.setColor(Color.BLACK);
			g.fillRect(810,265,17,350);
			g.setColor(Color.GRAY);
			g.fillRect(811,266,15,348);
			g.setColor(menuBlue);
			g.fillRect(811,barStart,15,height);
		}
		
		if(state == ACTIVESKILLMENU || state == PASSIVESKILLMENU)
		{
			g.setColor(menuDarkGreen);
			g.fillRect(505,155,330,80);
			g.setColor(menuLightBlue);
			g.fillRect(515,175,310,50);
			
			g.setColor(Color.WHITE);
			g.setFont(arialBold14);
			g.drawString("Skill Description",525,170);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold12);
			if(state == ACTIVESKILLMENU)
			{
				Action skill = party[partyIndex].getLearnedActiveSkills().get(curIndex()).action;
				
				if(skill.element != NOELEMENT)
				{
					draw("iconElement" + skill.element,522,177,g);
					g.drawString(skill.desc,525,215);
				}
				else
				{
					g.drawString(skill.desc,525,204);
				}
			}
			else if(state == PASSIVESKILLMENU)
			{
				g.drawString(party[partyIndex].getLearnedPassiveSkills().get(curIndex()).desc,525,205);
			}
		}
	}
	
	public int getStringWidth(String string, Graphics g)
	{
		FontMetrics font = g.getFontMetrics();
		return font.stringWidth(string);
	}
	
	public void draw(String name, int x, int y, Graphics g)
	{
		try
		{			
			icon = new ImageIcon(getClass().getResource("img/" + name + ".PNG")); //TODO: check for png as well?
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
			g.drawImage(img,x+xOffset,y+yOffset,h,w,this);
		}
		catch(Exception e)
		{
			icon = new ImageIcon(getClass().getResource("img/" + name + ".png"));
			img = icon.getImage();
			g.drawImage(img,x+xOffset,y+yOffset,h,w,this);
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
			if(folder.equals("monster")) //TODO: do this for characters too
			{
				String monsterName = name.split("_")[0];
				String animationID = name.split("_")[1];
				
				if(!animationID.equals(NORMAL))
				{
					draw(monsterName + "_" + NORMAL,folder,x,y,g);
				}
			}
			else
			{
				System.out.println(e.getMessage() + ": " + name);
			}
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
	
	public void drawTile(int tile, int i, int j, Graphics g)
	{
		try
		{
			if(Tile.movingTileList().contains(tile) && tileState == 1)
			{
				g.drawImage(tileAltImage[movingTileList.indexOf(tile)],400+i*50+xOffset,300+j*50+yOffset,this);
			}
			else
			{
				g.drawImage(tileImage[tile],400+i*50+xOffset,300+j*50+yOffset,this);
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to draw a tile " + tile + " at " + i + ", " + j);
			g.drawImage(tileImage[Tile.BLACK],400+i*50+xOffset,300+j*50+yOffset,this);
		}
	}
	
	public void drawThing(int thing, int i, int j, Graphics g)
	{
		try
		{
			if(Thing.movingThingList().contains(thing) && tileState == 1)
			{
				g.drawImage(thingAltImage[movingThingList.indexOf(thing)],400+i*50+xOffset,300+j*50+yOffset,this);
			}
			else
			{
				g.drawImage(thingImage[thing],400+i*50+xOffset,300+j*50+yOffset,this);
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to draw thing " + thing + " at " + i + ", " + j);
		}
	}
	
	public void drawMapCharacter(Graphics g)
	{
		g.drawImage(mapCharacterImage[direction][walkCycle],400,300,this); 
	}
	
	public void drawEncounter(Graphics g)
	{
		if(animationID == 0 && startLoop) animationLoop(29,100);
		
		draw("encounter"+animationID,"animation/encounter",0,0,g);
	}
	
	public Tile curTile()
	{
		return curMap.tile[curX][curY];
	}
	
	public void drawMap(Graphics g)
	{
		for(int i=-9; i<10; i++)
		{
			for(int j=-7; j<8; j++)
			{
				if(curX+i >= 0 && curY+j >= 0 && curX+i < curMap.tile.length && curY+j < curMap.tile[0].length)
				{
					try
					{
						drawTile(curMap.tile[curX+i][curY+j].type,i,j,g);
					}
					catch(Exception e)
					{
						System.out.println("Couldn't draw tile " + (curX+i) + ", " + (curY+j) + " - " + e.getMessage());
						g.drawImage(tileImage[Tile.GRASS],400+i*50+xOffset,300+j*50+yOffset,this);
					}
				}
				else
				{
					drawTile(Tile.BLACK,i,j,g);
				}
			}
		}
		
		boolean drawPriorityThing = false;
		ArrayList<Integer> priorityThing = new ArrayList<Integer>();
		ArrayList<Integer> priorityX = new ArrayList<Integer>();
		ArrayList<Integer> priorityY = new ArrayList<Integer>();
		
		boolean drawCoverPlayer = false;
		ArrayList<Integer> coverPlayerThing = new ArrayList<Integer>();
		ArrayList<Integer> coverPlayerX = new ArrayList<Integer>();
		ArrayList<Integer> coverPlayerY = new ArrayList<Integer>();
		
		for(int i=-17; i<10; i++)
		{
			for(int j=-14; j<8; j++)
			{
				if(curX+i >= 0 && curY+j >= 0 && curX+i < curMap.tile.length && curY+j < curMap.tile[0].length)
				{
					if(curMap.tile[curX+i][curY+j].thing.type != Thing.NOTHING)
					{
						if(curMap.tile[curX+i][curY+j].thing.priority)
						{
							drawPriorityThing = true;
							priorityThing.add(curMap.tile[curX+i][curY+j].thing.type);
							priorityX.add(i);
							priorityY.add(j);
						}
						else if(curX+i > 0)
						{
							if(curMap.tile[curX+i][curY+j].thing.type == Thing.BIGROCK && !curMap.tile[curX+i-1][curY+j].isWater()
									&& curMap.tile[curX+i][curY+j].isWater())
							{
								drawThing(Thing.SHOREWEST,i,j,g);
							}
						}
						
						if(curX+i > 0 && curY+j > 0 && (curMap.tile[curX+i][curY+j].thing.type == Thing.ROCK || curMap.tile[curX+i][curY+j].thing.type == Thing.BIGROCK
								|| curMap.tile[curX+i][curY+j].thing.type == Thing.ICEBERG || curMap.tile[curX+i][curY+j].thing.type == Thing.BIGICEBERG))
						{
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i+1][curY+j].isWater() && curMap.tile[curX+i+1][curY+j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREEAST,i,j,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i-1][curY+j].isWater() && curMap.tile[curX+i-1][curY+j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREWEST,i,j,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i][curY+j+1].isWater() && curMap.tile[curX+i][curY+j+1].type != Tile.ICE))
							{
								drawThing(Thing.SHORESOUTH,i,j,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i][curY+j-1].isWater() && curMap.tile[curX+i][curY+j-1].type != Tile.ICE))
							{
								drawThing(Thing.SHORENORTH,i,j,g);
							}
						}
						
						drawThing(curMap.tile[curX+i][curY+j].thing.type,i,j,g);
					}
					
					//shore correction
					try
					{
						if(curX+i >= 1 && curY+j >= 1 && curX+i < curMap.tile.length-1 && curY+j < curMap.tile[0].length-1)
						{
							if((curMap.tile[curX+i+1][curY+j].thing.type == Thing.SHORENORTH || curMap.tile[curX+i+1][curY+j].thing.type == Thing.SHORENORTHEAST)
									&& (curMap.tile[curX+i][curY+j-1].thing.type == Thing.SHOREEAST || curMap.tile[curX+i][curY+j-1].thing.type == Thing.SHORENORTHEAST))
							{
								drawThing(Thing.SHORENORTHEAST2,i,j,g);
							}
							if((curMap.tile[curX+i-1][curY+j].thing.type == Thing.SHORENORTH || curMap.tile[curX+i-1][curY+j].thing.type == Thing.SHORENORTHWEST)
									&& (curMap.tile[curX+i][curY+j-1].thing.type == Thing.SHOREWEST || curMap.tile[curX+i][curY+j-1].thing.type == Thing.SHORENORTHWEST))
							{
								drawThing(Thing.SHORENORTHWEST2,i,j,g);
							}
							if((curMap.tile[curX+i+1][curY+j].thing.type == Thing.SHORESOUTH || curMap.tile[curX+i+1][curY+j].thing.type == Thing.SHORESOUTHEAST)
									&& (curMap.tile[curX+i][curY+j+1].thing.type == Thing.SHOREEAST || curMap.tile[curX+i][curY+j+1].thing.type == Thing.SHORESOUTHEAST))
							{
								drawThing(Thing.SHORESOUTHEAST2,i,j,g);
							}
							if((curMap.tile[curX+i-1][curY+j].thing.type == Thing.SHORESOUTH || curMap.tile[curX+i-1][curY+j].thing.type == Thing.SHORESOUTHWEST)
									&& (curMap.tile[curX+i][curY+j+1].thing.type == Thing.SHOREWEST || curMap.tile[curX+i][curY+j+1].thing.type == Thing.SHORESOUTHWEST
											|| curMap.tile[curX+i][curY+j+1].thing.type == Thing.BIGROCK))
							{
								drawThing(Thing.SHORESOUTHWEST2,i,j,g);
							}
							
							if((curMap.tile[curX+i+1][curY+j].thing.type == Thing.DOCKNORTH || curMap.tile[curX+i+1][curY+j].thing.type == Thing.DOCKNORTHEAST)
									&& (curMap.tile[curX+i][curY+j-1].thing.type == Thing.DOCKEAST || curMap.tile[curX+i][curY+j-1].thing.type == Thing.DOCKNORTHEAST))
							{
								drawThing(Thing.DOCKNORTHEAST2,i,j,g);
							}
							if((curMap.tile[curX+i-1][curY+j].thing.type == Thing.DOCKNORTH || curMap.tile[curX+i-1][curY+j].thing.type == Thing.DOCKNORTHWEST)
									&& (curMap.tile[curX+i][curY+j-1].thing.type == Thing.DOCKWEST || curMap.tile[curX+i][curY+j-1].thing.type == Thing.DOCKNORTHWEST))
							{
								drawThing(Thing.DOCKNORTHWEST2,i,j,g);
							}
							if((curMap.tile[curX+i+1][curY+j].thing.type == Thing.DOCKSOUTH || curMap.tile[curX+i+1][curY+j].thing.type == Thing.DOCKSOUTHEAST)
									&& (curMap.tile[curX+i][curY+j+1].thing.type == Thing.DOCKEAST || curMap.tile[curX+i][curY+j+1].thing.type == Thing.DOCKSOUTHEAST))
							{
								drawThing(Thing.DOCKSOUTHEAST2,i,j,g);
							}
							if((curMap.tile[curX+i-1][curY+j].thing.type == Thing.DOCKSOUTH || curMap.tile[curX+i-1][curY+j].thing.type == Thing.DOCKSOUTHWEST)
									&& (curMap.tile[curX+i][curY+j+1].thing.type == Thing.DOCKWEST || curMap.tile[curX+i][curY+j+1].thing.type == Thing.DOCKSOUTHWEST))
							{
								drawThing(Thing.DOCKSOUTHWEST2,i,j,g);
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
		
		if(drawPriorityThing)
		{
			for(int i=0; i<priorityThing.size(); i++)
			{
				drawThing(priorityThing.get(i),priorityX.get(i),priorityY.get(i),g);
			}
		}
		
		for(int i=-8; i<9; i++)
		{
			for(int j=-6; j<7; j++)
			{
				if(curX+i >= 0 && curY+j >= 0 && curX+i < curMap.tile.length && curY+j < curMap.tile[0].length)
				{
					Event mapEvent = curMap.event(curX+i,curY+j);
					
					int drawX = i + mapEvent.xOffset;
					int drawY = j + mapEvent.yOffset;
					if(drawX < -8 || drawX >= 9 || drawY < -6 || drawY >= 7)
					{
						continue;
					}
					
					if(mapEvent.type == Event.PORTAL)
					{
						//draw nothing
					}
					else if(mapEvent.type == Event.NPC)
					{
						if(mapEvent.name.equals("Sign")) draw("mapSign","npc",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
						else if(!mapEvent.imageName.equals("")) draw("map" + mapEvent.imageName + mapEvent.dir,"npc",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.SHOP)
					{
						if(!mapEvent.imageName.equals(""))
						{
							draw("map" + mapEvent.imageName + mapEvent.dir,"npc",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
						}
					}
					else if(mapEvent.type == Event.INNKEEPER)
					{
						draw("map" + mapEvent.imageName + mapEvent.dir,"npc",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.CHEST)
					{
						draw("mapChest" + mapEvent.state,400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.ART)
					{
						draw("mapArt",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.SAVEPOINT)
					{
						draw("mapSavePoint",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
				}
			}
		}
		
		drawMapCharacter(g);
		
		if(drawCoverPlayer)
		{
			for(int i=0; i<coverPlayerThing.size(); i++)
			{
				drawThing(coverPlayerThing.get(i),coverPlayerX.get(i),coverPlayerY.get(i),g);
			}
		}
	}
	
	public void addToInventory(Item item)
	{
		addToItemList(item, inventory);
	}
	
	public void addToItemList(Item item, ArrayList<Item> itemList)
	{
		boolean newitem = true;
		int itemIndex=0;
		
		System.out.println("Adding item " + item.name + " (" + item.qty + ")");
		
		if(item.id == Item.NOITEM)
		{
			return; //sanity check
		}
		
		for(int i=0; i<itemList.size(); i++)
		{
			if(itemList.get(i).id == item.id)
			{
				newitem = false;
				itemIndex = i;
			}
		}
		
		if(newitem)
		{
			itemList.add(Item.itemFromID(item.id));
			itemList.get(itemList.size()-1).qty = item.qty;
		}
		else
		{
			itemList.get(itemIndex).qty += item.qty;
			if(itemList.get(itemIndex).qty > 99) itemList.get(itemIndex).qty = 99;
		}
	}
	
	public void removeFromInventory(Item item)
	{
		removeFromInventory(item, item.qty);
	}
	
	public void removeFromInventory(Item item, int qty)
	{
		int itemIndex = -1;
		
		for(int i=0; i<inventory.size(); i++)
		{
			if(inventory.get(i).id == item.id)
			{
				itemIndex = i;
			}
		}
		
		if(itemIndex == -1) return;
		
		inventory.get(itemIndex).qty -= qty;
		if(inventory.get(itemIndex).qty <= 0)
		{
			inventory.remove(itemIndex);
		}
	}
	
	public void loadData(String folder)
	{
		//TODO
		try
		{
			inputParty0 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party0.txt"));
			inputParty1 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party1.txt"));
			inputParty2 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party2.txt"));
			inputParty3 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party3.txt"));
			inputParty4 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party4.txt"));
			inputParty5 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party5.txt"));
			inputParty6 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party6.txt"));
			inputParty7 = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party7.txt"));
			inputPosition = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\position.txt"));
			inputInventory = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\inventory.txt"));
			inputMaps = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\maps.txt"));
		}
		catch(Exception e)
		{
			System.out.println("Error reading files.");
		}
		
		ArrayList savedParty0 = loadFile(inputParty0);
		ArrayList savedParty1 = loadFile(inputParty1);
		ArrayList savedParty2 = loadFile(inputParty2);
		ArrayList savedParty3 = loadFile(inputParty3);
		ArrayList savedParty4 = loadFile(inputParty4);
		ArrayList savedParty5 = loadFile(inputParty5);
		ArrayList savedParty6 = loadFile(inputParty6);
		ArrayList savedParty7 = loadFile(inputParty7);
		ArrayList savedPosition = loadFile(inputPosition);
		ArrayList savedInventory = loadFile(inputInventory);
		ArrayList savedMaps = loadFile(inputMaps);
		
		createInventory(savedInventory);
		createPosition(savedPosition, savedMaps);
		
		party[0] = createCharacter(savedParty0,0);
		party[1] = createCharacter(savedParty1,1);
		party[2] = createCharacter(savedParty2,2);
		party[3] = createCharacter(savedParty3,0);
		party[4] = createCharacter(savedParty4,0);
		party[5] = createCharacter(savedParty5,0);
		party[6] = createCharacter(savedParty6,0);
		party[7] = createCharacter(savedParty6,0);
	}
	
	public boolean loadData()
	{
		String folder = "";
		
		boolean checkOld = false;
		
		try
		{
			inputPosition = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\data\\position.txt"));
			
			ArrayList saved0 = loadFile(inputPosition);
			
			//check if the existing game data was saved (since a new game may have been started but wasn't saved)
			String savedFlag = (String) saved0.get(6); //TODO
			
			if(!savedFlag.equals(""))
			{
				folder = "data";
			}
			else
			{
				checkOld = true;
			}
		}
		catch(Exception e1)
		{
			checkOld = true;
			
			System.out.println("Failed to find saved data, checking backup data");
		}
		
		if(checkOld)
		{
			try
			{
				inputPosition = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\BrianQuest2\\old\\position.txt"));
				
				ArrayList savedPosition = loadFile(inputPosition);
				
				String savedFlag = (String) savedPosition.get(6); //TODO

				if(!savedFlag.equals(""))
				{
					folder = "old";
				}
			}
			catch(Exception e2)
			{
				System.out.println("No backup data exists, so we'll return false and start a new game");
			}
		}
		
		if(!folder.equals(""))
		{
			loadData(folder);
			
			return true;
		}
		else return false;
	}
	
	public Unit createCharacter(ArrayList a, int index)
	{
		Unit temp;
		
		/**
		 * Basics
		 */
		int characterID = Integer.parseInt((String) a.get(0));
		if(characterID == -1) return new None();
		
		int level = Integer.parseInt((String) a.get(1));
		int exp = Integer.parseInt((String) a.get(2));
		
		temp = Unit.characterFromID(characterID,level,index);
		
		temp.exp = exp;
		temp.hp = Integer.parseInt((String) a.get(3));
		temp.mp = Integer.parseInt((String) a.get(4));
		
		String statusString = (String) a.get(5);
		String[] statusPieces = statusString.split(";");
		for(int i=0; i<temp.status.length; i++)
		{
			try
			{
				temp.status[i] = Integer.parseInt(statusPieces[i]);
			}
			catch(Exception e)
			{
				temp.status[i] = 0;
			}
		}
		
		/**
		 * Equipment
		 */
		String equipString = (String) a.get(6);
		String[] equipPieces = equipString.split(";");
		for(int i=0; i<4; i++) //there should always be 4 pieces so don't even bother checking
		{
			temp.equip[i] = Item.itemFromID(Integer.parseInt(equipPieces[i]));
		}
		
		/**
		 * Active Skills
		 */
		int ap;
		boolean learned;
		boolean equipped;
		try
		{
			String activeSkillString = (String) a.get(7);
			String[] activeSkillPieces = activeSkillString.split(";"); //get a piece for each skill
			int activeSkillID;
			if(!activeSkillPieces[0].equals("-1")) //default for empty character
			{
				for(int i=0; i<activeSkillPieces.length; i++)
				{
					String[] morePieces = activeSkillPieces[i].split("\\|"); //split into ID|AP|learned
					activeSkillID = Integer.parseInt(morePieces[0]);
					ap = Integer.parseInt(morePieces[1]);
					learned = morePieces[2].equals("1");
					
					temp.activeSkills.add(ActiveSkill.activeSkillFromID(activeSkillID, ap, learned));
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
		temp.cubes = temp.getTotalCubes();
		
		/**
		 * Passive Skills
		 */
		try
		{
			String passiveSkillString = (String) a.get(8);
			String[] passiveSkillPieces = passiveSkillString.split(";"); //get a piece for each skill
			int passiveSkillID;
			if(!passiveSkillPieces[0].equals("-1")) //default for empty character
			{
				for(int i=0; i<passiveSkillPieces.length; i++)
				{
					String[] morePieces = passiveSkillPieces[i].split("\\|"); //split into ID|AP|learned|equipped
					
					passiveSkillID = Integer.parseInt(morePieces[0]);
					ap = Integer.parseInt(morePieces[1]);
					learned = morePieces[2].equals("1");
					equipped = morePieces[3].equals("1");
					
					PassiveSkill skill = PassiveSkill.passiveSkillFromID(passiveSkillID, ap, learned, equipped);
					temp.passiveSkills.add(skill);
					
					if(equipped)
					{
						temp.cubes -= skill.cubeCost;
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		/**
		 * Cube Grid
		 */
		/*temp.lastActivatedNode = Integer.parseInt((String) a.get(8));
		temp.cubes = Integer.parseInt((String) a.get(9));
		
		String cubeString = (String) a.get(10);
		String[] cubePieces = cubeString.split(";");
		int numCubes = cubePieces.length;
		
		if(!cubePieces[0].equals("-1"))
		{
			boolean activated[] = new boolean[numCubes];
			for(int i=0; i<numCubes; i++)
			{
				activated[i] = (cubePieces[i].equals("1"));
			}
			
			if(characterID == Character.BRIAN)
			{
				temp.cubeGrid = new BrianGrid(activated);
			}
			else
			{
				temp.cubeGrid = null;
			}
		}*/
		
		temp.recalculateStats();
		
		if(temp.hp > temp.maxHp) temp.hp = temp.maxHp;
		
		return temp;
	}
	
	public void createPosition(ArrayList position, ArrayList maps)
	{
		int saveFlag = Integer.parseInt((String)position.get(0));
		
		//Create maps		
		map = new Map[Map.numMaps];
		
		int[] temp = Map.mapStates;

		int n = 0;
		for(int i=0; i<map.length; i++)
		{
			int[] aMap = new int[temp[i]];
			for(int j=0; j<temp[i]; j++)
			{
				try
				{
					aMap[j] = Integer.parseInt((String) maps.get(j+n));
				}
				catch(Exception e)
				{
					aMap[j] = 0;
				}
			}
			map[i] = new Map(aMap);
			n += temp[i];
		}
		
		int curMapID = Integer.parseInt((String) position.get(1));
		
		//Create position and respawn position
		curMap = Map.makeMap(curMapID,map[curMapID].states);
		curX = Integer.parseInt((String) position.get(2));
		curY = Integer.parseInt((String) position.get(3));
		respawnMap = Integer.parseInt((String) position.get(4));
		respawnX = Integer.parseInt((String) position.get(5));
		respawnY = Integer.parseInt((String) position.get(6));
		money = Integer.parseInt((String) position.get(7));
		gameTime = Integer.parseInt((String) position.get(8));
		
		curEvent = curMap.tile[curX][curY].event;
		
		direction = SOUTH;
	}
	
	public void createInventory(ArrayList a)
	{
		//Create inventory
		inventory = new ArrayList<Item>();
		
		String itemString;
		int itemID, qty;
		for(int i=0; i<a.size(); i++)
		{
			itemString = (String)a.get(i);
			itemID = Integer.parseInt(itemString.split("-")[0]);
			inventory.add(Item.itemFromID(itemID));
			
			try
			{
				qty = Integer.parseInt(itemString.split("-")[1]);
				
				inventory.get(i).qty = qty;
			}
			catch(Exception e)
			{
				inventory.get(i).qty = 1;
			}
		}
	}
	
	public void newGame()
	{
		try
		{
			File file = new File(System.getProperty("user.home") + "\\BrianQuest2\\data\\party0.txt");
			file.getParentFile().mkdirs();
			
			outputParty0 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party0.txt"));
			outputParty1 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party1.txt"));
			outputParty2 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party2.txt"));
			outputParty3 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party3.txt"));
			outputParty4 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party4.txt"));
			outputParty5 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party5.txt"));
			outputParty6 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party6.txt"));
			outputParty7 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party7.txt"));
			outputInventory = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\inventory.txt"));
			outputPosition = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\position.txt"));
			outputMaps = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\maps.txt"));
		}
		catch(IOException e)
		{
			System.out.println("new game error: " + e.getMessage());
			System.exit(0);
		}

		writeCharacter(new Brian(1,0),outputParty0);
		writeCharacter(new None(),outputParty1);
		writeCharacter(new None(),outputParty2);
		writeCharacter(new None(),outputParty3);
		writeCharacter(new None(),outputParty4);
		writeCharacter(new None(),outputParty5);
		writeCharacter(new None(),outputParty6);
		writeCharacter(new None(),outputParty7);
		
		inventory = new ArrayList<Item>();
		writeInventory(outputInventory);
		
		writePositionNew(outputPosition);
		
		writeMapsNew(outputMaps);

		setState(MAP); //TODO: intro
		playMapSong(false);
	}
		
	public void writeInventory(PrintWriter print)
	{
		/*for(int i=0; i<inventory.size(); i++)
		{
			print.println(inventory.get(i).id);
		}
		for(int i=0; i<inventory.size(); i++)
		{
			print.println(inventory.get(i).qty);
		}*/
		
		for(int i=0; i<inventory.size(); i++)
		{
			print.println(inventory.get(i).id + "-" + inventory.get(i).qty);
		}
		
		print.close();
	}
	
	public void writePosition(PrintWriter print)
	{
		print.println("1");
		print.println(curMap.id);
		print.println(curX);
		print.println(curY);
		print.println(respawnMap);
		print.println(respawnX);
		print.println(respawnY);
		print.println(money);
		print.println(gameTime);
		
		print.close();
	}
	
	public void writePositionNew(PrintWriter print)
	{
		print.println("0"); 	//save flag
		print.println("0"); 	//current map ID
		print.println("20");	//current X
		print.println("20");	//current Y
		print.println("0");		//respawn map ID
		print.println("20");	//respawn X
		print.println("20");	//respawn Y
		print.println("50");	//money
		print.println("0");		//game time
		
		print.close();
	}
	
	public void writeMaps(PrintWriter print)
	{
		try
		{
			curMap.recalculateStates();
			map[curMap.id].states = curMap.states;
			
			for(int i=0; i<map.length; i++)
			{
				for(int j=0; j<map[i].states.length; j++)
				{
					outputMaps.println(map[i].states[j]);
				}
			}
			
		}
		catch(Exception e)
		{
			for(int i=0; i<Map.numMaps; i++)
			{
				for(int j=0; j<Map.mapStates[i]; j++) outputMaps.println("0");
			}
		}
		
		print.close();
	}
	
	public void writeMapsNew(PrintWriter print)
	{
		for(int i=0; i<Map.numMaps; i++)
		{
			for(int j=0; j<Map.mapStates[i]; j++) outputMaps.println("0");
		}

		print.close();
	}
	
	public void writeCharacter(Unit character, PrintWriter print)
	{
		if(character.id != Character.NONE)
		{
			print.println(character.id);
			print.println(character.level);
			print.println(character.exp);
			print.println(character.hp);
			print.println(character.mp);
			
			for(int i=0; i<character.status.length; i++)
			{
				print.print(character.status[i]);
				if(i < character.status.length-1) print.print(";");
			}
			print.println();
			
			/**
			 * Equip
			 */
			for(int i=0; i<character.equip.length; i++)
			{
				print.print(character.equip[i].id);
				if(i < character.equip.length-1) print.print(";");
			}
			print.println();
			
			/**
			 * Active Skills
			 */
			if(character.activeSkills.size() < 1)
			{
				print.println("-1");
			}
			else
			{
				for(int i=0; i<character.activeSkills.size(); i++) //ID|AP|learned;ID|AP|learned ...
				{
					print.print(character.activeSkills.get(i).id + "|");
					print.print(character.activeSkills.get(i).currentSP + "|");
					if(character.activeSkills.get(i).learned) print.print("1");
					else print.print("0");
					if(i < character.activeSkills.size()-1) print.print(";");
				}
				
				print.println();
			}
			
			/**
			 * Passive Skills
			 */
			if(character.passiveSkills.size() < 1)
			{
				print.println("-1");
			}
			else
			{
				for(int i=0; i<character.passiveSkills.size(); i++) //ID|AP|learned|equipped;ID|AP|learned|equipped ...
				{
					print.print(character.passiveSkills.get(i).id + "|");
					print.print(character.passiveSkills.get(i).currentSP + "|");
					if(character.passiveSkills.get(i).learned) print.print("1|");
					else print.print("0|");
					if(character.passiveSkills.get(i).equipped) print.print("1");
					else print.print("0");
					if(i < character.passiveSkills.size()-1) print.print(";");
				}
				
				print.println();
			}
			
		}
		else
		{
			print.println("-1");
		}
		
		print.close();
	}
	
	public class CompareID implements Comparator<Item>
	{
		public int compare(Item a, Item b)
		{
			if(a.id == b.id) return 0;
			else if(a.id < b.id) return -1;
			else return 1;
		}
	}
	
	public class CompareName implements Comparator<Item>
	{
		public int compare(Item a, Item b)
		{
			if(a.name.equals(b.name)) return 0;
			else if(a.name.compareTo(b.name) < 0) return -1;
			else return 1;
		}
	}
		
	class Task extends TimerTask
	{
        public void run()
        {
        	timer.cancel();
        	
        	onChangingState(newState);
        	
        	setState(newState);
        	
           	repaint();
        }
    }
	
	public void onChangingState(int state)
	{
		//do stuff that should happen once when changing to another state via TimerTask
		if(state == BATTLE)
		{
			battleAction = null;
			
			battleRecalculateState(false);
			
			repaint();
		}
		else if(state == BATTLERECALCULATE)
		{
			//battleAction = null;
			
			battleRecalculateState(true);
			
			repaint();
		}
	}
	
	public void battleRecalculateState(boolean applyBattleAction)
	{
		if(applyBattleAction && battleAction.targets != null)
		{
			for(int i=0; i<battleAction.targets.size(); i++)
			{
				Unit target = getUnitFromTargetIndex(battleAction.targets.get(i));
				ArrayList<Integer> valuesToApply = new ArrayList<Integer>();
				valuesToApply.add(i);
				if(battleAction.values.size() == battleAction.targets.size()*2) valuesToApply.add(i+battleAction.values.size()/2);
				
				for(int j=0; j<valuesToApply.size(); j++)
				{
					int index = valuesToApply.get(j);
					
					if(battleAction.values.size() > 0)
					{
						int value = battleAction.values.get(index);
						
						if(battleAction.action == Action.MURDER || battleAction.action == Action.MASSMURDER)
						{
							if(value == 1)
							{
								target.hp = 0;
							}
						}
						else
						{
							if(battleAction.mp.get(index))
							{
								target.healMP(value);
							}
							else if(battleAction.values.get(index) >= 0)
							{
								target.doDamage(value);
								
								if(target.status[Unit.SLEEP] > 0 && Action.wakesUp(battleAction.action))
								{
									target.status[Unit.SLEEP] = 0;
								}
							}
							else
							{
								target.healHP(value*-1);
							}
						}
					}
				}
				
				if(battleAction.action == Action.BARF || battleAction.action == Action.VOMITERUPTION)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.POISON);
					}
				}
				else if(battleAction.action == Action.SHRIEK)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.SILENCE);
					}
				}
				else if(battleAction.action == Action.GOTTAGOFAST || battleAction.action == Action.GOTTAGOFASTER)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.HASTE,7);
					}
				}
				else if(battleAction.action == Action.ANNOY)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.BERSERK,7);
					}
				}
				else if(battleAction.action == Action.COWER)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.DEFEND);
					}
				}
				else if(battleAction.action == Action.BLESSINGOFARINO)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.ATKUP,7);
					}
				}
				else if(battleAction.action == Action.SILLYDANCE)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.EVADE,7);
					}
				}
				else if(battleAction.action == Action.AMP)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.AMP,7);
					}
				}
				else if(battleAction.action == Action.BAJABLAST)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.SLOW,5);
					}
				}
				else if(battleAction.action == Action.BLESSINGOFMIKU)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.REGEN,10);
					}
				}
				else if(battleAction.action == Action.BLUESHIELD || battleAction.action == Action.BLUEBARRIER)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.SHELL,7);
					}
				}
				else if(battleAction.action == Action.KAGESHADOWS)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.BLIND,7);
					}
				}
				else if(battleAction.action == Action.DEFENDHONOR || battleAction.action == Action.HONORFORALL)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.PROTECT,7);
					}
				}
				else if(battleAction.action == Action.INFLICTSHAME)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.SHAME,5);
					}
				}
				else if(battleAction.action == Action.CATNAP)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.SLEEP,5);
					}
				}
				else if(battleAction.action == Action.CATSCRATCH)
				{
					if(battleAction.inflictStatus.get(i))
					{
						target.inflictStatus(Unit.BLIND);
					}
				}
				
				else if(battleAction.action == Action.STEAL)
				{
					if(battleAction.item != null)
					{
						int indexToRemove = target.stealItems.indexOf(battleAction.item);
						target.stealItems.remove(indexToRemove);
						target.stealItemRates.remove(indexToRemove);
						
						addToInventory(battleAction.item);
					}
				}
			}
		}
		
		battleAction = null;
		
		newState = getNextState();
		if(state == BATTLERECALCULATE)
		{
			setState(newState);
			repaint();
		}

		if(currentUnit.status[Unit.POISON] < 0)
		{
			setState(BATTLEEFFECT);
			repaint();
			
			ArrayList<Integer> targets = new ArrayList<Integer>();
			targets.add(currentUnit.getBattleIndex());
			
			doBattleCalculations(currentUnit, Action.POISON, targets);
		}
		else if(currentUnit.status[Unit.REGEN] < 0)
		{
			setState(BATTLEEFFECT);
			repaint();
			
			ArrayList<Integer> targets = new ArrayList<Integer>();
			targets.add(currentUnit.getBattleIndex());
			
			doBattleCalculations(currentUnit, Action.REGEN, targets);
		}
		
		else if(newState == BATTLEEFFECT) //no choice for next move (monster attack, party member is berserk, etc.)
		{
			int action;
			ArrayList<Integer> targets =  new ArrayList<Integer>();
			
			if(currentUnit.unitType == Unit.MONSTER)
			{
				action = getMonsterAction(currentUnit);
				targets = getMonsterTargets(currentUnit, action);
			}
			else //Berserk
			{
				action = Action.ATTACK;
				targets.add(getRandomMonsterIndex());
			}
			
			doBattleCalculations(currentUnit, action, targets);

			//animationLoop(Action.actionFromID(action).numFrames,50);
		}
		else if(newState == BATTLECHOICE)
		{
			index.clear();
			addIndex(0);
		}
	}
	
	public int getMonsterAction(Unit monster)
	{
		int id = monster.id;
		int action = Action.ATTACK; //default
		
		if(monster.status[Unit.SILENCE] == 1 || monster.status[Unit.BERSERK] > 0) return Action.ATTACK;
		
		if(id == Monster.SNAKE)
		{
			int r = rand.nextInt(3);
			if(r == 0) action = Action.ATTACK;
			else if(r == 1) action = Action.LIGHTNINGBOLT;
			else action = Action.MTNDEWWAVE;
		}
		
		return action;
	}
	
	public ArrayList<Integer> getMonsterTargets(Unit monster, int action)
	{
		ArrayList<Integer> targets = new ArrayList<Integer>();
		
		ArrayList<Integer> existingPlayerTargets = new ArrayList<Integer>();
		for(int i=0; i<3; i++)
		{
			if(party[i].existsAndAlive()) existingPlayerTargets.add(i);
		}
		
		ArrayList<Integer> existingMonsterTargets = new ArrayList<Integer>();
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) existingMonsterTargets.add(i+3);
		}
		
		int targetType = Action.actionFromID(action).targetType;
		
		if(targetType == Action.ONEENEMY)
		{
			int target = existingPlayerTargets.get(rand.nextInt(existingPlayerTargets.size()));
			
			targets.add(target);
		}
		else if(targetType == Action.ALLENEMIES)
		{
			targets = existingPlayerTargets;
		}
		else if(targetType == Action.ONEALLY) //TODO: add some actual logic here?
		{
			int target = existingMonsterTargets.get(rand.nextInt(existingMonsterTargets.size()));
			
			targets.add(target);
		}
		else if(targetType == Action.SELF)
		{
			targets.add(monster.getBattleIndex());
		}
		else if(targetType == Action.ALLALLIES)
		{
			targets = existingMonsterTargets;
		}
		else if(targetType == Action.ONEUNIT)
		{
			//TODO: any use case?
		}
		
		return targets;
	}
	
	public int getRandomMonsterIndex()
	{
		ArrayList<Integer> monsterList = new ArrayList<Integer>();
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) monsterList.add(monsters.get(i).getBattleIndex());
		}
		
		return monsterList.get(rand.nextInt(monsterList.size()));
	}
	
	public int getNextState()
	{
		/**
		 * Check for losing the battle
		 */
		boolean lostBattle = true;
		for(int i=0; i<3; i++)
		{
			if(party[i].hp > 0) lostBattle = false;
		}
		
		if(lostBattle)
		{
			return BATTLELOST;
		}
		
		/**
		 * Check for winning the battle
		 */
		boolean wonBattle = true;
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) wonBattle = false;
		}
		
		if(wonBattle)
		{
			return BATTLEWON;
		}
		
		/**
		 * Get whose turn it is next
		 */
		currentUnit = null;
		
		while(currentUnit == null)
		{
			for(int i=0; i<3; i++)
			{
				if(party[i].existsAndAlive())
				{
					if(party[i].ct == 0)
					{
						//currentUnit = party[i];
						if(!party[i].checkFlags())
						{
							party[i].resetCT();
							
							party[i].clearFlags();
						}
						
						if(party[i].status[Unit.SLEEP] > 0)
						{
							party[i].resetCT();
							party[i].status[Unit.SLEEP]--;
						}
						else
						{
							boolean berserk = (party[i].status[Unit.BERSERK] > 0); 
							
							party[i].decrementStatuses();

							currentUnit = party[i];
							
							if(berserk) return BATTLEEFFECT; //had berserk status, so don't choose move
							else return BATTLECHOICE;
						}
						
					}
				}
			}
			
			for(int i=0; i<monsters.size(); i++)
			{
				if(monsters.get(i).hp > 0)
				{
					if(monsters.get(i).ct == 0)
					{
						//currentUnit = monsters.get(i);
						if(!monsters.get(i).checkFlags())
						{
							monsters.get(i).resetCT();
							
							monsters.get(i).clearFlags();
						}
						
						if(monsters.get(i).status[Unit.SLEEP] > 0)
						{
							monsters.get(i).resetCT();
							monsters.get(i).status[Unit.SLEEP]--;
						}
						else
						{
							currentUnit = monsters.get(i);
							return BATTLEEFFECT;
						}
					}
				}
			}
			
			for(int i=0; i<3; i++)
			{
				if(party[i].existsAndAlive())
				{
					party[i].ct--;
				}
			}
			
			for(int i=0; i<monsters.size(); i++)
			{
				if(monsters.get(i).hp > 0)
				{
					monsters.get(i).ct--;
				}
			}
		}
		
		return BATTLE; //should never happen
	}
	
	public void doBattleCalculations(Unit user, int action, ArrayList<Integer> targets)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		ArrayList<Boolean> mp = new ArrayList<Boolean>();
		ArrayList<Boolean> critArray = new ArrayList<Boolean>();
		ArrayList<Boolean> critTemp = new ArrayList<Boolean>();
		ArrayList<Boolean> missArray = new ArrayList<Boolean>();
		ArrayList<Boolean> missTemp = new ArrayList<Boolean>();
		ArrayList<Boolean> inflictArray = new ArrayList<Boolean>();
		Item usedItem = null;
		
		//for Mysterious Melody
		int element = NOELEMENT; //for Mysterious Melody
		
		//for Steal
		Item stealItem = null; //for Steal
		int stealOutcome = 0;
		
		int[] animation = new int[7];
		for(int i=0; i<7; i++) animation[i] = Action.NOACTION;
		
		/**
		 * Attack
		 */
		if(action == Action.ATTACK)
		{
			int dmgToAdd = calculateDamage(user, action, getUnitFromTargetIndex(targets.get(0)), critTemp, missTemp);
			values.add(dmgToAdd);
			mp.add(false);
			critArray.add(critTemp.get(0));
			missArray.add(missTemp.get(0));
			
			animation[user.getBattleIndex()] = ATTACK;
			for(int i=0; i<targets.size(); i++)
			{
				if(values.get(i) > 0) animation[targets.get(i)] = DAMAGED;
			}
		}
		
		/**
		 * Damage Skills
		 */
		else if(action == Action.BRIANPUNCH || action == Action.CHEEZITBLAST || action == Action.COOLRANCHLASER || action == Action.BRIANSMASH || action == Action.FLAVOREXPLOSION
				|| action == Action.BARF || action == Action.FLAIL || action == Action.SHRIEK || action == Action.KAMIKAZE || action == Action.VOMITERUPTION || action == Action.SUMMONTRAINS
				|| action == Action.BAJABLAST || action == Action.MYSTERIOUSMELODY || action == Action.SHURIKEN || action == Action.NINJUTSUSLICE || action == Action.SAMURAISLASH
				|| action == Action.BUSHIDOBLADE || action == Action.MURAMASAMARA || action == Action.FIRE || action == Action.BIGFIRE || action == Action.LIGHTNINGBOLT
				|| action == Action.LIGHTNINGSTORM || action == Action.DEVOUR || action == Action.EARTHSPIKE || action == Action.EARTHQUAKE || action == Action.CATSCRATCH)
		{
			for(int i=0; i<targets.size(); i++)
			{
				int dmgToAdd = 0;
				if(action == Action.MYSTERIOUSMELODY)
				{
					element = rand.nextInt(NUMELEMENTS);
					dmgToAdd = calculateDamage(user, action, getUnitFromTargetIndex(targets.get(i)), critTemp, missTemp, element);
				}
				else
				{
					dmgToAdd = calculateDamage(user, action, getUnitFromTargetIndex(targets.get(i)), critTemp, missTemp);
				}
				
				values.add(dmgToAdd);
				mp.add(false);
				critArray.add(critTemp.get(0));
				missArray.add(missTemp.get(0));
				
				if(action == Action.BARF || action == Action.VOMITERUPTION)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,Unit.POISON,40))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.SHRIEK)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,Unit.SILENCE,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.BAJABLAST)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,Unit.SLOW,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.CATSCRATCH)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,Unit.BLIND,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else inflictArray.add(false);
			}
			
			if(action == Action.CHEEZITBLAST || action == Action.BARF || action == Action.SUMMONTRAINS || action == Action.PURR || action == Action.CATNAP) //TODO: enhance Vomit Eruption
			{
				animation[user.getBattleIndex()] = action;
			}
			else if((action == Action.FLAIL && values.get(0) > 0) || action == Action.KAMIKAZE)
			{
				animation[user.getBattleIndex()] = DAMAGED;
			}
			else
			{
				animation[user.getBattleIndex()] = ATTACK;
			}
			
			for(int i=0; i<targets.size(); i++)
			{
				if(values.get(i) > 0) animation[targets.get(i)] = DAMAGED;
			}
			
			if(action == Action.FLAIL)
			{
				targets.add(currentUnit.getBattleIndex());
				values.add(values.get(0)/2);
				mp.add(false);
				critArray.add(critArray.get(0));
				missArray.add(missArray.get(0));
			}
			else if(action == Action.KAMIKAZE)
			{
				targets.add(currentUnit.getBattleIndex());
				values.add(currentUnit.hp);
				mp.add(false);
				critArray.add(critArray.get(0));
				missArray.add(missArray.get(0));
			}
			else if(action == Action.DEVOUR)
			{
				if(values.get(0) > 0)
				{
					targets.add(currentUnit.getBattleIndex());
					values.add(-1*(values.get(0)/2));
					mp.add(false);
					critArray.add(false);
					missArray.add(false);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		
		/**
		 * Murder
		 */
		else if(action == Action.MURDER)
		{
			Unit target = getUnitFromTargetIndex(targets.get(0));
			
			if(!target.murderable)
			{
				values.add(-1); //-1 = can't murder
			}
			else //TODO: work on this formula
			{
				double dmurderChance = 20 + 5*(currentUnit.level - target.level) + 7*(currentUnit.dex - target.dex);
				if(dmurderChance < 0) dmurderChance = 0;
				dmurderChance *= (3.0 - 2.0*((double)target.hp/target.maxHp));
				
				if(rand.nextInt(100) < dmurderChance)
				{
					values.add(1); //success
					
					animation[targets.get(0)] = DAMAGED;
				}
				else
				{
					values.add(0); //miss
				}
			}
		}
		
		/**
		 * Mass Murder
		 */
		else if(action == Action.MASSMURDER)
		{
			for(int i=0; i<targets.size(); i++)
			{
				Unit target = getUnitFromTargetIndex(targets.get(i));
				
				if(!target.murderable)
				{
					values.add(-1); //-1 = can't murder
				}
				else
				{
					double dmurderChance = 20 + 5*(currentUnit.level - target.level) + 7*(currentUnit.dex - target.dex);
					if(dmurderChance < 0) dmurderChance = 0;
					dmurderChance *= (3.0 - 2.0*((double)target.hp/target.maxHp));
					
					if(rand.nextInt(100) < dmurderChance)
					{
						values.add(1); //success
						
						animation[targets.get(i)] = DAMAGED;
					}
					else
					{
						values.add(0); //miss
					}
				}
			}
		}
		
		/**
		 * Healing skills
		 */
		else if(action == Action.MTNDEWWAVE)
		{
			for(int i=0; i<targets.size(); i++)
			{
				double dhealAmt = 1.5*(5.0 + user.level + 3.0*user.mag + Math.pow(user.mag,1.5));
				dhealAmt = randomize(dhealAmt,user.getDmgLowerBound(),100);
				
				int healAmt = (int) dhealAmt;
				values.add(healAmt * -1);
				
				mp.add(false);
			}
			
			animation[user.getBattleIndex()] = Action.MTNDEWWAVE;
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}		
		else if(action == Action.EATPOPTART)
		{
			double dhealAmt = 1.5*(20.0 + 2.0*user.level + 5.0*user.mag + Math.pow(user.mag,1.5));
			dhealAmt = randomize(dhealAmt,85,100);
			
			int healAmt = (int) dhealAmt;
			values.add(healAmt * -1);
			
			mp.add(false);
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.RADICALRIFF) //TODO: work on this formula
		{
			for(int i=0; i<targets.size(); i++)
			{
				double dhealAmt = 10 + user.mag;
				dhealAmt = randomize(dhealAmt,user.getDmgLowerBound(),100);
				
				int healAmt = (int) dhealAmt;
				values.add(healAmt);
				
				mp.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.CHIPTUNEOFLIFE)
		{
			if(getUnitFromTargetIndex(targets.get(0)).hp == 0)
			{
				double dhealAmt = (getUnitFromTargetIndex(targets.get(0)).maxHp * 0.10) + (user.dex * 2.0) + (user.mag * 3.0);
				dhealAmt = randomize(dhealAmt,85,100);
				
				int healAmt = (int) dhealAmt;
				values.add(healAmt * -1);
			}
			else
			{
				values.add(0);
				missArray.add(true);
			}
			
			mp.add(false);
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.PURR) //TODO: heal formula
		{
			//HP part
			double dhealAmt = (user.maxHp * 0.15) + (user.mag * 2.0);
			dhealAmt = randomize(dhealAmt,85,100);
			
			int healAmt = (int) dhealAmt;
			values.add(healAmt * -1);
			
			mp.add(false);
			
			//MP part
			dhealAmt = (user.maxMp * 0.05) + user.mag/2;
			dhealAmt = randomize(dhealAmt,85,100);
			
			healAmt = (int) dhealAmt;
			values.add(healAmt);
			
			mp.add(true);
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		
		/**
		 * Status skills
		 */
		else if(action == Action.GOTTAGOFAST || action == Action.GOTTAGOFASTER)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[Unit.HASTE] == 0)
				{
					inflictArray.add(true);
					missArray.add(false);
				}
				else
				{
					inflictArray.add(false);
					missArray.add(true);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.ANNOY)
		{
			int rate = 90;
			if(user.unitType == getUnitFromTargetIndex(targets.get(0)).unitType) rate = 200;  
			
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,Unit.BERSERK,rate))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.COWER)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.DEFEND] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.BLESSINGOFARINO)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.ATKUP] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.SILLYDANCE)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.EVADE] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.AMP)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.AMP] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.BLESSINGOFMIKU)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[Unit.REGEN] == 0)
				{
					inflictArray.add(true);
					missArray.add(false);
				}
				else
				{
					inflictArray.add(false);
					missArray.add(true);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.BLUESHIELD || action == Action.BLUEBARRIER)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[Unit.SHELL] == 0)
				{
					inflictArray.add(true);
					missArray.add(false);
				}
				else
				{
					inflictArray.add(false);
					missArray.add(true);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.KAGESHADOWS)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,Unit.BLIND,70))
				{
					inflictArray.add(true);
					missArray.add(false);
				}
				else
				{
					inflictArray.add(false);
					missArray.add(true);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.DEFENDHONOR || action == Action.HONORFORALL)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[Unit.PROTECT] == 0)
				{
					inflictArray.add(true);
					missArray.add(false);
				}
				else
				{
					inflictArray.add(false);
					missArray.add(true);
				}
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.INFLICTSHAME)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.SHAME] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		else if(action == Action.CATNAP)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[Unit.SLEEP] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		
		/**
		 * Steal
		 */
		else if(action == Action.STEAL)
		{
			Unit target = getUnitFromTargetIndex(targets.get(0));
			
			if(target.stealItems.size() > 0)
			{
				for(int i=0; i<target.stealItems.size(); i++)
				{
					if(rand.nextInt(100) < target.stealItemRates.get(i))
					{
						stealItem = target.stealItems.get(i);
						stealOutcome = 1;
					}
					
					if(stealItem != null) break;
				}
				
				if(stealItem == null) stealOutcome = 0;
			}
			else stealOutcome = -1;
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).mp;
		}
		
		/**
		 * Items
		 */
		else if(action == Action.HPITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			values.add(usedItem.amt * -1);
			mp.add(false);
		}
		else if(action == Action.MPITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			values.add(usedItem.amt);
			mp.add(true);
		}
		else if(action == Action.REVIVEITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			double healAmount = getUnitFromTargetIndex(targets.get(0)).maxHp * (usedItem.amt/100.0);
			values.add((int) healAmount * -1);
			mp.add(false);
		}
		
		/**
		 * Poison damage
		 */
		else if(action == Action.POISON)
		{
			int dmgToAdd = calculateDamage(user, action, user, critTemp, missTemp);
			values.add(dmgToAdd);
			mp.add(false);
			
			animation[user.getBattleIndex()] = DAMAGED;
		}
		
		/**
		 * Regen
		 */
		else if(action == Action.REGEN)
		{
			double dvalue = user.maxHp * 0.05;
			dvalue = randomize(dvalue,85,115);
			
			int value = (int) dvalue;
			
			values.add(-1 * value);
			mp.add(false);
		}
		
		battleAction = new BattleAction(user,action,targets,values,mp,animation,critArray,missArray,inflictArray);
		battleAction.element = element;
		
		if(battleAction.action == Action.STEAL)
		{
			battleAction.item = stealItem;
			battleAction.stealOutcome = stealOutcome;
		}
		
		if(usedItem != null) battleAction.item = usedItem;
		
		setState(BATTLEEFFECT);
		repaint();
		
		timer = new Timer();
		timer.schedule(new Task(),Action.actionFromID(action).numFrames*50);
		
		animationLoop(Action.actionFromID(action).numFrames,50);
		newState = BATTLERECALCULATE;
	}
	
	public Unit getUnitFromTargetIndex(int index)
	{
		if(index == 0) return party[0];
		else if(index == 1) return party[1];
		else if(index == 2) return party[2];
		else if(index == 3) return monsters.get(0);
		else if(index == 4) return monsters.get(1);
		else if(index == 5) return monsters.get(2);
		else if(index == 6) return monsters.get(3);
		
		return null;
	}
	
	public static ArrayList loadFile(BufferedReader a)
    {
		ArrayList<String> list = new ArrayList<String>();
		
		try
		{
			String line = a.readLine();
			while(line != null)
			{
				list.add(line);
				line = a.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Error in loadFile: " + e.getMessage());
		}
		
		return list;
    }
	
	public void saveGame(String folder)
	{
		//TODO
		try
		{
			File file = new File(System.getProperty("user.home") + "\\BrianQuest2\\" + folder + "\\party0.txt");
			file.getParentFile().mkdirs();
			
			outputParty0 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party0.txt"));
			outputParty1 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party1.txt"));
			outputParty2 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party2.txt"));
			outputParty3 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party3.txt"));
			outputParty4 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party4.txt"));
			outputParty5 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party5.txt"));
			outputParty6 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party6.txt"));
			outputParty7 = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\party7.txt"));
			outputInventory = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\inventory.txt"));
			outputPosition = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\position.txt"));
			outputMaps = new PrintWriter(new FileWriter(System.getProperty("user.home") + "\\BrianQuest2\\data\\maps.txt"));
		}
		catch(IOException e)
		{
			System.out.println("saveGame() - " + e.getMessage());
		}

		writeCharacter(party[0],outputParty0);
		writeCharacter(party[1],outputParty1);
		writeCharacter(party[2],outputParty2);
		writeCharacter(party[3],outputParty3);
		writeCharacter(party[4],outputParty4);
		writeCharacter(party[5],outputParty5);
		writeCharacter(party[6],outputParty6);
		writeCharacter(party[7],outputParty7);
		
		writeInventory(outputInventory);
		writePosition(outputPosition);
		writeMaps(outputMaps);
	}
	
	public Tile tileFaced()
	{
		if(direction == NORTH)
		{
			return curMap.tile[curX][curY-1];
		}
		else if(direction == EAST)
		{
			return curMap.tile[curX+1][curY];
		}
		else if(direction == SOUTH)
		{
			return curMap.tile[curX][curY+1];
		}
		else
		{
			return curMap.tile[curX-1][curY];
		}
	}
		
	public static boolean haveItem(int id)
	{
		for(int i=0; i<inventory.size(); i++)
		{
			if(inventory.get(i).id == id) return true;
		}
		
		return false;
	}
	
	public static int howManyOfItem(int id)
	{
		if(!haveItem(id)) return 0;
		else
		{
			for(int i=0; i<inventory.size(); i++)
			{
				if(inventory.get(i).id == id) return inventory.get(i).qty;
			}
		}
		
		return 0;
	}
		
	public String gameTimeString()
	{
		int time = gameTime;
		
		int hours = time/3600;
		time = time - hours*3600;
		int minutes = time/60;
		time = time - minutes*60;
		int seconds = time;
		
		String sHours;
		if(hours < 10) sHours = "0" + hours;
		else sHours = "" + hours;
		
		String sMinutes;
		if(minutes < 10) sMinutes = "0" + minutes;
		else sMinutes = "" + minutes;
		
		String sSeconds ;
		if(seconds < 10) sSeconds = "0" + seconds;
		else sSeconds = "" + seconds;
		
		return sHours + ":" + sMinutes + ":" + sSeconds;
	}
		
	public int curIndex()
	{
		try
		{
			return index.get(index.size()-1);
		}
		catch(Exception e)
		{
			System.out.println("trying to return a non-existent curIndex");
			return 0;
		}
	}
	
	public void clearIndex()
	{
		index = new ArrayList<Integer>();
		index.add(0);
	}
	
	public void setIndex(int amt)
	{
		index.set(index.size()-1,amt);
	}
	
	public void incIndex()
	{
		index.set(index.size()-1, index.get(index.size()-1)+1);
	}
	
	public void decIndex()
	{
		index.set(index.size()-1, index.get(index.size()-1)-1);
	}
	
	public int prevIndex(int amt)
	{
		return index.get(index.size()-1-amt);
	}
	
	public void addIndex(int amt)
	{
		index.add(amt);
	}
	
	public void removeIndex()
	{
		index.remove(index.size()-1);
		
		if(index.size() == 0) index.add(0);
	}
		
	public void move(int dir)
	{
		move(dir,false);
	}
	
	public void move(int dir, boolean skipMonsters)
	{
		boolean move = false;
		
		if(dir == NORTH)
		{			
			direction = NORTH;
			
			if(curMap.tile[curX][curY].type == Tile.SNOWWALLNORTH)
			{
				move = false;
			}
			else if(curMap.tile[curX][curY-1].walkable)
			{
				move = true;
				
				curY--;
				yOffset = -24;
			}
		}
		else if(dir == EAST)
		{
			direction = EAST;
			
			if(curMap.tile[curX+1][curY].walkable)
			{
				move = true;
				
				curX++;
				xOffset = 24;
			}
		}
		else if(dir == SOUTH)
		{
			direction = SOUTH;
			
			if(curMap.tile[curX][curY+1].type == Tile.SNOWWALLNORTH && curMap.tile[curX][curY+1].thing.type != Thing.MOUNTAINCAVEENTRANCE2)
			{
				move = false;
			}
			else if(curMap.tile[curX][curY+1].walkable)
			{
				move = true;
				
				curY++;
				yOffset = 24;
			}
		}
		else if(dir == WEST)
		{
			direction = WEST;
			
			if(curMap.tile[curX-1][curY].walkable)
			{
				move = true;
				
				curX--;
				xOffset = -24;
			}
		}
		
		if(move)
		{
			moveTimer = System.currentTimeMillis();
			
			if(curMap.tile[curX][curY].type == Tile.ICE)
			{
				sliding = dir;
				
				xOffset = 0;
				yOffset = 0;
			}
			else sliding = -1;
			
			if(sliding == -1)
			{
				//if going up/down a ladder, or warping, don't do any offset (looks bad)
				if(curMap.tile[curX][curY].thing.type == Thing.LADDERUP || curMap.tile[curX][curY].thing.type == Thing.LADDERDOWN)
				{
					xOffset = 0;
					yOffset = 0;
				}
				
				walkLoop();
			}

			steps++;
			
			if(steps%250 == 0)
			{
				saveGame("data_backup");
			}
			
			if(curMap.tile[curX][curY].event.type == Event.PORTAL)
			{
				int a = curMap.tile[curX][curY].event.destMap;
				int b = curMap.tile[curX][curY].event.destX;
				int c = curMap.tile[curX][curY].event.destY;
				
				if(curMap.tile[curX][curY].event.dir != -1)
				{
					direction = curMap.tile[curX][curY].event.dir;
					
					moveTimer += 250;
				}
				
				if(curMap.tile[curX][curY].sound != null)
				{
					playSound(curMap.tile[curX][curY].sound);
				}
				
				changeMap(a,b,c);

				wildRate = 0;
			}
			else if(curMap.tile[curX][curY].hasMonsters && !skipMonsters && sliding == -1)
			{
				int encRate = 70; //TODO: was 4
				int encThreshold = 1; //TODO: was 6
				
				if(wildRate > encThreshold && rand.nextInt(100) < encRate)
				{
					//TODO
					monsters = curMap.getMonsters(curMap.tile[curX][curY].monsterListID);
					for(int i=0; i<monsters.size(); i++)
					{
						monsters.get(i).resetCT();
					}
					
					for(int i=0; i<2; i++)
					{
						if(party[i].id != Character.NONE)
						{
							party[i].resetCT();
						}
					}
					
					setState(ENCOUNTER);
					
					timer = new Timer();
					timer.schedule(new Task(),29*100); //29 frames, 100ms
					newState = BATTLE;
					
					wildRate = 0;
					
					if(!mute)
					{
						songFrame = clip.getFramePosition();
						if(songFrame >= getLoopPoints(curMap.song)[1]) songFrame = (songFrame-getLoopPoints(curMap.song)[0])%(getLoopPoints(curMap.song)[1]-getLoopPoints(curMap.song)[0])+getLoopPoints(curMap.song)[0];
						
						playSong("Battle");
					}
				}
				
				wildRate++;
			}
			
			System.out.println("coord: " + curX + "," + curY);
		}
		else
		{
			long currentTime = System.currentTimeMillis();
			if(currentTime > lastBonk + 500)
			{
				playSound("bonk");
				lastBonk = currentTime;
			}
			
			if(sliding != -1) sliding = -1;
		}
	}
	
	public void getEquippableIndexList(int partyIndex, int equipIndex)
	{
		int equipType;
		if(equipIndex == 0) equipType = Item.WEAPON;
		else if(equipIndex == 1) equipType = Item.HAT;
		else if(equipIndex == 2) equipType = Item.ARMOR;
		else if(equipIndex == 3) equipType = Item.SHOE;
		else equipType = Item.ACCESSORY;
		
		equippableIndexList = new ArrayList<Integer>();
		equippableIndexList.add(-1); //None
		
		for(int i=0; i<inventory.size(); i++)
		{
			Item item = inventory.get(i);
			if(item.type == equipType)
			{
				if(item.equippableBy(party[partyIndex].id))
				{
					equippableIndexList.add(i);
				}
			}
		}
	}
	
	public boolean facingNPC()
	{
		if(tileFaced().event.type == Event.NPC) return true;
		
		return false;
	}
	
	public boolean facingShop()
	{
		if(tileFaced().event.type == Event.SHOP) return true;
		
		return false;
	}
	
	public boolean facingInnkeeper()
	{
		if(tileFaced().event.type == Event.INNKEEPER) return true;
		
		return false;
	}
	
	public boolean facingChest()
	{
		if(tileFaced().event.type == Event.CHEST)
		{
			if(tileFaced().event.state == 0) return true;
		}
		
		return false;
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
	
	}

	public void keyPressed(KeyEvent evt)
	{
		if(inputDisabled)
		{
			//do nothing
		}
		else if(evt.getKeyCode() == KeyEvent.VK_UP)
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
		/*else if(evt.getKeyCode() == KeyEvent.VK_ENTER) //TODO: remove
		{
			if(direction == NORTH) curY--;
			else if(direction == EAST) curX++;
			else if(direction == SOUTH) curY++;
			else if(direction == WEST) curX--;
			
			System.out.println("coord: " + curX + "," + curY);
		}*/
		else if(evt.getKeyCode() == KeyEvent.VK_I)
		{
			if(money < 1000000) money += 1000;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_P)
		{
			if(party[1].id == Character.NONE && party[3].id == Character.NONE)
			{
				party[3] = new Alex(1,0);
				party[4] = new Ryan(1,0);
				party[5] = new Mychal(1,0);
				party[6] = new Kitten(1,0);
			}
		}
		else if(evt.getKeyCode() == KeyEvent.VK_O)
		{
			for(int i=0; i<party.length; i++)
			{
				if(party[i].id == Character.BRIAN)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveBrianPunch(25,true));
					party[i].activeSkills.add(new ActiveBrianSmash(25,true));
					party[i].activeSkills.add(new ActiveCheezItBlast(25,true));
					party[i].activeSkills.add(new ActiveCoolRanchLaser(25,true));
					party[i].activeSkills.add(new ActiveFlavorExplosion(25,true));
					party[i].activeSkills.add(new ActiveMtnDewWave(25,true));
					party[i].activeSkills.add(new ActiveGottaGoFast(25,true));
					party[i].activeSkills.add(new ActiveGottaGoFaster(25,true));
					party[i].activeSkills.add(new ActiveMurder(25,true));
					party[i].activeSkills.add(new ActiveMassMurder(25,true));
				}
				else if(party[i].id == Character.ALEX)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveBarf(25,true));
					party[i].activeSkills.add(new ActiveFlail(25,true));
					party[i].activeSkills.add(new ActiveAnnoy(25,true));
					party[i].activeSkills.add(new ActiveCower(25,true));
					party[i].activeSkills.add(new ActiveShriek(25,true));
					party[i].activeSkills.add(new ActiveEatPopTart(25,true));
					party[i].activeSkills.add(new ActiveKamikaze(25,true));
					party[i].activeSkills.add(new ActiveVomitEruption(25,true));
					party[i].activeSkills.add(new ActiveSummonTrains(25,true));
				}
				else if(party[i].id == Character.RYAN)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveBlessingOfArino(25,true));
					party[i].activeSkills.add(new ActiveBlessingOfMiku(25,true));
					party[i].activeSkills.add(new ActiveMysteriousMelody(25,true));
					party[i].activeSkills.add(new ActiveBajaBlast(25,true));
					party[i].activeSkills.add(new ActiveBlueShield(25,true));
					party[i].activeSkills.add(new ActiveBlueBarrier(25,true));
					party[i].activeSkills.add(new ActiveSillyDance(25,true));
					party[i].activeSkills.add(new ActiveAmp(25,true));
					party[i].activeSkills.add(new ActiveRadicalRiff(25,true));
					party[i].activeSkills.add(new ActiveChiptuneOfLife(25,true));
				}
				else if(party[i].id == Character.MYCHAL)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveShuriken(25,true));
					party[i].activeSkills.add(new ActiveKageShadows(25,true));
					party[i].activeSkills.add(new ActiveInflictShame(25,true));
					party[i].activeSkills.add(new ActiveDefendHonor(25,true));
					party[i].activeSkills.add(new ActiveHonorForAll(25,true));
					party[i].activeSkills.add(new ActiveNinjutsuSlice(25,true));
					party[i].activeSkills.add(new ActiveSamuraiSlash(25,true));
					party[i].activeSkills.add(new ActiveBushidoBlade(25,true));
					party[i].activeSkills.add(new ActiveMuramasamara(25,true));
				}
				else if(party[i].id == Character.KITTEN)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveFire(25,true));
					party[i].activeSkills.add(new ActiveBigFire(25,true));
					party[i].activeSkills.add(new ActiveLightningBolt(25,true));
					party[i].activeSkills.add(new ActiveLightningStorm(25,true));
					party[i].activeSkills.add(new ActiveEarthSpike(25,true));
					party[i].activeSkills.add(new ActiveEarthquake(25,true));
					party[i].activeSkills.add(new ActiveSteal(25,true));
					party[i].activeSkills.add(new ActivePurr(25,true));
					party[i].activeSkills.add(new ActiveCatNap(25,true));
					party[i].activeSkills.add(new ActiveCatScratch(25,true));
					party[i].activeSkills.add(new ActiveDevour(25,true));
				}
			}
			
			for(int passiveID = 0; passiveID < PassiveSkill.NUMPASSIVESKILLS; passiveID++)
			{
				PassiveSkill passive = PassiveSkill.passiveSkillFromID(passiveID,0,false,false);
				for(int i=0; i<party.length; i++)
				{
					if(party[i].id != Character.NONE)
					{
						if(passive.canLearn(party[i]) && !party[i].hasLearnedPassiveSkill(passiveID))
						{
							party[i].passiveSkills.add(PassiveSkill.passiveSkillFromID(passiveID,passive.getCost(party[i]),true,false));
						}
					}
				}
			}
		}
		
		repaint();
	}
	
	public void talkToNPC()
	{
		if(direction == NORTH)
		{
			curEvent = curMap.tile[curX][curY-1].event;
			curEvent.dir = SOUTH;
		}
		else if(direction == EAST)
		{
			curEvent = curMap.tile[curX+1][curY].event;
			curEvent.dir = WEST;
		}
		else if(direction == SOUTH)
		{
			curEvent = curMap.tile[curX][curY+1].event;
			curEvent.dir = NORTH;
		}
		else
		{
			curEvent = curMap.tile[curX-1][curY].event;
			curEvent.dir = EAST;
		}
		
		//TODO: special NPC logic
		/*if(curEvent.name.equals("Example"))
		{
			if(condition)
			{
				curEvent.state = 2;
			}
		}*/
		
		setState(MAPDIALOGUE);
		addIndex(0);
	}
	
	public void pressedX()
	{
		if(state == MAINMENU)
		{
			if(curIndex() == 0) //Status
			{
				setState(MAINMENUSELECTCHAR);
				addIndex(0);
				
				playSound("menuX");
			}
			else if(curIndex() == 1) //Skills
			{
				setState(MAINMENUSELECTCHAR);
				addIndex(0);
				
				playSound("menuX");
			}
			else if(curIndex() == 2) //Equip
			{
				setState(MAINMENUSELECTCHAR);
				addIndex(0);
				
				playSound("menuX");
			}
			else if(curIndex() == 3 && inventory.size() > 0) //Inventory
			{
				setState(INVENTORY);
				addIndex(0);
				cursorAlign = 0;
				
				playSound("menuX");
			}
			else if(curIndex() == 4)
			{
				setState(EDITPARTY);
				addIndex(0);
				
				playSound("menuX");
			}
			else if(curIndex() == 5)
			{
				setState(WORLDMAP);
				
				playSound("menuX");
			}
			else if(curIndex() == 6) //Save
			{
				if(canSave())
				{
					setState(GAMESAVED);
					
					saveGame("data");
					playSound("saveGame");
				}
			}
		}
		else if(state == MAINMENUSELECTCHAR)
		{
			if(prevIndex(1) == 0) //Status
			{
				setState(STATUSMENU);
				addIndex(curIndex());
				addIndex(0);
			}
			else if(prevIndex(1) == 1) //Skills
			{
				setState(SKILLMENUSEL);
				addIndex(curIndex());
				addIndex(0);
			}
			else if(prevIndex(1) == 2) //Equip
			{
				setState(EQUIPMENU);
				addIndex(0);
			}
		}
		else if(state == EQUIPMENU)
		{
			getEquippableIndexList(prevIndex(1),curIndex());
			
			placeholderCharacter = Unit.getCopyCharacter(party[prevIndex(1)]);
			placeholderCharacter.equip[curIndex()] = new NoItem();
			placeholderCharacter.recalculateStats();
			
			setState(EQUIPMENUSEL);
			cursorAlign = 0;
			addIndex(0);
		}
		else if(state == EQUIPMENUSEL)
		{
			int equipItemIndex = equippableIndexList.get(curIndex());
			int equipSlot = prevIndex(1);
			int partyIndex = prevIndex(2);
			
			Item oldEquip = party[partyIndex].equip[equipSlot];
			if(equipItemIndex == -1)
			{
				party[partyIndex].equip[equipSlot] = new NoItem();
			}
			else
			{
				party[partyIndex].equip[equipSlot] = Item.itemFromID(inventory.get(equipItemIndex).id);
				removeFromInventory(inventory.get(equipItemIndex),1);
			}
			addToInventory(oldEquip);
			
			party[partyIndex].recalculateStats();
			
			equippableIndexList = null;
			
			setState(EQUIPMENU);
			removeIndex();
		}
		else if(state == GAMESAVED)
		{
			setState(MAINMENU);
		}
		else if(state == MAP)
		{
			if(facingNPC())
			{
				talkToNPC();
			}
			else if(facingShop())
			{
				setState(SHOPOPTION);
				
				curEvent = tileFaced().event;
				
				addIndex(0);
			}
			else if(facingInnkeeper())
			{
				setState(INNCHOICE);
				
				curEvent = tileFaced().event;
				
				addIndex(0);
			}
			else if(facingChest())
			{
				setState(MAPDIALOGUE);
				
				curEvent = tileFaced().event;
				
				addIndex(0);
				
				addToInventory(curEvent.item);
				
				playSound("item");
			}
			else if(tileFaced().event.type == Event.ART)
			{
				curEvent = tileFaced().event;
				
				setState(VIEWART);
			}
		}
		else if(state == MAPDIALOGUE)
		{
			if(curIndex() == curEvent.getDialogue(curEvent.state).size()-1)
			{
				if(curEvent.type == Event.CHEST)
				{
					curMap.tile[curEvent.x][curEvent.y].openChest();
					
					curMap.recalculateStates();
					
					setState(MAP);
				}
				else if(curEvent.type == Event.NPC)
				{
					//things that happen when you finish talking to an NPC
					
					setState(MAP);
				}
			}
			else
			{
				incIndex();
			}
		}
		else if(state == INVENTORY)
		{
			if(inventory.get(curIndex()).usable)
			{
				setState(INVENTORYUSE);
				addIndex(0);
				
				playSound("menuX");
			}
		}
		else if(state == INVENTORYSORT)
		{
			System.out.println("curIndex() = " + curIndex());
			
			if(curIndex() == 0)
			{
				Collections.sort(inventory, new CompareID());
			}
			else if(curIndex() == 1)
			{
				Collections.sort(inventory, new CompareName());
			}
			
			playSound("menuX");
		}
		else if(state == INVENTORYUSE)
		{
			if(inventory.get(prevIndex(1)).type == Item.HP)
			{
				if(party[curIndex()].hp < party[curIndex()].maxHp && party[curIndex()].hp > 0)
				{
					party[curIndex()].healHP(inventory.get(prevIndex(1)).amt);

					inventory.get(prevIndex(1)).qty--;
					if(inventory.get(prevIndex(1)).qty <= 0)
					{
						inventory.remove(prevIndex(1));
						if(inventory.size() == 0) setState(MAINMENU);
						else setState(INVENTORY);
						removeIndex();
						setIndex(0);
						cursorAlign = 0;
					}
					
					playSound("heal");
				}
			}
			else if(inventory.get(prevIndex(1)).type == Item.MP)
			{
				if(party[curIndex()].mp < party[curIndex()].maxMp && party[curIndex()].hp > 0)
				{
					party[curIndex()].healMP(inventory.get(prevIndex(1)).amt);

					inventory.get(prevIndex(1)).qty--;
					if(inventory.get(prevIndex(1)).qty <= 0)
					{
						inventory.remove(prevIndex(1));
						if(inventory.size() == 0) setState(MAINMENU);
						else setState(INVENTORY);
						removeIndex();
						setIndex(0);
						cursorAlign = 0;
					}
					
					playSound("heal");
				}
			}
			else if(inventory.get(prevIndex(1)).type == Item.REVIVE)
			{
				if(party[curIndex()].hp == 0)
				{
					if(inventory.get(prevIndex(1)).id == Item.MOUNTAINDEW)
					{
						party[curIndex()].hp = party[curIndex()].maxHp/4;
					}
					
					inventory.get(prevIndex(1)).qty--;
					if(inventory.get(prevIndex(1)).qty <= 0)
					{
						inventory.remove(prevIndex(1));
						if(inventory.size() == 0) setState(MAINMENU);
						else setState(INVENTORY);
						removeIndex();
						setIndex(0);
						cursorAlign = 0;
					}
					
					playSound("heal");
				}
			}
		}
		else if(state == SHOPOPTION)
		{
			if(curIndex() == 0)
			{
				setState(SHOPBUY);
				addIndex(0);
				cursorAlign = 0;
				
				playSound("menuX");
			}
			else if(curIndex() == 1)
			{
				if(inventory.size() > 0)
				{
					setState(SHOPSELL);
					addIndex(0);
					cursorAlign = 0;
					
					playSound("menuX");
				}
			}
			else
			{
				setState(MAP);
				removeIndex();
				
				playSound("menuX");
			}
		}
		else if(state == SHOPBUY)
		{
			if(money >= curEvent.inventory[curIndex()].price)
			{
				setState(SHOPBUY2);
				addIndex(1);
				
				playSound("menuX");
			}
		}
		else if(state == SHOPBUY2)
		{
			int maxAmt = 99;
			
			int indexOf = -1;
			for(int i=0; i<inventory.size(); i++)
			{
				if(inventory.get(i).name.equals(curEvent.inventory[prevIndex(1)].name)) indexOf = i;
			}
			
			int maxBuy = 99;
			
			if(indexOf > -1) maxBuy = Math.min(maxBuy, maxAmt-inventory.get(indexOf).qty);
			
			if(money >= curIndex()*curEvent.inventory[prevIndex(1)].price && curIndex() <= maxBuy)
			{
				for(int i=0; i<curIndex(); i++)
				{
					addToInventory(curEvent.inventory[prevIndex(1)]);
				}
				money -= curIndex() * curEvent.inventory[prevIndex(1)].price;
				
				setState(SHOPBUY);
				removeIndex();
				
				playSound("kaching");
			}
			else
			{
				playSound("bonk");
			}
		}
		else if(state == SHOPSELL)
		{
			if(inventory.get(curIndex()).price > 0)
			{
				setState(SHOPSELL2);
				addIndex(1);
				
				playSound("menuX");
			}
		}
		else if(state == SHOPSELL2)
		{
			money += curIndex()*inventory.get(prevIndex(1)).price/2;
			
			if(money > 999999) money = 999999;
			
			inventory.get(prevIndex(1)).qty -= curIndex();
			
			if(inventory.get(prevIndex(1)).qty <= 0)
			{
				inventory.remove(prevIndex(1));
				
				removeIndex();
				
				if(curIndex() == inventory.size() && inventory.size() > 0)
				{
					decIndex();
					cursorAlign--; //TODO: test this
				}
				else if(curIndex() == inventory.size())
				{
					setIndex(0);
					cursorAlign = 0;
				}
			}
			else
			{
				removeIndex();
			}
			
			playSound("kaching");
			
			if(inventory.size() > 0) setState(SHOPSELL);
			else
			{
				setState(SHOPOPTION);
				removeIndex();
			}
		}
		else if(state == TITLESCREEN)
		{
			if(curIndex() == 1) //New Game
			{
				if(loadData()) saveGame("old");
				
				newGame();
				loadData("data"); //load the new data
				
				if(!mute)
				{
					playSong("Intro");
					clip.setFramePosition(30737);
				}
				
				addIndex(0);
			}
			else if(curIndex() == 0) //Continue
			{
				if(loadData()) //data exists
				{
					setState(MAP);
					
					playMapSong(false);
				}
				else
				{
					playSound("bonk");
				}
			}
		}
		else if(state == INVENTORYREARRANGE)
		{
			int index = curIndex();
			
			Item temp = inventory.get(prevIndex(1));
			inventory.set(prevIndex(1),inventory.get(curIndex()));
			inventory.set(curIndex(),temp);
			
			setState(INVENTORY);
			
			removeIndex();
			setIndex(index);
		}
		else if(state == VIEWART)
		{
			setState(MAP);
			
			curEvent = null;
		}
		else if(state == BATTLECHOICE)
		{
			if(curIndex() == 0) //Attack
			{
				selectedAction = new Attack();
				
				setState(BATTLETARGET);
				addIndex(topAliveMonsterIndex());
			}
			else if(curIndex() == 1 && currentUnit.getLearnedActiveSkills().size() > 0) //Skill
			{
				setState(BATTLESELECTSKILL);
				cursorAlign = 0;
				addIndex(0);
			}
			else if(curIndex() == 2 && inventory.size() > 0) //Item
			{
				setState(BATTLESELECTITEM);
				cursorAlign = 0;
				addIndex(0);
			}
			else if(curIndex() == 3) //Flee
			{
				int highestMonsterSpd = 0;
				for(int i=0; i<monsters.size(); i++)
				{
					if(monsters.get(i).hp > 0 && monsters.get(i).spd > highestMonsterSpd) highestMonsterSpd = monsters.get(i).spd;
				}
				
				double dfleeChance = 60.0*((double)currentUnit.spd/highestMonsterSpd);
				int fleeChance = (int) dfleeChance;
				
				if(rand.nextInt(100) < fleeChance)
				{
					setState(BATTLEFLED);
				}
				else	
				{
					int[] animation = new int[7];
					for(int i=0; i<animation.length; i++) animation[i] = NORMAL;
					
					battleAction = new BattleAction(currentUnit,Action.FLEE,null,null,null,animation,null,null,null);
					
					setState(BATTLEEFFECT);
					repaint();
					
					timer = new Timer();
					timer.schedule(new Task(),1000);
					newState = BATTLERECALCULATE;
				}
			}
		}
		else if(state == BATTLESELECTSKILL)
		{
			if(currentUnit.canUseSkill(currentUnit.getLearnedActiveSkills().get(curIndex()).action))
			{
				selectedAction = currentUnit.getLearnedActiveSkills().get(curIndex()).action;
				
				int targetType = selectedAction.targetType;
				if(targetType == Action.ONEENEMY || targetType == Action.ALLENEMIES)
				{
					addIndex(topAliveMonsterIndex());
				}
				else if(targetType == Action.ONEALLY || targetType == Action.ALLALLIES)
				{
					addIndex(topAlivePartyIndex());
				}
				else if(targetType == Action.SELF)
				{
					addIndex(currentUnit.getBattleIndex());
				}
				else if(targetType == Action.ONEUNIT)
				{
					addIndex(topAliveMonsterIndex()+3);
				}
				
				setState(BATTLETARGET);
			}
		}
		else if(state == BATTLESELECTITEM)
		{
			int itemType = inventory.get(curIndex()).type;
			if(itemType == Item.HP)
			{
				selectedAction = new HPItem();
			}
			else if(itemType == Item.MP)
			{
				selectedAction = new MPItem();
			}
			else if(itemType == Item.REVIVE)
			{
				selectedAction = new ReviveItem();
			}
			else
			{
				return; //unknown item type
			}
			
			if(selectedAction.targetType == Action.ONEALLY)
			{
				setState(BATTLETARGET);
				
				if(selectedAction.id == Action.REVIVEITEM) addIndex(topDeadPartyIndex());
				else addIndex(topAlivePartyIndex());
			}
		}
		else if(state == BATTLETARGET)
		{
			ArrayList<Integer> targets = new ArrayList<Integer>();

			if(selectedAction.targetType == Action.ONEENEMY)
			{
				targets.add(monsters.get(curIndex()).getBattleIndex());
			}
			else if(selectedAction.targetType == Action.ALLENEMIES)
			{
				for(int i=0; i<monsters.size(); i++)
				{
					if(monsters.get(i).hp > 0)
					{
						targets.add(monsters.get(i).getBattleIndex());
					}
				}
			}
			else if(selectedAction.targetType == Action.ONEALLY || selectedAction.targetType == Action.SELF)
			{
				targets.add(curIndex());
			}
			else if(selectedAction.targetType == Action.ALLALLIES)
			{
				for(int i=0; i<3; i++)
				{
					if(party[i].hp > 0)
					{
						targets.add(i);
					}
				}
			}
			else if(selectedAction.targetType == Action.ONEUNIT)
			{
				targets.add(curIndex());
			}
			
			if(prevState == BATTLESELECTITEM) //using an item
			{
				inventory.get(prevIndex(1)).qty--;
				if(inventory.get(prevIndex(1)).qty <= 0)
				{
					inventory.remove(prevIndex(1));
				}
			}
			
			doBattleCalculations(currentUnit,selectedAction.id,targets);
		}
		else if(state == BATTLEWON)
		{
			afterBattleAlerts = new ArrayList<AfterBattleAlert>();
			
			int totalExp = getTotalExpGain();
			int totalSP = getTotalSPGain();
			boolean leveledUp;
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE)
				{
					leveledUp = false;
					if(party[i].hp > 0)
					{
						leveledUp = party[i].giveExp(totalExp);
					}
					
					if(leveledUp)
					{
						AfterBattleAlert levelUpAlert = new AfterBattleAlert("Leveled up!",i,Color.ORANGE);
						afterBattleAlerts.add(levelUpAlert);
					}
					
					for(int j=0; j<party[i].getLearnedActiveSkills().size(); j++)
					{
						int cost = party[i].getLearnedActiveSkills().get(j).getCost(party[i]);
						if(party[i].getLearnedActiveSkills().get(j).currentSP < cost)
						{
							party[i].getLearnedActiveSkills().get(j).currentSP += totalSP;
							if(party[i].getLearnedActiveSkills().get(j).currentSP >= cost)
							{
								party[i].getLearnedActiveSkills().get(j).currentSP = cost;
								
								//learned skill!
								AfterBattleAlert learnedSkillAlert = new AfterBattleAlert("Learned " + party[i].getLearnedActiveSkills().get(j).action.name + "!",i,Color.BLUE);
								afterBattleAlerts.add(learnedSkillAlert);
							}
						}
					}
					
					for(int j=0; j<party[i].getLearnedPassiveSkills().size(); j++)
					{
						int cost = party[i].getLearnedPassiveSkills().get(j).getCost(party[i]);
						if(party[i].getLearnedPassiveSkills().get(j).currentSP < cost)
						{
							party[i].getLearnedPassiveSkills().get(j).currentSP += totalSP;
							if(party[i].getLearnedPassiveSkills().get(j).currentSP >= cost)
							{
								party[i].getLearnedPassiveSkills().get(j).currentSP = cost;
								
								//learned skill!
								AfterBattleAlert learnedSkillAlert = new AfterBattleAlert("Learned " + party[i].getLearnedPassiveSkills().get(j).name + "!",i,Color.BLUE);
								afterBattleAlerts.add(learnedSkillAlert);
							}
						}
					}
					
					party[i].clearTempStatuses();
					party[i].resetCT();
				}
			}
			
			money += getTotalMoneyGain();
			
			/**
			 * Calculate drops
			 */
			itemDrops = new ArrayList<Item>();
			for(int i=0; i<monsters.size(); i++)
			{
				for(int j=0; j<monsters.get(i).itemDrops.size(); j++)
				{
					if(rand.nextInt(100) < monsters.get(i).itemRates.get(j))
					{
						addToItemList(monsters.get(i).itemDrops.get(j), itemDrops);
						addToInventory(monsters.get(i).itemDrops.get(j));
					}
				}
			}
			
			setState(AFTERBATTLE);
		}
		else if(state == BATTLEFLED)
		{
			setState(AFTERBATTLE);
		}
		else if(state == AFTERBATTLE)
		{
			setState(MAP);
			
			clearIndex();
		}
		else if(state == BATTLELOST)
		{
			setState(GAMEOVER);
		}
		else if(state == GAMEOVER)
		{
			setState(TITLESCREEN);
			
			clearIndex();
		}
		else if(state == EDITPARTY)
		{
			if(curIndex() == 8) return;
			
			setState(EDITPARTYSWITCH);
			addIndex(curIndex());
		}
		else if(state == EDITPARTYSWITCH)
		{
			if(curIndex() == 8) return;
			
			int moveFromIndex = prevIndex(1);
			int moveToIndex = curIndex();
			
			//make sure the entire party won't be dead
			int[] partyArray = {0,1,2,3,4,5,6,7};
			partyArray[moveToIndex] = moveFromIndex;
			partyArray[moveFromIndex] = moveToIndex;
			
			boolean partyWillBeAlive = false;
			for(int i=0; i<3; i++)
			{
				if(party[partyArray[i]].existsAndAlive()) partyWillBeAlive = true;
			}
			
			if(!partyWillBeAlive) return; //don't do it
			
			Unit tempUnit = party[moveToIndex];
			party[moveToIndex] = party[moveFromIndex];
			party[moveFromIndex] = tempUnit;
			
			int tempBattleIndex = party[moveToIndex].index;
			party[moveToIndex].index = party[moveFromIndex].index;
			party[moveFromIndex].index = tempBattleIndex;
			
			//shift party if necessary
			ArrayList<Integer> actualIndices = new ArrayList<Integer>();
			ArrayList<Integer> correctIndices = new ArrayList<Integer>();
			int numCharacters = 0;
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE)
				{
					actualIndices.add(i);
					correctIndices.add(numCharacters);
					numCharacters++;
				}
			}
			
			for(int i=0; i<actualIndices.size(); i++)
			{
				if(actualIndices.get(i) != correctIndices.get(i))
				{
					switchCharacters(actualIndices.get(i), correctIndices.get(i));
				}
			}
			
			actualIndices = new ArrayList<Integer>();
			correctIndices = new ArrayList<Integer>();
			numCharacters = 0;
			for(int i=3; i<8; i++)
			{
				if(party[i].id != Character.NONE)
				{
					actualIndices.add(i);
					correctIndices.add(3+numCharacters);
					numCharacters++;
				}
			}
			
			for(int i=0; i<actualIndices.size(); i++)
			{
				if(actualIndices.get(i) != correctIndices.get(i))
				{
					switchCharacters(actualIndices.get(i), correctIndices.get(i));
				}
			}
			
			
//			for(int i=0; i<2; i++)
//			{
//				if(party[i].id == Character.NONE && party[i+1].id != Character.NONE)
//				{
//					for(int j=i; j<2; j++)
//					{
//						party[j] = party[j+1];
//					}
//					
//					party[2] = new None();
//				}
//			}
//			for(int i=3; i<7; i++)
//			{				
//				if(party[i].id == Character.NONE)
//				{
//					boolean partyExistsAfter = false;
//					for(int j=i; j<=7; j++)
//					{
//						if(party[j].id != Character.NONE) partyExistsAfter = true; 
//					}
//					
//					if(partyExistsAfter)
//					{
//						while(party[i].id == Character.NONE)
//						{
//							for(int j=i; j<7; j++)
//							{
//								party[j] = party[j+1];
//							}
//						}
//						
//						party[7] = new None();
//						
//						System.out.println(party[3].id + ", " + party[4].id + ", " + party[5].id + ", " + party[6].id + ", " + party[7].id);
//					}
//				}
//			}
			
			setState(EDITPARTY);
			removeIndex();
			setIndex(moveToIndex);
		}
		else if(state == SKILLMENUSEL)
		{
			if(curIndex() == 0 && party[prevIndex(1)].getLearnedActiveSkills().size() > 0)
			{
				setState(ACTIVESKILLMENU);
				addIndex(0);
				cursorAlign = 0;
			}
			else if(curIndex() == 1 && party[prevIndex(1)].getLearnedPassiveSkills().size() > 0)
			{
				setState(PASSIVESKILLMENU);
				addIndex(0);
				cursorAlign = 0;
			}
		}
		else if(state == PASSIVESKILLMENU)
		{
			if(party[prevIndex(2)].passiveSkills.get(curIndex()).equipped) //unequipping
			{
				party[prevIndex(2)].passiveSkills.get(curIndex()).equipped = false;
				party[prevIndex(2)].cubes += party[prevIndex(2)].passiveSkills.get(curIndex()).cubeCost;
				party[prevIndex(2)].recalculateStats();
			}
			else //equipping
			{
				if(party[prevIndex(2)].cubes >= party[prevIndex(2)].passiveSkills.get(curIndex()).cubeCost)
				{
					party[prevIndex(2)].passiveSkills.get(curIndex()).equipped = true;
					party[prevIndex(2)].cubes -= party[prevIndex(2)].passiveSkills.get(curIndex()).cubeCost;
					party[prevIndex(2)].recalculateStats();
				}
			}
		}
		else if(state == INNCHOICE)
		{
			if(curIndex() == 1)
			{
				if(money >= curEvent.innPrice)
				{
					money -= curEvent.innPrice;
				}
				
				for(int i=0; i<party.length; i++)
				{
					if(party[i].id != Character.NONE)
					{
						party[i].fullHeal();
					}
				}
			}
			
			setState(MAP);
			removeIndex();
		}
	}
	
	public void pressedZ()
	{
		if(state == MAINMENU)
		{
			setState(MAP);
			setIndex(0);
		}
		else if(state == MAINMENUSELECTCHAR)
		{
			setState(MAINMENU);
			removeIndex();
		}
		else if(state == STATUSMENU)
		{
			setState(MAINMENU);
			
			int index = prevIndex(2);
			removeIndex();
			removeIndex();
			removeIndex();
			setIndex(index);
		}
		else if(state == EQUIPMENU)
		{
			setState(MAINMENU);
			
			int index = prevIndex(2);
			removeIndex();
			removeIndex();
			setIndex(index);
		}
		else if(state == EQUIPMENUSEL)
		{
			setState(EQUIPMENU);
			
			placeholderCharacter = null;
			
			removeIndex();
		}
		else if(state == GAMESAVED)
		{
			setState(MAINMENU);
		}
		else if(state == MAPDIALOGUE)
		{
			if(curIndex() == curEvent.getDialogue(curEvent.state).size()-1)
			{
				if(curEvent.type == Event.NPC)
				{
					setState(MAP);
					
					removeIndex();
				}
			}
			else
			{
				incIndex();
			}
		}
		else if(state == INVENTORY || state == INVENTORYSORT)
		{
			setState(MAINMENU);
			removeIndex();
		}
		else if(state == INVENTORYUSE)
		{
			setState(INVENTORY);
			removeIndex();
		}
		else if(state == SHOPOPTION)
		{
			setState(MAP);
			removeIndex();
		}
		else if(state == SHOPBUY)
		{
			setState(SHOPOPTION);
			removeIndex();
		}
		else if(state == SHOPBUY2)
		{
			setState(SHOPBUY);
			removeIndex();
		}
		else if(state == SHOPSELL)
		{
			setState(SHOPOPTION);
			removeIndex();
		}
		else if(state == SHOPSELL2)
		{
			setState(SHOPSELL);
			removeIndex();
		}
		else if(state == WORLDMAP)
		{
			setState(MAINMENU);
		}
		else if(state == INVENTORYREARRANGE)
		{
			setState(INVENTORY);
			removeIndex();
		}
		else if(state == VIEWART)
		{
			setState(MAP);
			
			curEvent = null;
		}
		else if(state == BATTLETARGET)
		{
			if(prevState == BATTLECHOICE && prevIndex(1) == 0) //Attack
			{
				setState(BATTLECHOICE);
			}
			else if(prevState == BATTLESELECTSKILL) //Skill
			{
				setState(BATTLESELECTSKILL);
			}
			else if(prevState == BATTLESELECTITEM) //Item
			{
				setState(BATTLESELECTITEM);
			}
			
			removeIndex();
		}
		else if(state == BATTLESELECTSKILL || state == BATTLESELECTITEM)
		{
			setState(BATTLECHOICE);
			removeIndex();
		}
		else if(state == EDITPARTY)
		{
			setState(MAINMENU);
			removeIndex();
		}
		else if(state == EDITPARTYSWITCH)
		{
			setState(EDITPARTY);
			removeIndex();
		}
		else if(state == SKILLMENUSEL)
		{
			setState(MAINMENU);
			
			int index = prevIndex(3);
			removeIndex();
			removeIndex();
			removeIndex();
			setIndex(index);
		}
		else if(state == ACTIVESKILLMENU || state == PASSIVESKILLMENU)
		{
			setState(SKILLMENUSEL);
			removeIndex();
		}
		else if(state == STATUSMENUMOREINFO)
		{
			setState(STATUSMENU);
			removeIndex();
		}
	}
	
	public void pressedUp()
	{
		if(state == MAP && sliding == -1)
		{
			moveNorth = true;
		}
		else if(state == MAINMENU)
		{
			if(curIndex() > 0) decIndex();
			else setIndex(6);
		}
		else if(state == MAINMENUSELECTCHAR || state == INVENTORYUSE)
		{
			if(curIndex() == 0)
			{
				if(party[2].id != Character.NONE) setIndex(2);
				else if(party[1].id != Character.NONE) setIndex(1); 
			}
			else decIndex();
		}
		else if(state == STATUSMENU)
		{
			if(curIndex() > 0) decIndex();
		}
		else if(state == EQUIPMENU)
		{
			if(curIndex() > 0) decIndex();
		}
		else if(state == EQUIPMENUSEL)
		{
			if(curIndex() > 0)
			{
				//need to reset passive skills in case things got unequipped on the placeholder
				placeholderCharacter.passiveSkills = new ArrayList<PassiveSkill>();
				for(int i=0; i<party[prevIndex(2)].passiveSkills.size(); i++)
				{
					placeholderCharacter.passiveSkills.add(PassiveSkill.duplicate(party[prevIndex(2)].passiveSkills.get(i)));
				}
				
				decIndex();
				
				int itemIndex = equippableIndexList.get(curIndex());
				
				if(itemIndex == -1)
				{
					placeholderCharacter.equip[prevIndex(1)] = new NoItem();
				}
				else	
				{
					placeholderCharacter.equip[prevIndex(1)] = Item.itemFromID(inventory.get(itemIndex).id);
				}
				
				placeholderCharacter.recalculateStats();
				
				if(cursorAlign > 0) cursorAlign--;
			}
		}
		else if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			if(curIndex() > 0)
			{
				decIndex();
				
				if(cursorAlign > 0) cursorAlign--;
			}
			else if(state == INVENTORY && inventory.size() > 0)
			{
				setState(INVENTORYSORT);
				setIndex(0);
			}
		}
		else if(state == SHOPBUY)
		{
			if(curIndex() > 0)
			{
				decIndex();
				
				if(cursorAlign > 0) cursorAlign--;
			}
		}
		else if(state == SHOPBUY2)
		{
			setIndex(curIndex()+10);
			
			int maxAmt = 99;
			
			int maxBuy = Math.min(maxAmt,money/(curEvent.inventory[prevIndex(1)].price));
			
			int indexOf = -1;
			for(int i=0; i<inventory.size(); i++)
			{
				if(inventory.get(i).name.equals(curEvent.inventory[prevIndex(1)].name)) indexOf = i;
			}
			
			if(indexOf > -1) maxBuy = Math.min(maxBuy, maxAmt-inventory.get(indexOf).qty);
			
			if(curIndex() > maxBuy) setIndex(maxBuy);
			
			if(curIndex() < 1) setIndex(1);
		}
		else if(state == SHOPSELL)
		{
			if(curIndex() > 0)
			{
				decIndex();
				
				if(cursorAlign > 0) cursorAlign--;
			}
		}
		else if(state == SHOPSELL2)
		{
			setIndex(curIndex()+10);
			if(curIndex() > inventory.get(prevIndex(1)).qty) setIndex(inventory.get(prevIndex(1)).qty);
		}
		else if(state == TITLESCREEN)
		{
			if(curIndex() == 0) setIndex(1);
			else setIndex(0);
		}
		else if(state == BATTLECHOICE)
		{
			if(curIndex() > 0) decIndex();
		}
		else if(state == BATTLETARGET)
		{
			if(selectedAction.targetType == Action.ONEENEMY)
			{
				setIndex(aboveMonsterIndex(curIndex()));
			}
			else if(selectedAction.targetType == Action.ONEALLY)
			{
				setIndex(abovePartyIndex(curIndex()));
			}
			else if(selectedAction.targetType == Action.ONEUNIT)
			{
				if(curIndex() < 3)
				{
					setIndex(abovePartyIndex(curIndex()));
				}
				else
				{
					setIndex(aboveMonsterIndex(curIndex()-3)+3);
				}
			}
		}
		else if(state == BATTLESELECTSKILL || state == BATTLESELECTITEM)
		{
			if(curIndex() > 0)
			{
				decIndex();
				if(cursorAlign > 0) cursorAlign--;
			}
		}
		else if(state == EDITPARTY || state == EDITPARTYSWITCH)
		{
			if(curIndex() == 1 || curIndex() == 2)
			{
				decIndex();
			}
			else if(curIndex() >= 5)
			{
				setIndex(curIndex()-2);
			}
		}
		else if(state == STATUSMENUMOREINFO)
		{
			if(curIndex() >= 1 && curIndex() <= 13) decIndex();
			else if(curIndex() == 14 || curIndex() == 15) setIndex(3);
			else if(curIndex() >= 16 && curIndex() <= 26) setIndex(curIndex() - 2);
		}
		else if(state == ACTIVESKILLMENU)
		{
			if(curIndex() > 0)
			{
				decIndex();
				if(cursorAlign > 0) cursorAlign--;
			}
		}
		else if(state == PASSIVESKILLMENU)
		{
			if(curIndex() > 0)
			{
				decIndex();
				if(cursorAlign > 0) cursorAlign--;
			}
		}
	}
	
	public void pressedDown()
	{
		if(state == MAP && sliding == -1)
		{
			moveSouth = true;
		}
		else if(state == MAINMENU)
		{
			if(curIndex() < 6) incIndex();
			else setIndex(0);
		}
		else if(state == MAINMENUSELECTCHAR || state == INVENTORYUSE)
		{
			if(curIndex() < 2 && party[curIndex()+1].id != Character.NONE) incIndex();
			else setIndex(0);
		}
		else if(state == EQUIPMENU)
		{
			if(curIndex() < 4) incIndex();
		}
		else if(state == EQUIPMENUSEL)
		{
			if(curIndex() < equippableIndexList.size()-1)
			{
				//need to reset passive skills in case things got unequipped on the placeholder
				placeholderCharacter.passiveSkills = new ArrayList<PassiveSkill>();
				for(int i=0; i<party[prevIndex(2)].passiveSkills.size(); i++)
				{
					placeholderCharacter.passiveSkills.add(PassiveSkill.duplicate(party[prevIndex(2)].passiveSkills.get(i)));
				}
				
				incIndex();
				
				int itemIndex = equippableIndexList.get(curIndex());
				
				if(itemIndex == -1)
				{
					placeholderCharacter.equip[prevIndex(1)] = new NoItem();
				}
				else
				{
					placeholderCharacter.equip[prevIndex(1)] = Item.itemFromID(inventory.get(itemIndex).id);
				}
				
				if(cursorAlign < 3) cursorAlign++;
				
				placeholderCharacter.recalculateStats();
			}
		}
		else if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			if(curIndex() < inventory.size() - 1)
			{
				incIndex();
				
				if(cursorAlign < 14) cursorAlign++;
			}
		}
		else if(state == INVENTORYSORT)
		{
			setState(INVENTORY);
			setIndex(0);
			cursorAlign = 0;
		}
		else if(state == SHOPBUY)
		{
			if(curIndex() < curEvent.inventory.length - 1)
			{
				incIndex();
				
				if(cursorAlign < 14) cursorAlign++;
			}
		}
		else if(state == SHOPBUY2)
		{
			setIndex(curIndex()-10);
			if(curIndex() < 1) setIndex(1);
		}
		else if(state == SHOPSELL)
		{
			if(curIndex() + 1 < inventory.size())
			{
				incIndex();
				
				if(cursorAlign < 14) cursorAlign++;
			}
		}
		else if(state == SHOPSELL2)
		{
			setIndex(curIndex()-10);
			if(curIndex() < 1) setIndex(1);
		}
		else if(state == TITLESCREEN)
		{
			if(curIndex() == 0) setIndex(1);
			else setIndex(0);
		}
		else if(state == BATTLECHOICE)
		{
			if(curIndex() < 3) incIndex();
		}
		else if(state == BATTLETARGET)
		{
			if(selectedAction.targetType == Action.ONEENEMY)
			{
				setIndex(belowMonsterIndex(curIndex()));
			}
			else if(selectedAction.targetType == Action.ONEALLY)
			{
				setIndex(belowPartyIndex(curIndex()));
			}
			else if(selectedAction.targetType == Action.ONEUNIT)
			{
				if(curIndex() < 3)
				{
					setIndex(belowPartyIndex(curIndex()));
				}
				else
				{
					setIndex(belowMonsterIndex(curIndex()-3)+3);
				}
			}
		}
		else if(state == BATTLESELECTSKILL)
		{
			if(curIndex() < currentUnit.getLearnedActiveSkills().size()-1)
			{
				incIndex();
				if(cursorAlign < 3) cursorAlign++;
			}
		}
		else if(state == BATTLESELECTITEM)
		{
			if(curIndex() < inventory.size()-1)
			{
				incIndex();
				if(cursorAlign < 3) cursorAlign++;
			}
		}
		else if(state == EDITPARTY || state == EDITPARTYSWITCH)
		{
			if(curIndex() < 2)
			{
				incIndex();
			}
			else if(curIndex() >= 3 && curIndex() <= 6)
			{
				setIndex(curIndex()+2);
			}
		}
		else if(state == STATUSMENUMOREINFO)
		{
			if(curIndex() >= 0 && curIndex() <= 12) incIndex();
			else if(curIndex() >= 14 && curIndex() <= 24) setIndex(curIndex() + 2);
			else if(curIndex() == 25) incIndex();
		}
		else if(state == ACTIVESKILLMENU)
		{
			if(curIndex() < party[prevIndex(2)].getLearnedActiveSkills().size()-1)
			{
				incIndex();
				if(cursorAlign < 8) cursorAlign++;
			}
		}
		else if(state == PASSIVESKILLMENU)
		{
			if(curIndex() < party[prevIndex(2)].getLearnedPassiveSkills().size()-1)
			{
				incIndex();
				if(cursorAlign < 8) cursorAlign++;
			}
		}
	}
	
	public void pressedRight()
	{
		if(state == MAP && sliding == -1)
		{
			moveEast = true;
		}
		else if(state == STATUSMENU)
		{
			int prevPartyIndex = prevIndex(1);
			if(prevIndex(1) < 2 && party[prevIndex(1)+1].id != Character.NONE) index.set(index.size()-2,prevIndex(1)+1);
			else index.set(index.size()-2,0);
			
			if(prevIndex(1) != prevPartyIndex) setIndex(0); //select skill 0 if party member changed
		}
		else if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			setIndex(curIndex()+10);
			if(curIndex() >= inventory.size()) setIndex(inventory.size() - 1);
			
			cursorAlign += 10;
			if(cursorAlign > 14) cursorAlign = 14;
			if(cursorAlign > inventory.size() - 1) cursorAlign = inventory.size() - 1;
		}
		else if(state == INVENTORYSORT)
		{
			setIndex(curIndex()*-1+1);
		}
		else if(state == BATTLESELECTSKILL)
		{
			int numSkills = currentUnit.getLearnedActiveSkills().size();
			
			setIndex(curIndex() + 4);
			if(curIndex() > numSkills-1)
			{
				setIndex(numSkills-1);
			}
			
			cursorAlign += 4;
			if(cursorAlign > 3) cursorAlign = 3;
			if(cursorAlign > numSkills - 1) cursorAlign = numSkills - 1;
		}
		else if(state == BATTLESELECTITEM)
		{
			int numItems = inventory.size();
			
			setIndex(curIndex() + 4);
			if(curIndex() > numItems-1)
			{
				setIndex(numItems-1);
			}
			
			cursorAlign += 4;
			if(cursorAlign > 3) cursorAlign = 3;
			if(cursorAlign > numItems - 1) cursorAlign = numItems - 1;
		}
		else if(state == BATTLETARGET)
		{
			if(selectedAction.targetType == Action.ONEUNIT && curIndex() < 3)
			{
				setIndex(topAliveMonsterIndex() + 3);
			}
		}
		else if(state == SHOPOPTION)
		{
			if(curIndex() < 2) incIndex();
		}
		else if(state == SHOPBUY)
		{
			setIndex(curIndex()+10);
			if(curIndex() >= curEvent.inventory.length) setIndex(curEvent.inventory.length-1);
			
			cursorAlign += 10;
			if(cursorAlign > 14) cursorAlign = 14;
			if(cursorAlign > curEvent.inventory.length-1) cursorAlign = curEvent.inventory.length-1;
		}
		else if(state == SHOPBUY2)
		{
			incIndex();

			int maxAmt = 99;
			
			int maxBuy = Math.min(maxAmt,money/(curEvent.inventory[prevIndex(1)].price));
			
			int indexOf = -1;
			for(int i=0; i<inventory.size(); i++)
			{
				if(inventory.get(i).name.equals(curEvent.inventory[prevIndex(1)].name)) indexOf = i;
			}
			
			if(indexOf > -1) maxBuy = Math.min(maxBuy, maxAmt-inventory.get(indexOf).qty);
			
			if(curIndex() > maxBuy) setIndex(maxBuy);
			
			if(curIndex() < 1) setIndex(1);
		}
		else if(state == SHOPSELL)
		{
			setIndex(curIndex()+10);
			if(curIndex() >= inventory.size()) setIndex(inventory.size()-1);
			
			cursorAlign += 10;
			if(cursorAlign > 14) cursorAlign = 14;
			if(cursorAlign > inventory.size()-1) cursorAlign = inventory.size()-1;
		}
		else if(state == SHOPSELL2)
		{
			incIndex();
			if(curIndex() > inventory.get(prevIndex(1)).qty) setIndex(inventory.get(prevIndex(1)).qty);
		}
		else if(state == EDITPARTY || state == EDITPARTYSWITCH)
		{
			if(curIndex() < 3)
			{
				setIndex(3);
			}
			else if(curIndex() == 3 || curIndex() == 5 || curIndex() == 7)
			{
				incIndex();
			}
		}
		else if(state == SKILLMENUSEL)
		{
			if(curIndex() == 0)
			{
				setIndex(1);
			}
		}
		else if(state == STATUSMENUMOREINFO)
		{
			if(curIndex() == 4 || curIndex() == 5) setIndex(14);
			else if(curIndex() == 6) setIndex(16);
			else if(curIndex() == 7 || curIndex() == 8) setIndex(18);
			else if(curIndex() == 9 || curIndex() == 10) setIndex(20);
			else if(curIndex() == 11) setIndex(22);
			else if(curIndex() == 12) setIndex(24);
			else if(curIndex() == 13) setIndex(26);
			else if(curIndex() == 14 || curIndex() == 16 || curIndex() == 18 || curIndex() == 20 || curIndex() == 22 || curIndex() == 24) incIndex();
		}
		else if(state == ACTIVESKILLMENU)
		{
			int numActiveSkills = party[prevIndex(2)].getLearnedActiveSkills().size();
			
			setIndex(curIndex()+6);
			if(curIndex() >= numActiveSkills - 1) setIndex(numActiveSkills - 1);
			
			cursorAlign += 6;
			if(cursorAlign > 8) cursorAlign = 8;
			if(cursorAlign > numActiveSkills - 1) cursorAlign = numActiveSkills - 1;
		}
		else if(state == PASSIVESKILLMENU)
		{
			int numPassiveSkills = party[prevIndex(2)].getLearnedPassiveSkills().size();
			
			setIndex(curIndex()+6);
			if(curIndex() >= numPassiveSkills - 1) setIndex(numPassiveSkills - 1);
			
			cursorAlign += 6;
			if(cursorAlign > 8) cursorAlign = 8;
			if(cursorAlign > numPassiveSkills - 1) cursorAlign = numPassiveSkills - 1;
		}
		else if(state == INNCHOICE)
		{
			if(curIndex() == 0) setIndex(1);
		}
	}
	
	public void pressedLeft()
	{
		if(state == MAP && sliding == -1)
		{
			moveWest = true;
		}
		else if(state == STATUSMENU)
		{
			int prevPartyIndex = prevIndex(1);
			
			if(prevIndex(1) == 0)
			{
				if(party[2].id != Character.NONE) index.set(index.size()-2,2);
				else if(party[1].id != Character.NONE) index.set(index.size()-2,1);
			}
			else index.set(index.size()-2,prevIndex(1)-1);
			
			if(prevIndex(1) != prevPartyIndex) setIndex(0); //select skill 0 if party member changed
		}
		else if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			setIndex(curIndex() - 10);
			if(curIndex() < 0) setIndex(0);
			
			cursorAlign -= 10;
			if(cursorAlign < 0) cursorAlign = 0;
		}
		else if(state == INVENTORYSORT)
		{
			setIndex(curIndex()*-1 + 1);
		}
		else if(state == BATTLETARGET)
		{
			if(selectedAction.targetType == Action.ONEUNIT && curIndex() >= 3)
			{
				setIndex(topAlivePartyIndex());
			}
		}
		else if(state == SHOPOPTION)
		{
			if(curIndex() > 0) decIndex();
		}
		else if(state == SHOPBUY || state == SHOPSELL)
		{
			setIndex(curIndex() - 10);
			if(curIndex() < 0) setIndex(0);
			
			cursorAlign -= 10;
			
			if(cursorAlign < 0) cursorAlign = 0;
		}
		else if(state == SHOPBUY2 || state == SHOPSELL2)
		{
			decIndex();
			if(curIndex() < 1) setIndex(1);
		}
		else if(state == EDITPARTY || state == EDITPARTYSWITCH)
		{
			if(curIndex() == 3 || curIndex() == 5 || curIndex() == 7)
			{
				setIndex(0);
			}
			else if(curIndex() == 4 || curIndex() == 6 || curIndex() == 8)
			{
				decIndex();
			}
		}
		else if(state == SKILLMENUSEL)
		{
			if(curIndex() == 1)
			{
				setIndex(0);
			}
		}
		else if(state == STATUSMENUMOREINFO)
		{
			if(curIndex() == 14) setIndex(4);
			else if(curIndex() == 16) setIndex(6);
			else if(curIndex() == 18) setIndex(7);
			else if(curIndex() == 20) setIndex(10);
			else if(curIndex() == 22) setIndex(11);
			else if(curIndex() == 24) setIndex(12);
			else if(curIndex() == 26) setIndex(13);
			else if(curIndex() == 15 || curIndex() == 17 || curIndex() == 19 || curIndex() == 21 || curIndex() == 23 || curIndex() == 25) decIndex();
		}
		else if(state == ACTIVESKILLMENU || state == PASSIVESKILLMENU)
		{
			setIndex(curIndex() - 6);
			if(curIndex() < 0) setIndex(0);
			
			cursorAlign -= 6;
			if(cursorAlign < 0) cursorAlign = 0;
		}
		else if(state == INNCHOICE)
		{
			if(curIndex() == 1) setIndex(0);
		}
	}
	
	public void pressedSpace()
	{
		if(state == MAP)
		{
			setState(MAINMENU);
			addIndex(0);
			
			playSound("menuX");
		}
		else if(state == MAINMENU)
		{
			setState(MAP);
			
			removeIndex();
		}
		else if(state == STATUSMENU)
		{
			setState(STATUSMENUMOREINFO);
			
			addIndex(0);
		}
		else if(state == INVENTORY)
		{
			setState(INVENTORYREARRANGE);
			addIndex(curIndex());
		}
		else if(state == INVENTORYREARRANGE)
		{
			int index = curIndex();
			
			Item temp = inventory.get(prevIndex(1));
			inventory.set(prevIndex(1),inventory.get(curIndex()));
			inventory.set(curIndex(),temp);
			
			setState(INVENTORY);
			
			removeIndex();
			setIndex(index);
		}
	}
	
	public void pressedM()
	{
		if(state == MAINMENU)
		{
			if(mute)
			{
				mute = false;
				
				playMapSong(false);
				
				if(volume < -29) volume = -29;
				
				FloatControl fVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				fVolume.setValue(volume);
			}
			else
			{
				mute = true;
				
				clip.stop();
				clip.close();
			}
		}
	}

	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_UP)
		{
			moveNorth = false;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveEast = false;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moveSouth = false;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveWest = false;
		}
	}

	public void keyTyped(KeyEvent arg0)
	{
		
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
}