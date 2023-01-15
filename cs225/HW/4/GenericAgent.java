/*
Class: GenericAgent
Name: Jack Lee
Date: 10/11/2022

Purpose: Used to build and modify GenericAgents

Attributes: 
	-currentTile: GenericTile -stores the player's current tile
	-neighborhood: GenericTile[][] -to be implemented in future HW's
	
Methods: 
	+move: char -randomly selects a number 1-4 and outputs a direction as a char
	+printTileDescription: void -to be implemented in future HW's
*/
public class GenericAgent {
	private GenericTile currentTile;
	private GenericTile[][] neighborhood = new GenericTile[3][3];
	
	public char move(){
		int rand = (int)(Math.random()*4)+1;
		char result = 'N';
		switch(rand){
			case 1:
				result = 'N';
				break;
			case 2:
				result = 'E';
				break;
			case 3:
				result = 'S';		
				break;
			case 4:
				result = 'W';	
				break;
		}
		return result;
	}
	
	public void printTileDescription(){
		
	}
	
	public void setCurrentTile(GenericTile newTile){
		currentTile = newTile;
	}
	public GenericTile getCurrentTile(){
		return currentTile;
	}
}