package game;

import java.util.ArrayList;

public class Item
{
	/**
	 * Item types
	 */
	public static final int HP = 0;
	public static final int MP = 1;
	public static final int REVIVE = 2;
	public static final int WEAPON = 3;
	public static final int HAT = 4;
	public static final int ARMOR = 5;
	public static final int SHOE = 6;
	public static final int ACCESSORY = 7;
	public static final int VALUABLE = 8;
	public static final int KEYITEM = 9;
	
	/**
	 * ID of the item
	 */
	public static final int NOITEM = -1;
	
	//HP
	public static final int POTION = 0;
	
	//MP
	public static final int ETHER = 100;
	
	//Revive
	public static final int MOUNTAINDEW = 200;
	
	//Weapon - Sword
	public static final int BADSWORD = 300;
	public static final int RUSTYSWORD = 301;
	
	//Weapon - Stick
	public static final int STICK = 400;
	public static final int STICKOFENLIGHTENMENT = 499;
	
	//Weapon - Instrument
	public static final int UKULELE = 500;
	
	//Weapon - Other
	
	
	//Hat
	public static final int MTNDEWHAT = 700;
	public static final int BIKEHELMET = 701;
	
	//Armor
	public static final int TORNSHIRT = 800;
	public static final int PLASTICARMOR = 801;
	
	//Shoe
	public static final int OLDSOCKS = 900;
	public static final int NICEBOOTS = 901;
	
	//Accessory
	public static final int RINGPOP = 1000;
	public static final int SPICYHOTDORITOS = 1001;
	
	//Valuable
	
	//Key Item
	
	public int id;
	public int type;
	public String name;
	public String desc;
	public String equipDesc; //additional desc for equipment
	public int price;
	public int amt;
	public int qty;
	
	//for equipment
	public int atk;
	public int def;
	public int mdef;
	public int hpMod;
	public int mpMod;
	public int strMod;
	public int magMod;
	public int dexMod;
	public int spdMod;
	public int hitRateMod;
	public int evasionMod;
	public int critRateMod;
	public int[] elementResistance;
	public int[] statusResistance;
	public String attackAnimation;
	public String imageName;
	public ArrayList<ActiveSkill> activeSkills;
	public ArrayList<PassiveSkill> passiveSkills;
	
	public boolean usable; //usable outside of battle
	
	public int weaponType;
	public static final int SWORDTYPE = 0;
	public static final int STICKTYPE = 1;
	public static final int INSTRUMENTTYPE = 2;
	
	public Item()
	{
		
	}
	
	public static Item itemFromID(int id)
	{
		if(id == NOITEM) return new NoItem();
		
		//HP
		else if(id == POTION) return new Potion(1);
		
		//MP
		else if(id == ETHER) return new Ether(1);
		
		//Status Heal
		
		//Revive
		else if(id == MOUNTAINDEW) return new MountainDew(1);
		
		//Weapon - Sword
		else if(id == BADSWORD) return new BadSword(1);
		else if(id == RUSTYSWORD) return new RustySword(1);
		
		//Weapon - Stick
		else if(id == STICK) return new Stick(1);
		else if(id == STICKOFENLIGHTENMENT) return new StickOfEnlightenment(1);
		
		//Weapon - Instrument
		else if(id == UKULELE) return new Ukulele(1);
		
		//Weapon - Other
		
		//Hat
		else if(id == MTNDEWHAT) return new MtnDewHat(1);
		else if(id == BIKEHELMET) return new BikeHelmet(1);
		
		//Armor
		else if(id == TORNSHIRT) return new TornShirt(1);
		else if(id == PLASTICARMOR) return new PlasticArmor(1);
		
		//Shoe
		else if(id == OLDSOCKS) return new OldSocks(1);
		else if(id == NICEBOOTS) return new NiceBoots(1);
		
		//Accessory
		else if(id == RINGPOP) return new RingPop(1);
		else if(id == SPICYHOTDORITOS) return new SpicyHotDoritos(1);
		
		//Valuable
		
		//Key Item
		
		else return new NoItem();
	}
	
	public String getIconString()
	{
		if(this.type == WEAPON)
		{
			return "itemIcon"+this.type+"_"+this.weaponType; 
		}
		else return "itemIcon"+this.type; 
	}
	
	public static String weaponTypeString(int weaponType)
	{
		switch(weaponType)
		{
			case SWORDTYPE: return "Sword";
			case STICKTYPE: return "Stick";
		}
		
		return "";
	}
	
	public boolean equippableBy(int characterID)
	{
		return false;
	}
	
	public boolean isEquipment()
	{
		return (this.type == WEAPON || this.type == HAT || this.type == ARMOR || this.type == SHOE || this.type == ACCESSORY);
	}
}

class NoItem extends Item
{
	public NoItem()
	{
		this.name = "None";
		this.id = NOITEM;
		this.type = NOITEM;
		this.qty = 1;
	}
}

/**
 * HP
 */

class Potion extends Item
{
	public Potion(int qty)
	{
		this.name = "Potion";
		this.desc = "Heals 30 HP.";
		this.id = POTION;
		this.type = HP;
		this.price = 50;
		this.amt = 30;
		this.usable = true;
		this.qty = qty;
	}
}

/**
 * MP
 */

class Ether extends Item
{
	public Ether(int qty)
	{
		this.name = "Ether";
		this.desc = "Restores 20 MP.";
		this.id = ETHER;
		this.type = MP;
		this.price = 75;
		this.amt = 20;
		this.usable = true;
		this.qty = qty;
	}
}

/**
 * Revive
 */

class MountainDew extends Item
{
	public MountainDew(int qty)
	{
		this.name = "Mountain Dew";
		this.desc = "Revives someone.";
		this.id = MOUNTAINDEW;
		this.type = REVIVE;
		this.price = 1000;
		this.amt = 25;
		this.usable = true;
		this.qty = qty;
	}
}

/**
 * Weapon
 */
class BadSword extends Item
{
	public BadSword(int qty)
	{
		this.name = "Bad Sword";
		this.desc = "A terrible sword that's falling apart.";
		this.equipDesc = "7 atk.";
		this.id = BADSWORD;
		this.type = WEAPON;
		this.weaponType = SWORDTYPE;
		this.usable = false;
		this.qty = qty;
		
		this.imageName = "Bad Sword";
		this.attackAnimation = Game.SLICE;
		
		this.price = 100;
		
		this.atk = 7;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveBrianPunch(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.BRIAN: return true;
			case Character.RYAN: return true;
			case Character.MYCHAL: return true;
			case Character.KITTEN: return true;
		}
		
		return false;
	}
}

class RustySword extends Item
{
	public RustySword(int qty)
	{
		this.name = "Rusty Sword";
		this.desc = "A sword covered in centuries of rust.";
		this.equipDesc = "8 atk.";
		this.id = RUSTYSWORD;
		this.type = WEAPON;
		this.weaponType = SWORDTYPE;
		this.usable = false;
		this.qty = qty;
		
		this.imageName = "Rusty Sword";
		this.attackAnimation = Game.SLICE;
		
		this.price = 250;
		
		this.atk = 8;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveFire(0, false));
		this.activeSkills.add(new ActiveNinjutsuSlice(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new PoisonStatusResist());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.BRIAN: return true;
			case Character.RYAN: return true;
			case Character.MYCHAL: return true;
			case Character.KITTEN: return true;
		}
		
		return false;
	}
}

class Stick extends Item
{
	public Stick(int qty)
	{
		this.name = "Stick";
		this.desc = "A stick. Possibly from a tree or a shrub.";
		this.equipDesc = "4 atk.";
		this.id = STICK;
		this.type = WEAPON;
		this.weaponType = STICKTYPE;
		this.usable = false;
		this.qty = qty;
		
		this.imageName = "Stick";
		this.attackAnimation = Game.HIT;
		
		this.price = 20;
		
		this.atk = 4;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveBarf(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new MeScared());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.ALEX: return true;
		}
		
		return false;
	}
}

class StickOfEnlightenment extends Item
{
	public StickOfEnlightenment(int qty)
	{
		this.name = "Stick Of Enlightenment";
		this.desc = "A glorious stick packed full of wisdom.";
		this.equipDesc = "10 atk.";
		this.id = STICKOFENLIGHTENMENT;
		this.type = WEAPON;
		this.weaponType = STICKTYPE;
		this.usable = false;
		this.qty = qty;
		
		this.imageName = "Stick";
		this.attackAnimation = Game.HIT;
		
		this.price = 50000;
		
		this.atk = 10;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveSummonTrains(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new Enlightenment());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.ALEX: return true;
		}
		
		return false;
	}
}

class Ukulele extends Item
{
	public Ukulele(int qty)
	{
		this.name = "Ukulele";
		this.desc = "A small guitar-like instrument.";
		this.equipDesc = "5 atk.";
		this.id = UKULELE;
		this.type = WEAPON;
		this.weaponType = INSTRUMENTTYPE;
		this.usable = false;
		this.qty = qty;
		
		this.imageName = "Ukeleke";
		this.attackAnimation = Game.HIT;
		
		this.price = 300;
		
		this.atk = 5;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveBlessingOfMiku(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new MPPlus10());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.RYAN: return true;
		}
		
		return false;
	}
}

/**
 * Hats
 */
class MtnDewHat extends Item
{
	public MtnDewHat(int qty)
	{
		this.name = "Mtn Dew Hat";
		this.desc = "A green hat with the MD logo on it.";
		this.equipDesc = "1 Def. 1 MDef. 20% Water resistance.";
		this.id = MTNDEWHAT;
		this.type = HAT;
		this.usable = false;
		this.qty = qty;
		
		this.price = 150;
		
		this.def = 1;
		this.mdef = 1;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		this.elementResistance[Game.WATER] = 20;
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveMtnDewWave(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new HPPlus10());
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}

class BikeHelmet extends Item
{
	public BikeHelmet(int qty)
	{
		this.name = "Bike Helmet";
		this.desc = "A head-protecting helmet.";
		this.equipDesc = "2 Def.";
		this.id = BIKEHELMET;
		this.type = HAT;
		this.usable = false;
		this.qty = qty;
		
		this.price = 200;
		
		this.def = 2;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveVomitEruption(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new Miracle());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.ALEX: return true;
		}
		
		return false;
	}
}

/**
 * Armor
 */
class TornShirt extends Item
{
	public TornShirt(int qty)
	{
		this.name = "Torn Shirt";
		this.desc = "This shirt is all torn up.";
		this.equipDesc = "1 def.";
		this.id = TORNSHIRT;
		this.type = ARMOR;
		this.usable = false;
		this.qty = qty;
		
		this.price = 50;
		
		this.def = 1;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new HPPlus10());
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}

class PlasticArmor extends Item
{
	public PlasticArmor(int qty)
	{
		this.name = "Plastic Armor";
		this.desc = "Armor made of low-quality plastic.";
		this.equipDesc = "2 def.";
		this.id = PLASTICARMOR;
		this.type = ARMOR;
		this.usable = false;
		this.qty = qty;
		
		this.price = 200;
		
		this.def = 2;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
		this.passiveSkills.add(new HPPlus20());
	}
	
	public boolean equippableBy(int characterID)
	{
		switch(characterID)
		{
			case Character.RYAN: return true; //TODO: remove
			case Character.MYCHAL: return true;
		}
		return true;
	}
}

/**
 * Shoes
 */
class OldSocks extends Item
{
	public OldSocks(int qty)
	{
		this.name = "Old Socks";
		this.desc = "A pair of rancid old socks.";
		this.equipDesc = "+5 evasion. +10% Fire resistance.";
		this.id = OLDSOCKS;
		this.type = SHOE;
		this.usable = false;
		this.qty = qty;
		
		this.price = 25;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 5;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		this.elementResistance[Game.FIRE] = 10;
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.passiveSkills = new ArrayList<PassiveSkill>();
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}

class NiceBoots extends Item
{
	public NiceBoots(int qty)
	{
		this.name = "Nice Boots";
		this.desc = "A decent pair of boots.";
		this.equipDesc = "1 Def.";
		this.id = NICEBOOTS;
		this.type = SHOE;
		this.usable = false;
		this.qty = qty;
		
		this.price = 50;
		
		this.def = 1;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveBlueShield(0, false));
		this.activeSkills.add(new ActiveDefendHonor(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}

/**
 * Accessory
 */
class RingPop extends Item
{
	public RingPop(int qty)
	{
		this.name = "Ring Pop";
		this.desc = "A filthy Ring Pop. Literally useless.";
		this.equipDesc = "";
		this.id = RINGPOP;
		this.type = ACCESSORY;
		this.usable = false;
		this.qty = qty;
		
		this.price = 10;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 0;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.passiveSkills = new ArrayList<PassiveSkill>();
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}

class SpicyHotDoritos extends Item
{
	public SpicyHotDoritos(int qty)
	{
		this.name = "Spicy Hot Doritos";
		this.desc = "A half-eaten bag of Spicy Hot Doritos.";
		this.equipDesc = "+5% Crit Rate.";
		this.id = SPICYHOTDORITOS;
		this.type = ACCESSORY;
		this.usable = false;
		this.qty = qty;
		
		this.price = 10;
		
		this.def = 0;
		this.mdef = 0;
		
		this.hpMod = 0;
		this.mpMod = 0;
		this.strMod = 0;
		this.magMod = 0;
		this.dexMod = 0;
		this.spdMod = 0;
		
		this.hitRateMod = 0;
		this.evasionMod = 0;
		this.critRateMod = 5;
		
		this.elementResistance = new int[Game.NUMELEMENTS];
		this.statusResistance = new int[Unit.NUMSTATUS];
		
		this.activeSkills = new ArrayList<ActiveSkill>();
		this.activeSkills.add(new ActiveFire(0, false));
		
		this.passiveSkills = new ArrayList<PassiveSkill>();
	}
	
	public boolean equippableBy(int characterID)
	{
		return true;
	}
}