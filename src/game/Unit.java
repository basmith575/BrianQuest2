package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

public class Unit implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String name;
	public String className;
	
	public int unitType;
	public static final int CHARACTER = 0;
	public static final int MONSTER = 1;
	
	public int index;		//index in the party

	public int level;
	public int exp;
	
	public int maxHp;		//max hp
	public int hp;			//current hp
	public int maxMp;		//max mp
	public int mp;			//current mp
	
	public int str;			//attack power
	public int mag;			//magic power
	public int dex;			//boosts hit rate, crit chance, skill effectiveness, etc.
	public int spd;			//how often you can attack, dodge attacks
	
	public int atk;
	public int hitRate;
	public int evasion;
	public int critRate;
	
	public int def;			//damage reduced from physical attacks
	public int mdef;		//damage reduced from magic attacks
	
	public int ct;			//timer in battle
	
	public Item[] equip;
	public static final int WEAPON = 0;
	public static final int HAT = 1;
	public static final int ARMOR = 2;
	public static final int SHOE = 3;
	public static final int ACCESSORY = 4;
	
	public ArrayList<ActiveSkill> activeSkills;
	public ArrayList<PassiveSkill> passiveSkills;
	
	public int[] status;
	
	public int[] elementResistance;
	public int[] statusResistance;
	
	public int cubes;
	
	public int imageWidth; //used for drawing
	public int imageHeight; //used for drawing
	public int spriteHeight; //might have white space offset in image
	
	public ArrayList<Integer> actionHistory;
	
	/**
	 * Monster specific
	 */
	public String species;	//display name
	public int type; 		//monster type (beast, human, etc.)
	public int expGain;		//how much exp it gives
	public double spGain; 	//how many cubes it gives (rounds up to nearest int)
	public int moneyGain;	//how much money it gives
	public ArrayList<Item> itemDrops;
	public ArrayList<Integer> itemRates;
	public ArrayList<Item> stealItems;
	public ArrayList<Integer> stealItemRates;
	public String attackAnimation;
	public boolean preventFlee; //for bosses and stuff, prevent fleeing
	
	public Unit()
	{
		
	}
	
	public Unit(Unit unit)
	{
		this.id = unit.id;		
		this.index = unit.index;
		this.unitType = unit.unitType;
		
		this.maxHp = unit.maxHp;
		this.hp = unit.hp;
		this.maxMp = unit.maxMp;
		this.mp = unit.mp;
		
		this.str = unit.str;
		this.mag = unit.mag;
		this.dex = unit.dex;
		this.spd = unit.spd;
		
		this.atk = unit.atk;
		this.hitRate = unit.hitRate;
		this.evasion = unit.evasion;
		this.critRate = unit.critRate;

		this.def = unit.def;
		this.mdef = unit.mdef;
		
		this.ct = unit.ct;
		
		this.status = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.status.length; i++)
		{
			if(i >= unit.status.length) //TODO: can remove this check once I know more statuses won't be added
			{
				this.status[i] = 0;
			}
			else this.status[i] = unit.status[i];
		}
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		for(int i=0; i<this.elementResistance.length; i++)
		{
			this.elementResistance[i] = unit.elementResistance[i];
		}
		
		this.statusResistance = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.statusResistance.length; i++)
		{
			if(i >= unit.statusResistance.length) //TODO: can remove this check once I know more statuses won't be added
			{
				this.statusResistance[i] = 0;
			}
			else this.statusResistance[i] = unit.statusResistance[i];
		}
		
		this.imageHeight = unit.imageHeight;
		this.imageWidth = unit.imageWidth;
		this.spriteHeight = unit.spriteHeight;
		
		this.actionHistory = new ArrayList<Integer>();
		if(unit.actionHistory != null)
		{
			for(int i=0; i<unit.actionHistory.size(); i++)
			{
				this.actionHistory.add(unit.actionHistory.get(i));
			}
		}
		
		//Character specific
		if(unit.unitType == Unit.CHARACTER)
		{
			this.name = unit.name;
			this.className = unit.className;
			this.level = unit.level;
			this.exp = unit.exp;
			
			this.cubes = unit.cubes;
			
			this.equip = new Item[5];
			for(int i=0; i<equip.length; i++) this.equip[i] = Item.itemFromID(unit.equip[i].id);
			
			this.activeSkills = new ArrayList<ActiveSkill>();
			for(int i=0; i<unit.activeSkills.size(); i++)
			{
				this.activeSkills.add(ActiveSkill.activeSkillFromID(unit.activeSkills.get(i).id, unit.activeSkills.get(i).currentSP, unit.activeSkills.get(i).learned));
			}
			
			this.passiveSkills = new ArrayList<PassiveSkill>();
			for(int i=0; i<unit.passiveSkills.size(); i++)
			{
				this.passiveSkills.add(PassiveSkill.passiveSkillFromID(unit.passiveSkills.get(i).id, unit.passiveSkills.get(i).currentSP, unit.passiveSkills.get(i).learned, unit.passiveSkills.get(i).equipped));
			}
		}
		
		//Monster specific
		if(unit.unitType == Unit.MONSTER)
		{
			this.species = unit.species;
			this.expGain = unit.expGain;
			this.spGain = unit.spGain;
			this.moneyGain = unit.moneyGain;
			
			this.itemDrops = unit.itemDrops;
			this.itemRates = unit.itemRates;
			this.stealItems = unit.stealItems;
			this.stealItemRates = unit.stealItemRates;
			
			this.attackAnimation = unit.attackAnimation;
			
			this.preventFlee = unit.preventFlee;
			this.type = unit.type;
		}
	}
	
	public static Unit getCopyCharacter(Unit character)
	{
		if(character instanceof Brian) return new Brian((Brian) character);
		else if(character instanceof Alex) return new Alex((Alex) character);
		else if(character instanceof Hank) return new Hank((Hank) character);
		else if(character instanceof Mychal) return new Mychal((Mychal) character);
		else if(character instanceof Kitten) return new Kitten((Kitten) character);
		else if(character instanceof KevBot) return new KevBot((KevBot) character);
		
		return null;
	}
	
	public String getClassName()
	{
		if(this.hasEquippedPassiveSkill(PassiveSkill.ENLIGHTENMENT))
		{
			return "Enlightened";
		}
		
		return this.className;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getBattleIndex()
	{
		if(this.unitType == CHARACTER)
		{
			return this.index;
		}
		else
		{
			return this.index + 3;
		}
	}
	
	public int getLastAction()
	{
		return getLastAction(1);
	}
	
	public int getLastAction(int numActionsAgo)
	{
		if(this.actionHistory == null)
		{
			return -1;
		}
		else if(this.actionHistory.size() < numActionsAgo)
		{
			return -1;
		}
		else
		{
			return actionHistory.get(actionHistory.size()-numActionsAgo);
		}
	}
	
	public boolean usedActionInPreviousTurns(int action, int turns)
	{
		if(this.actionHistory == null)
		{
			return false;
		}
		
		int start = Math.max(0, this.actionHistory.size() - turns);
		
		for(int i=start; i<this.actionHistory.size(); i++)
		{
			if(this.actionHistory.get(i) == action) return true;
		}
		
		return false;
	}
	
	public void initialize()
	{
		//things that get initialized the same for every character
		
		this.status = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.status.length; i++)
		{
			this.status[i] = 0;
		}
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		for(int i=0; i<this.elementResistance.length; i++)
		{
			this.elementResistance[i] = 0;
		}
		
		this.statusResistance = new int[Game.NUMSTATUSES];
		for(int i=0; i<this.statusResistance.length; i++)
		{
			this.statusResistance[i] = 0;
		}
		
		this.actionHistory = new ArrayList<Integer>();
		
		if(this.unitType == CHARACTER)
		{
			this.calcBaseStats();
			
			this.hp = this.maxHp;
			this.mp = this.maxMp;
			
			this.equip = new Item[5];
			for(int i=0; i<this.equip.length; i++) this.equip[i] = new NoItem();
			
			this.activeSkills = new ArrayList<ActiveSkill>();
			this.passiveSkills = new ArrayList<PassiveSkill>();
			
			this.atk = 1;
			this.hitRate = 100 + this.dex;
			this.evasion = this.spd;
			this.critRate = this.dex/5;
			
			this.def = 0;
			this.mdef = 0;
			
			this.cubes = this.getTotalCubes();
		}
		else if(this.unitType == MONSTER)
		{
			if(this.itemDrops.size() != this.itemRates.size())
			{
				System.out.println("itemDrops.size() != itemRates.size() for " + this.species + "!");
			}
			
			if(this.stealItems.size() != this.stealItemRates.size())
			{
				System.out.println("stealItems.size() != stealItemRates.size() for " + this.species + "!");
			}
		}
		
		this.ct = this.getBaseCT();
	}
	
	public void calculateCubes()
	{
		this.cubes = this.getTotalCubes();
		for(int i=0; i<this.passiveSkills.size(); i++)
		{
			if(this.passiveSkills.get(i).equipped)
			{
				this.cubes -= this.passiveSkills.get(i).cubeCost;
			}
		}
	}
	
	public int getTotalCubes()
	{
		return 12 + this.level/3;
	}
	
	public void calcBaseStats()
	{
		//overridden for each character
	}
	
	public boolean giveExp(int exp)
	{
		boolean leveledUp = false;
		
		this.exp += exp;
		
		while(this.exp > this.getExpNext() && level < 99) //99 is max level
		{
			this.levelUp();
			leveledUp = true;
		}
		
		return leveledUp;
	}
	
	public void levelUp()
	{
		this.exp -= this.getExpNext();
		if(this.exp < 0) this.exp = 0; //shouldn't be possible, but whatever
		
		this.level++;
		
		this.recalculateStats();
	}
	
	public static int getHitRate(Unit attack, Unit target, int hitBonus)
	{
		int hitRate = attack.hitRate - target.evasion + hitBonus;
		
		if(attack.status[Game.STATUS_BLIND] == 1)
		{
			hitRate -= 50;
		}
		if(attack.status[Game.STATUS_BERSERK] == 1)
		{
			hitRate -= 15;
		}
		if(target.status[Game.STATUS_EVADE] == 1)
		{
			hitRate -= 50;
		}
		if(hitRate < 5) hitRate = 5; //5% is the minimum
		
		if(attack.status[Game.STATUS_BLIND] == 1 && hitRate > 80) hitRate = 80;
		if(attack.status[Game.STATUS_BERSERK] == 1 && hitRate > 90) hitRate = 90;
		
		return hitRate;
	}
	
	public boolean hasLearnedActiveSkill(int activeSkillID)
	{
		for(int i=0; i<this.getLearnedActiveSkills().size(); i++)
		{
			if(this.getLearnedActiveSkills().get(i).id == activeSkillID) return true;
		}
		
		return false;
	}
	
	public boolean hasLearnedPassiveSkill(int passiveSkillID)
	{		
		for(int i=0; i<this.getLearnedPassiveSkills().size(); i++)
		{
			if(this.getLearnedPassiveSkills().get(i).id == passiveSkillID) return true;
		}
		
		return false;
	}
	
	public boolean hasEquippedPassiveSkill(int passiveSkillID)
	{
		if(this.passiveSkills == null) //if we check this for a monster, could be null
		{
			return false;
		}
		
		for(int i=0; i<this.getLearnedPassiveSkills().size(); i++)
		{
			if(this.getLearnedPassiveSkills().get(i).id == passiveSkillID && this.getLearnedPassiveSkills().get(i).equipped) return true;
		}
		
		return false;
	}
	
	public double getStatusChance(Unit user, int status, int rate)
	{
		if(this.status[status] != 0) return 0; //already have the status
		
		if(status == Game.STATUS_PROTECT || status == Game.STATUS_SHELL || status == Game.STATUS_HASTE || status == Game.STATUS_REGEN || status == Game.STATUS_DEFEND
				|| status == Game.STATUS_ATKUP || status == Game.STATUS_EVADE || status == Game.STATUS_GOALKEEPER)
		{
			if(this.status[Game.STATUS_SHAME] == 1)
			{
				return 0; //don't allow buffs
			}
			else
			{
				return 100; //don't care about dex or resistance		
			}
		}
		
		rate += 2*user.dex - this.level;
		
		rate *= (100.0 - this.statusResistance[status])/100.0;
		
		return rate;
	}
	
	public void clearModifiedStats()
	{
		if(this.equip[WEAPON].id == Item.NOITEM)
		{
			this.atk = 1; //have a base of 1 when no weapon equipped
		}
		else
		{
			this.atk = 0; //use the weapon's Atk as the base
		}
		
		this.def = 0;
		this.mdef = 0;
		this.hitRate = 100 + this.dex;
		this.evasion = this.spd;
		this.critRate = this.dex/5;
		
		for(int i=0; i<this.elementResistance.length; i++)
		{
			this.elementResistance[i] = 0;
		}
		
		for(int i=0; i<this.statusResistance.length; i++)
		{
			this.statusResistance[i] = 0;
		}
	}
	
	public void recalculateStats()
	{
		this.calcBaseStats(); //Character-specific base stats
		this.clearModifiedStats();
		
		/**
		 * Equipment
		 */
		for(int i=0; i<this.equip.length; i++)
		{
			Item equip = this.equip[i];
			if(equip.id != Item.NOITEM)
			{
				this.maxHp += equip.hpMod;
				this.maxMp += equip.mpMod;
				this.str += equip.strMod;
				this.mag += equip.magMod;
				this.dex += equip.dexMod;
				this.spd += equip.spdMod;
				
				this.atk += equip.atk;
				this.def += equip.def;
				this.mdef += equip.mdef;
				this.hitRate += equip.hitRateMod;
				this.evasion += equip.evasionMod;
				this.critRate += equip.critRateMod;
				
				for(int j=0; j<this.elementResistance.length; j++)
				{
					this.elementResistance[j] += equip.elementResistance[j];
				}
				
				for(int j=0; j<this.statusResistance.length; j++)
				{
					this.statusResistance[j] += equip.statusResistance[j];
				}
				
				//add active skills from equipment
				boolean hasActiveSkill;
				for(int j=0; j<equip.activeSkills.size(); j++)
				{
					hasActiveSkill = false;
					for(int skillIndex=0; skillIndex<this.activeSkills.size(); skillIndex++)
					{
						if(this.activeSkills.get(skillIndex).action.id == equip.activeSkills.get(j).action.id)
						{
							hasActiveSkill = true;
						}
					}
					
					if(!hasActiveSkill)
					{
						this.activeSkills.add(equip.activeSkills.get(j));
					}
				}
				
				//add passive skills from equipment
				boolean hasPassiveSkill;
				for(int j=0; j<equip.passiveSkills.size(); j++)
				{
					if(equip.passiveSkills.get(j).canLearn(this))
					{
						hasPassiveSkill = false;
						for(int skillIndex=0; skillIndex<this.passiveSkills.size(); skillIndex++)
						{
							if(this.passiveSkills.get(skillIndex).id == equip.passiveSkills.get(j).id)
							{
								hasPassiveSkill = true;
							}
						}
						
						if(!hasPassiveSkill)
						{
							this.passiveSkills.add(equip.passiveSkills.get(j));
						}
					}
				}
			}
		}
		
		//make sure the correct active skills are "learned"
		for(int i=0; i<this.activeSkills.size(); i++)
		{
			this.activeSkills.get(i).learned = false;
			
			//if AP is maxed out, the skill is learned
			if(activeSkills.get(i).currentSP >= this.activeSkills.get(i).getCost(this))
			{
				this.activeSkills.get(i).learned = true;
			}
			
			//otherwise, check if the skill is on a piece of equipment
			for(int j=0; j<this.equip.length; j++)
			{
				if(this.equip[j].id != Item.NOITEM)
				{
					for(int skillIndex=0; skillIndex<this.equip[j].activeSkills.size(); skillIndex++)
					{
						if(this.equip[j].activeSkills.get(skillIndex).action.id == this.activeSkills.get(i).action.id)
						{
							this.activeSkills.get(i).learned = true;
						}
					}
				}
			}
		}
		
		//make sure the correct passive skills are "learned"
		for(int i=0; i<this.passiveSkills.size(); i++)
		{
			this.passiveSkills.get(i).learned = false;
			
			//if SP is maxed out, the skill is learned
			if(passiveSkills.get(i).currentSP >= this.passiveSkills.get(i).getCost(this))
			{
				this.passiveSkills.get(i).learned = true;
			}
			
			//otherwise, check if the skill is on a piece of equipment
			for(int j=0; j<this.equip.length; j++)
			{
				if(this.equip[j].id != Item.NOITEM)
				{
					for(int skillIndex=0; skillIndex<this.equip[j].passiveSkills.size(); skillIndex++)
					{
						if(this.equip[j].passiveSkills.get(skillIndex).id == this.passiveSkills.get(i).id)
						{
							this.passiveSkills.get(i).learned = true;
						}
					}
				}
			}
		}
		
		//check for passive skills that are equipped but no longer learned - remove them
		for(int i=0; i<this.passiveSkills.size(); i++)
		{
			if(this.passiveSkills.get(i).equipped && !this.passiveSkills.get(i).learned)
			{
				this.passiveSkills.get(i).equipped = false;
				this.cubes += this.passiveSkills.get(i).cubeCost;
			}
		}
		
		/**
		 * Process passive skills
		 */
		for(int i=0; i<this.getLearnedPassiveSkills().size(); i++)
		{
			if(this.getLearnedPassiveSkills().get(i).equipped)
			{
				this.getLearnedPassiveSkills().get(i).process(this);
			}
		}
		
		if(this.hp > this.maxHp) this.hp = this.maxHp;
		if(this.mp > this.maxMp) this.mp = this.maxMp;
		
		for(int i=0; i<this.statusResistance.length; i++)
		{
			if(this.statusResistance[i] > 100) this.statusResistance[i] = 100;
		}
		
		Collections.sort(this.activeSkills, new ActiveSkillComparator());
		Collections.sort(this.passiveSkills, new PassiveSkillComparator());
	}
	
	public class ActiveSkillComparator implements Comparator<ActiveSkill>
	{
		public int compare(ActiveSkill a, ActiveSkill b)
		{
			if(a.id == b.id) return 0;
			else if(a.id < b.id) return -1;
			else return 1;
		}
	}
	
	public class PassiveSkillComparator implements Comparator<PassiveSkill>
	{
		public int compare(PassiveSkill a, PassiveSkill b)
		{
			if(a.id == b.id) return 0;
			else if(a.id < b.id) return -1;
			else return 1;
		}
	}
	
	public boolean canUseAction(Action action)
	{
		if(this.status[Game.STATUS_SILENCE] > 0) return false;
		
		if(this.mp < action.mp) return false;
		
		if(this.id == Character.HANK && this.equip[WEAPON].weaponType != Item.INSTRUMENTTYPE)
		{
			//Hank requires an instrument unless he's using these skills
			if(action.id != Action.BAJABLAST || action.id == Action.BLUESHIELD || action.id == Action.BLUEBARRIER || action.id == Action.SILLYDANCE)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<ActiveSkill> getLearnedActiveSkills()
	{
		ArrayList<ActiveSkill> list = new ArrayList<ActiveSkill>();
		
		for(int i=0; i<this.activeSkills.size(); i++)
		{
			if(activeSkills.get(i).learned)	list.add(this.activeSkills.get(i));
		}
		
		return list;
	}
	
	public ArrayList<PassiveSkill> getLearnedPassiveSkills()
	{
		ArrayList<PassiveSkill> list = new ArrayList<PassiveSkill>();
		
		for(int i=0; i<this.passiveSkills.size(); i++)
		{
			if(passiveSkills.get(i).learned) list.add(this.passiveSkills.get(i));
		}
		
		return list;
	}
	
	public int indexOfActiveSkill(int activeSkillID)
	{
		for(int i=0; i<this.activeSkills.size(); i++)
		{
			if(activeSkills.get(i).action.id == activeSkillID)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int indexOfPassiveSkill(int passiveSkillID)
	{
		for(int i=0; i<this.passiveSkills.size(); i++)
		{
			if(passiveSkills.get(i).id == passiveSkillID)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public String displayName()
	{
		if(this.unitType == CHARACTER) return this.name;
		else return this.species;
	}
	
	public void healHP(int amt)
	{
		if(this.hp == 0) this.ct = this.getBaseCT(); //reset CT if being revived
		
		this.hp += amt;
		if(this.hp > this.maxHp) this.hp = this.maxHp; 
	}
	
	public String getSkillString()
	{
		switch(this.id)
		{
		case Character.BRIAN: return "Skill";
		case Character.ALEX: return "Folly";
		case Character.HANK: return "Music";
		case Character.MYCHAL: return "Sword Art";
		case Character.KITTEN: return "Feline";
		case Character.KEVBOT: return "Robotics";
		}
		
		return "???";
	}
	
	public void healMP(int amt)
	{
		this.mp += amt;
		if(this.mp > this.maxMp) this.mp = this.maxMp; 
	}
	
	public void fullHeal()
	{
		this.hp = this.maxHp;
		this.mp = this.maxMp;
		
		for(int i=0; i<this.status.length; i++)
		{
			this.status[i] = 0;
		}
		
		this.recalculateStats(); //just in case - maybe they have some sort of auto-status
	}
	
	public void inflictStatus(int status)
	{
		inflictStatus(status, 1); //some statuses last forever, so number of turns can just be 1
	}
	
	public void inflictStatus(int status, int numberOfTurns)
	{
		this.status[status] = numberOfTurns;
		
		if(status == Game.STATUS_HASTE)
		{
			this.status[Game.STATUS_SLOW] = 0;
			
			if(this.ct > this.getBaseCT()) this.ct = (this.ct + this.getBaseCT())/2; //give a CT boost
		}
		else if(status == Game.STATUS_SLOW)
		{
			this.status[Game.STATUS_HASTE] = 0;
			
			this.ct = (this.ct + this.getBaseCT())/2; //give a CT un-boost
		}
		else if(status == Game.STATUS_SHAME)
		{
			this.status[Game.STATUS_PROTECT] = 0;
			this.status[Game.STATUS_SHELL] = 0;
			this.status[Game.STATUS_HASTE] = 0;
			this.status[Game.STATUS_REGEN] = 0;
			this.status[Game.STATUS_DEFEND] = 0;
			this.status[Game.STATUS_ATKUP] = 0;
			this.status[Game.STATUS_EVADE] = 0;
			this.status[Game.STATUS_GOALKEEPER] = 0;
		}
	}
	
	public void decrementStatuses()
	{
		//decrement statuses that wear off in battle
		int[] statusesToDecrement = { Game.STATUS_PROTECT, Game.STATUS_SHELL, Game.STATUS_HASTE, Game.STATUS_SLOW, Game.STATUS_REGEN, Game.STATUS_BERSERK, Game.STATUS_DEFEND,
				Game.STATUS_ATKUP, Game.STATUS_EVADE, Game.STATUS_ATKDOWN, Game.STATUS_GOALKEEPER };
		
		for(int i=0; i<statusesToDecrement.length; i++)
		{
			if(this.status[statusesToDecrement[i]] > 0)
			{
				this.status[statusesToDecrement[i]]--;
			}
		}
	}
	
	public boolean existsAndAlive()
	{
		if(this.id == Character.NONE) return false;
		else if(this.hp == 0) return false;
		
		return true;
	}
	
	public void doDamage(int damage)
	{
		boolean miraclesAreReal = false;
		if(this.hp > 1)
		{
			miraclesAreReal = true; //Miracle should only trigger if you had more than 1 HP
		}
		
		this.hp -= damage;
		
		if(this.hp <= 0) //unit was killed
		{
			boolean miracle = false;
			if(miraclesAreReal && this.hasEquippedPassiveSkill(PassiveSkill.MIRACLE))
			{
				Random rand = new Random();
				if(rand.nextInt(100) < 3 + this.dex)
				{
					this.hp = 1;
					miracle = true;
				}
			}
			
			if(!miracle)
			{
				this.hp = 0; //don't want negative HP
				this.clearAllStatuses(); //if revived, shouldn't still have statuses
			}
		}
	}
	
	public int getExpNext()
	{
		//TODO: some kind of exponential formula here
		if(this.level == 99) return -1;
		else return 1+100*this.level;
	}
	
	public boolean checkFlags()
	{
		//Flag set to 2 means we should trigger that effect, only want to trigger once
		
		if(this.status[Game.STATUS_POISON] == 1)
		{
			this.status[Game.STATUS_POISON] = -1;
			return true;
		}
		
		if(this.status[Game.STATUS_REGEN] > 0 && this.hp < this.maxHp)
		{
			this.status[Game.STATUS_REGEN] = this.status[Game.STATUS_REGEN] * -1;
			return true;
		}
		
		return false;
	}
	
	public void clearFlags()
	{
		//Clear the flags
		
		if(this.status[Game.STATUS_POISON] == -1)
		{
			this.status[Game.STATUS_POISON] = 1;
		}
		
		if(this.status[Game.STATUS_REGEN] < 0)
		{
			this.status[Game.STATUS_REGEN] = this.status[Game.STATUS_REGEN] * -1;
		}
	}
	
	public void clearTempStatuses()
	{
		//these statuses should go away when a battle ends
		
		int[] tempStatuses = { Game.STATUS_PROTECT, Game.STATUS_SHELL, Game.STATUS_HASTE, Game.STATUS_SLOW, Game.STATUS_REGEN, Game.STATUS_BERSERK, Game.STATUS_DEFEND,
				Game.STATUS_ATKUP, Game.STATUS_EVADE, Game.STATUS_SHAME, Game.STATUS_AMP, Game.STATUS_ATKDOWN, Game.STATUS_GOALKEEPER };
		
		for(int i=0; i<tempStatuses.length; i++)
		{
			this.status[tempStatuses[i]] = 0;
		}
	}
	
	public void clearAllStatuses()
	{
		for(int i=0; i<this.status.length; i++)
		{
			this.status[i] = 0;
		}
	}
	
	public int getBaseCT()
	{
		//TODO: rework this formula?
		
		int ct = 250 / (this.spd + 3) - this.spd/7;
		
		if(this.status[Game.STATUS_HASTE] == 1) ct /= 2;
		else if(this.status[Game.STATUS_SLOW] == 1) ct *= 2;
		
		if(ct < 3) ct = 3;
		
		return ct;
	}
	
	public static Unit characterFromID(int id, int level, int index)
	{
		switch(id)
		{
			case Character.BRIAN: return new Brian(level, index);
			case Character.ALEX: return new Alex(level, index);
			case Character.HANK: return new Hank(level, index);
			case Character.MYCHAL: return new Mychal(level, index);
			case Character.KITTEN: return new Kitten(level, index);
			case Character.KEVBOT: return new KevBot(level, index);
		}
		
		return new None(index);
	}
	
	public int getDef()
	{
		if(this.status[Game.STATUS_VIRUS] > 0)
		{
			return this.def/3; //TODO: might need to rework this (same with mdef)
		}
		
		return this.def;
	}
	
	public int getMdef()
	{
		if(this.status[Game.STATUS_VIRUS] > 0)
		{
			return this.mdef/3;
		}
		
		return this.mdef;
	}
	
	public int getDmgLowerBound()
	{
		double lowerBound = 75 + 2.5*Math.sqrt(this.dex);
		
		return Math.min((int) lowerBound, 95);
	}
	
	public void resetBeforeBattle()
	{		
		this.resetCT();
		
		this.actionHistory = new ArrayList<Integer>();
	}
	
	public void resetCT()
	{		
		this.ct = this.getBaseCT();
	}
}
