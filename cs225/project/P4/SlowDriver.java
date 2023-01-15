/*
Class: SlowDriver
Name: Jack Lee
Date: 11/9/2022
Purpose: inheirits GenericDriver attributes to create SlowDriver objects
Attributes: 
	inheirited from GenericDriver
Methods: 
	+(double, double, GenericDriver):SlowDriver -overridden constructor
*/
public class SlowDriver extends GenericDriver{
	public SlowDriver(){
		super(getRandom(1,3),getRandom(1,3),(int)getRandom(50,70), 10);
	}
}