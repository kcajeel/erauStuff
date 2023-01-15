/*
Class: PrizeBoxes
Author: Jack Lee
Last Modified: 9/26/2022

Purpose: Creates an integer array of "prize boxes" for the prize box game

Attributes: 
	-boxArray:int
Methods: 
	+createArray(int size):void- used to declare the array size
	+initializeArray():void- initializes each index to a random integer from 20-100 and initializes random indeces to 0 and -100
	+checkBoxes():void- verifies that the boxes meet the criteria set in initializeArray
	+printArray():void- prints the boxArray values, used to help with program development
	+getPrize(int box):int- returns the value stored at the index selected
	+setPrize(int box, int prize):void- sets the box specified to contain the prize specified
*/

public class PrizeBoxes{	

	private int[] boxArray;
	
	public void createArray(int size){
		boxArray = new int[size];
	}
	
	public void initializeArray(){
		for(int i = 0; i < boxArray.length; i++){
			boxArray[i] = (int)(Math.random()*81)+20;
		}
		boxArray[(int)(Math.random()*boxArray.length)] = 0;
		boxArray[(int)(Math.random()*boxArray.length)] = -100;
	}
	
	public void checkBoxes(){
		boolean lowPrizeCheck = false;
		boolean highValueCheck = false;
		for(int i: boxArray){
			if(i > 100){
				highValueCheck = true;
			}
			if(i == -100){
				lowPrizeCheck = true;
			}
		}
		if(highValueCheck){
			System.out.println("There is at least one box value greater than 100. ");
		}
		if(!lowPrizeCheck){
			System.out.println("No box has value -100. ");
		}
	}	
	
	public void printArray(){
		for (int i: boxArray){
			System.out.println(i);
		}
	}
	
	public int getPrize(int box){
		return boxArray[box];
	}
	
	public void setPrize(int box, int prize){
		boxArray[box] = prize;
	}
}