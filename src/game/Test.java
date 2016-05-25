package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Test
{
	public static void main(String[] args)
	{
		Unit brian = new Brian(1,0);
		brian.equip[Unit.WEAPON] = new BadSword(1);
		brian.recalculateStats();
		brian.str = 20;
		brian.atk = 20;
		
		Unit snake = new Snake(0);
		
		Action attack = new Attack();
		for(int i=0; i<=25; i++)
		{
			snake.def = i;
			
			System.out.print(i + ": " + (int) attack.calculateDamage(brian,snake));
			
			double newDmg = (5.0 + ((brian.str + (brian.level/7.0) + brian.atk)*(brian.str * brian.atk))/32.0)*(20.0/(20.0+snake.def)) - snake.def;
			
			System.out.println("\t" + (int) newDmg);
		}
	}
	
	public static void dmgTest()
	{
		Unit user = new Kitten(1,0);
		user.equip[Unit.WEAPON] = new BadSword(1);
		user.recalculateStats();
		
		Snake snake = new Snake(0);
		snake.mag = 30;
		
		int[][] damage = new int[99][8];
		
		for(int lvl=1; lvl<=99; lvl++)
		{
			for(int j=0; j<8; j++)
			{
				user.atk = 5+3*j;
				damage[lvl-1][j] = Game.calculateDamage(user,Action.EARTHQUAKE,snake);
			}
			
			user.levelUp();
			user.hp = user.maxHp;
		}
		
		System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s %-8s %-8s %-8s","Lvl","5 atk","8 atk","11 atk","14 atk","17 atk", "20 atk", "23 atk", "26 atk");
		for(int lvl=1; lvl<=99; lvl++)
		{
			System.out.println();
			System.out.printf("%-8s %-8s %-8s %-8s %-8s %-8s %-8s %-8s %-8s",lvl,damage[lvl-1][0],damage[lvl-1][1],damage[lvl-1][2],damage[lvl-1][3]
			                 ,damage[lvl-1][4],damage[lvl-1][5],damage[lvl-1][6],damage[lvl-1][7]);
		}
	}
	
	public static void statLoop(int base, double constant, double exponential)
	{
		for(int level=1; level<=99; level++)
		{
			System.out.println("Level " + level + " = " + (base + constant*level)*Math.pow(exponential,level));
		}
	}
	
	public static void characterAttackDmgTest(int str, int weap, int def)
	{
		double ddmg = 2 + str + ((str + weap)*(str * weap))/32;
		ddmg = ddmg * (1.0 - 2*def/100.0) - 2*def;
		
		System.out.println(str + " str, " + weap + " weap, " + def + " def ===> " + (int) ddmg + " dmg");
	}
	
	public static void newCharacterAttackDmgTest(int level, int str, int weap, int def)
	{
		double ddmg = level + str + ((str + weap)*(str * weap))/32;
		ddmg = ddmg * (20.0/(20.0+def)) - def;
		
		System.out.println(level + " lvl, " + str + " str, " + weap + " weap, " + def + " def ===> " + (int) ddmg + " dmg");
	}
	
	public static void characterMagicDmgTest(int intl, int constant, double mod, int mdef)
	{
		double ddmg = constant + (intl + (2 * intl)) * mod;
		ddmg = ddmg * (1.0 - 2*mdef/100.0) - 2*mdef;
		
		System.out.println(intl + " intl, " + mod + " mod, " + mdef + " mdef ===> " + (int) ddmg + " dmg");
	}
	
	public static void monsterDmgTest()
	{
		System.out.println("Atk\tDef\tDmg");
		for(int atk=5; atk<=10; atk++)
		{
			for(int def=0; def<=5; def++)
			{
				double ddmg = Math.sqrt(atk) + 2*atk;
				System.out.println(atk + "\t" + def + "\t" + (int)ddmg);
			}
			
			System.out.println();
		}
	}
	
	public static void test()
	{
		
	}
	
	public static void why(int... links)
	{
		int[] test = new int[4];
		for(int dir : links)
		{
			test[dir] = 1;
			System.out.println("Adding link " + dir);
		}
	}
	
	public static void inheritanceTest()
	{
		ArrayList<Master> elements = new ArrayList<Master>();
		elements.add(new Slave1());
		elements.add(new Slave2());
		
		System.out.println(elements.get(0).test(1));
		System.out.println(elements.get(1).test(1));
	}
	
	public static void monsterTest()
	{
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Snake(0));
		monsters.add(new SnakeKing(1));
		
		MonsterAction action1 = monsters.get(0).getActionTest();
		MonsterAction action2 = monsters.get(1).getActionTest();
		
		System.out.println("action 1 = " + action1.action);
		System.out.println("action 2 = " + action2.action);
	}
}

class Master
{
	public Master()
	{
		
	}
	
	public String test(int a)
	{
		return "Master " + a;
	}
}

class Slave1 extends Master
{
	public Slave1()
	{
		
	}
	
	public String test(int a)
	{
		return "Slave1 " + a;
	}
}

class Slave2 extends Master
{
	public Slave2()
	{
		
	}
	
	public String test(int a)
	{
		return "Slave2 " + a;
	}
}
