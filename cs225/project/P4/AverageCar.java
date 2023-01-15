/*
Class: AverageCar
Name: Jack Lee
Date: 11/9/2022
Purpose: inheirits GenericCar attributes to create AverageCar objects
Attributes: 
	inheirited from GenericCar
Methods: 
	+(double, double, GenericDriver):AverageCar -overridden constructor
*/
public class AverageCar extends GenericCar{
	public AverageCar(double distance, double lane, GenericDriver driver){
		super(distance, lane, driver, getRandom(3,5), getRandom(3,5),(int)getRandom(75,90));
	}
}