package game;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Unit
{
	public static int numMonsters = 30; //the total number of monsters, used for the bestiary
	
	/**
	 * Monster IDs
	 */
	public static final int SNAKE = 1;
	
	//TODO: Coke/Pepsi dino return with Snack damage attacks
	//TODO: SockLord
}

class Snake extends Unit
{
	public Snake()
	{
		this.id = Monster.SNAKE;
		this.species = "Snake";
		this.unitType = Unit.MONSTER;
		
		this.level = 1;
		
		this.maxHp = 32;
		this.hp = this.maxHp;
		
		//image width and height
		this.imageWidth = 100;
		this.imageHeight = 100;
		this.spriteHeight = 50;
		
		this.str = 3;
		this.mag = 0;
		this.dex = 2;
		this.spd = 3;
		
		this.hitRate = 100;
		this.evasion = 0;
		this.critRate = 0;
		
		this.def = 0;
		this.mdef = 0;
		
		this.expGain = 10;
		this.spGain = 0.5;
		this.moneyGain = 8;
		
		this.attackAnimation = "hit";
		
		this.murderable = true;
		
		this.itemDrops = new ArrayList<Item>();
		this.itemRates = new ArrayList<Integer>();
		itemDrops.add(new Potion(1));
		itemDrops.add(new OldSocks(1));
		itemRates.add(15);
		itemRates.add(5);
		
		this.stealItems = new ArrayList<Item>();
		this.stealItemRates = new ArrayList<Integer>();
		this.stealItems.add(new Potion(1));
		this.stealItemRates.add(50);
		this.stealItems.add(new Ether(1));
		this.stealItemRates.add(50);
		
		this.initialize();
	}
}