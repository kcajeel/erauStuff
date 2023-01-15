//***********************************************
// Class: MGMain
// Author: Keith Garfield (used but not modified by Jack Lee)
// Created: October 16, 2022
// Modified: October 30, 2022 - Added requests to create agent and play the game.
//
// Attributes: +ctrl: GameController - Sets up and executes the game.
//
// Methods: +main(String[]): void - Creates the GameController object and begins the game.
//          +createMaze(int, int): void - Checks for legality of maze, requests controller to create the maze.
//                                 Maze is created with randomize locked/unlocked doors.
//          +createMaze(int, int, boolean): void - Maze is created with all doors locked or unlocked.
//          +isMazeLegal(int, int): boolean - Returns true iff maze is n-by-n and n is odd.
//          +createAgent( Agent ): void - Requests controller to create agent. Input enumerated type Agent.
//          +playGame(): void - Requests controller to play the entire game.
//
//***********************************************

public class MGMain {
	private GameController ctrl = new GameController();
	
	// ***** Methods *****
	public static void main( String[] args ){
		MGMain me = new MGMain();
		me.createMaze(5, 5);
		me.createAgent( Agent.Generic );
		me.playGame();
	}
	
	public void createMaze(int rows, int cols) {
		if (isMazeLegal(rows, cols) ) {
			ctrl.createMaze( rows, cols );
			ctrl.printMaze();
		}
	}

	public void createMaze(int rows, int cols, boolean doorValue) {
		if (isMazeLegal(rows, cols) ) {
			ctrl.createMaze( rows, cols, doorValue );
			ctrl.printMaze();
		}
	}
	
	public void createAgent( Agent agentType) { 
		ctrl.setAgent( agentType);
	}
	
	public void playGame() {
		ctrl.playGame();
	}

	public boolean isMazeLegal(int rows, int cols) {
		boolean legality = true;
		
		if ( rows != cols ) {
			legality = false;
			System.out.println("The maze does not have an equal number of rows and columns.");
			System.out.println("The maze will not be created.");
		}
		
		if ( rows%2 != 1 ) {
			legality = false;
			System.out.println("The maze does not have an odd number of rows and columns.");
			System.out.println("The maze will not be created.");
		}
		
		return legality;
	}
	
	// ***** Setters and Getters *****
}