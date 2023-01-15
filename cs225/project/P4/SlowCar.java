/*
Class: SlowCar
Name: Jack Lee
Date: 11/9/2022
Purpose: inheirits GenericCar attributes to create SlowCar objects
Attributes: 
	inheirited from GenericCar
Methods: 
	+(double, double, GenericDriver):SlowCar -overridden constructor
*/
public class SlowCar extends GenericCar{
	public SlowCar(double distance, double lane, GenericDriver driver){
		super(distance, lane, driver, getRandom(2,4), getRandom(2,4),(int)getRandom(60,70));
	}
}