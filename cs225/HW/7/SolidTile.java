/*
Class: SolidTile
Name: Jack Lee
Date: 11/21/2022
Purpose: Solid Tile
Attributes: 
	Inheirited from GenericTile
Methods: 
	+constructor -sets all exits to false
	+enterAction:void -tells the user that solid tiles shouldn't be entered
	+exitAction:void -tells the user that solid tiles shouldn't be exited
	+specialAction:void -does nothing
*/
public class SolidTile extends GenericTile{
	public SolidTile(){
		super();
		setAllExitsValue(false);
	}
	public void enterAction(){
		System.out.println("Solid tiles shouldn't be entered. ");
	}
	public void exitAction(){
		System.out.println("Solid tiles shouldn't be exited. ");
	}
	public void specialAction(){}		
}