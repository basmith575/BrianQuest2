/*package game;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeGrid
{
	public int id;
	public static final int BRIANGRID = 0;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	public String gridImage;
	public ArrayList<CubeNode> nodes;
	public int[][] nodeMap;
	
	public int width, height;
	
	public CubeGrid()
	{
		
	}
	
	public ArrayList<CubeNode> getVisibleNodes(int x, int y)
	{
		ArrayList<CubeNode> visibleNodes = new ArrayList<CubeNode>();
		for(int i=0; i<this.nodes.size(); i++)
		{
			int nodeSize = this.nodes.get(i).nodeSize;
			int nodeX = getXCoordinate(this.nodes.get(i).x);
			int nodeY = getYCoordinate(this.nodes.get(i).y);
			
			if(nodeX+nodeSize >= x && nodeY+nodeSize >= y && nodeX < x+850 && nodeY < y+650)
			{
				visibleNodes.add(this.nodes.get(i));
			}
		}
		
		return visibleNodes;
	}
	
	public static int getXCoordinate(int xIndex)
	{
		return 50 + 145*xIndex;
	}
	
	public static int getYCoordinate(int yIndex)
	{
		return 150 + 145*yIndex;
	}
	
	public void addNode(CubeNode node)
	{
		this.nodes.add(node);
		this.nodeMap[node.x][node.y] = this.nodes.size() - 1;
	}
	
	public int getUpNodeIndex(CubeNode node)
	{
		for(int i=node.y-1; i>=0; i++)
		{
			if(this.nodeMap[node.x][i] != -1) return this.nodeMap[node.x][i];
		}
		
		return -1;
	}
	
	public int getUpNodeIndex(int nodeIndex)
	{
		return getUpNodeIndex(this.nodes.get(nodeIndex));
	}
	
	public int getDownNodeIndex(CubeNode node)
	{
		for(int i=node.y+1; i<this.nodeMap[0].length; i++)
		{
			if(this.nodeMap[node.x][i] != -1) return this.nodeMap[node.x][i];
		}
		
		return -1;
	}
	
	public int getDownNodeIndex(int nodeIndex)
	{
		return getDownNodeIndex(this.nodes.get(nodeIndex));
	}
	
	public int getRightNodeIndex(CubeNode node)
	{
		for(int i=node.x+1; i<this.nodeMap.length; i++)
		{
			if(this.nodeMap[i][node.y] != -1) return this.nodeMap[i][node.y];
		}
		
		return -1;
	}
	
	public int getRightNodeIndex(int nodeIndex)
	{
		return getRightNodeIndex(this.nodes.get(nodeIndex));
	}
	
	public int getLeftNodeIndex(CubeNode node)
	{
		for(int i=node.x-1; i>=0; i++)
		{
			if(this.nodeMap[i][node.y] != -1) return this.nodeMap[i][node.y];
		}
		
		return -1;
	}
	
	public int getLeftNodeIndex(int nodeIndex)
	{
		return getLeftNodeIndex(this.nodes.get(nodeIndex));
	}
	
	public int getAdjacentNodeIndex(CubeNode node, int direction)
	{
		switch(direction)
		{
			case UP: return getUpNodeIndex(node);
			case RIGHT: return getRightNodeIndex(node);
			case DOWN: return getDownNodeIndex(node);
			case LEFT: return getLeftNodeIndex(node);
		}
		
		return -1;
	}
	
	public boolean isLocked(CubeNode node)
	{
		//TODO: check other things like if a skill is required
		
		if(this.id == BRIANGRID)
		{
			if(node.id == Action.BRIANPUNCH) return false; //Brian Punch unlocked by default 
		}
		
		if(node.activated) return false;
		
		//check if any adjacent linked nodes are unlocked
		int nodeIndex;
		for(int i=0; i<4; i++)
		{
			if(node.links[i] == 1)
			{
				nodeIndex = getAdjacentNodeIndex(node, i);
				
				if(nodeIndex == -1) break;
				
				if(this.nodes.get(nodeIndex).activated) return false;
			}
		}
		
		return true;
	}
}

class BrianGrid extends CubeGrid
{
	public BrianGrid(boolean[] activated)
	{
		this.gridImage = "BrianGrid";
		this.nodes = new ArrayList<CubeNode>();
		
		this.nodeMap = new int[12][9];
		for(int i=0; i<12; i++)
		{
			for(int j=0; j<9; j++)
			{
				this.nodeMap[i][j] = -1;
			}
		}
		
		if(activated.length < 98) //sanity check
		{
			activated = Arrays.copyOf(activated, 98);
		}
		
		//this.width = 1800;
		//this.height = 1400;
		this.width = 205 + 145*(this.nodeMap.length - 1);
		this.height = 240 + 145*(this.nodeMap[0].length - 1);
		
		addNode(new StatNode(CubeNode.STR,2,activated[0],0,0,DOWN));
		addNode(new SkillNode(new BrianPunch(),activated[1],0,1,UP));
		addNode(new StatNode(CubeNode.DEX,1,activated[2],0,2,UP));
		addNode(new StatNode(CubeNode.HP,20,activated[3],0,3,UP));
		addNode(new StatNode(CubeNode.MAG,2,activated[4],0,4,UP));
		addNode(new StatNode(CubeNode.SPD,2,activated[5],0,5,UP));
		addNode(new StatNode(CubeNode.MP,20,activated[6],0,6,UP));
	}
}
*/