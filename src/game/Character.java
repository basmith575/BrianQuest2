package game;

import java.util.ArrayList;

public class Character
{
	public static final int NONE = -1;
	public static final int BRIAN = 0;
	public static final int ALEX = 1;
	public static final int RYAN = 2;
	public static final int MYCHAL = 3;
	public static final int KITTEN = 4;
	public static final int KEVBOT = 5; //TODO
}

class None extends Unit
{
	public None()
	{
		this.id = Character.NONE;
	}
}

class Brian extends Unit
{
	public Brian(int level, int index)
	{
		this.id = Character.BRIAN;
		this.name = "Brian";
		this.className = "Coolguy";
		this.level = level;
		this.index = index;
		this.unitType = Unit.CHARACTER;
		
		this.imageWidth = 100;
		this.imageHeight = 125;
		this.spriteHeight = 125;
		
		this.initialize();
	}
	
	public Brian(Brian brian)
	{
		super(brian);
	}
	
	public void calcBaseStats()
	{
		this.maxHp = (int)((130 + 8.0*this.level)*Math.pow(1.01,this.level));
		this.maxMp = (int)((40 + 3.0*this.level)*Math.pow(1.002,this.level));
		
		this.str = (int)(6.5 + 0.40*this.level);
		this.mag = (int)(6.0 + 0.40*this.level);
		this.dex = (int)(5.0 + 0.35*this.level);
		this.spd = (int)(5.5 + 0.35*this.level);
	}
}

class Alex extends Unit
{
	public Alex(int level, int index)
	{
		this.id = Character.ALEX;
		this.name = "Alex";
		this.className = "Fool";
		this.level = level;
		this.index = index;
		this.unitType = Unit.CHARACTER;
		
		this.imageWidth = 100;
		this.imageHeight = 125;
		this.spriteHeight = 125;
		
		initialize();
	}
	
	public Alex(Alex alex)
	{
		super(alex);
	}
	
	public void calcBaseStats()
	{
		this.maxHp = (int)((90 + 6.0*this.level)*Math.pow(1.007,this.level));
		this.maxMp = (int)((20 + 2.0*this.level)*Math.pow(1.0015,this.level));
		
		this.str = (int)(3.0 + 0.25*this.level);
		this.mag = (int)(1.0 + 0.10*this.level);
		this.dex = (int)(1.5 + 0.25*this.level);
		this.spd = (int)(2.0 + 0.25*this.level);
	}
}

class Ryan extends Unit
{
	public Ryan(int level, int index)
	{
		this.id = Character.RYAN;
		this.name = "Ryan";
		this.className = "Blue Bard";
		this.level = level;
		this.index = index;
		this.unitType = Unit.CHARACTER;
		
		this.imageWidth = 100;
		this.imageHeight = 125;
		this.spriteHeight = 125;
		
		this.initialize();
	}
	
	public Ryan(Ryan ryan)
	{
		super(ryan);
	}
	
	public void calcBaseStats()
	{
		this.maxHp = (int)((110 + 7.0*this.level)*Math.pow(1.009,this.level));
		this.maxMp = (int)((45 + 3.0*this.level)*Math.pow(1.0025,this.level));
		
		this.str = (int)(5.0 + 0.30*this.level);
		this.mag = (int)(6.0 + 0.45*this.level);
		this.dex = (int)(6.0 + 0.40*this.level);
		this.spd = (int)(4.0 + 0.30*this.level);
	}
}

class Mychal extends Unit
{
	public Mychal(int level, int index)
	{
		this.id = Character.MYCHAL;
		this.name = "Mychal";
		this.className = "Anime Ninja Knight";
		this.level = level;
		this.index = index;
		this.unitType = Unit.CHARACTER;
		
		this.imageWidth = 100;
		this.imageHeight = 125;
		this.spriteHeight = 125;
		
		this.initialize();
	}
	
	public Mychal(Mychal mychal)
	{
		super(mychal);
	}
	
	public void calcBaseStats()
	{
		this.maxHp = (int)((150 + 9.0*this.level)*Math.pow(1.0125,this.level));
		this.maxMp = (int)((30 + 2.0*this.level)*Math.pow(1.002,this.level));
		
		this.str = (int)(7.0 + 0.40*this.level);
		this.mag = (int)(2.0 + 0.25*this.level);
		this.dex = (int)(4.0 + 0.30*this.level);
		this.spd = (int)(3.0 + 0.25*this.level);
	}
}

class Kitten extends Unit
{
	public Kitten(int level, int index)
	{
		this.id = Character.KITTEN;
		this.name = "Kitten";
		this.className = "Cat";
		this.level = level;
		this.index = index;
		this.unitType = Unit.CHARACTER;
		
		this.imageWidth = 125;
		this.imageHeight = 125;
		this.spriteHeight = 125;
		
		initialize();
	}
	
	public Kitten(Kitten kitten)
	{
		super(kitten);
	}
	
	public void calcBaseStats()
	{
		this.maxHp = (int)((105 + 6.5*this.level)*Math.pow(1.008,this.level));
		this.maxMp = (int)((50 + 3.5*this.level)*Math.pow(1.003,this.level));
		
		this.str = (int)(4.0 + 0.30*this.level);
		this.mag = (int)(7.0 + 0.45*this.level);
		this.dex = (int)(5.0 + 0.30*this.level);
		this.spd = (int)(6.0 + 0.40*this.level);
	}
}
