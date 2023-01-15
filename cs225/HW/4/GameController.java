/*
Class: GameController
Name: Jack Lee
Date: 10/11/2022
Purpose: Contains methods necessary for Maze Game operation
Attributes: 
	-maze: GenericTile[][] -used to store the maze data
	-player: GenericAgent -used to simulate the player 
	-playerLocation: int[2] -used to store the player's current location 
	-turnCounter: int -used to store the turn number 
	-playerMoved: boolean -used to tell if the player  moved( sometimes the move requested is invalid)
Methods: 
	+createMaze(int): GenericTile[][] -used to initialize the maze given a size
	+startGame():void -controls game end conditions and calls move() method
	+move(char): void -moves the player to a valid tile given directional input
	+findPlayer():void -sets the playerLocation attribute equal to the player's current location
	+printMaze(): void -prints each maze cell and which doors are (un)locked
*/

public class GameController {
	
	private GenericTile[][] maze;
	private GenericAgent player;
	private int[] playerLocation = new int[2];
	private int turnCounter;
	private boolean playerMoved;
	
	public GenericTile[][] createMaze(int xSize, int ySize){
		if((xSize%2 == 0) || (ySize%2 == 0)){
			System.out.println("Please choose an odd number for the maze size. ");
		}
		else {
			int lockCount = 0;
			maze = new GenericTile[xSize][ySize];
			for(int i = 0; i < maze.length; i++){
				for(int j = 0; j < maze[0].length; j++){
					maze[i][j] = new GenericTile();
					for(int k = 0; k < 4; k++){
						maze[i][j].setExit(k,(((int)(Math.random()*2%2) == 0)?true:false));
						if(maze[i][j].getExits()[k]){
							lockCount++;
						}
					}
					if(lockCount == 4){
						maze[i][j].setExit((int)Math.random()*2, true);
					}
					lockCount = 0;
				}
			}
		}
		return maze;
	}

	public void startGame(){
		turnCounter = 0;
		player = new GenericAgent();
		player.setCurrentTile(maze[(maze.length-1)/2][0]);
		while((player.getCurrentTile() != maze[maze.length-1][(maze.length-1)/2]) && turnCounter < 50){
			findPlayer();
			move(player.move());
			if(playerMoved){
				// because of the random movement it can take a while for the player to move and it gets stuck, 
				// if this happens just exit and re-run it works like half the time
				turnCounter++;
			}
		}
		if(turnCounter > 49)
			System.out.println("Game over! The player ran out of turns. ");
		else
			System.out.println("The player reached the Goal!");
	}
	public void move(char direction){
		findPlayer();
		playerMoved = false;
		int oldX = playerLocation[0];
		int oldY = playerLocation[1];
		switch(direction){
			case 'N':
				if((playerLocation[0] > 0) && !(player.getCurrentTile().getExits()[0])){
					player.setCurrentTile(maze[playerLocation[0]-1][playerLocation[1]]);
					playerMoved = true;
				}
				break;
			case 'E':
				if((playerLocation[1] < maze[0].length-1) && !(player.getCurrentTile().getExits()[2])){
					player.setCurrentTile(maze[playerLocation[0]][playerLocation[1]+1]);
					playerMoved = true;
				}
				break;
			case 'S':
				if((playerLocation[0] < maze.length-1) && !(player.getCurrentTile().getExits()[1])){
					player.setCurrentTile(maze[playerLocation[0]+1][playerLocation[1]]);
					playerMoved = true;
				}
				break;
			case 'W': 
				if((playerLocation[1] > 0) && !(player.getCurrentTile().getExits()[3])){
					player.setCurrentTile(maze[playerLocation[0]][playerLocation[1]-1]);
					playerMoved = true;
				}
				break;
			default:
				System.out.println("it didn't work, recieved: "+direction);
				playerMoved = false;
				break;
		}
		findPlayer();
		if(playerMoved){
	System.out.println("The player moved "+direction+" from ("+oldX+", "+oldY+") to ("+playerLocation[0]+", "+playerLocation[1]+") ");
		}
	}
	
	public void findPlayer(){
		// i is row, j is column
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				if(maze[i][j] == player.getCurrentTile()){
					playerLocation[0] = i;
					playerLocation[1] = j;
				}
			}
		}
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
		System.out.println("There are "+maze.length+" rows and "+maze[0].length+" columns in the Maze. "
		+"Start is at (0, "+(maze.length-1)/2+"), and Goal is at ("+(maze.length-1)+", "+(maze.length-1)/2+"). "+
		"\nI'm having trouble with part 4.1 in the SDD, sometimes the ouput just pauses like it's stuck. \nIf that happens re-run the MGMain, it works but it gets stuck sometimes.");
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
	public GenericTile getMazeTile(int row, int column){
		return maze[row][column];
	}
	public int[] getPlayerLocation(){
		return playerLocation;
	}
	public int getTurnCounter(){
		return turnCounter;
	}
}