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
	
	/***
	 * Cross-System Compatibility
	 */
	public static String FileSeparator = "";
	public static String OS = null;
	
	/**
	 * Game states
	 */
	public static final int TITLESCREEN = 0;			//title screen
	public static final int CUTSCENE = 1;				//a cutscene (like new game intro)
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
	public static final int DATASELECT1 = 42;			//New Game or Continue
	public static final int DATASELECT2 = 43;			//Choose the save file
	public static final int CONFIRMOVERRIDE = 44;		//Confirm starting a new game over an existing file
	
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
	 * Cutscenes
	 */
	public static final int CUTSCENE_INTRO = 0;
	
	/**
	 * Statuses
	 */
	public static final int NUMSTATUSES = 20;
	public static final int STATUS_POISON = 0;
	public static final int STATUS_SILENCE = 1;
	public static final int STATUS_BLIND = 2;
	public static final int STATUS_SLEEP = 3;
	public static final int STATUS_PROTECT = 4;
	public static final int STATUS_SHELL = 5;
	public static final int STATUS_HASTE = 6;
	public static final int STATUS_SLOW = 7;
	public static final int STATUS_REGEN = 8;
	public static final int STATUS_BERSERK = 9;
	public static final int STATUS_DEFEND = 10;
	public static final int STATUS_ATKUP = 11;
	public static final int STATUS_EVADE = 12;
	public static final int STATUS_SHAME = 13;
	public static final int STATUS_AMP = 14;
	public static final int STATUS_DEATH = 15;
	public static final int STATUS_ATKDOWN = 16;
	public static final int STATUS_GOALKEEPER = 17;
	public static final int STATUS_OIL = 18;
	public static final int STATUS_VIRUS = 19;
	
	/**
	 * Elements
	 */
	public static final int NUMELEMENTS = 8;
	public static final int ELEMENT_NONE = -1;
	public static final int ELEMENT_SNACK = 0;
	public static final int ELEMENT_FIRE = 1;
	public static final int ELEMENT_LIGHTNING = 2;
	public static final int ELEMENT_WATER = 3;
	public static final int ELEMENT_EARTH = 4;
	public static final int ELEMENT_POISON = 5;
	public static final int ELEMENT_DARK = 6;
	public static final int ELEMENT_HOLY = 7;
	
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
	
	public static Data[] data;
	public static int dataIndex;
	
	public static Unit[] party;
	public Unit placeholderCharacter;
	public ArrayList<Monster> monsters;
	
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
	
	public int direction = SOUTH;
	
	static int stepsWithoutBattle; //used with "encounter threshold" - guarantee a certain number of steps without battle
	
	public static ImageIcon icon;
	public static Image img;
	
	//cached images
	public static Image tileImage[];
	public static Image thingImage[];
	public static ArrayList<Image[]> movingTileImage;
	public static ArrayList<Image[]> movingThingImage;
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
	static boolean mute = true; //TODO: false

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

	static long baseTime = System.currentTimeMillis();
	static long lastPaintTime = System.currentTimeMillis();
	
	static boolean walkCycling = false;
	
	static boolean inputDisabled = false;
	
	static int frameCount; //25 frames per second (arbitrary)
	static int gameTime;
	static boolean dataLoaded; //don't want to do certain things in gameLoop until the game is loaded

	static long lastBonk = 0; //don't spam the bonk sound
	static int sliding = -1; //determines if you're sliding on ice
	
	public boolean saveOverride = true; //TODO: un-set this
	
	public int cutsceneID;
	public int cutsceneState;
	public int cutsceneNumStates;
	public int cutsceneFramesPerState;
	public int cutsceneCurrentFramesForState;
	public int cutsceneNextState;

	public void init()
	{
		//setDoubleBuffered(true); Apparently this never gets called? Moved double buffering to Game constructor
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
	                }
	                
	                if(state == CUTSCENE)
	                {
	                	cutsceneCurrentFramesForState++;
	                	                		
                		if(cutsceneCurrentFramesForState > cutsceneFramesPerState)
                		{
                			cutsceneCurrentFramesForState = 0;
                			cutsceneState++;
                			
                			if(cutsceneState == cutsceneNumStates) //cutscene over, go to next state
                			{
                				setState(cutsceneNextState);
                				
                				if(cutsceneNextState == MAP)
                				{
                					playMapSong(false);
                				}
                			}
                		}
	                }
	                
	                if(dataLoaded)
	                {
	                	try
	                	{
		                	frameCount++;
			                if(frameCount % 25 == 0) //1000 milliseconds have passed
			                {
			                	if(gameTime < 359999) //max game time is 99:59:59
			                	{
			                		gameTime++; //another second passed
			                	}
			                }
			                
			                updateTileAndThingTimers();
	                	}
	                	catch(Exception e) //if frameCount somehow gets too large... should be impossible, would take 2.7 years, but just to be safe
	                	{
	                		frameCount = 0;
	                	}
	                	
	                	repaint();
	                }
	            }
	        }
	    }.start();
	}
	
	public void updateTileAndThingTimers()
	{
		for(int i=-9; i<10; i++)
		{
			for(int j=-7; j<8; j++)
			{
				if(curX+i >= 0 && curY+j >= 0 && curX+i < curMap.tile.length && curY+j < curMap.tile[0].length)
				{
					if(Tile.hasOwnTimer(curMap.tile[curX+i][curY+j].type))
					{
						curMap.tile[curX+i][curY+j].timer++;
					}
					
					if(Thing.hasOwnTimer(curMap.tile[curX+i][curY+j].thing.type))
					{
						curMap.tile[curX+i][curY+j].thing.timer++;
					}
				}
			}
		}
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
		case CUTSCENE: return "Cutscene";
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
		
		setDoubleBuffered(true);
		
		checkOS(); //set OS and FileSeparator before doing any File I/O
		
		loadData();
		
		setState(TITLESCREEN);
		
		party = new Unit[8]; //6 party members, 2 extra space in case 1 in party and 5 in "storage"
		for(int i=0; i<party.length; i++)
		{
			party[i] = new None(i);
		}
		
		afterBattleAlerts = new ArrayList<AfterBattleAlert>();
		
		monsters = new ArrayList<Monster>();
		
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
				tileImage[i] = null;
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
				thingImage[i] = null;
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
		
		playSong("Nice");
		
		gameLoop();
	}
	
	public void startCutscene(int cutscene)
	{
		setState(CUTSCENE);
		
		cutsceneID = cutscene;
		cutsceneState = 0;
		cutsceneCurrentFramesForState = 0;
		
		if(cutscene == CUTSCENE_INTRO) //new game
		{
			cutsceneNumStates = 4;
			cutsceneFramesPerState = 25 * 8; //8 seconds per frame
			cutsceneNextState = MAP;
		}
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
	
	public int getTotalExpGain(Unit unit)
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
		
		if(unit.hasEquippedPassiveSkill(PassiveSkill.EXPBOOST))
		{
			totalExp *= 1.5;
		}
		
		return totalExp;
	}
	
	public int getTotalSPGain(Unit unit)
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
		
		if(unit.hasEquippedPassiveSkill(PassiveSkill.SPBOOST))
		{
			actualTotalSP *= 2;
		}
		
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
		
		if(partyHasEquippedPassiveSkill(PassiveSkill.PAYDAY))
		{
			totalMoney *= 1.5;
		}
		
		return totalMoney;
	}
	
	public int[] getLoopPoints(String name)
	{
		int[] loopPoints = new int[2];
		
		//TODO: when the music exists, add the loop points here
		
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
	
	public void playMapSong(boolean useSavedFrame)
	{
		if(!mute)
		{
			if(curMap.tile[curX][curY].music != null)
			{
				playSong(curMap.tile[curX][curY].music); 
			}
			else playSong(curMap.song);
			
			if(useSavedFrame)
			{
				clip.setFramePosition(songFrame);
			}
		}
	}
	
	public void playSong(String song)
	{
		playSong(song, false);
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
	
	public void playSong(String song, boolean saveClipPosition)
	{
		currentSong = song;
		
		if(!mute)
		{
			try
			{
				int[] mapSongLoopPoints = getLoopPoints(curMap.song);
				int[] songLoopPoints = getLoopPoints(song);
				
				if(saveClipPosition)
				{
					songFrame = clip.getFramePosition();
					if(songFrame >= mapSongLoopPoints[1]) songFrame = (songFrame-mapSongLoopPoints[0])%(mapSongLoopPoints[1]-mapSongLoopPoints[0])+mapSongLoopPoints[0];
				}
				
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
				
				clip.setLoopPoints(songLoopPoints[0], songLoopPoints[1]);
				
				gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
				
				playRunnable();
			}
			catch(Exception e)
			{
				System.out.println("Error playing song " + song + ": " + e.getMessage());
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
				System.out.println("Error playing sound " + name + ": " + e.getMessage());
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
		
		if(!prevSong.equals(newSong))
		{
			playSong(newSong, false);
		}
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
			drawViewArt(g);
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
		else if(state == DATASELECT1 || state == DATASELECT2 || state == CONFIRMOVERRIDE)
		{
			drawDataSelect(g);
		}
		else if(state == CUTSCENE)
		{
			drawCutscene(g);
		}
	}
	
	public void drawCutscene(Graphics g)
	{
		draw("scene" + cutsceneID + "_" + cutsceneState,"cutscene",0,0,g);
	}
	
	public void drawTitleScreen(Graphics g)
	{
		draw("titleScreen",0,0,g);
	}
	
	public void drawDataSelect(Graphics g)
	{
		g.setColor(darkGray);
		g.fillRect(0,0,850,650);
		
		g.setColor(Color.BLACK);
		g.fillRect(100,20,650,45);
		g.setColor(menuBlue);
		g.fillRect(102,22,646,41);
		
		g.setColor(Color.BLACK);
		g.setFont(arialBold20);
		g.drawString("New Game",170,50);
		g.drawString("Continue",560,50);
		
		for(int i=0; i<3; i++)
		{
			g.setColor(Color.BLACK);
			g.fillRect(100,70+183*i,650,185);
			g.setColor(menuBlue);
			g.fillRect(102,72+183*i,646,181);
			
			if(data[i].dataExists)
			{
				for(int j=0; j<3; j++)
				{
					if(data[i].party[j].id != Character.NONE)
					{
						g.setColor(Color.BLACK);
						g.fillRect(108+160*j,78+183*i,154,154);
						draw("port" + data[i].party[j].name,"port",110+160*j,80+183*i,g);
						
						g.setFont(arialBold12);
						g.drawString(data[i].party[j].name,125+160*j,245+183*i);
						drawStringRightAligned("Lv " + data[i].party[j].level,245+160*j,245+183*i,g);
					}
				}
				
				g.setFont(arialBold14);
				g.drawString("Money",595,120+183*i);
				drawStringRightAligned(""+moneyFormat(data[i].money),730,120+183*i,g);
				g.drawString("Play time",595,180+183*i);
				drawStringRightAligned(gameTimeString(data[i].gameTime),730,180+183*i,g);
			}
			else
			{
				g.setColor(Color.BLACK);
				g.setFont(arialBold16);
				g.drawString("Empty",400,180+183*i);
			}
		}
		
		if(state == DATASELECT1) //Continue or New Game?
		{
			draw("pointer","icon",130+390*curIndex(),30,g);
		}
		else if(state == DATASELECT2)
		{
			draw("pointerSel","icon",130+390*prevIndex(1),30,g);
			draw("pointer","icon",80,140+183*curIndex(),g);
		}
		else if(state == CONFIRMOVERRIDE)
		{
			draw("pointerSel","icon",130+390*prevIndex(2),30,g);
			draw("pointerSel","icon",80,140+183*prevIndex(1),g);
			
			g.setColor(Color.BLACK);
			g.fillRect(200,300,450,80);
			g.setColor(Color.GRAY);
			g.fillRect(202,302,446,76);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold14);
			g.drawString("When the new game is saved, existing",215,330);
			g.drawString("data will be overridden. Continue?",215,355);
			g.drawString("No",580,330);
			g.drawString("Yes",580,355);
			
			draw("pointer","icon",542,315+25*curIndex(),g);
		}
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
				draw(curEvent.inventory[i].getIconString(),"icon",170,90+32*(i-top),g);
				
				g.setFont(arialBold14);
				g.setColor(Color.BLACK);
				
				drawStringRightAligned(moneyFormat(curEvent.inventory[i].price),515,110+32*(i-top),g);
			}
			
			if(state == SHOPBUY) draw("pointer","icon",140,86+32*(itemIndex-top),g);
			else draw("pointerSel","icon",140,86+32*(itemIndex-top),g);
			
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
				
				draw("leftArrow","icon",650,330,g);
				draw("rightArrow","icon",700,330,g);
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
				draw(inventory.get(i).getIconString(),"icon",150,90+32*(i-top),g);
				
				g.setFont(arialBold14);
				g.setColor(Color.BLACK);
				
				if(inventory.get(i).price/2 > 0) drawStringRightAligned(moneyFormat(inventory.get(i).price/2),510,110+32*(i-top),g);
				drawStringRightAligned(""+inventory.get(i).qty,535,110+32*(i-top),g);
			}
			
			if(state == SHOPSELL) draw("pointer","icon",120,86+32*(itemIndex-top),g);
			else draw("pointerSel","icon",120,86+32*(itemIndex-top),g);
			
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
				
				draw("leftArrow","icon",650,330,g);
				draw("rightArrow","icon",700,330,g);
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
				else if(party[i].id == Character.HANK)
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
			draw("iconActive","icon",boxLeft+20,yPos-20,g);
			g.drawString(equip.activeSkills.get(i).action.name,boxLeft+45,yPos);
			yPos += 25;
		}
		for(int i=0; i<numPassiveSkills; i++)
		{
			draw("iconPassive","icon",boxLeft+20,yPos-20,g);
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
			if(battleAction.action.id == Action.KAGESHADOWS)
			{
				draw("battleBackground_Kage","battleBackground",0,0,g);
				return;
			}
		}
		
		int backgroundID = curTile().type;
		
		draw("battleBackground" + "_" + backgroundID,"battleBackground",0,0,g);
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
			
			if(partyHasEquippedPassiveSkill(PassiveSkill.SCAN))
			{
				g.setFont(arialBold12);
				g.setColor(Color.BLACK);
				drawStringRightAligned(""+monsters.get(i).hp,700,548+27*i,g);
				
				double hpBarLength = 100.0*((double)monsters.get(i).hp/monsters.get(i).maxHp);
				if(hpBarLength < 1) hpBarLength = 1;
				drawBar(710,540+27*i,104,10,(int)hpBarLength,healthColor(monsters.get(i)),g);
			}
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
				
				if(prevState == BATTLEFLED && party[i].hp > 0)
				{
					draw("port" + party[i].name + "Fled","port",x,70,g);
				}
				else draw("port" + party[i].name,"port",x,70,g);
				
				if(party[i].hp == 0) draw("dead","port",x,70,g);
				
				g.setFont(arialBold18);
				g.setColor(Color.BLACK);
				width = getStringWidth(party[i].name + " - Lv " + party[i].level,g);
				g.drawString(party[i].name + " - Lv " + party[i].level,x+75-width/2,240);
				
				if(prevState != BATTLEFLED)
				{
					if(party[i].hp > 0)
					{
						g.setFont(arialBold14);
						message = "Gained " + getTotalExpGain(party[i]) + " exp.";
						width = getStringWidth(message,g);
						g.drawString(message,x+75-width/2,270);
						
						message = "Gained " + getTotalSPGain(party[i]) + " SP.";
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
				draw(itemDrops.get(i).getIconString(),"icon",455,440+30*i,g);
				g.drawString("Found " + itemDrops.get(i).name + " (" + itemDrops.get(i).qty + ").",485,460+30*i);
			}
		}
	}
	
	public void drawGameOver(Graphics g)
	{
		draw("gameOver",0,0,g);
	}
	
	public int drawDamageString(String displayString, int x, int y, Graphics g)
	{
		int damageFrame = Action.actionFromID(battleAction.action.id).damageFrame;
		if(animationID >= damageFrame && animationID < damageFrame+19)
		{
			y += getDamageStringOffset(animationID-damageFrame);
			g.drawString(displayString,x,y);
			
			return y;
		}
		
		return -1;
	}
	
	public void drawBattleEffect(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(arialBold12);
		
		if(battleAction == null) return; //race condition?
		
		if(battleAction.action.id == Action.FLEE) //no targets/damages/effects to show
		{
			String battleMessage = battleAction.user.displayName() + " attempts to flee like a coward, but fails.";
			
			drawBattleMessage(battleMessage,g);
			
			return;
		}
		
		for(int i=0; i<battleAction.targets.size(); i++)
		{
			int[] coord;
			int coordXOffset;
			
			boolean targetIsMonster = false;
			if(battleAction.targets.get(i) < 3)
			{
				coord = getPlayerCoordinates(battleAction.targets.get(i));
				coordXOffset = getUnitFromTargetIndex(battleAction.targets.get(i)).imageWidth + 20;
			}
			else
			{
				coord = getMonsterCoordinates(battleAction.targets.get(i) - 3);
				coordXOffset = -20;
				
				targetIsMonster = true;
			}
			
			ArrayList<Integer> valuesToDisplay = new ArrayList<Integer>(); //this is really dumb, but I don't know a better way to handle it
			valuesToDisplay.add(i);
			if(battleAction.values.size() == battleAction.targets.size()*2) valuesToDisplay.add(i+battleAction.values.size()/2);
			
			/**
			 * Drawing damage/healing/MP
			 */
			//TODO: damage currently overlaps a bit with animations and status icons, so shift things around
			
			g.setFont(arialBold20);
			for(int j=0; j<valuesToDisplay.size(); j++)
			{
				if(battleAction.values.size() > 0)
				{
					int index = valuesToDisplay.get(j);
					int value = battleAction.values.get(index);
					String displayString;
					
					if(battleAction.action.id == Action.MURDER)
					{
						g.setColor(Color.WHITE);
						
						if(value == -1) //immune
						{
							displayString = "Immune";
							
							if(targetIsMonster)
							{
								coordXOffset -= 80;
							}
						}
						else if(value == 0)
						{
							displayString = "Miss";
							
							if(targetIsMonster)
							{
								coordXOffset -= 60;
							}
						}
						else
						{
							g.setColor(Color.RED);
							displayString = "Murder";
							
							if(targetIsMonster)
							{
								coordXOffset -= 80;
							}
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
						
						if(battleAction.mp.get(index) == true) g.setColor(mpBlue);
						else if(value >= 0) g.setColor(Color.WHITE);
						else g.setColor(hpGreen);
						
						if(value < 0)
						{
							displayString = ""+value*-1;
						}
						else if(value > 0)
						{
							displayString = damageDisplay;
						}
						else if(miss)
						{
							displayString = "Miss";
							
							if(targetIsMonster)
							{
								coordXOffset -= 60;
							}
						}
						else
						{
							displayString = "Immune";
							
							if(targetIsMonster)
							{
								coordXOffset -= 80;
							}
						}
					}
					
					int yOffset = 0;
					if(valuesToDisplay.size() == 2 && j == 0) yOffset = -50;
					int y = drawDamageString(displayString,coord[0]+coordXOffset,coord[1]+100+yOffset,g);
					
					if(battleAction.crit.size() > index) //crit array may be empty
					{
						if(battleAction.crit.get(index))
						{
							g.setColor(Color.ORANGE);
							if(targetIsMonster)
							{
								g.drawString("Critical!",coord[0]+coordXOffset-50,y-30);
							}
							else
							{
								g.drawString("Critical!",coord[0]+coordXOffset,y-30);
							}
						}
					}
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
			
			//if(animationID > Action.actionFromID(battleAction.action.id).numFrames-2) animationID = Action.actionFromID(battleAction.action.id).numFrames-2; //race condition in animationLoop?
			int animationState = Action.animationState(battleAction.action.id, animationID);
			int xOffset = Action.xOffsetState(battleAction.action.id, animationID);
			int yOffset = Action.yOffsetState(battleAction.action.id, animationID);
			
			//adjust for image height/width
			int actionImageWidth = Action.actionFromID(battleAction.action.id).imageWidth;
			if(actionImageWidth == 0) actionImageWidth = 125;
			int actionImageHeight = Action.actionFromID(battleAction.action.id).imageHeight;
			if(actionImageHeight == 0) actionImageHeight = 125;
			xOffset += (getUnitFromTargetIndex(battleAction.targets.get(i)).imageWidth-actionImageWidth)/2;
			yOffset += (getUnitFromTargetIndex(battleAction.targets.get(i)).imageHeight-actionImageHeight)/2;
			
			g.setFont(arialBold16);
			
			/**
			 * Attack
			 */
			if(battleAction.action.id == Action.ATTACK)
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
				
				draw(attackAnimation + "_" + animationState,"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(battleAction.action.id == Action.HPITEM || battleAction.action.id == Action.MPITEM || battleAction.action.id == Action.REVIVEITEM)
			{
				Item usedItem = battleAction.item;
				
				draw("item" + usedItem.id + "_" + animationState,"battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			else if(!Action.actionFromID(battleAction.action.id).animationPrefix.equals(""))
			{
				draw(Action.actionFromID(battleAction.action.id).animationPrefix + "_" + animationState, "battleEffect",coord[0]+xOffset,coord[1]+yOffset,g);
			}
			
			if(battleAction.action.id == Action.STEAL)
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
			else if(battleAction.action.id >= 10) //skills are 10+, don't show a message for stuff like attack/poison/flee
			{
				battleMessage = Action.actionFromID(battleAction.action.id).name;
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
		return calculateDamage(attack,action,target,crit,miss,ELEMENT_NONE);
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
		
		if(action == Action.POISON)
		{
			double ddmg = attack.maxHp * 0.05;
			ddmg = randomize(ddmg,85,115); //randomize
			
			value = (int) ddmg;
			if(value < 1) value = 1;
			
			return value;
		}
		
		Action actionObj = Action.actionFromID(action);
		
		int hitRateMod = actionObj.hitRateMod; //TODO: figure out how high these hit rate boosts should be
		
		if(hitRateMod != 999) //999 = never miss
		{
			int hitRate = Unit.getHitRate(attack,target,actionObj.hitRateMod);
			if(rand.nextInt(100) >= hitRate)
			{
				miss.clear();
				miss.add(true);
				return 0; //miss
			}
		}
		
		if(actionObj.element != ELEMENT_NONE) //TODO: for ghosts, can return 0 if the attack is physical
		{
			if(target.elementResistance[actionObj.element] == 100) return 0;
		}
		
		double ddmg = actionObj.calculateDamage(attack, target);
		ddmg = randomize(ddmg,attack.getDmgLowerBound(),100);
		
		if(actionObj.damageType == Action.PHYSICAL)
		{
			if(attack.status[STATUS_BERSERK] > 0)
			{
				ddmg *= 1.25;
			}
			
			if(attack.status[STATUS_ATKUP] > 0)
			{
				ddmg *= 1.5;
			}
			
			if(attack.status[STATUS_ATKDOWN] > 0)
			{
				ddmg *= 0.5;
			}
			
			if(target.status[STATUS_PROTECT] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[STATUS_DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
		}
		else if(actionObj.damageType == Action.MAGICAL)
		{
			if(target.status[STATUS_SHELL] > 0)
			{
				ddmg /= 2.0;
			}
			
			if(target.status[STATUS_DEFEND] == 1)
			{
				ddmg /= 2.0;
			}
		}
		
		if(action == Action.ATTACK)
		{
			if(attack.hasEquippedPassiveSkill(PassiveSkill.MPATTACK))
			{
				int mpCost = 3 + (int)(attack.maxMp/20.0); //TODO: work on both of these values
				double mod = 1.5;
				
				if(attack.mp < mpCost)
				{
					mod = 1.0 + 0.5*((double)attack.mp / (double)mpCost);
				}
							
				ddmg *= mod;
			}
			
			if(rand.nextInt(100) < attack.critRate)
			{
				ddmg *= 2.0;
				crit.clear();
				crit.add(true);
			}
		}
		else if(action == Action.SAMURAISLASH)
		{
			int halfChance = 40 + attack.dex;
			if(rand.nextInt(100) < halfChance) //TODO: work on this forumla, some monsters should be immune, etc.
			{
				if(target.hp/2 > ddmg) ddmg = target.hp/2;
			}
		}
		
		if(attack.hasEquippedPassiveSkill(PassiveSkill.SOLOARTIST))
		{
			boolean isOnlyPartyMember = true;
			
			if(party != null)
			{
				for(int i=0; i<3; i++)
				{
					if(i != attack.index && party[i].existsAndAlive())
					{
						isOnlyPartyMember = false;
					}
				}
			}
			
			if(isOnlyPartyMember)
			{
				ddmg *= 1.75; //TODO: adjust this if necessary
			}
		}
		
		//TODO: for stuff like Destroy Humans, check the "enemy type" here and increase damage.
		//Should have a passive skill for most enemy types. Right now enemy types don't exist.
		
		if(target.hasEquippedPassiveSkill(PassiveSkill.RNGDEFENSES))
		{
			double dmgMod = (rand.nextInt(100) + 50)/100.0; //random between 0.5 and 1.5
			
			ddmg *= dmgMod;
		}
		
		if(ddmg < 1)
		{
			ddmg = 1;
		}
		
		/*
		 * Handle elements last since we need to know beforehand if the value was negative, positive, etc.
		 */
		
		if(actionObj.element != ELEMENT_NONE) //attack is 100% one element
		{
			ddmg -= ddmg*(target.elementResistance[actionObj.element]/100.0);
			
			if(actionObj.element == ELEMENT_FIRE && target.status[STATUS_OIL] > 0)
			{
				ddmg *= 2.0;
			}
		}
		else if(actionObj.attacksWithWeapon) //may need to account for partial elemental attacks
		{
			int percentElemental = 20 + 3*attack.dex; //TODO: work on this formula
			int oilMultiplier = 0;
			
			if(attack.hasEquippedPassiveSkill(PassiveSkill.SNACKELEMENTATTACK))
			{				
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_SNACK]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.EARTHELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_EARTH]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.WATERELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_WATER]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.FIREELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_FIRE]) + ddmg*(1.0 - percentElemental/100.0);
				oilMultiplier = percentElemental;
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.POISONELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_POISON]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.LIGHTNINGELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_LIGHTNING]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.DARKELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_DARK]) + ddmg*(1.0 - percentElemental/100.0);
			}
			else if(attack.hasEquippedPassiveSkill(PassiveSkill.HOLYELEMENTATTACK))
			{
				ddmg = ddmg*(percentElemental/100.0)*(1.0 - target.elementResistance[ELEMENT_HOLY]) + ddmg*(1.0 - percentElemental/100.0);
			}
			
			//TODO: would also want to handle elemental weapons (if they exist)
			//could impact what percentage of the attack is Fire elemental in the case of Oil, so modify oilMultiplier
			
			if(oilMultiplier != 0)
			{
				ddmg = ddmg*(1.0 + 1.0*(percentElemental/100.0));
			}
		}
		
		if(ddmg < 0 && ddmg > -1) ddmg = -1;
		else if(ddmg >= 0 && ddmg < 1) ddmg = 1;
		
		value = (int) ddmg;
		
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
			if(battleAction.user.unitType == Unit.CHARACTER && battleAction.action.id != Action.POISON && battleAction.action.id != Action.REGEN)
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
			if(battleAction.user.unitType == Unit.MONSTER && battleAction.action.id != Action.POISON && battleAction.action.id != Action.REGEN)
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
		//Note: using just ifs rather than if/else since we want to consider the ALLUNITS case (add both enemies and allies)
		
		int targetType = selectedAction.targetType;
		
		if(targetType == Action.ONEENEMY || (targetType == Action.ONEUNIT && curIndex() >= 3))
		{
			int index;
			if(targetType == Action.ONEENEMY) index = curIndex();
			else index = curIndex() - 3;
			
			int[] coord = getMonsterCoordinates(index);
			coord[0] -= 50;
			coord[1] += monsters.get(index).imageHeight/2;
			
			draw("pointer","icon",coord[0],coord[1],g);
		}
		
		if(targetType == Action.ALLENEMIES || targetType == Action.ALLUNITS)
		{
			for(int i=0; i<monsters.size(); i++)
			{
				if(monsters.get(i).hp > 0)
				{
					int[] coord = getMonsterCoordinates(i);
					coord[0] -= 50;
					coord[1] += monsters.get(i).imageHeight/2;
					
					draw("pointer","icon",coord[0],coord[1],g);
				}
			}
		}
		
		if(targetType == Action.ONEALLY || targetType == Action.SELF || (targetType == Action.ONEUNIT && curIndex() < 3))
		{
			int[] coord = getPlayerCoordinates(curIndex());
			coord[0] += -40;
			coord[1] += 75;
			
			draw("pointer","icon",coord[0],coord[1],g);
			
		}
		
		if(targetType == Action.ALLALLIES || targetType == Action.ALLUNITS)
		{
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE && party[i].hp > 0)
				{
					int[] coord = getPlayerCoordinates(i);
					coord[0] += - 40;
					coord[1] += 75;
					
					draw("pointer","icon",coord[0],coord[1],g);
				}
			}
		}
	}
	
	public void setState(int setState)
	{
		prevState = state;
		state = setState;
		
		if(state == BATTLEWON)
		{
			playSong("Victory");
		}
		else if(state == BATTLELOST)
		{
			playSong("Fancy Feast Funeral");
		}
	}
	
	public boolean preventFlee()
	{
		if(this.monsters == null)
		{
			return false;
		}
		
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).preventFlee)
			{
				return true;
			}
		}
		
		return false;
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
			g.setFont(arialBold16);
			g.drawString("Attack",330,550);
			
			if(currentUnit.getLearnedActiveSkills().size() > 0)
			{
				g.setColor(Color.WHITE);
			}
			else
			{
				g.setColor(Color.GRAY);
			}
			g.drawString(currentUnit.getSkillString(),330,577);
			
			if(inventory.size() > 0)
			{
				g.setColor(Color.WHITE);
			}
			else
			{
				g.setColor(Color.GRAY);
			}
			g.drawString("Item",330,604);
			
			if(!preventFlee())
			{
				g.setColor(Color.WHITE);
			}
			else
			{
				g.setColor(Color.GRAY);
			}
			g.drawString("Flee",330,631);
			
			draw("pointer","icon",290,530+27*choiceIndex,g);
		}
		else if(state == BATTLESELECTSKILL || (state == BATTLETARGET && prevState == BATTLESELECTSKILL))
		{
			int numActiveSkills = currentUnit.getLearnedActiveSkills().size();
			
			int top = Math.max(0,choiceIndex-cursorAlign);
			int bottom = Math.min(top+3,numActiveSkills-1);
			
			for(int i=top; i<=bottom; i++)
			{
				if(currentUnit.canUseAction(currentUnit.getLearnedActiveSkills().get(i).action)) g.setColor(Color.WHITE);
				else g.setColor(Color.GRAY);
				g.setFont(arialBold16);
				
				g.drawString(currentUnit.getLearnedActiveSkills().get(i).action.name,330,550+27*(i-top));
				drawStringRightAligned(currentUnit.getLearnedActiveSkills().get(i).action.getMPCost(currentUnit) + " MP",535,550+27*(i-top),g);
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
				draw("pointerSel","icon",290,530+27*cursorAlign,g);
			}
			else
			{
				draw("pointer","icon",290,530+27*cursorAlign,g);
			}
		}
		else if(state == BATTLESELECTITEM || (state == BATTLETARGET && prevState == BATTLESELECTITEM))
		{
			int numItems = inventory.size();
			
			int top = Math.max(0,choiceIndex-cursorAlign);
			int bottom = Math.min(top+3,numItems-1);
			
			g.setColor(Color.WHITE);
			g.setFont(arialBold16);
			
			for(int i=top; i<=bottom; i++)
			{
				draw(inventory.get(i).getIconString(),"icon",330,530+27*(i-top),g);
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
				draw("pointerSel","icon",290,530+27*cursorAlign,g);
			}
			else
			{
				draw("pointer","icon",290,530+27*cursorAlign,g);
			}
		}
		
		draw("battleCurrentUnit","icon",x+35,y-20,g);
	}
	
	public void drawBattleCharacter(Unit character, int animation, Graphics g)
	{
		if(animation == NORMAL && character.status[STATUS_SLEEP] > 0)
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
		else if(character.status[STATUS_DEFEND] > 0) //defend animation should override others (like damaged)
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
		if(animation == NORMAL && monster.status[STATUS_SLEEP] > 0)
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
			if(i == STATUS_DEFEND) continue; //don't draw an icon for Defend
			
			if(unit.status[i] != 0) //also need to consider negatives for "flags"
			{
				statusList.add(i);
			}
		}
		
		for(int i=0; i<statusList.size(); i++)
		{
			draw("iconStatus" + statusList.get(i),"icon",x+100+25*(i%2),y+25*(i/2),g);
		}
	}
	
	public void drawMonsterStatusIcons(Unit unit, int x, int y, Graphics g)
	{
		ArrayList<Integer> statusList = new ArrayList<Integer>();
		for(int i=0; i<unit.status.length; i++)
		{
			if(i == STATUS_DEFEND) continue; //don't draw an icon for Defend
			
			if(unit.status[i] != 0)
			{
				statusList.add(i);
			}
		}
		
		for(int i=0; i<statusList.size(); i++)
		{
			draw("iconStatus" + statusList.get(i),"icon",x-25-25*(i%2),y+25*(i/2),g);
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
			g.fillRect(240,90+32*(i-top),350,25);
			g.setColor(lightGray);
			g.fillRect(241,91+32*(i-top),348,23);
			g.setColor(Color.BLACK);
			g.setFont(arialBold18);
			g.drawString(inventory.get(i).name,280,110+32*(i-top));
			draw(inventory.get(i).getIconString(),"icon",240,90+32*(i-top),g);
			
			g.setFont(arialBold14);
			g.setColor(Color.BLACK);
			drawStringRightAligned(""+inventory.get(i).qty,585,108+32*(i-top),g);
			
			if(state == INVENTORYREARRANGE && i == prevIndex(1))
			{
				draw("pointerSel","icon",210,90+32*(i-top),g);
			}
		}
		
		if(state == INVENTORY || state == INVENTORYREARRANGE)
		{
			draw("pointer","icon",210,90+32*cursorAlign,g);
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
			draw("pointer","icon",200+curIndex()%2*240,21,g);
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
		if((curEvent.type == Event.NPC && (curEvent.name.equals("") || curEvent.name.equals("Sign"))) || curEvent.type == Event.CHEST) //Observation, Sign, Chest
		{
			draw("observationBox",350,115,g);
			
			if(curIndex() < curEvent.getDialogue(curEvent.state).size()-1)
			{
				draw("npcNextDialogue","icon",595,205,g);
			}
			
			g.setColor(Color.BLACK);
			g.fillRect(370,130,225,75);
			g.setColor(Color.WHITE);
			g.fillRect(372,132,221,71);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold12);
			if(curEvent.type == Event.CHEST)
			{
				drawCenteredString(curEvent.getDialogue(0).get(curIndex())[1],482,170,g);
			}
			else
			{
				g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[0],380,150);
				g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[1],380,170);
				g.drawString(curEvent.getDialogue(curEvent.state).get(curIndex())[2],380,190);
			}
		}
		else
		{
			draw("dialogueBox",350,100,g);
			
			if(curEvent.type == Event.NPC && curIndex() < curEvent.getDialogue(curEvent.state).size()-1)
			{
				draw("npcNextDialogue","icon",705,225,g);
			}
			
			g.setColor(menuBlue);
			g.fillRect(360,115,110,110);
			
			draw("npcPort" + curEvent.imageName,"event",365,120,g);	
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString(curEvent.name,485,130);
			
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
	}
	
	public void drawInnChoice(Graphics g)
	{
		draw("dialogueBox",350,100,g);
		
		g.setColor(menuBlue);
		g.fillRect(360,115,110,110);
		
		try
		{
			draw("npcPort" + curEvent.imageName,"event",365,120,g);	
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
		
		draw("pointer","icon",490+100*curIndex(),190,g);
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
				draw("port" + party[i].name,"port",42,69+180*i,g);
				if(party[i].hp == 0) draw("dead","port",42,69+180*i,g);
				
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
					draw("port" + party[i].name,"port",472+175*xPos,69+180*yPos,g);
					if(party[i].hp == 0) draw("dead","port",472+175*xPos,69+180*yPos,g);
				}
			}
		}
		
		if(state == EDITPARTYSWITCH)
		{
			if(prevIndex(1) < 3)
			{
				draw("pointerSel","icon",15,120+180*prevIndex(1),g);
			}
			else
			{
				int xPos = (prevIndex(1)+1)%2;
				int yPos = (prevIndex(1)-3)/2;
				
				draw("pointerSel","icon",445+175*xPos,120+180*yPos,g);
			}
		}
		
		if(curIndex() < 3)
		{
			draw("pointer","icon",15,120+180*curIndex(),g);
		}
		else
		{
			int xPos = (curIndex()+1)%2;
			int yPos = (curIndex()-3)/2;
			
			draw("pointer","icon",445+175*xPos,120+180*yPos,g);
		}
	}
	
	public void drawViewArt(Graphics g)
	{
		drawMap(g);
		
		//normally we'd use the draw() function, but we need to get the image's width and height
		icon = new ImageIcon(getClass().getResource("img/art/" + curEvent.name + ".PNG"));
		img = icon.getImage();
		
		int imageWidth = img.getWidth(null);
		int imageHeight = img.getHeight(null);
		
		g.setColor(Color.BLACK);
		g.fillRect(425-imageWidth/2-10,275-imageHeight/2-10,imageWidth+20,imageHeight+20);
		g.setColor(Color.GRAY);
		g.fillRect(425-imageWidth/2-7,275-imageHeight/2-7,imageWidth+14,imageHeight+14);
		g.setColor(Color.BLACK);
		g.fillRect(425-imageWidth/2-4,275-imageHeight/2-4,imageWidth+8,imageHeight+8);
		
		g.drawImage(img,425-imageWidth/2,275-imageHeight/2,this);
		
		g.fillRect(425-imageWidth/2-10,275+imageHeight/2+15,imageWidth+20,40);
		g.setColor(Color.GRAY);
		g.fillRect(425-imageWidth/2-7,275+imageHeight/2+18,imageWidth+14,34);
		
		if(!curEvent.caption.equals(""))
		{
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			drawCenteredString(curEvent.caption,425,275+imageHeight/2+40,g);
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
				draw("port" + party[i].name,"port",42,69+180*i,g);
				if(party[i].hp == 0) draw("dead","port",42,69+180*i,g);
				
				int statusIndex = 0;
				for(int j=0; j<party[i].status.length; j++)
				{
					if(party[i].status[j] > 0)
					{
						draw("iconStatus" + j,"icon",480-25*statusIndex,70+180*i,g);
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
			
			if(state == MAINMENU) draw("pointer","icon",625,60+56*curIndex(),g);
			else if(state == GAMESAVED) draw("pointerSel","icon",625,60+56*6,g);
			else
			{
				draw("pointerSel","icon",625,60+56*prevIndex(1),g);
				draw("pointer","icon",15,120+180*curIndex(),g);
			}
			
			g.setColor(menuDarkGreen);
			g.fillRect(605,490,230,130);
			g.setColor(menuLightBlue);
			g.fillRect(615,500,210,110);
			
			g.setColor(Color.BLACK);
			g.setFont(arialBold16);
			g.drawString(curMap.name,625,528);
			g.drawString("Money",625,563);
			drawStringRightAligned(""+moneyFormat(money),800,563,g);
			g.drawString("Play time",625,593);
			drawStringRightAligned(gameTimeString(gameTime),800,593,g);
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
			
			draw(usingItem.getIconString(),"icon",617,78,g);
			g.setFont(arialBold20);
			g.drawString(usingItem.name,650,100);
			g.drawString(""+usingItem.qty,800,100);
			
			draw("pointer","icon",15,120+180*curIndex(),g);
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
		draw(icon,"icon",x,y,g);
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
		draw("port" + calculatingCharacter.name,"port",42,59,g);
		if(calculatingCharacter.hp == 0) draw("dead","port",42,59,g);

		int statusIndex = 0;
		for(int j=0; j<calculatingCharacter.status.length; j++)
		{
			if(calculatingCharacter.status[j] > 0)
			{
				draw("iconStatus" + j,"icon",400-25*statusIndex,60,g);
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
		
		drawBorderedIcon("iconElement0",280,280,g);
		drawBorderedIcon("iconElement1",400,280,g);
		drawBorderedIcon("iconElement2",280,318,g);
		drawBorderedIcon("iconElement3",400,318,g);
		drawBorderedIcon("iconElement4",280,356,g);
		drawBorderedIcon("iconElement5",400,356,g);
		drawBorderedIcon("iconElement6",280,394,g);
		drawBorderedIcon("iconElement7",400,394,g);
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);

		int[] elementList = {ELEMENT_SNACK, ELEMENT_FIRE, ELEMENT_LIGHTNING, ELEMENT_WATER, ELEMENT_EARTH, ELEMENT_POISON, ELEMENT_DARK, ELEMENT_HOLY};
		for(int element=0; element<elementList.length; element++)
		{
			if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[element] > selectedCharacter.elementResistance[element]) g.setColor(statUpGreen);
			else if(state == EQUIPMENUSEL && calculatingCharacter.elementResistance[element] < selectedCharacter.elementResistance[element]) g.setColor(Color.RED); 
			else g.setColor(Color.BLACK);
			g.drawString(calculatingCharacter.elementResistance[element] + "%",310+120*(element%2),295+38*(element/2));
		}
		
		/**
		 * Status Resistance
		 */
		g.setFont(arialBold14);
		g.setColor(Color.WHITE);
		g.drawString("Status Resistance",265,445);
		
		drawBorderedIcon("iconStatus" + STATUS_POISON,280,465,g);
		drawBorderedIcon("iconStatus" + STATUS_SILENCE,400,465,g);
		drawBorderedIcon("iconStatus" + STATUS_BLIND,280,500,g);
		drawBorderedIcon("iconStatus" + STATUS_SLEEP,400,500,g);
		drawBorderedIcon("iconStatus" + STATUS_SLOW,280,535,g);
		drawBorderedIcon("iconStatus" + STATUS_BERSERK,400,535,g);
		drawBorderedIcon("iconStatus" + STATUS_SHAME,280,570,g);
		drawBorderedIcon("iconStatus" + STATUS_DEATH,400,570,g);
		g.setColor(Color.BLACK);
		g.setFont(arialBold16);
		
		int[] statusList = {STATUS_POISON, STATUS_SILENCE, STATUS_BLIND, STATUS_SLEEP, STATUS_SLOW, STATUS_BERSERK, STATUS_SHAME, STATUS_DEATH};
		for(int status=0; status<statusList.length; status++)
		{
			if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[status] > selectedCharacter.statusResistance[status]) g.setColor(statUpGreen);
			else if(state == EQUIPMENUSEL && calculatingCharacter.statusResistance[status] < selectedCharacter.statusResistance[status]) g.setColor(Color.RED); 
			else g.setColor(Color.BLACK);
			g.drawString(calculatingCharacter.statusResistance[status] + "%",310+120*(status%2),480+35*(status/2));
		}
		
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
					draw("iconActive","icon",525,520+25*yIndex,g);
					
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
					draw("iconPassive","icon",525,520+25*yIndex,g);
					
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
					draw(equip.getIconString(),"icon",620,270+27*i,g);
					g.drawString(equip.name,655,290+27*i);
				}
				else
				{
					g.drawString("---",655,290+27*i);
				}
			}
			
			if(state == EQUIPMENU) draw("pointer","icon",490,270+27*curIndex(),g);
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
					draw(inventory.get(index).getIconString(),"icon",525,270+27*(i-top),g);
					g.drawString(inventory.get(index).name,560,290+27*(i-top));
					g.drawString(""+inventory.get(index).qty,780,290+27*(i-top));
				}
			}
			
			draw("pointer","icon",490,270+27*cursorAlign,g);
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
					case 20: moreInfo = "Reduces damage from Dark element attacks"; break;
					case 21: moreInfo = "Reduces damage from Holy element attacks"; break;
					case 22: moreInfo = "Poison status - take damage each turn"; break;
					case 23: moreInfo = "Silence status - can't use skills in battle"; break;
					case 24: moreInfo = "Blind status - greatly reduces hit rate"; break;
					case 25: moreInfo = "Sleep status - can't take action until woken up"; break;
					case 26: moreInfo = "Slow status - take fewer actions in battle"; break;
					case 27: moreInfo = "Berserk status - auto-attack with boosted strength"; break;
					case 28: moreInfo = "Shame status - prevents positive status effects"; break;
					case 29: moreInfo = "Death status - instant death effects"; break;
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
					case 14: x = 234; y = 278; break;
					case 15: x = 354; y = 278; break;
					case 16: x = 234; y = 316; break;
					case 17: x = 354; y = 316; break;
					case 18: x = 234; y = 354; break;
					case 19: x = 354; y = 354; break;
					case 20: x = 234; y = 392; break;
					case 21: x = 354; y = 392; break;
					case 22: x = 234; y = 463; break;
					case 23: x = 354; y = 463; break;
					case 24: x = 234; y = 498; break;
					case 25: x = 354; y = 498; break;
					case 26: x = 234; y = 533; break;
					case 27: x = 354; y = 533; break;
					case 28: x = 234; y = 568; break;
					case 29: x = 354; y = 568; break;
				}
				
				g.drawString(moreInfo,525,205);
				
				draw("pointer","icon",x,y,g);
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
		draw("port" + party[partyIndex].name,"port",42,59,g);
		if(party[partyIndex].hp == 0) draw("dead","port",42,59,g);

		int statusIndex = 0;
		for(int j=0; j<party[partyIndex].status.length; j++)
		{
			if(party[partyIndex].status[j] > 0)
			{
				draw("iconStatus" + j,"icon",400-25*statusIndex,60,g);
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
			
			draw("pointerSel","icon",140,240,g);
			
			//draw active skills
			int top = Math.max(0, curIndex()-cursorAlign);
			int bottom = Math.min(top+8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setFont(arialBold16);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				
				int mpCost = party[partyIndex].getLearnedActiveSkills().get(i).action.getMPCost(party[partyIndex]);
				if(mpCost != -1)
				{
					drawStringRightAligned(mpCost + " MP",350,305+35*(i-top),g);
				}
				else
				{
					drawStringRightAligned("? MP",350,305+35*(i-top),g);
				}
			}
			
			draw("pointer","icon",33,285+35*(curIndex()-top),g);
			
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
			
			draw("pointerSel","icon",530,240,g);
			
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
			
			draw("pointer","icon",430,285+35*(curIndex()-top),g);
			
			//draw active skills (only the top 9)
			top = 0;
			bottom = Math.min(8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				
				int mpCost = party[partyIndex].getLearnedActiveSkills().get(i).action.getMPCost(party[partyIndex]);
				if(mpCost != -1)
				{
					drawStringRightAligned(mpCost + " MP",350,305+35*(i-top),g);
				}
				else
				{
					drawStringRightAligned("? MP",350,305+35*(i-top),g);
				}
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
			
			draw("pointer","icon",140+390*curIndex(),240,g);
			
			//draw active skills (only the top 9)
			int top = 0;
			int bottom = Math.min(8,party[partyIndex].getLearnedActiveSkills().size()-1);
			g.setFont(arialBold16);
			g.setColor(Color.BLACK);
			for(int i=top; i<=bottom; i++)
			{
				g.drawString(party[partyIndex].getLearnedActiveSkills().get(i).action.name,75,305+35*(i-top));
				
				int mpCost = party[partyIndex].getLearnedActiveSkills().get(i).action.getMPCost(party[partyIndex]);
				if(mpCost != -1)
				{
					drawStringRightAligned(mpCost + " MP",350,305+35*(i-top),g);
				}
				else
				{
					drawStringRightAligned("? MP",350,305+35*(i-top),g);
				}
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
				
				if(skill.element != ELEMENT_NONE)
				{
					draw("iconElement" + skill.element,"icon",522,177,g);
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
			if(folder.equals("monster"))
			{
				String monsterName = name.split("_")[0];
				String animationID = name.split("_")[1];
				
				if(!animationID.equals(NORMAL))
				{
					draw(monsterName + "_" + NORMAL,folder,x,y,g);
				}
			}
			else if(folder.equals("battleCharacter"))
			{
				 String characterName = name.split("_")[0];
				 String animationID = name.split("_")[1];
				 
				 if(!animationID.equals(NORMAL))
				 {
					 draw(characterName + "_" + NORMAL,folder,x,y,g);
				 }
			}
			else if(folder.equals("battleBackground"))
			{
				draw("battleBackground_0",folder,0,0,g);
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
	
	public void drawTile(int tile, int i, int j, int timer, Graphics g)
	{
		try
		{
			if(Tile.movingTileList().contains(tile))
			{
				int numAnimationStates = Tile.numAnimationStates(tile);
				int framesPerAnimationState = Tile.framesPerAnimationState(tile);
				
				int actualAnimationState = (timer / framesPerAnimationState) % numAnimationStates;
				
				g.drawImage(movingTileImage.get(movingTileList.indexOf(tile))[actualAnimationState],400+i*50+xOffset,300+j*50+yOffset,this);
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
	
	public void drawThing(int thing, int i, int j, int timer, Graphics g)
	{
		try
		{
			if(Thing.movingThingList().contains(thing))
			{
				int numAnimationStates = Thing.numAnimationStates(thing);
				int framesPerAnimationState = Thing.framesPerAnimationState(thing);
				
				int actualAnimationState = (timer / framesPerAnimationState) % numAnimationStates;

				g.drawImage(movingThingImage.get(movingThingList.indexOf(thing))[actualAnimationState],400+i*50+xOffset,300+j*50+yOffset,this);
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
						if(Tile.hasOwnTimer(curMap.tile[curX+i][curY+j].type))
						{
							drawTile(curMap.tile[curX+i][curY+j].type,i,j,curMap.tile[curX+i][curY+j].timer,g);
						}
						else
						{
							drawTile(curMap.tile[curX+i][curY+j].type,i,j,frameCount,g);
						}
					}
					catch(Exception e)
					{
						System.out.println("Couldn't draw tile " + (curX+i) + ", " + (curY+j) + " - " + e.getMessage());
						g.drawImage(tileImage[Tile.GRASS],400+i*50+xOffset,300+j*50+yOffset,this);
					}
				}
				else
				{
					drawTile(Tile.BLACK,i,j,0,g);
				}
			}
		}
		
		boolean drawPriorityThing = false;
		ArrayList<Integer> priorityThing = new ArrayList<Integer>();
		ArrayList<Integer> priorityX = new ArrayList<Integer>();
		ArrayList<Integer> priorityY = new ArrayList<Integer>();
		ArrayList<Integer> priorityTimer = new ArrayList<Integer>();
		
		boolean drawCoverPlayer = false;
		ArrayList<Integer> coverPlayerThing = new ArrayList<Integer>();
		ArrayList<Integer> coverPlayerX = new ArrayList<Integer>();
		ArrayList<Integer> coverPlayerY = new ArrayList<Integer>();
		ArrayList<Integer> coverPlayerTimer = new ArrayList<Integer>();
		
		for(int i=-17; i<10; i++)
		{
			for(int j=-14; j<8; j++)
			{
				if(curX+i >= 0 && curY+j >= 0 && curX+i < curMap.tile.length && curY+j < curMap.tile[0].length)
				{
					if(curMap.tile[curX+i][curY+j].thing.type != Thing.NOTHING)
					{
						if(curMap.tile[curX+i][curY+j].thing.priority) //should be drawn on top of other things
						{
							drawPriorityThing = true;
							priorityThing.add(curMap.tile[curX+i][curY+j].thing.type);
							priorityX.add(i);
							priorityY.add(j);
							priorityTimer.add(curMap.tile[curX+i][curY+j].thing.timer);
						}
						else if(curMap.tile[curX+i][curY+j].thing.coverPlayer) //should be drawn on top of the player
						{
							drawCoverPlayer = true;
							coverPlayerThing.add(curMap.tile[curX+i][curY+j].thing.type);
							coverPlayerX.add(i);
							coverPlayerY.add(j);
							coverPlayerTimer.add(curMap.tile[curX+i][curY+j].thing.timer);
						}
						else if(curX+i > 0)
						{
							if(curMap.tile[curX+i][curY+j].thing.type == Thing.BIGROCK && !curMap.tile[curX+i-1][curY+j].isWater()
									&& curMap.tile[curX+i][curY+j].isWater())
							{
								drawThing(Thing.SHOREWEST,i,j,0,g);
							}
						}
						
						if(curX+i > 0 && curY+j > 0 && (curMap.tile[curX+i][curY+j].thing.type == Thing.ROCK || curMap.tile[curX+i][curY+j].thing.type == Thing.BIGROCK
								|| curMap.tile[curX+i][curY+j].thing.type == Thing.ICEBERG || curMap.tile[curX+i][curY+j].thing.type == Thing.BIGICEBERG))
						{
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i+1][curY+j].isWater() && curMap.tile[curX+i+1][curY+j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREEAST,i,j,0,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i-1][curY+j].isWater() && curMap.tile[curX+i-1][curY+j].type != Tile.ICE))
							{
								drawThing(Thing.SHOREWEST,i,j,0,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i][curY+j+1].isWater() && curMap.tile[curX+i][curY+j+1].type != Tile.ICE))
							{
								drawThing(Thing.SHORESOUTH,i,j,0,g);
							}
							
							if(curMap.tile[curX+i][curY+j].isWater() && (!curMap.tile[curX+i][curY+j-1].isWater() && curMap.tile[curX+i][curY+j-1].type != Tile.ICE))
							{
								drawThing(Thing.SHORENORTH,i,j,0,g);
							}
						}
						
						if(Thing.hasOwnTimer(curMap.tile[curX+i][curY+j].thing.type))
						{
							drawThing(curMap.tile[curX+i][curY+j].thing.type,i,j,curMap.tile[curX+i][curY+j].thing.timer,g);
						}
						else
						{
							drawThing(curMap.tile[curX+i][curY+j].thing.type,i,j,frameCount,g);
						}
					}
					
					//TODO: removed shore correction, make sure shores still draw correctly
				}
			}
		}
		
		if(drawPriorityThing)
		{
			for(int i=0; i<priorityThing.size(); i++)
			{
				if(Thing.hasOwnTimer(priorityThing.get(i)))
				{
					drawThing(priorityThing.get(i),priorityX.get(i),priorityY.get(i),priorityTimer.get(i),g);
				}
				else
				{
					drawThing(priorityThing.get(i),priorityX.get(i),priorityY.get(i),0,g);
				}
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
						if(mapEvent.name.equals("Sign")) draw("mapSign","event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
						else if(!mapEvent.imageName.equals("")) draw("map" + mapEvent.imageName + mapEvent.dir,"event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.SHOP)
					{
						if(!mapEvent.imageName.equals(""))
						{
							draw("map" + mapEvent.imageName + mapEvent.dir,"event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
						}
					}
					else if(mapEvent.type == Event.INNKEEPER)
					{
						draw("map" + mapEvent.imageName + mapEvent.dir,"event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.CHEST)
					{
						//don't draw chests if they're unopened, hidden, and no party member has Cat Eyes
						if(!(mapEvent.state == 0 && mapEvent.hidden && !partyHasEquippedPassiveSkill(PassiveSkill.CATEYES)) && !mapEvent.invisible)
						{
							draw("mapChest" + mapEvent.state,"event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
						}
					}
					else if(mapEvent.type == Event.ART)
					{
						draw("mapArt","event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
					else if(mapEvent.type == Event.SAVEPOINT)
					{
						draw("mapSavePoint","event",400+drawX*50+xOffset,300+drawY*50+yOffset,g);
					}
				}
			}
		}
		
		drawMapCharacter(g);
		
		if(drawCoverPlayer)
		{
			for(int i=0; i<coverPlayerThing.size(); i++)
			{
				if(Thing.hasOwnTimer(coverPlayerThing.get(i)))
				{
					drawThing(coverPlayerThing.get(i),coverPlayerX.get(i),coverPlayerY.get(i),coverPlayerTimer.get(i),g);
				}
				else
				{
					drawThing(coverPlayerThing.get(i),coverPlayerX.get(i),coverPlayerY.get(i),0,g);
				}
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
	
	public void loadData()
	{
		data = new Data[3];
		
		for(int i=0; i<3; i++)
		{
			try
			{
				FileInputStream fileIn = new FileInputStream(System.getProperty("user.home") + FileSeparator + "BrianQuest2" + FileSeparator + "data" + FileSeparator + "data" + i + ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				data[i] = (Data) in.readObject();
				in.close();
				fileIn.close();
				
				System.out.println("Successfully read data[" + i + "]");
			}
			catch(Exception e)
			{
				data[i] = new Data();
				
				System.out.println(e.getMessage());
				System.out.println("Failed to read data[" + i + "] at " + System.getProperty("user.home") + FileSeparator + "BrianQuest2" + FileSeparator + "data" + FileSeparator + "data" + i + ".ser. Setting to new Data()");
			}
		}
	}
	
	public Unit createCharacter(ArrayList a, int index)
	{
		Unit temp;
		
		/**
		 * Basics
		 */
		int characterID = Integer.parseInt((String) a.get(0));
		if(characterID == -1)
		{
			return new None(index);
		}
		
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
		for(int i=0; i<5; i++) //there should always be 5 pieces so don't even bother checking
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
	
	public void dataToLocal()
	{
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
					aMap[j] = data[dataIndex].mapStates.get(j+n);
				}
				catch(Exception e)
				{
					aMap[j] = 0;
				}
			}
			map[i] = new Map(aMap);
			n += temp[i];
		}

		for(int i=0; i<data[dataIndex].party.length; i++)
		{
			if(data[dataIndex].party[i].id != Character.NONE)
			{
				party[i] = Unit.getCopyCharacter(data[dataIndex].party[i]);
			}
			else
			{
				party[i] = new None(i);
			}
		}
		
		inventory = new ArrayList<Item>(data[dataIndex].inventory);
		int curMapID = data[dataIndex].curMap;
		curMap = Map.makeMap(curMapID,map[curMapID].states);
		curX = data[dataIndex].curX;
		curY = data[dataIndex].curY;
		respawnMap = data[dataIndex].respawnMap;
		respawnX = data[dataIndex].respawnX;
		respawnY = data[dataIndex].respawnY;
		money = data[dataIndex].money;
		gameTime = data[dataIndex].gameTime;
		
		dataLoaded = true;
	}
	
	public void localToData()
	{
		for(int i=0; i<party.length; i++)
		{
			if(party[i].id != Character.NONE)
			{
				data[dataIndex].party[i] = Unit.getCopyCharacter(party[i]);
			}
			else
			{
				data[dataIndex].party[i] = new None(i);
			}
		}
		
		data[dataIndex].inventory = new ArrayList<Item>(inventory);
		
		data[dataIndex].mapStates = new ArrayList<Integer>(); 
		for(int i=0; i<Map.numMaps; i++)
		{
			for(int j=0; j<Map.mapStates[i]; j++)
			{
				data[dataIndex].mapStates.add(map[i].states[j]);
			}
		}
		
		data[dataIndex].curMap = curMap.id;
		data[dataIndex].curX = curX;
		data[dataIndex].curY = curY;
		data[dataIndex].respawnMap = respawnMap;
		data[dataIndex].respawnX = respawnX;
		data[dataIndex].respawnY = respawnY;
		data[dataIndex].money = money;
		data[dataIndex].gameTime = gameTime;
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
	
	public void onChangingState(int newState)
	{
		//do stuff that should happen once when changing to another state via TimerTask
		if(newState == BATTLE)
		{
			battleAction = null;
			
			battleRecalculateState(false);
			
			repaint();
		}
		else if(newState == BATTLERECALCULATE)
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
				if(battleAction.values.size() == battleAction.targets.size()*2) //2 values for the same target, so multiple effects to apply (like Purr healing both HP and MP)
				{
					valuesToApply.add(i+battleAction.values.size()/2);
				}
				
				for(int j=0; j<valuesToApply.size(); j++)
				{
					int index = valuesToApply.get(j);
					
					if(battleAction.values.size() > 0)
					{
						int value = battleAction.values.get(index);
						
						if(battleAction.action.id == Action.MURDER || battleAction.action.id == Action.MASSMURDER)
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
								
								if(target.status[STATUS_SLEEP] > 0 && Action.actionFromID(battleAction.action.id).wakesUp())
								{
									target.status[STATUS_SLEEP] = 0;
								}
							}
							else
							{
								target.healHP(value*-1);
							}
						}
					}
				}
				
				if(target.hp > 0) //only care about inflicting status if the target is alive
				{
					if(battleAction.action.id == Action.BARF || battleAction.action.id == Action.VOMITERUPTION)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_POISON);
						}
					}
					else if(battleAction.action.id == Action.SHRIEK)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_SILENCE);
						}
					}
					else if(battleAction.action.id == Action.GOTTAGOFAST || battleAction.action.id == Action.GOTTAGOFASTER)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_HASTE,7);
						}
					}
					else if(battleAction.action.id == Action.ANNOY)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_BERSERK,7);
						}
					}
					else if(battleAction.action.id == Action.COWER)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_DEFEND);
						}
					}
					else if(battleAction.action.id == Action.BLESSINGOFARINO)
					{
						int numTurns = 7;
						if(battleAction.user.status[STATUS_AMP] == 1)
						{
							numTurns = 12;
							battleAction.user.status[STATUS_AMP] = 0;
						}
						
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_ATKUP,numTurns);
						}
					}
					else if(battleAction.action.id == Action.SOOTHINGSONG)
					{
						target.status[STATUS_POISON] = 0;
						target.status[STATUS_SILENCE] = 0;
						target.status[STATUS_BLIND] = 0;
						target.status[STATUS_SLEEP] = 0;
					}
					else if(battleAction.action.id == Action.SILLYDANCE)
					{
						int numTurns = 7;
						if(battleAction.user.status[STATUS_AMP] == 1)
						{
							numTurns = 12;
							battleAction.user.status[STATUS_AMP] = 0;
						}
						
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_EVADE,numTurns);
						}
					}
					else if(battleAction.action.id == Action.AMP)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_AMP);
						}
					}
					else if(battleAction.action.id == Action.BAJABLAST)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_SLOW,5);
						}
					}
					else if(battleAction.action.id == Action.BLESSINGOFMIKU)
					{
						int numTurns = 10;
						if(battleAction.user.status[STATUS_AMP] == 1)
						{
							numTurns = 15;
							battleAction.user.status[STATUS_AMP] = 0;
						}
						
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_REGEN,numTurns);
						}
					}
					else if(battleAction.action.id == Action.BLUESHIELD || battleAction.action.id == Action.BLUEBARRIER)
					{
						int numTurns = 7;
						if(battleAction.user.status[STATUS_AMP] == 1)
						{
							numTurns = 12;
							battleAction.user.status[STATUS_AMP] = 0;
						}
						
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_SHELL,numTurns);
						}
					}
					else if(battleAction.action.id == Action.KAGESHADOWS)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_BLIND,7);
						}
					}
					else if(battleAction.action.id == Action.DEFENDHONOR || battleAction.action.id == Action.HONORFORALL)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_PROTECT,7);
						}
					}
					else if(battleAction.action.id == Action.INFLICTSHAME)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_SHAME,5);
						}
					}
					else if(battleAction.action.id == Action.CATNAP)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_SLEEP,5);
						}
					}
					else if(battleAction.action.id == Action.CATSCRATCH)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_BLIND);
						}
					}
					else if(battleAction.action.id == Action.ROBOTTEARS)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_ATKDOWN,7);
						}
					}
					else if(battleAction.action.id == Action.GOALKEEPER)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_GOALKEEPER);
						}
					}
					else if(battleAction.action.id == Action.SQUIRTOIL)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_OIL,7);
						}
					}
					else if(battleAction.action.id == Action.INSTALLVIRUS)
					{
						if(battleAction.inflictStatus.get(i))
						{
							target.inflictStatus(STATUS_VIRUS,7);
						}
					}
					
					/**
					 * Check passive skills that inflict status
					 */
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSPOISON))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_POISON,statusChance))
						{
							target.inflictStatus(STATUS_POISON);
						}
					}

					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSSILENCE))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_SILENCE,statusChance))
						{
							target.inflictStatus(STATUS_SILENCE);
						}
					}
					
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSBLIND))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_BLIND,statusChance))
						{
							target.inflictStatus(STATUS_BLIND);
						}
					}
					
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSSLEEP))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_SLEEP,statusChance))
						{
							target.inflictStatus(STATUS_SLEEP);
						}
					}
					
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSSLOW))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_SLOW,statusChance))
						{
							target.inflictStatus(STATUS_SLOW);
						}
					}
					
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSBERSERK))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_BERSERK,statusChance))
						{
							target.inflictStatus(STATUS_BERSERK);
						}
					}
					
					if(battleAction.user.hasEquippedPassiveSkill(PassiveSkill.INFLICTSTATUSSHAME))
					{
						int statusChance = 20 + 3*battleAction.user.dex; //TODO: work on this formula

						if(rand.nextInt(100) < target.getStatusChance(battleAction.user,STATUS_SHAME,statusChance))
						{
							target.inflictStatus(STATUS_SHAME);
						}
					}
				}
				
				else if(battleAction.action.id == Action.STEAL)
				{
					if(battleAction.item != null)
					{
						int indexToRemove = target.stealItems.indexOf(battleAction.item);
						target.stealItems.remove(indexToRemove);
						target.stealItemRates.remove(indexToRemove);
						
						addToInventory(battleAction.item);
					}
				}
				else if(battleAction.action.id == Action.EJECTMONEY)
				{
					//TODO: spend the money
				}
			}
		}
		
		if(battleAction != null)
		{
			battleAction.user.actionHistory.add(battleAction.action.id);
		}
		
		battleAction = null;
		
		newState = getNextState();
		if(state == BATTLERECALCULATE)
		{
			setState(newState);
			repaint();
		}

		if(currentUnit.status[STATUS_POISON] < 0)
		{
			setState(BATTLEEFFECT);
			repaint();
			
			ArrayList<Integer> targets = new ArrayList<Integer>();
			targets.add(currentUnit.getBattleIndex());
			
			doBattleCalculations(currentUnit, Action.POISON, targets);
		}
		else if(currentUnit.status[STATUS_REGEN] < 0)
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
				MonsterAction monsterAction = monsters.get(currentUnit.index).getAction(party, monsters);
				
				action = monsterAction.action;
				targets = monsterAction.targets;
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
						
						if(party[i].status[STATUS_SLEEP] > 0)
						{
							party[i].resetCT();
							party[i].status[STATUS_SLEEP]--;
						}
						else
						{
							boolean berserk = (party[i].status[STATUS_BERSERK] > 0); 
							
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
						
						if(monsters.get(i).status[STATUS_SLEEP] > 0)
						{
							monsters.get(i).resetCT();
							monsters.get(i).status[STATUS_SLEEP]--;
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
		int element = ELEMENT_NONE; //for Mysterious Melody
		
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
				if(values.get(i) > 0) animation[targets.get(i)] = DAMAGED; //TODO: only show the DAMAGED animation while damage is showing?
			}
			
			if(user.hasEquippedPassiveSkill(PassiveSkill.DRAINLIFE))
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

			if(user.hasEquippedPassiveSkill(PassiveSkill.MPATTACK))
			{
				user.mp -= (3 + (int)(user.maxMp/20.0)); //TODO: if MP Attack's cost is adjusted, adjust it here as well
				
				if(user.mp < 0) user.mp = 0;
			}
		}
		
		/**
		 * Damage Skills
		 */
		else if(action == Action.BRIANPUNCH || action == Action.CHEEZITBLAST || action == Action.COOLRANCHLASER || action == Action.BRIANSMASH || action == Action.FLAVOREXPLOSION
				|| action == Action.BARF || action == Action.FLAIL || action == Action.SHRIEK || action == Action.KAMIKAZE || action == Action.VOMITERUPTION || action == Action.SUMMONTRAINS
				|| action == Action.BAJABLAST || action == Action.MYSTERIOUSMELODY || action == Action.SHURIKEN || action == Action.NINJUTSUSLICE || action == Action.SAMURAISLASH
				|| action == Action.BUSHIDOBLADE || action == Action.MURAMASAMARA || action == Action.FIRE || action == Action.BIGFIRE || action == Action.LIGHTNINGBOLT
				|| action == Action.LIGHTNINGSTORM || action == Action.DEVOUR || action == Action.EARTHSPIKE || action == Action.EARTHQUAKE || action == Action.CATSCRATCH
				|| action == Action.DISCHARGE || action == Action.EJECTMONEY || action == Action.MALFUNCTION || action == Action.OVERLOAD || action == Action.ROBOTBEAM)
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
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_POISON,40))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.SHRIEK)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_SILENCE,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.BAJABLAST)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_SLOW,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else if(action == Action.CATSCRATCH)
				{
					if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_BLIND,60))
					{
						inflictArray.add(true);
					}
					else inflictArray.add(false);
				}
				else inflictArray.add(false);
			}
			
			if(action == Action.CHEEZITBLAST || action == Action.BARF || action == Action.SUMMONTRAINS || action == Action.PURR || action == Action.CATNAP
					|| action == Action.GOALKEEPER || action == Action.ROBOTBEAM) //TODO: enhance Vomit Eruption  TODO: Goalkeeper animation  TODO: Robot Beam + other KevBot skills?
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		
		/**
		 * Murder
		 */
		else if(action == Action.MURDER)
		{
			Unit target = getUnitFromTargetIndex(targets.get(0));
			
			if(target.statusResistance[STATUS_DEATH] == 100)
			{
				values.add(-1); //-1 = can't murder
			}
			else
			{
				double dmurderChance = 20 + 5*(currentUnit.level - target.level) + 7*(currentUnit.dex - target.dex); //TODO: work on this
				if(dmurderChance < 0) dmurderChance = 0;
				dmurderChance *= (3.0 - 2.0*((double)target.hp/target.maxHp));
				dmurderChance *= ((100.0 - target.statusResistance[STATUS_DEATH])/100.0);
				
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
				
				if(target.statusResistance[STATUS_DEATH] == 100)
				{
					values.add(-1); //-1 = can't murder
				}
				else
				{
					double dmurderChance = 20 + 5*(currentUnit.level - target.level) + 7*(currentUnit.dex - target.dex); //TODO: work on this
					if(dmurderChance < 0) dmurderChance = 0;
					dmurderChance *= (3.0 - 2.0*((double)target.hp/target.maxHp));
					dmurderChance *= ((100.0 - target.statusResistance[STATUS_DEATH])/100.0);
					
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}		
		else if(action == Action.EATPOPTART)
		{
			double dhealAmt = 1.5*(20.0 + 2.0*user.level + 5.0*user.mag + Math.pow(user.mag,1.5));
			dhealAmt = randomize(dhealAmt,85,100);
			
			int healAmt = (int) dhealAmt;
			values.add(healAmt * -1);
			
			mp.add(false);
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.RADICALRIFF) //TODO: work on this formula
		{
			double mod = 1.0;
			if(user.status[Game.STATUS_AMP] == 1)
			{
				mod = 1.5;
				user.status[Game.STATUS_AMP] = 0; 
			}
			
			for(int i=0; i<targets.size(); i++)
			{
				double dhealAmt = mod*(10 + user.mag);
				dhealAmt = randomize(dhealAmt,user.getDmgLowerBound(),100);
				
				int healAmt = (int) dhealAmt;
				values.add(healAmt);
				
				mp.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.CHIPTUNEOFLIFE)
		{
			double basePercent = 0.25;
			if(user.status[Game.STATUS_AMP] == 1)
			{
				basePercent = 0.5;
				user.status[Game.STATUS_AMP] = 0; 
			}
			
			if(getUnitFromTargetIndex(targets.get(0)).hp == 0)
			{
				double dhealAmt = (getUnitFromTargetIndex(targets.get(0)).maxHp * basePercent) + (user.dex * 2.0) + (user.mag * 3.0);
				dhealAmt = randomize(dhealAmt,85,100);
				
				int healAmt = (int) dhealAmt;
				if(healAmt > getUnitFromTargetIndex(targets.get(0)).maxHp)
				{
					healAmt = getUnitFromTargetIndex(targets.get(0)).maxHp;
				}
				
				values.add(healAmt * -1);
			}
			else
			{
				values.add(0);
				missArray.add(true);
			}
			
			mp.add(false);
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.SYSTEMREBOOT)
		{
			if(rand.nextInt(2) == 0)
			{
				System.out.println("miss");
				missArray.add(true);
				
				values.add(0);
				mp.add(false);
			}
			else
			{
				System.out.println("success");
				int healAmt = user.maxHp - user.hp;
				values.add(healAmt * -1);
				
				mp.add(false);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		
		/**
		 * Status skills
		 */
		else if(action == Action.GOTTAGOFAST || action == Action.GOTTAGOFASTER)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[STATUS_HASTE] == 0)
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.ANNOY)
		{
			int rate = 85;
			if(user.unitType == getUnitFromTargetIndex(targets.get(0)).unitType) rate = 100;  
			
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,STATUS_BERSERK,rate))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.COWER)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[STATUS_DEFEND] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.BLESSINGOFARINO)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[STATUS_ATKUP] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.SOOTHINGSONG)
		{
			Unit target = getUnitFromTargetIndex(targets.get(0));
			if(target.status[STATUS_POISON] != 0 || target.status[STATUS_SILENCE] != 0 || target.status[STATUS_BLIND] != 0 || target.status[STATUS_SLEEP] != 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.SILLYDANCE)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[STATUS_EVADE] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.AMP)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[STATUS_AMP] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.BLESSINGOFMIKU)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[STATUS_REGEN] == 0)
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.BLUESHIELD || action == Action.BLUEBARRIER)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[STATUS_SHELL] == 0)
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.KAGESHADOWS)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_BLIND,70))
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.DEFENDHONOR || action == Action.HONORFORALL)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(getUnitFromTargetIndex(targets.get(i)).status[STATUS_PROTECT] == 0)
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.INFLICTSHAME)
		{
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,STATUS_SHAME,85))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.CATNAP)
		{
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,STATUS_SLEEP,85))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.ROBOTTEARS)
		{
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,STATUS_ATKDOWN,85))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.GOALKEEPER)
		{
			if(getUnitFromTargetIndex(targets.get(0)).status[STATUS_GOALKEEPER] == 0)
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.SQUIRTOIL)
		{
			for(int i=0; i<targets.size(); i++)
			{
				if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(i)).getStatusChance(user,STATUS_OIL,85))
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
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		else if(action == Action.INSTALLVIRUS)
		{
			if(rand.nextInt(100) < getUnitFromTargetIndex(targets.get(0)).getStatusChance(user,STATUS_VIRUS,85))
			{
				inflictArray.add(true);
				missArray.add(false);
			}
			else
			{
				inflictArray.add(false);
				missArray.add(true);
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
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
					int stealItemRate = target.stealItemRates.get(i);
					
					if(user.hasEquippedPassiveSkill(PassiveSkill.CATBURGLAR))
					{
						stealItemRate *= 2; //TODO: adjust this if necessary
					}
					
					if(rand.nextInt(100) < stealItemRate)
					{
						stealItem = target.stealItems.get(i);
						stealOutcome = 1;
					}
					
					if(stealItem != null) break;
				}
				
				if(stealItem == null) stealOutcome = 0;
			}
			else stealOutcome = -1;
			
			if(user.hasEquippedPassiveSkill(PassiveSkill.MUG)) //make Steal do damage
			{
				int dmgToAdd = calculateDamage(user, action, target, critTemp, missTemp);
				
				values.add(dmgToAdd);
				mp.add(false);
				critArray.add(critTemp.get(0));
				missArray.add(missTemp.get(0));
				
				if(values.get(0) > 0) animation[targets.get(0)] = DAMAGED;
			}
			
			if(user.unitType == Unit.CHARACTER) user.mp -= Action.actionFromID(action).getMPCost(user);
		}
		
		/**
		 * Items
		 */
		else if(action == Action.HPITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			int itemAmt = usedItem.amt;
			if(getUnitFromTargetIndex(targets.get(0)).hasEquippedPassiveSkill(PassiveSkill.ITEMADDICT))
			{
				itemAmt *= 1.5;
			}
			values.add(itemAmt * -1);
			mp.add(false);
		}
		else if(action == Action.MPITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			int itemAmt = usedItem.amt;
			if(getUnitFromTargetIndex(targets.get(0)).hasEquippedPassiveSkill(PassiveSkill.ITEMADDICT))
			{
				itemAmt *= 1.5;
			}
			values.add(itemAmt);
			mp.add(true);
		}
		else if(action == Action.REVIVEITEM)
		{
			usedItem = inventory.get(prevIndex(1));
			double healAmt = getUnitFromTargetIndex(targets.get(0)).maxHp * (usedItem.amt/100.0);
			if(getUnitFromTargetIndex(targets.get(0)).hasEquippedPassiveSkill(PassiveSkill.ITEMADDICT))
			{
				healAmt *= 1.5;
			}
			values.add((int) healAmt * -1);
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
		
		battleAction = new BattleAction(user,Action.actionFromID(action),targets,values,mp,animation,critArray,missArray,inflictArray);
		battleAction.element = element;
		
		if(battleAction.action.id == Action.STEAL)
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
	
	public void saveGame()
	{
		localToData();
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.home") + FileSeparator + "BrianQuest2" + FileSeparator + "data" + FileSeparator + "data" + dataIndex + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(data[dataIndex]);
			out.close();
			fileOut.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		
	public String gameTimeString(int time)
	{
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
			else if(curY-1 >= 0 && curMap.tile[curX][curY-1].walkable)
			{
				move = true;
				
				curY--;
				yOffset = -24;
			}
		}
		else if(dir == EAST)
		{
			direction = EAST;
			
			if(curX+1 < curMap.getWidth()-1 && curMap.tile[curX+1][curY].walkable)
			{
				move = true;
				
				curX++;
				xOffset = 24;
			}
		}
		else if(dir == SOUTH)
		{
			direction = SOUTH;
			
			if(curY+1 < curMap.getHeight()-1 && curMap.tile[curX][curY+1].type == Tile.SNOWWALLNORTH && curMap.tile[curX][curY+1].thing.type != Thing.MOUNTAINCAVEENTRANCE2)
			{
				move = false;
			}
			else if(curY+1 < curMap.getHeight()-1 && curMap.tile[curX][curY+1].walkable)
			{
				move = true;
				
				curY++;
				yOffset = 24;
			}
		}
		else if(dir == WEST)
		{
			direction = WEST;
			
			if(curX-1 >= 0 && curMap.tile[curX-1][curY].walkable)
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
			
			if(curMap.tile[curX][curY].event.type == Event.PORTAL)
			{
				int destMap = curMap.tile[curX][curY].event.destMap;
				int destX = curMap.tile[curX][curY].event.destX;
				int destY = curMap.tile[curX][curY].event.destY;
				
				if(curMap.tile[curX][curY].event.dir != -1)
				{
					direction = curMap.tile[curX][curY].event.dir;
					
					moveTimer += 250;
				}
				
				if(curMap.tile[curX][curY].sound != null)
				{
					playSound(curMap.tile[curX][curY].sound);
				}
				
				changeMap(destMap, destX, destY);

				stepsWithoutBattle = 0;
			}
			else if(curMap.tile[curX][curY].hasMonsters && !skipMonsters && sliding == -1)
			{
				int encRate = 70; //TODO: was 4 (increased rate for testing purposes)
				int encThreshold = 1; //TODO: was 6 (increased rate for testing purposes)
				
				if(partyHasEquippedPassiveSkill(PassiveSkill.ENCOUNTERHALF))
				{
					encRate /= 2; //cut the encounter rate in half
					encThreshold *= 2; //double the number of steps guaranteed without a battle
				}
				
				if(partyHasEquippedPassiveSkill(PassiveSkill.ENCOUNTERNONE))
				{
					encRate = 0; //no battles
				}
				
				if(stepsWithoutBattle > encThreshold && rand.nextInt(100) < encRate)
				{
					monsters = curMap.getMonsters(curMap.tile[curX][curY].monsterListID);
					for(int i=0; i<monsters.size(); i++)
					{
						monsters.get(i).resetBeforeBattle();
					}
					
					for(int i=0; i<3; i++)
					{
						if(party[i].id != Character.NONE)
						{
							party[i].resetBeforeBattle();
						}
					}
					
					setState(ENCOUNTER);
					
					timer = new Timer();
					timer.schedule(new Task(),29*100); //29 frames, 100ms
					newState = BATTLE;
					
					stepsWithoutBattle = 0;
					
					/**
					 * Check passive skills that add status at start of battle
					 */
					for(int i=0; i<3; i++)
					{
						if(party[i].existsAndAlive())
						{
							if(party[i].hasEquippedPassiveSkill(PassiveSkill.AUTOREGEN))
							{
								party[i].inflictStatus(STATUS_REGEN,10);
							}
							
							if(party[i].hasEquippedPassiveSkill(PassiveSkill.AUTOHASTE))
							{
								party[i].inflictStatus(STATUS_HASTE,7);
							}
							
							if(party[i].hasEquippedPassiveSkill(PassiveSkill.AUTOBERSERK))
							{
								party[i].inflictStatus(STATUS_BERSERK,7);
							}
							
							if(party[i].hasEquippedPassiveSkill(PassiveSkill.AUTOPROTECT))
							{
								party[i].inflictStatus(STATUS_PROTECT,7);
							}
							
							if(party[i].hasEquippedPassiveSkill(PassiveSkill.AUTOSHELL))
							{
								party[i].inflictStatus(STATUS_SHELL,7);
							}
						}
					}
											
					playSong("Battle", true);
				}
				
				stepsWithoutBattle++;
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
	
	public boolean partyHasEquippedPassiveSkill(int passiveID)
	{
		for(int i=0; i<3; i++)
		{
			if(party[i].id != Character.NONE)
			{
				if(party[i].hasEquippedPassiveSkill(passiveID))
				{
					return true;
				}
			}
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
		else if(evt.getKeyCode() == KeyEvent.VK_ENTER) //TODO: remove this and other cheats in "finished" game
		{
			if(direction == NORTH) curY--;
			else if(direction == EAST) curX++;
			else if(direction == SOUTH) curY++;
			else if(direction == WEST) curX--;
			
			System.out.println("coord: " + curX + "," + curY);
		}
		else if(evt.getKeyCode() == KeyEvent.VK_I)
		{
			if(money < 1000000) money += 1000;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_P)
		{
			if(party[1].id == Character.NONE && party[3].id == Character.NONE)
			{
				party[3] = new Alex(1,0);
				party[4] = new Hank(1,0);
				party[5] = new Mychal(1,0);
				party[6] = new Kitten(1,0);
				party[7] = new KevBot(1,0);
			}
		}
		else if(evt.getKeyCode() == KeyEvent.VK_Q)
		{
			party[2] = new KevBot(1,2);
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
				else if(party[i].id == Character.HANK)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveBlessingOfArino(25,true));
					party[i].activeSkills.add(new ActiveBlessingOfMiku(25,true));
					party[i].activeSkills.add(new ActiveMysteriousMelody(25,true));
					party[i].activeSkills.add(new ActiveSoothingSong(25,true));
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
				else if(party[i].id == Character.KEVBOT)
				{
					party[i].activeSkills = new ArrayList<ActiveSkill>();
					party[i].activeSkills.add(new ActiveDischarge(25,true));
					party[i].activeSkills.add(new ActiveRobotTears(25,true));
					party[i].activeSkills.add(new ActiveEjectMoney(25,true));
					party[i].activeSkills.add(new ActiveSystemReboot(25,true));
					party[i].activeSkills.add(new ActiveMalfunction(25,true));
					party[i].activeSkills.add(new ActiveOverload(25,true));
					party[i].activeSkills.add(new ActiveSquirtOil(25,true));
					party[i].activeSkills.add(new ActiveInstallVirus(25,true));
					party[i].activeSkills.add(new ActiveRobotBeam(25,true));
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
					
					saveGame();
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
				
				addToInventory(curEvent.item);
				playSound("item");
				
				curEvent.state = 1;
				
				addIndex(0);
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
					//curMap.tile[curEvent.x][curEvent.y].openChest();
					
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
			setState(DATASELECT1);
			
			boolean hasExistingData = false;;
			
			for(int i=0; i<3; i++)
			{
				if(data[i].dataExists)
				{
					hasExistingData = true;
				}
			}
			
			if(hasExistingData)
			{
				addIndex(1);
			}
			else
			{
				addIndex(0);
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
				
				if(currentUnit.hasEquippedPassiveSkill(PassiveSkill.MESCARED))
				{
					fleeChance = 100; //fleeing always works
				}

				if(rand.nextInt(100) < fleeChance && !preventFlee())
				{
					setState(BATTLEFLED);
				}
				else	
				{
					int[] animation = new int[7];
					for(int i=0; i<animation.length; i++) animation[i] = NORMAL;
					
					battleAction = new BattleAction(currentUnit,Action.actionFromID(Action.FLEE),null,null,null,animation,null,null,null);
					
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
			selectedAction = currentUnit.getLearnedActiveSkills().get(curIndex()).action;
			
			if(currentUnit.canUseAction(selectedAction))
			{
				int targetType = selectedAction.targetType;
				if(targetType == Action.ONEENEMY || targetType == Action.ALLENEMIES || targetType == Action.ALLUNITS)
				{
					addIndex(topAliveMonsterIndex());
				}
				else if(targetType == Action.ONEALLY || targetType == Action.ALLALLIES)
				{
					addIndex(topAlivePartyIndex());
				}
				else if(targetType == Action.SELF)
				{
					ArrayList<Integer> targets = new ArrayList<Integer>();
					
					if(selectedAction.id == Action.MALFUNCTION) //random target
					{
						ArrayList<Integer> allTargets = Monster.getExistingPlayerTargets(party);
						allTargets.addAll(Monster.getExistingMonsterTargets(monsters));
						
						targets.add(allTargets.get(rand.nextInt(allTargets.size())));
					}
					else
					{
						targets.add(currentUnit.getBattleIndex());
					}
					
					doBattleCalculations(currentUnit,selectedAction.id,targets);
				}
				else if(targetType == Action.ONEUNIT)
				{
					addIndex(topAliveMonsterIndex()+3);
				}
				
				if(targetType != Action.SELF)
				{
					setState(BATTLETARGET);
				}
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
			
			int targetType = selectedAction.targetType;

			if(targetType == Action.ONEENEMY)
			{
				targets.add(monsters.get(curIndex()).getBattleIndex());
			}
			
			if(targetType == Action.ALLENEMIES || targetType == Action.ALLUNITS)
			{
				for(int i=0; i<monsters.size(); i++)
				{
					if(monsters.get(i).hp > 0)
					{
						targets.add(monsters.get(i).getBattleIndex());
					}
				}
			}
			
			if(targetType == Action.ONEALLY || targetType == Action.SELF)
			{
				targets.add(curIndex());
			}
			
			if(targetType == Action.ALLALLIES || targetType == Action.ALLUNITS)
			{
				for(int i=0; i<3; i++)
				{
					if(party[i].hp > 0)
					{
						targets.add(i);
					}
				}
			}
			
			if(targetType == Action.ONEUNIT)
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
			
			boolean leveledUp;
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE)
				{
					int totalExp = getTotalExpGain(party[i]);
					int totalSP = getTotalSPGain(party[i]);
					
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
					int itemRate = monsters.get(i).itemRates.get(j);
					
					if(partyHasEquippedPassiveSkill(PassiveSkill.FOOLSFORTUNE))
					{
						itemRate *= 1.5; //TODO: +50%? base this on dex or no?
					}
					
					if(rand.nextInt(100) < itemRate)
					{
						addToItemList(monsters.get(i).itemDrops.get(j), itemDrops);
						addToInventory(monsters.get(i).itemDrops.get(j));
					}
				}
			}
			
			/**
			 * Ignorance Is Bliss
			 */
			for(int i=0; i<3; i++)
			{
				if(party[i].id != Character.NONE)
				{
					if(party[i].hasEquippedPassiveSkill(PassiveSkill.IGNORANCEISBLISS))
					{
						party[i].clearAllStatuses(); //remove all statuses at end of battle
					}
				}
			}
			
			setState(AFTERBATTLE);
		}
		else if(state == BATTLEFLED)
		{
			playSong("Cowardice");
			
			setState(AFTERBATTLE);
		}
		else if(state == AFTERBATTLE)
		{			
			setState(MAP);
			
			playMapSong(true);
			
			clearIndex();
		}
		else if(state == BATTLELOST)
		{
			setState(GAMEOVER);
		}
		else if(state == GAMEOVER)
		{
			setState(TITLESCREEN);
			
			playSong("Nice");
			
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
		else if(state == DATASELECT1)
		{
			setState(DATASELECT2);
			addIndex(0);
		}
		else if(state == DATASELECT2)
		{
			if(prevIndex(1) == 0) //New Game
			{
				if(data[curIndex()].dataExists)
				{
					setState(CONFIRMOVERRIDE);
					addIndex(0);
				}
				else
				{
					dataIndex = curIndex();
					data[dataIndex].newGame();
					dataToLocal();
					
					direction = NORTH;
					
					startCutscene(CUTSCENE_INTRO);
					
					clearIndex();
				}
			}
			else //Continue
			{
				if(data[curIndex()].dataExists)
				{
					dataIndex = curIndex();
					dataToLocal();
					
					setState(MAP);
					playMapSong(false);
					
					clearIndex();
				}
				else
				{
					//TODO: play sound
				}
			}
		}
		else if(state == CONFIRMOVERRIDE)
		{
			if(curIndex() == 0) //No
			{
				setState(DATASELECT2);
				removeIndex();
			}
			else //Yes
			{
				dataIndex = prevIndex(1);
				data[dataIndex].newGame();
				dataToLocal();
				
				direction = NORTH;
				
				startCutscene(CUTSCENE_INTRO);
				
				clearIndex();
			}
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
		else if(state == DATASELECT2)
		{
			setState(DATASELECT1);
			removeIndex();
		}
		else if(state == CONFIRMOVERRIDE)
		{
			setState(DATASELECT2);
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
			else if(curIndex() >= 16 && curIndex() <= 29) setIndex(curIndex() - 2);
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
		else if(state == DATASELECT2)
		{
			if(curIndex() > 0)
			{
				decIndex();
			}
		}
		else if(state == CONFIRMOVERRIDE)
		{
			setIndex(0);
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
			else if(curIndex() >= 14 && curIndex() <= 27) setIndex(curIndex() + 2);
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
		else if(state == DATASELECT2)
		{
			if(curIndex() < 2)
			{
				incIndex();
			}
		}
		else if(state == CONFIRMOVERRIDE)
		{
			setIndex(1);
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
			if(curIndex() < 3 && party[3].id != Character.NONE)
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
			else if(curIndex() == 14 || curIndex() == 16 || curIndex() == 18 || curIndex() == 20 || curIndex() == 22 || curIndex() == 24 || curIndex() == 26 || curIndex() == 28) incIndex();
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
		else if(state == DATASELECT1)
		{
			setIndex(1);
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
			else if(curIndex() == 22) setIndex(10);
			else if(curIndex() == 24) setIndex(11);
			else if(curIndex() == 26) setIndex(12);
			else if(curIndex() == 28) setIndex(13);
			else if(curIndex() == 15 || curIndex() == 17 || curIndex() == 19 || curIndex() == 21 || curIndex() == 23 || curIndex() == 25 || curIndex() == 27 || curIndex() == 29) decIndex();
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
		else if(state == DATASELECT1)
		{
			setIndex(0);
		}
	}
	
	public void pressedSpace()
	{
		if(state == TITLESCREEN)
		{
			setState(DATASELECT1);
		}
		else if(state == MAP)
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
				
				if(clip != null)
				{
					clip.stop();
					clip.close();
				}
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