/*
Class: GameController
Name: Jack Lee
Date: 10/11/2022

Purpose: Contains methods necessary for Maze Game operation

Attributes: 
	-maze: GenericTile[][] -used to store the maze data
	-player: GenericAgent -used to simulate the player (implemented in future HW's)
	-playerLocation: int[2] -used to store the player's current location (implemented in future HW's)
	-turnCounter: int -used to store the turn number (implemented in future HW's)

Methods: 
	+move(char): void -used to move the player (implemented in future HW's)
	+createMaze(int): GenericTile[][] -used to initialize the maze given a size
	+printMaze(): void -prints each maze cell and which doors are (un)locked
*/

public class GameController {
	
	private GenericTile[][] maze;
	//private GenericAgent player;
	private int[] playerLocation = new int[2];
	private int turnCounter;
	
	public void move(char direction){
		
	}
	
	public GenericTile[][] createMaze(int size){
		if(size%2 == 0){
			System.out.println("Please choose an odd number for the maze size. ");
		}
		else {
			maze = new GenericTile[size][size];
			for(int i = 0; i < maze.length; i++){
				for(int j = 0; j < maze[0].length; j++){
					maze[i][j] = new GenericTile();
					for(int k = 0; k < 4; k++){
						maze[i][j].setExit(k,(((int)(Math.random()*2%2) == 0)?true:false));
					}
				}
			}
		}
		return maze;
	}
	
	public void printMaze(){
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				System.out.print("Tile ("+i+", "+j+") (N, S, E, W) status: (");
				for(int k = 0; k < maze[i][j].getExits().length; k++){
					System.out.print(((maze[i][j].getExits()[k] == false)?"U":"L"));
					if(k < 3)
						System.out.print(",");
				}
				System.out.println(")");
			}
		}
		//Start node: (0, [n-1]/2), Goal node: ( n-1, [n-1]/2)
		System.out.print("There are "+maze.length+" rows and "+maze[0].length+" columns in the Maze. "
		+"Start is at (0, "+(maze.length-1)/2+"), and Goal is at ("+(maze.length-1)+", "+(maze.length-1)/2+"). ");
	}
	
	//setters and getters
	public void setMazeTile(int row, int column, GenericTile tile){
		maze[row][column] = tile;
	}
	public void setPlayerLocation(int index, int newValue){
		playerLocation[index] = newValue;
	}
	public void setTurnCounter(int newTurnCounter){
		turnCounter = newTurnCounter;
	}
	public int[] getPlayerLocation(){
		return playerLocation;
	}
	public int getTurnCounter(){
		return turnCounter;
	}
}