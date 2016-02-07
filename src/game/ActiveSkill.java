package game;

public class ActiveSkill
{
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
			
			//Ryan
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
			case Action.LIGHTNINGSTORM: return new ActiveLightningStorm(currentSP, learned);
			case Action.EARTHSPIKE: return new ActiveEarthSpike(currentSP, learned);
			case Action.EARTHQUAKE: return new ActiveEarthquake(currentSP, learned);
			case Action.STEAL: return new ActiveSteal(currentSP, learned);
			case Action.PURR: return new ActivePurr(currentSP, learned);
			case Action.CATNAP: return new ActiveCatNap(currentSP, learned);
			case Action.CATSCRATCH: return new ActiveCatScratch(currentSP, learned);
			case Action.DEVOUR: return new ActiveDevour(currentSP, learned);
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
		return 25;
	}
}

class ActiveBrianSmash extends ActiveSkill
{
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
 * Ryan
 */
class ActiveBlessingOfArino extends ActiveSkill
{
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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
		if(character.id == Character.RYAN)
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