//***********************************************
// Class: GenericTile
// Author: Keith Garfield, modified by Jack Lee
// Created: October 16, 2022
// Modified: Nov 21, 2022
//
// Attributes: -exits: boolean[4] - An array of length 4 [N, E, S, W], #f = locked, #t = unlocked
//             -description: String - a text description of the room.
//
// Methods: +enterAction: void - Performs an action when an agent enters
//          +exitAction: void - Performs an action when the agent exits
//          +specialAction: void - Performs an action randomly (probability tbd)
//
// Specialized setters and getters:
//          +setAllExitsValue( boolean ): void - Sets all exits to true or false.
//          +setAllExitsRandom(): void - sets all exits randomly (50/50 true or false)
//
//***********************************************

public class GenericTile {
	private boolean[] exits = new boolean[4];
	private String description;
	
	// ***** Methods *****
	public void enterAction() {
	}
	
	public void exitAction() {
	}
	
	public void specialAction() {
	}
	
	// ***** Setters and Getters *****
	public boolean getSingleExit( int direction ) {
		return exits[direction];
	}
	
	public boolean[] getAllExits() {
		return exits;
	}
	
	public void setSingleExit( int direction, boolean lockStatus ) {
		exits[ direction ] = lockStatus;
	}
	
	public void setAllExitsValue( boolean lockStatus ) {
		for (int i = 0; i < exits.length; i++) {
			exits[i] = lockStatus;
		}
	}
	
	public void setAllExitsRandom( ) {
		for (int i = 0; i < exits.length; i++) {
			if (Math.random() > 0.5) {
				exits[i] = true;
			} else {
				exits[i] = false;
			}
		}
	}
}