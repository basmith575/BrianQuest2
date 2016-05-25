package game;

import java.util.Random;

public abstract class Action implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	
	public static final int NOACTION = 0;
	public static final int ATTACK = 1;
	public static final int POISON = 2;
	public static final int REGEN = 3;
	public static final int FLEE = 4;
	
	//TODO: should rethink the way some of these skills are sorted (esp. Kitten and Hank)
	
	/**
	 * Brian
	 */
	public static final int BRIANPUNCH = 100;
	public static final int BRIANSMASH = 101;
	public static final int CHEEZITBLAST = 102;
	public static final int COOLRANCHLASER = 103;
	public static final int FLAVOREXPLOSION = 104;
	public static final int MTNDEWWAVE = 105;
	public static final int GOTTAGOFAST = 106;
	public static final int GOTTAGOFASTER = 107;
	public static final int MURDER = 108;
	public static final int MASSMURDER = 109;
	
	/**
	 * Alex
	 */
	public static final int BARF = 200;
	public static final int FLAIL = 201;
	public static final int ANNOY = 202;
	public static final int COWER = 203;
	public static final int SHRIEK = 204;
	public static final int EATPOPTART = 205;
	public static final int KAMIKAZE = 206;
	public static final int VOMITERUPTION = 207;
	public static final int SUMMONTRAINS = 208;
	
	/**
	 * Hank
	 */
	public static final int BLESSINGOFARINO = 300;
	public static final int BLESSINGOFMIKU = 301;
	public static final int MYSTERIOUSMELODY = 302;
	public static final int SOOTHINGSONG = 303;
	public static final int BAJABLAST = 304;
	public static final int BLUESHIELD = 305;
	public static final int BLUEBARRIER = 306;
	public static final int SILLYDANCE = 307;
	public static final int AMP = 308;
	public static final int RADICALRIFF = 309;
	public static final int CHIPTUNEOFLIFE = 310;
	
	/**
	 * Michael
	 */
	public static final int SHURIKEN = 400;
	public static final int KAGESHADOWS = 401;
	public static final int INFLICTSHAME = 402;
	public static final int DEFENDHONOR = 403;
	public static final int HONORFORALL = 404;
	public static final int NINJUTSUSLICE = 405;
	public static final int SAMURAISLASH = 406;
	public static final int BUSHIDOBLADE = 407;
	public static final int MURAMASAMARA = 408;
	
	/**
	 * Kitten
	 */
	public static final int FIRE = 500;
	public static final int BIGFIRE = 501;
	public static final int LIGHTNINGBOLT = 502;
	public static final int LIGHTNINGSTORM = 503;
	public static final int EARTHSPIKE = 504;
	public static final int EARTHQUAKE = 505;
	public static final int STEAL = 506;
	public static final int PURR = 507;
	public static final int CATNAP = 508;
	public static final int CATSCRATCH = 509;
	public static final int DEVOUR = 510;
	
	/**
	 * Kev-Bot
	 */
	public static final int DISCHARGE = 600;
	public static final int ROBOTTEARS = 601;
	public static final int GOALKEEPER = 602;
	public static final int EJECTMONEY = 603;
	public static final int SYSTEMREBOOT = 604;
	public static final int MALFUNCTION = 605;
	public static final int OVERLOAD = 606;
	public static final int SQUIRTOIL = 607;
	public static final int INSTALLVIRUS = 608;
	public static final int ROBOTBEAM = 609;
	
	/**
	 * Miscellaneous (other, monsters, etc.)
	 */
	
	/**
	 * Item
	 */
	public static final int HPITEM = 1000;
	public static final int MPITEM = 1001;
	public static final int REVIVEITEM = 1002;
	
	/**
	 * Physical or Magical
	 */
	public static final int PHYSICAL = 0;
	public static final int MAGICAL = 1;
	public static final int OTHERDAMAGETYPE = 2;
	
	public int damageType;
	public int hitRateMod;
	public boolean attacksWithWeapon; //does the skill use the equipped weapon? used for weapon element attacks
	
	public int mp; //MP cost
	
	public int targetType; //what it targets
	public static final int ONEENEMY = 0;
	public static final int ALLENEMIES = 1;
	public static final int SELF = 2;
	public static final int ONEALLY = 3;
	public static final int ALLALLIES = 4;
	public static final int ONEUNIT = 5;
	public static final int ALLUNITS = 6;
	public static final int OTHERTARGETTYPE = 7; //special cases to handle manually
	
	public String name;
	public String desc; //skill description

	public int element;
	
	public int numFrames, damageFrame;
	
	public int imageHeight, imageWidth;
	
	public String animationPrefix;
	
	public Random rand = new Random();
	
	public Action()
	{
		
	}
	
	public static Action actionFromID(int id)
	{
		switch(id)
		{
			case NOACTION: return new NoAction();
			
			case ATTACK: return new Attack();
			case POISON: return new Poison();
			case REGEN: return new Regen();
			
			//Brian
			case BRIANPUNCH: return new BrianPunch();
			case BRIANSMASH: return new BrianSmash();
			case CHEEZITBLAST: return new CheezItBlast();
			case COOLRANCHLASER: return new CoolRanchLaser();
			case FLAVOREXPLOSION: return new FlavorExplosion();
			case MTNDEWWAVE: return new MtnDewWave();
			case GOTTAGOFAST: return new GottaGoFast();
			case GOTTAGOFASTER: return new GottaGoFaster();
			case MURDER: return new Murder();
			case MASSMURDER: return new MassMurder();
			
			//Alex
			case BARF: return new Barf();
			case FLAIL: return new Flail();
			case ANNOY: return new Annoy();
			case COWER: return new Cower();
			case SHRIEK: return new Shriek();
			case EATPOPTART: return new EatPopTart();
			case KAMIKAZE: return new Kamikaze();
			case VOMITERUPTION: return new VomitEruption();
			case SUMMONTRAINS: return new SummonTrains();
			
			//Hank
			case BLESSINGOFARINO: return new BlessingOfArino();
			case BLESSINGOFMIKU: return new BlessingOfMiku();
			case MYSTERIOUSMELODY: return new MysteriousMelody();
			case SOOTHINGSONG: return new SoothingSong();
			case BAJABLAST: return new BajaBlast();
			case BLUESHIELD: return new BlueShield();
			case BLUEBARRIER: return new BlueBarrier();
			case SILLYDANCE: return new SillyDance();
			case AMP: return new Amp();
			case RADICALRIFF: return new RadicalRiff();
			case CHIPTUNEOFLIFE: return new ChiptuneOfLife();
			
			//Michael
			case SHURIKEN: return new Shuriken();
			case KAGESHADOWS: return new KageShadows();
			case INFLICTSHAME: return new InflictShame();
			case DEFENDHONOR: return new DefendHonor();
			case HONORFORALL: return new HonorForAll();
			case NINJUTSUSLICE: return new NinjutsuSlice();
			case SAMURAISLASH: return new SamuraiSlash();
			case BUSHIDOBLADE: return new BushidoBlade();
			case MURAMASAMARA: return new Muramasamara();
			
			//Kitten
			case FIRE: return new Fire();
			case BIGFIRE: return new BigFire();
			case LIGHTNINGBOLT: return new LightningBolt();
			case LIGHTNINGSTORM: return new LightningStorm();
			case EARTHSPIKE: return new EarthSpike();
			case EARTHQUAKE: return new Earthquake();
			case STEAL: return new Steal();
			case PURR: return new Purr();
			case CATNAP: return new CatNap();
			case CATSCRATCH: return new CatScratch();
			case DEVOUR: return new Devour();
			
			//Kev-Bot
			case DISCHARGE: return new Discharge();
			case ROBOTTEARS: return new RobotTears();
			case GOALKEEPER: return new Goalkeeper();
			case EJECTMONEY: return new EjectMoney();
			case SYSTEMREBOOT: return new SystemReboot();
			case MALFUNCTION: return new Malfunction();
			case OVERLOAD: return new Overload();
			case SQUIRTOIL: return new SquirtOil();
			case INSTALLVIRUS: return new InstallVirus();
			case ROBOTBEAM: return new RobotBeam();
			
			//Miscellaneous
			
			//Item
			case HPITEM: return new HPItem();
			case MPITEM: return new MPItem();
			case REVIVEITEM: return new ReviveItem();
		}
		
		return new NoAction();
	}
	
	public int getMPCost(Unit unit)
	{
		if(this.mp == -1)
		{
			return unit.mp;
		}
		
		if(unit.hasEquippedPassiveSkill(PassiveSkill.MPHALF))
		{
			return this.mp / 2;
		}
		
		return this.mp;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 0; //override for each action
	}
	
	public static String targetTypeString(int targetType)
	{
		switch(targetType)
		{
			case ONEENEMY: return "One enemy";
			case ALLENEMIES: return "All enemies";
			case SELF: return "Self";
			case ONEALLY: return "One ally";
			case ALLALLIES: return "All allies";
			case ONEUNIT: return "One unit";
		}
		
		return "";
	}
	
	public boolean wakesUp()
	{
		if(this.damageType == PHYSICAL)
		{
			return true;
		}
		
		return false;
	}
	
	public static int animationState(int actionID, int frame)
	{
		/**
		 * Brian
		 */
		if(actionID == CHEEZITBLAST)
		{
			int[] animationMap = {0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		
		/**
		 * Alex
		 */
		else if(actionID == SHRIEK)
		{
			int[] animationMap = {0,1,2,0,1,2,0,1,2,0,1,2,0,1,2,0,1,2,0,1};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		
		/**
		 * Hank
		 */
		else if(actionID == BAJABLAST)
		{
			int[] animationMap = {0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		else if(actionID == BLESSINGOFMIKU)
		{
			int[] animationMap = {0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		else if(actionID == AMP)
		{
			int[] animationMap = {0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		
		/**
		 * Michael
		 */
		else if(actionID == KAGESHADOWS)
		{
			int[] animationMap = {0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		
		/**
		 * Kitten
		 */
		else if(actionID == LIGHTNINGBOLT || actionID == LIGHTNINGSTORM)
		{
			int[] animationMap = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4};
			if(frame > animationMap.length - 1) return animationMap[animationMap.length - 1];
			return animationMap[frame];
		}
		
		return 0;
	}
	
	public static int xOffsetState(int actionID, int frame)
	{
		int numFrames = Action.actionFromID(actionID).numFrames;
		
		int[] offsets = new int[numFrames-1];
		for(int i=0; i<offsets.length; i++) offsets[i] = 0;
		
		if(actionID == SHURIKEN)
		{
			for(int i=0; i<5; i++)
			{
				offsets[i] = -160 + 40*i;
			}
			
			return offsets[frame];
		}
		
		return 0;
	}
	
	public static int yOffsetState(int id, int frame)
	{
		int numFrames = Action.actionFromID(id).numFrames;
		
		int[] offsets = new int[numFrames-1];
		for(int i=0; i<offsets.length; i++) offsets[i] = 0;
		
		//TODO: actions that have Y offsets
		
		return 0;
	}
}

class NoAction extends Action
{
	private static final long serialVersionUID = 1L;

	public NoAction()
	{
		this.id = NOACTION;
		this.name = "None";
		this.desc = "No action";
		this.element = Game.ELEMENT_NONE;
	}
}

class Attack extends Action
{
	private static final long serialVersionUID = 1L;

	public Attack()
	{
		this.id = ATTACK;
		this.name = "Attack";
		this.desc = "Attack";
		//no animation prefix, based on equipped weapon
		
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 0;
		this.attacksWithWeapon = true; //obviously
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		double ddmg = 0;
		
		if(attack.unitType == Unit.CHARACTER)
		{
			ddmg = (5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
		}
		else
		{
			ddmg = (5.0 + ((2 * Math.pow(attack.str,3.0))/32.0))*(20.0/(20.0+target.getDef())) - target.getDef();
		}
		
		return ddmg;
	}
}

class Poison extends Action
{
	private static final long serialVersionUID = 1L;

	public Poison()
	{
		this.id = POISON;
		this.name = "Poison";
		this.desc = "Poison";
		this.animationPrefix = "poison";
		
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE; //no point setting this
		this.damageType = OTHERDAMAGETYPE; //no point setting this
		this.hitRateMod = 0; //no point setting this
	}
}

class Regen extends Action
{
	private static final long serialVersionUID = 1L;

	public Regen()
	{
		this.id = REGEN;
		this.name = "Regen";
		this.desc = "Regen";
		this.animationPrefix = "regen";
		
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = OTHERDAMAGETYPE;
	}
}

/**
 * Brian
 */
class BrianPunch extends Action
{
	private static final long serialVersionUID = 1L;

	public BrianPunch()
	{
		this.id = BRIANPUNCH;
		this.name = "Brian Punch";
		this.desc = "A punch with the strength of Brian.";
		this.animationPrefix = "brianPunch";
		
		this.mp = 6;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class BrianSmash extends Action
{
	private static final long serialVersionUID = 1L;

	public BrianSmash()
	{
		this.id = BRIANSMASH;
		this.name = "Brian Smash";
		this.desc = "Smash a foe with Brian-like force. Ignores Def.";
		this.animationPrefix = "brianPunch"; //TODO: have own animation
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (40.0 + 10.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0);
	}
}

class CheezItBlast extends Action
{
	private static final long serialVersionUID = 1L;

	public CheezItBlast()
	{
		this.id = CHEEZITBLAST;
		this.name = "Cheez-It Blast";
		this.desc = "An eruption of delicious Cheez-its.";
		this.animationPrefix = "cheezItBlast";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_SNACK;
		this.damageType = MAGICAL;
		this.hitRateMod = 30;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (10.0 + 0.8*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.8))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class CoolRanchLaser extends Action
{
	private static final long serialVersionUID = 1L;

	public CoolRanchLaser()
	{
		this.id = COOLRANCHLASER;
		this.name = "Cool Ranch Laser";
		this.desc = "A powerful laser made of Cool Ranch Doritos.";
		this.animationPrefix = "coolRanchLaser";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_SNACK;
		this.damageType = MAGICAL;
		this.hitRateMod = 30;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (40.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class FlavorExplosion extends Action
{
	private static final long serialVersionUID = 1L;

	public FlavorExplosion()
	{
		this.id = FLAVOREXPLOSION;
		this.name = "Flavor Explosion";
		this.desc = "A massive explosion of the most appetizing flavors.";
		this.animationPrefix = "flavorExplosion";
		
		this.mp = 44;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_SNACK;
		this.damageType = MAGICAL;
		this.hitRateMod = 30;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (20.0 + 1.7*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.8))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class MtnDewWave extends Action
{
	private static final long serialVersionUID = 1L;

	public MtnDewWave()
	{
		this.id = MTNDEWWAVE;
		this.name = "Mtn Dew Wave";
		this.desc = "Create a soothing wave of succulent Mtn Dew.";
		this.animationPrefix = "mtnDewWave";
		
		this.mp = 12;
		this.targetType = ALLALLIES;
		
		this.numFrames = 30;
		this.damageFrame = 10;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class GottaGoFast extends Action
{
	private static final long serialVersionUID = 1L;

	public GottaGoFast()
	{
		this.id = GOTTAGOFAST;
		this.name = "Gotta Go Fast";
		this.desc = "Summon Sonic to boost an ally's speed.";
		this.animationPrefix = "gottaGoFast";
		
		this.mp = 10;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class GottaGoFaster extends Action
{
	private static final long serialVersionUID = 1L;

	public GottaGoFaster()
	{
		this.id = GOTTAGOFASTER;
		this.name = "Gotta Go Faster";
		this.desc = "Unleash an army of Sonics for optimal speed.";
		this.animationPrefix = "gottaGoFast";
		
		this.mp = 38;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Murder extends Action
{
	private static final long serialVersionUID = 1L;

	public Murder()
	{
		this.id = MURDER;
		this.name = "Murder";
		this.desc = "Stone cold murder.";
		this.animationPrefix = "murder";
		
		this.mp = 24;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class MassMurder extends Action
{
	private static final long serialVersionUID = 1L;

	public MassMurder()
	{
		this.id = MASSMURDER;
		this.name = "Mass Murder";
		this.desc = "Stone cold mass murder.";
		this.animationPrefix = "murder";
		
		this.mp = 54;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

/**
 * Alex
 */
class Barf extends Action
{
	private static final long serialVersionUID = 1L;

	public Barf()
	{
		this.id = BARF;
		this.name = "Barf";
		this.desc = "Barf on a foe. May inflict Poison.";
		this.animationPrefix = "barf";
		
		this.mp = 4;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_POISON;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (20.0 + 1.5*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class Flail extends Action
{
	private static final long serialVersionUID = 1L;

	public Flail()
	{
		this.id = FLAIL;
		this.name = "Flail";
		this.desc = "A wild and crazy flail. Also hurts the user.";
		this.animationPrefix = "hit";
		
		this.mp = 6;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = true;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 2.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class Annoy extends Action
{
	private static final long serialVersionUID = 1L;

	public Annoy()
	{
		this.id = ANNOY;
		this.name = "Annoy";
		this.desc = "Annoy a target and make it go Berserk.";
		this.animationPrefix = "annoy";
		
		this.mp = 8;
		this.targetType = ONEUNIT;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Cower extends Action
{
	private static final long serialVersionUID = 1L;

	public Cower()
	{
		this.id = COWER;
		this.name = "Cower";
		this.desc = "Defend like a coward until your next turn.";
		this.animationPrefix = "cower";
		
		this.mp = 4;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Shriek extends Action
{
	private static final long serialVersionUID = 1L;

	public Shriek()
	{
		this.id = SHRIEK;
		this.name = "Shriek";
		this.desc = "A horrible shriek. May inflict Silence.";
		this.animationPrefix = "shriek";
		
		this.mp = 12;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (25.0 + 2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class EatPopTart extends Action
{
	private static final long serialVersionUID = 1L;

	public EatPopTart()
	{
		this.id = EATPOPTART;
		this.name = "Eat Pop-Tart";
		this.desc = "Eat a tasty, healing Pop-Tart. May poison consumer.";
		this.animationPrefix = "item0";
		
		this.mp = 6;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Kamikaze extends Action
{
	private static final long serialVersionUID = 1L;

	public Kamikaze()
	{
		this.id = KAMIKAZE;
		this.name = "Kamikaze";
		this.desc = "The ultimate sacrifice to deal massive damage.";
		this.animationPrefix = "kamikaze";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false; //TODO: should this be true? weapon atk is used in the damage formula, but seems like a non-weapon attack
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 7.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class VomitEruption extends Action
{
	private static final long serialVersionUID = 1L;

	public VomitEruption()
	{
		this.id = VOMITERUPTION;
		this.name = "Vomit Eruption";
		this.desc = "An eruption of toxic vomit on all foes.";
		this.animationPrefix = "barf";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_POISON;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (20.0 + 1.5*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class SummonTrains extends Action
{
	private static final long serialVersionUID = 1L;

	public SummonTrains()
	{
		this.id = SUMMONTRAINS;
		this.name = "Summon Trains";
		this.desc = "Unleash a torrent of trains from the sky.";
		this.animationPrefix = "summonTrains";
		
		this.mp = 32;
		this.targetType = ALLENEMIES;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 20;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 4.0*(30.0 + ((attack.str + (attack.level/7.0) + attack.dex)*(attack.str * attack.mag))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

/**
 * Hank
 */
class BlessingOfArino extends Action
{
	private static final long serialVersionUID = 1L;

	public BlessingOfArino()
	{
		this.id = BLESSINGOFARINO;
		this.name = "Blessing Of Arino";
		this.desc = "A song with the power of the Kacho.";
		this.animationPrefix = "blessingOfArino";
		
		this.mp = 20;
		this.targetType = ONEALLY;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class BlessingOfMiku extends Action
{
	private static final long serialVersionUID = 1L;

	public BlessingOfMiku()
	{
		this.id = BLESSINGOFMIKU;
		this.name = "Blessing Of Miku";
		this.desc = "A song with the healing power of Miku-chan.";
		this.animationPrefix = "blessingOfMiku";
		
		this.mp = 26;
		this.targetType = ALLALLIES;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class MysteriousMelody extends Action
{
	private static final long serialVersionUID = 1L;

	public MysteriousMelody()
	{
		this.id = MYSTERIOUSMELODY;
		this.name = "Mysterious Melody";
		this.desc = "A strange melody of a random element.";
		this.animationPrefix = "mysteriousMelody";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = MAGICAL;
		this.hitRateMod = 20;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		double mod = 1.0;
		if(attack.status[Game.STATUS_AMP] == 1)
		{
			mod = 1.5;
			attack.status[Game.STATUS_AMP] = 0; 
		}
		
		return (20.0 + mod*2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class SoothingSong extends Action
{
	private static final long serialVersionUID = 1L;

	public SoothingSong()
	{
		this.id = SOOTHINGSONG;
		this.name = "Soothing Song";
		this.desc = "Cures a target's status ailments.";
		this.animationPrefix = "soothingSong";
		
		this.mp = 10;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class BajaBlast extends Action
{
	private static final long serialVersionUID = 1L;

	public BajaBlast()
	{
		this.id = BAJABLAST;
		this.name = "Baja Blast";
		this.desc = "A dark and evil water magic. May inflict Slow.";
		this.animationPrefix = "bajaBlast";
		
		this.mp = 22;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_WATER;
		this.damageType = MAGICAL;
		this.hitRateMod = 20;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		double mod = 1.0;
		System.out.println("attack.status[Game.STATUS_AMP] = " + attack.status[Game.STATUS_AMP]);
		if(attack.status[Game.STATUS_AMP] == 1)
		{
			mod = 1.5;
			attack.status[Game.STATUS_AMP] = 0; 
		}
		
		return (20.0 + mod*2.0*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class BlueShield extends Action
{
	private static final long serialVersionUID = 1L;

	public BlueShield()
	{
		this.id = BLUESHIELD;
		this.name = "Blue Shield";
		this.desc = "Shield an ally from magical attacks.";
		this.animationPrefix = "blueShield";
		
		this.mp = 12;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class BlueBarrier extends Action
{
	private static final long serialVersionUID = 1L;

	public BlueBarrier()
	{
		this.id = BLUEBARRIER;
		this.name = "Blue Barrier";
		this.desc = "Shield all allies from magical attacks.";
		this.animationPrefix = "blueShield";
		
		this.mp = 40;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class SillyDance extends Action
{
	private static final long serialVersionUID = 1L;

	public SillyDance()
	{
		this.id = SILLYDANCE;
		this.name = "Silly Dance";
		this.desc = "The silly dance of a masterful bard. Raises evasion.";
		this.animationPrefix = "sillyDance";
		
		this.mp = 16;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Amp extends Action //TODO: make the Amp status effect actually do something
{
	private static final long serialVersionUID = 1L;

	public Amp()
	{
		this.id = AMP;
		this.name = "Amp";
		this.desc = "Boosts the power of your next non-Amp skill.";
		this.animationPrefix = "amp"; //TODO
		
		this.mp = 8;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class RadicalRiff extends Action
{
	private static final long serialVersionUID = 1L;

	public RadicalRiff()
	{
		this.id = RADICALRIFF;
		this.name = "Radical Riff";
		this.desc = "A tubular melody that restores allies' MP.";
		this.animationPrefix = "radicalRiff";
		
		this.mp = 25;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class ChiptuneOfLife extends Action
{
	private static final long serialVersionUID = 1L;

	public ChiptuneOfLife()
	{
		this.id = CHIPTUNEOFLIFE;
		this.name = "Chiptune Of Life";
		this.desc = "A delightful chiptune to raise the dead.";
		this.animationPrefix = "chiptuneOfLife";
		
		this.mp = 30;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Shuriken extends Action
{
	private static final long serialVersionUID = 1L;

	public Shuriken()
	{
		this.id = SHURIKEN;
		this.name = "Shuriken";
		this.desc = "A ninja shuriken with pinpoint accuracy.";
		this.animationPrefix = "shuriken";
		
		this.mp = 4;
		this.targetType = ONEENEMY;
		
		this.numFrames = 25;
		this.damageFrame = 5;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 999; //never miss
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class KageShadows extends Action
{
	private static final long serialVersionUID = 1L;

	public KageShadows()
	{
		this.id = KAGESHADOWS;
		this.name = "Kage Shadows";
		this.desc = "A powerful jutsu that Blinds all foes.";
		this.animationPrefix = "kageShadows";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 30;
		this.damageFrame = 10;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class InflictShame extends Action
{
	private static final long serialVersionUID = 1L;

	public InflictShame()
	{
		this.id = INFLICTSHAME;
		this.name = "Inflict Shame";
		this.desc = "Shame a dishonorable foe. Removes/prevents buffs.";
		this.animationPrefix = "inflictShame";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class DefendHonor extends Action
{
	private static final long serialVersionUID = 1L;

	public DefendHonor()
	{
		this.id = DEFENDHONOR;
		this.name = "Defend Honor";
		this.desc = "Defend an ally's honor to protect them.";
		this.animationPrefix = "defendHonor";
		
		this.mp = 12;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class HonorForAll extends Action
{
	private static final long serialVersionUID = 1L;

	public HonorForAll()
	{
		this.id = HONORFORALL;
		this.name = "Honor For All";
		this.desc = "Protect all party members with honorable honor.";
		this.animationPrefix = "defendHonor";
		
		this.mp = 40;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class NinjutsuSlice extends Action
{
	private static final long serialVersionUID = 1L;

	public NinjutsuSlice()
	{
		this.id = NINJUTSUSLICE;
		this.name = "Ninjutsu Slice";
		this.desc = "A ninja-like slice. Stronger when foe has high Mag.";
		this.animationPrefix = "slice";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = true;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 1.25*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk + 1.5*target.mag)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class SamuraiSlash extends Action
{
	private static final long serialVersionUID = 1L;

	public SamuraiSlash()
	{
		this.id = SAMURAISLASH;
		this.name = "Samurai Slash";
		this.desc = "Slash a foe and attempt to cut their HP in half.";
		this.animationPrefix = "slice";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = true;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 1.25*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class BushidoBlade extends Action
{
	private static final long serialVersionUID = 1L;

	public BushidoBlade()
	{
		this.id = BUSHIDOBLADE;
		this.name = "Bushido Blade";
		this.desc = "Becomes stronger as the user gets closer to death.";
		this.animationPrefix = "slice";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = true;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		double mod = 7.0/(6.0*((double)attack.hp/attack.maxHp)+1.0);
		
		return mod*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class Muramasamara extends Action
{
	private static final long serialVersionUID = 1L;

	public Muramasamara()
	{
		this.id = MURAMASAMARA;
		this.name = "Muramasamara";
		this.desc = "A wild attack of anime power. Low accuracy.";
		this.animationPrefix = "slice";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = -50;
		this.attacksWithWeapon = true;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 5.0*(5.0 + ((attack.str + (attack.level/7.0) + attack.atk)*(attack.str * attack.atk))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}


/**
 * Kitten
 */
class Fire extends Action
{
	private static final long serialVersionUID = 1L;

	public Fire()
	{
		this.id = FIRE;
		this.name = "Fire";
		this.desc = "A boring, regular Fire spell.";
		this.animationPrefix = "fire";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_FIRE;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (20.0 + Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class BigFire extends Action
{
	private static final long serialVersionUID = 1L;

	public BigFire()
	{
		this.id = BIGFIRE;
		this.name = "Big Fire";
		this.desc = "A large and good Fire spell.";
		this.animationPrefix = "fire"; //TODO
		
		this.mp = 28;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_FIRE;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (40.0 + 1.6*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),2.0))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class LightningBolt extends Action
{
	private static final long serialVersionUID = 1L;

	public LightningBolt()
	{
		this.id = LIGHTNINGBOLT;
		this.name = "Lightning Bolt";
		this.desc = "A bolt of lightning.";
		this.animationPrefix = "lightningBolt";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_LIGHTNING;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (30.0 + 1.15*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class LightningStorm extends Action
{
	private static final long serialVersionUID = 1L;

	public LightningStorm()
	{
		this.id = LIGHTNINGSTORM;
		this.name = "Lightning Storm";
		this.desc = "A storm of lightning.";
		this.animationPrefix = "lightningBolt";
		
		this.mp = 34;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_LIGHTNING;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (30.0 + 1.15*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class EarthSpike extends Action
{
	private static final long serialVersionUID = 1L;

	public EarthSpike()
	{
		this.id = EARTHSPIKE;
		this.name = "Earth Spike";
		this.desc = "A spike of earth.";
		this.animationPrefix = "earthquake"; //TODO: earthSpike
		
		this.mp = 18;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_EARTH;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (35.0 + 1.25*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class Earthquake extends Action
{
	private static final long serialVersionUID = 1L;

	public Earthquake()
	{
		this.id = EARTHQUAKE;
		this.name = "Earthquake";
		this.desc = "A large and scary earthquake.";
		this.animationPrefix = "earthquake";
		
		this.mp = 40;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_EARTH;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (35.0 + 1.25*Math.pow(attack.mag + (attack.level/7.0) + (attack.dex/10.0),1.9))*(20.0/(20.0+target.getMdef())) - target.getMdef();
	}
}

class Steal extends Action
{
	private static final long serialVersionUID = 1L;

	public Steal()
	{
		this.id = STEAL;
		this.name = "Steal";
		this.desc = "Steal an item.";
		this.animationPrefix = ""; //TODO
		
		this.mp = 0;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		Action attackAction = new Attack();
		
		return attackAction.calculateDamage(attack, target) / 2; //half Attack damage
	}
}

class Purr extends Action
{
	private static final long serialVersionUID = 1L;

	public Purr()
	{
		this.id = PURR;
		this.name = "Purr";
		this.desc = "The sign of a happycat. Restore some HP and MP.";
		this.animationPrefix = ""; //TODO
		
		this.mp = 0;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class CatNap extends Action
{
	private static final long serialVersionUID = 1L;

	public CatNap()
	{
		this.id = CATNAP;
		this.name = "Cat Nap";
		this.desc = "Put a foe to sleep with a cat-like yawn.";
		this.animationPrefix = "catNap"; //TODO
		
		this.mp = 8;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class CatScratch extends Action
{
	private static final long serialVersionUID = 1L;

	public CatScratch()
	{
		this.id = CATSCRATCH;
		this.name = "Cat Scratch";
		this.desc = "Slash at a foe's eyes. May inflict Blind.";
		this.animationPrefix = "catScratch";
		
		this.mp = 8;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

class Devour extends Action
{
	private static final long serialVersionUID = 1L;

	public Devour()
	{
		this.id = DEVOUR;
		this.name = "Devour";
		this.desc = "Chomp on a foe and consume their nutrients.";
		this.animationPrefix = "devour";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef();
	}
}

/**
 * Kev-Bot
 */

class Discharge extends Action
{
	private static final long serialVersionUID = 1L;

	public Discharge()
	{
		this.id = DISCHARGE;
		this.name = "Discharge";
		this.desc = "Uncontrollably blast electricity everywhere.";
		this.animationPrefix = "discharge";
		
		this.mp = 16;
		this.targetType = ALLUNITS;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_LIGHTNING;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		if(attack.unitType == target.unitType) //do less damage to allies
		{
			return 1;
		}
		else
		{
			return 100;
		}

		//return (25.0 + 5.0*attack.str + ((attack.str + (attack.level/7.0))*(Math.pow(attack.str,2.0)))/32.0)*(20.0/(20.0+target.getDef())) - target.getDef(); TODO
	}
}

class RobotTears extends Action
{
	private static final long serialVersionUID = 1L;

	public RobotTears()
	{
		this.id = ROBOTTEARS;
		this.name = "Robot Tears";
		this.desc = "Cry on a foe, reducing their Atk.";
		this.animationPrefix = "robotTears";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Goalkeeper extends Action
{
	private static final long serialVersionUID = 1L;

	public Goalkeeper()
	{
		this.id = GOALKEEPER;
		this.name = "Goalkeeper";
		this.desc = "Provokes all single target attacks.";
		this.animationPrefix = "goalKeeper"; //TODO: can just leave this blank?
		
		this.mp = 8;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class EjectMoney extends Action
{
	private static final long serialVersionUID = 1L;

	public EjectMoney()
	{
		this.id = EJECTMONEY;
		this.name = "Eject Money";
		this.desc = "Launch your hard earned money at an enemy.";
		this.animationPrefix = "ejectMoney";
		
		this.mp = 14; //TODO: work on all these MP costs, they're mostly random right now
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = PHYSICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 10; //TODO: strong physical
	}
}

class SystemReboot extends Action
{
	private static final long serialVersionUID = 1L;

	public SystemReboot()
	{
		this.id = SYSTEMREBOOT;
		this.name = "System Reboot";
		this.desc = "Turn off and on again. Might do nothing.";
		this.animationPrefix = "item0"; //TODO
		
		this.mp = 20; //TODO: work on all these MP costs, they're mostly random right now
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class Malfunction extends Action
{
	private static final long serialVersionUID = 1L;

	public Malfunction()
	{
		this.id = MALFUNCTION;
		this.name = "Malfunction";
		this.desc = "Critical error. Beep boop.";
		this.animationPrefix = "malfunction";
		
		this.mp = 1;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_FIRE;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;				//TODO: make magic "almost never" miss (like hit rate 100) and give more reasonable hit rate boosts to physical skills
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 15; //TODO: damage should be randomized (0.1 - 2.0?)
	}
}

class Overload extends Action
{
	private static final long serialVersionUID = 1L;

	public Overload()
	{
		this.id = OVERLOAD;
		this.name = "Overload";
		this.desc = "Costs all MP. Deal damage based on amount spent.";
		this.animationPrefix = "overload";
		
		this.mp = -1; //will consume all MP
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_LIGHTNING;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 15; //TODO: damage should be random
	}
}

class SquirtOil extends Action
{
	private static final long serialVersionUID = 1L;

	public SquirtOil()
	{
		this.id = SQUIRTOIL;
		this.name = "Squirt Oil";
		this.desc = "Cover your foes with gooey robot oil.";
		this.animationPrefix = "squirtOil";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class InstallVirus extends Action
{
	private static final long serialVersionUID = 1L;

	public InstallVirus()
	{
		this.id = INSTALLVIRUS;
		this.name = "Install Virus";
		this.desc = "Install a virus that lowers a foe's defenses.";
		this.animationPrefix = "installVirus";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class RobotBeam extends Action
{
	private static final long serialVersionUID = 1L;

	public RobotBeam()
	{
		this.id = ROBOTBEAM;
		this.name = "Robot Beam";
		this.desc = "An unexciting robot beam. It does damage.";
		this.animationPrefix = "robotBeam";
		
		this.mp = 12;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
		this.damageType = MAGICAL;
		this.hitRateMod = 10;
		this.attacksWithWeapon = false;
	}
	
	public double calculateDamage(Unit attack, Unit target)
	{
		return 15; //TODO
	}
}

/**
 * Items
 */
class HPItem extends Action
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HPItem()
	{
		this.id = HPITEM;
		this.name = "HP Item";
		this.desc = "Using an HP healing item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class MPItem extends Action
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MPItem()
	{
		this.id = MPITEM;
		this.name = "MP Item";
		this.desc = "Using an MP healing item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}

class ReviveItem extends Action
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReviveItem()
	{
		this.id = REVIVEITEM;
		this.name = "Revive Item";
		this.desc = "Using a revive item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.ELEMENT_NONE;
	}
}