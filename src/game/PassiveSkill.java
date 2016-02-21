package game;

public class PassiveSkill
{
	public static final int NUMPASSIVESKILLS = 72;
	
	public static final int HPPLUS10 = 0;					//Raises Max HP by 10%
	public static final int HPPLUS20 = 1;					//Raises Max HP by 20%
	public static final int MPPLUS10 = 2;					//Raises Max MP by 10%
	public static final int MPPLUS20 = 3;					//Raises Max MP by 20%
	public static final int STRPLUS1 = 4;					//Raises Str by 1
	public static final int STRPLUS2 = 5;					//Raises Str by 2
	public static final int MAGPLUS1 = 6;					//Raises Mag by 1
	public static final int MAGPLUS2 = 7;					//Raises Mag by 2
	public static final int DEXPLUS1 = 8;					//Raises Dex by 1
	public static final int DEXPLUS2 = 9;					//Raises Dex by 2
	public static final int SPDPLUS1 = 10;					//Raises Spd by 1
	public static final int SPDPLUS2 = 11;					//Raises Spd by 2
	public static final int DEFPLUS1 = 12;					//Raises Def by 1
	public static final int DEFPLUS2 = 13;					//Raises Def by 2
	public static final int MDEFPLUS1 = 14;					//Raises MDef by 1
	public static final int MDEFPLUS2 = 15;					//Raises MDef by 2
	public static final int CRITPLUS5 = 16;					//Raises Crit by 10
	public static final int HITPLUS10 = 17;					//Raises Hit by 10
	public static final int EVASIONPLUS10 = 18;				//Raises Evasion by 10
	public static final int EVASIONPLUS15 = 19;				//Raises Evasion by 15
	
	//Brian
	public static final int DRAINLIFE = 20;					//Normal attacks heal for half the damage dealt
	public static final int SNACKELEMENTRESIST = 21;		//Raises Snack element resistance (% based on Dex)
	public static final int SNACKELEMENTATTACK = 22;		//Makes attacks do Snack element damage (% based on Dex)
	
	//Alex
	public static final int MESCARED = 23;					//Makes Flee command always work
	public static final int MIRACLE = 24;					//Sometimes survive lethal damage with 1 HP
	public static final int IGNORANCEISBLISS = 25;			//Removes negative statuses at the end of battle
	public static final int FOOLSFORTUNE = 26;				//Raises item drop %
	public static final int ENLIGHTENMENT = 27;				//Boosts all stats a lot
	public static final int EARTHELEMENTRESIST = 28;		//Raises Earth element resistance (% based on Dex)
	public static final int EARTHELEMENTATTACK = 29;		//Makes attacks do Earth element damage (% based on Dex)
	
	//Ryan
	public static final int MPATTACK = 30;					//Normal attacks deal boosted damage but consume MP
	public static final int WATERELEMENTRESIST = 31;		//Raises Water element resistance (% based on Dex)
	public static final int WATERELEMENTATTACK = 32;		//Makes attacks do Water element damage (% based on Dex)
	
	//Mychal
	public static final int SOLOARTIST = 33;				//Damage is boosted while other party members are dead
	public static final int FIREELEMENTRESIST = 34;			//Raises Fire element resistance (% based on Dex)
	public static final int FIREELEMENTATTACK = 35;			//Makes attacks do Fire element damage (% based on Dex)
	
	//Kitten
	public static final int CATBURGLAR = 36;				//Raises Steal % rate
	public static final int MUG = 37;						//Turns Steal into Mug
	public static final int PAYDAY = 38;					//Get more money after battle
	public static final int CATEYES = 39;					//Reveals hidden chests
	public static final int POISONELEMENTRESIST = 40;		//Raises Poison element resistance (% based on Dex)
	public static final int POISONELEMENTATTACK = 41;		//Makes attacks do Poison element damage (% based on Dex)
	
	//Kev-Bot
	public static final int SCAN = 42;						//Show enemy HP in battle
	public static final int RNGDEFENSES = 43;				//Randomizes damage taken (0.5x-1.5x)
	public static final int DESTROYHUMANS = 44;				//Deal increased damage to Human targets
	public static final int LIGHTNINGELEMENTRESIST = 45;	//Raises Lightning element resistance (% based on Dex)
	public static final int LIGHTNINGELEMENTATTACK = 46;	//Makes attacks do Lightning element damage (% based on Dex)
	
	
	public static final int POISONSTATUSRESIST = 47;		//Increase Poison status resistance (% based on Dex)		TODO: have actual names for these passives
	public static final int SILENCESTATUSRESIST = 48;		//Increase Silence status resistance (% based on Dex)
	public static final int BLINDSTATUSRESIST = 49;			//Increase Blind status resistance (% based on Dex)
	public static final int SLEEPSTATUSRESIST = 50;			//Increase Sleep status resistance (% based on Dex)
	public static final int SLOWSTATUSRESIST = 51;			//Increase Slow status resistance (% based on Dex)
	public static final int BERSERKSTATUSRESIST = 52;		//Increase Berserk status resistance (% based on Dex)
	public static final int SHAMESTATUSRESIST = 53;			//Increase Shame status resistance (% based on Dex)
	public static final int INFLICTSTATUSPOISON = 54;		//Attacks may inflict Poison status (% based on Dex)
	public static final int INFLICTSTATUSSILENCE = 55;		//Attacks may inflict Silence status (% based on Dex)
	public static final int INFLICTSTATUSBLIND = 56;		//Attacks may inflict Blind status (% based on Dex)
	public static final int INFLICTSTATUSSLEEP = 57;		//Attacks may inflict Sleep status (% based on Dex)
	public static final int INFLICTSTATUSSLOW = 58;			//Attacks may inflict Slow status (% based on Dex)
	public static final int INFLICTSTATUSBERSERK = 59;		//Attacks may inflict Berserk status (% based on Dex)
	public static final int INFLICTSTATUSSHAME = 60;		//Attacks may inflict Shame status (% based on Dex)
	public static final int ITEMADDICT = 61;				//Raises effectiveness of items
	public static final int AUTOREGEN = 62;					//Start the battle with Regen status
	public static final int AUTOHASTE = 63;					//Start the battle with Haste status
	public static final int AUTOBERSERK = 64;				//Start the battle with Berserk status
	public static final int AUTOPROTECT = 65;				//Start the battle with Protect status
	public static final int AUTOSHELL = 66;					//Start the battle with Shell status
	public static final int EXPBOOST = 67;					//Gain increased EXP
	public static final int SPBOOST = 68;					//Gain increased SP
	public static final int ENCOUNTERHALF = 69;				//Halves the random encounter rate
	public static final int ENCOUNTERNONE = 70;				//No random encounters
	public static final int MPHALF = 71;					//Skills cost half MP (rounded up)
	
	public int id;
	public String name;
	public String desc;
	public int currentSP;
	public boolean learned;
	public boolean equipped;
	public int cubeCost;
	
	public PassiveSkill()
	{
		
	}
	
	public static PassiveSkill duplicate(PassiveSkill passiveSkill)
	{
		return passiveSkillFromID(passiveSkill.id, passiveSkill.currentSP, passiveSkill.learned, passiveSkill.equipped);
	}
	
	public static PassiveSkill passiveSkillFromID(int id, int currentSP, boolean learned, boolean equipped)
	{
		switch(id)
		{
			case HPPLUS10: return new HPPlus10(currentSP, learned, equipped);
			case HPPLUS20: return new HPPlus20(currentSP, learned, equipped);
			case MPPLUS10: return new MPPlus10(currentSP, learned, equipped);
			case MPPLUS20: return new MPPlus20(currentSP, learned, equipped);
			case STRPLUS1: return new StrPlus1(currentSP, learned, equipped);
			case STRPLUS2: return new StrPlus2(currentSP, learned, equipped);
			case MAGPLUS1: return new MagPlus1(currentSP, learned, equipped);
			case MAGPLUS2: return new MagPlus2(currentSP, learned, equipped);
			case DEXPLUS1: return new DexPlus1(currentSP, learned, equipped);
			case DEXPLUS2: return new DexPlus2(currentSP, learned, equipped);
			case SPDPLUS1: return new SpdPlus1(currentSP, learned, equipped);
			case SPDPLUS2: return new SpdPlus2(currentSP, learned, equipped);
			case DEFPLUS1: return new DefPlus1(currentSP, learned, equipped);
			case DEFPLUS2: return new DefPlus2(currentSP, learned, equipped);
			case MDEFPLUS1: return new MDefPlus1(currentSP, learned, equipped);
			case MDEFPLUS2: return new MDefPlus2(currentSP, learned, equipped);
			case CRITPLUS5: return new CritPlus5(currentSP, learned, equipped);
			case HITPLUS10: return new HitPlus10(currentSP, learned, equipped);
			case EVASIONPLUS10: return new EvasionPlus10(currentSP, learned, equipped);
			case EVASIONPLUS15: return new EvasionPlus15(currentSP, learned, equipped);
			
			//Brian
			case DRAINLIFE: return new DrainLife(currentSP, learned, equipped);
			case SNACKELEMENTRESIST: return new SnackElementResist(currentSP, learned, equipped);
			case SNACKELEMENTATTACK: return new SnackElementAttack(currentSP, learned, equipped);
			
			//Alex
			case MESCARED: return new MeScared(currentSP, learned, equipped);
			case MIRACLE: return new Miracle(currentSP, learned, equipped);
			case IGNORANCEISBLISS: return new IgnoranceIsBliss(currentSP, learned, equipped);
			case FOOLSFORTUNE: return new FoolsFortune(currentSP, learned, equipped);
			case ENLIGHTENMENT: return new Enlightenment(currentSP, learned, equipped);
			case EARTHELEMENTRESIST: return new EarthElementResist(currentSP, learned, equipped);
			case EARTHELEMENTATTACK: return new EarthElementAttack(currentSP, learned, equipped);
			
			//Ryan
			case MPATTACK: return new MPAttack(currentSP, learned, equipped);
			case WATERELEMENTRESIST: return new WaterElementResist(currentSP, learned, equipped);
			case WATERELEMENTATTACK: return new WaterElementAttack(currentSP, learned, equipped);
			
			//Mychal
			case SOLOARTIST: return new SoloArtist(currentSP, learned, equipped);
			case FIREELEMENTRESIST: return new FireElementResist(currentSP, learned, equipped);
			case FIREELEMENTATTACK: return new FireElementAttack(currentSP, learned, equipped);
			
			//Kitten
			case CATBURGLAR: return new CatBurglar(currentSP, learned, equipped);
			case MUG: return new Mug(currentSP, learned, equipped);
			case PAYDAY: return new PayDay(currentSP, learned, equipped);
			case CATEYES: return new CatEyes(currentSP, learned, equipped);
			case POISONELEMENTRESIST: return new PoisonElementResist(currentSP, learned, equipped);
			case POISONELEMENTATTACK: return new PoisonElementAttack(currentSP, learned, equipped);
			
			//Kev-Bot
			case SCAN: return new Scan(currentSP, learned, equipped);
			case RNGDEFENSES: return new RNGDefenses(currentSP, learned, equipped);
			case DESTROYHUMANS: return new DestroyHumans(currentSP, learned, equipped);
			case LIGHTNINGELEMENTRESIST: return new LightningElementResist(currentSP, learned, equipped);
			case LIGHTNINGELEMENTATTACK: return new LightningElementAttack(currentSP, learned, equipped);
			
			case POISONSTATUSRESIST: return new PoisonStatusResist(currentSP, learned, equipped);
			case SILENCESTATUSRESIST: return new SilenceStatusResist(currentSP, learned, equipped);
			case BLINDSTATUSRESIST: return new BlindStatusResist(currentSP, learned, equipped);
			case SLEEPSTATUSRESIST: return new SleepStatusResist(currentSP, learned, equipped);
			case SLOWSTATUSRESIST: return new SlowStatusResist(currentSP, learned, equipped);
			case BERSERKSTATUSRESIST: return new BerserkStatusResist(currentSP, learned, equipped);
			case SHAMESTATUSRESIST: return new ShameStatusResist(currentSP, learned, equipped);
			case INFLICTSTATUSPOISON: return new InflictStatusPoison(currentSP, learned, equipped);
			case INFLICTSTATUSSILENCE: return new InflictStatusSilence(currentSP, learned, equipped);
			case INFLICTSTATUSBLIND: return new InflictStatusBlind(currentSP, learned, equipped);
			case INFLICTSTATUSSLEEP: return new InflictStatusSleep(currentSP, learned, equipped);
			case INFLICTSTATUSBERSERK: return new InflictStatusBerserk(currentSP, learned, equipped);
			case INFLICTSTATUSSLOW: return new InflictStatusSlow(currentSP, learned, equipped);
			case INFLICTSTATUSSHAME: return new InflictStatusShame(currentSP, learned, equipped);
			case ITEMADDICT: return new ItemAddict(currentSP, learned, equipped);
			case AUTOREGEN: return new AutoRegen(currentSP, learned, equipped);
			case AUTOHASTE: return new AutoHaste(currentSP, learned, equipped);
			case AUTOBERSERK: return new AutoBerserk(currentSP, learned, equipped);
			case AUTOPROTECT: return new AutoProtect(currentSP, learned, equipped);
			case AUTOSHELL: return new AutoShell(currentSP, learned, equipped);
			case EXPBOOST: return new EXPBoost(currentSP, learned, equipped);
			case SPBOOST: return new SPBoost(currentSP, learned, equipped);
			case ENCOUNTERHALF: return new EncounterHalf(currentSP, learned, equipped);
			case ENCOUNTERNONE: return new EncounterNone(currentSP, learned, equipped);
			case MPHALF: return new MPHalf(currentSP, learned, equipped);
		}
		
		return null;
	}
	
	public void process(Unit character)
	{
		//by default do nothing
		//overridden in child classes
	}
	
	public boolean canLearn(Unit character)
	{
		return false;
	}
	
	public int getCost(Unit character)
	{
		return -1;
	}
}

class HPPlus10 extends PassiveSkill
{
	public HPPlus10()
	{
		this(0, false, false);
	}
	
	public HPPlus10(int currentSP, boolean learned, boolean equipped)
	{
		this.id = HPPLUS10;
		this.name = "HP +10%";
		this.desc = "Raises Maximum HP by 10%.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.maxHp *= 1.1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class HPPlus20 extends PassiveSkill
{
	public HPPlus20()
	{
		this(0, false, false);
	}
	
	public HPPlus20(int currentSP, boolean learned, boolean equipped)
	{
		this.id = HPPLUS20;
		this.name = "HP +20%";
		this.desc = "Raises Maximum HP by 20%.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.maxHp *= 1.2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.MYCHAL)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class MPPlus10 extends PassiveSkill
{
	public MPPlus10()
	{
		this(0, false, false);
	}
	
	public MPPlus10(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MPPLUS10;
		this.name = "MP +10%";
		this.desc = "Raises Maximum MP by 10%.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.maxMp *= 1.1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class MPPlus20 extends PassiveSkill
{
	public MPPlus20()
	{
		this(0, false, false);
	}
	
	public MPPlus20(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MPPLUS20;
		this.name = "MP +20%";
		this.desc = "Raises Maximum MP by 20%.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.maxMp *= 1.2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class StrPlus1 extends PassiveSkill
{
	public StrPlus1()
	{
		this(0, false, false);
	}
	
	public StrPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = STRPLUS1;
		this.name = "Str +1";
		this.desc = "Raises Strength by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.str += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class StrPlus2 extends PassiveSkill
{
	public StrPlus2()
	{
		this(0, false, false);
	}
	
	public StrPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = STRPLUS2;
		this.name = "Str +2";
		this.desc = "Raises Strength by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.str += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN || character.id == Character.MYCHAL)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class MagPlus1 extends PassiveSkill
{
	public MagPlus1()
	{
		this(0, false, false);
	}
	
	public MagPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MAGPLUS1;
		this.name = "Mag +1";
		this.desc = "Raises Magic by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.mag += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class MagPlus2 extends PassiveSkill
{
	public MagPlus2()
	{
		this(0, false, false);
	}
	
	public MagPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MAGPLUS2;
		this.name = "Mag +2";
		this.desc = "Raises Magic by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.mag += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN || character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class DexPlus1 extends PassiveSkill
{
	public DexPlus1()
	{
		this(0, false, false);
	}
	
	public DexPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DEXPLUS1;
		this.name = "Dex +1";
		this.desc = "Raises Dexterity by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.dex += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class DexPlus2 extends PassiveSkill
{
	public DexPlus2()
	{
		this(0, false, false);
	}
	
	public DexPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DEXPLUS2;
		this.name = "Dex +2";
		this.desc = "Raises Dexterity by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.dex += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.RYAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class SpdPlus1 extends PassiveSkill
{
	public SpdPlus1()
	{
		this(0, false, false);
	}
	
	public SpdPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SPDPLUS1;
		this.name = "Spd +1";
		this.desc = "Raises Speed by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.spd += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class SpdPlus2 extends PassiveSkill
{
	public SpdPlus2()
	{
		this(0, false, false);
	}
	
	public SpdPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SPDPLUS2;
		this.name = "Spd +2";
		this.desc = "Raises Speed by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.spd += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN || character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class DefPlus1 extends PassiveSkill
{
	public DefPlus1()
	{
		this(0, false, false);
	}
	
	public DefPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DEFPLUS1;
		this.name = "Def +1";
		this.desc = "Raises Defense by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.def += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class DefPlus2 extends PassiveSkill
{
	public DefPlus2()
	{
		this(0, false, false);
	}
	
	public DefPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DEFPLUS2;
		this.name = "Def +2";
		this.desc = "Raises Defense by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.def += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.MYCHAL || character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class MDefPlus1 extends PassiveSkill
{
	public MDefPlus1()
	{
		this(0, false, false);
	}
	
	public MDefPlus1(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MDEFPLUS1;
		this.name = "M Def +1";
		this.desc = "Raises Magic Defense by 1.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.mdef += 1;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class MDefPlus2 extends PassiveSkill
{
	public MDefPlus2()
	{
		this(0, false, false);
	}
	
	public MDefPlus2(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MDEFPLUS2;
		this.name = "M Def +2";
		this.desc = "Raises Magic Defense by 2.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.mdef += 2;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.RYAN || character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class CritPlus5 extends PassiveSkill
{
	public CritPlus5()
	{
		this(0, false, false);
	}
	
	public CritPlus5(int currentSP, boolean learned, boolean equipped)
	{
		this.id = CRITPLUS5;
		this.name = "Crit +5%";
		this.desc = "Raises Crit Rate by 5%.";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.critRate += 5;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 35;
	}
}

class HitPlus10 extends PassiveSkill
{
	public HitPlus10()
	{
		this(0, false, false);
	}
	
	public HitPlus10(int currentSP, boolean learned, boolean equipped)
	{
		this.id = HITPLUS10;
		this.name = "Hit Rate +10%";
		this.desc = "Raises Hit Rate by 10%.";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.hitRate += 10;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class EvasionPlus10 extends PassiveSkill
{
	public EvasionPlus10()
	{
		this(0, false, false);
	}
	
	public EvasionPlus10(int currentSP, boolean learned, boolean equipped)
	{
		this.id = EVASIONPLUS10;
		this.name = "Evasion +10%";
		this.desc = "Raises Evasion by 10%.";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.evasion += 10;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class EvasionPlus15 extends PassiveSkill
{
	public EvasionPlus15()
	{
		this(0, false, false);
	}
	
	public EvasionPlus15(int currentSP, boolean learned, boolean equipped)
	{
		this.id = EVASIONPLUS15;
		this.name = "Evasion +15%";
		this.desc = "Raises Evasion by 15%.";
		this.cubeCost = 5;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		character.evasion += 15;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class DrainLife extends PassiveSkill
{
	public DrainLife()
	{
		this(0, false, false);
	}
	
	public DrainLife(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DRAINLIFE;
		this.name = "Drain Life";
		this.desc = "Attacks heal for portion of damage (% based on Dex).";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//checks this in Game.doBattleCalculations()
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 120;
	}
}

class SnackElementResist extends PassiveSkill
{
	public SnackElementResist()
	{
		this(0, false, false);
	}
	
	public SnackElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SNACKELEMENTRESIST;
		this.name = "Snack Ele Resist";
		this.desc = "Raises Snack Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.SNACK] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class SnackElementAttack extends PassiveSkill
{
	public SnackElementAttack()
	{
		this(0, false, false);
	}
	
	public SnackElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SNACKELEMENTATTACK;
		this.name = "Snack Ele Attack";
		this.desc = "Weapon becomes Snack element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.BRIAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class MeScared extends PassiveSkill
{
	public MeScared()
	{
		this(0, false, false);
	}
	
	public MeScared(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MESCARED;
		this.name = "Me Scared!";
		this.desc = "Fleeing battle will always succeed when possible.";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this when calculating flee rate
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 20;
	}
}

class Miracle extends PassiveSkill
{
	public Miracle()
	{
		this(0, false, false);
	}
	
	public Miracle(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MIRACLE;
		this.name = "Miracle";
		this.desc = "Sometimes survive lethal damage with 1 HP.";
		this.cubeCost = 5;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Unit.doDamage()
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class IgnoranceIsBliss extends PassiveSkill
{
	public IgnoranceIsBliss()
	{
		this(0, false, false);
	}
	
	public IgnoranceIsBliss(int currentSP, boolean learned, boolean equipped)
	{
		this.id = IGNORANCEISBLISS;
		this.name = "Ignorance Is Bliss";
		this.desc = "Negative status effects go away after battle.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this when battle ends
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 35;
	}
}

class FoolsFortune extends PassiveSkill
{
	public FoolsFortune()
	{
		this(0, false, false);
	}
	
	public FoolsFortune(int currentSP, boolean learned, boolean equipped)
	{
		this.id = FOOLSFORTUNE;
		this.name = "Fool's Fortune";
		this.desc = "Raises chance of getting items after battle.";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this when calculating drops
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class Enlightenment extends PassiveSkill
{
	public Enlightenment()
	{
		this(0, false, false);
	}
	
	public Enlightenment(int currentSP, boolean learned, boolean equipped)
	{
		this.id = ENLIGHTENMENT;
		this.name = "Enlightenment";
		this.desc = "Cast away foolishness and become enlightened.";
		this.cubeCost = 12;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//TODO: use different stat formulas entirely?
		
		character.maxHp *= 1.2;
		character.maxMp *= 1.2;
		character.str += 3;
		character.mag += 3;
		character.dex += 3;
		character.spd += 3;
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 200;
	}
}

class EarthElementResist extends PassiveSkill
{
	public EarthElementResist()
	{
		this(0, false, false);
	}
	
	public EarthElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = EARTHELEMENTRESIST;
		this.name = "Earth Ele Resist";
		this.desc = "Raises Earth Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.EARTH] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class EarthElementAttack extends PassiveSkill
{
	public EarthElementAttack()
	{
		this(0, false, false);
	}
	
	public EarthElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = EARTHELEMENTATTACK;
		this.name = "Earth Ele Attack";
		this.desc = "Weapon becomes Earth element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.ALEX)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class MPAttack extends PassiveSkill
{
	public MPAttack()
	{
		this(0, false, false);
	}
	
	public MPAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MPATTACK;
		this.name = "MP Attack";
		this.desc = "Attacks deal more damage but consume MP."; //consume 5% for 1.5x damage?
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() and when checking for action's MP cost
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.RYAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 120;
	}
}

class WaterElementResist extends PassiveSkill
{
	public WaterElementResist()
	{
		this(0, false, false);
	}
	
	public WaterElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = WATERELEMENTRESIST;
		this.name = "Water Ele Resist";
		this.desc = "Raises Water Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.WATER] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.RYAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class WaterElementAttack extends PassiveSkill
{
	public WaterElementAttack()
	{
		this(0, false, false);
	}
	
	public WaterElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = WATERELEMENTATTACK;
		this.name = "Water Ele Attack";
		this.desc = "Weapon becomes Water element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.RYAN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class SoloArtist extends PassiveSkill
{
	public SoloArtist()
	{
		this(0, false, false);
	}
	
	public SoloArtist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SOLOARTIST;
		this.name = "Solo Artist";
		this.desc = "Deal more damage if no other party member is alive.";
		this.cubeCost = 5;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.MYCHAL)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 80;
	}
}

class FireElementResist extends PassiveSkill
{
	public FireElementResist()
	{
		this(0, false, false);
	}
	
	public FireElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = FIREELEMENTRESIST;
		this.name = "Fire Ele Resist";
		this.desc = "Raises Fire Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.FIRE] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.MYCHAL)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class FireElementAttack extends PassiveSkill
{
	public FireElementAttack()
	{
		this(0, false, false);
	}
	
	public FireElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = FIREELEMENTATTACK;
		this.name = "Fire Ele Attack";
		this.desc = "Weapon becomes Fire element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.MYCHAL)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class CatBurglar extends PassiveSkill
{
	public CatBurglar()
	{
		this(0, false, false);
	}
	
	public CatBurglar(int currentSP, boolean learned, boolean equipped)
	{
		this.id = CATBURGLAR;
		this.name = "Cat Burglar";
		this.desc = "Raises chance to Steal items.";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this when calculating Steal rate
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class Mug extends PassiveSkill
{
	public Mug()
	{
		this(0, false, false);
	}
	
	public Mug(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MUG;
		this.name = "Mug";
		this.desc = "Steal skill deals some damage.";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//look at this when using Steal
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class PayDay extends PassiveSkill
{
	public PayDay()
	{
		this(0, false, false);
	}
	
	public PayDay(int currentSP, boolean learned, boolean equipped)
	{
		this.id = PAYDAY;
		this.name = "Pay Day";
		this.desc = "Gain more money after battle.";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//look at this when calculating total money
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 35;
	}
}

class CatEyes extends PassiveSkill
{
	public CatEyes()
	{
		this(0, false, false);
	}
	
	public CatEyes(int currentSP, boolean learned, boolean equipped)
	{
		this.id = CATEYES;
		this.name = "Cat Eyes";
		this.desc = "Reveals hidden treasure chests.";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//look at this when drawing the map
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class PoisonElementResist extends PassiveSkill
{
	public PoisonElementResist()
	{
		this(0, false, false);
	}
	
	public PoisonElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = POISONELEMENTRESIST;
		this.name = "Poison Ele Resist";
		this.desc = "Raises Poison Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.POISON] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class PoisonElementAttack extends PassiveSkill
{
	public PoisonElementAttack()
	{
		this(0, false, false);
	}
	
	public PoisonElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = POISONELEMENTATTACK;
		this.name = "Poison Ele Attack";
		this.desc = "Weapon becomes Poison element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KITTEN)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class Scan extends PassiveSkill
{
	public Scan()
	{
		this(0, false, false);
	}
	
	public Scan(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SCAN;
		this.name = "Scan";
		this.desc = "Reveals enemies' HP in battle.";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class RNGDefenses extends PassiveSkill
{
	public RNGDefenses()
	{
		this(0, false, false);
	}
	
	public RNGDefenses(int currentSP, boolean learned, boolean equipped)
	{
		this.id = RNGDEFENSES;
		this.name = "RNG Defenses";
		this.desc = "Randomizes damage taken (0.5x - 1.5x).";
		this.cubeCost = 2;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 30;
	}
}

class DestroyHumans extends PassiveSkill
{
	public DestroyHumans()
	{
		this(0, false, false);
	}
	
	public DestroyHumans(int currentSP, boolean learned, boolean equipped)
	{
		this.id = DESTROYHUMANS;
		this.name = "Destroy Humans";
		this.desc = "Deal increased damage to Human enemies.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class LightningElementResist extends PassiveSkill
{
	public LightningElementResist()
	{
		this(0, false, false);
	}
	
	public LightningElementResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = LIGHTNINGELEMENTRESIST;
		this.name = "Lightning Ele Resist";
		this.desc = "Raises Lightning Element resist (% based on Dex).";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 20 + 3*character.dex; //TODO: work on this formula?
		
		character.elementResistance[Game.LIGHTNING] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class LightningElementAttack extends PassiveSkill
{
	public LightningElementAttack()
	{
		this(0, false, false);
	}
	
	public LightningElementAttack(int currentSP, boolean learned, boolean equipped)
	{
		this.id = LIGHTNINGELEMENTATTACK;
		this.name = "Lightning Ele Attack";
		this.desc = "Weapon becomes Lightning element (% based on Dex).";
		this.cubeCost = 6;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//looks at this in Game.calculateDamage() 
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.KEVBOT)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class PoisonStatusResist extends PassiveSkill
{
	public PoisonStatusResist()
	{
		this(0, false, false);
	}
	
	public PoisonStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = POISONSTATUSRESIST;
		this.name = "Poison Status Resist";
		this.desc = "Raises Poison status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.POISON] += resistAmt;
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class SilenceStatusResist extends PassiveSkill
{
	public SilenceStatusResist()
	{
		this(0, false, false);
	}
	
	public SilenceStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SILENCESTATUSRESIST;
		this.name = "Silence Status Resist";
		this.desc = "Raises Silence status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.SILENCE] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class BlindStatusResist extends PassiveSkill
{
	public BlindStatusResist()
	{
		this(0, false, false);
	}
	
	public BlindStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = BLINDSTATUSRESIST;
		this.name = "Blind Status Resist";
		this.desc = "Raises Blind status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.BLIND] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class SleepStatusResist extends PassiveSkill
{
	public SleepStatusResist()
	{
		this(0, false, false);
	}
	
	public SleepStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SLEEPSTATUSRESIST;
		this.name = "Sleep Status Resist";
		this.desc = "Raises Sleep status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.SLEEP] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class SlowStatusResist extends PassiveSkill
{
	public SlowStatusResist()
	{
		this(0, false, false);
	}
	
	public SlowStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SLOWSTATUSRESIST;
		this.name = "Slow Status Resist";
		this.desc = "Raises Slow status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.SLOW] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class BerserkStatusResist extends PassiveSkill
{
	public BerserkStatusResist()
	{
		this(0, false, false);
	}
	
	public BerserkStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = BERSERKSTATUSRESIST;
		this.name = "Berserk Status Resist";
		this.desc = "Raises Berserk status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.BERSERK] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ShameStatusResist extends PassiveSkill
{
	public ShameStatusResist()
	{
		this(0, false, false);
	}
	
	public ShameStatusResist(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SHAMESTATUSRESIST;
		this.name = "Shame Status Resist";
		this.desc = "Raises Shame status resistance (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		int resistAmt = 40 + 2*character.dex; //TODO: work on this formula?
		
		character.statusResistance[Unit.SHAME] += resistAmt; 
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class InflictStatusPoison extends PassiveSkill
{
	public InflictStatusPoison()
	{
		this(0, false, false);
	}
	
	public InflictStatusPoison(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSPOISON;
		this.name = "Inflict Poison Status";
		this.desc = "Attacks inflict Poison status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusSilence extends PassiveSkill
{
	public InflictStatusSilence()
	{
		this(0, false, false);
	}
	
	public InflictStatusSilence(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSSILENCE;
		this.name = "Inflict Silence Status";
		this.desc = "Attacks inflict Silence status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusBlind extends PassiveSkill
{
	public InflictStatusBlind()
	{
		this(0, false, false);
	}
	
	public InflictStatusBlind(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSBLIND;
		this.name = "Inflict Blind Status";
		this.desc = "Attacks inflict Blind status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusSleep extends PassiveSkill
{
	public InflictStatusSleep()
	{
		this(0, false, false);
	}
	
	public InflictStatusSleep(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSSLEEP;
		this.name = "Inflict Sleep Status";
		this.desc = "Attacks inflict Sleep status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusSlow extends PassiveSkill
{
	public InflictStatusSlow()
	{
		this(0, false, false);
	}
	
	public InflictStatusSlow(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSSLOW;
		this.name = "Inflict Slow Status";
		this.desc = "Attacks inflict Slow status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusBerserk extends PassiveSkill
{
	public InflictStatusBerserk()
	{
		this(0, false, false);
	}
	
	public InflictStatusBerserk(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSBERSERK;
		this.name = "Inflict Berserk Status";
		this.desc = "Attacks inflict Berserk status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class InflictStatusShame extends PassiveSkill
{
	public InflictStatusShame()
	{
		this(0, false, false);
	}
	
	public InflictStatusShame(int currentSP, boolean learned, boolean equipped)
	{
		this.id = INFLICTSTATUSSHAME;
		this.name = "Inflict Shame Status";
		this.desc = "Attacks inflict Shame status (% based on Dex).";
		this.cubeCost = 3;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when applying battle effects - rate = 5 + 2*dex?
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 40;
	}
}

class ItemAddict extends PassiveSkill
{
	public ItemAddict()
	{
		this(0, false, false);
	}
	
	public ItemAddict(int currentSP, boolean learned, boolean equipped)
	{
		this.id = ITEMADDICT;
		this.name = "Item Addict";
		this.desc = "Boosts the effectiveness of items.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when using items
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class AutoRegen extends PassiveSkill
{
	public AutoRegen()
	{
		this(0, false, false);
	}
	
	public AutoRegen(int currentSP, boolean learned, boolean equipped)
	{
		this.id = AUTOREGEN;
		this.name = "Auto-Regent";
		this.desc = "Start the battle with Regen status.";
		this.cubeCost = 7;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when starting battle
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class AutoHaste extends PassiveSkill
{
	public AutoHaste()
	{
		this(0, false, false);
	}
	
	public AutoHaste(int currentSP, boolean learned, boolean equipped)
	{
		this.id = AUTOHASTE;
		this.name = "Auto-Haste";
		this.desc = "Start the battle with Haste status.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when starting battle
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 100;
	}
}

class AutoBerserk extends PassiveSkill
{
	public AutoBerserk()
	{
		this(0, false, false);
	}
	
	public AutoBerserk(int currentSP, boolean learned, boolean equipped)
	{
		this.id = AUTOBERSERK;
		this.name = "Auto-Berserk";
		this.desc = "Start the battle with Berserk status.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when starting battle
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 60;
	}
}

class AutoProtect extends PassiveSkill
{
	public AutoProtect()
	{
		this(0, false, false);
	}
	
	public AutoProtect(int currentSP, boolean learned, boolean equipped)
	{
		this.id = AUTOPROTECT;
		this.name = "Auto-Protect";
		this.desc = "Start the battle with Protect status.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when starting battle
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 80;
	}
}

class AutoShell extends PassiveSkill
{
	public AutoShell()
	{
		this(0, false, false);
	}
	
	public AutoShell(int currentSP, boolean learned, boolean equipped)
	{
		this.id = AUTOSHELL;
		this.name = "Auto-Shell";
		this.desc = "Start the battle with Shell status.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when starting battle
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 80;
	}
}

class EXPBoost extends PassiveSkill
{
	public EXPBoost()
	{
		this(0, false, false);
	}
	
	public EXPBoost(int currentSP, boolean learned, boolean equipped)
	{
		this.id = EXPBOOST;
		this.name = "EXP Boost";
		this.desc = "Gain 50% more EXP.";
		this.cubeCost = 7;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when calculating EXP gain
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 75;
	}
}

class SPBoost extends PassiveSkill
{
	public SPBoost()
	{
		this(0, false, false);
	}
	
	public SPBoost(int currentSP, boolean learned, boolean equipped)
	{
		this.id = SPBOOST;
		this.name = "SP Boost";
		this.desc = "Gain double SP.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when calculating SP gain
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 50;
	}
}

class EncounterHalf extends PassiveSkill
{
	public EncounterHalf()
	{
		this(0, false, false);
	}
	
	public EncounterHalf(int currentSP, boolean learned, boolean equipped)
	{
		this.id = ENCOUNTERHALF;
		this.name = "Encounter Half";
		this.desc = "Halves the rate of random encounters.";
		this.cubeCost = 4;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when calculating encounter rate
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 55;
	}
}

class EncounterNone extends PassiveSkill
{
	public EncounterNone()
	{
		this(0, false, false);
	}
	
	public EncounterNone(int currentSP, boolean learned, boolean equipped)
	{
		this.id = ENCOUNTERNONE;
		this.name = "Encounter None";
		this.desc = "No more random encounters.";
		this.cubeCost = 8;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when calculating encounter rate
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 100;
	}
}

class MPHalf extends PassiveSkill
{
	public MPHalf()
	{
		this(0, false, false);
	}
	
	public MPHalf(int currentSP, boolean learned, boolean equipped)
	{
		this.id = MPHALF;
		this.name = "MP Half";
		this.desc = "MP costs are halved.";
		this.cubeCost = 12;
		
		this.currentSP = currentSP;
		this.learned = learned;
		this.equipped = equipped;
	}
	
	public void process(Unit character)
	{
		//check when calculating/displaying MP costs
	}
	
	public boolean canLearn(Unit character)
	{
		return true;
	}
	
	public int getCost(Unit character)
	{
		return 250;
	}
}