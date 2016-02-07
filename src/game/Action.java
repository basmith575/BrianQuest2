package game;

import java.util.Random;

public abstract class Action
{
	public int id;
	
	public static final int NOACTION = 0;
	public static final int ATTACK = 1;
	public static final int POISON = 2;
	public static final int REGEN = 3;
	public static final int FLEE = 4;
	
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
	 * Ryan
	 */
	public static final int BLESSINGOFARINO = 300;
	public static final int BLESSINGOFMIKU = 301;
	public static final int MYSTERIOUSMELODY = 302;
	public static final int BAJABLAST = 303;
	public static final int BLUESHIELD = 304;
	public static final int BLUEBARRIER = 305;
	public static final int SILLYDANCE = 306;
	public static final int AMP = 307;
	public static final int RADICALRIFF = 308;
	public static final int CHIPTUNEOFLIFE = 309;
	
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
	 * Other
	 */
	
	/**
	 * Item
	 */
	public static final int HPITEM = 1000;
	public static final int MPITEM = 1001;
	public static final int REVIVEITEM = 1002;
	
	public int mp; //MP cost
	
	public int targetType; //what it targets
	public static final int ONEENEMY = 0;
	public static final int ALLENEMIES = 1;
	public static final int SELF = 2;
	public static final int ONEALLY = 3;
	public static final int ALLALLIES = 4;
	public static final int ONEUNIT = 5;
	public static final int OTHER = 6; //special cases to handle manually
	
	public String name;
	public String desc; //skill description

	public int element;
	
	public int numFrames, damageFrame;
	
	public int imageHeight, imageWidth;
	
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
			
			//Ryan
			case BLESSINGOFARINO: return new BlessingOfArino();
			case BLESSINGOFMIKU: return new BlessingOfMiku();
			case MYSTERIOUSMELODY: return new MysteriousMelody();
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
			
			//Other
			
			//Item
			case HPITEM: return new HPItem();
			case MPITEM: return new MPItem();
			case REVIVEITEM: return new ReviveItem();
		}
		
		return new NoAction();
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
	
	public static boolean wakesUp(int id)
	{
		//TODO: add any physical skills here
		if(id == ATTACK || id == BRIANPUNCH) return true;
		
		return false;
	}
	
	public static int[] animationMap(int id)
	{
		int numFrames = Action.actionFromID(id).numFrames;
		
		int[] animations = new int[numFrames-1];
		for(int i=0; i<animations.length; i++) animations[i] = 0;
		
		//TODO: clean up this code
		
		/**
		 * Brian
		 */
		if(id == CHEEZITBLAST)
		{
			animations[0] = 0;
			animations[1] = 0;
			animations[2] = 1;
			animations[3] = 1;
			animations[4] = 1;
			animations[5] = 1;
			animations[6] = 1;
			animations[7] = 1;
			animations[8] = 2;
			animations[9] = 2;
			animations[10] = 2;
			animations[11] = 2;
			animations[12] = 2;
			animations[13] = 2;
			animations[14] = 3;
			animations[15] = 3;
			animations[16] = 3;
			animations[17] = 3;
			animations[18] = 3;
		}
		
		/**
		 * Alex
		 */
		else if(id == SHRIEK)
		{
			//loop 0-2
			for(int i=0; i<animations.length; i++)
			{
				animations[i] = (i/2)%3;
			}
		}
		
		/**
		 * Ryan
		 */
		else if(id == BAJABLAST)
		{
			//full animation
			for(int i=0; i<animations.length; i++)
			{
				animations[i] = i/2;
			}
		}
		else if(id == BLESSINGOFMIKU)
		{
			//loop 0-1 slower
			for(int i=0; i<animations.length; i++)
			{
				animations[i] = ((i/4)%2);
			}
		}
		
		/**
		 * Michael
		 */
		else if(id == KAGESHADOWS)
		{
			for(int i=0; i<animations.length; i++)
			{
				animations[i] = (i/4)%2;
			}
		}
		
		/**
		 * Kitten
		 */
		else if(id == LIGHTNINGBOLT || id == LIGHTNINGSTORM)
		{
			//0-4
			for(int i=0; i<animations.length; i++)
			{
				animations[i] = i/4;
			}
		}
		
		return animations;
	}
	
	public static int[] xOffset(int id)
	{
		int numFrames = Action.actionFromID(id).numFrames;
		
		int[] offsets = new int[numFrames-1];
		for(int i=0; i<offsets.length; i++) offsets[i] = 0;
		
		if(id == SHURIKEN)
		{
			for(int i=0; i<5; i++)
			{
				offsets[i] = -160 + 40*i;
			}
		}
		
		return offsets;
	}
	
	public static int[] yOffset(int id)
	{
		int numFrames = Action.actionFromID(id).numFrames;
		
		int[] offsets = new int[numFrames-1];
		for(int i=0; i<offsets.length; i++) offsets[i] = 0;
		
		return offsets;
	}
}

class NoAction extends Action
{
	public NoAction()
	{
		this.id = NOACTION;
		this.name = "None";
		this.desc = "No action";
		this.element = Game.NOELEMENT;
	}
}

class Attack extends Action
{
	public Attack()
	{
		this.id = ATTACK;
		this.name = "Attack";
		this.desc = "Attack";
		
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Poison extends Action
{
	public Poison()
	{
		this.id = POISON;
		this.name = "Poison";
		this.desc = "Poison";
		
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT; //no point setting this
	}
}

class Regen extends Action
{
	public Regen()
	{
		this.id = REGEN;
		this.name = "Regen";
		this.desc = "Regen";
		
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

/**
 * Brian
 */
class BrianPunch extends Action
{
	public BrianPunch()
	{
		this.id = BRIANPUNCH;
		this.name = "Brian Punch";
		this.desc = "A punch with the strength of Brian.";
		
		this.mp = 6;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class BrianSmash extends Action
{
	public BrianSmash()
	{
		this.id = BRIANSMASH;
		this.name = "Brian Smash";
		this.desc = "Smash a foe with Brian-like force. Ignores Def.";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class CheezItBlast extends Action
{
	public CheezItBlast()
	{
		this.id = CHEEZITBLAST;
		this.name = "Cheez-It Blast";
		this.desc = "An eruption of delicious Cheez-its.";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.SNACK;
	}
}

class CoolRanchLaser extends Action
{
	public CoolRanchLaser()
	{
		this.id = COOLRANCHLASER;
		this.name = "Cool Ranch Laser";
		this.desc = "A powerful laser made of Cool Ranch Doritos.";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.SNACK;
	}
}

class FlavorExplosion extends Action
{
	public FlavorExplosion()
	{
		this.id = FLAVOREXPLOSION;
		this.name = "Flavor Explosion";
		this.desc = "A massive explosion of the most appetizing flavors.";
		
		this.mp = 44;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.SNACK;
	}
}

class MtnDewWave extends Action
{
	public MtnDewWave()
	{
		this.id = MTNDEWWAVE;
		this.name = "Mtn Dew Wave";
		this.desc = "Create a soothing wave of succulent Mtn Dew.";
		
		this.mp = 12;
		this.targetType = ALLALLIES;
		
		this.numFrames = 30;
		this.damageFrame = 10;
		
		this.element = Game.NOELEMENT;
	}
}

class GottaGoFast extends Action
{
	public GottaGoFast()
	{
		this.id = GOTTAGOFAST;
		this.name = "Gotta Go Fast";
		this.desc = "Summon Sonic to boost an ally's speed.";
		
		this.mp = 10;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class GottaGoFaster extends Action
{
	public GottaGoFaster()
	{
		this.id = GOTTAGOFASTER;
		this.name = "Gotta Go Faster";
		this.desc = "Unleash an army of Sonics for optimal speed.";
		
		this.mp = 38;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Murder extends Action
{
	public Murder()
	{
		this.id = MURDER;
		this.name = "Murder";
		this.desc = "Stone cold murder.";
		
		this.mp = 24;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class MassMurder extends Action
{
	public MassMurder()
	{
		this.id = MASSMURDER;
		this.name = "Mass Murder";
		this.desc = "Stone cold mass murder.";
		
		this.mp = 54;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

/**
 * Alex
 */
class Barf extends Action
{
	public Barf()
	{
		this.id = BARF;
		this.name = "Barf";
		this.desc = "Barf on a foe. May inflict Poison.";
		
		this.mp = 4;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.POISON;
	}
}

class Flail extends Action
{
	public Flail()
	{
		this.id = FLAIL;
		this.name = "Flail";
		this.desc = "A wild and crazy flail. Also hurts the user.";
		
		this.mp = 6;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Annoy extends Action
{
	public Annoy()
	{
		this.id = ANNOY;
		this.name = "Annoy";
		this.desc = "Annoy a target and make it go Berserk.";
		
		this.mp = 8;
		this.targetType = ONEUNIT;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Cower extends Action
{
	public Cower()
	{
		this.id = COWER;
		this.name = "Cower";
		this.desc = "Defend like a coward until your next turn.";
		
		this.mp = 4;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Shriek extends Action
{
	public Shriek()
	{
		this.id = SHRIEK;
		this.name = "Shriek";
		this.desc = "A horrible shriek. May inflict Silence.";
		
		this.mp = 12;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class EatPopTart extends Action
{
	public EatPopTart()
	{
		this.id = EATPOPTART;
		this.name = "Eat Pop-Tart";
		this.desc = "Eat a tasty, healing Pop-Tart. May poison consumer.";
		
		this.mp = 6;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Kamikaze extends Action
{
	public Kamikaze()
	{
		this.id = KAMIKAZE;
		this.name = "Kamikaze";
		this.desc = "The ultimate sacrifice to deal massive damage.";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class VomitEruption extends Action
{
	public VomitEruption()
	{
		this.id = VOMITERUPTION;
		this.name = "Vomit Eruption";
		this.desc = "An eruption of toxic vomit on all foes.";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.POISON;
	}
}

class SummonTrains extends Action
{
	public SummonTrains()
	{
		this.id = SUMMONTRAINS;
		this.name = "Summon Trains";
		this.desc = "Unleash a torrent of trains from the sky.";
		
		this.mp = 32;
		this.targetType = ALLENEMIES;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

/**
 * Ryan
 */
class BlessingOfArino extends Action
{
	public BlessingOfArino()
	{
		this.id = BLESSINGOFARINO;
		this.name = "Blessing Of Arino";
		this.desc = "Bless an ally with the power of the Kacho.";
		
		this.mp = 20;
		this.targetType = ONEALLY;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class BlessingOfMiku extends Action
{
	public BlessingOfMiku()
	{
		this.id = BLESSINGOFMIKU;
		this.name = "Blessing Of Miku";
		this.desc = "Summon Miku-chan to give the party Regen.";
		
		this.mp = 26;
		this.targetType = ALLALLIES;
		
		this.imageHeight = 150;
		this.imageWidth = 150;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class MysteriousMelody extends Action
{
	public MysteriousMelody()
	{
		this.id = MYSTERIOUSMELODY;
		this.name = "Mysterious Melody";
		this.desc = "A strange melody of a random element.";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class BajaBlast extends Action
{
	public BajaBlast()
	{
		this.id = BAJABLAST;
		this.name = "Baja Blast";
		this.desc = "A dark and evil water magic. May inflict Slow.";
		
		this.mp = 22;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.WATER;
	}
}

class BlueShield extends Action
{
	public BlueShield()
	{
		this.id = BLUESHIELD;
		this.name = "Blue Shield";
		this.desc = "Shield an ally from magical attacks.";
		
		this.mp = 12;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class BlueBarrier extends Action
{
	public BlueBarrier()
	{
		this.id = BLUEBARRIER;
		this.name = "Blue Barrier";
		this.desc = "Shield all allies from magical attacks.";
		
		this.mp = 40;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class SillyDance extends Action
{
	public SillyDance()
	{
		this.id = SILLYDANCE;
		this.name = "Silly Dance";
		this.desc = "The silly dance of a masterful bard. Raises evasion.";
		
		this.mp = 16;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Amp extends Action //TODO: make the Amp status effect actually do something
{
	public Amp()
	{
		this.id = AMP;
		this.name = "Amp";
		this.desc = "Boosts the power of your next non-Amp skill.";
		
		this.mp = 8;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class RadicalRiff extends Action
{
	public RadicalRiff()
	{
		this.id = RADICALRIFF;
		this.name = "Radical Riff";
		this.desc = "A tubular melody that restores allies' MP.";
		
		this.mp = 25;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class ChiptuneOfLife extends Action
{
	public ChiptuneOfLife()
	{
		this.id = CHIPTUNEOFLIFE;
		this.name = "Chiptune Of Life";
		this.desc = "A delightful chiptune to raise the dead.";
		
		this.mp = 30;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Shuriken extends Action
{
	public Shuriken()
	{
		this.id = SHURIKEN;
		this.name = "Shuriken";
		this.desc = "A ninja shuriken with pinpoint accuracy.";
		
		this.mp = 4;
		this.targetType = ONEENEMY;
		
		this.numFrames = 25;
		this.damageFrame = 5;
		
		this.element = Game.NOELEMENT;
	}
}

class KageShadows extends Action
{
	public KageShadows()
	{
		this.id = KAGESHADOWS;
		this.name = "Kage Shadows";
		this.desc = "A powerful jutsu that Blinds all foes.";
		
		this.mp = 10;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 30;
		this.damageFrame = 10;
		
		this.element = Game.NOELEMENT;
	}
}

class InflictShame extends Action
{
	public InflictShame()
	{
		this.id = INFLICTSHAME;
		this.name = "Inflict Shame";
		this.desc = "Shame a dishonorable foe. Removes/prevents buffs.";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class DefendHonor extends Action
{
	public DefendHonor()
	{
		this.id = DEFENDHONOR;
		this.name = "Defend Honor";
		this.desc = "Defend an ally's honor to protect them.";
		
		this.mp = 12;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class HonorForAll extends Action
{
	public HonorForAll()
	{
		this.id = HONORFORALL;
		this.name = "Honor For All";
		this.desc = "Protect all party members with honorable honor.";
		
		this.mp = 40;
		this.targetType = ALLALLIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class NinjutsuSlice extends Action
{
	public NinjutsuSlice()
	{
		this.id = NINJUTSUSLICE;
		this.name = "Ninjutsu Slice";
		this.desc = "A ninja-like slice. Stronger when foe has high Mag.";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class SamuraiSlash extends Action
{
	public SamuraiSlash()
	{
		this.id = SAMURAISLASH;
		this.name = "Samurai Slash";
		this.desc = "Slash a foe and attempt to cut their HP in half.";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class BushidoBlade extends Action
{
	public BushidoBlade()
	{
		this.id = BUSHIDOBLADE;
		this.name = "Bushido Blade";
		this.desc = "Becomes stronger as the user gets closer to death.";
		
		this.mp = 16;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Muramasamara extends Action
{
	public Muramasamara()
	{
		this.id = MURAMASAMARA;
		this.name = "Muramasamara";
		this.desc = "A wild attack of anime power. Low accuracy.";
		
		this.mp = 20;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}


/**
 * Kitten
 */
class Fire extends Action
{
	public Fire()
	{
		this.id = FIRE;
		this.name = "Fire";
		this.desc = "A boring, regular Fire spell.";
		
		this.mp = 10;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.FIRE;
	}
}

class BigFire extends Action
{
	public BigFire()
	{
		this.id = BIGFIRE;
		this.name = "Big Fire";
		this.desc = "A large and good Fire spell.";
		
		this.mp = 28;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.FIRE;
	}
}

class LightningBolt extends Action
{
	public LightningBolt()
	{
		this.id = LIGHTNINGBOLT;
		this.name = "Lightning Bolt";
		this.desc = "A bolt of lightning.";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.LIGHTNING;
	}
}

class LightningStorm extends Action
{
	public LightningStorm()
	{
		this.id = LIGHTNINGSTORM;
		this.name = "Lightning Storm";
		this.desc = "A storm of lightning.";
		
		this.mp = 34;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.LIGHTNING;
	}
}

class EarthSpike extends Action
{
	public EarthSpike()
	{
		this.id = EARTHSPIKE;
		this.name = "Earth Spike";
		this.desc = "A spike of earth.";
		
		this.mp = 18;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.EARTH;
	}
}

class Earthquake extends Action
{
	public Earthquake()
	{
		this.id = EARTHQUAKE;
		this.name = "Earthquake";
		this.desc = "A large and scary earthquake.";
		
		this.mp = 40;
		this.targetType = ALLENEMIES;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.EARTH;
	}
}

class Steal extends Action
{
	public Steal()
	{
		this.id = STEAL;
		this.name = "Steal";
		this.desc = "Steal an item.";
		
		this.mp = 0;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Purr extends Action
{
	public Purr()
	{
		this.id = PURR;
		this.name = "Purr";
		this.desc = "The sign of a happycat. Restore some HP and MP.";
		
		this.mp = 0;
		this.targetType = SELF;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class CatNap extends Action
{
	public CatNap()
	{
		this.id = CATNAP;
		this.name = "Cat Nap";
		this.desc = "Put a foe to sleep with a cat-like yawn.";
		
		this.mp = 8;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class CatScratch extends Action
{
	public CatScratch()
	{
		this.id = CATSCRATCH;
		this.name = "Cat Scratch";
		this.desc = "Slash at a foe's eyes. May inflict Blind.";
		
		this.mp = 8;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class Devour extends Action
{
	public Devour()
	{
		this.id = DEVOUR;
		this.name = "Devour";
		this.desc = "Chomp on a foe and consume their nutrients.";
		
		this.mp = 14;
		this.targetType = ONEENEMY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}


/**
 * Items
 */
class HPItem extends Action
{
	public HPItem()
	{
		this.id = HPITEM;
		this.name = "HP Item";
		this.desc = "Using an HP healing item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class MPItem extends Action
{
	public MPItem()
	{
		this.id = MPITEM;
		this.name = "MP Item";
		this.desc = "Using an MP healing item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}

class ReviveItem extends Action
{
	public ReviveItem()
	{
		this.id = REVIVEITEM;
		this.name = "Revive Item";
		this.desc = "Using a revive item";
		
		this.mp = 0;
		this.targetType = ONEALLY;
		
		this.numFrames = 20;
		this.damageFrame = 0;
		
		this.element = Game.NOELEMENT;
	}
}