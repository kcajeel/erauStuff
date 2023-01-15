/*
Class: GameController
Author: Jack Lee
Last Modified: 9/26/2022

Purpose: Allows the user to select five prize boxes and reports the sum of each selection

Attributes: 
	-boxes:PrizeBoxes- used to store and access the prize box data
Methods: 
	+testCase(int num, int value):void- sets all prize boxes to the specified value and notifies the user if the array contians irregular data, prints out the test case number
	+pickBoxes:void- accepts user input for boxes, prints out and sums the boxes chosen, and prints the sum
*/
import java.util.Scanner;
public class GameController {
	
	private PrizeBoxes boxes = new PrizeBoxes();
	
	public static void main(String[] args){
		GameController me = new GameController();
		me.testCase(1,101);
		me.testCase(2,50);
		me.pickBoxes();
	}
	
	public void testCase(int num, int value){
		System.out.println("\nTest Case "+num+": ");
		boxes.createArray(30);
		for(int i = 0; i < 30; i++){
			boxes.setPrize(i,value);
		}
		boxes.checkBoxes();
	}
	
	public void pickBoxes(){
		boxes.createArray(30);
		boxes.initializeArray();
		//boxes.printArray();
		boxes.checkBoxes();
		Scanner scanny = new Scanner(System.in);
		int boxSum = 0;
		for(int i = 0; i < 5; i++){
			System.out.println("\nPlease pick a prize box 1-30. Choice "+(i+1)+ " of 5. ");
			int choice = scanny.nextInt();
			boxSum += boxes.getPrize(choice-1);
			System.out.println("Box "+choice+" has "+boxes.getPrize(choice-1)+" prizes. ");
		}
		scanny.close();
		System.out.println("You have won "+boxSum+" total prizes. ");
	}	
}