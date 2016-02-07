package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Test
{
	public static void main(String[] args)
	{
		//dmgTest();
		
		for(int damage=10; damage<=3000; damage+=50)
		{
			for(int def=0; def<=22; def++)
			{
				//double result = damage * (20.0/(20.0+def));
				//result -= Math.pow(def,2.0)/20.0;
				double result = damage * (1.0 - (2.5*def)/100.0) - def;
				
				if(result < 1) result = 1;
				
				System.out.println("Damage = " + damage + ", Def = " + def + ": " + (int) result);
			}
		}
	}
	
	public static void dmgTest()
	{
		Unit user = new Kitten(1,0);
		user.equip[Unit.WEAPON] = new BadSword(1);
		user.recalculateStats();
		
		Snake snake = new Snake();
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
}
