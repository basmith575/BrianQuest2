package game;

import java.awt.Color;
import java.util.ArrayList;

public class AfterBattleAlert
{
	String message;
	int characterIndex;
	Color color;
	
	public AfterBattleAlert(String message, int characterIndex, Color color)
	{
		this.message = message;
		this.characterIndex = characterIndex;
		this.color = color;
	}
	
	public static ArrayList<AfterBattleAlert> getAlertsForCharacterIndex(ArrayList<AfterBattleAlert> allAlerts, int index)
	{
		ArrayList<AfterBattleAlert> alerts = new ArrayList<AfterBattleAlert>();
		
		for(int i=0; i<allAlerts.size(); i++)
		{
			if(allAlerts.get(i).characterIndex == index)
			{
				alerts.add(allAlerts.get(i));
			}
		}
		
		return alerts;
	}
}