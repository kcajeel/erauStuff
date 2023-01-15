/*
Class: StaticTile
Name: Jack Lee
Date: 11/21/2022
Purpose: Static Tile
Attributes: 
	Inheirited from GenericTile
Methods: 
	+enterAction:void -tells the user that a static tile has been entered
	+exitAction:void -tells the user that a static tile has been exited
	+specialAction:void -does nothing
*/
public class StaticTile extends GenericTile{
	public void enterAction(){
		System.out.println("Static Tile entered. ");
	}
	public void exitAction(){
		System.out.println("Static Tile exited. ");
	}
	public void specialAction(){}		
}