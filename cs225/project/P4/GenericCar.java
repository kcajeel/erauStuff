/* 
Class: GenericCar
Name: Jack Lee
Date: 11/9/2022
Purpose: Contains all attributes and methods to create a Car object
Attributes: 
	-position:double[2]
	-driver:GenericDriver
	-acceleration:double
	-deceleration:double
	-maxSpeed:double
	-speed:double
Methods: 
	+getRandom(int, int):double -returns a random value between two integers. 
	+constructor, setters, getters, toString
 */
 
 public class GenericCar{
	private double[] position = new double[2];
	private GenericDriver driver;
	private double acceleration,deceleration,maxSpeed,speed;
	
	public GenericCar(double distance, double lane, GenericDriver setDriver, double setAcceleration, double setDeceleration, double setMaxSpeed){
		position[0] = distance;
		position[1] = lane;
		driver = setDriver;
		acceleration = setAcceleration;
		deceleration = setDeceleration;
		maxSpeed = setMaxSpeed;
	}
	
	public static double getRandom(int min, int max){
		return (Math.random()*(max-min))+min;
	}
	
	//setters and getters
	public void setPosition(int index, double newPosition){
		position[index] = newPosition;
	}
	public void setDriver(GenericDriver newDriver){
		driver = newDriver;
	}
	public void setAcceleration(double newAcceleration){
	 acceleration = newAcceleration;
	}
	public void setDeceleration(double newDeceleration){
		deceleration = newDeceleration;
	}
	public void setMaxSpeed(double newMaxSpeed){
		maxSpeed = newMaxSpeed;
	}
	public void setSpeed(double newSpeed){
		speed = newSpeed;
	}
	
	public double getPosition(int index){
		return position[index];
	}
	public GenericDriver getDriver(){
		return driver;
	}
	public String getDriverInfo(){
		return driver.toString();
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
	public double getSpeed(){
		return speed;
	}
	
	public String toString(){
		return "\n\nThis car's distance is: "+position[0]+", and its lane is: "+position[1]
		+". \nThe acceleration is: "+acceleration+", the deceleration is: "+deceleration+", the max speed is: "+maxSpeed+", and the current speed is: "+speed
		+". \nThe driver information is below. \n\n"+driver.toString();
	}
 }