/* 
Class: TrafficMain
Name: Jack Lee
Date: 11/9/2022
Purpose: runs the main method
Attributes: none

Methods: 
	+main:void(String[]) -main method
 */
 
 public class TrafficMain{
	 public static void main(String[] args){
		 RoadController road = new RoadController();
		 road.runTestCases();
		 road.testObjects();		//comment out this line to only display test case results
	 }
 }