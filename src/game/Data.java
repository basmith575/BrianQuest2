package game;

import java.util.ArrayList;

public class Data implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Unit[] party;
	public ArrayList<Integer> mapStates;
	public ArrayList<Item> inventory;
	
	public boolean dataExists;
	public int curMap;
	public int curX;
	public int curY;
	public int respawnMap;
	public int respawnX;
	public int respawnY;
	public int money;
	public int gameTime;	
	
	public Data()
	{
		this.dataExists = false;
	}
	
	public void newGame()
	{
		this.party = new Unit[8]; //6 party members, 2 extra space in case 1 in party and 5 in "storage"
		
		this.party[0] = Unit.characterFromID(Character.BRIAN,1,0);
		this.party[0].equip[Unit.WEAPON] = new Stick(1);
		
		for(int i=1; i<this.party.length; i++) //TODO: consider having a "shell" character class w/ character info and rebuild character from that (so things always get instantiated/refreshed as expected)
		{
			this.party[i] = new None(i);
		}
		
		this.mapStates = new ArrayList<Integer>();
		for(int i=0; i<Map.numMaps; i++)
		{
			for(int j=0; j<Map.mapStates[i]; j++)
			{
				this.mapStates.add(0);
			}
		}
		
		this.inventory = new ArrayList<Item>();
		
		this.dataExists = true;
		this.curMap = 0;
		this.curX = 14;
		this.curY = 58;
		this.respawnMap = 0;
		this.respawnX = 14;
		this.respawnY = 58;
		this.money = 0;
		this.gameTime = 0;
	}
}
