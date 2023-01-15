/* 
Class: RoadController
Name: Jack Lee
Date: 11/9/2022
Purpose: contains all methods to run the traffic simulation
Attributes: 
	-cars:ArrayList<GenericCar>
	-speedLimit:double
	-weather:String
Methods: 
	+testObjects:void -used during development
	+runTestCases:void -runs test cases
	+isDistanceValid(GenericCar):boolean -boolean test
	+isSpeedValid(GenericCar):boolean -boolean test
	+isCarAccelerationGreater(GenericCar):boolean -boolean test
	+isCarDecelerationGreater(GenericCar):boolean -boolean test
	+isCarMaxSpeedGreater(GenericCar):boolean -boolean test
 */
 import java.util.ArrayList;
 
 public class RoadController{
	private ArrayList<GenericCar> cars = new ArrayList<GenericCar>();
	private double speedLimit;
	private String weather;
	
	public void testObjects(){
		end test code
		while (1 == 1){
			for(GenericCar i: cars){
				if(!(isSpeedValid(i))){
					i.setSpeed(i.getSpeed() + i.getAcceleration());
				}
				if(isDistanceValid(i) && !isSpeedValid(i)){
					System.out.println(i.toString());
					i.setPosition(0,i.getPosition(0)+i.getSpeed());
				}
			}
		}
	}
	
	public void runTestCases(){
		GenericDriver testDriver = new GenericDriver(10,10,200,10);
		GenericCar testCar = new GenericCar(0,0,testDriver,7,7,150);		//test case 1
		System.out.println("If the code is runing, Test Case 1 passed. \nTest Case 2: "+testCar.getDriver().getMaxSpeed()+"\nTest Case 3: "+testCar.getDriverInfo()+"\nTest Case 4: position before: "+testCar.getPosition(0));
		testCar.setPosition(0,45);
		System.out.println("Position after: "+testCar.getPosition(0)+"\nTest Case 5: lane before: "+testCar.getPosition(1));
		testCar.setPosition(1,3);
		System.out.println("Lane after: "+testCar.getPosition(1)+"\nTest Case 6: Distance before: "+testCar.getPosition(0));
		testCar.setSpeed(testCar.getSpeed()+testCar.getAcceleration());
		testCar.setPosition(0,testCar.getPosition(0)+testCar.getSpeed());
		System.out.println("Distance after: "+testCar.getPosition(0));
		cars.add(testCar);
	}
	public boolean isDistanceValid(GenericCar car){
		if(car.getPosition(0) < 100){
			return true;
		}
		car.setPosition(0,0);
		return false;
	}
	
	public boolean isSpeedValid(GenericCar car){
		if((car.getSpeed() > car.getMaxSpeed()) && (car.getSpeed() < speedLimit)){
			return true;
		}
		car.setSpeed(speedLimit);
		return false;
	}
	
	public boolean isCarAccelerationGreater(GenericCar car){
		if(car.getAcceleration() > car.getDriver().getAcceleration()){
			return true;
		}
		return false;
	}
	
	public boolean isCarDecelerationGreater(GenericCar car){
		if(car.getDeceleration() > car.getDriver().getDeceleration()){
			return true;
		}
		return false;
	}
	
	public boolean isCarMaxSpeedGreater(GenericCar car){
		if(car.getMaxSpeed() > car.getDriver().getMaxSpeed()){
			return true;
		}
		return false;
	}
 }