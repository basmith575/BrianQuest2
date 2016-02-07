package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

public class Unit
{
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
	public static final int NUMSTATUS = 15;
	public static final int POISON = 0;
	public static final int SILENCE = 1;
	public static final int BLIND = 2;
	public static final int SLEEP = 3;
	public static final int PROTECT = 4;
	public static final int SHELL = 5;
	public static final int HASTE = 6;
	public static final int SLOW = 7;
	public static final int REGEN = 8;
	public static final int BERSERK = 9;
	public static final int DEFEND = 10;
	public static final int ATKUP = 11;
	public static final int EVADE = 12;
	public static final int SHAME = 13;
	public static final int AMP = 14;
	
	public int[] elementResistance;
	public int[] statusResistance;
	
	public int cubes;
	
	public int imageWidth; //used for drawing
	public int imageHeight; //used for drawing
	public int spriteHeight; //might have white space offset in image
	
	/**
	 * Monster specific
	 */
	public String species;
	public int expGain;		//how much exp it gives
	public double spGain; //how many cubes it gives (rounds up to nearest int)
	public int moneyGain;	//how much money it gives
	public ArrayList<Item> itemDrops;
	public ArrayList<Integer> itemRates;
	public ArrayList<Item> stealItems;
	public ArrayList<Integer> stealItemRates;
	public String attackAnimation;
	public boolean murderable; //does Murder work on it?
	
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
		
		this.status = new int[NUMSTATUS];
		for(int i=0; i<this.status.length; i++) this.status[i] = unit.status[i];
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		for(int i=0; i<this.elementResistance.length; i++) this.elementResistance[i] = unit.elementResistance[i];
		
		this.statusResistance = new int[NUMSTATUS];
		for(int i=0; i<this.statusResistance.length; i++) this.statusResistance[i] = unit.statusResistance[i];
		
		this.imageHeight = unit.imageHeight;
		this.imageWidth = unit.imageWidth;
		this.spriteHeight = unit.spriteHeight;
		
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
			
			this.murderable = unit.murderable;
		}
	}
	
	public static Unit getCopyCharacter(Unit character)
	{
		if(character instanceof Brian) return new Brian((Brian) character);
		else if(character instanceof Alex) return new Alex((Alex) character);
		else if(character instanceof Ryan) return new Ryan((Ryan) character);
		else if(character instanceof Mychal) return new Mychal((Mychal) character);
		else if(character instanceof Kitten) return new Kitten((Kitten) character);
		
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
	
	public void initialize()
	{
		//things that get initialized the same for every character
		
		this.status = new int[NUMSTATUS];
		for(int i=0; i<this.status.length; i++)
		{
			this.status[i] = 0;
		}
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		for(int i=0; i<this.elementResistance.length; i++)
		{
			this.elementResistance[i] = 0;
		}
		
		this.statusResistance = new int[NUMSTATUS];
		for(int i=0; i<this.statusResistance.length; i++)
		{
			this.statusResistance[i] = 0;
		}
		
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
		
		if(attack.status[Unit.BLIND] == 1)
		{
			hitRate -= 50;
		}
		if(attack.status[Unit.BERSERK] == 1)
		{
			hitRate -= 15;
		}
		if(target.status[Unit.EVADE] == 1)
		{
			hitRate -= 50;
		}
		if(hitRate < 5) hitRate = 5; //5% is the minimum
		
		if(attack.status[Unit.BLIND] == 1 && hitRate > 80) hitRate = 80;
		if(attack.status[Unit.BERSERK] == 1 && hitRate > 90) hitRate = 90;
		
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
		for(int i=0; i<this.getLearnedPassiveSkills().size(); i++)
		{
			if(this.getLearnedPassiveSkills().get(i).id == passiveSkillID && this.getLearnedPassiveSkills().get(i).equipped) return true;
		}
		
		return false;
	}
	
	public double getStatusChance(Unit user, int status, int rate)
	{
		if(this.status[status] != 0) return 0; //already have the status
		
		if(status == PROTECT || status == SHELL || status == HASTE || status == REGEN || status == DEFEND || status == ATKUP || status == EVADE)
		{
			if(this.status[SHAME] == 1) return 0; //don't allow buffs
			else return 100; //don't care about dex or resistance		
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
		
		for(int i=0; i<this.elementResistance.length; i++) this.elementResistance[i] = 0;
		for(int i=0; i<this.statusResistance.length; i++) this.statusResistance[i] = 0;
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
	
	public boolean canUseSkill(Action skill)
	{
		if(this.status[SILENCE] > 0) return false;
		
		if(this.mp < skill.mp) return false;
		
		if(this.id == Character.RYAN && this.equip[WEAPON].weaponType != Item.INSTRUMENTTYPE)
		{
			return false; //Ryan has to have an instrument equipped to use skills
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
		case Character.RYAN: return "Music";
		case Character.MYCHAL: return "Sword Art";
		case Character.KITTEN: return "Feline";
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
		
		if(status == HASTE)
		{
			this.status[SLOW] = 0;
			
			if(this.ct > this.getBaseCT()) this.ct = (this.ct + this.getBaseCT())/2; //give a CT boost
		}
		else if(status == SLOW)
		{
			this.status[HASTE] = 0;
			
			this.ct = (this.ct + this.getBaseCT())/2; //give a CT un-boost
		}
		else if(status == SHAME)
		{
			this.status[PROTECT] = 0;
			this.status[SHELL] = 0;
			this.status[HASTE] = 0;
			this.status[REGEN] = 0;
			this.status[DEFEND] = 0;
			this.status[ATKUP] = 0;
			this.status[EVADE] = 0;
		}
	}
	
	public void decrementStatuses()
	{
		//decrement statuses that wear off in battle
		if(this.status[Unit.PROTECT] > 0) this.status[Unit.PROTECT]--;
		if(this.status[Unit.SHELL] > 0) this.status[Unit.SHELL]--;
		if(this.status[Unit.SLOW] > 0) this.status[Unit.SLOW]--;
		if(this.status[Unit.HASTE] > 0) this.status[Unit.HASTE]--;
		if(this.status[Unit.REGEN] > 0) this.status[Unit.REGEN]--;
		if(this.status[Unit.BERSERK] > 0) this.status[Unit.BERSERK]--;
		if(this.status[Unit.DEFEND] > 0) this.status[Unit.DEFEND]--;
		if(this.status[Unit.ATKUP] > 0) this.status[Unit.ATKUP]--;
		if(this.status[Unit.EVADE] > 0) this.status[Unit.EVADE]--;
	}
	
	public boolean existsAndAlive()
	{
		if(this.id == Character.NONE) return false;
		else if(this.hp == 0) return false;
		
		return true;
	}
	
	public void doDamage(int damage)
	{
		this.hp -= damage;
		if(this.hp < 0)
		{
			this.hp = 0;
			this.clearAllStatuses();
		}
	}
	
	public int getExpNext()
	{
		//TODO
		if(this.level == 99) return -1;
		else return 1+100*this.level;
	}
	
	public boolean checkFlags()
	{
		//Flag set to 2 means we should trigger that effect, only want to trigger once
		
		if(this.status[POISON] == 1)
		{
			this.status[POISON] = -1;
			return true;
		}
		
		if(this.status[REGEN] > 0 && this.hp < this.maxHp)
		{
			this.status[REGEN] = this.status[REGEN] * -1;
			return true;
		}
		
		return false;
	}
	
	public void clearFlags()
	{
		//Clear the flags
		
		if(this.status[POISON] == -1)
		{
			this.status[POISON] = 1;
		}
		
		if(this.status[REGEN] < 0)
		{
			this.status[REGEN] = this.status[REGEN] * -1;
		}
	}
	
	public void clearTempStatuses()
	{
		//these statuses should go away when a battle ends
		this.status[PROTECT] = 0;
		this.status[SHELL] = 0;
		this.status[HASTE] = 0;
		this.status[SLOW] = 0;
		this.status[REGEN] = 0;
		this.status[BERSERK] = 0;
		this.status[DEFEND] = 0;
		this.status[ATKUP] = 0;
		this.status[EVADE] = 0;
		this.status[SHAME] = 0;
		this.status[AMP] = 0;
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
		
		if(this.status[HASTE] == 1) ct /= 2;
		else if(this.status[SLOW] == 1) ct *= 2;
		
		if(ct < 3) ct = 3;
		
		return ct;
	}
	
	public static Unit characterFromID(int id, int level, int index)
	{
		switch(id)
		{
			case Character.BRIAN: return new Brian(level, index);
			case Character.ALEX: return new Alex(level, index);
			case Character.RYAN: return new Ryan(level, index);
			case Character.MYCHAL: return new Mychal(level, index);
			case Character.KITTEN: return new Kitten(level, index);
		}
		
		return new None();
	}
	
	public static Unit monsterFromID(int id, int level)
	{
		switch(id)
		{
			case Monster.SNAKE: return new Snake();
		}
		
		return new None();
	}
	
	public int getDmgLowerBound()
	{
		double lowerBound = 75 + 2.5*Math.sqrt(this.dex);
		
		return Math.min((int) lowerBound, 95);
	}
	
	public void resetCT()
	{		
		this.ct = this.getBaseCT();
	}
}