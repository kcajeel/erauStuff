/* 
Class: TrafficMain
Name: Jack Lee
Date: 11/18/2022
Purpose: runs the main method
Attributes: none

Methods: 
	+main:void(String[]) -main method
Note: The .txt/.jpeg/.traffic files are used during test cases
 */
 
 public class TrafficMain{
	 public static void main(String[] args){
		 RoadController road = new RoadController();
		 // road.readFromFile("asdf.txt");
		 road.runTestCases();
		 // road.testObjects();		//comment out this line to only display test case results
	 }
 }