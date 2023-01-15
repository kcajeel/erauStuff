/*
Class: AverageDriver
Name: Jack Lee
Date: 11/9/2022
Purpose: inheirits GenericCar attributes to create AverageDriver objects
Attributes: 
	inheirited from GenericDriver
Methods: 
	+(double, double, GenericDriver):AverageDriver -overridden constructor
*/
public class AverageDriver extends GenericDriver{
	public AverageDriver(){
		super(getRandom(4,5),getRandom(4,5),(int)getRandom(60,80), 7);
	}
}