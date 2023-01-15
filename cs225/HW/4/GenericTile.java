/*
Class: GenericTile
Name: Jack Lee
Date: 10/11/2022

Purpose: Used to build and modify GenericTiles

Attributes: 
	-exits: boolean[] -stores which exits are (un)locked
	-description: String -stores a tile description
	
Methods: 
	+enterAction: void -to be implemented in future HW's
	+exitAction: void -to be implemented in future HW's
	+specialAction: void -to be implemented in future HW's
*/

public class GenericTile {
	
	private boolean[] exits = new boolean[4];
	private String description;
	
	public void enterAction(){
		
	}
	
	public void exitAction(){
		
	}
	
	public void specialAction(){
		
	}
	
	//setters and getters
	public void setExit(int index, boolean newStatus){
		exits[index] = newStatus;
	}
	public void setDescription(String newDescription){
		description = newDescription;
	}
	public boolean[] getExits(){
		return exits;
	}
	public String getDescription(){
		return description;
	}
}