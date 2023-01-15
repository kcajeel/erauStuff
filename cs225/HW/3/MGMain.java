/*
Class: MGMain
Name: Jack Lee
Date: 10/11/2022

Purpose: Used to run the Maze Game

Attributes: 
	none
	
Methods: 
	+main(String[] args): void -main method
*/

public class MGMain {

	public static void main(String[] args){
		GameController game = new GameController();
		game.createMaze(5);
		game.printMaze();
	}
}