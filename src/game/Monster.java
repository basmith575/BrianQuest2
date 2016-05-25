package game;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Unit
{	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Monster IDs
	 */
	public static final int SNAKE = 0;
	public static final int SNAKEKING = 1;
	
	/**
	 * Monster types
	 */
	public static final int BEAST = 0;
	public static final int HUMAN = 1;
	public static final int ROBOT = 2;
	
	//TODO: Coke/Pepsi dino return with Snack damage attacks
	//TODO: SockLord
	//TODO: Sponge
	//TODO: Lord Wasabi
	//TODO: Mega Ghost rematch, gives Brian weapon with Mass Murder (optional fight?)
	//TODO: Veggie Boss (get Fruit Knife beforehand)
	
	//TODO: birds with 100% earth resistance?
	
	public Monster()
	{
		
	}
	
	public Monster(int index)
	{
		
	}
	
	public Monster(Monster m)
	{
		this.id = m.id;		
		this.index = m.index;
		this.unitType = m.unitType;
		
		this.maxHp = m.maxHp;
		this.hp = m.hp;
		this.maxMp = m.maxMp;
		this.mp = m.mp;
		
		this.str = m.str;
		this.mag = m.mag;
		this.dex = m.dex;
		this.spd = m.spd;
		
		this.atk = m.atk;
		this.hitRate = m.hitRate;
		this.evasion = m.evasion;
		this.critRate = m.critRate;

		this.def = m.def;
		this.mdef = m.mdef;
		
		this.ct = m.ct;
		
		this.status = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.status.length; i++) this.status[i] = m.status[i];
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		for(int i=0; i<this.elementResistance.length; i++) this.elementResistance[i] = m.elementResistance[i];
		
		this.statusResistance = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.statusResistance.length; i++) this.statusResistance[i] = m.statusResistance[i];
		
		this.imageHeight = m.imageHeight;
		this.imageWidth = m.imageWidth;
		this.spriteHeight = m.spriteHeight;
		
		//Monster specific
		this.species = m.species;
		this.expGain = m.expGain;
		this.spGain = m.spGain;
		this.moneyGain = m.moneyGain;
		
		this.itemDrops = m.itemDrops;
		this.itemRates = m.itemRates;
		this.stealItems = m.stealItems;
		this.stealItemRates = m.stealItemRates;
		
		this.attackAnimation = m.attackAnimation;
		
		this.preventFlee = m.preventFlee;
	}
	
	public static Monster getMonsterFromID(int id, int index)
	{
		if(index < 0) index = 0;
		
		switch(id)
		{
			case SNAKE: return new Snake(index);
			case SNAKEKING: return new SnakeKing(index);
		}
		
		return null;
	}
	
	public static ArrayList<Integer> getExistingPlayerTargets(Unit[] party)
	{
		ArrayList<Integer> existingPlayerTargets = new ArrayList<Integer>();
		for(int i=0; i<3; i++)
		{
			if(party[i].existsAndAlive()) existingPlayerTargets.add(i);
		}
		
		return existingPlayerTargets;
	}
	
	public static ArrayList<Integer> getExistingMonsterTargets(ArrayList<Monster> monsters)
	{
		ArrayList<Integer> existingMonsterTargets = new ArrayList<Integer>();
		for(int i=0; i<monsters.size(); i++)
		{
			if(monsters.get(i).hp > 0) existingMonsterTargets.add(i+3);
		}
		
		return existingMonsterTargets;
	}
	
	//For a given action, returns a MonsterAction with a random target
	public static MonsterAction randomTargetMonsterAction(int action, Unit[] party, ArrayList<Monster> monsters, int selfBattleIndex)
	{
		Random rand = new Random();
		
		ArrayList<Integer> targets = new ArrayList<Integer>();
		ArrayList<Integer> existingPlayerTargets = getExistingPlayerTargets(party);
		ArrayList<Integer> existingMonsterTargets = getExistingMonsterTargets(monsters);
		
		int targetType = Action.actionFromID(action).targetType;
		
		if(targetType == Action.ONEENEMY)
		{
			boolean goalkeeper = false;
			for(int i=0; i<existingPlayerTargets.size(); i++)
			{
				if(party[existingPlayerTargets.get(i)].status[Game.STATUS_GOALKEEPER] != 0)
				{
					targets.add(existingPlayerTargets.get(i));
					goalkeeper = true;
				}
			}
			
			if(!goalkeeper)
			{
				int target = existingPlayerTargets.get(rand.nextInt(existingPlayerTargets.size()));
				
				targets.add(target);
			}
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
			targets.add(selfBattleIndex);
		}
		else if(targetType == Action.ALLALLIES)
		{
			targets = existingMonsterTargets;
		}
		else if(targetType == Action.ONEUNIT)
		{
			//TODO: any use case?
		}
		
		return new MonsterAction(action, targets);
	}
	
	public MonsterAction getActionTest()
	{
		ArrayList<Integer> targets = new ArrayList<Integer>();
		targets.add(0);
		MonsterAction a = new MonsterAction(Action.ATTACK, targets);
		
		return a;
	}
	
	public MonsterAction getAction(Unit[] party, ArrayList<Monster> monsters)
	{
		return null; //override for each monster
	}
}

class Snake extends Monster
{
	private static final long serialVersionUID = 1L;

	public Snake(int index)
	{
		this.id = Monster.SNAKE;
		this.species = "Snake";
		this.type = BEAST;
		this.unitType = Unit.MONSTER;
		this.setIndex(index);
		
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
		
		this.preventFlee = false;
		
		this.itemDrops = new ArrayList<Item>();
		this.itemRates = new ArrayList<Integer>();
		this.itemDrops.add(new Potion(1));
		this.itemRates.add(10);
		
		this.stealItems = new ArrayList<Item>();
		this.stealItemRates = new ArrayList<Integer>();
		this.stealItems.add(new Potion(1));
		this.stealItemRates.add(100);
		
		this.initialize();
		
		this.elementResistance[Game.ELEMENT_SNACK] = 100;
	}
	
	
	public MonsterAction getAction(Unit[] party, ArrayList<Monster> monsters)
	{
		/**
		 * Always attack
		 */
		int action;

		action = Action.ATTACK;
		
		return Monster.randomTargetMonsterAction(action, party, monsters, this.getBattleIndex());
	}
}

class SnakeKing extends Monster
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SnakeKing(int index)
	{
		this.id = Monster.SNAKEKING;
		this.species = "Snake King";
		this.type = BEAST;
		this.unitType = Unit.MONSTER;
		this.setIndex(index);
		
		this.level = 3;
		
		this.maxHp = 150;
		this.hp = this.maxHp;
		
		//image width and height
		this.imageWidth = 100;
		this.imageHeight = 100;
		this.spriteHeight = 85;
		
		this.str = 5;
		this.mag = 3;
		this.dex = 3;
		this.spd = 4;
		
		this.hitRate = 100;
		this.evasion = 0;
		this.critRate = 0;
		
		this.def = 1;
		this.mdef = 1;
		
		this.expGain = 25;
		this.spGain = 5;
		this.moneyGain = 50;
		
		this.attackAnimation = "hit";
		
		this.preventFlee = true;
		
		this.itemDrops = new ArrayList<Item>();
		this.itemRates = new ArrayList<Integer>();
		this.itemDrops.add(new Potion(1));
		this.itemDrops.add(new OldSocks(1));
		this.itemRates.add(100);
		this.itemRates.add(100);
		
		this.stealItems = new ArrayList<Item>();
		this.stealItemRates = new ArrayList<Integer>();
		this.stealItems.add(new Potion(1));
		this.stealItemRates.add(50);
		this.stealItems.add(new Ether(1));
		this.stealItemRates.add(50);
		
		this.initialize();
	}
	
	public MonsterAction getAction(Unit[] party, ArrayList<Monster> monsters)
	{
		int action;
		Random rand = new Random();
		
		if(rand.nextInt(2) == 0)
		{
			action = Action.ATTACK;
		}
		else
		{
			action = Action.FIRE;
		}
		
		return Monster.randomTargetMonsterAction(action, party, monsters, this.getBattleIndex());
	}
}

class MonsterAction
{
	int action;
	ArrayList<Integer> targets;
	
	public MonsterAction(int action, ArrayList<Integer> targets)
	{
		this.action = action;
		this.targets = targets;
	}
}