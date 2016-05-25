package game;

public class ActiveSkill implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ID
	public int id;
	public int currentSP;
	public boolean learned;
	public Action action;
	
	public ActiveSkill()
	{
		
	}
	
	public static ActiveSkill activeSkillFromID(int id, int currentSP, boolean learned)
	{
		switch(id)
		{
			//Brian
			case Action.BRIANPUNCH: return new ActiveBrianPunch(currentSP, learned);
			case Action.BRIANSMASH: return new ActiveBrianSmash(currentSP, learned);
			case Action.CHEEZITBLAST: return new ActiveCheezItBlast(currentSP, learned);
			case Action.COOLRANCHLASER: return new ActiveCoolRanchLaser(currentSP, learned);
			case Action.FLAVOREXPLOSION: return new ActiveFlavorExplosion(currentSP, learned);
			case Action.MTNDEWWAVE: return new ActiveMtnDewWave(currentSP, learned);
			case Action.GOTTAGOFAST: return new ActiveGottaGoFast(currentSP, learned);
			case Action.GOTTAGOFASTER: return new ActiveGottaGoFaster(currentSP, learned);
			case Action.MURDER: return new ActiveMurder(currentSP, learned);
			case Action.MASSMURDER: return new ActiveMassMurder(currentSP, learned);
			//TODO: holy element skill
			
			//Alex
			case Action.BARF: return new ActiveBarf(currentSP, learned);
			case Action.FLAIL: return new ActiveFlail(currentSP, learned);
			case Action.ANNOY: return new ActiveAnnoy(currentSP, learned);
			case Action.COWER: return new ActiveCower(currentSP, learned);
			case Action.SHRIEK: return new ActiveShriek(currentSP, learned);
			case Action.EATPOPTART: return new ActiveEatPopTart(currentSP, learned);
			case Action.KAMIKAZE: return new ActiveKamikaze(currentSP, learned);
			case Action.VOMITERUPTION: return new ActiveVomitEruption(currentSP, learned);
			case Action.SUMMONTRAINS: return new ActiveSummonTrains(currentSP, learned);
			
			//Hank
			case Action.BLESSINGOFARINO: return new ActiveBlessingOfArino(currentSP, learned);
			case Action.BLESSINGOFMIKU: return new ActiveBlessingOfMiku(currentSP, learned);
			case Action.MYSTERIOUSMELODY: return new ActiveMysteriousMelody(currentSP, learned);
			case Action.BAJABLAST: return new ActiveBajaBlast(currentSP, learned);
			case Action.BLUESHIELD: return new ActiveBlueShield(currentSP, learned);
			case Action.BLUEBARRIER: return new ActiveBlueBarrier(currentSP, learned);
			case Action.SILLYDANCE: return new ActiveSillyDance(currentSP, learned);
			case Action.AMP: return new ActiveAmp(currentSP, learned);
			case Action.RADICALRIFF: return new ActiveRadicalRiff(currentSP, learned);
			case Action.CHIPTUNEOFLIFE: return new ActiveChiptuneOfLife(currentSP, learned);
			case Action.SOOTHINGSONG: return new ActiveSoothingSong(currentSP, learned); //Esuna
			//TODO: skill that lowers enemy's defenses
			
			//Michael
			case Action.SHURIKEN: return new ActiveShuriken(currentSP, learned);
			case Action.KAGESHADOWS: return new ActiveKageShadows(currentSP, learned);
			case Action.INFLICTSHAME: return new ActiveInflictShame(currentSP, learned);
			case Action.DEFENDHONOR: return new ActiveDefendHonor(currentSP, learned);
			case Action.HONORFORALL: return new ActiveHonorForAll(currentSP, learned);
			case Action.NINJUTSUSLICE: return new ActiveNinjutsuSlice(currentSP, learned);
			case Action.SAMURAISLASH: return new ActiveSamuraiSlash(currentSP, learned);
			case Action.BUSHIDOBLADE: return new ActiveBushidoBlade(currentSP, learned);
			case Action.MURAMASAMARA: return new ActiveMuramasamara(currentSP, learned);
			
			//Kitten
			case Action.FIRE: return new ActiveFire(currentSP, learned);
			case Action.BIGFIRE: return new ActiveBigFire(currentSP, learned);
			case Action.LIGHTNINGBOLT: return new ActiveLightningBolt(currentSP, learned);
			case Action.LIGHTNINGSTORM: return new ActiveLightningStorm(currentSP, learned); //TODO: remove from Kitten since KevBot has Discharge/Overload?
			case Action.EARTHSPIKE: return new ActiveEarthSpike(currentSP, learned);
			case Action.EARTHQUAKE: return new ActiveEarthquake(currentSP, learned);
			case Action.STEAL: return new ActiveSteal(currentSP, learned);
			case Action.PURR: return new ActivePurr(currentSP, learned);
			case Action.CATNAP: return new ActiveCatNap(currentSP, learned); //give to Hank instead as Lullaby?
			case Action.CATSCRATCH: return new ActiveCatScratch(currentSP, learned);
			case Action.DEVOUR: return new ActiveDevour(currentSP, learned);
			//TODO: dark "ultima"-like skill
			
			//Kev-Bot
			case Action.DISCHARGE: return new ActiveDischarge(currentSP, learned); //lightning to all (plus also slightly damages party)
			case Action.ROBOTTEARS: return new ActiveRobotTears(currentSP, learned); //atk debuff
			case Action.GOALKEEPER: return new ActiveGoalkeeper(currentSP, learned); //provokes single target attacks
			case Action.EJECTMONEY: return new ActiveEjectMoney(currentSP, learned); //strong attack, uses money
			case Action.SYSTEMREBOOT: return new ActiveSystemReboot(currentSP, learned); //fully heal self + remove statuses, may fail
			case Action.MALFUNCTION: return new ActiveMalfunction(currentSP, learned); //random damage to random targets
			case Action.OVERLOAD: return new ActiveOverload(currentSP, learned); //consume all MP to do high lightning damage to all
			case Action.SQUIRTOIL: return new ActiveSquirtOil(currentSP, learned); //gives Oil status (higher damage from fire)
			case Action.INSTALLVIRUS: return new ActiveInstallVirus(currentSP, learned); //def/mdef debuff
			case Action.ROBOTBEAM: return new ActiveRobotBeam(currentSP, learned); //neutral magic, one target
		}
		
		return null;
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

/**
 * Brian
 */
class ActiveBrianPunch extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBrianPunch()
	{
		this(0, false);
	}
	
	public ActiveBrianPunch(int currentSP, boolean learned)
	{
		this.id = Action.BRIANPUNCH;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BrianPunch();
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
		return 25; //TODO: work on the costs for all skills (they're all just 25)
	}
}

class ActiveBrianSmash extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBrianSmash()
	{
		this(0, false);
	}
	
	public ActiveBrianSmash(int currentSP, boolean learned)
	{
		this.id = Action.BRIANSMASH;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BrianSmash();
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
		return 25;
	}
}

class ActiveCheezItBlast extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveCheezItBlast()
	{
		this(0, false);
	}
	
	public ActiveCheezItBlast(int currentSP, boolean learned)
	{
		this.id = Action.CHEEZITBLAST;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new CheezItBlast();
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
		return 25;
	}
}

class ActiveCoolRanchLaser extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveCoolRanchLaser()
	{
		this(0, false);
	}
	
	public ActiveCoolRanchLaser(int currentSP, boolean learned)
	{
		this.id = Action.COOLRANCHLASER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new CoolRanchLaser();
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
		return 25;
	}
}

class ActiveFlavorExplosion extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveFlavorExplosion()
	{
		this(0, false);
	}
	
	public ActiveFlavorExplosion(int currentSP, boolean learned)
	{
		this.id = Action.FLAVOREXPLOSION;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new FlavorExplosion();
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
		return 25;
	}
}

class ActiveMtnDewWave extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMtnDewWave()
	{
		this(0, false);
	}
	
	public ActiveMtnDewWave(int currentSP, boolean learned)
	{
		this.id = Action.MTNDEWWAVE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new MtnDewWave();
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
		return 25;
	}
}

class ActiveGottaGoFast extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveGottaGoFast()
	{
		this(0, false);
	}
	
	public ActiveGottaGoFast(int currentSP, boolean learned)
	{
		this.id = Action.GOTTAGOFAST;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new GottaGoFast();
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
		return 25;
	}
}

class ActiveGottaGoFaster extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveGottaGoFaster()
	{
		this(0, false);
	}
	
	public ActiveGottaGoFaster(int currentSP, boolean learned)
	{
		this.id = Action.GOTTAGOFASTER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new GottaGoFaster();
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
		return 25;
	}
}

class ActiveMurder extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMurder()
	{
		this(0, false);
	}
	
	public ActiveMurder(int currentSP, boolean learned)
	{
		this.id = Action.MURDER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Murder();
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
		return 25;
	}
}

class ActiveMassMurder extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMassMurder()
	{
		this(0, false);
	}
	
	public ActiveMassMurder(int currentSP, boolean learned)
	{
		this.id = Action.MASSMURDER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new MassMurder();
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
		return 25;
	}
}

/**
 * Alex
 */
class ActiveBarf extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBarf()
	{
		this(0, false);
	}
	
	public ActiveBarf(int currentSP, boolean learned)
	{
		this.id = Action.BARF;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Barf();
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
		return 25;
	}
}

class ActiveFlail extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveFlail()
	{
		this(0, false);
	}
	
	public ActiveFlail(int currentSP, boolean learned)
	{
		this.id = Action.FLAIL;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Flail();
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
		return 25;
	}
}

class ActiveAnnoy extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveAnnoy()
	{
		this(0, false);
	}
	
	public ActiveAnnoy(int currentSP, boolean learned)
	{
		this.id = Action.ANNOY;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Annoy();
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
		return 25;
	}
}

class ActiveCower extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveCower()
	{
		this(0, false);
	}
	
	public ActiveCower(int currentSP, boolean learned)
	{
		this.id = Action.COWER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Cower();
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
		return 25;
	}
}

class ActiveShriek extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveShriek()
	{
		this(0, false);
	}
	
	public ActiveShriek(int currentSP, boolean learned)
	{
		this.id = Action.SHRIEK;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Shriek();
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
		return 25;
	}
}

class ActiveEatPopTart extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveEatPopTart()
	{
		this(0, false);
	}
	
	public ActiveEatPopTart(int currentSP, boolean learned)
	{
		this.id = Action.EATPOPTART;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new EatPopTart();
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
		return 25;
	}
}

class ActiveKamikaze extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveKamikaze()
	{
		this(0, false);
	}
	
	public ActiveKamikaze(int currentSP, boolean learned)
	{
		this.id = Action.KAMIKAZE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Kamikaze();
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
		return 25;
	}
}

class ActiveVomitEruption extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveVomitEruption()
	{
		this(0, false);
	}
	
	public ActiveVomitEruption(int currentSP, boolean learned)
	{
		this.id = Action.VOMITERUPTION;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new VomitEruption();
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
		return 25;
	}
}

class ActiveSummonTrains extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSummonTrains()
	{
		this(0, false);
	}
	
	public ActiveSummonTrains(int currentSP, boolean learned)
	{
		this.id = Action.SUMMONTRAINS;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SummonTrains();
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
		return 25;
	}
}

/**
 * Hank
 */
class ActiveBlessingOfArino extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBlessingOfArino()
	{
		this(0, false);
	}
	
	public ActiveBlessingOfArino(int currentSP, boolean learned)
	{
		this.id = Action.BLESSINGOFARINO;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BlessingOfArino();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveBlessingOfMiku extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBlessingOfMiku()
	{
		this(0, false);
	}
	
	public ActiveBlessingOfMiku(int currentSP, boolean learned)
	{
		this.id = Action.BLESSINGOFMIKU;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BlessingOfMiku();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveMysteriousMelody extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMysteriousMelody()
	{
		this(0, false);
	}
	
	public ActiveMysteriousMelody(int currentSP, boolean learned)
	{
		this.id = Action.MYSTERIOUSMELODY;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new MysteriousMelody();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveBajaBlast extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBajaBlast()
	{
		this(0, false);
	}
	
	public ActiveBajaBlast(int currentSP, boolean learned)
	{
		this.id = Action.BAJABLAST;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BajaBlast();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveBlueShield extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBlueShield()
	{
		this(0, false);
	}
	
	public ActiveBlueShield(int currentSP, boolean learned)
	{
		this.id = Action.BLUESHIELD;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BlueShield();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveBlueBarrier extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBlueBarrier()
	{
		this(0, false);
	}
	
	public ActiveBlueBarrier(int currentSP, boolean learned)
	{
		this.id = Action.BLUEBARRIER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BlueBarrier();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveSillyDance extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSillyDance()
	{
		this(0, false);
	}
	
	public ActiveSillyDance(int currentSP, boolean learned)
	{
		this.id = Action.SILLYDANCE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SillyDance();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveAmp extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveAmp()
	{
		this(0, false);
	}
	
	public ActiveAmp(int currentSP, boolean learned)
	{
		this.id = Action.AMP;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Amp();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveRadicalRiff extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveRadicalRiff()
	{
		this(0, false);
	}
	
	public ActiveRadicalRiff(int currentSP, boolean learned)
	{
		this.id = Action.RADICALRIFF;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new RadicalRiff();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveChiptuneOfLife extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveChiptuneOfLife()
	{
		this(0, false);
	}
	
	public ActiveChiptuneOfLife(int currentSP, boolean learned)
	{
		this.id = Action.CHIPTUNEOFLIFE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new ChiptuneOfLife();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

class ActiveSoothingSong extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSoothingSong()
	{
		this(0, false);
	}
	
	public ActiveSoothingSong(int currentSP, boolean learned)
	{
		this.id = Action.SOOTHINGSONG;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SoothingSong();
	}
	
	public boolean canLearn(Unit character)
	{
		if(character.id == Character.HANK)
		{
			return true;
		}
		
		return false;
	}
	
	public int getCost(Unit character)
	{
		return 25;
	}
}

/**
 * Michael
 */
class ActiveShuriken extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveShuriken()
	{
		this(0, false);
	}
	
	public ActiveShuriken(int currentSP, boolean learned)
	{
		this.id = Action.SHURIKEN;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Shuriken();
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
		return 25;
	}
}

class ActiveKageShadows extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveKageShadows()
	{
		this(0, false);
	}
	
	public ActiveKageShadows(int currentSP, boolean learned)
	{
		this.id = Action.KAGESHADOWS;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new KageShadows();
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
		return 25;
	}
}

class ActiveInflictShame extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveInflictShame()
	{
		this(0, false);
	}
	
	public ActiveInflictShame(int currentSP, boolean learned)
	{
		this.id = Action.INFLICTSHAME;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new InflictShame();
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
		return 25;
	}
}

class ActiveDefendHonor extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveDefendHonor()
	{
		this(0, false);
	}
	
	public ActiveDefendHonor(int currentSP, boolean learned)
	{
		this.id = Action.DEFENDHONOR;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new DefendHonor();
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
		return 25;
	}
}

class ActiveHonorForAll extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveHonorForAll()
	{
		this(0, false);
	}
	
	public ActiveHonorForAll(int currentSP, boolean learned)
	{
		this.id = Action.HONORFORALL;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new HonorForAll();
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
		return 25;
	}
}

class ActiveNinjutsuSlice extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveNinjutsuSlice()
	{
		this(0, false);
	}
	
	public ActiveNinjutsuSlice(int currentSP, boolean learned)
	{
		this.id = Action.NINJUTSUSLICE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new NinjutsuSlice();
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
		return 25;
	}
}

class ActiveSamuraiSlash extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSamuraiSlash()
	{
		this(0, false);
	}
	
	public ActiveSamuraiSlash(int currentSP, boolean learned)
	{
		this.id = Action.SAMURAISLASH;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SamuraiSlash();
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
		return 25;
	}
}

class ActiveBushidoBlade extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBushidoBlade()
	{
		this(0, false);
	}
	
	public ActiveBushidoBlade(int currentSP, boolean learned)
	{
		this.id = Action.BUSHIDOBLADE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BushidoBlade();
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
		return 25;
	}
}

class ActiveMuramasamara extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMuramasamara()
	{
		this(0, false);
	}
	
	public ActiveMuramasamara(int currentSP, boolean learned)
	{
		this.id = Action.MURAMASAMARA;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Muramasamara();
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
		return 25;
	}
}

/**
 * Kitten
 */
class ActiveFire extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveFire()
	{
		this(0, false);
	}
	
	public ActiveFire(int currentSP, boolean learned)
	{
		this.id = Action.FIRE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Fire();
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
		return 25;
	}
}

class ActiveBigFire extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveBigFire()
	{
		this(0, false);
	}
	
	public ActiveBigFire(int currentSP, boolean learned)
	{
		this.id = Action.BIGFIRE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new BigFire();
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
		return 25;
	}
}

class ActiveLightningBolt extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveLightningBolt()
	{
		this(0, false);
	}
	
	public ActiveLightningBolt(int currentSP, boolean learned)
	{
		this.id = Action.LIGHTNINGBOLT;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new LightningBolt();
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
		return 25;
	}
}

class ActiveLightningStorm extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveLightningStorm()
	{
		this(0, false);
	}
	
	public ActiveLightningStorm(int currentSP, boolean learned)
	{
		this.id = Action.LIGHTNINGSTORM;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new LightningStorm();
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
		return 25;
	}
}

class ActiveEarthSpike extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveEarthSpike()
	{
		this(0, false);
	}
	
	public ActiveEarthSpike(int currentSP, boolean learned)
	{
		this.id = Action.EARTHSPIKE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new EarthSpike();
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
		return 25;
	}
}

class ActiveEarthquake extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveEarthquake()
	{
		this(0, false);
	}
	
	public ActiveEarthquake(int currentSP, boolean learned)
	{
		this.id = Action.EARTHQUAKE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Earthquake();
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
		return 25;
	}
}

class ActiveSteal extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSteal()
	{
		this(0, false);
	}
	
	public ActiveSteal(int currentSP, boolean learned)
	{
		this.id = Action.STEAL;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Steal();
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
		return 25;
	}
}

class ActivePurr extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivePurr()
	{
		this(0, false);
	}
	
	public ActivePurr(int currentSP, boolean learned)
	{
		this.id = Action.PURR;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Purr();
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
		return 25;
	}
}

class ActiveCatNap extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveCatNap()
	{
		this(0, false);
	}
	
	public ActiveCatNap(int currentSP, boolean learned)
	{
		this.id = Action.CATNAP;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new CatNap();
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
		return 25;
	}
}

class ActiveCatScratch extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveCatScratch()
	{
		this(0, false);
	}
	
	public ActiveCatScratch(int currentSP, boolean learned)
	{
		this.id = Action.CATSCRATCH;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new CatScratch();
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
		return 25;
	}
}

class ActiveDevour extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveDevour()
	{
		this(0, false);
	}
	
	public ActiveDevour(int currentSP, boolean learned)
	{
		this.id = Action.DEVOUR;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Devour();
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
		return 25;
	}
}

/**
 * Kev-Bot
 */

class ActiveDischarge extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveDischarge()
	{
		this(0, false);
	}
	
	public ActiveDischarge(int currentSP, boolean learned)
	{
		this.id = Action.DISCHARGE;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Discharge();
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
		return 25;
	}
}

class ActiveRobotTears extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveRobotTears()
	{
		this(0, false);
	}
	
	public ActiveRobotTears(int currentSP, boolean learned)
	{
		this.id = Action.ROBOTTEARS;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new RobotTears();
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
		return 25;
	}
}

class ActiveGoalkeeper extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveGoalkeeper()
	{
		this(0, false);
	}
	
	public ActiveGoalkeeper(int currentSP, boolean learned)
	{
		this.id = Action.GOALKEEPER;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Goalkeeper();
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
		return 25;
	}
}

class ActiveEjectMoney extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveEjectMoney()
	{
		this(0, false);
	}
	
	public ActiveEjectMoney(int currentSP, boolean learned)
	{
		this.id = Action.EJECTMONEY;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new EjectMoney();
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
		return 25;
	}
}

class ActiveSystemReboot extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSystemReboot()
	{
		this(0, false);
	}
	
	public ActiveSystemReboot(int currentSP, boolean learned)
	{
		this.id = Action.SYSTEMREBOOT;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SystemReboot();
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
		return 25;
	}
}

class ActiveMalfunction extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMalfunction()
	{
		this(0, false);
	}
	
	public ActiveMalfunction(int currentSP, boolean learned)
	{
		this.id = Action.MALFUNCTION;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Malfunction();
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
		return 25;
	}
}

class ActiveOverload extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveOverload()
	{
		this(0, false);
	}
	
	public ActiveOverload(int currentSP, boolean learned)
	{
		this.id = Action.OVERLOAD;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new Overload();
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
		return 25;
	}
}

class ActiveSquirtOil extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveSquirtOil()
	{
		this(0, false);
	}
	
	public ActiveSquirtOil(int currentSP, boolean learned)
	{
		this.id = Action.SQUIRTOIL;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new SquirtOil();
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
		return 25;
	}
}

class ActiveInstallVirus extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveInstallVirus()
	{
		this(0, false);
	}
	
	public ActiveInstallVirus(int currentSP, boolean learned)
	{
		this.id = Action.INSTALLVIRUS;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new InstallVirus();
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
		return 25;
	}
}

class ActiveRobotBeam extends ActiveSkill
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveRobotBeam()
	{
		this(0, false);
	}
	
	public ActiveRobotBeam(int currentSP, boolean learned)
	{
		this.id = Action.ROBOTBEAM;
		this.currentSP = currentSP;
		this.learned = learned;
		this.action = new RobotBeam();
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
		return 25;
	}
}