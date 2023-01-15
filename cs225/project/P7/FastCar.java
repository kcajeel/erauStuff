/*
Class: FastCar
Name: Jack Lee
Date: 11/18/2022
Purpose: inheirits GenericCar attributes to create FastCar objects
Attributes: 
	inheirited from GenericCar
Methods: 
	+(double, double, GenericDriver):FastCar -overridden constructor
*/
public class FastCar extends GenericCar{
	public FastCar(double distance, double lane, GenericDriver driver){
		super(distance, lane, driver, getRandom(6,8), getRandom(6,8),(int)getRandom(90,110));
	}
}