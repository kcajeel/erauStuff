//***********************************************
// Class: GameController
// Author: Keith Garfield, modified by Jack Lee
// Created: October 16, 2022
// Modified: Nov 21, 2022 -- Modified to add different tiles and enter/exit/special actions
//
// Attributes: -maze: GenericTile[][] - An n-by-n array of tiles composing the maze. Order is [col][row].
//             -agent: GenericAgent - The agent moving through the maze.
//             -agentLocation: int[2] - The [col][row] position of the agent in the maze.
//             -turnCounter: int - Tracks move attempts. Increments for every attempt regardless of succcess. 
//
// Game Play Methods: 
//          +playGame(): Moves that agent until agent wins or loses.
//          +agentIsInGoal(): boolean - Returns TRUE if the agent is in the goal tile, FALSE otherwise.
//          +hasAgentWon(): boolean - Returns TRUE if the agent is in the goal tile, FALSE otherwise.
//          +hasAgentLost(): boolean - Returns TRUE if turn counter has reached limit, FALSE otherwise. 
//
// Movement Methods:
//          +moveAgent(): void - Executes one game move. Updates agentLocation and increments turnCounter.
//          +isMoveLegal(): boolean - Returns TRUE if agent's requested move results in a change location, FALSE otherwise.
//          +isDoorLocked(GenericTile, int): boolean - Returns TRUE if door in current tile is locked, FALSE otherwise.
//                                                     Inputs are current tile location and direction (specified as an int).
//
// Game Creation Methods:
//          +createMaze(int, int, String): void - Creates an m-by-n array of tiles with doors randomly locked/unlocked.
//                                        Inputs are number of columns, number of rows.
//          +createMaze(int, int, boolean): void - Creates an m-by-n array of tiles, all doors locked or unlocked.
//                                        Inputs are number of columnes, number of rows, locked status (TRUE or FALSE).
//
// Printing Game State Methods:
//          +printMaze(): void - Prints the door status of every tile in the maze.
//          +printMazeSummary(): void - Prints the maze size, identifies Start and Goal tiles.
//          +convertExitsToString(): String - Utility method to compose a string from status of tile doors.
//          +printAgentLocation(): void - Prints [col][row] position of the agent. 
//
// Specialized Setters/Getters:
//          +setAgent(Agent): void - Creates the agent of specified type. Input is enumerated type Agent.
//
//File Reading Methods: 
//				+readMazeFromFile(String):void -creates a maze from a given file
//				+createTileFromFile(String[]):void -creates a tile given an array of data
//***********************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class GameController {
	private GenericTile[][] maze;
	private GenericAgent agent;
	private int[] agentLocation = new int[2];
	private int turnCounter = 0;
	
	// ***** Game Play Methods *****
	public void playGame() {
		while (!hasAgentLost() && !hasAgentWon() ) {
			int rand = (int)(Math.random()*100)+1;
			moveAgent();
			turnCounter++;
			if(rand == 79){
				maze[agentLocation[1]][agentLocation[0]].specialAction();
			}
		}
		
		if ( hasAgentLost() ) {
			printAgentLocation();
			System.out.println("Agent has moved 50 times without reaching the goal. Agent has lost.");
		}
		
		if ( hasAgentWon() ) {
			printAgentLocation();
			System.out.println("Agent has reached the goal. Agent has won.");
		}
	}

	public boolean agentIsInGoal() {
		boolean inGoal = false;
		
		if ( agentLocation[0] == (maze.length -1) ) {
			if ( agentLocation[1] == ( (maze[0].length - 1 )/2 ) ) {
				inGoal = true;
			}
		}
		
		return inGoal;
	}
	
	public boolean hasAgentWon() {
		boolean agentWon = false;
		
		if ( agentIsInGoal() ) {
			agentWon = true;
		}	
		return agentWon;
	}
	
	public boolean hasAgentLost() {
		boolean agentLost = false;
		
		if (turnCounter > 50) {
			agentLost = true;
		}
		return agentLost;
	}
	
	// ***** Movement Methods *****
	public void moveAgent() {
		int direction = agent.move(); // Note: should use enumerated types?
		
		if ( isMoveLegal( direction ) ) {
			maze[agentLocation[1]][agentLocation[0]].exitAction();
			switch (direction) {
				case 0: agentLocation[1]--;
						break;
				case 1: agentLocation[0]++;
						break;
				case 2: agentLocation[1]++;
						break;
				case 3: agentLocation[0]--;
						break;
				default: System.out.println("GameController: moveAgent() method default case activated. Something is wrong!");
			}
		}
		maze[agentLocation[1]][agentLocation[0]].enterAction();
			
		printAgentLocation();
	}
	
	public boolean isMoveLegal( int direction ) {
		boolean legality = true;
		int col = agentLocation[0]; // This feels like a hack.
		int row = agentLocation[1];
		
		if ( isDoorLocked( maze[col][row], direction ) ) {
			legality = false;
		}
		
		switch ( direction ) {
			case 0: if ( row == 0 ) { legality = false; } 
					break;
			case 1: if ( col == maze.length -1 ) { legality = false; }  
					break;
			case 2: if ( row == maze[0].length -1 ) { legality = false; }  
					break;
			case 3: if ( col == 0 ) { legality = false; }  
					break;
			default: System.out.println("GameController: isMoveLegal() method default case activated. Something is wrong!");
		}
		
		return legality;
	}
	
	public boolean isDoorLocked( GenericTile tile, int direction ) {
		return !tile.getSingleExit( direction );
	}
		
	// ***** Game Creation Methods *****
	public void createMaze( int cols, int rows, String fileInputIndicator ) {
		maze = new GenericTile[cols][rows];
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				maze[i][j] = new GenericTile();
				maze[i][j].setAllExitsRandom( );
			}
		}
		if(fileInputIndicator.equals("no")){
			maze[0][0] = new StaticTile();
			maze[0][0].setAllExitsRandom();
			maze[0][1] = new StaticTile();
			maze[0][1].setAllExitsRandom();
			maze[0][2] = new StaticTile();
			maze[0][2].setAllExitsRandom();
		}
	}

	public void createMaze(int cols, int rows, boolean doorValues) {
		maze = new GenericTile[cols][rows];
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				maze[i][j] = new GenericTile();
				maze[i][j].setAllExitsValue( doorValues );
			}
		}		
		maze[0][0] = new StaticTile();
		maze[0][0].setAllExitsValue(doorValues);
		maze[0][1] = new RotatingTile();
		maze[0][1].setAllExitsValue(doorValues);
		maze[0][2] = new SolidTile();
	}
	
	public void readMazeFromFile(String fileName){
		try{
			File mazeFile = new File(fileName);
			FileReader fr = new FileReader(mazeFile);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int mazeColumns = Integer.parseInt(br.readLine());
			int mazeRows = Integer.parseInt(br.readLine());
			createMaze(mazeColumns, mazeRows, "yes");
			System.out.println("The maze will have "+mazeColumns+" columns and "+mazeRows+" rows. ");
			while (((line = br.readLine()) != null) && !(line.equals(""))){
				String[] columns = line.split(" ");
				// System.out.println(Arrays.toString(columns));
				// index 3 is null
				createTileFromFile(columns);
				for(int i = 0; i < 4; i++){
					maze[Integer.parseInt(columns[1])][Integer.parseInt(columns[0])].setSingleExit(i, ((columns[4+i].equals("L"))?false:true)); //this is a fire line of code if i do say so myself
				}
			}
			printMaze();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public void createTileFromFile(String[] tileData){
		switch(tileData[2]){
			case "Static":
				maze[Integer.parseInt(tileData[1])][Integer.parseInt(tileData[0])] = new StaticTile();
				break;
			case "Rotating":
				maze[Integer.parseInt(tileData[1])][Integer.parseInt(tileData[0])] = new RotatingTile();
				break;
			case "Solid":			
				maze[Integer.parseInt(tileData[1])][Integer.parseInt(tileData[0])] = new SolidTile();
				break;		
			default: 
				System.out.println("\n\ncreateTileFromFile default case activated: Something is wrong, printing tileData: \n"+Arrays.toString(tileData)+"\n\n");
				System.exit(0);
		}
	}
	
	// ***** Printing Game State Methods *****
	public void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j=0; j < maze[i].length; j++) {
				System.out.print("Tile (" + i + ", " + j + ") " );
				System.out.println( convertExitsToString(i, j));
			}
		}
		printMazeSummary();
	}
	
	public void printMazeSummary() {
		int cols = maze.length;
		System.out.print("There are " + cols + " rows and columns in the maze. ");
		System.out.print("Start is at (" + (cols - 1)/2 + ", 0), ");
		System.out.println("and Goal is at (" + (cols - 1)/2 + ", " + (cols-1) + ").");
	}
	
	public String convertExitsToString( int row, int col ) {
		boolean[] exits = maze[row][col].getAllExits();
		String msg = "(N, E, S, W) status: (";
		
		for (int i = 0; i < exits.length; i++) {
			if (exits[i]) {
				msg = msg + "U, ";
			} else {
				msg = msg + "L, ";
			}
		}
		msg = msg + ").";
		
		return msg;
	}
	
	public void printAgentLocation() {
		System.out.print("The agent is at column " + agentLocation[0]);
		System.out.println(" and row " + agentLocation[1] +".");
	}

	// ***** Setters and Getters *****
	public void setAgent( Agent agentType) {
		switch (agentType) {
		case Generic: agent = new GenericAgent();
					  break;
		case Greedy: agent = new GenericAgent();
					System.out.println("Gamecontroller: Creating Greedy agent requires definition.");
					  break;
		case LeftWall: agent = new GenericAgent();
					System.out.println("Gamecontroller: Creating LeftWall agent requires definition.");
					  break;
		default: agent = new GenericAgent();
		}
		
		agentLocation[0] = 0;
		agentLocation[1] = ( maze[0].length - 1) / 2;
		printAgentLocation();
	}
	

		
}