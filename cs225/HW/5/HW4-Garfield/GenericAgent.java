//***********************************************
// Class: GenericAgent
// Author: Keith Garfield (used but not modified by Jack Lee)
// Created: October 29, 2022
// Modified:
//
// Attributes: -currenTile: GenericTile - The tile tht agent is currently in.
//             -neighborhood: GenericTile[][] - a 3-by-3 array of tiles composed of current tile plus
//                            the eight adjacent tiles.  
//
// Methods: +move(): int - Returns a random value: 0 = N, 1 = E, 2 = S, 3 = W.
//
// Specialized setters and getters:
//          +setNeighborhood( GenericTile[][] ): void - Sets the 3-by-3 array of neighborhood tiles.
//
//***********************************************
public class GenericAgent {
	private GenericTile currenTile;
	private GenericTile[][] neighborhood = new GenericTile[3][3];
		
	// ***** Methods *****
	public int move() {
		return (int) Math.floor(4 * Math.random() );
	}
	
	// ***** Setters and Getters *****
	public void setCurrentTile( GenericTile newTile ) {
		currenTile = newTile;
	}
	
	public GenericTile getCurrentTile() {
		return currenTile;
	}
	
	public void setNeighborhood( GenericTile[][] newNeighbors ) {
		System.out.println("GenericAgent: setNeighborhood requires definition.");
	}
}