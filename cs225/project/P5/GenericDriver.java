/* 
Class: GenericDriver
Name: Jack Lee
Date: 11/18/2022
Purpose: Contains all attributes and methods to create a Driver object
Attributes: 
	-acceleration:double
	-deceleration:double
	-maxSpeed:double
	-visibility:double --possible rename to followDistance

Methods: 
	+getRandom(int, int):double -returns a random value between two integers. 
	+constructor, setters, getters, toString
 */
 
public class GenericDriver{
	private double acceleration,deceleration,maxSpeed,visibility;
	
	public GenericDriver(double setAcceleration, double setDeceleration, double setMaxSpeed, double setVisibility){
		acceleration = setAcceleration;
		deceleration = setDeceleration;
		maxSpeed = setMaxSpeed;
		visibility = setVisibility;
	}
	
	public static double getRandom(int min, int max){
		return (Math.random()*(max-min))+min;
	}
	
	//setters and getters
	public void setAcceleration(double newAcceleration){
		acceleration = newAcceleration;
	}
	public void setDeceleration(double newDeceleration){
		deceleration = newDeceleration;
	}
	public void setMaxSpeed(double newMaxSpeed){
		maxSpeed = newMaxSpeed;
	}
	public void setVisibility(double newVisibility){
		visibility = newVisibility;
	}
	
	public double getAcceleration(){
		return acceleration;
	}
	public double getDeceleration(){
		return deceleration;
	}
	public double getMaxSpeed(){
		return maxSpeed;
	}
	public double getVisibility(){
		return visibility;
	}
	
	public String toString(){
		return "This Driver has acceleration of: "+acceleration+", deceleration of: "+deceleration+"\nMax speed of: "+maxSpeed+", and visibility of: "+visibility+". ";
	}
}