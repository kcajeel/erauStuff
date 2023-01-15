/*
Class: FastDriver
Name: Jack Lee
Date: 11/9/2022
Purpose: inheirits GenericDriver attributes to create FastDriver objects
Attributes: 
	inheirited from GenericDriver
Methods: 
	+(double, double, GenericDriver):FastDriver -overridden constructor
*/
public class FastDriver extends GenericDriver{
	public FastDriver(){
		super(getRandom(5,8),getRandom(5,8),(int)getRandom(70,110), 5);
	}
}