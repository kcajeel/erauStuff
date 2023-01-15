/*
Class: RotatingTile
Name: Jack Lee
Date: 11/21/2022
Purpose: Rotating Tile
Attributes: 
	-tempExits:boolean -used to store exits during rotations
Methods: 
	+enterAction:void -tells the user that a rotating tile has  entered
	+exitAction:void -tells the user that a roatating tile has been exited and "rotates" the tile 90 degrees clockwise
	+specialAction:void -tells the user the tile is rotating counterclockwise and "rotates" the tile 90 degrees counterclockwise
*/
public class RotatingTile extends GenericTile{
	
	private boolean[] tempExits = getAllExits();
	
	public void enterAction(){
		System.out.println("Rotating Tile entered. ");
	}
	public void exitAction(){
		System.out.println("Rotating Tile exited. Rotating clockwise. ");
		//NESW
		//N -> E, E-> S, S-> W, W-> N
		for(int i = 1; i < tempExits.length; i++){
			setSingleExit(i, tempExits[i-1]);
		}
		setSingleExit(0, tempExits[3]);
	}
	public void specialAction(){
		System.out.println("Rotating counterclockwise. ");
		for(int i = tempExits.length-1; i > -1; i--){
			setSingleExit(i, tempExits[i+1]);
		}
		setSingleExit(3, tempExits[0]);
	}	
}