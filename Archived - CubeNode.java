/*package game;

import java.util.ArrayList;

public class CubeNode
{
	public int id;
	public String nodeImage;
	public int nodeSize;
	public Action skill;
	public String name;
	public String desc;
	public int mod;
	public boolean activated;
	public int x;
	public int y;
	public int[] links;
	
	public static final int HP = 1000;
	public static final int MP = 1001;
	public static final int STR = 1002;
	public static final int MAG = 1003;
	public static final int DEX = 1004;
	public static final int SPD = 1005;
	
	public static final int DEF = 1006;
	public static final int MDEF = 1007;
	public static final int HITRATE = 1008;
	public static final int EVASION = 1009;
	public static final int CRITRATE = 1010;
	
	public static final int ELESNACKRES = 1011;
	public static final int ELEFIRERES = 1012;
	public static final int ELELIGHTNINGRES = 1013;
	public static final int ELEWATERRES = 1014;
	public static final int ELEEARTHRES = 1015;
	public static final int ELEPOISONRES = 1016;
	public static final int ELEALLRES = 1017;
	
	public static final int STATUSPOISONRES = 1018;
	public static final int STATUSSILENCERES = 1019;
	public static final int STATUSBLINDRES = 1020;
	public static final int STATUSSLEEPRES = 1021;
	public static final int STATUSSLOWRES = 1022;
	public static final int STATUSBERSERKRES = 1023;
	public static final int STATUSALLRES = 1024;
	
	public CubeNode()
	{
		
	}
	
	public CubeNode(boolean activated, int x, int y, int... links)
	{
		this.activated = activated;
		this.x = x;
		this.y = y;
		
		this.links = new int[4];
		for(int dir : links)
		{
			this.links[dir] = 1;
		}
	}
	
	public CubeNode(int mod, boolean activated, int x, int y, int... links)
	{
		this.mod = mod;
		this.activated = activated;
		this.x = x;
		this.y = y;
		
		this.links = new int[4];
		for(int dir : links)
		{
			this.links[dir] = 1;
			System.out.println("Adding link " + dir);
		}
	}
	
	public String getNodeImage()
	{
		if(this.nodeImage.equals("")) return "";
		
		if(this.activated) return this.nodeImage + "1";
		else return this.nodeImage + "0";
	}
	
	public void processNode(Unit character)
	{
		//overwritten for each node
	}
}

class SkillNode extends CubeNode
{
	public SkillNode()
	{
		
	}
	
	public SkillNode(Action skill, boolean activated, int x, int y, int... links)
	{
		super(activated, x, y, links);
		
		this.id = skill.id;
		this.skill = skill;
		this.name = skill.name;
		this.desc = "Skill: " + skill.desc;
		this.nodeImage = "node" + skill.name;
		this.nodeSize = 100;
	}
	
	public void processNode(Unit character)
	{
		if(!character.hasSkill(this.skill.id))
		{
			character.skill.add(skill);
		}
	}
}

class StatNode extends CubeNode
{
	public StatNode()
	{
		
	}
	
	public StatNode(int stat, int mod, boolean activated, int x, int y, int... links)
	{
		super(activated, x, y, links);
		
		this.id = stat;
		this.mod = mod;
		this.nodeSize = 100;
		
		if(this.id == HP)
		{
			this.name = "HP";
			this.desc = "Max HP +" + this.mod;
		}
		else if(this.id == MP)
		{
			this.name = "MP";
			this.desc = "Max MP + " + this.mod;
		}
		else if(this.id == STR)
		{
			this.name = "Str";
			this.desc = "Strength + " + this.mod;
		}
		else if(this.id == MAG)
		{
			this.name = "Mag";
			this.desc = "Magic + " + this.mod;
		}
		else if(this.id == DEX)
		{
			this.name = "Dex";
			this.desc = "Dexterity + " + this.mod;
		}
		else if(this.id == SPD)
		{
			this.name = "Spd";
			this.desc = "Speed + " + this.mod;
		}
		
		else if(this.id == DEF)
		{
			this.name = "Def";
			this.desc = "Defense + " + this.mod;
		}
		else if(this.id == MDEF)
		{
			this.name = "M Def";
			this.desc = "Magic Defense + " + this.mod;
		}
		else if(this.id == HITRATE)
		{
			this.name = "Hit";
			this.desc = "Hit Rate + " + this.mod;
		}
		else if(this.id == EVASION)
		{
			this.name = "Eva";
			this.desc = "Evasion + " + this.mod;
		}
		else if(this.id == CRITRATE)
		{
			this.name = "Crit";
			this.desc = "Crit Rate + " + this.mod + "%";
		}
		
		else if(this.id == ELESNACKRES)
		{
			this.name = "Ele Res";
			this.desc = "Snack Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELEFIRERES)
		{
			this.name = "Ele Res";
			this.desc = "Fire Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELELIGHTNINGRES)
		{
			this.name = "Ele Res";
			this.desc = "Lightning Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELEWATERRES)
		{
			this.name = "Ele Res";
			this.desc = "Water Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELEEARTHRES)
		{
			this.name = "Ele Res";
			this.desc = "Earth Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELEPOISONRES)
		{
			this.name = "Ele Res";
			this.desc = "Poison Elemental Resistance + " + this.mod + "%";
		}
		else if(this.id == ELEALLRES)
		{
			this.name = "Ele Res";
			this.desc = "All Elemental Resistance + " + this.mod + "%";
		}
		
		else if(this.id == STATUSPOISONRES)
		{
			this.name = "Sta Res";
			this.desc = "Poison Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSSILENCERES)
		{
			this.name = "Sta Res";
			this.desc = "Silence Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSBLINDRES)
		{
			this.name = "Sta Res";
			this.desc = "Blind Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSSLEEPRES)
		{
			this.name = "Sta Res";
			this.desc = "Sleep Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSSLOWRES)
		{
			this.name = "Sta Res";
			this.desc = "Slow Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSBERSERKRES)
		{
			this.name = "Sta Res";
			this.desc = "Berserk Status Resistance + " + this.mod + "%";
		}
		else if(this.id == STATUSALLRES)
		{
			this.name = "Sta Res";
			this.desc = "All Status Resistance + " + this.mod + "%";
		}
		
		this.nodeImage = ""; //Create node image with Graphics instead
	}
	
	public void processNode(Unit character)
	{
		switch(this.id)
		{
			case HP: character.maxHp += this.mod; break;
			case MP: character.maxMp += this.mod; break;
			case STR: character.str += this.mod; break;
			case MAG: character.mag += this.mod; break;
			case DEX: character.dex += this.mod; break;
			case SPD: character.spd += this.mod; break;
			
			case DEF: character.def += this.mod; break;
			case MDEF: character.mdef += this.mod; break;
			case HITRATE: character.hitRate += this.mod; break;
			case EVASION: character.evasion += this.mod; break;
			case CRITRATE: character.critRate += this.mod; break;
			
			case ELESNACKRES: character.elementResistance[Game.SNACK] += this.mod; break;
			case ELEFIRERES: character.elementResistance[Game.FIRE] += this.mod; break;
			case ELELIGHTNINGRES: character.elementResistance[Game.LIGHTNING] += this.mod; break;
			case ELEWATERRES: character.elementResistance[Game.WATER] += this.mod; break;
			case ELEEARTHRES: character.elementResistance[Game.EARTH] += this.mod; break;
			case ELEPOISONRES: character.elementResistance[Game.POISON] += this.mod; break;
			case ELEALLRES:
			{
				character.elementResistance[Game.SNACK] += this.mod;
				character.elementResistance[Game.FIRE] += this.mod;
				character.elementResistance[Game.LIGHTNING] += this.mod;
				character.elementResistance[Game.WATER] += this.mod;
				character.elementResistance[Game.EARTH] += this.mod;
				character.elementResistance[Game.POISON] += this.mod;
			}
			break;
			
			case STATUSPOISONRES: character.statusResistance[Unit.POISON] += this.mod; break;
			case STATUSSILENCERES: character.statusResistance[Unit.SILENCE] += this.mod; break;
			case STATUSBLINDRES: character.statusResistance[Unit.BLIND] += this.mod; break;
			case STATUSSLEEPRES: character.statusResistance[Unit.SLEEP] += this.mod; break;
			case STATUSSLOWRES: character.statusResistance[Unit.SLOW] += this.mod; break;
			case STATUSBERSERKRES: character.statusResistance[Unit.BERSERK] += this.mod; break;
			case STATUSALLRES:
			{
				character.statusResistance[Unit.POISON] += this.mod;
				character.statusResistance[Unit.SILENCE] += this.mod;
				character.statusResistance[Unit.BLIND] += this.mod;
				character.statusResistance[Unit.SLEEP] += this.mod;
				character.statusResistance[Unit.SLOW] += this.mod;
				character.statusResistance[Unit.BERSERK] += this.mod;
			}
			break;
		}
	}
}*/