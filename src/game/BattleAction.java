package game;

import java.util.ArrayList;

public class BattleAction
{	
	public Unit user;
	public ArrayList<Integer> targets;
	public ArrayList<Integer> values;
	public ArrayList<Boolean> mp;		//true if that index in damage refers to MP
	public ArrayList<Boolean> crit;
	public ArrayList<Boolean> miss;
	public ArrayList<Boolean> inflictStatus;
	public int[] animation;
	public int action;
	
	//for Mysterious Melody
	public int element;
	
	//for Steal
	public Item item;
	public int stealOutcome;
	
	public BattleAction(Unit user, int action, ArrayList<Integer> targets, ArrayList<Integer> values, ArrayList<Boolean> mp, int[] animation, ArrayList<Boolean> crit, ArrayList<Boolean> miss, ArrayList<Boolean> inflictStatus)
	{
		this.user = user;
		this.targets = targets;
		this.values = values;
		this.mp = mp;
		this.animation = animation;
		this.crit = crit;
		this.miss = miss;
		this.inflictStatus = inflictStatus;
		this.action = action;
		
		this.element = Game.NOELEMENT;
	}
}
