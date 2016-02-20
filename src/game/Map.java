package game;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Map
{
	public int id;
	public String name;
	public String song; 
	public Tile[][] tile;
	public int size;
	public int[] states;
	public int numStates;
	 
	public Random rand;
	
	ArrayList<ArrayList<ArrayList<Unit>>> wildArea;
	ArrayList<ArrayList<Integer>> wildRate;
	
	//UPDATE THESE WHEN ADDING A NEW MAP
	public static int[] mapStates = {TestTown.num};
	
	public static int numMaps = 1;
	
	public static Map makeMap(int index, int[] states)
	{
		if(index == 0) return new TestTown(states);
		
		else return null;
	}
	
	public static final int TESTTOWN = 0;
	
	public Map()
	{
		
	}
	
	public Map(int[] states)
	{
		this.states = states;
	}
	
	public ArrayList<Unit> getMonsters(int area)
	{
		ArrayList<Unit> monsterList = new ArrayList<Unit>();
		
		int totalRate = 0;
		for(int i=0; i<this.wildRate.get(area).size(); i++)
		{
			totalRate += this.wildRate.get(area).get(i);
		}
		
		rand = new Random();
		int a = rand.nextInt(totalRate);
		
		int sumRate = 0;
		for(int i=0; i<this.wildArea.get(area).size(); i++)
		{
			sumRate += this.wildRate.get(area).get(i);
			if(a < sumRate)
			{
				ArrayList<Unit> tempList = this.wildArea.get(area).get(i);
				
				for(int j=0; j<tempList.size(); j++)
				{
					monsterList.add(new Unit(tempList.get(j))); //new references
				}
				
				return monsterList;
			}
		}
		
		return null;
	}
	
	public void recalculateStates()
	{
		
	}
	
	public void addEnemyList(ArrayList<ArrayList<Unit>> list, Unit a, Unit b, Unit c, Unit d)
	{
		a.setIndex(0);
		b.setIndex(1);
		c.setIndex(2);
		d.setIndex(3);
		
		ArrayList<Unit> enemies = new ArrayList<Unit>();
		enemies.add(a);
		enemies.add(b);
		enemies.add(c);
		enemies.add(d);
		list.add(enemies);
	}
	
	public void addEnemyList(ArrayList<ArrayList<Unit>> list, Unit a, Unit b, Unit c)
	{
		a.setIndex(0);
		b.setIndex(1);
		c.setIndex(2);
		
		ArrayList<Unit> enemies = new ArrayList<Unit>();
		enemies.add(a);
		enemies.add(b);
		enemies.add(c);
		list.add(enemies);
	}
	
	public void addEnemyList(ArrayList<ArrayList<Unit>> list, Unit a, Unit b)
	{
		a.setIndex(0);
		b.setIndex(1);
		
		ArrayList<Unit> enemies = new ArrayList<Unit>();
		enemies.add(a);
		enemies.add(b);
		list.add(enemies);
	}
	
	public void addEnemyList(ArrayList<ArrayList<Unit>> list, Unit a)
	{
		a.setIndex(0);
		
		ArrayList<Unit> enemies = new ArrayList<Unit>();
		enemies.add(a);
		list.add(enemies);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2, int type)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				tile(i,j,type);
			}
		}
	}
	
	public void fillWater(int x1, int y1, int x2, int y2)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				tile(i,j,Tile.WATER);
				
				if(i == x1 && j == y1) thing(i,j,Thing.SHORENORTHWEST);
				else if(i == x1 && j == y2)	thing(i,j,Thing.SHORESOUTHWEST);
				else if(i == x2 && j == y1)	thing(i,j,Thing.SHORENORTHEAST);
				else if(i == x2 && j == y2)	thing(i,j,Thing.SHORESOUTHEAST);
				else if(i == x1) thing(i,j,Thing.SHOREWEST);
				else if(i == x2) thing(i,j,Thing.SHOREEAST);
				else if(j == y1) thing(i,j,Thing.SHORENORTH);
				else if(j == y2) thing(i,j,Thing.SHORESOUTH);
					
			}
		}
	}
	
	public void fillWater(int x1, int y1, int x2, int y2, boolean dock)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				tile(i,j,Tile.WATER);
				
				if(i == x1 && j == y1) thing(i,j,Thing.DOCKNORTHWEST);
				else if(i == x1 && j == y2)	thing(i,j,Thing.DOCKSOUTHWEST);
				else if(i == x2 && j == y1)	thing(i,j,Thing.DOCKNORTHEAST);
				else if(i == x2 && j == y2)	thing(i,j,Thing.DOCKSOUTHEAST);
				else if(i == x1) thing(i,j,Thing.DOCKWEST);
				else if(i == x2) thing(i,j,Thing.DOCKEAST);
				else if(j == y1) thing(i,j,Thing.DOCKNORTH);
				else if(j == y2) thing(i,j,Thing.DOCKSOUTH);
					
			}
		}
	}
	
	public void insideHouse(int x1, int y1, int x2, int y2)
	{
		fillRect(x1-1,y1-2,x2+1,y2+1,Tile.WOODWALL);
		fillRect(x1,y1-1,x2,y1-1,Tile.DARKWOODWALL);
		fillRect(x1-1,y2+2,x2+1,y2+2,Tile.DARKWOODWALL);
		fillRect(x1,y1,x2,y2,Tile.WOODFLOOR);
		
		int middle = (x1+x2)/2;
		
		fillRect(middle,y2+1,middle,y2+2,Tile.WOODFLOOR);
		tile(middle,y2+3,Tile.BLACKWALKABLE);
		thing(middle,y2+2,Thing.HOUSEEXIT);
	}
	
	public void insideHouse(int x1, int y1, int x2, int y2, int floor, int wall1, int wall2)
	{
		fillRect(x1-1,y1-2,x2+1,y2+1,wall1);
		fillRect(x1,y1-1,x2,y1-1,wall2);
		fillRect(x1-1,y2+2,x2+1,y2+2,wall2);
		fillRect(x1,y1,x2,y2,floor);
		
		int middle = (x1+x2)/2;
		
		fillRect(middle,y2+1,middle,y2+2,floor);
		tile(middle,y2+3,Tile.BLACKWALKABLE);
		thing(middle,y2+2,Thing.HOUSEEXIT);
	}
	
	public void insideHouse(int x1, int y1, int x2, int y2, int floor, int wall1, int wall2, int exitX)
	{
		fillRect(x1-1,y1-2,x2+1,y2+1,wall1);
		fillRect(x1,y1-1,x2,y1-1,wall2);
		fillRect(x1-1,y2+2,x2+1,y2+2,wall2);
		fillRect(x1,y1,x2,y2,floor);
		
		if(exitX != -1)
		{
			fillRect(exitX,y2+1,exitX,y2+2,floor);
			tile(exitX,y2+3,Tile.BLACKWALKABLE);
			thing(exitX,y2+2,Thing.HOUSEEXIT);
		}
	}
	
	public void insideHouse(int x1, int y1, int x2, int y2, int floor, int wall1, int wall2, int exitX, int ignoreSide)
	{
		
		if(ignoreSide == Game.NORTH)
		{
			fillRect(x1-1,y1+1,x2+1,y2+1,wall1);
			fillRect(x1-1,y2+2,x2+1,y2+2,wall2);
			fillRect(x1,y1,x2,y2,floor);
		}
		else if(ignoreSide == Game.EAST)
		{
			fillRect(x1-1,y1-2,x2,y2+1,wall1);
			fillRect(x1,y1-1,x2,y1-1,wall2);
			fillRect(x1-1,y2+2,x2,y2+2,wall2);
			fillRect(x1,y1,x2,y2,floor);
		}
		else if(ignoreSide == Game.SOUTH)
		{
			fillRect(x1-1,y1-2,x2+1,y2,wall1);
			fillRect(x1,y1-1,x2,y1-1,wall2);
			fillRect(x1,y1,x2,y2,floor);
			
			tile(x1-1,y2,wall2);
			tile(x2+1,y2,wall2);
		}
		else if(ignoreSide == Game.WEST)
		{
			fillRect(x1,y1-2,x2+1,y2+1,wall1);
			fillRect(x1,y1-1,x2,y1-1,wall2);
			fillRect(x1,y2+2,x2+1,y2+2,wall2);
			fillRect(x1,y1,x2,y2,floor);
		}
		else
		{
			fillRect(x1-1,y1-2,x2+1,y2+1,wall1);
			fillRect(x1,y1-1,x2,y1-1,wall2);
			fillRect(x1-1,y2+2,x2+1,y2+2,wall2);
			fillRect(x1,y1,x2,y2,floor);
		}
		
		if(exitX != -1)
		{
			fillRect(exitX,y2+1,exitX,y2+2,floor);
			tile(exitX,y2+3,Tile.BLACKWALKABLE);
			thing(exitX,y2+2,Thing.HOUSEEXIT);
		}
	}
	
	public void convertDock(int x1, int y1, int x2, int y2)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(this.tile[i][j].thing.type == Thing.SHORENORTH) thing(i,j,Thing.DOCKNORTH);
				else if(this.tile[i][j].thing.type == Thing.SHORENORTHEAST) thing(i,j,Thing.DOCKNORTHEAST);
				else if(this.tile[i][j].thing.type == Thing.SHOREEAST) thing(i,j,Thing.DOCKEAST);
				else if(this.tile[i][j].thing.type == Thing.SHORESOUTHEAST) thing(i,j,Thing.DOCKSOUTHEAST);
				else if(this.tile[i][j].thing.type == Thing.SHORESOUTH) thing(i,j,Thing.DOCKSOUTH);
				else if(this.tile[i][j].thing.type == Thing.SHORESOUTHWEST) thing(i,j,Thing.DOCKSOUTHWEST);
				else if(this.tile[i][j].thing.type == Thing.SHOREWEST) thing(i,j,Thing.DOCKWEST);
				else if(this.tile[i][j].thing.type == Thing.SHORENORTHWEST) thing(i,j,Thing.DOCKNORTHWEST);
			}
		}
	}
	
	public void drawShore(int x1, int y1, int x2, int y2)
	{
		drawShore(x1,y1,x2,y2,false);
	}
	
	public void drawShore(int x1, int y1, int x2, int y2, boolean dock)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(dock)
				{
					if(i == x1 && j == y1) thing(i,j,Thing.DOCKNORTHWEST);
					else if(i == x1 && j == y2)	thing(i,j,Thing.DOCKSOUTHWEST);
					else if(i == x2 && j == y1)	thing(i,j,Thing.DOCKNORTHEAST);
					else if(i == x2 && j == y2)	thing(i,j,Thing.DOCKSOUTHEAST);
					else if(i == x1) thing(i,j,Thing.DOCKWEST);
					else if(i == x2) thing(i,j,Thing.DOCKEAST);
					else if(j == y1) thing(i,j,Thing.DOCKNORTH);
					else if(j == y2) thing(i,j,Thing.DOCKSOUTH);
				}
				else
				{
					if(i == x1 && j == y1) thing(i,j,Thing.SHORENORTHWEST);
					else if(i == x1 && j == y2)	thing(i,j,Thing.SHORESOUTHWEST);
					else if(i == x2 && j == y1)	thing(i,j,Thing.SHORENORTHEAST);
					else if(i == x2 && j == y2)	thing(i,j,Thing.SHORESOUTHEAST);
					else if(i == x1) thing(i,j,Thing.SHOREWEST);
					else if(i == x2) thing(i,j,Thing.SHOREEAST);
					else if(j == y1) thing(i,j,Thing.SHORENORTH);
					else if(j == y2) thing(i,j,Thing.SHORESOUTH);
				}					
			}
		}
	}
	
	public void fillThingRect(int x1, int y1, int x2, int y2, int type)
	{
		int dx = Math.max(new Thing(type).width,1);
		int dy = Math.max(new Thing(type).height,1);
		
		for(int i=x1; i<=x2; i+=dx)
		{
			for(int j=y1; j<=y2; j+=dy)
			{
				try
				{
					thing(i,j,type);
				}
				catch(Exception e) {}
			}
		}
	}
	
	public void drawHollowThingRect(int x1, int y1, int x2, int y2, int insidex1, int insidey1, int insidex2, int insidey2, int type)
	{
		int dx = Math.max(new Thing(type).width,1);
		int dy = Math.max(new Thing(type).height,1);
		
		for(int i=x1; i<=x2; i+=dx)
		{
			for(int j=y1; j<=y2; j+=dy)
			{
				try
				{
					if(!(i >= insidex1 && i <= insidex2 && j >= insidey1 && j <= insidey2)) thing(i,j,type);
				}
				catch(Exception e) {}
			}
		}
	}
	
	public void drawRect(int x1, int y1, int x2, int y2, int type)
	{
		for(int i=x1; i<=x2; i++) this.tile[i][y1] = new Tile(type);
		for(int i=x1; i<=x2; i++) this.tile[i][y2] = new Tile(type);
		for(int i=y1; i<=y2; i++) this.tile[x1][i] = new Tile(type);
		for(int i=y1; i<=y2; i++) this.tile[x2][i] = new Tile(type);
	}
	
	public void drawThingRect(int x1, int y1, int x2, int y2, int type)
	{
		int dx = Math.max(new Thing(type).width,1);
		int dy = Math.max(new Thing(type).height,1);
		
		for(int i=x1; i<=x2; i+=dx) this.thing(i,y1,type);
		for(int i=x1; i<=x2; i+=dx) this.thing(i,y2,type);
		for(int i=y1; i<=y2; i+=dy) this.thing(x1,i,type);
		for(int i=y1; i<=y2; i+=dy) this.thing(x2,i,type);
	}
	
	public void drawCaveWalls()
	{
		int[] floors = {Tile.CAVEFLOOR};
		drawCaveWalls(floors,0);
	}
	
	public void drawCaveWalls(int[] floor)
	{
		drawCaveWalls(floor,0);
	}
	
	public void drawCaveWalls(int[] floor, int wall)
	{
		ArrayList<Integer> floorTypes = new ArrayList<Integer>();
		for(int i=0; i<floor.length; i++) floorTypes.add(floor[i]);
		
		int NORTH, EAST, SOUTH, WEST, NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHWEST;
		
		//if there are other cave wall types, use wall parameter to set these differently
		NORTH = Tile.CAVEWALLNORTH;
		EAST = Tile.CAVEWALLEAST;
		SOUTH = Tile.CAVEWALLSOUTH;
		WEST = Tile.CAVEWALLWEST;
		NORTHWEST = Tile.CAVEWALLNORTHWEST;
		NORTHEAST = Tile.CAVEWALLNORTHEAST;
		SOUTHEAST = Tile.CAVEWALLSOUTHEAST;
		SOUTHWEST = Tile.CAVEWALLSOUTHWEST;
		
		for(int i=5; i<this.tile.length-5; i++)
		{
			for(int j=5; j<this.tile[0].length-5; j++)
			{
				if(this.tile[i][j].thing.type == Thing.CAVEENTRANCE)
				{
					continue;
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j,NORTH);
					tile(i,j-1,NORTH);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i-1][j].type))
				{
					tile(i,j,EAST);
					tile(i+1,j,EAST);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i+1][j].type))
				{
					tile(i,j,WEST);
					tile(i-1,j,WEST);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i][j-1].type))
				{
					tile(i,j,SOUTH);
				}
			}
		}
		
		for(int i=5; i<this.tile.length-5; i++)
		{
			for(int j=5; j<this.tile[0].length-5; j++)
			{
				if(floorTypes.contains(this.tile[i-1][j+1].type) && this.tile[i][j+1].type == EAST
						&& this.tile[i-1][j].type == NORTH)
				{
					tile(i,j-1,NORTH);
					tile(i+1,j,EAST);
					tile(i,j,NORTHEAST);
					tile(i+1,j-1,NORTHEAST);
				}
				else if(floorTypes.contains(this.tile[i+1][j+1].type) && this.tile[i][j+1].type == WEST
						&& this.tile[i+1][j].type == NORTH)
				{
					tile(i,j-1,NORTH);
					tile(i-1,j,WEST);
					tile(i,j,NORTHWEST);
					tile(i-1,j-1,NORTHWEST);
				}
				else if(this.tile[i][j].type == NORTH && floorTypes.contains(this.tile[i+1][j].type)
						&& floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j-1,WEST);
					tile(i,j,SOUTHEAST);
					tile(i-1,j-1,SOUTHEAST);
				}
				else if(this.tile[i][j].type == NORTH && floorTypes.contains(this.tile[i-1][j].type)
						&& floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j-1,EAST);
					tile(i,j,SOUTHWEST);
					tile(i+1,j-1,SOUTHWEST);
				}
				else if(this.tile[i][j].type == SOUTH && this.tile[i+1][j-1].type == EAST)
				{
					tile(i+1,j,SOUTH);
				}
				else if(this.tile[i][j].type == SOUTH && this.tile[i-1][j-1].type == WEST)
				{
					tile(i-1,j,SOUTH);
					tile(i-2,j,SOUTH);
				}
				else if(this.tile[i][j].type == Tile.BLACK && (this.tile[i][j-1].type == EAST
						|| this.tile[i][j-1].type == WEST))
				{
					tile(i,j,SOUTH);
				}
			}
		}
	}
	
	public void drawCaveWalls(int x1, int y1, int x2, int y2, int[] floor, int wall)
	{
		ArrayList<Integer> floorTypes = new ArrayList<Integer>();
		for(int i=0; i<floor.length; i++) floorTypes.add(floor[i]);
		
		int NORTH, EAST, SOUTH, WEST, NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHWEST;
		
		//if there are other cave wall types, use the wall parameter to set these differently
		NORTH = Tile.CAVEWALLNORTH;
		EAST = Tile.CAVEWALLEAST;
		SOUTH = Tile.CAVEWALLSOUTH;
		WEST = Tile.CAVEWALLWEST;
		NORTHWEST = Tile.CAVEWALLNORTHWEST;
		NORTHEAST = Tile.CAVEWALLNORTHEAST;
		SOUTHEAST = Tile.CAVEWALLSOUTHEAST;
		SOUTHWEST = Tile.CAVEWALLSOUTHWEST;
		
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j,NORTH);
					tile(i,j-1,NORTH);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i-1][j].type))
				{
					tile(i,j,EAST);
					tile(i+1,j,EAST);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i+1][j].type))
				{
					tile(i,j,WEST);
					tile(i-1,j,WEST);
				}
				
				if(this.tile[i][j].type == Tile.BLACK && floorTypes.contains(this.tile[i][j-1].type))
				{
					tile(i,j,SOUTH);
				}
			}
		}
		
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(floorTypes.contains(this.tile[i-1][j+1].type) && this.tile[i][j+1].type == EAST
						&& this.tile[i-1][j].type == NORTH)
				{
					tile(i,j-1,NORTH);
					tile(i+1,j,EAST);
					tile(i,j,NORTHEAST);
					tile(i+1,j-1,NORTHEAST);
				}
				else if(floorTypes.contains(this.tile[i+1][j+1].type) && this.tile[i][j+1].type == WEST
						&& this.tile[i+1][j].type == NORTH)
				{
					tile(i,j-1,NORTH);
					tile(i-1,j,WEST);
					tile(i,j,NORTHWEST);
					tile(i-1,j-1,NORTHWEST);
				}
				else if(this.tile[i][j].type == NORTH && floorTypes.contains(this.tile[i+1][j].type)
						&& floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j-1,WEST);
					tile(i,j,SOUTHEAST);
					tile(i-1,j-1,SOUTHEAST);
				}
				else if(this.tile[i][j].type == NORTH && floorTypes.contains(this.tile[i-1][j].type)
						&& floorTypes.contains(this.tile[i][j+1].type))
				{
					tile(i,j-1,EAST);
					tile(i,j,SOUTHWEST);
					tile(i+1,j-1,SOUTHWEST);
				}
				else if(this.tile[i][j].type == SOUTH && this.tile[i+1][j-1].type == EAST)
				{
					tile(i+1,j,SOUTH);
				}
				else if(this.tile[i][j].type == SOUTH && this.tile[i-1][j-1].type == WEST)
				{
					tile(i-1,j,SOUTH);
					tile(i-2,j,SOUTH);
				}
				else if(this.tile[i][j].type == Tile.BLACK && (this.tile[i][j-1].type == EAST
						|| this.tile[i][j-1].type == WEST))
				{
					tile(i,j,SOUTH);
				}
			}
		}
	}
	
	public void drawPlateauWalls(int x1, int y1, int x2, int y2)
	{
		int[] plateauTileArray = {Tile.PLATEAU,Tile.PLATEAUWALLNORTH,Tile.PLATEAUWALLEAST,Tile.PLATEAUWALLSOUTH,Tile.PLATEAUWALLWEST,
				Tile.PLATEAUWALLNORTHEAST,Tile.PLATEAUWALLSOUTHEAST,Tile.PLATEAUWALLSOUTHWEST,Tile.PLATEAUWALLNORTHWEST};
		
		ArrayList<Integer> plateauTiles = new ArrayList<Integer>();
		for(int i=0; i<plateauTileArray.length; i++) plateauTiles.add(plateauTileArray[i]);
		
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(this.tile[i][j].type == Tile.PLATEAU)
				{
					if(!plateauTiles.contains(this.tile[i][j+1].type)) tile(i,j,Tile.PLATEAUWALLSOUTH);
					else if(!plateauTiles.contains(this.tile[i+1][j].type)) tile(i,j,Tile.PLATEAUWALLEAST);
					else if(!plateauTiles.contains(this.tile[i][j-1].type)) tile(i,j,Tile.PLATEAUWALLNORTH);
					else if(!plateauTiles.contains(this.tile[i-1][j].type)) tile(i,j,Tile.PLATEAUWALLWEST);
				}
			}
		}
		
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(this.tile[i][j].type == Tile.PLATEAU)
				{
					if(this.tile[i-1][j].type == Tile.PLATEAUWALLSOUTH && this.tile[i][j+1].type == Tile.PLATEAUWALLWEST) tile(i,j,Tile.PLATEAUWALLNORTHEAST);
					else if(this.tile[i+1][j].type == Tile.PLATEAUWALLSOUTH && this.tile[i][j+1].type == Tile.PLATEAUWALLEAST) tile(i,j,Tile.PLATEAUWALLNORTHWEST); 
				}
				else if(this.tile[i][j].type == Tile.PLATEAUWALLSOUTH)
				{
					if(this.tile[i][j-1].type == Tile.PLATEAUWALLWEST) tile(i,j,Tile.PLATEAUWALLSOUTHWEST);
					else if(this.tile[i][j-1].type == Tile.PLATEAUWALLEAST) tile(i,j,Tile.PLATEAUWALLSOUTHEAST); 
				}
				else if(this.tile[i][j].type == Tile.PLATEAUWALLNORTH)
				{
					if(!plateauTiles.contains(this.tile[i-1][j].type)) tile(i,j,Tile.PLATEAUWALLWEST); 
				}
			}
		}
	}
	
	public void drawPlateauWalls()
	{
		drawPlateauWalls(1,1,this.tile.length-2,this.tile[0].length-2);
	}
	
	public void drawSouthMountainWalls(int x1, int y1, int x2, int y2, int tile)
	{
		if(tile == Tile.SNOWWALLSOUTHWEST)
		{
			for(int i=x1; i<=x2; i++)
			{
				for(int j=y1; j<=y2; j++)
				{
					if(i == x1+(y2-j)) tile(i,j,Tile.SNOWWALLSOUTHWEST);
					else if(i > x1+(y2-j)) tile(i,j,Tile.SNOWWALLSOUTH);
				}
			}
		}
		else if(tile == Tile.SNOWWALLSOUTHEAST)
		{
			for(int i=x1; i<=x2; i++)
			{
				for(int j=y1; j<=y2; j++)
				{
					if(i == x2+(j-y2)) tile(i,j,Tile.SNOWWALLSOUTHEAST);
					else if(i < x2+(j-y2)) tile(i,j,Tile.SNOWWALLSOUTH);
				}
			}
		}
		else if(tile == Tile.SNOWWALLNORTHEAST2)
		{
			for(int i=x1; i<=x2; i++)
			{
				for(int j=y1; j<=y2; j++)
				{
					if(i == x1+(y2-j)) tile(i,j,Tile.SNOWWALLNORTHEAST2);
					else if(i < x1+(y2-j)) tile(i,j,Tile.SNOWWALLSOUTH);
				}
			}
		}
		else if(tile == Tile.SNOWWALLNORTHWEST2)
		{
			for(int i=x1; i<=x2; i++)
			{
				for(int j=y1; j<=y2; j++)
				{
					if(i == x1+(j-y1)) tile(i,j,Tile.SNOWWALLNORTHWEST2);
					else if(i > x1+(j-y1)) tile(i,j,Tile.SNOWWALLSOUTH);
				}
			}
		}
	}
	
	public void tile(int x, int y, int type)
	{
		this.tile[x][y] = new Tile(type);
		
		try
		{
			int treeCheck1 = this.tile[x][y-2].thing.type;
			int treeCheck2 = this.tile[x-1][y-2].thing.type;
			
			if(treeCheck1 == Thing.TREE || treeCheck2 == Thing.TREE) this.tile[x][y].walkable = false;
			else if(treeCheck1 == Thing.HAZELNUTTREE || treeCheck2 == Thing.HAZELNUTTREE) this.tile[x][y].walkable = false;
			else if(treeCheck1 == Thing.DARKTREE || treeCheck2 == Thing.DARKTREE) this.tile[x][y].walkable = false;
			else if(treeCheck1 == Thing.SNOWTREE || treeCheck2 == Thing.SNOWTREE) this.tile[x][y].walkable = false;
			else if(treeCheck1 == Thing.CHRISTMASTREE || treeCheck2 == Thing.CHRISTMASTREE) this.tile[x][y].walkable = false;
			else if(treeCheck1 == Thing.FRUITTREE || treeCheck2 == Thing.FRUITTREE) this.tile[x][y].walkable = false; 
		}
		catch(Exception e) {}
	}
	
	public Event event(int x, int y)
	{
		return this.tile[x][y].event;
	}
	
	public void addArt(int x, int y, String art, String caption)
	{
		tile[x][y].event = new Art(art, caption);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
	}
	
	public void addNPC(int x, int y, String name, String image, int dir, int state)
	{
		this.tile[x][y].event = new NPC(name, image, dir, state);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		this.tile[x][y].walkable = false;
		this.tile[x][y].hasSolidThing = true;
	}
	
	public void addSign(int x, int y)
	{
		this.tile[x][y].event = new NPC("Sign","Sign",Game.SOUTH,0);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		this.tile[x][y].walkable = false;
		this.tile[x][y].hasSolidThing = true;
	}
	
	public void addSign(int x, int y, String d1)
	{
		this.tile[x][y].event = new NPC("Sign","Sign",Game.SOUTH,0);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		this.tile[x][y].walkable = false;
		this.tile[x][y].hasSolidThing = true;
		
		this.tile[x][y].event.addText(0,d1);
	}
	
	public void addSign(int x, int y, String d1, String d2)
	{
		this.tile[x][y].event = new NPC("Sign","Sign",Game.SOUTH,0);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		this.tile[x][y].walkable = false;
		this.tile[x][y].hasSolidThing = true;
		
		this.tile[x][y].event.addText(0,d1,d2);
	}
	
	public void addSign(int x, int y, String d1, String d2, String d3)
	{
		this.tile[x][y].event = new NPC("Sign","Sign",Game.SOUTH,0);
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		this.tile[x][y].walkable = false;
		this.tile[x][y].hasSolidThing = true;
		
		this.tile[x][y].event.addText(0,d1,d2,d3);
	}
	
	public void addChest(int x, int y, Item item, int state)
	{
		this.tile[x][y].event = new Chest(item, state);
		this.tile[x][y].walkable = false;
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
		
		this.tile[x][y].hasSolidThing = true;
	}
	
	public void addSavePoint(int x, int y)
	{
		this.tile[x][y].event = new SavePoint();
		this.tile[x][y].walkable = true;
		this.tile[x][y].event.x = x;
		this.tile[x][y].event.y = y;
	}
	
	public void addEnemy(int area, ArrayList<Unit> monsterList, int rate)
	{
		wildArea.get(area).add(monsterList);
		wildRate.get(area).add(rate);
	}
	
	public void addMonsters(int x, int y, int id)
	{
		this.tile[x][y].hasMonsters = true;
		this.tile[x][y].monsterListID = id;
	}
	
	public void fillMusic(int x1, int y1, int x2, int y2, String music)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				this.tile[i][j].music = music;
			}
		}
	}
	
	public void fillWalkable(int x1, int y1, int x2, int y2, boolean walkable)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				this.tile[i][j].walkable = walkable;
			}
		}
	}
	
	public void fillMonsters(int x1, int y1, int x2, int y2, int id)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				if(!this.tile[i][j].hasMonsters)
				{
					this.tile[i][j].hasMonsters = true;
					this.tile[i][j].monsterListID = id;
				}
			}
		}
	}
	
	public void clearEnemies(int x1, int y1, int x2, int y2)
	{
		for(int i=x1; i<=x2; i++)
		{
			for(int j=y1; j<=y2; j++)
			{
				this.tile[i][j].monsterListID = 0;
				this.tile[i][j].hasMonsters = false;
			}
		}
	}
	
	public void addInnkeeper(int x, int y, String name, String imageName, int dir, int xOffset, int yOffset, int innPrice)
	{
		this.tile[x][y].event = new Innkeeper(name, imageName, dir, xOffset, yOffset, innPrice);
	}
	
	public void addShop(int x, int y, int x2, int y2, Item[] inventory)
	{
		thing(x,y,Thing.BASICHOUSE);
		
		fillRect(x2-1,y2,x2+11,y2+11,Tile.WOODFLOOR);
		fillRect(x2,y2+1,x2+10,y2+1,Tile.DARKSTONEWALL);
		fillRect(x2-1,y2+12,x2+11,y2+12,Tile.DARKSTONEWALL);
		fillRect(x2,y2+2,x2+10,y2+10,Tile.WOODFLOOR);
		fillRect(x2+5,y2+11,x2+5,y2+12,Tile.WOODFLOOR);
		
		thing(x2+5,y2+1,Thing.BOOKCASE);
		thing(x2+7,y2+1,Thing.BOOKCASE);
		thing(x2+9,y2+1,Thing.BOOKCASE);
		
		addNPC(x2+2,y2+3,"Shopkeeper","Shopkeeper",Game.SOUTH,0);
		this.tile[x2+2][y2+4].event = new Shop(inventory);
		this.tile[x2+2][y2+4].walkable = false;
		
		tile(x2+5,y2+13,Tile.BLACKWALKABLE);
		thing(x2+5,y2+12,Thing.HOUSEEXIT);
		
		for(int i=x2; i<=x2+10; i++)
		{
			for(int j=y2; j<=y2+15; j++)
			{
				this.tile[i][j].music = "Shop";
			}
		}
		
		addPortal(x+2,y+3,id,x2+5,y2+12);
		addPortal(x2+5,y2+13,id,x+2,y+4);
	}
	
	public void addPortal(int x, int y, int dest, int x2, int y2)
	{
		this.tile[x][y].event = new Portal(dest, x2, y2);
	}
	
	public void addPortal(int x, int y, int dest, int x2, int y2, int dir)
	{
		this.tile[x][y].event = new Portal(dest, x2, y2, dir);
	}
	
	public void thing(int x, int y, int type)
	{
		this.tile[x][y].thing = new Thing(type);
		
		for(int i=0; i<this.tile[x][y].thing.width; i++)
		{
			for(int j=0; j<this.tile[x][y].thing.height; j++)
			{
					this.tile[x+i][y+j].walkable = false;
					if(type != Thing.SHORENORTH && type != Thing.SHOREEAST && type != Thing.SHORESOUTH && type != Thing.SHOREWEST
							&& type != Thing.SHORENORTHEAST && type != Thing.SHORESOUTHEAST && type != Thing.SHORESOUTHWEST && type != Thing.SHORENORTHWEST
							&& type != Thing.DOCKNORTH && type != Thing.DOCKEAST && type != Thing.DOCKSOUTH && type != Thing.DOCKWEST
							&& type != Thing.DOCKNORTHEAST && type != Thing.DOCKSOUTHEAST && type != Thing.DOCKSOUTHWEST && type != Thing.DOCKNORTHWEST
							&& type != Thing.SHORENORTHWEST2 && type != Thing.SHORENORTHEAST2 && type != Thing.SHORESOUTHEAST2 && type != Thing.SHORESOUTHWEST2
							&& type != Thing.DOCKNORTHWEST2 && type != Thing.DOCKNORTHEAST2 && type != Thing.DOCKSOUTHEAST2 && type != Thing.DOCKSOUTHWEST2) this.tile[x+i][y+j].hasSolidThing = true;
			}
		}
		
		if(type == Thing.NOTHING)
		{
			this.tile[x][y].walkable = true;
		}
		else if(type == Thing.TREE || type == Thing.DARKTREE || type == Thing.SNOWTREE || type == Thing.HAZELNUTTREE || type == Thing.CHRISTMASTREE || type == Thing.FRUITTREE)
		{
			this.tile[x][y+2].walkable = false;
			this.tile[x+1][y+2].walkable = false;
		}
		else if(type == Thing.BASICHOUSE || type == Thing.SNOWHOUSE)
		{
			this.tile[x+2][y+3].walkable = true;
			this.tile[x+2][y+3].sound = "doorIn";
		}
		else if(type == Thing.FLOWER)
		{
			this.tile[x][y].walkable = true;
		}
		else if(type == Thing.HORIZONTALBRIDGE)
		{
			//kill shores
			this.tile[x][y+1].thing = new NoThing();
			this.tile[x][y+2].thing = new NoThing();
			this.tile[x+4][y].thing = new NoThing();
			this.tile[x+4][y+1].thing = new NoThing();
			this.tile[x+4][y+2].thing = new NoThing();
			
			fillRect(x,y,x+4,y+2,Tile.WOODFLOOR);
			
			this.tile[x][y].thing = new Thing(type);
		}
		else if(type == Thing.VERTICALBRIDGE)
		{
			//kill shores
			this.tile[x+1][y].thing = new NoThing();
			this.tile[x+2][y].thing = new NoThing();
			this.tile[x][y+4].thing = new NoThing();
			this.tile[x+1][y+4].thing = new NoThing();
			this.tile[x+2][y+4].thing = new NoThing();
			
			fillRect(x,y,x+2,y+4,Tile.WOODFLOOR);
			
			this.tile[x][y].thing = new Thing(type);
		}
		else if(type == Thing.LADDERUP || type == Thing.LADDERDOWN)
		{
			this.tile[x][y].walkable = true;
			this.tile[x][y].sound = "doorOut";
		}
		else if(type == Thing.HOUSEEXIT)
		{
			this.tile[x][y+1].sound = "doorOut";
		}
		else if(type == Thing.DOOR)
		{
			this.tile[x][y].walkable = true;
			this.tile[x][y].sound = "doorIn";
		}
		else if(type == Thing.STAIRSUPNORTH || type == Thing.STAIRSUPSOUTH || type == Thing.STAIRSDOWNNORTH || type == Thing.STAIRSDOWNSOUTH)
		{
			this.tile[x][y].walkable = true;
			this.tile[x][y].sound = "doorOut";
		}
		else if(type == Thing.CAVEENTRANCE)
		{
			this.tile[x][y].walkable = true;
			this.tile[x][y].sound = "doorOut";
		}
		else if(type == Thing.MOUNTAINCAVEENTRANCE)
		{
			this.tile[x][y+1].walkable = true;
			this.tile[x][y+1].sound = "doorOut";
		}
		else if(type == Thing.MOUNTAINCAVEENTRANCE2)
		{
			this.tile[x][y].walkable = true;
			this.tile[x][y].sound = "doorOut";
		}
	}
	
	public void addObservation(int x, int y, String a)
	{
		if(this.event(x,y).type == Event.NOEVENT) this.addNPC(x,y,"","",Game.SOUTH,0);
		event(x,y).addText(0,a);
	}
	
	public void addObservation(int x, int y, String a, String b)
	{
		if(this.event(x,y).type == Event.NOEVENT) this.addNPC(x,y,"","",Game.SOUTH,0);
		event(x,y).addText(0,a,b);
	}
	
	public void addObservation(int x, int y, String a, String b, String c)
	{
		if(this.event(x,y).type == Event.NOEVENT) this.addNPC(x,y,"","",Game.SOUTH,0);
		event(x,y).addText(0,a,b,c);
	}
	
	public void addObservation(int x, int y, String name, String a, String b, String c)
	{
		if(this.event(x,y).type == Event.NOEVENT) this.addNPC(x,y,name,"",Game.SOUTH,0);
		event(x,y).addText(0,a,b,c);
	}
}

class TestTown extends Map
{
	public static int num = 1;
	
	public TestTown(int[] states)
	{		
		this.id = 0;
		this.name = "Test Town";
		this.song = "Ducks Ahoy";
		this.tile = new Tile[100][70];
		this.states = states;
		
		int width = this.tile.length - 1;
		int height = this.tile[0].length - 1;
		
		fillRect(0,0,width,height,Tile.BLACK);
		fillRect(0,0,50,50,Tile.GRASS);
		fillThingRect(0,0,49,49,Thing.TREE);
		fillRect(10,10,41,39,Tile.GRASS);
		fillRect(20,20,30,30,Tile.STONEFLOOR);
		
		fillRect(11,11,15,15,Tile.DARKGRASS);
		
		fillWater(25,25,28,27);
		
		thing(10,10,Thing.FENCENORTHWEST);
		fillThingRect(11,10,40,10,Thing.HORIZONTALFENCE);
		thing(41,10,Thing.FENCENORTHEAST);
		fillThingRect(41,11,41,38,Thing.VERTICALFENCE);
		thing(41,39,Thing.FENCESOUTHEAST);
		fillThingRect(11,39,40,39,Thing.HORIZONTALFENCE);
		thing(10,39,Thing.FENCESOUTHWEST);
		fillThingRect(10,11,10,38,Thing.VERTICALFENCE);
		
		thing(22,22,Thing.TORCH);
		thing(29,29,Thing.TORCH);
		
		thing(13,20,Thing.TREE);
		thing(15,24,Thing.DARKTREE);
		thing(21,15,Thing.FLOWER);
		thing(22,13,Thing.FLOWER);
		thing(29,17,Thing.FLOWER);
		thing(30,13,Thing.FLOWER);
		thing(39,20,Thing.MUSHROOM);
		thing(35,21,Thing.MUSHROOM);
		thing(32,16,Thing.STUMP);
		
		fillRect(11,32,22,38,Tile.DUST);
		fillRect(13,33,20,36,Tile.PLATEAU);
		drawPlateauWalls(13,33,20,36);
		
		fillRect(25,34,27,36,Tile.LAVA);
		drawShore(25,34,27,36);
		
		thing(24,35,Thing.COFFIN);
		thing(28,35,Thing.TOMBSTONE);
		
		fillRect(34,24,40,31,Tile.SNOW);
		fillRect(30,32,40,38,Tile.SNOW);
		fillRect(35,34,39,36,Tile.ICE);
		
		thing(38,26,Thing.CHRISTMASTREE);
		thing(31,33,Thing.CHRISTMASTREE);
		thing(38,30,Thing.SNOWTREE);
		thing(31,37,Thing.ICEBERG);
		thing(32,36,Thing.BIGICEBERG);
		
		fillRect(35,13,38,16,Tile.CITYFLOOR);
		fillRect(36,14,37,15,Tile.PURPLESTONEFLOOR);
		
		addNPC(16,14,"Insane Old Lady",NPC.OLDLADY,Game.SOUTH,0);
		event(16,14).addText(0,"Watch out! It appears that I've","dropped my collection of snakes","in this dark grassy area.");
		
		addNPC(35,14,"Mega Ghost",NPC.GHOST,Game.SOUTH,0);
		event(35,14).addText(0,"Boo! Remember me?","I was in BrianQuest too!");
		
		addNPC(38,16,"Game Guy",NPC.LOSER,Game.SOUTH,0);
		event(38,16).addText(0,"Don't touch my video games!!!");
		
		addNPC(36,27,"Saint Nick",NPC.OLDMAN,Game.SOUTH,0);
		event(36,27).addText(0,"There's no music in this game yet!","Enjoy the silence while you can.");
		
		addNPC(37,33,"Silly Clown",NPC.CLOWN,Game.SOUTH,0);
		event(37,33).addText(0,"Sliding around on the ice?","Don't clown around!");
		
		addNPC(29,36,"Largeman",NPC.FATMAN,Game.WEST,0);
		event(29,36).addText(0,"People fall into this pit of lava","all the time. It's horrible!");
		
		addNPC(17,32,"Guy",NPC.MAN,Game.NORTH,0);
		event(17,32).addText(0,"Greetings.");
		
		addNPC(22,26,"Scientist",NPC.SCIENTIST,Game.SOUTH,0);
		event(22,26).addText(0,"Welcome to the BrianQuest 2 closed","alpha prototype. There are many","amazing things for you to explore.");
		event(22,26).addText(0,"In the alpha version, there are","some cheats available for your","convenience.");
		event(22,26).addText(0,"For example, press 'P' to add all","the party members who currently","exist. Space -> Edit Party.");
		event(22,26).addText(0,"You can also press 'O' to add all","of the existing skills to your","party members.");
		event(22,26).addText(0,"However, those skills will only","be temporary until the party member","has their stats/skills recalculated.");
		event(22,26).addText(0,"Many skills don't work yet, and","many things will be broken.","Forgive me.");
		event(22,26).addText(0,"Press 'I' to give yourself 1000","money, if you want.");
		event(22,26).addText(0,"Enjoy your magical adventure!");
		
		addNPC(23,26,"Pet Pig",NPC.PIG,Game.SOUTH,0);
		event(23,26).addText(0,"Oink.");
		
		addNPC(12,25,"Nudist",NPC.NUDIST,Game.EAST,0);
		event(12,25).addText(0,"This is my treasure.","Please don't steal it!");
		
		addNPC(31,13,"Snackman",NPC.STONER,Game.SOUTH,0);
		event(31,13).addText(0,"Don't mind me. Just hangin' around.");
		
		addNPC(28,24,"Lil' Man",NPC.DWARF,Game.SOUTH,0);
		event(28,24).addText(0,"That 'S' on the ground over there","is a save point. The 'S' stands","for Save.");
		
		addChest(13,25,new StickOfEnlightenment(1),states[0]);
		
		addSavePoint(24,23);
		
		/**
		 * House
		 */
		thing(24,12,Thing.BASICHOUSE);
		insideHouse(60,10,70,20);
		addPortal(26,15,this.id,65,22);
		addPortal(65,23,this.id,26,16);
		
		fillRect(67,15,70,15,Tile.WOODWALL);
		fillRect(67,16,70,16,Tile.DARKWOODWALL);
		
		thing(68,9,Thing.WINDOW);
		thing(60,9,Thing.BOOKCASE);
		thing(60,10,Thing.BOOKCASE);
		thing(65,9,Thing.BOOKCASE);
		thing(65,10,Thing.BOOKCASE);
		thing(70,10,Thing.BED);
		thing(61,14,Thing.SQUARETABLE);
		thing(63,14,Thing.WOODENCHAIR);
		thing(61,16,Thing.WOODENCHAIR);
		thing(60,20,Thing.BOX);
		thing(70,20,Thing.POTTEDPLANT);
		thing(62,11,Thing.LONGTABLE);
		thing(67,11,Thing.LONGTABLE);
		
		addArt(67,16,"Brian1","A primitive cave painting.");
		addArt(69,16,"AlexFursona","A strange and mysterious creature.");
		addArt(70,16,"Frogsona","A glorious frog of legend.");
		
		addNPC(63,15,"Town Beauty",NPC.BEAUTY,Game.WEST,0);
		event(63,15).addText(0,"Welcome to my lovely house.","Please stop intruding.");
		
		addNPC(68,17,"Fedora Man",NPC.FEDORAMAN,Game.SOUTH,0);
		event(68,17).addText(0,"I tip my fedora to you, m'sir.");
		
		addNPC(66,12,"Jokester",NPC.CLOWN,Game.SOUTH,0);
		event(66,12).addText(0,"Shops and inns will obviously not","look like this in the real game!");
		
		Item[] inventory = {new Potion(1), new Ether(1), new MountainDew(1), new BadSword(1), new RustySword(1), new Stick(1), new Ukulele(1), new MtnDewHat(1), new BikeHelmet(1), new TornShirt(1),
				new PlasticArmor(1), new OldSocks(1), new NiceBoots(1), new RingPop(1), new SpicyHotDoritos(1)};
		addNPC(63,10,"Shopkeeper","Shopkeeper",Game.SOUTH,0);
		this.tile[63][11].event = new Shop(inventory);
		this.tile[63][11].walkable = false;
		
		addInnkeeper(68,11,"Innkeeper",NPC.MAN,Game.SOUTH,0,-1,30);
		
		fillMusic(60,10,70,23,"Soiled Soil");
		
		this.makeEnemyList();
		this.recalculateStates();
	}
	
	public void makeEnemyList()
	{
		this.wildArea = new ArrayList<ArrayList<ArrayList<Unit>>>();
		this.wildRate = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Unit>> monster0 = new ArrayList<ArrayList<Unit>>();
		addEnemyList(monster0, new Snake(), new Snake());
		addEnemyList(monster0, new Snake(), new Snake(), new Snake());
		addEnemyList(monster0, new Snake(), new Snake(), new Snake(), new Snake());
		
		ArrayList<Integer> rate0 = new ArrayList<Integer>();
		rate0.add(40);
		rate0.add(30);
		rate0.add(30);
		
		this.wildArea.add(monster0);
		this.wildRate.add(rate0);
		
		fillMonsters(11,11,15,15,0);
	}
	
	public void recalculateStates()
	{
		this.states[0] = event(13,25).state;
	}
}

class Tile
{
	public int type;
	public boolean walkable;
	public boolean hasMonsters;
	public String sound; //play sound for portals (up ladder, stairs, etc.)
	public String music;
	public int monsterListID;
	public boolean hasSolidThing;
	
	public Event event;
	public Thing thing;
	
	/**
	 * Tile types
	 */
	public static final int GRASS = 0;
	public static final int WATER = 1;
	public static final int CITYFLOOR = 2;
	public static final int BLACK = 3;
	public static final int STONEFLOOR = 4;
	public static final int STONEWALL = 5;
	public static final int BLACKWALKABLE = 6;
	public static final int WOODFLOOR = 7;
	public static final int WOODWALL = 8;
	public static final int LIGHTGRASS = 9;
	public static final int STREET = 10;
	public static final int DARKGRASS = 11;
	public static final int SAND = 12;
	public static final int CAVEFLOOR = 13;
	public static final int DIRT = 14;
	public static final int CAVEWALLNORTH = 15;
	public static final int CAVEWALLEAST = 16;
	public static final int CAVEWALLSOUTH = 17;
	public static final int CAVEWALLWEST = 18;
	public static final int CAVEWALLNORTHEAST = 19;
	public static final int CAVEWALLSOUTHEAST = 20;
	public static final int CAVEWALLSOUTHWEST = 21;
	public static final int CAVEWALLNORTHWEST = 22;
	public static final int BRICKFLOOR = 23;
	public static final int SNOWGRASS = 24;
	public static final int ICE = 25;
	public static final int STEEL = 26;
	public static final int DARKSTONEFLOOR = 27;
	public static final int DARKSTONEWALL = 28;
	public static final int LAVA = 29;
	public static final int SHADOWGRASS = 30;
	public static final int DARKWOODFLOOR = 31;
	public static final int DARKWOODWALL = 32;
	public static final int DARKDIRT = 33;
	public static final int GRAY = 34;
	public static final int PURPLESTONEFLOOR = 35;
	public static final int SNOW = 36;
	public static final int PLATEAUWALLSOUTH = 37;
	public static final int PLATEAUWALLEAST = 38;
	public static final int PLATEAUWALLNORTH = 39;
	public static final int PLATEAUWALLWEST = 40;
	public static final int PLATEAUWALLNORTHEAST = 41;
	public static final int PLATEAUWALLSOUTHEAST = 42;
	public static final int PLATEAUWALLSOUTHWEST = 43;
	public static final int PLATEAUWALLNORTHWEST = 44;
	public static final int PLATEAU = 45;
	public static final int DUST = 46;
	public static final int DUSTTRAIL = 47;
	public static final int SNOWWALLSOUTH = 48;
	public static final int SNOWWALLEAST = 49;
	public static final int SNOWWALLNORTH = 50;
	public static final int SNOWWALLWEST = 51;
	public static final int SNOWWALLNORTHEAST = 52;
	public static final int SNOWWALLSOUTHEAST = 53;
	public static final int SNOWWALLSOUTHWEST = 54;
	public static final int SNOWWALLNORTHWEST = 55;
	public static final int SNOWWALLNORTHEAST2 = 56;
	public static final int SNOWWALLNORTHWEST2 = 57;
	
	public static final int NUMTILES = 58; //starts at 0, so add 1
	
	public Tile(int type)
	{
		this.type = type;
		this.walkable = shouldBeWalkable();
		
		this.event = new NoEvent();
		this.thing = new NoThing();
	}
	
	public boolean shouldBeWalkable()
	{
		if(this.type == GRASS || this.type == CITYFLOOR || this.type == STONEFLOOR || this.type == BLACKWALKABLE || this.type == WOODFLOOR
				|| this.type == LIGHTGRASS || this.type == STREET || this.type == DARKGRASS || this.type == SAND || this.type == CAVEFLOOR
				|| this.type == DIRT || this.type == BRICKFLOOR || this.type == SNOWGRASS || this.type == ICE || this.type == DARKSTONEFLOOR
				|| this.type == SHADOWGRASS || this.type == DARKWOODFLOOR || this.type == DARKDIRT || this.type == GRAY
				|| this.type == SNOW || this.type == DUST || this.type == DUSTTRAIL || this.type == PURPLESTONEFLOOR) return true;
		else return false;
	}
	
	public boolean isWater()
	{
		//if other water types, check here
		if(this.type == WATER) return true;
		else return false;
	}
	
	public void openChest()
	{
		this.event.state = 1;
	}

	public static ArrayList<Integer> movingTileList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(WATER);
		list.add(LAVA);
		
		return list;
	}
}

class Event
{
	public int id;
	public int x;
	public int y;
	public int dir;
	public int state;
	public int xOffset; //draw the actual event on a different tile (e.g. shops and inns)
	public int yOffset; //draw the actual event on a different tile (e.g. shops and inns)
	
	public String name;
	public String imageName;
	
	public int type;
	/**
	 * Event types
	 */
	public final static int NOEVENT = 0;
	public final static int NPC = 1;
	public final static int SHOP = 2;
	public final static int INNKEEPER = 3;
	public final static int PORTAL = 4;
	public final static int CHEST = 5;
	public final static int ART = 6;
	public final static int SAVEPOINT = 7;
	
	//for chests
	public Item item;
	
	//for shops
	public Item[] inventory;
	
	//for portals
	public int destMap, destX, destY;
	
	//for inns
	public int innPrice;
	
	//for NPCs
	public ArrayList<ArrayList<String[]>> dialogue;
	String[] toAdd;
	
	//for art
	public String caption;
	
	public Event()
	{
		
	}

	public void addText(int state, String[] a)
	{
		String[] toAdd = new String[3];
		
		if(a.length == 1)
		{
			toAdd[0] = a[0];
			toAdd[1] = "";
			toAdd[2] = "";
		}
		else if(a.length == 2)
		{
			toAdd[0] = a[0];
			toAdd[1] = a[1];
			toAdd[2] = "";
		}
		else
		{
			toAdd[0] = a[0];
			toAdd[1] = a[1];
			toAdd[2] = a[2];
		}
		
		while(this.dialogue.size() <= state)
		{
			this.dialogue.add(new ArrayList<String[]>());
		}
		
		this.dialogue.get(state).add(toAdd);
	}
	
	public void addText(int state, String a)
	{
		String[] toAdd = new String[3];
		toAdd[0] = a;
		toAdd[1] = "";
		toAdd[2] = "";
		
		while(this.dialogue.size() <= state)
		{
			this.dialogue.add(new ArrayList<String[]>());
		}
		
		this.dialogue.get(state).add(toAdd);
	}
	
	public void addText(int state, String a, String b)
	{
		String[] toAdd = new String[3];
		toAdd[0] = a;
		toAdd[1] = b;
		toAdd[2] = "";
		
		while(this.dialogue.size() <= state)
		{
			this.dialogue.add(new ArrayList<String[]>());
		}
		
		this.dialogue.get(state).add(toAdd);
	}
	
	public void addText(int state, String a, String b, String c)
	{
		String[] toAdd = new String[3];
		toAdd[0] = a;
		toAdd[1] = b;
		toAdd[2] = c;
		
		while(this.dialogue.size() <= state)
		{
			this.dialogue.add(new ArrayList<String[]>());
		}
		
		this.dialogue.get(state).add(toAdd);
	}
	
	public void addDuplicateDialogue(int duplicateState)
	{
		this.dialogue.add(this.dialogue.get(duplicateState));
	}
	
	public ArrayList<String[]> getDialogue(int state)
	{
		try
		{
			return this.dialogue.get(state);
		}
		catch(Exception e)
		{
			ArrayList<String[]> temp = new ArrayList<String[]>();
			String[] placeholder = {"???","",""};
			temp.add(0,placeholder);
			
			return temp;
		}
	}
}

class NPC extends Event
{
	public int npcType;
	/**
	 * NPC types
	 */
	public static final String BEAUTY = "Beauty";
	public static final String CLOWN = "Clown";
	public static final String CREEPYMAN = "Creepy Man";
	public static final String FATMAN = "Fat Man";
	public static final String FEDORAMAN = "Fedora Man";
	public static final String GHOST = "Ghost";
	public static final String LOSER = "Loser";
	public static final String MAN = "Man";
	public static final String DWARF = "Dwarf";
	public static final String NUDIST = "Nudist";
	public static final String OLDLADY = "Old Lady";
	public static final String OLDMAN = "Old Man";
	public static final String PIG = "Pig";
	public static final String SCIENTIST = "Scientist";
	public static final String STONER = "Stoner";

	public NPC(String name, String image, int dir, int state)
	{
		this.type = NPC;
		this.name = name;
		this.imageName = image;
		this.dir = dir;
		this.state = state;
		this.id = -1;
		
		this.dialogue = new ArrayList<ArrayList<String[]>>();
	}
}

class Shop extends Event
{
	public Shop(Item[] inventory)
	{
		this.type = SHOP;
		this.inventory = inventory;
		this.imageName = "";
	}
}

class Innkeeper extends Event
{
	public Innkeeper(String name, String imageName, int dir, int xOffset, int yOffset, int innPrice)
	{
		this.type = INNKEEPER;
		this.name = name;
		this.imageName = imageName;
		this.dir = dir;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.innPrice = innPrice;
	}
}

class Chest extends Event
{
	public Chest(Item item, int state)
	{
		this.type = CHEST;
		this.name = "Chest";
		this.state = state;
		this.item = item;
		
		this.name = "Chest";
		
		this.dialogue = new ArrayList<ArrayList<String[]>>();
		
		String[] toAdd = new String[3];
		
		String article;
		if(item.name.charAt(0) == 'A' || item.name.charAt(0) == 'E' || item.name.charAt(0) == 'I' || item.name.charAt(0) == 'O' || item.name.charAt(0) == 'U')
			article = "an";
		else article = "a";
		
		toAdd[0] = "Found " + article + " " + item.name + "!";
		toAdd[1] = "";
		toAdd[2] = "";
		
		this.addText(this.state,toAdd);
	}
	
	public void open()
	{
		this.state = 1;
	}
}

class Portal extends Event
{
	public int escapeMap;
	public int escapeX;
	public int escapeY;
	
	public Portal(int destMap, int destX, int destY)
	{				
		this.type = PORTAL;
		
		this.destMap = destMap;
		this.destX = destX;
		this.destY = destY;
		
		this.name = "Portal";
		
		this.dir = -1; //no dir
	}
	
	public Portal(int destMap, int destX, int destY, int dir)
	{				
		this.type = PORTAL;
		
		this.destMap = destMap;
		this.destX = destX;
		this.destY = destY;
		
		this.name = "Portal";
		
		this.dir = dir;
	}
}

class Art extends Event
{
	public Art(String art, String caption)
	{
		this.type = ART;
		
		this.name = art;
		this.caption = caption;
	}
}

class SavePoint extends Event
{
	public SavePoint()
	{
		this.type = SAVEPOINT;
	}
}

class Thing
{
	public int type;
	/**
	 * Thing types
	 */
	public static final int NOTHING = 0;
	public static final int POTTEDPLANT = 1;
	public static final int BOOKCASE = 2;
	public static final int TABLE = 3;
	public static final int LONGTABLE = 4;
	public static final int WINDOW = 5;
	public static final int BOX = 6;
	public static final int HOUSEEXIT = 7;
	public static final int BASICHOUSE = 8;
	public static final int WOODENCHAIR = 9;
	public static final int SQUARETABLE = 10;
	public static final int TREE = 11;
	public static final int SHRUB = 12;
	public static final int SHORENORTH = 13;
	public static final int SHOREEAST = 14;
	public static final int SHORESOUTH = 15;
	public static final int SHOREWEST = 16;
	public static final int SHORENORTHEAST = 17;
	public static final int SHORESOUTHEAST = 18;
	public static final int SHORESOUTHWEST = 19;
	public static final int SHORENORTHWEST = 20;
	public static final int BIGSQUARETABLE = 21;
	public static final int EMPTYHOUSE = 22;
	public static final int DARKTREE = 23;
	public static final int BIGROCK = 24;
	public static final int ROCK = 25;
	public static final int LADDERDOWN = 26;
	public static final int LADDERUP = 27;
	public static final int HORIZONTALFENCE = 28;
	public static final int STONEBLOCK = 29;
	public static final int VERTICALBRIDGE = 30;
	public static final int HORIZONTALBRIDGE = 31;
	public static final int FLOWER = 32;
	public static final int SNOWTREE = 33;
	public static final int DOOR = 34;
	public static final int BED = 35;
	public static final int FIREPLACE = 36;
	public static final int TORCH = 37;
	public static final int INN = 38;
	public static final int STAIRSUPNORTH = 39;
	public static final int STAIRSDOWNNORTH = 40;
	public static final int STAIRSUPSOUTH = 41;
	public static final int STAIRSDOWNSOUTH = 42;
	public static final int MUSHROOM = 43;
	public static final int SNOWHOUSE = 44;
	public static final int ICEBERG = 45;
	public static final int BIGICEBERG = 46;
	public static final int COFFIN = 47;
	public static final int TOMBSTONE = 48;
	public static final int DOCKNORTH = 49;
	public static final int DOCKEAST = 50;
	public static final int DOCKSOUTH = 51;
	public static final int DOCKWEST = 52;
	public static final int DOCKNORTHEAST = 53;
	public static final int DOCKSOUTHEAST = 54;
	public static final int DOCKSOUTHWEST = 55;
	public static final int DOCKNORTHWEST = 56;
	public static final int SHORENORTHWEST2 = 57;
	public static final int SHORENORTHEAST2 = 58;
	public static final int SHORESOUTHEAST2 = 59;
	public static final int SHORESOUTHWEST2 = 60;
	public static final int DOCKNORTHWEST2 = 61;
	public static final int DOCKNORTHEAST2 = 62;
	public static final int DOCKSOUTHEAST2 = 63;
	public static final int DOCKSOUTHWEST2 = 64;
	public static final int HAZELNUTTREE = 65;
	public static final int STONETABLE = 66;
	public static final int LONGSTONETABLE = 67;
	public static final int STUMP = 68;
	public static final int EMPTYSNOWHOUSE = 69;
	public static final int CHRISTMASTREE = 70;
	public static final int CAVEENTRANCE = 71;
	public static final int FENCENORTHWEST = 72;
	public static final int FENCENORTHEAST = 73;
	public static final int FENCESOUTHEAST = 74;
	public static final int FENCESOUTHWEST = 75;
	public static final int FENCENORTHCONNECT = 76;
	public static final int FENCEEASTCONNECT = 77;
	public static final int FENCESOUTHCONNECT = 78;
	public static final int FENCEWESTCONNECT = 79;
	public static final int MOUNTAINCAVEENTRANCE = 80;
	public static final int MOUNTAINCAVEENTRANCE2 = 81;
	public static final int FRUITTREE = 82;
	public static final int VERTICALFENCE = 83;
	
	public static final int NUMTHINGS = 83; //starts at 1, so don't add 1
	
	public int height;
	public int width;
	
	public boolean priority;
	
	public Thing()
	{
		
	}
	
	public Thing(int type)
	{
		this.type = type;
		
		/**
		 * Set width/heights
		 */
		if(this.type == NOTHING || this.type == HOUSEEXIT || this.type == LADDERUP || this.type == LADDERDOWN || this.type == SHORENORTH
				|| this.type == SHOREEAST || this.type == SHORESOUTH || this.type == SHOREWEST || this.type == SHORENORTHEAST
				|| this.type == SHORENORTHWEST || this.type == DOCKSOUTHEAST || this.type == DOCKSOUTHWEST || this.type == DOCKNORTH
				|| this.type == DOCKEAST || this.type == DOCKSOUTH || this.type == DOCKWEST || this.type == DOCKNORTHEAST || this.type == DOCKNORTHWEST
				|| this.type == DOCKSOUTHEAST || this.type == DOCKSOUTHWEST || this.type == Thing.SHORENORTHWEST2 || this.type == Thing.SHORENORTHEAST2
				|| this.type == Thing.SHORESOUTHEAST2 || this.type == Thing.SHORESOUTHWEST2 || this.type == Thing.DOCKNORTHWEST2 || this.type == Thing.DOCKNORTHEAST2
				|| this.type == Thing.DOCKSOUTHEAST2 || this.type == Thing.DOCKSOUTHWEST2)
		{
			this.height = 0;
			this.width = 0;
		}
		else if(this.type == TABLE || this.type == STONETABLE)
		{
			this.height = 1;
			this.width = 2;
		}
		else if(this.type == LONGTABLE || this.type == LONGSTONETABLE)
		{
			this.height = 1;
			this.width = 3;
		}
		else if(this.type == BED || this.type == COFFIN || this.type == TOMBSTONE)
		{
			this.height = 2;
			this.width = 1;
		}
		else if(this.type == SQUARETABLE || this.type == TREE || this.type == BOOKCASE || this.type == DARKTREE || this.type == BIGROCK
				|| this.type == SNOWTREE|| this.type == BIGICEBERG || this.type == HAZELNUTTREE|| this.type == CHRISTMASTREE || this.type == FRUITTREE)
		{
			this.height = 2;
			this.width = 2;
		}
		else if(this.type == BIGSQUARETABLE)
		{
			this.height = 3;
			this.width = 3;
		}
		else if(this.type == HORIZONTALBRIDGE)
		{
			this.height = 3;
			this.width = 5;
		}
		else if(this.type == BASICHOUSE || this.type == EMPTYHOUSE || this.type == SNOWHOUSE || this.type == EMPTYSNOWHOUSE)
		{
			this.height = 4;
			this.width = 5;
		}
		else if(this.type == VERTICALBRIDGE)
		{
			this.height = 5;
			this.width = 3;
		}
		else
		{
			this.height = 1;
			this.width = 1;
		}
		
		if(this.type == HORIZONTALBRIDGE)
		{
			this.priority = true;
		}
		else this.priority = false;
	}
	
	public static ArrayList<Integer> movingThingList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(FLOWER);
		list.add(TORCH);
		list.add(CHRISTMASTREE);
		
		return list;
	}
}

class NoThing extends Thing
{
	public NoThing()
	{
		this.type = NOTHING;
	}
}

class NoEvent extends Event
{
	public NoEvent()
	{
		this.type = NOEVENT;
		this.name = "";
		this.state = -1;
	}
	
	public NoEvent(int state)
	{
		this.type = NOEVENT;
		this.name = "";
		this.state = state;
	}
}
